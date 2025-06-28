#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_checkinnative_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
extern "C"
JNIEXPORT jboolean JNICALL
Java_com_example_checkinnative_MainActivity_checkUser(JNIEnv *env, jobject thiz, jstring user_name,
                                                      jstring user_pwd) {
    const char *uname = env->GetStringUTFChars(user_name, 0);
    const char *upwd = env->GetStringUTFChars(user_pwd, 0);
    if (strlen(uname) == 0) {
        return jboolean(false);
    }
    if (strlen(uname) == strlen(upwd)) {
        for (int i = 0; i < strlen(uname); ++i) {
            if (uname[i] != upwd[i]) {
                return jboolean(false);
            }
        }
        return jboolean(true);
    }
    return jboolean(false);
}
extern "C"
JNIEXPORT jint JNICALL
Java_com_example_checkinnative_MainActivity_getCoin(JNIEnv *env, jobject thiz) {
    int coin = 100;
    return coin;
}