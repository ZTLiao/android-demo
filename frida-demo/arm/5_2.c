int main(int argc, char *argv[]) {
    int n1 = argc + 1;
    int n2 = argc - 1;
    int n3 = argc * 0x4000;
    argc = 0x1000;
    int n4 = argc / 13;
    int n5 = argc % 14;
    return 0;
}