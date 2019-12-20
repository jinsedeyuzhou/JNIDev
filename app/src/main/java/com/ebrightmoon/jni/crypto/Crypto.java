package com.ebrightmoon.jni.crypto;


public class Crypto {

    static {
        System.loadLibrary("crypto");
    }

    public native String encrypt(String encrypt,int length);

    public native String decrypt(String decrypt,int length);

    public native String encode(String encrypt, int length);

    public native String decode(String decrypt, int length);


}
