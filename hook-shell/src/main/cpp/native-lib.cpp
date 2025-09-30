#include <jni.h>
#include <string>
#include <android/log.h>
#include <sys/types.h>
#include <dirent.h>

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_hook_1shell_MainActivity_stringFromJNI(JNIEnv *env, jobject thiz) {
    std::string hello = "hello from C++";
    return env->NewStringUTF(hello.c_str());
}
extern "C"
JNIEXPORT void JNICALL
Java_com_example_hook_1shell_MainActivity_secondShell(JNIEnv *env, jobject thiz) {
    // TODO: implement secondShell()
}