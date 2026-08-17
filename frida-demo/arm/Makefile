ifeq ($(OS),Windows_NT)
    NDK_PATH := /cygdrive/c/Program Files (x86)/Android/AndroidNDK/android-ndk-r23c/toolchains/llvm/prebuilt/windows-x86_64
else
    NDK_PATH := /root/Android/Sdk/ndk/30.0.15729638/toolchains/llvm/prebuilt/linux-x86_64
endif

# 通用编译设置 - 使用NDK官方推荐方式
TARGET = armv7a-linux-androideabi31
CLANG = clang --target=$(TARGET) --gcc-toolchain=$(NDK_PATH) --sysroot=$(NDK_PATH)/sysroot -D__ANDROID_API__=31

hello.i:
	export PATH="$$PATH:$(NDK_PATH)/bin" && \
	$(CLANG) -E hello.c -o hello.i

hello.s:
	export PATH="$$PATH:$(NDK_PATH)/bin" && \
	$(CLANG) -E hello.c -o hello.i && \
	$(CLANG) -S hello.i -o hello.s

hello.o:
	export PATH="$$PATH:$(NDK_PATH)/bin" && \
	$(CLANG) -E hello.c -o hello.i && \
	$(CLANG) -S hello.i -o hello.s && \
	$(CLANG) -c hello.s -o hello.o

hello:
	export PATH="$$PATH:$(NDK_PATH)/bin" && \
	$(CLANG) -E hello.c -o hello.i && \
	$(CLANG) -S hello.i -o hello.s && \
	$(CLANG) -c hello.c -o hello.o && \
	$(CLANG) -c add.c -o add.o && \
	$(CLANG) add.o hello.o -o hello

hello_arm:
	export PATH="$$PATH:$(NDK_PATH)/bin" && \
	$(CLANG) -E hello_arm.c -o hello_arm.i && \
	$(CLANG) -S hello_arm.i -o hello_arm.s && \
	$(CLANG) -c hello_arm.c -o hello_arm.o && \
	$(CLANG) hello_arm.o -o hello_arm

add:
	export PATH="$$PATH:$(NDK_PATH)/bin" && \
	$(CLANG) -c add.c -o add.o

all:
	export PATH="$$PATH:$(NDK_PATH)/bin" && \
	$(CLANG) -E hello.c -o hello.i && \
	$(CLANG) -S hello.i -o hello.s && \
	$(CLANG) -c hello.c -o hello.o && \
	$(CLANG) -c add.c -o add.o && \
	$(CLANG) add.o hello.o -o hello && \
	adb push hello /data/local/tmp/hello && \
	adb shell chmod +x /data/local/tmp/hello && \
	adb shell /data/local/tmp/hello

clear:
	rm -rf *.i *.s *.o hello hello_arm