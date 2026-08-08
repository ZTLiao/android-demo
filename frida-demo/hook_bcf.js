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
    hook_native_addr(base_hello_jni.add(0x12B44), 0x12B44);
    hook_native_addr(base_hello_jni.add(0x12BF8), 0x12BF8);
    hook_native_addr(base_hello_jni.add(0x1391C), 0x1391C);
    hook_native_addr(base_hello_jni.add(0x18AB0), 0x18AB0);
    hook_native_addr(base_hello_jni.add(0x12CF4), 0x12CF4);
    hook_native_addr(base_hello_jni.add(0x16900), 0x16900);
    hook_native_addr(base_hello_jni.add(0x16214), 0x16214);
}

function main() {
    hook_native();
}

setImmediate(main);