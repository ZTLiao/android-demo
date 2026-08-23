
#inclulde <stdio.h>


class CBase {

    public:
    void setBaseNumber(int num) {
        mBaseNumber = num;
    }
    void setBaseNumber(int num1, int num2) {
        mBaseNumber = num1 + num2;
    }
    void setBaseNumber(int num1, int num2, int num3) {
        mBaseNumber = num1 + num2 + num3;
    }
    void setBaseNumber2(int num = 3) {
        mBaseNumber = num;
    }

    virtual int getBaseNumber() {
        return mBaseNumber;
    }

    virtual int getBaseNumber2 = 0;

    public:
        int mBaseNumber;
        int mBaseNumber2;

};

class CClassA: public CBase {
    public:
        int mBaseNumber;

    public:
    void setBaseNumber(int num) {
        mBaseNumber = num;
    }

    virtual int getBaseNumber2() {
        return mBaseNumber + 1;
    }
};

class CClassB: public CBase {
    public:
        virtual int getBaseNumber2() {
        return mBaseNumber + 2;
    }
}


class CClassC: public CClassB {
    public:
        virtual int getBaseNumber2() {
        return mBaseNumber + 3;
    }
}

void test_try_catch(int n) {
    try {
        if (n == 2) {
            throw (char) '2';
        }
    } catch(char e) {
        printf("catch char \r\n");
    }
}

int main(int argc, char *argv[]) {
    CBase cBase;
    cBase.mBaseNumber = 1;
    cBase.mBaseNumber2 = 2;
    cBase.setBaseNumber(3);
    cBase.setBaseNumber(4, 5);

    CBase *pBase = &cBase;
    pBase->getBaseNumber();

    ClassA classA;
    classA.mBaseNumber = 11;
    classA.mBaseNumber2 = 22;
    classA.setBaseNumber(33);
    classA.setBaseNumber(44, 55, 66);
    classA.setBaseNumber2();

    ClassB *pClassB = new ClassB();
    pClassB->getBaseNumber2();
    delete pClassB;

    ClassC *pClassC = new ClassC();
    pClassC->getBaseNumber2();
    delete pClassC;    

    test_try_catch(2);
    return 0;
}