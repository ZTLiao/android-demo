package com.example.demo;

import org.junit.Test;

public class ParseElfTest {

    @Test
    public void test() {
        String soPath = "libhello-jni.so";
        ParseSo parseSo = new ParseSo(soPath);
        System.out.println("===========Elf Header===========");
        parseSo.readElfHeader(0);
    }
}
