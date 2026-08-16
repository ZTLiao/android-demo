#include <stdio.h>

extern int add(int x, int y);

int main(int argc, char const *argv[]) {
    printf("hello arm:%d\r\n", add(1, 2));
    return 0;
}