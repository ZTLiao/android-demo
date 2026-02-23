#include <jni.h>
#include <string>
#include <string.h>
#include "android/log.h"


bool myr0(char *a) {
    if (strstr(a, "r0ysue") != nullptr) {
        __android_log_print(4, "r0ysue", "i am success");
    } else {
        __android_log_print(4, "r0ysue", "i am fail");
    }
    return strstr(a, "r0ysue");
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_hook_1demo_MainActivity_stringFromJNI(JNIEnv *env, jobject thiz) {
    std::string hello = "hello from C++";
    if (myr0("1234567890")) {
        __android_log_print(4, "r0ysue", "myr0 is true");
    } else {
        __android_log_print(4, "r0ysue", "myr0 is false");
    }
    return env->NewStringUTF(hello.c_str());
}