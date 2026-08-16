function enumerateClassLoader() {
    Java.perform(function() {
        Java.enumerateClassLoadersSync().forEach(function(loader) {
            if (loader.toString().indexOf('DexClassLoader') != -1) {
                console.log(loader);
                var ActivityThread = Java.use('android.app.ActivityThread');
                ActivityThread.fartwithClassloader(loader);
            }
        });
    });
}

function hookClassLoader() {
    Java.perform(function() {
        var DexClassLoader = Java.use('dalvik.system.DexClassLoader');
        DexClassLoader.$init.implementation = function(arg0, arg1, arg2, arg3) {
            console.log('DexClassLoader.$init is called! arg0 : ', arg0);
            var result = this.$init(arg0, arg1, arg2, arg3);
            console.log('thisClassLoader : ' + this);
            console.log('DexClassLoader.$init is called over!', arg0);
            return result;
        }
    });
}

function loadClass() {
    Java.perform(function() {
        var ActivityThread = Java.use('android.app.ActivityThread');
        ActivityThread.loadClassAndInvoke.implementation = function(arg0, arg1, arg2) {
            console.log('start load : ' + arg1);
            if (arg1.indexOf('androidx.') == -1 && arg1.indexOf('android.support.') == -1) {
                var result = this.loadClassAndInvoke(arg0, arg1, arg2);
                console.log('start load : ', arg1);
            } else {
                console.log('ignored load : ', arg1);
            }
            return result;
        }
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

function fartThread() {
    Java.perform(function() {
        var ActivityThread = Java.use('android.app.ActivityThread');
        console.log(ActivityThread);
        ActivityThread.fartThread();
    });
}

function main() {
    enumerateClassLoader();
    hookClassLoader();
    loadClass();
    loadOneClass('com.kanxue.test02.TestClass');
    fartThread();
}

setImmediate(main);