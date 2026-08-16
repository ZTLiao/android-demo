function LOGV(content) {
    Java.perform(function() {
        var Log = Java.use('android.util.Log');
        Log.v('testRpc', content);
    });
}

function LOGE(content) {
    Java.perform(function() {
        var Log = Java.use('android.util.Log');
        Log.e('testRpc', content);
    });
}

function loadOneClass(className) {
    Java.perform(function() {
        Java.enumerateClassLoadersSync().forEach(function(loader) {
            try {
                var thisClass = loader.loadClass(className);
                console.log('find class in classLoader : ', loader);
                var DexFile = Java.use('dalvik.system.DexFile');
                var DexFileClass = DexFile.class;
                var methods = DexFileClass.getDeclaredMethods();
                var dumpMethodCode = null;
                methods.forEach(function(method) {
                    if (method.getName() == 'dumpMethodCode') {
                        dumpMethodCode = method;
                        dumpMethodCode.setAccessiable(true);
                    }
                });
                console.log(dumpMethodCode);
                var ActivityThread = Java.use('android.app.ActivityThread');
                ActivityThread.loadClassAndInvoke(loader, className, dumpMethodCode);
            } catch (e) {

            }
        });
    });
}

rpc.exports = {
    logv(content) {
        LOGV(content);
    },
    loge(content) {
        LOGE(content);
    },
    loadClass(className) {
        loadOneClass(className);
    }
};