#include <jni.h>
#include <string>
#include <stdio.h>

#include "dlfcn.h"
#include "android/log.h"

extern "C" {
#include "dlfcn/dlfcn_compat.h"
#include "dlfcn/dlfcn_nougat.h"
#include "include/inlineHook.h"
}

int (*old_strstr)(const char *,const char *) = NULL;
int new_strstr(const char *string, const char *myString) {
    __android_log_print(4, "r0ysue", "i amd hook success, %s", myString);
    if (strcmp(myString, "r0ysue")) {
        return true;
    } else {
        old_strstr(string, myString);
    }
}

int hook() {
    void *libcAddr = dlopen("libc.so", RTLD_NOW);
    void *strstrAddr = dlsym(libcAddr, "strstr");
    void *libNativeAddr = dlopen_compat("libnative-lib.so", RTLD_NOW);
    void *myR0Addr = dlsym_compat(libcAddr, "myR0");
    if (registerInlineHook((uint32_t) strstrAddr, (uint32_t) new_strstr, (uint32_t **) &old_strstr) != ELE7EN_OK) {
        return -1;
    }
    if (inlineHook((uint32_t) strstrAddr) != ELE7EN_OK) {
        return -1;
    }
    return 0;
}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_myxposed_R0SoHook_my_1hook_1strstr(JNIEnv *env, jobject thiz) {
    hook();
}

extern "C"
void _init() {
    hook();
}

extern "C" jint JNICALL JNI_OnLoad(JavaVM *vm, void *res) {
    __android_log_print(4, "r0ysue", "i am from jni_onload");
    hook();
    return JNI_VERSION_1_6;
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_myxposed_MainActivity_stringFromJNI(JNIEnv *env, jobject thiz) {
    Java_com_example_myxposed_R0SoHook_my_1hook_1strstr(env, thiz);
    std::string hello = "hello from C++";
    return env->NewStringUTF(hello.c_str());
}
