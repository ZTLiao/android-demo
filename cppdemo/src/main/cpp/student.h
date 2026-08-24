//
// Created by pc on 2026/8/23.
//

#ifndef ANDROID_DEMO_STUDENT_H
#define ANDROID_DEMO_STUDENT_H


#include "person.h"
#include "android/log.h"


class student: public person {

public:
    student(int sex, int age, int id): person(sex, age) {
        this->id = id;
        __android_log_print(4, "CPP11", "%s", "student(int sex, int age, int id) is called");
    }

    int getId();
    friend void setId(int id);

private:
    int id;
};


#endif //ANDROID_DEMO_STUDENT_H
