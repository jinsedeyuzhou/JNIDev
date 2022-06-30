#include <jni.h>
#include <stdio.h>
#include <stdlib.h>
#include <android/log.h>


/**
 * 动态注册函数
 * 计算器
 */
// 内部链接
static const char *kTAG = "calculator";

#define LOGI(...) ((void)__android_log_print(ANDROID_LOG_INFO, kTAG, __VA_ARGS__))
#define LOGW(...) ((void)__android_log_print(ANDROID_LOG_WARN, kTAG, __VA_ARGS__))
#define LOGE(...) ((void)__android_log_print(ANDROID_LOG_ERROR, kTAG, __VA_ARGS__))
#define LOGD(...) ((void)__android_log_print(ANDROID_LOG_DEBUG, kTAG, __VA_ARGS__))


jint addNumber(JNIEnv *env, jclass clazz, jint a, jint b) {
    return a + b;
}

jint subNumber(JNIEnv *env, jclass clazz, jint a, jint b) {
    return a - b;
}

jint mulNumber(JNIEnv *env, jclass clazz, jint a, jint b) {
    return a * b;
}

jint divNumber(JNIEnv *env, jclass clazz, jint a, jint b) {
    return a / b;
}

JNIEXPORT jint JNI_OnLoad(JavaVM *vm, void *reserved) {
    //打印日志，说明已经进来了
    LOGD("JNITag", "enter jni_onload");
    JNIEnv * env=NULL;

    //判断是否正确
    if ((*vm)->GetEnv(vm,(void**)&env,JNI_VERSION_1_6)!=JNI_OK)
    {
        return JNI_ERR;
    }

    //注册四个方法，注意签名
    const JNINativeMethod method[]={
            {"add","(II)I",(void *)addNumber},
            {"sub","(II)I",(void *)subNumber},
            {"mul","(II)I",(void *)mulNumber},
            {"div","(II)I",(void *)divNumber}
    };

    //找到对应的
    jclass jClassName=(*env)->FindClass(env,"com/ebrightmoon/jni/calculator/Calculator");

    // 开始注册
    jint ret=(*env)->RegisterNatives(env,jClassName,method, sizeof(method)/ sizeof(JNINativeMethod) );
    if (ret!=JNI_OK)
    {
        LOGD("jni-register error");
        return JNI_ERR;
    }
    return JNI_VERSION_1_6;
}

