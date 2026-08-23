#include <jni.h>
#include <string>
#include <string.h>
#include "android/log.h"
#include <unistd.h>
#include <sys/syscall.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>

bool myr0(char *a) {
    if (strstr(a, "r0ysue") != nullptr) {
        __android_log_print(4, "r0ysue", "i am success");
    } else {
        __android_log_print(4, "r0ysue", "i am fail");
    }
    return strstr(a, "r0ysue");
}

long test_asm(long n) {
    long m = 0;
#ifdef __arm__
    __asm__("mov r0, %[input_n]\r\n"
            "add r0, r0\r\n"
            "mov %[result_m], r0\r\n"
            :[result_m] "=r" (m)
            :[input_n] "r" (n)
            );
#elif __aarch64__
    __asm__("mov x0, %[input_n]\r\n"
            "add x0, x0, x0\r\n"
            "add x0, x0, x0\r\n"
            "lsl x0, x0, #2\r\n"
            "mov %[result_m], x0\r\n"
    :[result_m] "=r" (m)
    :[input_n] "r" (n)
    );
#endif
    return m;
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_hook_1demo_MainActivity_stringFromJNI(JNIEnv *env, jobject thiz) {
    test_asm(10);
    std::string hello = "hello from C++";
    if (myr0("1234567890")) {
        __android_log_print(4, "r0ysue", "myr0 is true");
    } else {
        __android_log_print(4, "r0ysue", "myr0 is false");
    }
    return env->NewStringUTF(hello.c_str());
}
//
//__attribute__((naked))
//long raw_syscall(long number, ...) {
//#ifdef __arm__
//    __asm__("mov r12, sp\r\n"
//            "stmfd sp!, {r4-r7}\r\n"
//            "mov r7, r0\r\n"
//            "mov r0, r1\r\n"
//            "mov r1, r2\r\n"
//            "mov r2, r3\r\n"
//            "ldmia r12, {r3-r6}\r\n"
//            "svc 0\r\n"
//            "ldmfd sp!, {r4-r7}\r\n"
//            "bx lr");
//#elif __aarch64__
//    __asm__("mov x8, x0\r\n"
//            "mov x0, x1\r\n"
//            "mov x1, x2\r\n"
//            "mov x2, x3\r\n"
//            "mov x3, x4\r\n"
//            "mov x4, x5\r\n"
//            "mov x5, x6\r\n"
//            "svc 0\r\n"
//            "ret");
//#endif
//}

extern "C" long raw_syscall(long number, ...);

std::string readFile(const char *file_path) {
    std::string result = "";
    long fd = syscall(__NR_openat, 0, file_path, O_RDONLY | O_CREAT, 0644);
    if (fd != -1) {
        char buffer[0x1000] = {0};
        raw_syscall(__NR_read, fd, buffer, 0x1000);
        result = buffer;
        syscall(__NR_close, fd);
    }
    return result;
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_hook_1demo_MainActivity_readFile(JNIEnv *env, jobject thiz, jstring file_path) {
    const char *filePath = env->GetStringUTFChars(file_path, nullptr);
    std::string result = readFile(filePath);
    env->ReleaseStringUTFChars(file_path, filePath);
    std::string hello = "Hello from C++" + std::to_string(test_asm(10)) + "" + result;
    return env->NewStringUTF(hello.c_str());
}