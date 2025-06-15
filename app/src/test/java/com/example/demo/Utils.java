package com.example.demo;

import java.io.*;

public final class Utils {

    public static String bytes2HexString(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            int t = bytes[i] & 0xff;
            String hex = Integer.toHexString(t).toUpperCase();
            if (hex.length() < 2) {
                result.append("0" + hex);
            } else {
                result.append(hex);
            }
            result.append(" ");
        }
        return result.toString();

    }

    public static String bytes2HexStringBig(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            int t = bytes[i] & 0xff;
            String hex = Integer.toHexString(t).toUpperCase();
            if (hex.length() < 2) {
                result.append("0" + hex);
            } else {
                result.append(hex);
            }
            result.append(" ");
        }
        return result.toString();

    }

    public static byte[] readFile(String fileName) {
        File file = new File(fileName);
        FileInputStream fis = null;
        ByteArrayOutputStream bos = null;
        try {
            fis = new FileInputStream(file);
            bos = new ByteArrayOutputStream();
            byte[] temp = new byte[1024];
            int size = 0;
            while ((size = fis.read(temp)) != -1) {
                bos.write(temp, 0, size);
            }
            return bos.toByteArray();
        } catch (Exception e) {
            System.out.println("read file error:" + e.toString());
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (Exception e) {
                    System.out.println("close file error:" + e.toString());
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (Exception e) {
                    System.out.println("close file error:" + e.toString());
                }
            }
        }
        return null;
    }

    public static byte[] copyNewBytes(byte[] addr, int start, int length) {
        byte[] destByte = new byte[length];
        for (int i = 0; i < length; i++) {
            destByte[i] = addr[start + i];
        }
        return destByte;
    }

    public static int byte2Int_4(byte[] res, int pOff) {
        int targets = (res[pOff + 0] & 0xff)
                | ((res[pOff + 1] << 8) & 0xff00)
                | ((res[pOff + 2] << 24) >>> 8)
                | (res[pOff + 3] << 24);
        return targets;
    }

    public static short byte2Short_2(byte[] b, int pOff) {
        short s = 0;
        short s0 = (short) (b[pOff + 0] & 0xff);// 最低位
        short s1 = (short) (b[pOff + 1] & 0xff);
        s1 <<= 8;
        s = (short) (s0 | s1);
        return s;
    }


    static class RETULEB128 {
        int retValue;
        int readSize;
    }

    public static RETULEB128 readULEB128(byte[] data, int pOff) {
        try {
            RETULEB128 ret = new RETULEB128();
            int result = 0;
            int shift = 0;
            int bytesRead = 0;
            int i = -1;

            while (true) {
                i++;
                if (pOff + i >= data.length)
                    throw new IOException("pOff+i >= data.length");
                int currentByte = data[pOff + i];

                bytesRead++;
                result |= (currentByte & 0x7F) << shift;
                shift += 7;

                if ((currentByte & 0x80) == 0) {
                    break;
                }
                if (bytesRead >= 5) {
                    throw new IOException("Invalid ULEB128 encoded data: more than 5 bytes");
                }
            }

            ret.retValue = result;
            ret.readSize = bytesRead;
            return ret;
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return null;
    }


}
