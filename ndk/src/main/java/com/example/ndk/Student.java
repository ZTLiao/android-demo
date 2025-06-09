package com.example.ndk;

import android.util.Log;

public class Student {

    private String stuName;

    private int stuAge = 22;

    public Student(String stuName, int stuAge) {
        this.stuName = stuName;
        this.stuAge = stuAge;
    }

    public Student(String stuName) {
        this.stuName = stuName;
    }

    public String study(int flag) {
        Log.i(this.getClass().getSimpleName(), "name : " + stuName + ", age : " + stuAge + ", flag : " + flag);
        return "studyRet";
    }

    public static int calcLength(String param) {
        return param.length();
    }

    public int getAge() {
        return stuAge;
    }
}
