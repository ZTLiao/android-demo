package com.example.demo;

import static com.example.demo.ElfType32.STB_GLOBAL;
import static com.example.demo.ElfType32.STB_LOCAL;
import static com.example.demo.ElfType32.STT_NOTYPE;

public class ParseSo {

    public static byte[] byteArr;

    public ElfType32 type_32 = new ElfType32();

    public ElfType64 type_64 = new ElfType64();

    public String soPath;

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

    public void readProgramHeaderList(int offset, int count) {
        int pHeaderSize = Utils.byte2Short(type_32.hdr.e_phentsize);  //程序头结构体大小

        for (int i = 0; i < count; i++) {
            type_32.phdrList.add(readProgramHeader(offset + pHeaderSize * i));
            System.out.println();
            System.out.println("ProgramHeader index:" + i + " :");
            System.out.println(type_32.phdrList.get(i));
        }
    }

    public ElfType32.elf32_phdr readProgramHeader(int offset) {
        ElfType32.elf32_phdr phdr = new ElfType32.elf32_phdr();
        System.arraycopy(byteArr, offset, phdr.p_type, 0, phdr.p_type.length);
        System.arraycopy(byteArr, offset + 4, phdr.p_offset, 0, phdr.p_offset.length);
        System.arraycopy(byteArr, offset + 8, phdr.p_vaddr, 0, phdr.p_vaddr.length);
        System.arraycopy(byteArr, offset + 12, phdr.p_paddr, 0, phdr.p_paddr.length);
        System.arraycopy(byteArr, offset + 16, phdr.p_filesz, 0, phdr.p_filesz.length);
        System.arraycopy(byteArr, offset + 20, phdr.p_memsz, 0, phdr.p_memsz.length);
        System.arraycopy(byteArr, offset + 24, phdr.p_flags, 0, phdr.p_flags.length);
        System.arraycopy(byteArr, offset + 28, phdr.p_align, 0, phdr.p_align.length);
        return phdr;
    }

    public void readSectionHeaderList(int offset, int count) {
        int sHeaderSize = Utils.byte2Short(type_32.hdr.e_shentsize);
        int strIndex = Utils.byte2Int(type_32.hdr.e_shstrndx);

        readSectionHeader(offset + sHeaderSize * strIndex);

        for (int i = 0; i < count; i++) {
            if (i == strIndex) {
                continue;
            }

            System.out.println();
            System.out.println("SectionHeader index:" + i + " :");
            type_32.shdrList.add(readSectionHeader(offset + sHeaderSize * i));
        }
    }

    public ElfType32.elf32_shdr readSectionHeader(int offset) {
        ElfType32.elf32_shdr shdr = new ElfType32.elf32_shdr();
        System.arraycopy(byteArr, offset, shdr.sh_name, 0, shdr.sh_name.length);
        System.arraycopy(byteArr, offset + 4, shdr.sh_type, 0, shdr.sh_type.length);
        System.arraycopy(byteArr, offset + 8, shdr.sh_flags, 0, shdr.sh_flags.length);
        System.arraycopy(byteArr, offset + 12, shdr.sh_addr, 0, shdr.sh_addr.length);
        System.arraycopy(byteArr, offset + 16, shdr.sh_offset, 0, shdr.sh_offset.length);
        System.arraycopy(byteArr, offset + 20, shdr.sh_size, 0, shdr.sh_size.length);
        System.arraycopy(byteArr, offset + 24, shdr.sh_link, 0, shdr.sh_link.length);
        System.arraycopy(byteArr, offset + 28, shdr.sh_info, 0, shdr.sh_info.length);
        System.arraycopy(byteArr, offset + 32, shdr.sh_addralign, 0, shdr.sh_addralign.length);
        System.arraycopy(byteArr, offset + 36, shdr.sh_entsize, 0, shdr.sh_entsize.length);

        switch (Utils.byte2Int(shdr.sh_type)) {
            case ElfType32.SHT_STRTAB: {
                ElfStringTable elfStringTable = new ElfStringTable(byteArr, Utils.byte2Int(shdr.sh_offset), Utils.byte2Int(shdr.sh_size));
                type_32.elfStringTables.add(elfStringTable);
                break;
            }
            case ElfType32.SHT_DYNSYM: {  //符号表

                int baseOffset = Utils.byte2Int(shdr.sh_offset);
                int symCount = Utils.byte2Int(shdr.sh_size) / 16;

                for (int i = 0; i < symCount; i++) {
                    ElfSymbolTable elfSymbolTable = new ElfSymbolTable();
                    System.arraycopy(byteArr, baseOffset + i * 16, elfSymbolTable.st_name_ndx, 0, elfSymbolTable.st_name_ndx.length);
                    System.arraycopy(byteArr, baseOffset + 4 + i * 16, elfSymbolTable.st_value, 0, elfSymbolTable.st_value.length);
                    System.arraycopy(byteArr, baseOffset + 8 + i * 16, elfSymbolTable.st_size, 0, elfSymbolTable.st_size.length);
                    System.arraycopy(byteArr, baseOffset + 12 + i * 16, elfSymbolTable.st_info, 0, elfSymbolTable.st_info.length);
                    System.arraycopy(byteArr, baseOffset + 13 + i * 16, elfSymbolTable.st_other, 0, elfSymbolTable.st_other.length);
                    System.arraycopy(byteArr, baseOffset + 14 + i * 16, elfSymbolTable.st_shndx, 0, elfSymbolTable.st_shndx.length);

                    switch (elfSymbolTable.getBinding()) {
                        case STB_LOCAL:
                        case STB_GLOBAL: {

                        }
                    }
                    switch (elfSymbolTable.getType()) {
                        case STT_NOTYPE: {

                        }
                    }

                    type_32.elfSymbolTableList.add(elfSymbolTable);
                }

            }

            default:
                break;

        }

        String tmp = type_32.elfStringTables.get(0).getCStringByOffset(Utils.byte2Int(shdr.sh_name));
        String pri = "sh_name: " + tmp /*Utils.byte2Int(sh_name)*/
                + "\nsh_type:" + Utils.bytes2HexString(shdr.sh_type)
                + "\nsh_flags:" + Utils.bytes2HexString(shdr.sh_flags)
                + "\nsh_add:" + Utils.bytes2HexString(shdr.sh_addr)
                + "\nsh_offset:" + Utils.bytes2HexString(shdr.sh_offset)
                + "\nsh_size:" + Utils.bytes2HexString(shdr.sh_size)
                + "\nsh_link:" + Utils.bytes2HexString(shdr.sh_link)
                + "\nsh_info:" + Utils.bytes2HexString(shdr.sh_info)
                + "\nsh_addralign:" + Utils.bytes2HexString(shdr.sh_addralign)
                + "\nsh_entsize:" + Utils.bytes2HexString(shdr.sh_entsize);
        System.out.println(pri);

        return shdr;
    }

    public void printSymbolTableList() {
        for (int i = 0; i < type_32.elfSymbolTableList.size(); i++) {
            System.out.println();
            int symName = Utils.byte2Int(type_32.elfSymbolTableList.get(i).st_name_ndx);
            System.out.println("symbol index: " + i + " :");
            System.out.println(type_32.elfStringTables.get(1).getCStringByOffset(symName));
        }
    }

}
