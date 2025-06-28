package com.example.demo;

import java.util.Arrays;

public class ElfSymbolTable {
    public byte[] st_name_ndx = new byte[4];
    public byte[] st_value = new byte[4];
    public byte[] st_size = new byte[4];
    public byte[] st_info = new byte[1];
    public byte[] st_other = new byte[1];
    public byte[] st_shndx = new byte[2];
    public ElfStringTable mElfStringTable;

    public void setmElfStringTable(ElfStringTable p) {
        this.mElfStringTable = p;
    }

    public int getType() {
        return st_info[0] & 0x0F;
    }

    public int getBinding() {
        return st_info[0] >> 4;
    }


    @Override
    public String toString() {
        return "ElfSymbolTable{" +
                "st_name_ndx=" + Utils.byte2Int(st_name_ndx) + ":" + mElfStringTable.getCStringByIndex(Utils.byte2Int(st_name_ndx)) +
                ", st_value=" + Utils.byte2Int(st_value) +
                ", st_size=" + Utils.byte2Int(st_size) +
                ", st_info=" + Arrays.toString(st_info) +
                ", st_other=" + Arrays.toString(st_other) +
                ", st_shndx=" + Utils.byte2Short(st_shndx) +
                '}';
    }
}
