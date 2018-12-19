package com.ebrightmoon.jnidev.crypto;


public class Crypto {

    static {
        System.loadLibrary("Crypto");
    }

    public  native String encrypt(String encrypt);
    public   native String decrypt(String decrypt);
}
