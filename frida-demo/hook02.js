function hook_java() {
    Java.perform(function() {
        var MyApp = Java.use('com.gdufs.xman.MyApp');
        MyApp.saveSN.implementation = function(sn) {
            console.log('MyApp.saveSN : ', sn);
            this.saveSN(sn);
        }
        var Process = Java.use('android.os.Process');
        Process.killProcess.implementation = function(pid) {
            console.log('Process.killProcess pid : ', pid);
        }
    });
}

function hook_native() {
    var base_libmyjni = Module.findBaseAddress('libmyjni.so');

    Interceptor.attach(Module.findExportByName('libmyjni.so', 'n2'), {
        onEnter: function(args) {
            console.log('n2 :', args[3]);
        },
        onLeave: function(retval) {

        }
    });

    console.log('setValue : ', Module.findExportByName('libmyjni.so', 'setValue'));

    Interceptor.attach(Module.findExportByName('libmyjni.so', 'setValue'), {
        onEnter: function(args) {
            console.log('setValue :', args[1]);
            console.log('setValue called from: \n' + Thread.backtrace(this.context, Backtracer.ACCURATE)
                .map(DebugSymbol.fromAddress).join('\n') + '\n');
        },
        onLeave: function(retval) {

        }
    });
}

function hook_libart() {
    var module_libart = Process.findModuleName('libart.so');
    console.log(module_libart);
    var add_RegisterNatives = null;
    var symbols = module_libart.enumerateSymbols();
    for(var i = 0; i < symbols.length; i++) {
        var name = symbols[i].name;
        if(name.indexOf('CheckJNI') != -1 && name.indexOf('JNI') != -1 && name.indexOf('RegisterNatives') != -1) {
            console.log(name);
            add_RegisterNatives = symbols[i].address;
        }
    }

    if (add_RegisterNatives) {
        Interceptor.attach(add_RegisterNatives, {
            onEnter: function(args) {
                var java_class = Java.vm.tryGetEnv().getClassName(args[1]);
                var methods = args[2];
                var method_count = parseInt(args[3]);
                console.log('add_RegisterNatives args : ', args[0], java_class, methods, method_count);
                for (var i = 0; i < method_count; i++) {
                    console.log(methods.add(i * Process.pointerSize * 3).readPointer().readCString());
                    console.log(methods.add(i * Process.pointerSize * 3 + Process.pointerSize).readPointer().readCString());
                    var fnPtr = methods.add(i * Process.pointerSize * 3 + Process.pointerSize * 2).readPointer();
                    console.log(fnPtr);
                }
            },
            onLeave: function(retval) {
                console.log(retval);
            }
        });
    }
}

function hook_android_dlopen_ext() {
    Interceptor.attach(Module.findExportByName('linker', '__android_dlopen_ext'), {
        onEnter: function(args) {
            this.name = args[0].readCString();
            console.log('android_dlopen_ext : ', this.name);
        },
        onLeave: function(retval) {
            if(this.name.indexOf('libmyjni.so') != -1) {
                hook_native();
            }

        }
    });

}

function frida_file() {
    var file = new File('/sdcard/reg.bat', 'r+');
    file.werite('EoPaoY62@ElRD');
    file.flush();
    file.close();
}

function c_read_file() {
    var fopen = new NativeFunction(Module.findExportByName('libc.so', 'fopen'), 'pointer', ['pointer', 'pointer']);
    var fseek = new NativeFunction(Module.findExportByName('libc.so', 'fseek'), 'int', ['pointer', 'int', 'int']);
    var ftell = new NativeFunction(Module.findExportByName('libc.so', 'ftell'), 'long', ['pointer']);
    var fread = new NativeFunction(Module.findExportByName('libc.so', 'fread'), 'int', ['pointer', 'int', 'int', 'pointer']);
    var fclose = new NativeFunction(Module.findExportByName('libc.so', 'fclose'), 'int', ['pointer']);
    var file = fopen(Memory.allocUtf8String('/sdcard/reg.bat'), Memory.allocUtf8String('r+'));
    fseek(file, 0, 2);
    var size = ftell(file);
    var buffer = Memory.alloc(size + 1);
    fseek(file, 0, 0);
    fread(buffer, size, 1, file);
    console.log('buffer : ', buffer, buffer.readCString(), hexdump(buffer));
    fclose(file);
}

function main() {
    hook_java();
    hook_libart();
    hook_android_dlopen_ext();
    frida_file();
}

setImmediate(main);
function hook_libart() {
    var module_libart = Process.findModuleName('libart.so');
    console.log(module_libart);
    var add_RegisterNatives = null;
    var addr;
    var symbols = module_libart.enumerateSymbols();
    for (var i = 0; i < symbols.length; i++) {
        var name = symbols[i].name;
        if (name.indexOf('CheckJNI') != -1 && name.indexOf('JNI') != -1 && name.indexOf('RegisterNatives') != -1) {
            console.log(name);
            add_RegisterNatives = symbols[i].address;
        } else if (name.indexOf('GetStringUTFChars') != -1) {
            console.log(name);
        } else if (name.indexOf('NewStringUTF') != -1) {
        }
    }

    if (add_RegisterNatives) {
        Interceptor.attach(add_RegisterNatives, {
            onEnter: function (args) {
                var java_class = Java.vm.tryGetEnv().getClassName(args[1]);
                var methods = args[2];
                var method_count = parseInt(args[3]);
                console.log('add_RegisterNatives args : ', args[0], java_class, methods, method_count);
                for (var i = 0; i < method_count; i++) {
                    console.log(methods.add(i * Process.pointerSize * 3).readPointer().readCString());
                    console.log(methods.add(i * Process.pointerSize * 3 + Process.pointerSize).readPointer().readCString());
                    var fnPtr = methods.add(i * Process.pointerSize * 3 + Process.pointerSize * 2).readPointer();
                    console.log(fnPtr);
                    var module_so = Module.findModuleByAddress(fnPtr);
                    console.log(module_so);
                    console.log(module_so.name, fnPtr.sub(module_so.base));
                }
            },
            onLeave: function (retval) {
                console.log(retval);
            }
        });
    }
}
