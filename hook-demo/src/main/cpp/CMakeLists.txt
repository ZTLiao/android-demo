cmake_minimum_required(VERSION 3.4.1)

project("hookdemo")

enable_language(C ASM)
if(CMAKE_SYSTEM_PROCESSOR MATCHES "aarch")
    set_source_files_properties(raw_syscall64.s PROPERTIES COMPILE_FLAGS "-x assembler-with-cpp")
    add_library( # Sets the name of the library.
            hookdemo                                   # c/cpp代码将要编译成为so库的名称，java代码加载库文件要用这个名称
            SHARED                                      # 动态库
            raw_syscall64.s
            native-lib.cpp  # cpp代码文件路径 可以在下面接着随意添加c、c++文件
    )
elseif(CMAKE_SYSTEM_PROCESSOR MATCHES "arm")
    set_source_files_properties(raw_syscall32.s PROPERTIES COMPILE_FLAGS "-x assembler-with-cpp")
    add_library( # Sets the name of the library.
            hookdemo                                   # c/cpp代码将要编译成为so库的名称，java代码加载库文件要用这个名称
            SHARED                                      # 动态库
            raw_syscall32.s
            native-lib.cpp  # cpp代码文件路径 可以在下面接着随意添加c、c++文件
    )
endif ()

#设置生成的so动态库最后输出的路径
set(CMAKE_LIBRARY_OUTPUT_DIRECTORY ${PROJECT_SOURCE_DIR}/libs/${ANDROID_ABI})

#.h文件目录
include_directories(
        ${CMAKE_SOURCE_DIR}/src/main/cpp/include #h文件目录
)

#add_library( # Sets the name of the library.
#        hookdemo                                   # c/cpp代码将要编译成为so库的名称，java代码加载库文件要用这个名称
#        SHARED                                      # 动态库
#        native-lib.cpp  # cpp代码文件路径 可以在下面接着随意添加c、c++文件
#)

find_library(   # 调用系统库
        log-lib # 打印用
        log)

target_link_libraries( # Specifies the target library.
        hookdemo
        ${log-lib})