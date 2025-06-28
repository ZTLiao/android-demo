package com.example.demo;


import org.jf.dexlib2.Opcode;
import org.jf.dexlib2.Opcodes;

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

        int fieldOff = Utils.byte2Int_4(dexType.dexHeader.fieldIdsOff, 0);
        int fieldSize = Utils.byte2Int_4(dexType.dexHeader.fieldIdsSize, 0);
        readDexFieldId(fieldOff, fieldSize);

        int methodOff = Utils.byte2Int_4(dexType.dexHeader.methodIdsOff, 0);
        int methodSize = Utils.byte2Int_4(dexType.dexHeader.methodIdsSize, 0);
        readDexMethodId(methodOff, methodSize);

        int classOff = Utils.byte2Int_4(dexType.dexHeader.classDefsOff, 0);
        int classSize = Utils.byte2Int_4(dexType.dexHeader.classDefsSize, 0);
        readDexClassDefsId(classOff, classSize);

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
        System.out.println("---------------------protos---------------------");
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

    private void readDexFieldId(int pOff, int pCount) {
        System.out.println("---------------------fields---------------------");
        for (int i = 0; i < pCount; i++) {
            DexType.DexFieldId t_dexFieldId = dexType.new DexFieldId();
            System.arraycopy(byteData, pOff + 8 * i, t_dexFieldId.classIdx, 0, t_dexFieldId.classIdx.length);
            System.arraycopy(byteData, pOff + 8 * i + 2, t_dexFieldId.typeIdx, 0, t_dexFieldId.typeIdx.length);
            System.arraycopy(byteData, pOff + 8 * i + 4, t_dexFieldId.nameIdx, 0, t_dexFieldId.nameIdx.length);

            int classIdx = Utils.byte2Short_2(t_dexFieldId.classIdx, 0);
            int typeIdx = Utils.byte2Short_2(t_dexFieldId.typeIdx, 0);
            int nameIdx = Utils.byte2Int_4(t_dexFieldId.nameIdx, 0);

            String str = "class:" + getTypeStringById(classIdx) + ", type:" + getTypeStringById(typeIdx) + ", name:" + getStringById(nameIdx);
            System.out.println(str);
            dexType.dexFieldId.add(t_dexFieldId);
            dexType.dexFieldList.add(getStringById(nameIdx));
        }
    }

    private void readDexMethodId(int pOff, int pCount) {
        System.out.println("------------methods----------------");
        for (int i = 0; i < pCount; i++) {
            DexType.DexMethodId t_dexMethodId = dexType.new DexMethodId();
            System.arraycopy(byteData, pOff + 8 * i, t_dexMethodId.classIdx, 0, t_dexMethodId.classIdx.length);
            System.arraycopy(byteData, pOff + 8 * i + 2, t_dexMethodId.protoIdx, 0, t_dexMethodId.protoIdx.length);
            System.arraycopy(byteData, pOff + 8 * i + 4, t_dexMethodId.nameIdx, 0, t_dexMethodId.nameIdx.length);

            int classIdx = Utils.byte2Short_2(t_dexMethodId.classIdx, 0);
            int protoIdx = Utils.byte2Short_2(t_dexMethodId.protoIdx, 0);
            int nameIdx = Utils.byte2Int_4(t_dexMethodId.nameIdx, 0);

            String pri = "";
            pri += ("class:" + getTypeStringById(classIdx) + ", proto:" + getProtoStringById(protoIdx) + ", name:" + getStringById(nameIdx));
            System.out.println(pri);

            dexType.dexMethodId.add(t_dexMethodId);
            dexType.dexMethodList.add(getStringById(nameIdx));
        }
    }

    private void readDexClassDefsId(int pOff, int pCount) {
        System.out.println("------------ClassDefs----------------");
        for (int i = 0; i < pCount; i++) {
            DexType.DexClassDef t_dexClassDef = dexType.new DexClassDef();
            System.arraycopy(byteData, pOff + i * 32 + 0, t_dexClassDef.classIdx, 0, t_dexClassDef.classIdx.length);
            System.arraycopy(byteData, pOff + i * 32 + 4, t_dexClassDef.accessFlags, 0, t_dexClassDef.accessFlags.length);
            System.arraycopy(byteData, pOff + i * 32 + 8, t_dexClassDef.superclassIdx, 0, t_dexClassDef.superclassIdx.length);
            System.arraycopy(byteData, pOff + i * 32 + 12, t_dexClassDef.interfacesOff, 0, t_dexClassDef.interfacesOff.length);
            System.arraycopy(byteData, pOff + i * 32 + 16, t_dexClassDef.sourceFileIdx, 0, t_dexClassDef.sourceFileIdx.length);
            System.arraycopy(byteData, pOff + i * 32 + 20, t_dexClassDef.annotationsOff, 0, t_dexClassDef.annotationsOff.length);
            System.arraycopy(byteData, pOff + i * 32 + 24, t_dexClassDef.classDataOff, 0, t_dexClassDef.classDataOff.length);
            System.arraycopy(byteData, pOff + i * 32 + 28, t_dexClassDef.staticValuesOff, 0, t_dexClassDef.staticValuesOff.length);

            int classIdx = Utils.byte2Int_4(t_dexClassDef.classIdx, 0);
            int accessFlags = Utils.byte2Int_4(t_dexClassDef.accessFlags, 0);
            int superclassIdx = Utils.byte2Int_4(t_dexClassDef.superclassIdx, 0);
            int interfacesOff = Utils.byte2Int_4(t_dexClassDef.interfacesOff, 0);
            int sourceFileIdx = Utils.byte2Int_4(t_dexClassDef.sourceFileIdx, 0);
            int annotationsOff = Utils.byte2Int_4(t_dexClassDef.annotationsOff, 0);
            int classDataOff = Utils.byte2Int_4(t_dexClassDef.classDataOff, 0);
            int staticValuesOff = Utils.byte2Int_4(t_dexClassDef.staticValuesOff, 0);

            String pri = "classIdx: " + getTypeStringById(classIdx) + "\r\naccessFlags: " + accessFlags + "\r\nsuperclassIdx: "
                    + getTypeStringById(superclassIdx) + "\r\ninterfacesOff: ";
            pri += (interfacesOff + "\r\nsourceFileIdx: " + getStringById(sourceFileIdx) + "\r\nannotationsOff: " + annotationsOff + "\r\nclassDataOff: ");
            pri += (classDataOff + "\r\nstaticValuesOff: " + staticValuesOff);
            System.out.println(pri);

            if (classDataOff == 0) {
                continue;
            }

            int off = 0;
            Utils.RETULEB128 retuleb128 = null;

            retuleb128 = Utils.readULEB128(byteData, classDataOff + off);
            int static_fields_size = retuleb128.retValue;
            off += retuleb128.readSize;

            retuleb128 = Utils.readULEB128(byteData, classDataOff + off);
            int instance_fields_size = retuleb128.retValue;
            off += retuleb128.readSize;

            retuleb128 = Utils.readULEB128(byteData, classDataOff + off);
            int direct_methods_size = retuleb128.retValue;
            off += retuleb128.readSize;

            retuleb128 = Utils.readULEB128(byteData, classDataOff + off);
            int virtual_methods_size = retuleb128.retValue;
            off += retuleb128.readSize;

            System.out.println("static_fields_size:" + static_fields_size + ", ");
            for (int j = 0; j < static_fields_size; j++) {
                retuleb128 = Utils.readULEB128(byteData, classDataOff + off);
                int field_idx_diff = retuleb128.retValue;
                off += retuleb128.readSize;

                retuleb128 = Utils.readULEB128(byteData, classDataOff + off);
                int access_flags = retuleb128.retValue;
                off += retuleb128.readSize;

                System.out.println("\t" + getFieldStringById(field_idx_diff) + ", " + access_flags);
            }

            System.out.println("instance_fields_size:" + instance_fields_size + ", ");
            for (int j = 0; j < static_fields_size; j++) {
                retuleb128 = Utils.readULEB128(byteData, classDataOff + off);
                int field_idx_diff = retuleb128.retValue;
                off += retuleb128.readSize;

                retuleb128 = Utils.readULEB128(byteData, classDataOff + off);
                int access_flags = retuleb128.retValue;
                off += retuleb128.readSize;

                System.out.println("\t" + getFieldStringById(field_idx_diff) + ", " + access_flags);
            }

            System.out.println("direct_methods_size:" + direct_methods_size + ", ");
            for (int j = 0; j < direct_methods_size; j++) {
                retuleb128 = Utils.readULEB128(byteData, classDataOff + off);
                int method_idx_diff = retuleb128.retValue;
                off += retuleb128.readSize;

                retuleb128 = Utils.readULEB128(byteData, classDataOff + off);
                int access_flags = retuleb128.retValue;
                off += retuleb128.readSize;

                retuleb128 = Utils.readULEB128(byteData, classDataOff + off);
                int code_off = retuleb128.retValue;
                off += retuleb128.readSize;

                if (code_off < 1)
                    continue;

                System.out.println("\t" + getMethodStringById(method_idx_diff) + ", " + access_flags);
                DexType.DexCodeItem t_dexCodeItem = dexType.new DexCodeItem(byteData, code_off);
            }

            System.out.println("virtual_methods_size:" + virtual_methods_size + ", ");
            for (int j = 0; j < virtual_methods_size; j++) {
                retuleb128 = Utils.readULEB128(byteData, classDataOff + off);
                int method_idx_diff = retuleb128.retValue;
                off += retuleb128.readSize;

                retuleb128 = Utils.readULEB128(byteData, classDataOff + off);
                int access_flags = retuleb128.retValue;
                off += retuleb128.readSize;

                retuleb128 = Utils.readULEB128(byteData, classDataOff + off);
                int code_off = retuleb128.retValue;
                off += retuleb128.readSize;

                if (code_off < 1)
                    continue;

                System.out.println("\t" + getMethodStringById(method_idx_diff) + ", " + access_flags);
                DexType.DexCodeItem t_dexCodeItem = dexType.new DexCodeItem(byteData, code_off);
            }
        }
    }

    public static void getOpcodeByIns2(byte[] insns){
        //代码位置org.jf.dexlib2.dexbacked.instruction.DexBackedInstruction  public static Instruction readFrom(DexBackedDexFile dexFile, DexReader reader) {
        Opcode opcode = null;
        for (int insnsPosition=0; insnsPosition<insns.length; insnsPosition += (opcode.format.size/2*2)){
            try{
                int opcodeValue =  (insns[insnsPosition] & 255);
                if (opcodeValue == 0) {
                    opcodeValue = (insns[insnsPosition] & 255) | ((insns[insnsPosition + 1] & 255) << 8);
                }
                opcode = Opcodes.forApi(35).getOpcodeByValue(opcodeValue);
                byte[] tmp = new byte[opcode.format.size/2*2];
                System.arraycopy(insns, insnsPosition, tmp, 0, tmp.length);

                System.out.println("\t\t" + opcode.name + " : " + Utils.bytes2HexString(tmp));
                if (opcode.format.size < 1)
                    break;
            }catch (Exception ex){
                break;
            }
        }
    }


}
