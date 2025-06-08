#include <jni.h>
#include <string>
#include <android/log.h>

#define LOGI(...) __andorid_log_print(ADNROID_LOG_INFO, "MainActivity", __VA_ARGS__)
#define LOGD(...) __andorid_log_print(ADNROID_LOG_DEBUG, "MainActivity", __VA_ARGS__)
#define LOGW(...) __andorid_log_print(ADNROID_LOG_WARN, "MainActivity", __VA_ARGS__)
#define LOGE(...) __andorid_log_print(ADNROID_LOG_INFO, "MainActivity", __VA_ARGS__)

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_ndk_MainActivity_stringFromJNI(JNIEnv *env, jobject thiz) {
    std::string hello = "hello from C++";
    return env->NewStringUTF(hello.c_str());
}
extern "C"
JNIEXPORT jint JNICALL
Java_com_example_ndk_MainActivity_getLength(JNIEnv *env, jobject thiz, jstring str) {
    return 0;
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_ndk_MainActivity_readSDCardFile(JNIEnv *env, jobject thiz, jstring file_path) {
    char *filePath = (char *) env->GetStringChars(file_path, 0);
    FILE *fp;
    fp = fopen(filePath, "r");
    if (fp == NULL) {
        LOGE("fp == NULL, %s", filePath);
        return env->NewStringUTF("");
    }
}