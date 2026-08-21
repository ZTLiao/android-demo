int main(int argc, char *argv[]) {
    int a = 0;
    if (argc == 1) {
        a = 1;
        argc = 1;
    } else if (argc == 2) {
        a = 3;
        argc = 2;
    } else if (argc == 3) {
        a = 7;
        argc = 3;
    } else {
        a = 10;
        argc = 15;
    }
    return argc + a;
}