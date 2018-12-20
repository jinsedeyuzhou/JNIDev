/**
 * 头文件用于include其他文件  和   消除CPP文件内的函数的顺序问题（即先连接）
 */
#ifndef Crypto_util_hpp
#define Crypto_util_hpp

#include "common-define.hpp"

jstring toHex(JNIEnv *env, jbyteArray digested_bytes);//toHex 在CPP文件里可以放在任何位置

#endif
