function enumerateLoaderClasses() {
    Java.perform(function() {
        Java.enumerateClassLoadersSync().forEach(function(loader) {
            console.log(loader);
        });
    });
}

function hook() {
    Java.perform(function() {
        var className = '';
        Java.enumerateClassLoadersSync().forEach(function(loader) {
            try {
                console.log(loader);
                var thisClass = loader.findClass(className);
            } catch(e) {
                console.log(e); 
            }
        });
    });
}

function main() {
    enumerateLoaderClasses();
}

setImmediate(main);