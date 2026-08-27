function hooklibart() {
    var libartmodule = Process.getModuleByName('libart.so');
    var PrettyFilldaddr = null;
    var ResolveTypeAddr = null;
    var LoadMethodAddr = null;
    libartmodule.enumerateSymbols().forEach(function(symbol) {
        if (symbol.name == '_ZN3art11PrettyFieldEjRKNS_7DexFileEb') {
            console.log(JSON.stringify(symbol));
            PrettyFilldaddr = symbol.address;
        } else if (symbol.name == '_ZN3art11ClassLinker11ResolveTypeERKNS_7DexFileEtNS_6HandleINS_6mirroor8DexCacheEEENS4_INS5_11ClassLoaderEEE') {
            ResolveTypeAddr = symbol.address;
        } else if (symbol.name.indexOf('ClassLinker') >= 0
                && symbol.name.indexOf('LoadMethod') >= 0
                && symbol.name.indexOf('DexFile') >= 0
                && symbol.name.indexOf('ClassDataItemInterceptor') >= 0
                && symbol.name.indexOf('ArtMethod') >= 0) {
            LoadMethodAddr = symbol.address;
        } 
    });
    if (PrettyFilldaddr != null) {
        console.log('start hook PrettyFilldaddr...');
        Interceptor.attach(PrettyFilldaddr, {
            onEnter: function(args) {
                var dexfileptr = args[2];
                console.log('go into _ZN3art11PrettyFieldEjRKNS_7DexFileEb -> ' + hexdump(dexfileptr, {
                    length: 16
                }));
            },
            onLeave: function(retval) {

            }
        });
    }
    if (ResolveTypeAddr != null) {
        Interceptor.attach(ResolveTypeAddr, {
            onEnter: function(args) {
                var dexfileptr = args[1];
                var dexfilebegin = ptr(dexfileptr).add(Process.pointerSize * 1).readPointer();
                var dexfilesize = ptr(dexfileptr).add(Process.pointerSize * 2).readU32();
                console.log('dexfilebegin : ', dexfilebegin, ', dexfilesize : ', dexfilesize, '-----', hexdump(dexfilebegin, {
                    length: 16
                }));
                console.log('go into ResolveTypeAddr -> ' + hexdump(dexfileptr, {
                    length: 32
                }));
            },
            onLeave: function(retval) {

            }
        });
    }
    if (LoadMethodAddr != null) {
        Interceptor.attach(LoadMethodAddr, {
            onEnter: function(args) {
                var dexfileptr = args[2];
                this.artmethodStr = args[5];
                var dexfilebegin = ptr(dexfileptr).add(Process.pointerSize * 1).readPointer();
                var dexfilesize = ptr(dexfileptr).add(Process.pointerSize * 2).readU32();
                this.dexfilebegin = dexfilebegin;
                this.dexfilesize = dexfilesize;
                console.log('LoadMethodAddr dexfilebegin : ', dexfilebegin, ', dexfilesize : ', dexfilesize, '-----', hexdump(dexfilebegin, {
                    length: 16
                }));
                console.log('go into LoadMethodAddr -> ' + hexdump(dexfileptr, {
                    length: 32
                }));
            },
            onLeave: function(retval) {
                var code_item_off = ptr(this.artmethodStr).add(8).readreadU32ointer();
                var method_idx = ptr(this.artmethodStr).add(12).readreadU32ointer();
                console.log(this.dexfilesize + 'LoadMethodAddr -> ' + method_idx + '--' + code_item_off);
                console.log(this.dexfilebegin.add(code_item_off));
            }
        });
    }
}

function main() {
    console.log(Process.arch);
    hooklibart();
}

setImmediate(main);