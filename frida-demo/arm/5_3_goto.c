#include <stdio.h>

int main(int argc, char *argv[]) {
    printf("goto 0\r\n");
    goto goto_label;
    printf("goto 1\r\n");
goto_label:
    printf("goto 2\r\n");
    return 0;
}