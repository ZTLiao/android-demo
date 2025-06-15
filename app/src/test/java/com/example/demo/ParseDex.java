package com.example.demo;


public class ParseDex {

    public static byte[] byteData;

    private DexType dexType;

    public ParseDex(byte[] pArr) {
        byteData = pArr;
        dexType = new DexType();
    }

    public void parse() {
        parseHeader(0);
        printHeader();
        int stringOff = Utils.byte2Int_4(dexType.dexHeader.stringIdsOff, 0);
        int stringSize = Utils.byte2Int_4(dexType.dexHeader.stringIdsSize, 0);
        readDexStringId(stringOff, stringSize);
        printStrings();

        int typeOff = Utils.byte2Int_4(dexType.dexHeader.typeIdsOff, 0);
        int typeSize = Utils.byte2Int_4(dexType.dexHeader.typeIdsSize, 0);
        readDexTypeId(typeOff, typeSize);
        printTypes();

        int protoOff = Utils.byte2Int_4(dexType.dexHeader.protoIdsOff, 0);
        int protoSize = Utils.byte2Int_4(dexType.dexHeader.protoIdsSize, 0);
        readDexProtoId(protoOff, protoSize);
    }

    private String getStringById(int pId) {
        return dexType.dexStringList.get(pId);
    }

    private String getTypeStringById(int pId) {
        return dexType.dexTypeList.get(pId);
    }

    private String getProtoStringById(int pId) {
        return dexType.dexProtoList.get(pId);
    }

    private String getFieldStringById(int pId) {
        return dexType.dexFieldList.get(pId);
    }

    private String getMethodStringById(int pId) {
        return dexType.dexMethodList.get(pId);
    }

    private void parseHeader(int offset) {
        if (byteData == null) {
            System.out.println("byteData is null");
            return;
        }
        int curPos = 0;
        for (int i = 0; i < dexType.dexHeader.em.size(); i++) {
            System.arraycopy(byteData, curPos, dexType.dexHeader.em.get(i), 0, dexType.dexHeader.em.get(i).length);
            curPos += dexType.dexHeader.em.get(i).length;
        }

    }

    private void printHeader() {
        System.out.printf("%-12s: %s\r\n", "magic", Utils.bytes2HexString(dexType.dexHeader.magic));
        System.out.printf("%-12s: %s\r\n", "checksum", Utils.bytes2HexString(dexType.dexHeader.checksum));
        System.out.printf("%-12s: %s\r\n", "signature", Utils.bytes2HexString(dexType.dexHeader.signature));
    }

    private void readDexStringId(int pOff, int pCount) {
        for (int i = 0; i < pCount; i++) {
            DexType.DexStringId t_dexStringId = dexType.new DexStringId();
            System.arraycopy(byteData, pOff + i * 4, t_dexStringId.stringDataOff, 0, t_dexStringId.stringDataOff.length);
            int dexStringId = Utils.byte2Int_4(t_dexStringId.stringDataOff, 0);
            Utils.RETULEB128 tmp = Utils.readULEB128(byteData, dexStringId);
            byte[] strContent = Utils.copyNewBytes(byteData, dexStringId + tmp.readSize, tmp.retValue);
            String realString = new String(strContent);
            dexType.dexStringId.add(t_dexStringId);
            dexType.dexStringList.add(realString);
        }
    }

    private void printStrings() {
        System.out.println("---------------------strings---------------------");
        for (int i = 0; i < dexType.dexStringList.size(); i++) {
            System.out.println("\t" + dexType.dexStringList.get(i));
        }
    }

    private void readDexTypeId(int pOff, int pCount) {
        for (int i = 0; i < pCount; i++) {
            DexType.DexTypeId t_dexTypeId = dexType.new DexTypeId();
            System.arraycopy(byteData, pOff + i * 4, t_dexTypeId.descriptorIdx, 0, t_dexTypeId.descriptorIdx.length);
            int dexTypeId = Utils.byte2Int_4(t_dexTypeId.descriptorIdx, 0);
            dexType.dexTypeId.add(t_dexTypeId);
            dexType.dexTypeList.add(getStringById(dexTypeId));
        }
    }

    private void printTypes() {
        System.out.println("---------------------types---------------------");
        for (String one : dexType.dexTypeList) {
            System.out.println("\t" + one);
        }
    }

    private void readDexProtoId(int pOff, int pCount) {
        for (int i = 0; i < pCount; i++) {
            DexType.DexProtoId t_dexProtoId = dexType.new DexProtoId();
            System.arraycopy(byteData, pOff + 12 * i, t_dexProtoId.shortyIdx, 0, t_dexProtoId.shortyIdx.length);
            System.arraycopy(byteData, pOff + 12 * i + 4, t_dexProtoId.returnTypeIdx, 0, t_dexProtoId.returnTypeIdx.length);
            System.arraycopy(byteData, pOff + 12 * i + 8, t_dexProtoId.parametersOff, 0, t_dexProtoId.parametersOff.length);

            String pri = "";
            String shorty_string = getStringById(Utils.byte2Int_4(t_dexProtoId.shortyIdx, 0));
            String return_type_idx = getStringById(Utils.byte2Int_4(t_dexProtoId.returnTypeIdx, 0));
            pri += (shorty_string + ", ");
            pri += (return_type_idx + ", ");
            int parameters_Off = Utils.byte2Int_4(t_dexProtoId.parametersOff, 0);
            pri += "(";
            if (parameters_Off == 0) {
                dexType.dexProtoId.add(t_dexProtoId);
                dexType.dexProtoList.add(getTypeStringById(Utils.byte2Int_4(t_dexProtoId.returnTypeIdx, 0)));
                pri += ")";
                continue;
            }

            int structCount = Utils.byte2Int_4(byteData, parameters_Off);
            for (int j = 0; j < structCount; j++) {
                DexType.DexTypeItem t_dexTypeItem = dexType.new DexTypeItem();
                System.arraycopy(byteData, parameters_Off + 4 + j * t_dexTypeItem.typeIdx.length, t_dexTypeItem.typeIdx, 0, t_dexTypeItem.typeIdx.length);

                int tmp = Utils.byte2Short_2(t_dexTypeItem.typeIdx, 0);
                String p = getTypeStringById(tmp);
                pri += (p + ", ");
            }

            pri += ")";
            System.out.println(pri);

            dexType.dexProtoId.add(t_dexProtoId);
            dexType.dexProtoList.add(getTypeStringById(Utils.byte2Int_4(t_dexProtoId.returnTypeIdx, 0)));
        }
    }
}
