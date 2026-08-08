function hook_libart() {
    var module_libart = Process.findModuleName('libart.so');
    console.log(module_libart);
    var addr_RegisterNatives = null;
    var addr_GetStringUTFChars = null;
    var addr_NewStringUTF = null;
    var addr_FindClass = null;
    var symbols = module_libart.enumerateSymbols();
    for(var i = 0; i < symbols.length; i++) {
        var name = symbols[i].name;
        if(name.indexOf('CheckJNI') != -1 && name.indexOf('JNI') != -1 && name.indexOf('RegisterNatives') != -1) {
            console.log(name);
            addr_RegisterNatives = symbols[i].address;
        } else if (name.indexOf('GetStringUTFChars') != -1) {
            console.log(name);
            addr_GetStringUTFChars = symbols[i].address;
        } else if (name.indexOf('NewStringUTF') != -1) {
            addr_NewStringUTF = symbols[i].address;
        } else if (name.indexOf('FindClass') != -1) {
            addr_FindClass = symbols[i].address;
        }
    }

    if (addr_RegisterNatives) {
        Interceptor.attach(addr_RegisterNatives, {
            onEnter: function(args) {
                var java_class = Java.vm.tryGetEnv().getClassName(args[1]);
                var methods = args[2];
                var method_count = parseInt(args[3]);
                console.log('addr_RegisterNatives args : ', args[0], java_class, methods, method_count);
                for (var i = 0; i < method_count; i++) {
                    console.log(methods.add(i * Process.pointerSize * 3).readPointer().readCString());
                    console.log(methods.add(i * Process.pointerSize * 3 + Process.pointerSize).readPointer().readCString());
                    var fnPtr = methods.add(i * Process.pointerSize * 3 + Process.pointerSize * 2).readPointer();
                    var module_so = Module.findModuleByAddress(fnPtr);
                    console.log(module_so.name + '!' + fnPtr.sub(module_so.base));
                }
            },
            onLeave: function(retval) {
                console.log(retval);
            }
        });
    }

    if (addr_GetStringUTFChars) {
        Interceptor.attach(addr_GetStringUTFChars, {
            onEnter: function(args) {
                
            },
            onLeave: function(retval) {
                console.log('[GetStringUTFChars] : ', ptr(retval).readCString());
            }
        });
    }

    if (addr_NewStringUTF) {
        Interceptor.attach(addr_NewStringUTF, {
            onEnter: function(args) {

            },
            onLeave: function(retval) {
                console.log('[NewStringUTF] : ', ptr(retval).readCString());
            }
        });
    }

    if (addr_FindClass) {
        Interceptor.attach(addr_FindClass, {
            onEnter: function(args) {
                console.log('[FindClass] args : ', ptr(args[1]).readCString());
            },
            onLeave: function(retval) {
                console.log('[FindClass] retval : ', ptr(retval).readCString());
            }
        });
    }
}

function print_hex(addr) { 
    var base_hello_jni = Module.findBaseAddress('libhello-jni.so');
    console.log(hexdump(base_hello_jni.add(addr)));
}

function hook_native() {
    var base_hello_jni = Module.findBaseAddress('libhello-jni.so');
    if (base_hello_jni == null) {
        return;
    }
    console.log(base_hello_jni);
    Interceptor.attach(base_hello_jni.add(0x81D0), {
        onEnter: function(args) {
            this.arg0 = args[0];
            this.arg1 = args[1];
        },
        onLeave: function(retval) {
            console.log('0x81D0 : \r\n', hexdump(this.arg0), '\r\n', hexdump(this.arg1));
        }
    });
    Interceptor.attach(base_hello_jni.add(0x809C), {
        onEnter: function(args) {
            this.arg0 = args[0];
            this.arg1 = args[1];
            this.arg2 = args[2];
        },
        onLeave: function(retval) {
            console.log('0x809C : \r\n', hexdump(this.arg0), '\r\n', hexdump(this.arg1, {length: parseInt(this.arg2)}));  
            console.log('0x809C : ', ptr(this.arg1).readCString());
        }
    });

    Interceptor.attach(base_hello_jni.add(0x731C), {
        onEnter: function(args) {
            console.log('0x731C : ', this.context.x13, this.context.x14);
        },
        onLeave: function(retval) {
        }
    });
}

function hook_android_dlopen_ext() {
    Interceptor.attach(Module.findExportByName('linker', '__android_dlopen_ext'), {
        onEnter: function(args) {
            this.name = args[0].readCString();
            console.log('android_dlopen_ext : ', this.name);
        },
        onLeave: function(retval) {
            if(this.name.indexOf('libhello-jni.so') != -1) {
                hook_native();
            }

        }
    });

}


function main() {
    hook_libart();
    hook_android_dlopen_ext();
    hook_native();
}


setImmediate(main);