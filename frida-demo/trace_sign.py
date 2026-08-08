import idaapi
import idc
import re
import ida_dbg
import ida_idd
import ida_kernwin
import ida_loader
import ida_bytes
import ida_segment
from idaapi import *
from collections import OrderedDict
import logging
import time
import datetime
import os

debughook = None

def xx_hex(ea):
    return hex(ea).rstrip("L").lstrip("0x")

def set_breakpoint(ea, isthumb=1):
    # idc.SetReg(ea, "T", 1)    
    # IDA 9.1 兼容：使用 create_insn 替代 MakeCode
    try:
        # 先尝试新 API
        idc.del_items(ea)
        idc.create_insn(ea)
    except:
        # 如果新 API 失败，尝试旧 API（兼容降级）
        idc.MakeCode(ea)
    idc.add_bpt(ea)

def my_get_reg_value(register):
    rv = ida_idd.regval_t()
    ida_dbg.get_reg_val(register, rv)
    current_addr = rv.ival
    return current_addr


def suspend_other_thread():
    current_thread = idc.get_current_thread()
    thread_count = idc.get_thread_qty()
    for i in range(0, thread_count):
        other_thread = idc.getn_thread(i)
        if other_thread != current_thread:
            idc.suspend_thread(other_thread)

def resume_process():
    current_thread = idc.get_current_thread()
    thread_count = idc.get_thread_qty()
    for i in range(0, thread_count):
        other_thread = idc.getn_thread(i)
        if other_thread != current_thread:
            idc.resume_thread(other_thread)
    idc.resume_thread(current_thread)
    idc.resume_process()

class MyDbgHook(DBG_Hooks):
    """ Own debug hook class that implementd the callback functions """

    def __init__(self, modules_info, skip_functions, end_ea):
        super(MyDbgHook, self).__init__()
        self.modules_info = modules_info
        self.skip_functions = skip_functions
        self.trace_step_into_count = 0
        self.trace_step_into_size = 1
        self.trace_total_size = 300000
        self.trace_size = 0
        self.trace_lr = 0
        self.end_ea = end_ea
        self.bpt_trace = 0
        self.Logger = None
        self.line_trace = 0
        print("__init__")

    def start_line_trace(self):
        self.bpt_trace = 0
        self.line_trace = 1
        self.start_hook()

    def start_hook(self):
        self.hook()
        print("start_hook")

    def dbg_process_start(self, pid, tid, ea, name, base, size):
        print("Process started, pid=%d tid=%d name=%s" % (pid, tid, name))

    def dbg_process_exit(self, pid, tid, ea, code):
        self.unhook()
        if self.Logger:
            self.Logger.log_close()
        print("Process exited pid=%d tid=%d ea=0x%x code=%d" % (pid, tid, ea, code))

    def dbg_process_detach(self, pid, tid, ea):
        self.unhook()
        if self.Logger:
            self.Logger.log_close()
        return 0

    def dbg_bpt(self, tid, ea):
        print("Break point at 0x%x tid=%d" % (ea, tid))
        if ea in self.end_ea:
            ida_dbg.enable_insn_trace(False)
            ida_dbg.enable_step_trace(False)
            ida_dbg.suspend_process()
            return 0
        return 0

    def dbg_trace(self, tid, ea):
        #print("Trace tid=%d ea=0x%x" % (tid, ea))
        # return values:
        #   1  - do not log this trace event;
        #   0  - log it
        if self.line_trace:
            in_mine_so = False
            for module_info in self.modules_info:
                # print (module_info)
                so_base = module_info["base"]
                so_size = module_info["size"]
                if so_base <= ea <= (so_base + so_size):
                    in_mine_so = True
                    break

            self.trace_size += 1
            if (not in_mine_so) or (ea in self.skip_functions):
                if (self.trace_lr != 0) and (self.trace_step_into_count < self.trace_step_into_size):
                    self.trace_step_into_count += 1
                    return 0

                if (self.trace_lr != 0) and (self.trace_step_into_count == self.trace_step_into_size):
                    ida_dbg.enable_insn_trace(False)
                    ida_dbg.enable_step_trace(False)
                    ida_dbg.suspend_process()
                    if self.trace_size > self.trace_total_size:
                        self.trace_size = 0
                        ida_dbg.request_clear_trace()
                        ida_dbg.run_requests()

                    ida_dbg.request_run_to(self.trace_lr & 0xFFFFFFFE)
                    ida_dbg.run_requests()
                    self.trace_lr = 0
                    self.trace_step_into_count = 0
                    return 0

                if self.trace_lr == 0:
                    self.trace_lr = my_get_reg_value("LR")
            return 0

    def dbg_run_to(self, pid, tid=0, ea=0):
        # print("dbg_run_to 0x%x pid=%d" % (ea, pid))
        if self.line_trace:
            ida_dbg.enable_insn_trace(True)
            ida_dbg.enable_step_trace(True)
            ida_dbg.request_continue_process()
            ida_dbg.run_requests()


def unhook():
    global debughook
    # Remove an existing debug hook
    try:
        if debughook:
            print("Removing previous hook ...")
            debughook.unhook()
            if debughook.Logger:
                debughook.Logger.log_close()
    except:
        pass


def starthook():
    global debughook
    if debughook:
        debughook.start_line_trace()


def get_modules_by_name(target_names):
    """
    IDA 9.1 兼容方式获取模块列表
    通过遍历所有段来找到目标模块
    """
    modules = []
    try:
        # 尝试 IDA 9.1 方式
        seg_qty = ida_segment.get_segm_qty()
        for i in range(seg_qty):
            seg = ida_segment.getnseg(i)
            if not seg:
                continue
            seg_name = ida_segment.get_segm_name(seg)
            seg_start = seg.start_ea
            seg_end = seg.end_ea
            seg_size = seg_end - seg_start
            
            for target in target_names:
                if target in seg_name:
                    found = False
                    for mod in modules:
                        if mod["base"] == seg_start:
                            found = True
                            break
                    if not found:
                        modules.append({
                            "base": seg_start,
                            "size": seg_size,
                            "name": seg_name,
                            "end": seg_end
                        })
                    break
    except:
        # 降级方案：尝试旧 API
        try:
            for module in idc._get_modules():
                module_name = os.path.basename(module.name)
                for target in target_names:
                    if re.search(target, module_name, re.IGNORECASE):
                        modules.append({
                            "base": module.base,
                            "size": module.size,
                            "name": module.name
                        })
                        break
        except:
            # 最后的降级方案：使用 idaapi 旧方式
            try:
                for module in idaapi.get_modules():
                    module_name = os.path.basename(module.name)
                    for target in target_names:
                        if re.search(target, module_name, re.IGNORECASE):
                            modules.append({
                                "base": module.base,
                                "size": module.size,
                                "name": module.name
                            })
                            break
            except:
                print("Warning: Unable to get modules using any available API")
    return modules


def main():
    global debughook
    unhook()
    skip_functions = []
    modules_info = []
    start_ea = 0
    end_ea = []
    so_modules = ["libhello-jni.so"]        # module name
    
    # 使用兼容方式获取模块
    all_modules = get_modules_by_name(so_modules)
    
    if all_modules:
        for module in all_modules:
            module_name = module["name"]
            module_base = module["base"]
            module_size = module["size"]
            print("modules_info append %08X %s %08X" % (module_base, module_name, module_size))
            
            modules_info.append({
                "base": module_base,
                "size": module_size,
                "name": module_name
            })
            
            if "libhello-jni.so" in module_name:
                start_ea = module_base + 0x1CFF0
                end_ea = [module_base + 0x1D6D4]
    else:
        # 如果找不到模块，尝试使用旧的 _get_modules 方式
        print("Warning: No modules found via segment traversal, trying legacy method...")
        try:
            for module in idc._get_modules():
                module_name = os.path.basename(module.name)
                for so_module in so_modules:
                    if re.search(so_module, module_name, re.IGNORECASE):
                        print("modules_info append %08X %s %08X" % (module.base, module.name, module.size))
                        if module_name == "libhello-jni.so":
                            modules_info.append({"base": module.base, "size": module.size, "name": module.name})
                            start_ea = (module.base + 0x1CFF0)
                            end_ea = [((module.base + 0x1D6D4))]
                            break
        except:
            print("Error: Unable to find target module, please check module name and ensure it's loaded")
    
    if start_ea:
        print("Setting breakpoint at 0x%x" % start_ea)
        set_breakpoint(start_ea)
    else:
        print("Warning: start_ea not set, please check offset values")
    
    if end_ea:
        for ea in end_ea:
            print("Setting breakpoint at 0x%x" % ea)
            set_breakpoint(ea)
    
    if skip_functions:
        print("skip_functions")
        for skip_function in skip_functions:
            print ("%08X" % skip_function)
    
    debughook = MyDbgHook(modules_info, skip_functions, end_ea)
    print("Debug hook initialized successfully")


if __name__ == "__main__":
    main()
    pass