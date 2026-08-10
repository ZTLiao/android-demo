function dealWithClassLoader(classLoaderObj) {
    if (!Java.available) {
        return;
    }
    Java.perform(function() {
        try {
            var DexFileClass = Java.use('dalvik.system.DexFile');
            var BaseDexClassLoaderClass = Java.use('dalvik.system.BaseDexClassLoader');
            var DexPathListClass = Java.use('dalvik.system.DexPathList');
            var ElementClass = Java.use('dalvik.system.DexPathList$Element');
            var baseDexClassLoaderObj = Java.cast(classLoaderObj, BaseDexClassLoaderClass);
            var tempObj = baseDexClassLoaderObj.dexPathList.value;
            var dexPathListObj = Java.cast(tempObj, DexPathListClass);
            console.log('dexPathListObj ->', dexPathListObj);
            var dexElementsObj = dexPathListObj.dexElements.value;
            console.log('dexElementsObj ->', dexElementsObj);
            for (var i in dexElementsObj) {
                var obj = dexElementsObj;
                var elementObj = Java.cast(obj, ElementClass);
                console.log('elementObj ->', elementObj);
                var tempObj = elementObj.dexFile.value;
                var dexFileObj = Java.cast(tempObj, DexFileClass);
                console.log('dexFileObj ->', dexFileObj);
                var enumeratorClassNames = dexFileObj.entries();
                while (enumeratorClassNames.hasMoreElements()) {
                    var className = enumeratorClassNames.nextElement().toString();
                    console.log('start load className ->', className);
                    var loadClass = classLoaderObj.loadClass(className);
                    console.log('end load className ->', className);
                    var methods = loadClass.getDeclaredConstructors();
                    for (var i in methods) {
                        dealWithMethod(className, methods[i]);
                    }
                    methods = loadClass.getDeclaredMethods();
                    for (var i in methods) {
                        dealWithMethod(className, methods[i]);
                    }
                }
            }
        } catch(e) {
            console.error(e);
        }
    });
}

function dealWithMethod(className, method) {

}

function enumerateClassLoaders() {
    Java.perform(function() {
        Java.enumerateClassLoadersSync().forEach(function(loader) {
            console.log('--------------------------\n');
            console.log(loader);
            dealWithClassLoader(loader);
            console.log('--------------------------\n');
        });
    });
}

function main() {
    enumerateClassLoaders();
}

setImmediate(main);