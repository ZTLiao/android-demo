function hook_MainActivity() {
    Java.perform(function() {
        var MainActivity = Java.use('com.example.androiddemo.MainActivity');
        var System = Java.use('java.lang.System');
        System.getenv.overload('java.lang.String').implementation = function(name) {
            var env = this.getenv(name);
            if (name == 'USER') {
                return 'Imyang';
            }
            console.log('getenv : ', name, env);
            return env;
        } 

        var FridaActivity7 = Java.use('com.github.lastingyang.androiddemo.Activity.FridaActivity7');
        FridaActivity7.$init.implementation = function() {
            this.$init();
            this.next.value = true;
        }
    });
}

function call_startActivity() {
    Java.perform(function() {
        var ActivityThread = Java.use('android.app.ActivityThread');
        var application = ActivityThread.currentApplication();
        var context = application.getApplicationContext();
        var FridaActivity7 = Java.use('com.github.lastingyang.androiddemo.Activity.FridaActivity7');
        var Intent = Java.use('android.content.Intent');
        Java.scheduleOnMainThread(function() {
            var intent = Intent.$new(context, FridaActivity7.$new().getClass());
            intent.setFlags(0x10000000);
            console.log('intent : ', intent);
            context.startActivity(intent);
        });
    });
}

function load_dex() {
    var DecodeUtilsDex = Java.openClassFile('/data/local/tmp/DecodeUtils.dex');
    console.log('DecodeUtilsDex : ', DecodeUtilsDex);
    Java.perform(function() {
        DecodeUtilsDex.load();
        var DecodeUtils = Java.use('com.example.hook_demo.DecodeUtils');
        console.log('DecodeUtils : ', DecodeUtils);
        var FridaActivity8 = Java.use('com.github.lastingyang.androiddemo.Activity.FridaActivity8');
        Java.scheduleOnMainThread(function() {
            console.log(DecodeUtils.$new().decode(FridaActivity8.$new().password.value));
        });
    });
}

function hook_FridaActivity8() {
    Java.perform(function() {
        var FridaActivity8 = Java.use('com.github.lastingyang.androiddemo.Activity.FridaActivity8');
        FridaActivity8.a.implementation = function(str) {
            str = "go to next check!";
            var result = this.a(str);
            console.log(str, result);
            return result;
        }
    });
}

function hook_FridaActivity9() {
    Java.perform(function() {
        var Frida9Interface = Java.use('com.github.lastingyang.androiddemo.Activity.FridaActivity9$Frida9Interface');
        console.log(Frida9Interface);
        var Frida9InterfaceImpl = Java.registerClass({
            name : 'com.github.lastingyang.androiddemo.Activity.FridaActivity9.Frida9InterfaceImpl',
            implements : [Frida9Interface],
            methods: {
                check() {
                    console.log('Frida9InterfaceImpl.check');
                    return true;
                }
            },
        });

        var FridaActivity9 = Java.use('com.github.lastingyang.androiddemo.Activity.FridaActivity9');
        FridaActivity9.getInstance.implementation = function() {
            console.log('FridaActivity9.getInstance');
            return Frida9InterfaceImpl.$new();
        }
    });
}

function printStackStrace() {
    Java.perform(function() {
        var Exception = Java.use('java.lang.Exception');
        var exception = Exception.$new();
        console.log(exception.getStackTrace());
        var stackTrace = exception.getStackTrace().toString();
        console.log('====================\r\n');
        console.log(stackTrace.replaceAll(',', '\r\n'));
        console.log('====================\r\n');
        exception.$dispose();
    });
}

function hook_FridaActivity10() {
    Java.perform(function() {
        var FridaActivity10 = Java.use('com.github.lastingyang.androiddemo.Activity.FridaActivity10');
        FridaActivity10.onCheck.implementation = function() {
            printStackStrace();
            console.log('FridaActivity10.onCheck');
        }
        var Exception = Java.use('java.lang.Exception');
        var StackTraceElement = Java.use('java.lang.StackTraceElement');
        StackTraceElement.getMethodName.implementation = function() {
            var methodName = this.getMethodName();
            console.log('StackTraceElement.getMethodName : ', methodName);
            return methodName;
        }
        console.log(StackTraceElement);
        Exception.getStackTrace.implementation = function() {
            var stackTrace = this.getStackTrace();
            var element = StackTraceElement.$new('frida', 'FridaCheck', 'frida', 'frida');
            stackTrace[1] = element;
            console.log('Exception.getStackTrace : ', stackTrace[1]);
            console.log(stackTrace);
            return stackTrace;
        }
    });
}

function main() {
    hook_MainActivity();
    call_startActivity();
    hook_FridaActivity8();
    hook_FridaActivity9();
    hook_FridaActivity10();
}

setImmediate(main);