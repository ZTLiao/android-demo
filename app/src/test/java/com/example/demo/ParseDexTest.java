package com.example.demo;

import org.junit.Test;

public class ParseDexTest {

    @Test
    public void test() {
        String dexPath = "HelloWorld.dex";
        byte[] fileByteArrays = Utils.readFile(dexPath);
        if (fileByteArrays == null) {
            System.out.println("read file failed");
            return;
        }
        ParseDex parseDex = new ParseDex(fileByteArrays);
        parseDex.parse();
    }
}
