package com.example.demo.server;

import java.util.HashMap;
import java.util.Map;

public class Student {

    private static Map<String, Integer> info = new HashMap<>();

    public Student() {
        info.put("tom", 23);
        info.put("john", 19);
        info.put("jack", 20);
    }

    public int getAge(String name) {
        return info.get(name);
    }

    public void setAge(String name, Integer age) {
        info.put(name, age);
    }

    public static Map<String, Integer> getInfo() {
        return info;
    }

    public static void setInfo(Map<String, Integer> info) {
        Student.info = info;
    }
}
