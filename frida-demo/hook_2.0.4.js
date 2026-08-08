function hex_dump(p) {
    try {
        return hexdump(p) + '\r\n';
    } catch(error) {
        return ptr(p) + '\r\n';
    }
}

function hook_native_addr(addr, idb_addr) {
    var base_hello_jni = Module.findBaseAddress('libhello-jni.so');
    Interceptor.attach(addr, {
        onEnter: function(args) {
            this.arg0 = args[0];
            this.arg1 = args[1];
            this.arg2 = args[2];
            this.arg3 = args[3];
            this.lr = this.context.lr;
        },
        onLeave: function(retval) {
            console.log('sub_', ptr(addr), ', idb_addr : ' + ptr(idb_addr) + 'LR : ' + ptr(this.lr).sub(base_hello_jni) + '\r\n', 
            'this.arg0 : \r\n', hex_dump(this.arg0), 
            'this.arg1 : \r\n', hex_dump(this.arg1),
            'this.arg2 : \r\n', hex_dump(this.arg2),
            'this.arg3 : \r\n', hex_dump(this.arg3),
            'retval : \r\n', hex_dump(retval)
        ); 
        }
    });
}

function hook_native() {
    var base_hello_jni = Module.findBaseAddress('libhello-jni.so');
    var addr_0xF624 = base_hello_jni.add(0xF624);
    Interceptor.attach(addr_0xF624, {
        onEnter: function(args) {
            console.log('X8 : ', this.context.x8, ', X23 : ', this.context.x23);
        }
    });

    var memcpy = Module.findExportByName(null, 'memcpy');
    Interceptor.attach(memcpy, {
        onEnter: function(args) {
            var lr = ptr(this.context.lr);
            var module = Process.findModuleByAddress(lr);
            console.log(module, module.base);
            if (module.name == 'libhello-jni.so') {
                console.log('X8 : ', this.context.x8, ', X23 : ', this.context.x23, ', LR : ', lr, ptr(this.context.lr).sub(base_hello_jni), JSON.stringify(this.context));
            } 
        }
    });
    hook_native_addr(base_hello_jni.add(0x1DFB4), 0x1DFB4);
    hook_native_addr(base_hello_jni.add(0x1AB4C), 0x1AB4C);
    hook_native_addr(base_hello_jni.add(0x171C4), 0x171C4);
    hook_native_addr(base_hello_jni.add(0x18490), 0x18490);
}

function main() {
    hook_native();
}

setImmediate(main);