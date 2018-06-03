cmake_minimum_required(VERSION 3.4.1)

include_directories(
    ${CMAKE_SOURCE_DIR}/src/main/cpp/include #h文件目录
)
add_library( # Sets the name of the library.
             jni-lib                        #c/cpp代码将要编译成为so库的名称，java代码加载库文件要用这个名称
             SHARED
             src/main/cpp/netbase.cpp  #cpp代码文件路径 这里可以随意添加c、c++文件
              )
target_link_libraries( # Specifies the target library.
                       jni-lib
                     )