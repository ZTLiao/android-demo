package com.example.hook_demo;

public class DecodeUtils {

    private final String password = "\u0013YQWZQ\u0012Fx$/a.5a.&";

    private String decode(String str) {
        char[] charArray = str.toCharArray();
        for (int i = 0; i < charArray.length / 2; i++) {
            char c = charArray[i];
            charArray[i] = (char) (charArray[(charArray.length - i) - 1] ^ 'A');
            charArray[(charArray.length - i) - 1] = (char) (c ^ '2');
        }
        return new String(charArray);
    }

}
