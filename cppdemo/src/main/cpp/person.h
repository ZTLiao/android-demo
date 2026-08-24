//
// Created by pc on 2026/8/23.
//

#include "android/log.h"

#ifndef ANDROID_DEMO_PERSON_H
#define ANDROID_DEMO_PERSON_H


class person {
private:
    int age;
    int sex;
    int getSexPrivate() {
        return this->sex;
    }
protected:
    int getAgeProtected() {
        return this->age;
    }
public:
    person(int sex, int age):sex(sex), age(age) {
        __android_log_print(4, "CPP11", "%s", "person(int sex, int age) is called");
    }

    int getSex();
    int getAge();
    void setSex(int sex);
    void setAge(int age);
};


#endif //ANDROID_DEMO_PERSON_H
