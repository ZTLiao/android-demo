//
// Created by pc on 2026/8/23.
//

#include "person.h"

int person::getSex(){
    return this->sex;
}

int person::getAge(){
    return this->age;
}

void person::setSex(int sex) {
    this->sex = sex;
}

void person::setAge(int age) {
    this->age = age;
}
