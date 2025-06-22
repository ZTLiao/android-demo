#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <sys/mman.h>
#include <dlfcn.h>
#include <errno.h>
#include <elf.h>


uint32_t ret_addr;
uint32_t arg0, arg1;

int add(int aa, int bb, int cc) {
    int ret = 0;
    asm("add %0, %1, %2": "=r"(ret) : "r"(aa), "r"(bb) : "memory");
    asm("add %0, %1, %2": "+r"(ret) : "r"(ret), "r"(bb) : "memory");
    return ret;
}

int add2(int aa, int bb, int cc) {
    int ret = 0, tmp = 0;
    asm(
        "add %[param1], %[param2], %[param3]\n\t"
        "add %[param4], %[param1], %[param5]\n\t"
        : [param1]"+r"(tmp), [param4]"r"(ret)
        : [param2]"r"(aa), [param3]"r"(bb), [param5]"r"(cc)
        : "memory"
    );
    asm(
        "mov r0, #0x11"
        "mov r1, #0x22"
        "mov r2, #0x33"
        "bl add"
        "r0"
    );
    asm("mov r0, %[param]" : :[param]"r"(ret) : "memory");
    asm("mov pc, lr");
    return ret;
}

int add3() {
    asm(
        ""
    );
}

int oneFun(int a1, int a2, int a3, int a4, int a5, int a6, int a7, int a8) {
    printf("oneFun : %d, %d, %d, %d, %d, %d, %d, %d\n", a1, a2, a3, a4, a5, a6, a7, a8);
    return a1 + a2 + a3 + a4 + a5 + a6 + a7 + a8;
}

int main(int argc, char** argv){

    getchar();

    void* hand = dlopen("libc.so", RTLD_NOW);
    void* fopen = dlsym(hand, "fopen");

    printf("fopen:%p\n", fopen);

    int ret = oneFun(0x11, 0x22, 0x33, 0x44, 0x55, 0x66, 0x77, 0x88);

    printf("ret : %d\n", ret);

    int addRet = add(3, 4, 5);
    printf("addRet : %d\n", addRet);

    int addRet2 = add2(6, 7, 8);
    printf("addRet2 : %d\n", addRet2);

    return 0;
}
