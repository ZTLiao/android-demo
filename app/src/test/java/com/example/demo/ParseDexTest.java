package com.example.demo;

public class ParseDexTest {

    public static void main(String[] args) {
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
