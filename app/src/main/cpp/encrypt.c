#include <jni.h>
#include <string.h>
#include <malloc.h>

//将java字符串转换为c语言字符串（工具方法）
char *Jstring2CStr(JNIEnv *env, jstring jstr) {
    char *rtn = NULL;
    jclass clsstring = (*env)->FindClass(env, "java/lang/String");
    jstring strencode = (*env)->NewStringUTF(env, "GB2312");
    jmethodID mid = (*env)->GetMethodID(env, clsstring, "getBytes", "(Ljava/lang/String;)[B");
    jbyteArray barr = (jbyteArray) (*env)->CallObjectMethod(env, jstr, mid,
                                                            strencode); // String .getByte("GB2312");
    jsize alen = (*env)->GetArrayLength(env, barr);
    jbyte *ba = (*env)->GetByteArrayElements(env, barr, JNI_FALSE);
    if (alen > 0) {
        rtn = (char *) malloc(alen + 1); //"\0" memcpy(rtn,ba,alen);
        rtn[alen] = 0;
    }
    (*env)->ReleaseByteArrayElements(env, barr, ba, 0); //
    return rtn;
}

JNIEXPORT jstring JNICALL Java_com_ebrightmoon_jnidev_crypto_Crypto_encode
        (JNIEnv *env, jobject obj, jstring text, jint length) {
    char *cstr = Jstring2CStr(env, text);
    int i;
    for (i = 0; i < length; i++) {
        *(cstr + i) += 11; //加密算法，将字符串每个字符加1 }
    }
    return (*env)->NewStringUTF(env, cstr);
}


JNIEXPORT jstring JNICALL Java_com_ebrightmoon_jnidev_crypto_Crypto_decode
        (JNIEnv *env, jobject obj, jstring text, jint length) {
    char *cstr = Jstring2CStr(env, text);
    int i;
    for (i = 0; i < length; i++) {
        *(cstr + i) -= 11;
    }
    return (*env)->NewStringUTF(env, cstr);
}
