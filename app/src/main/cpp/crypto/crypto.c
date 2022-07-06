#include <jni.h>
#include <stdio.h>
#include <stdlib.h>
#include <android/log.h>
#include <string.h>

// 内部链接
static const char *kTAG = "crypto";

#define LOGI(...) ((void)__android_log_print(ANDROID_LOG_INFO, kTAG, __VA_ARGS__))
#define LOGW(...) ((void)__android_log_print(ANDROID_LOG_WARN, kTAG, __VA_ARGS__))
#define LOGE(...) ((void)__android_log_print(ANDROID_LOG_ERROR, kTAG, __VA_ARGS__))
#define LOGD(...) ((void)__android_log_print(ANDROID_LOG_DEBUG, kTAG, __VA_ARGS__))

// String 转成char 字符
char *Jstring2CStr(JNIEnv *env, jstring jstr) {
//    const char *strs=(*env)->GetStringUTFChars(env,strs,0);
    char *rtn = NULL;
    jclass clsstring = (*env)->FindClass(env, "java/lang/String");
    jstring strencode = (*env)->NewStringUTF(env, "GB2312");
    jmethodID mid = (*env)->GetMethodID(env, clsstring, "getBytes", "(Ljava/lang/String;)[B");
    jbyteArray barr = (jbyteArray) (*env)->CallObjectMethod(env, jstr, mid,
                                                            strencode); // String .getByte("GB2312");
    jsize alen = (*env)->GetArrayLength(env, barr);
    jbyte *ba = (*env)->GetByteArrayElements(env, barr, JNI_FALSE);
    if (alen > 0) {
        rtn = (char *) malloc(alen + 1); //"\0”
        memcpy(rtn, ba, alen);
        rtn[alen] = 0;
    }
    (*env)->ReleaseByteArrayElements(env, barr, ba, 0);
    return rtn;
}

// 自定义加密
JNIEXPORT jstring JNICALL Java_com_ebrightmoon_jni_crypto_Crypto_encrypt
        (JNIEnv *env, jobject obj, jstring text, jint length) {
    char *cstr = Jstring2CStr(env, text);
    int i;
    for (i = 0; i < length; i++) {
        *(cstr + i) += 1; //加密算法，将字符串每个字符加1
    }
    return (*env)->NewStringUTF(env, cstr);
}

/**
 * Class: Java_com_ebrightmoon_jni_crypto_Crypto
 * Method: decrypt
 * Signature:(Ljava/lang/String;)Ljava/lang/String
 * 自定义解密
 */
JNIEXPORT jstring JNICALL Java_com_ebrightmoon_jni_crypto_Crypto_decrypt
        (JNIEnv *env, jobject obj, jstring text, jint length) {
    char *cstr = Jstring2CStr(env, text);
    int i;
    for (i = 0; i < length; i++) {
        *(cstr + i) -= 1;
    }
    return (*env)->NewStringUTF(env, cstr);
}


