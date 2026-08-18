import keystone
import capstone

# pip3 install keystone-engine
# pip3 install capstone

def arm2bin(arm_code):
    ks = keystone.Ks(keystone.KS_ARCH_ARM, keystone.KS_MODE_ARM)
    asm = ks.asm(arm_code, as_bytes=True)
    opcode = int.from_bytes(asm[0], "little")
    bin_code = format(opcode, "032b")
    print(arm_code, hex(opcode), "\r\n", bin_code)

def bin2arm(asm):
    cs = capstone.Cs(capstone.CS_ARCH_ARM, capstone.CS_MODE_ARM)
    for insn in cs.disasm(asm, 0):
        print(insn.address, insn.mnemonic, insn.op_str)

def int2arm(hex_int):
    cs = capstone.Cs(capstone.CS_ARCH_ARM, capstone.CS_MODE_ARM)
    for insn in cs.disasm(int.to_bytes(hex_int, 4, "little"), 0):
        print(insn.address, insn.mnemonic, insn.op_str)

arm_code = "B #0x40"
arm2bin(arm_code)

int2arm(0xEA000008)