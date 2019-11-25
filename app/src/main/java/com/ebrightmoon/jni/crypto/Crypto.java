package com.ebrightmoon.jni.crypto;


import android.content.Context;

public class Crypto {

    static {
        System.loadLibrary("crypto");
    }


    public native String stringFromJNI();

    public native String encrypt(String encrypt);

    public native String decrypt(String decrypt);

    public native String encode(String encrypt, int length);

    public native String decode(String decrypt, int length);


}
