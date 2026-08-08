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
    var sub_13558 = base_hello_jni.add(0x13558);
    Interceptor.attach(sub_13558, {
        onEnter: function(args) {
            this.arg0 = args[0];
            this.arg1 = args[1];
            this.arg2 = args[2];
        },
        onLeave: function(retval) {
            console.log('sub_13558 : \r\n', hexdump(this.arg0), '\r\n', hexdump(this.arg1, {length: parseInt(this.arg2)})); 
        }
    });
    hook_native_addr(base_hello_jni.add(0x12D70), 0x12D70);
    hook_native_addr(base_hello_jni.add(0x13558), 0x13558);
    hook_native_addr(base_hello_jni.add(0x162B8), 0x162B8);
    hook_native_addr(base_hello_jni.add(0x130F0), 0x130F0);
    hook_native_addr(base_hello_jni.add(0x15F10), 0x15F10);
    hook_native_addr(base_hello_jni.add(0x154D4), 0x154D4);
    hook_native_addr(base_hello_jni.add(0x158AC), 0x158AC);
    Interceptor.attach(base_hello_jni.add(0x154D4), {
        onEnter: function(args) {
            this.arg0 = args[0];
            this.arg1 = args[1];
            this.arg2 = args[2];
        },
        onLeave: function(retval) {
            console.log('0x154D4 : \r\n', hexdump(this.arg0), '\r\n', hexdump(this.arg1, {length: parseInt(this.arg2)})); 
        }
    });
}

function hook_java() {
    Java.perform(function() {
        var HelloJni = Java.use('com.example.hellojni.HelloJni');
        HelloJni.sgin2.implementation = function(str, str2) {
            var result = this.sgin2('0123456789abcde', 'fedcba9876543210');
            console.log('Java sign2 result : ', result);
            return result;
        }
    });
}

function main() {
    hook_java();
    hook_native();
}

setImmediate(main);