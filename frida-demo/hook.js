function main() {
    Java.perform(function() {
        var LoginActivity = Java.use('com.github.lastingyang.androiddemo.Activity.LoginActivity');
        LoginActivity.a.overload('java.lang.String', 'java.lang.String').implementation = function(str, str2) {
            var result = this.a(str, str2);
            console.log('LoginActivity.a : ', str, str2, result);
            return result;
        }

        var FridaActivity1 = Java.use('com.github.lastingyang.androiddemo.Activity.FridaActivity1');
        FridaActivity1.a.implementation = function(arg1) {
            console.log('FridaActivity1.a arg1 : ', arg1);
            arg1 = this.b('Imyang');
            var result = this.a(arg1);
            console.log('FridaActivity1.a before : ', result);
            result = 'R4jSLLLLLLLLLLLOrLE7/5B+Z6fsl65yj6BgC6YWz66gO6g2t65Pk6a+P65NK44NNROl0wNOLLLL=';
            console.log('FridaActivity1.a after : ', result);
            return result;
        };

        console.log('main');
    });    


}

setImmediate(main);