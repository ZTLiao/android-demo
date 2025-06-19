package com.example.demo;

import org.junit.Test;

public class ParseElfTest {

    @Test
    public void test() {
        String soPath = "libhello-jni.so";
        ParseSo parseSo = new ParseSo(soPath);
        System.out.println("===========Elf Header===========");
        parseSo.readElfHeader(0);

        int pHeaderOffset = Utils.byte2Int(parseSo.type_32.hdr.e_phoff);
        int pHeaderCount = Utils.byte2Int(parseSo.type_32.hdr.e_phnum);
        parseSo.readProgramHeaderList(pHeaderOffset, pHeaderCount);

        int sHeaderOffset = Utils.byte2Int(parseSo.type_32.hdr.e_shoff);
        int sHeaderCount = Utils.byte2Int(parseSo.type_32.hdr.e_shnum);
        parseSo.readSectionHeaderList(pHeaderOffset, pHeaderCount);

        //读符号表
        System.out.println("++++++++++++++++++++++Symbol Table+++++++++++++++++");
        parseSo.printSymbolTableList();
    }
}
