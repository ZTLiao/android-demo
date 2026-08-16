hello.i:
	export PATH="$$PATH:/cygdrive/c/Program Files (x86)/Android/AndroidNDK/android-ndk-r23c/toolchains/llvm/prebuilt/windows-x86_64/bin"
	clang -target armv7a-linux-androideabi31 -E hello.c -o hello.i

hello.s:
	export PATH="$$PATH:/cygdrive/c/Program Files (x86)/Android/AndroidNDK/android-ndk-r23c/toolchains/llvm/prebuilt/windows-x86_64/bin"
	clang -target armv7a-linux-androideabi31 -E hello.c -o hello.i
	clang -target armv7a-linux-androideabi31 -S hello.i -o hello.s

hello.o:
	export PATH="$$PATH:/cygdrive/c/Program Files (x86)/Android/AndroidNDK/android-ndk-r23c/toolchains/llvm/prebuilt/windows-x86_64/bin"
	clang -target armv7a-linux-androideabi31 -E hello.c -o hello.i
	clang -target armv7a-linux-androideabi31 -S hello.i -o hello.s
	clang -target armv7a-linux-androideabi31 -c hello.s -o hello.o

hello:
	export PATH="$$PATH:/cygdrive/c/Program Files (x86)/Android/AndroidNDK/android-ndk-r23c/toolchains/llvm/prebuilt/windows-x86_64/bin"
	clang -target armv7a-linux-androideabi31 -E hello.c -o hello.i
	clang -target armv7a-linux-androideabi31 -S hello.i -o hello.s
	clang -target armv7a-linux-androideabi31 -c hello.c -o hello.o
	clang -target armv7a-linux-androideabi31 -c add.c -o add.o
	clang -target armv7a-linux-androideabi31 add.o hello.o -o hello

add:
	export PATH="$$PATH:/cygdrive/c/Program Files (x86)/Android/AndroidNDK/android-ndk-r23c/toolchains/llvm/prebuilt/windows-x86_64/bin"
	clang -target armv7a-linux-androideabi31 -c add.c -o add.o

all:
	export PATH="$$PATH:/cygdrive/c/Program Files (x86)/Android/AndroidNDK/android-ndk-r23c/toolchains/llvm/prebuilt/windows-x86_64/bin"
	clang -target armv7a-linux-androideabi31 -E hello.c -o hello.i
	clang -target armv7a-linux-androideabi31 -S hello.i -o hello.s
	clang -target armv7a-linux-androideabi31 -c hello.c -o hello.o
	clang -target armv7a-linux-androideabi31 -c add.c -o add.o
	clang -target armv7a-linux-androideabi31 add.o hello.o -o hello
	adb push hello /data/local/tmp/hello
	adb shell chmod +x /data/local/tmp/hello
	adb shell /data/local/tmp/hello	

clear:
	rm -rf *.i *.s *.o hello