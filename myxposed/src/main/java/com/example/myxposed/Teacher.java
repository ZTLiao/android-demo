package com.example.myxposed;

public class Teacher {

    private int age;

    private String name;

    private boolean gender;

    public Teacher() {}

    public Teacher(int age) {
        this.age = age;
    }

    public Teacher(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public Teacher(int age, String name, boolean gender) {
        this.age = age;
        this.name = name;
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                '}';
    }
}
