#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_com_kanxue_base64_MainActivity_stringFromJNI(JNIEnv *env, jobject thiz) {
    std::string hello = "hello from C++";
    return env->NewStringUTF(hello.c_str());
}