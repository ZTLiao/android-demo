#include <stdio.h>

int main(int argc, char *argv[]) {
    if (argc == 2) {
        printf("if argc == 2\r\n");
    } else if (argc == 3) {
        printf("else if argc == 3\r\n");
    } else {
        printf("else argc != 2 && argc != 3\r\n");
    }
    return 0;
}