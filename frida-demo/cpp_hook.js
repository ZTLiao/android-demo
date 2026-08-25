function activeCallTemplate() {
    var libTarget = Process.getModuleByName('libnative-lib.so');
    var targetFunAddr = null;
    libTarget.enumerateExports().forEach(function(symbol) {
        console.log(JSON.stringify(symbol));
        if (symbol.name == '_Z3addIiET_S0_S0_') {
            targetFunAddr = symbol.address;
        }
        if (symbol.name.indexOf('add') != -1) {

        }
    });
    var targetFunc = new NativeFunction(targetFunAddr, 'int', ['int', 'int']);
    var result = targetFunc(4, 5);
    console.log('result -> ', result);
}

function hookCallTemplate() {
    var libTarget = Process.getModuleByName('libnative-lib.so');
    var targetFunAddr = null;
    libTarget.enumerateExports().forEach(function(symbol) {
        console.log(JSON.stringify(symbol));
        if (symbol.name == '_Z3addIiET_S0_S0_') {
            targetFunAddr = symbol.address;
        }
    });
    Interceptor.attach(targetFunAddr, {
        onEnter: function(args) {
            this.arg0 = args[0];
            this.arg1 = args[1];
            args[0] = ptr(0x20);
            args[1] = ptr(0x10);
            console.log('add<int>(' + this.arg0 + ', ' + this.arg1 + ') is called');
        }, 
        onLeave: function(retVal) {
            console.log('add<int>(' + this.arg0 + ', ' + this.arg1 + ') is called, result : ', retVal);
            retVal.replace(0x50);
        }
    });
}

function hookTemplateClassFunc() {
    var libTarget = Process.getModuleByName('libnative-lib.so');
    var targetFunAddr = null;
    libTarget.enumerateExports().forEach(function(symbol) {
        console.log(JSON.stringify(symbol));
        if (symbol.name == '_ZN7ComputeIiE3addEv') {
            targetFunAddr = symbol.address;
        }
    });
    Interceptor.attach(targetFunAddr, {
        onEnter: function(args) {
            this.arg0 = args[0];
            ptr(this.arg0).writeInt(0x20);
            ptr(this.arg0).add(4).writeInt(0x50);
        }, 
        onLeave: function(retVal) {
            console.log('compute.add() is called, result : ', retVal);
        }
    });
}

function hookTemplateClassFunc1() {
    var libTarget = Process.getModuleByName('libnative-lib.so');
    libTarget.enumerateExports().forEach(function(symbol) {
        console.log(JSON.stringify(symbol));
        if (symbol.name.indexOf('Compute') != -1 && symbol.name.indexOf('add') != -1) {
            Interceptor.attach(symbol.address, {
                onEnter: function(args) {
                    this.arg0 = args[0];
                    console.log('this->' + this.arg0);
                }, 
                onLeave: function(retVal) {
                    console.log('result : ', retVal);
                }
            });
        }
    });
}

function hookExecuteSwitchImplFunc() {
    var libTarget = Process.getModuleByName('libnative-lib.so');
    libTarget.enumerateExports().forEach(function(symbol) {
        console.log(JSON.stringify(symbol));
        if (symbol.name.indexOf('ExecuteSwitchImpl') != -1) {
            Interceptor.attach(symbol.address, {
                onEnter: function(args) {
                    this.arg0 = args[0];
                    console.log(symbol.name + ' is called!');
                    console.log('this->' + this.arg0);
                }, 
                onLeave: function(retVal) {
                    console.log('result : ', retVal);
                }
            });
        }
    });
}

function main() {
    activeCallTemplate();
    hookCallTemplate();
    hookTemplateClassFunc();
    hookTemplateClassFunc1();
    hookExecuteSwitchImplFunc();
}

setImmediate(main);