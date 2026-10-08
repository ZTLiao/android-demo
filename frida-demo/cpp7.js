function init() {
    console.log("go into init," + "Process.arch:" + Process.arch);
    var module_libext = null;
    if (Process.arch === "arm64") {
        module_libext = Module.load("/data/app/fart64.so");
    } else if (Process.arch === "arm") {
        module_libext = Module.load("/data/app/fart.so");
    }
    if (module_libext != null) {
        addrGetDexFile = module_libext.findExportByName("GetDexFile");
        funcGetDexFile = new NativeFunction(addrGetDexFile, "pointer", ["pointer", "pointer"]);
        addrGetCodeItemLength = module_libext.findExportByName("GetCodeItemLength");
        funcGetCodeItemLength = new NativeFunction(addrGetCodeItemLength, "int", ["pointer"]);
        addrBase64_encode = module_libext.findExportByName("Base64_encode");
        funcBase64_encode = new NativeFunction(addrBase64_encode, "pointer", ["pointer", "int", "pointer"]);
        addrFreeptr = module_libext.findExportByName("Freeptr");
        funcFreeptr = new NativeFunction(addrFreeptr, "void", ["pointer"]);
    }
    var symbols = Module.enumerateSymbolsSync("libart.so");
    for (var i = 0; i < symbols.length; i++) {
        var symbol = symbols[i];
        if (symbol.name.indexOf("ArtMethod") >= 0 && symbol.name.indexOf("GetObsoleteDexCache") >= 0) {
            addrGetObsoleteDexCache = symbol.address;
            break;
        }
    }
}


function getDexFileByArtMethod() {
    Java.perform(function() {
        var getDexFileByArtMethodAddr = null;
        var module_libext = null;
        if (Process.arch === "arm64") {
            module_libext = Module.load("/data/app/fart64.so");
            getDexFileByArtMethodAddr = module_libext.getExportByName('getDexFileByArtMethod64');
        } else if (Process.arch === "arm") {
            module_libext = Module.load("/data/app/fart32.so");
            getDexFileByArtMethodAddr = module_libext.getExportByName('getDexFileByArtMethod32');
        }
        
        if (module_libext != null) {
            module_libext.enumerateExports().forEach(function(symbol) {
                console.log(JSON.stringify(symbol));
                if (symbol.name.indexOf('getDexFileByArtMethod32') >= 0) {
                    console.log(symbol);
                }
            });
        }
        var getDexFileByArtMethodFunc = null;
        if (getDexFileByArtMethodAddr != null) {
            getDexFileByArtMethodFunc = new NativeFunction(getDexFileByArtMethodAddr, 'pointer', ['pointer', 'pointer']);
        }
        var GetObsoleteDexCacheAddr = null;
        var libartmodule = Process.getModuleByName('libart.so');
        libartmodule.enumerateSymbols().forEach(function(symbol) {
            if (symbol.name.indexOf('GetObsoleteDexCache') >= 0) {
                console.log(JSON.stringify(symbol));
                GetObsoleteDexCacheAddr = symbol.address;
            }
        });
        var MainActivity = Java.use('com.kanxue.cpp7.MainActivity');
        var methods = MainActivity.class.getDeclaredMethods();
        methods.forEach(function(method) {
            console.log(method.toString());
            var methodhandle = method.$handle;
            var ArtMethodptr = Java.vm.tryGetEnv().FromReflectedMethod(methodhandle);
            if (getDexFileByArtMethod32Func != null) {
                var dexfileptr = getDexFileByArtMethodFunc(ArtMethodptr, GetObsoleteDexCacheAddr);
                var dexfilebegin = ptr(dexfileptr).add(Process.pointerSize * 1).readPointer();
                var dexfilesize = ptr(dexfileptr).add(Process.pointerSize * 2).readPointer();
                console.log(method.toString() + '----' + ArtMethodptr + '----' + dexfileptr + '----' + dexfilebegin + '----' + dexfilesize + '----' + hexdump(dexfilebegin, {
                    length: 16
                }));
            }
        });
    });
}

function main() {
    getDexFileByArtMethod();
}

setImmediate(main);