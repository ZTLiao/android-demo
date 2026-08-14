function hook_RegisterNatives() {
    var module_libart = Process.findModuleName('libart.so');
    console.log(module_libart);
    var addr_RegisterNatives = null;
    var symbols = module_libart.enumerateSymbols();
    for(var i = 0; i < symbols.length; i++) {
        var name = symbols[i].name;
        if(name.indexOf('CheckJNI') != -1 && name.indexOf('JNI') != -1 && name.indexOf('RegisterNatives') != -1) {
            console.log(name);
            addr_RegisterNatives = symbols[i].address;
        }
    }

    if (addr_RegisterNatives) {
        Interceptor.attach(addr_RegisterNatives, {
            onEnter: function(args) {
                var java_class = Java.vm.tryGetEnv().getClassName(args[1]);
                var methods = args[2];
                var method_count = parseInt(args[3]);
                console.log('[RegisterNatives] args : ', args[0], ', className : ', java_class, ', fnPtr : ', methods, ', method num : ', method_count);
                for (var i = 0; i < method_count; i++) {
                    console.log(methods.add(i * Process.pointerSize * 3).readPointer().readCString());
                    console.log(methods.add(i * Process.pointerSize * 3 + Process.pointerSize).readPointer().readCString());
                    var fnPtr = methods.add(i * Process.pointerSize * 3 + Process.pointerSize * 2).readPointer();
                    var module_so = Module.findModuleByAddress(fnPtr);
                    console.log('[RegisterNatives] ', module_so.name + '!' + fnPtr.sub(module_so.base));
                }
            },
            onLeave: function(retval) {
                console.log(retval);
            }
        });
    }
}

setImmediate(hook_RegisterNatives);