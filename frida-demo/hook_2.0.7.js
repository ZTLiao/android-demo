function call_1CFF0(input_str) {
    var base_hello_jni = Module.findBaseAddress('libhello-jni.so');
    var sub_1CFF0 = new NativeFunction(base_hello_jni.add(0x1CFF0), 'int', ['pointer', 'int', 'pointer']);
    var arg0 = Memory.allocUtf8String(input_str);
    var arg1 = input_str.length;
    var arg2 = Memory.alloc(arg1);
    sub_1CFF0(arg0, arg1, arg2);
    console.log(hexdump(arg2, {length: arg1}));
    return args2;
}

function makeid(length) {
    var result = '';
    var characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    var charactersLength = characters.length;
    for (var i = 0; i < length; i++) {
        result += characters.charAt(Math.floor(Math.random() * charactersLength));
    }
    return result;
}

function test_enc() {
    const cm = new CModule(`
void enc_function(char* input_str, int input_len, char* result) {
    const char* table_key1 = "9d9107e02f0f07984956767ab1ac87e5";
    const unsigned char table_key2[] = {
        0x37, 0x92, 0x44, 0x68, 0xA5, 0x3D, 0xCC, 0x7F, 0xBB, 0xF, 0xD9, 0x88, 0xEE, 0x9A, 0xE9, 0x5A
    }; 
    int i = 0;
    for (i = 0; i < input_len; i++) {
        unsigned char X2 = input_str[i];
        unsigned char key2 = table_key2[(i & 0xF) & 0xFFFFFFFF];
        unsigned char W8 = 0xDA;
        unsigned char W30 = 0x25;
        unsigned char W2 = X2;
        unsigned char W7 = W8 & (~W2);
        X2 = X2 & 0x25;
        W2 = W7 | W2;
        unsigned char W3 = key2;
        W7 = W8 & (~W3);
        W3 = W3 & W30;
        W3 = W7 | W3;
        W2 = W2 ^ W3;
        if (i == 8) {
            i = 8; 
        }
        W3 = W2;
        
        unsigned char key1 = table_key1[(i ^ 0xFFFFFFF8) & i];
        W2 = key1;
        W7 = key2;
        W30 = key2;
        unsigned char W1 = W2 & (~W3);
        W3 = = W3 & (~W2);
        unsigned char W5 = W2 & (~W30);
        W1 = W1 | W3;
        W2 = W5 | W2;
        W1 = (unsigned char)((unsigned char)W1) + ((unsigned char)w7);
        W3 = W1 & (~W2);
        W1 = W2 & (~W1);
        W1 = W3 | W1;
        printf("%02x ", W1);
        result[i] = W1;
    }
}

int test_eq(const char *buf1, const char *buf2, int buf_len) {
    int i = 0;
    for (i = 0; i < buf_len; i++) {
        if (buf1[i] != buf2[i]) {
            printf("eq failed at %d\n", i);
            return 0;
        }
    }
    printf("eq success\n");
    return 1;
}
        `);
    console.log(JSON.stringify(cm));
    const enc_function = new NativeFunction(cm.enc_function, 'void', ['pointer', 'int', 'pointer']);
    const test_eq = new NativeFunction(cm.test_eq, 'int', ['pointer', 'pointer', 'int']);

    for (var index = 1; index < 0x100; index++) {
        var input_str = makeid(index);
        var arg0 = Memory.allocUtf8String(input_str);
        var arg1 = input_str.length;
        var arg2 = Memory.alloc(arg1);
        enc_function(arg0, arg1, arg2);
        //console.log(hexdump(arg2, {length: arg1}));
        var test_ret = test_eq(call_1CFF0(input_str), arg2, arg1);
        if (test_ret == 0) {
            console.log(input_str);
        }
    }
   
}

function hook_java() {
    Java.perform(function() {
        var HelloJni = Java.use('com.example.hellojni.HelloJni');
        HelloJni.sign2.implementation = function(arg1) {
            arg1 = '0123456789abcdef';
            var result = this.sign2(arg1);
            console.log('HelloJni.sign2 : ', arg1, result);
            return result;
        }
    });

}

function main() {
    var base_hello_jni = Module.findBaseAddress('libhello-jni.so');
    var sub_1CFF0 = base_hello_jni.add(0x1CFF0);
    console.log(sub_1CFF0); 
    // Interceptor.attach(sub_1CFF0, {
    //     onEnter: function(args) {
    //         this.arg0 = args[0];
    //         this.arg1 = args[1];
    //         this.arg2 = args[2];
    //     },
    //     onLeave: function(retval) {
    //         console.log('sub_1CFF0', 
    //             hexdump(this.arg0, {length: parseInt(this.arg1)}), '\r\n',
    //             hexdump(this.arg2, {length: parseInt(this.arg1)}), '\r\n'
    //         );
    //     }
    // });
    var input_str = '0123456789abcdef';
    call_1CFF0(input_str);
    hook_java();
    test_enc();
}

setImmediate(main);