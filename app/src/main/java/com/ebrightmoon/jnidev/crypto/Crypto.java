package com.ebrightmoon.jnidev.crypto;


import android.content.Context;

public class Crypto {

    static {
        System.loadLibrary("Crypto");
    }


    public native String stringFromJNI(Context context);

    public native String encrypt(String encrypt);

    public native String decrypt(String decrypt);

    public native String encode(String encrypt, int length);

    public native String decode(String decrypt, int length);


}
