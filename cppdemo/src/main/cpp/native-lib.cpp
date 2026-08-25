#include <jni.h>
#include <string>
#include "android/log.h"

#include "person.h"
#include "student.h"
#include "teacher.h"
#include "Compute.h"

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

int add_my(int a, int b) {
    return a + b;
}

struct MyStruct {
    const static auto var = 1;
};

template <class T> T add(T a, T b) {
    return a + b;
}

template<typename T> T sub(T a, T b) {
    return a - b;
}

class Test {
private:
    int a;
public:
    Test(int a) : a(a) {
    }
    int getA() {
        return a;
    }
};

class NullClass {};

class CBase {
    int a;
    char p;
public:
    CBase(int a, char p): a(a), p(p) {

    }
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

    int result = add(5, 6);
    __android_log_print(4, "CPP11", "add(5, 6) = %d\n", result);
    __android_log_print(4, "CPP11", "sub(5, 6) = %d\n", sub(5, 6));
    short a1 = 2;
    short b1 = 3;
    short result_short = add(a1, b1);
    __android_log_print(4, "CPP11", "add(2, 3) = %d\n", result_short);
    __android_log_print(4, "CPP11", "sub(2, 3) = %d\n", sub(a1, b1));

    Compute<int> compute(5, 6);
    int result2 = compute.add();
    __android_log_print(4, "CPP11", "compute.add(5, 6) = %d\n", result2);

    Compute<short> compute1(2, 3);
    __android_log_print(4, "CPP11", "compute1.sub(5, 6) = %d\n", compute1.sub());

    auto func = []()->void {
        __android_log_print(4, "lambda", "called");
    };
    func();

    []()->void {
        __android_log_print(4, "lambda", "called");
    }();

    int a2 = 10;
    int b2 = 20;
    [a2]()->int {
        int result = 0;
        for (int i = 0; i < a2; i++) {
            result = result + i;
        }
        __android_log_print(4, "lambda", "called! result : %d", result);
        return result;
    }();

    [a2]() mutable ->int {
        int result = 0;
        a2 = 20;
        for (int i = 0; i < a2; i++) {
            result = result + i;
        }
        __android_log_print(4, "lambda", "called! result : %d", result);
        return result;
    }();
    __android_log_print(4, "lambda", "called! a2 : %d", a2);
    [=]() mutable ->int {
        int result = 0;
        a2 = 20;
        __android_log_print(4, "lambda", "called! b2 : %d", b2);
        for (int i = 0; i < a2; i++) {
            result = result + i;
        }
        __android_log_print(4, "lambda", "called! result : %d", result);
        return result;
    }();
    [&a2, &b2]() mutable ->int {
        int result = 0;
        a2 = 20;
        b2 = 30;
        __android_log_print(4, "lambda", "called! b2 : %d", b2);
        for (int i = 0; i < a2; i++) {
            result = result + i;
        }
        __android_log_print(4, "lambda", "called! result : %d", result);
        return result;
    }();
    [&]() mutable ->int {
        int result = 0;
        a2 = 20;
        b2 = 30;
        __android_log_print(4, "lambda", "called! b2 : %d", b2);
        for (int i = 0; i < a2; i++) {
            result = result + i;
        }
        __android_log_print(4, "lambda", "called! result : %d", result);
        return result;
    }();
    [&](int num) mutable ->int {
        int result = 0;
        a2 = 20;
        b2 = 30;
        __android_log_print(4, "lambda", "called! b2 : %d", b2);
        for (int i = 0; i < a2; i++) {
            result = result + i;
        }
        __android_log_print(4, "lambda", "called! result : %d", result);
        return result;
    }(30);

    Test test(100);
    int a3 = test.getA();
    int *a_ptr = (int *)&test;

    __android_log_print(4, "CPP11", "called! a3 : %d, a_ptr : %d", a3, *a_ptr);

    NullClass nullClass;
    __android_log_print(4, "CPP11", "sizeof(NullClass) : %d, sizeof(nullClass) : %d", sizeof(NullClass), sizeof(nullClass));

    CBase cbase;
    __android_log_print(4, "CPP11", "sizeof(cbase) : %d", sizeof(cbase));
    void *cbase_ptr = &cbase;
    unsigned char *tmpptr = (unsigned char *)cbase_ptr;
    for (int i = 0; i < sizeof(cbase); i++) {
        unsigned char value = *(tmpptr + i);
        __android_log_print(4, "CPP11", "i : %d, value : %d", i, value);
    }
    return env->NewStringUTF(hello.c_str());
}