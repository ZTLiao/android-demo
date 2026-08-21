struct StructSix {
    char ch;
    short s;
    int n;
};

int main(int argc, char *argv[]) {
    struct StructSix struct_6 = {0};
    struct_6.ch = '0';
    struct_6.s = 1;
    struct_6.n = 2;
    struct StructSix struct_6_1 = {'0', 1, 2};
    struct StructSix* p_struct_6_2 = &struct_6_1;
    p_struct_6_2->ch = '1';
    p_struct_6_2->n = 5;
    return 0;
}