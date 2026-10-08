function tracesoload() {
    console.log(Process.arch);
    var libnativeloadermodule = Process.getModuleByName('libnativeloader.so');
    var OpenNativeLibraryaddr = libnativeloadermodule.getExportByName('OpenNativeLibrary');
    Interceptor.attach(OpenNativeLibraryaddr, {
        onEnter: function(args) {
            this.sopath = ptr(args[2]).readUtf8String();
            console.log('go into OpenNativeLibrary->' + this.sopath);
        },
        onLeave: function(retval) {
            console.log('leave OpenNativeLibrary->' + this.sopath + ', return : ' + retval);
        }
    });
    
    var libcmodule = Process.getModuleByName('libc.so');
    var dlsymaddr = libcmodule.getExportByName('dlsym');
    Interceptor.attach(dlsymaddr, {
        onEnter: function(args) {
            this.handle = args[0];
            this.symbol = ptr(args[1]).readUtf8String();
            console.log('go into dlsym : ' + this.handle + '---' + this.symbol);
        },
        onLeave: function(retval) {
            console.log('leave dlsym : ' + this.handle + '---' + this.symbol + ', retval : ' + retval);
        }
    });
}

function main() {
    tracesoload();
}

setImmediate(main);