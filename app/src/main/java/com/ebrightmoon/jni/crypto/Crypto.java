package com.ebrightmoon.jni.crypto;

/**
 * 自定义加密
 */
public class Crypto {

    static {
        System.loadLibrary("crypto");
    }

    public static native String encrypt(String encrypt,int length);

    public static native String decrypt(String decrypt,int length);


}
