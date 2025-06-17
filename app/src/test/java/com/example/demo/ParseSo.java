package com.example.demo;

public class ParseSo {

    public static byte[] byteArr;

    public static ElfType32 type_32 = new ElfType32();

    public static ElfType64 type_64 = new ElfType64();

    public static String soPath;

    public ParseSo(String pSoPath) {
        soPath = pSoPath;
        byteArr = Utils.readFile(soPath);
        if (byteArr == null) {
            System.out.println("read file byte failed...");
        }
    }

    public void readElfHeader(int offset) {
        if (offset <= 0) {
            System.out.println("elf header offset error");
            return;
        }
        int off = 0;
        for (int i = 0; i < type_32.hdr.em.size(); i++) {
            System.arraycopy(byteArr, off, type_32.hdr.em.get(i), 0, type_32.hdr.em.get(i).length);
            off += type_32.hdr.em.get(i).length;
        }
        System.out.println(type_32.hdr);
    }

}
