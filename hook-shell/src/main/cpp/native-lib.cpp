#include <jni.h>
#include <string>

#include <unistd.h>
#include <android/log.h>
#include <fcntl.h>
#include <asm/fcntl.h>
#include <sys/mman.h>
#include <dlfcn.h>

extern "C" {
#include "hook/dlfcn/dlfcn_compat.h"
#include "hook/include/inlineHook.h"
}

typedef unsigned char byte;

#define TAG "SecondShell"

#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, TAG, __VA_ARGS__)

struct DexFile {
    uint32_t declaring_class_;
    void *begin;
    uint32_t size;
};
struct ArtMethod {
    uint32_t declaring_class_;
    uint32_t access_flags_;
    uint32_t dex_code_item_offset_;
    uint32_t dex_method_index_;
};

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_hook_1shell_MainActivity_stringFromJNI(JNIEnv *env, jobject thiz) {
    std::string hello = "hello from C++";
    return env->NewStringUTF(hello.c_str());
}

void* *(originExecve)(const char *__file, char *const *__argv, char *const *__envp);

void* *myExecve(const char *__file, char *const *__argv, char *const *__envp) {
    LOGD("process : %d, execve : %s", getpid(), __file);
    if (strstr(__file, "dex2oat")) {
        return NULL;
    } else {
        return originExecve(__file, __argv, __envp);
    }
}

void* *(originLoadMethod)(void *, void *, void *, void *, void *);

void* myLoadMethod(void *a, void *b, void *c, void *d, void *e) {
    LOGD("process : %d, before run loadMethod ", getpid());
    struct ArtMethod *artMethod = (struct ArtMethod *) e;
    struct DexFile *dexFile = (struct DexFile *) b;
    LOGD("process : %d, enter loadMethod : dexFileBegin : %p, size : %d", getpid(), dexFile->begin, dexFile->size);
    char dexFilePath[100] = {0};
    sprintf(dexFilePath, "/sdcard/5/%d_%d.dex", dexFile->size, getpid());
    int fd = open(dexFilePath, O_CREAT | O_RDWR, 0666);
    if (fd > 0) {
        write(fd, dexFile->begin, dexFile->size);
        close(fd);
    }
    void *result = originLoadMethod(a, b, c, d, e);
    LOGD("process : %d, enter loadMethod : code_offset : %d, idx : %d", getpid(), artMethod->dex_code_item_offset_, artMethod->dex_method_index_);
    byte *code_item_addr = static_cast<byte *>(dexFile->begin) + artMethod->dex_code_item_offset_;
    LOGD("process : %d, enter loadMethod : dexFile->begin : %p, before dump code item : %p", getpid(), dexFile->begin, dexFile->size, code_item_addr);
    if (artMethod->dex_method_index_ == 30076) {
        LOGD("process : %d, enter loadMethod : dexFile->begin : %p, size : %d, start replace method", getpid(), dexFile->begin, dexFile->size);
        byte *code_item_addr = (byte *) dexFile->begin + artMethod->dex_code_item_offset_;
        LOGD("process : %d, enter loadMethod : dexFile->begin : %p, size : %d, before dump code item : %p", getpid(), dexFile->begin, dexFile->size, code_item_addr);
        int result = mprotect(dexFile->begin, dexFile->size, PROT_WRITE);
        byte *code_item_start = static_cast<byte *>(code_item_addr) + 16;
        LOGD("process : %d, enter loadMethod : dexFile->begin : %p, size : %d, code item start : %p", getpid(), dexFile->begin, dexFile->size, code_item_start);
        byte inst[16] = {0x1A, 0x00, 0x1D, 0x60, 0x1A, 0x01, 0x64, 0x5A, 0x71, 0x20, 0x57, 0x07,
                         0x10, 0x00, 0x0E, 0x00};
        for (int i = 0; i < sizeof(inst); i++) {
            code_item_start[i] = inst[i];
        }
        memset(dexFilePath, 0, 100);
        sprintf(dexFilePath, "/sdcard/5/%d_%d_after.dex", dexFile->size, getpid());
        fd = open(dexFilePath, O_CREAT | O_RDWR, 0666);
        if (fd > 0) {
            write(fd, dexFile->begin, dexFile->size);
            close(fd);
        }
    }
    LOGD("process : %d, after loadMethod : code offset : %d, idx : %d", getpid(), artMethod->dex_code_item_offset_, artMethod->dex_method_index_);
    return result;
}

void hookLibc() {
    LOGD("go into hookLibc");
    void *libc_addr = dlopen_compat("libc.so", RTLD_NOW);
    void *execve_addr = dlsym_compat(libc_addr, "execve");
    if (execve_addr == NULL) {
        return;
    }
    if (ELE7EN_OK == registerInlineHook((uint32_t) execve_addr, (uint32_t) myExecve, (uint32_t **) &originExecve)) {
        if (ELE7EN_OK == inlineHook((uint32_t) execve_addr)) {
            LOGD("inline hook execve success");
        } else {
            LOGD("inline hook execve failure");
        }
    }
}

void hookART() {
    LOGD("go into hookART");
    void *libart_addr = dlopen_compat("/system/lib/libart.so", RTLD_NOW);
    if (libart_addr == NULL) {
        return;
    }
    void *loadMethod_addr = dlsym_compat(libart_addr, "_ZN3art11ClassLinker10LoadMethodERKNS_7DexFileERKNS_21ClassDataItemIteratorENS_6HandleINS_6mirror5ClassEEEPNS_9ArtMethodE");
    if (loadMethod_addr == NULL) {
        return;
    }
    if (ELE7EN_OK == registerInlineHook((uint32_t) loadMethod_addr, (uint32_t) myLoadMethod, (uint32_t **) &originLoadMethod)) {
        if (ELE7EN_OK == inlineHook((uint32_t) loadMethod_addr)) {
            LOGD("inline hook loadMethod success");
        } else {
            LOGD("inline hook loadMethod failure");
        }
    }
}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_hook_1shell_MainActivity_secondShell(JNIEnv *env, jobject thiz) {
    hookLibc();
    hookART();
}