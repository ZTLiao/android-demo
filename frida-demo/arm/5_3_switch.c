#include <stdio.h>

int main(int argc, char *argv[]) {
    switch(argc) {
        case 1:
            argc = 2;
            break;
        case 2:
            argc = 3;
            break;    
        case 3:
            argc = 4;
            break;     
        case 4:
            argc = 5;
            break;                         
        default:
            break;
    }
    switch(argc) {
        case 5:
            argc = 2;
            break;
        case 55:
            argc = 3;
            break;    
        case 60:
            argc = 4;
            break;     
        case 80:
            argc = 5;
            break;                         
        default:
            break;
    }
    return argc;
}