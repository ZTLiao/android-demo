#include <jni.h>
#include <string>
#include "android/log.h"

#include "person.h"
#include "student.h"
#include "teacher.h"

void swap(int *a, int *b) {
    int c = *a;
    *a = *b;
    *b = c;
}

void swap(int &a, int &b) {
    int c = a;
    a = b;
    b = c;
}

int fun1(int a) {
    return a;
}

int* fun2(int *a) {
    return a;
}

int add(int a, int b) {
    return a + b;
}

struct MyStruct {
    const static auto var = 1;
};

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_cppdemo_MainActivity_stringFromJNI(JNIEnv *env, jobject thiz) {
    std::string hello = "hello from C++";
    bool boolValue = true;
    __android_log_print(4, "CPP11", "sizeof(pointer)->%d\n", sizeof(&boolValue));
    __android_log_print(4, "CPP11", "sizeof(bool)->%d\n", sizeof(bool));
    __android_log_print(4, "CPP11", "sizeof(char)->%d\n", sizeof(char));
    __android_log_print(4, "CPP11", "sizeof(wchar_t)->%d\n", sizeof(wchar_t));
    __android_log_print(4, "CPP11", "sizeof(char16_t)->%d\n", sizeof(char16_t));
    __android_log_print(4, "CPP11", "sizeof(char32_t)->%d\n", sizeof(char32_t));
    __android_log_print(4, "CPP11", "sizeof(short)->%d\n", sizeof(short));
    __android_log_print(4, "CPP11", "sizeof(int)->%d\n", sizeof(int));
    __android_log_print(4, "CPP11", "sizeof(long)->%d\n", sizeof(long));
    __android_log_print(4, "CPP11", "sizeof(long long)->%d\n", sizeof(long long));
    __android_log_print(4, "CPP11", "sizeof(float)->%d\n", sizeof(float));
    __android_log_print(4, "CPP11", "sizeof(double)->%d\n", sizeof(double));
    __android_log_print(4, "CPP11", "sizeof(jlong)->%d\n", sizeof(jlong));
    int value_int = 50;
    int *value_ptr = &value_int;
    __android_log_print(4, "CPP11", "ptr:%p,value:%d\n", value_ptr, *value_ptr);
    int &value2 = value_int;
    __android_log_print(4, "CPP11", "ptr:%p,value:%d\n", value2, value2);
    int a = 20, b = 40;
    swap(&a, &b);
    __android_log_print(4, "CPP11", "a:%d, b:%d\n", a, b);
    int &c = a;
    int &d = b;
    swap(c, d);
    __android_log_print(4, "CPP11", "c:%d, d:%d\n", c, d);

    auto i = 1;
    __android_log_print(4, "CPP11", "i:%d\n", i);
    auto x = 5;
    auto pi = new auto(1);
    const auto *v = &x, u = 6;
    auto y = 0.0;

    MyStruct mystr;
    __android_log_print(4, "CPP11", "mystr:%d\n", mystr.var);

    int int_tmp = 10;
    decltype(int_tmp) tmp_b;
    tmp_b = 20;

    float float_b = 0.2;
    decltype(int_tmp * float_b) tmp_c;
    decltype(add(5, 6)) tmp_add;

    person man(0, 24);
    man.getAge();
    man.getSex();

    student stu(0, 24, 110);
    stu.getId();

    teacher tea(0, 50, 200);
    return env->NewStringUTF(hello.c_str());
}