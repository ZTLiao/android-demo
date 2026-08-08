function main() {
    hook_libart();
    hook_java();
    hook_native();
    hook_libc();
    print_hex(0x37070);
}

function hook_java() {
    Java.perform(function() {
        var HelloJni = Java.use('com.example.hellojni.HelloJni');
        HelloJni.sign1.implementation = function(args) {
            var result = this.sign1('0123456789abcdef');
            return result;
        }
    });
}

function print_hex(addr) { 
    var base_hello_jni = Module.findBaseAddress('libhello-jni.so');
    console.log(hexdump(base_hello_jni.add(addr)));
}

function hook_native() {
    var base_hello_jni = Module.findBaseAddress('libhello-jni.so');
    Interceptor.attach(base_hello_jni.add(0xFD90), {
        onEnter: function(args) {
            this.arg0 = args[0];
            this.arg1 = args[1];
            console.log();
        },
        onLeave: function(retval) {
            console.log('0xFD90 : \r\n', hexdump(this.arg0), '\r\n', hexdump(this.arg1));  
        }
    });
    Interceptor.attach(base_hello_jni.add(0xF008), {
        onEnter: function(args) {
            this.arg0 = args[0];
            this.arg1 = args[1];
            console.log();
        },
        onLeave: function(retval) {
            console.log('0xF008 : \r\n', hexdump(this.arg0), '\r\n', hexdump(this.arg1));  
            console.log('0xF008 : ', ptr(this.arg1).readCString());
        }
    });
}

function hook_libc() {
    Interceptor.attach(Module.findBaseAddress('libc.so', 'lrand48'), {
        onEnter: function(args) {

        },
        onLeave: function(retval) {
           console.log('lrand48 : ', retval);
           retval.replace(0xAAAAAA); 
           console.log('lrand48 : ', retval);
        }
    });
}

function hook_libart() {
    var module_libart = Process.findModuleName('libart.so');
    console.log(module_libart);
    var addr_RegisterNatives = null;
    var addr_GetStringUTFChars = null;
    var addr_NewStringUTF = null;
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
}

setImmediate(main);