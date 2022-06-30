package com.ebrightmoon.jni.encrypt;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class DESUtils {
    public static final String ALGORITHM = "DES";

    public static String decryptBASE64(String key) throws Exception {
        return new String(Base64.decode(key, Base64.DEFAULT));
    }

    public static byte[] encryptBASE64(String data) throws Exception {
        return Base64.encode(data.getBytes("GBK"), Base64.DEFAULT);
    }



    public static String decrypt(String data, String key) throws Exception {
        DESKeySpec dks = new DESKeySpec(key.getBytes());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        SecretKey secretKey = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return new String(Base64.decode(cipher.doFinal(Base64.decode(data, Base64.DEFAULT)),Base64.DEFAULT));
    }

    public static String encrypt(String data, String key) throws Exception {
        DESKeySpec dks = new DESKeySpec(key.getBytes());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        SecretKey secretKey = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] bytes = cipher.doFinal(encryptBASE64(data));
        return  Base64.encodeToString(bytes, Base64.DEFAULT);
    }


}
