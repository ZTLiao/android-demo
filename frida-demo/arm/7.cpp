class CNumber {

public:
    CNumber() {
        num1 = 11;
        num2 = 22;
        num3 = 33;
    }
    ~CNumber() {
        num1 = 0;
        num2 = 0;
        num3 = 0;
    }
    void setNum1(int num) {
        num1 = num;
    }    
    int getNum1() {
        return num1;
    }
    static void setNum4(int num) {
        CNumber::num4 = num;
    }
    virtual void setNum2(int num) {
        num2 = num;
    }
    virtual int getNum2() {
        return num2;
    }
public:
    int num1;
    int num2;
    int num3;
    static int num4;
};

CNumber g_cNumber;

int main(int argc, char *argv[]) {
    CNumber cNumber;
    cNumber.num1 = 1;
    cNumber.num2 = 2;
    cNumber.num3 = 3;
    cNumber.setNum1(4);
    cNumber.getNum1();
    cNumber.setNum4(5);
    CNumber::setNum4(6);
    cNumber.setNum2(7);
    cNumber.getNum2();

    Cnumber * pCNumber = &cNumber;
    pCNumber->setNum2(8);
    pCNumber->getNum2();
    delete cNumber;
    return 0;
}