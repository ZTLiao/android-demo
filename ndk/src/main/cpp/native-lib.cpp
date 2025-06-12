#include <jni.h>
#include <string>
#include <android/log.h>
#include <sys/types.h>
#include <dirent.h>

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
    return flag;
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_ndk_MainActivity_callStaticJavaFunFromJNI(JNIEnv *env, jobject thiz) {
    jclass jclass_strudent_2 = env->FindClass("com/example/ndk/Student");
    jmethodID jmethodId = env->GetStaticMethodID(jclass_strudent_2, "calcLength", "(Ljava/lang/String;)I");
    jstring jstring1 = env->NewStringUTF("hahaha");
    jint jint_ret = env->CallStaticIntMethod(jclass_strudent_2, jmethodId, jstring1);
    LOGI("ndk call calcLength ret : %d", jint_ret);
    return jstring1;
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_ndk_MainActivity_getPackageNameFromJNI(JNIEnv *env, jobject thiz) {
    jclass cls_activity_thread = env->FindClass("android/app/ActivityThread");
    jmethodID jmethodId = env->GetStaticMethodID(cls_activity_thread, "currentActivityThread", "()Landroid/app/ActivityThread;");
    jobject jobj = env->CallStaticObjectMethod(cls_activity_thread, jmethodId);
    jmethodID jmethodId_getApplication = env->GetMethodID(cls_activity_thread, "getApplication", "()Landroid/app/Application;");
    jobject ins_mintialApplication = env->CallObjectMethod(jobj, jmethodId_getApplication);
    jclass cls_Application = env->GetObjectClass(ins_mintialApplication);
    jmethodID  jmethodId_getPackageName = env->GetMethodID(cls_Application, "getPackageName", "()Ljava.lang.String;");
    jstring pkgName = (jstring) env->CallObjectMethod(ins_mintialApplication, jmethodId_getPackageName);
    return pkgName;
}

jstring getPackageName(JNIEnv *env, jobject thiz) {
    jclass cls_activity_thread = env->FindClass("android/app/ActivityThread");
    jmethodID jmethodId = env->GetStaticMethodID(cls_activity_thread, "currentActivityThread", "()Landroid/app/ActivityThread;");
    jobject jobj = env->CallStaticObjectMethod(cls_activity_thread, jmethodId);
    jmethodID jmethodId_getApplication = env->GetMethodID(cls_activity_thread, "getApplication", "()Landroid/app/Application;");
    jobject ins_mintialApplication = env->CallObjectMethod(jobj, jmethodId_getApplication);
    jclass cls_Application = env->GetObjectClass(ins_mintialApplication);
    jmethodID  jmethodId_getPackageName = env->GetMethodID(cls_Application, "getPackageName", "()Ljava.lang.String;");
    jstring pkgName = (jstring) env->CallObjectMethod(ins_mintialApplication, jmethodId_getPackageName);
    jstring ret = env->NewStringUTF("getPackageName !!!");
    return ret;
}

const int PATH_MAX_LENGTH = 256;

JavaVM *javaVM;
struct PARAM {
    JNIEnv *a;
    jobject b;
    jstring c;
    jobject d;
};

jint JNI_OnLoad(JavaVM* vm, void* reserved) {
    javaVM = vm;
    LOGI("%s", "JNI_OnLoad");

    JNIEnv* env = NULL;
    jint result = -1;
    vm->GetEnv(reinterpret_cast<void **>(&env), JNI_VERSION_1_4);
    jclass cls_MainActivity = env->FindClass("com/example/ndk/MainActivity");
    JNINativeMethod  methods[] = {{"getPackageNameFromJNI2", "()Ljava/lang/String;", (void *) getPackageName}};
    env->RegisterNatives(cls_MainActivity, methods, sizeof(methods) / sizeof(JNINativeMethod));
    result = JNI_VERSION_1_4;
    return result;
}

static void sleep_ms(unsigned int secs) {
    struct timeval tval;
    tval.tv_sec = secs / 1000;
    tval.tv_usec = (secs * 100) * 1000000;
    select (0, NULL, NULL, NULL, &tvtall);
}

void *doWork(void *args) {
    PARAM tmp = *(PARAM *) args;
    JNIEnv *env = tmp.a;
    jobject thiz = tmp.b;
    jstring dirPath_ = tmp.c;
    jobject h_handler = tmp.d;
    if (dirPath_ == nullptr) {
        LOGE("dirPath is null!");
        return nullptr;
    }
    const char *dirPath = env->GetStringUTFChars(dirPath_, nullptr);
    if (strlen(dirPath) == 0) {
        LOGE("dirPath length is 0!");
        return nullptr;
    }
    DIR *dir = opendir(dirPath);
    if (nullptr == dir) {
        LOGE("can not open dir, check path or permission!");
        return nullptr;
    }
    struct dirent *file;
    while ((file = readdir(dir)) != nullptr) {
        if (strcmp(file->d_name, ".") == 0 || strcmp(file->d_name, "..") == 0) {
            LOGI("ignore . and ..");
            continue;
        }
        char *path = new char[PATH_MAX_LENGTH];
        memset(path, 0, PATH_MAX_LENGTH);
        strcpy(path, dirPath);
        strcat(path, "/");
        strcat(path, file->d_name);
        jstring tDir = env->NewStringUTF(path);

        if (file->d_type == DT_DIR) {
            PARAM param;
            param.a = env;
            param.b = thiz;
            param.c = (jstring) env->NewGlobalRef(tDir);
            param.d = h_handler;
            doWork(&param);
            env->DeleteGlobalRef(param.c);
        } else {
            LOGI("%s", path);
            jclass cls = env->FindClass("android/os/Message");
            jmethodID obtainMethod = env->GetStaticMethodID(cls, "obtain", "()Landroid/os/Message;");
            jobject ins_Message = env->CallStaticObjectMethod(cls, obtainMethod);
            jfieldID whatField = env->GetFieldID(cls, "what", "I");
            env->SetIntField(ins_Message, whatField, 1);

            jclass cls_bundle = env->FindClass("android/os/Bundle");
            jmethodID method_init_bundle = env->GetMethodID(cls_bundle, "<init>", "()L");
            jobject ins_bundle = env->NewObject(cls_bundle, method_init_bundle);

            jmethodID method_putString = env->GetMethodID(cls_bundle, "putString", "(Ljava/lang/String;Ljava/lang/String;)V");
            jstring jstr_key = env->NewStringUTF("fp");

            env->CallVoidMethod(ins_bundle, method_putString, jstr_key, tDir);

            jmethodID method_setData = env->GetMethodID(cls, "setData", "(Landroid/os/Bundle;)V");
            env->CallVoidMethod(ins_Message, method_setData, ins_bundle);

            jclass jcls_handler = env->GetObjectClass(h_handler);
            jmethodID method_sendMessage = env->GetMethodID(jcls_handler, "sendMessage", "(Landroid/os/Message;)Z");

            env->CallBooleanMethod(h_handler, method_sendMessage, ins_Message);

            sleep_ms(100);
        }
        delete path;
    }
}

void *doWork2(void *args) {

}

void *subWalkDir(void *args) {
    JNIEnv *env;
    javaVM->AttachCurrentThread(&env, 0);
    ((PARAM *) args)->a = env;
    void *ret = doWork2(args);
    javaVM->DetachCurrentThread();
    return ret;
}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_ndk_MainActivity_walkDir(JNIEnv *env, jobject thiz, jstring path,
                                          jobject my_handler) {
    pthread_t pid;
    PARAM* param = (PARAM*) malloc(sizeof(PARAM));
    param->a = env;
    param->b = env->NewGlobalRef(thiz);
    param->c = static_cast<jstring>(env->NewGlobalRef(path));
    pthread_create(&pid, 0, subWalkDir, param);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_ndk_MainActivity_walkDir2(JNIEnv *env, jobject thiz, jstring path,
                                           jobject my_handler) {

}
