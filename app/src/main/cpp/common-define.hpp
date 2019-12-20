#ifndef common_define_hpp
#define common_define_hpp

#include <jni.h>
#include <cstdlib>
#include <string>
#include <android/log.h>

#define  LOG_TAG    "NativeLog"
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
#define  LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG,LOG_TAG,__VA_ARGS__)
#define  LOGW(...)  __android_log_print(ANDROID_LOG_WARN,LOG_TAG,__VA_ARGS__)
#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)							

#define UTF_8 "UTF-8"

#endif
