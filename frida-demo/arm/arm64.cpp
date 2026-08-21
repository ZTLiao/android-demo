#include <stdio.h>

struct TestStruct {
    unsigned long a;
    unsigned long b;
    unsigned long c;
};

extern "C"
TestStruct testFunction(TestStruct test, unsigned long argc) {
    TestStruct result;
    result.a = test.a + 1 * argc;
    result.b = test.b + 2 * argc;
    result.c = test.c + 3 * argc;
    return result;
}

extern "C"
unsigned long testFunction2(TestStruct test, unsigned long argc) {
    TestStruct result;
    result.a = test.a + 1 * argc;
    result.b = test.b + 2 * argc;
    result.c = test.c + 3 * argc;
    return result.a + result.b + result.c;
}

int main(int argc, char *argv[]) {
    TestStruct test;
    test.a = 1;
    test.b = 2;
    test.c = 3;
    TestStruct result = testFunction(test, argc);
    printf("%ld, %ld, %ld, %ld\r\n", result.a, result.b, result.c, testFunction2(test, argc));
    return 0;
}