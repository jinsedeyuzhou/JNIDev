# CMake

### 1、单个so库生成
略
### 2、多个so库生成
1. 直接修改CMakeLists.txt,如下所示
```

    cmake_minimum_required(VERSION 3.4.1)


    SET(CMAKE_CXX_FLAGS "-Wno-error=format-security -Wno-error=pointer-sign")

    set(CMAKE_LIBRARY_OUTPUT_DIRECTORY ${PROJECT_SOURCE_DIR}/../jniLibs/${ANDROID_ABI})


    add_library( # Sets the name of the library.
            crypto

            SHARED

            crypto.c
            jni_tool.cpp
            md5.cpp
            aes.c
            base64.c
            memory-util.cpp
            )


    find_library( # Sets the name of the path variable.
            log-lib

            log)


    target_link_libraries( # Specifies the target library.
            crypto

            ${log-lib})


    add_library( # Sets the name of the library.
            calculator

            SHARED

            calculator.c
            )

    target_link_libraries( # Specifies the target library.
            calculator

            ${log-lib})
```
2. 新建两个目录分别为crypto和calculator,并在目录下分别新建CMakeLists.txt，在jni 目录下CMakeLists.txt 修改如下
```

```
