#include <stdio.h>

void my_constructor1(void) __attribute__((constructor));
void my_constructor2(void) __attribute__((constructor(102)));
void my_constructor3(void) __attribute__((constructor(103)));

void my_constructor1(void) {
    printf("Call my_constructor1()\n");
}

void my_constructor2(void) {
    printf("Call my_constructor2()\n");
}

void my_constructor3(void) {
    printf("Call my_constructor3()\n");
}

int my_destructor(void) __attribute__((destructor));
int my_destructor(void) {
    printf("Call my_destructor()\n");
    return 0;
}

void void_function(char ch, short s, int i, float f, double d) {

}

int int_function(char ch, short s, int i, float f, double d) {
    return i;
}

int *pointer_function(char ch, short s, int* i, float f, double d) {
    return i;
}

int main(int argc, char *argv[]) {
    printf("Call main()\n");
    void_function('1', 2, 3, 4.0f, 5.0);
    int ret = int_function('1', 2, 3, 4.0f, 5.0);
    int *p_ret = pointer_function('1', 2, &ret, 4.0f, 5.0);
    return 0;
}