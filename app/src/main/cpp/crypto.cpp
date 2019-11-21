#include <jni.h>
#include <string>


extern "C"
JNIEXPORT jstring JNICALL
Java_com_ebrightmoon_jni_crypto_Crypto_encrypt(JNIEnv *env, jobject instance, jstring encrypt_) {
    const char *encrypt = env->GetStringUTFChars(encrypt_, 0);
    // TODO
    env->ReleaseStringUTFChars(encrypt_, encrypt);
    std::string hello = "Hello from Cdfasf  adssf";
    return env->NewStringUTF(hello.c_str());
}


extern "C"

JNIEXPORT jstring JNICALL
Java_com_ebrightmoon_jni_crypto_Crypto_decrypt(JNIEnv *env, jobject instance, jstring decrypt_) {
    const char *decrypt = env->GetStringUTFChars(decrypt_, 0);
    // TODO
    env->ReleaseStringUTFChars(decrypt_, decrypt);
    std::string hello = "Hello from Cdfasf  dsaf";

    return env->NewStringUTF(hello.c_str());
}