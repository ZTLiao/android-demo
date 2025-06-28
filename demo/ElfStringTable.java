package com.example.demo;

public class ElfStringTable {
    private byte data[];
    private int stringCount;

    public int getSt_offset() {
        return st_offset;
    }

    public int getSt_size() {
        return st_size;
    }

    private int st_offset;
    private int st_size;
    private String[] strs;

    public ElfStringTable(byte[] arr, int st_offset, int st_size) {
        this.st_offset = st_offset;
        this.st_size = st_size;
        data = new byte[st_size];
        System.arraycopy(arr, st_offset, data, 0, st_size);
        stringCount = 0;
        for (int ptr = 0; ptr < data.length; ptr++) {
            if (data[ptr] == '\0') {
                stringCount++;
            }
        }
        strs = new String[stringCount];
        for (int i = 0; i < stringCount; i++) {
            strs[i] = getCStringByIndex(i);
        }
    }

    public String getCStringByIndex(int index) {
        int startPtr = 1;
        int endPtr = 1;
        int i = -1;
        for (; endPtr < data.length; endPtr++) {
            if (data[endPtr] == '\0') {
                i++;
                if (i == index)
                    return new String(data, startPtr, endPtr - startPtr);
                else {
                    startPtr = endPtr + 1;
                }
            }
        }
        return null;
    }

    public String getCStringByOffset(long offset) {
        if (offset == 0x13)
            System.out.println("");
        for (long i = offset; i < data.length; i++) {
            if (data[(int) i] == 0) {
                if (offset == i) return "SHN_UNDEF";
                byte[] tmp = new byte[(int) (i - offset)];
                System.arraycopy(data, (int) offset, tmp, 0, tmp.length);
                return new String(tmp);
            }
        }
        return null;
    }

    public String readCStringByArrayOffset(byte[] arr, long offset) {
        for (long i = offset; i < arr.length; i++) {
            if (arr[(int) i] == 0) {
                if (offset == i) return "SHN_UNDEF";
                byte[] tmp = new byte[(int) (i - offset)];
                System.arraycopy(arr, (int) offset, tmp, 0, tmp.length);
                return new String(tmp);
            }
        }
        return null;
    }

    public int getStringCount() {
        return stringCount;
    }

}
