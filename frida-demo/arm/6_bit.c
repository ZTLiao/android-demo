int main(int argc, char *argv[]) {
    int n1 = argc;
    int n2 = ~n1;
    int n3 = n1 & n2;
    int n4 = n1 | n2;
    int n5 = n1 ^ n2;
    int n6 = n1 << 1;
    int n7 = n1 >> 1;
    return 0;
}