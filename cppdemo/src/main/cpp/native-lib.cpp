#include <jni.h>
#include <string>
#include "android/log.h"
#include <sys/time.h>

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
private:
    static int staticValue;
    int a;
    char p;
public:
    CBase(int a, char p): a(a), p(p) {
        __android_log_print(4, "CPP11", "CBase(int a, char p) is called");
    }

    void setStaticValue(int value) {
        staticValue = value;
    }
};

class Base {
private :
    int a;
int b;
public:
//    Base(int a, int b): a(a), b(b) {
//        __android_log_print(4, "CPP11", "Base(int a, int b) is called");
//    }
    virtual void f() {
        __android_log_print(4, "CPP11", "base.f() is called");
    }
    virtual void g() {
        __android_log_print(4, "CPP11", "base.g() is called");
    }
    virtual void h() {
        __android_log_print(4, "CPP11", "base.h() is called");
    }
};

class Base1 {
public:
    virtual void f() {
        __android_log_print(4, "CPP11", "Base1.f() is called");
    }
};

class Base2 {
public:
    virtual void f() {
        __android_log_print(4, "CPP11", "Base2.f() is called");
    }
};

class Base3 {
public:
    virtual void f() {
        __android_log_print(4, "CPP11", "Base3.f() is called");
    }
};

class Derived: public Base {
public:
    virtual void f1() {
        __android_log_print(4, "CPP11", "derived.f1() is called");
    }
    virtual void g1() {
        __android_log_print(4, "CPP11", "derived.g1() is called");
    }
    virtual void h1() {
        __android_log_print(4, "CPP11", "derived.h1() is called");
    }
};

class FinalClass: Base1, Base2, Base3 {
public:
    virtual void f() {
        __android_log_print(4, "CPP11", "FinalClass.f() is called");
    }
};

int CBase::staticValue = 20;

void test_function() {
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

    CBase cbase(10, 2);
    __android_log_print(4, "CPP11", "sizeof(cbase) : %d", sizeof(cbase));
    void *cbase_ptr = &cbase;
    unsigned char *tmpptr = (unsigned char *)cbase_ptr;
    for (int i = 0; i < sizeof(cbase); i++) {
        unsigned char value = *(tmpptr + i);
        __android_log_print(4, "CPP11", "i : %d, value : %d", i, value);
    }
    typedef void (*Func)(void *);
    Base base;
    __android_log_print(4, "CPP11", "sizeof(base) : %d", sizeof(base));
    base.f();
    base.g();
    base.h();
    tmpptr = (unsigned char *) &base;
    for (int i = 0; i < sizeof(base); i++) {
        unsigned char value = *(tmpptr + i);
        __android_log_print(4, "CPP11", "i : %d, value : %d", i, value);
    }
    unsigned long vtableptr = *(unsigned long *) &base;
    unsigned long ffuncaddr = *(unsigned long *) vtableptr;
    Func ffunc = reinterpret_cast<Func>(ffuncaddr);
    ffunc(&base);

    unsigned long gfuncaddr = *(unsigned long *) (vtableptr + sizeof(void *));
    unsigned long hfuncaddr = *(unsigned long *) (vtableptr + sizeof(void *) * 2);
    Func gfunc = reinterpret_cast<Func>(gfuncaddr);
    Func hfunc = reinterpret_cast<Func>(hfuncaddr);
    gfunc(&base);
    hfunc(&base);

    Derived derived;
    __android_log_print(4, "CPP11", "sizeof(derived) : %d", sizeof(derived));
    vtableptr = *(unsigned long *) &derived;
    unsigned long f1funcaddr = *(unsigned long *) (vtableptr);
    unsigned long g1funcaddr = *(unsigned long *) (vtableptr + sizeof(void *));
    unsigned long h1funcaddr = *(unsigned long *) (vtableptr + sizeof(void *) * 2);
    Func f1func = reinterpret_cast<Func>(f1funcaddr);
    Func g1func = reinterpret_cast<Func>(g1funcaddr);
    Func h1func = reinterpret_cast<Func>(h1funcaddr);
    f1func(&derived);
    g1func(&derived);
    h1func(&derived);

    FinalClass finalClass;
    __android_log_print(4, "CPP11", "sizeof(finalClass) : %d", sizeof(finalClass));
    vtableptr = *((unsigned long *) &finalClass + 2);
    unsigned long findClassfaddr = *(unsigned long *) (vtableptr + sizeof(void *) * 0);
    Func finalClassfaddrf1func = reinterpret_cast<Func>(findClassfaddr);
    finalClassfaddrf1func(&finalClass);
}

class DexFile {
public:
    DexFile(uint8_t * begin, size_t size, uint32_t locationChecksum)
            : begin_(begin), size_(size), location_checksum_(locationChecksum) {

    }

    static const uint8_t kDexMagic[];
    static constexpr size_t kNumDexVersions = 3;
    static constexpr size_t kDexVersionLen = 4;
    static const uint8_t kDexMagicVersions[kNumDexVersions][kDexVersionLen];
    static constexpr size_t kSha1DigestSize = 20;
    static constexpr uint32_t kDexEndianConstant = 0x12345678;
    static const char* kClassesDex;
    static const uint32_t kDexNoIndex = 0xFFFFFFFF;
    static const uint16_t kDexNoIndex16 = 0xFFFF;
    static constexpr char kMultiDexSeparator = ':';
    virtual ~DexFile() {};
public:
    uint8_t *begin_;
    size_t size_;
    std::string location_;
    uint32_t location_checksum_;
};

struct DexFileStruct {
    void *vptr;
    void *begin_;
    uint32_t size;
};

struct ArtMethod {
public:
    uint32_t reference_;
    std::atomic<std::uint32_t> access_flag_;
    uint32_t idex_code;
    uint32_t dex_method_index_;
};

void test_function1() {
    DexFile dexFile(nullptr, 0, 0);
    void* beginaddr = (void *) &(dexFile.begin_);
    void* sizeaddr = (void *) &(dexFile.size_);
    unsigned long dexfileaddr = reinterpret_cast<unsigned long>(&(dexFile));
    unsigned long beginoffset = reinterpret_cast<unsigned long>(beginaddr) - reinterpret_cast<unsigned long>(dexfileaddr);
    unsigned long sizeoffset = reinterpret_cast<unsigned long>(sizeaddr) - reinterpret_cast<unsigned long>(dexfileaddr);
    __android_log_print(4, "CPP11", "beginoffset : %d, sizeoffset : %d", beginoffset, sizeoffset);
}

int add(int a, int b) {
    return a + b;
}

int sum(int m) {
    int result = 0;
    for (int i = 0; i < m; i++) {
        result = add(result, i);
    }
    return result;
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_cppdemo_MainActivity_stringFromJNI(JNIEnv *env, jobject thiz) {
    test_function();
    test_function1();
    struct timeval timestart, timeend;
    gettimeofday(&timestart, nullptr);
    int result = sum(1000);
    gettimeofday(&timeend, nullptr);
    unsigned long time = (timeend.tv_sec * 1000000 + timeend.tv_usec) - (timestart.tv_sec * 1000000 + timestart.tv_usec);
    __android_log_print(4, "CPP11", "sum : %d, time : %ld", result, time);
    std::string hello = "hello from C++";
    return env->NewStringUTF(hello.c_str());
}