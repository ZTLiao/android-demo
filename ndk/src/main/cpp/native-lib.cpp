#include <jni.h>
#include <string>
#include <android/log.h>

#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, "MainActivity", __VA_ARGS__)
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, "MainActivity", __VA_ARGS__)
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN, "MainActivity", __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, "MainActivity", __VA_ARGS__)

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
    jstring jstring_ret = env->NewStringUTF("null");
    const char *temp = env->GetStringUTFChars(jstring_ret);
    char *filePath = (char *) env->GetStringChars(file_path, 0);
    FILE *fp;
    fp = fopen(filePath, "r");
    if (fp == NULL) {
        LOGE("fp == NULL, %s", filePath);
        return jstring_ret;
    }
    char buff[1024];
    while (fgets(buff, 1024, fp) != NULL) {
        LOGI("fgets : %s", buff);
    }
    env->ReleaseStringUTFChars(jstring_ret, temp);
    jstring_ret = env->NewStringUTF(buff);
    return jstring_ret;
}
extern "C"
JNIEXPORT jint JNICALL
Java_com_example_ndk_MainActivity_callJavaFunFromJNI(JNIEnv *env, jobject thiz, jobject param) {
    jclass jclass_strudent_1 = env->GetObjectClass(param);
    jclass jclass_strudent_2 = env->FindClass("com/example/ndk/Student");
    jmethodID jmethodId_study = env->GetMethodID(jclass_strudent_1, "study", "(I)Ljava/lang/String;");
    int flag = 34;
    jobject jobject_ret = env->CallObjectMethod(param, jmethodId_study, flag);
    const char *t = env->GetStringUTFChars((jstring) jobject_ret, 0);
    LOGI("ndk call study ret : %s", t);
}