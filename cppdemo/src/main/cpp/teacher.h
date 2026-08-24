//
// Created by pc on 2026/8/23.
//

#ifndef ANDROID_DEMO_TEACHER_H
#define ANDROID_DEMO_TEACHER_H

#include "android/log.h"
#include "person.h"
#include "student.h"

class teacher : person{
public:
    teacher(int sex, int age, int teacherId): person(sex, age) {
        this->teacherId = teacherId;
        __android_log_print(4, "CPP11", "%s", "teacher(int sex, int age, int teacherId) is called");
    }

    int getStudentId(student* stu) {
        return stu->getId();
    }

private:
    int teacherId;
};


#endif //ANDROID_DEMO_TEACHER_H
