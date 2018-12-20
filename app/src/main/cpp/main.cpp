#include "main.hpp"

extern "C" JNIEXPORT jstring JNICALL Java_com_ebrightmoon_jnidev_crypto_Crypto_stringFromJNI(
        JNIEnv *env,
        jobject thiz,
        jobject argContext) {

    jstring imei = getAppendedString(env, thiz,
                                     getDeviceId(env, thiz, argContext),
                                     getSerialNumber(env, thiz, argContext));
    jstring sign = getPublicKey(env, thiz, argContext);
    jstring imei_sign = getAppendedString(env, thiz, imei, sign);
    jstring imei_sign_package = getAppendedString(env, thiz,
                                                  imei_sign,
                                                  getPackageName(env, thiz, argContext));
    //请再加入自己的移位或替换 或其他加密算法,例如我又append了一次imei
    imei_sign_package = getAppendedString(env, thiz,
                                          imei_sign_package,
                                          imei);
    return getMD5(env, imei_sign_package);
}
