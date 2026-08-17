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
#    That binary was built with a plain cross-gcc (not the NDK). The Android
#    linker does not process the DT_ANDROID_REL entries such a toolchain emits,
#    relocations stay unapplied and the program crashes. Use the NDK clang/lld
#    (this Makefile); for maximum device compatibility also -static is provided.

ifeq ($(OS),Windows_NT)
    NDK_PATH := /cygdrive/c/Program Files (x86)/Android/AndroidNDK/android-ndk-r23c/toolchains/llvm/prebuilt/windows-x86_64
    CC       := $(NDK_PATH)/bin/clang
else
    NDK_PATH := /root/Android/Sdk/ndk/30.0.15729638/toolchains/llvm/prebuilt/linux-x86_64
    CC       := $(NDK_PATH)/bin/clang
endif

TARGET := aarch64-linux-android31

# Android has required PIE executables since API 21; lld is the NDK's only
# supported linker.
CFLAGS  := --target=$(TARGET) -fPIE
LDFLAGS := -fuse-ld=lld -pie

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