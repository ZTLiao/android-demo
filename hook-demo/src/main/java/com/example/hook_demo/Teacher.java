package com.example.hook_demo;

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

    static class Student {
        int age = 10;
        String name = "mark";
        String city = "Guangzhou";
        boolean hair = false;

        public int getAge() {
            return age;
        }

        @Override
        public String toString() {
            Student a = new Student();
            String string = a.toString();
            return "Student{" +
                    "age=" + age +
                    ", name='" + name + '\'' +
                    ", city='" + city + '\'' +
                    ", hair=" + hair +
                    '}' + "\n" + string;
        }
    }
}
