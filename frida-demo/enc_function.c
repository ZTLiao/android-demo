#include <stdio.h>
#include <string.h>

void enc_function(char* input_str, int input_len, char* result) {
    const char* table_key1 = "9d9107e02f0f07984956767ab1ac87e5";
    const unsigned char table_key2[] = {
        0x37, 0x92, 0x44, 0x68, 0xA5, 0x3D, 0xCC, 0x7F, 0xBB, 0xF, 0xD9, 0x88, 0xEE, 0x9A, 0xE9, 0x5A
    }; 
    int i = 0;
    for (i = 0; i < input_len; i++) {
        unsigned char X2 = input_str[i];
        unsigned char key2 = table_key2[(i & 0xF) & 0xFFFFFFFF];
        unsigned char W8 = 0xDA;
        unsigned char W30 = 0x25;
        unsigned char W2 = X2;
        unsigned char W7 = W8 & (~W2);
        X2 = X2 & 0x25;
        W2 = W7 | W2;
        unsigned char W3 = key2;
        W7 = W8 & (~W3);
        W3 = W3 & W30;
        W3 = W7 | W3;
        W2 = W2 ^ W3;
        if (i == 8) {
            i = 8; 
        }
        W3 = W2;
        
        unsigned char key1 = table_key1[(i ^ 0xFFFFFFF8) & i];
        W2 = key1;
        W7 = key2;
        W30 = key2;
        unsigned char W1 = W2 & (~W3);
        W3 = = W3 & (~W2);
        unsigned char W5 = W2 & (~W30);
        W1 = W1 | W3;
        W2 = W5 | W2;
        W1 = W1 + w7;
        W3 = W1 & (~W2);
        W1 = W2 & (~W1);
        W1 = W3 | W1;
        printf("%02x ", W1);
        result[i] = W1;
    }
}

int test_eq(const char *buf1, const char *buf2, int buf_len) {
    int i = 0;
    for (i = 0; i < buf_len; i++) {
        if (buf1[i] != buf2[i]) {
            printf("eq failed at %d\n", i);
            return 0;
        }
    }
    printf("eq success\n");
    return 1;
}

int main(int argc, char *argv[]) {
    char input_str[] = "0123456789abcdef";
    int len = strlen(input_str);
    char* result = (char*)malloc(len);
    memset(result, 0, len);
    enc_function(input_str, len, result);
    printf("\r\n result : ");
    int i = 0;
    for (i = 0; i < len; i++) {
        printf("%02x", (char) result[i]);
    }
    int ret = test_eq(result, result, len);
    printf("test_eq ret : %d\n", ret);
    free(result);
    return 0;
}