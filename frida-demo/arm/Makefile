# Cross-compile AArch64 (Android) executables with the Android NDK (clang/lld).
#
# The two errors you saw:
#
# 1) "posix_spawn failed: Exec format error" (clang link step)
#    clang tries to exec its linker bin/ld.lld. NDK ships the linker and the
#    aarch64-*-* tools as SYMLINKS. If the NDK zip was extracted with a tool
#    that does not preserve symlinks (Windows extractor, some GUI archive
#    managers, or a filesystem without symlink support), ld.lld becomes an
#    empty/garbage file and execve() returns "Exec format error".
#    Re-extract the NDK on Kali preserving symlinks, e.g.:
#        cd /root/Android/Sdk/ndk
#        unzip android-ndk-r27-linux.zip
#    then verify with:   make check
#
# 2) "unused DT entry: type 0x6fffe000/.../0x6fffe003" + Segmentation fault (on device)
#    Those tags are Android packed relocations (DT_ANDROID_REL) which modern
#    NDK lld emits by default. This device's linker is too old (Android < 6.0)
#    to process them, so relocations are never applied and the program
#    segfaults. Fix: link with --pack-dyn-relocs=none (emit plain DT_RELA)
#    or, for maximum compatibility, use a fully static binary
#    (make run_static), which does not touch the device linker at all.

ifeq ($(OS),Windows_NT)
    NDK_PATH := /cygdrive/c/Program Files (x86)/Android/AndroidNDK/android-ndk-r23c/toolchains/llvm/prebuilt/windows-x86_64
    CC       := $(NDK_PATH)/bin/clang
else
    NDK_PATH := /root/Android/Sdk/ndk/27.0.12077973/toolchains/llvm/prebuilt/linux-x86_64
    CC       := $(NDK_PATH)/bin/clang
endif

TARGET := aarch64-linux-android31

# Android has required PIE executables since API 21; lld is the NDK's only
# supported linker.
# --pack-dyn-relocs=none / -z nopack-relative-relocs: use plain DT_RELA
# instead of Android packed relocs, so old (< Android 6.0) device linkers
# can apply them instead of crashing.
CFLAGS  := --target=$(TARGET) -fPIE
LDFLAGS := -fuse-ld=lld -pie -Wl,--pack-dyn-relocs=none -Wl,-z,nopack-relative-relocs

.PHONY: check hello hello_arm hello_arm_static add all run run_static clean

check:                 # verify the NDK toolchain can actually run its linker
	@echo "CC       = $(CC)"
	@echo "NDK_PATH = $(NDK_PATH)"
	file -L $(NDK_PATH)/bin/ld.lld
	$(CC) -fuse-ld=lld -Wl,--version

hello.i:
	$(CC) $(CFLAGS) -E hello.c -o hello.i

hello.s: hello.i
	$(CC) $(CFLAGS) -S hello.i -o hello.s

hello.o: hello.s
	$(CC) $(CFLAGS) -c hello.s -o hello.o

hello:
	$(CC) $(CFLAGS) -c hello.c -o hello.o
	$(CC) $(CFLAGS) -c add.c -o add.o
	$(CC) $(CFLAGS) $(LDFLAGS) add.o hello.o -o hello

hello_arm:             # PIE executable linked against bionic (normal)
	$(CC) $(CFLAGS) -c hello_arm.c -o hello_arm.o
	$(CC) $(CFLAGS) $(LDFLAGS) hello_arm.o -o hello_arm

hello_arm_static:      # fully static binary, avoids the device linker entirely
	$(CC) $(CFLAGS) -c hello_arm.c -o hello_arm_s.o
	$(CC) -fuse-ld=lld -static hello_arm_s.o -o hello_arm_static

hello_arm_2:
	$(CC) $(CFLAGS) -c hello_arm_2.s -o hello_arm_2.o
	$(CC) $(CFLAGS) $(LDFLAGS) hello_arm_2.o -o hello_arm_2
	adb push hello_arm_2 /data/local/tmp/hello_arm_2
	adb shell chmod +x /data/local/tmp/hello_arm_2
	adb shell /data/local/tmp/hello_arm_2

arm_open:
	$(CC) $(CFLAGS) -c arm_open.s -o arm_open.o
	$(CC) $(CFLAGS) $(LDFLAGS) arm_open.o -o arm_open
	adb push arm_open /data/local/tmp/arm_open /data/local/tmp/test.txt 123456
	adb shell chmod +x /data/local/tmp/arm_open
	adb shell /data/local/tmp/arm_open	

add:
	$(CC) $(CFLAGS) -c add.c -o add.o

all: hello
	adb push hello /data/local/tmp/hello
	adb shell chmod +x /data/local/tmp/hello
	adb shell /data/local/tmp/hello

run: hello_arm
	adb push hello_arm /data/local/tmp/hello_arm
	adb shell chmod +x /data/local/tmp/hello_arm
	adb shell /data/local/tmp/hello_arm

run_static: hello_arm_static
	adb push hello_arm_static /data/local/tmp/hello_arm_static
	adb shell chmod +x /data/local/tmp/hello_arm_static
	adb shell /data/local/tmp/hello_arm_static

clean:
	rm -rf *.i *.s *.o hello hello_arm hello_arm_static