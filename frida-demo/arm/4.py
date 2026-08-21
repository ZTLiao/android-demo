import keystone
import capstone

# pip3 install keystone-engine
# pip3 install capstone

def thumb2bin(arm_code):
    ks = keystone.Ks(keystone.KS_ARCH_ARM, keystone.KS_MODE_THUMB)
    asm, count = ks.asm(arm_code, as_bytes=True)  # 解包元组
    if asm is None:
        return None
    opcode = int.from_bytes(asm, "little")  # asm 已经是 bytes
    bin_code = format(opcode, "016b")
    print(arm_code, hex(opcode), "\r\n", bin_code)
    return asm  # 返回 bytes

def bin2thumb(asm):
    cs = capstone.Cs(capstone.CS_ARCH_ARM, capstone.CS_MODE_THUMB)
    if asm is None:
        print("汇编失败，无法反汇编")
        return
    for insn in cs.disasm(asm, 0):  # asm 是 bytes，可以直接用
        print(insn.address, insn.mnemonic, insn.op_str)

arm_code = "IT EQ"

bin2thumb(thumb2bin(arm_code))