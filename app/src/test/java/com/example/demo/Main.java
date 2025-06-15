package com.example.demo;

public class Main {

    int a = 0;

    static String b = "test Dex File";

    public int calcAdd(int c, int d) {
        int e = 5;
        System.out.println(b);
        return a + c + d + e;
    }

    public static void main(String[] args) {
        Main one = new Main();
        int f = 8;
        int g = f + one.calcAdd(34, 12);
        System.out.println("result : " + g);
    }
}
