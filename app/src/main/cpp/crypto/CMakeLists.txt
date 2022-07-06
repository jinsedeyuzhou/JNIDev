# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.
#指定需要CMAKE的最小版本
cmake_minimum_required(VERSION 3.4.1)

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.
# 设定编译类型为debug，因为在调试时需要选择debug
set(CMAKE_BUILD_TYPE Debug)
#设定编译类型为release，因为在发布时需要选择release
#set(CMAKE_BUILD_TYPE release)
#C 的编译选项是 CMAKE_C_FLAGS
# 指定编译参数，可选
SET(CMAKE_CXX_FLAGS "-Wno-error=format-security -Wno-error=pointer-sign")

#设置生成的so动态库最后输出的路径
set(CMAKE_LIBRARY_OUTPUT_DIRECTORY ${PROJECT_SOURCE_DIR}/../jniLibs/${ANDROID_ABI})

set(crypto_srcs
        crypto.c
        jni_tool.cpp
        md5.cpp
        aes.c
        base64.c
        memory-util.cpp)

add_library( # Sets the name of the library.
        crypto

        # Sets the library as a shared library.
        SHARED

        # 只能加载一个 JNI_OnLoad 方法
        # Provides a relative path to your source file(s).
        ${crypto_srcs}
        )

# Searches for a specified prebuilt library and stores the path as a
# variable. Because CMake includes system libraries in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.
# 查找系统库
find_library( # Sets the name of the path variable.
        log-lib

        # Specifies the name of the NDK library that
        # you want CMake to locate.
        log)

# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in this
# build script, prebuilt third-party libraries, or system libraries.

target_link_libraries( # Specifies the target library.
        crypto

        # Links the target library to the log library
        # included in the NDK.
        ${log-lib})


