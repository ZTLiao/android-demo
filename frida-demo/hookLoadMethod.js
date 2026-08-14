var saveDex = {};

function hookLoadMethod() {
    console.log(Process.arch);
    var libArtModule = Process.getModuleByName('libart.so');
    libArtModule.enumerateSymbols().forEach(function(symbol) {
        console.log(symbol.name);
        if (symbol.name.indexOf('LoadMethod') !== -1 && symbol.name.indexOf('ClassDataItemIterator') !== -1) {
            console.log('LoadMethod found');
            console.log(JSON.stringify(symbol));
            Interceptor.attach(symbol.address, {
                onEnter: function(args) {
                    console.log('LoadMethod enter');
                    this.dexFilePtr = args[1];
                    console.log(dexFile);
                    var dexFileBegin = Memory.readPointer(ptr(this.dexFilePtr).add(Process.pointerSize * 1));
                    var dexFileSize = Memory.readU32(ptr(this.dexFilePtr).add(Process.pointerSize * 2));
                    console.log(hexdump(dexFileBegin, {length: 16}));
                    if (saveDex[dexFileSize] === undefined) {
                        console.log('got a dex file : ', dexFileSize);
                        saveDex[dexFileSize] = dexFileBegin;
                    }
                },
                onLeave: function(retval) {
                    console.log('LoadMethod leave');
                }
            });
        }
    });
}

function main() {
    hookLoadMethod();
}

setImmediate(main);