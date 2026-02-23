#include <jni.h>
#include <string>
#include <stdio.h>

#include "sandhook_native.h"
#include "android/log.h"

void *(*old_strstr)(char *, char *) = nullptr;

void *new_strstr(char *a, char *b) {
    __android_log_print(4, "r0ysue", "i am from new_strstr");
    if (strcmp(b, "r0ysue")) {
        int a0 = 1;
        return &a0;
    } else {
        return old_strstr(a, b);
    }
}

extern "C" JNICALL jint JNI_OnLoad(JavaVM *vm, void *re) {
    __android_log_print(4, "r0ysue", "i am from jni_onload");
    old_strstr = reinterpret_cast<void *(*)(char *, char *)>(SandInlineHookSym("/system/lib64/libc.so", "strstr", reinterpret_cast<void*>(new_strstr)));
    return JNI_VERSION_1_6;
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_roysue_r0somake_MainActivity_stringFromJNI(JNIEnv *env, jobject thiz) {
    std::string hello = "hello from C++";
    return env->NewStringUTF(hello.c_str());
}