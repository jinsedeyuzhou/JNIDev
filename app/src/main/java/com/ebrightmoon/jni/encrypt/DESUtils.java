package com.ebrightmoon.jni.encrypt;

import android.util.Base64;

import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * 需要加解密顺序一致
 */
public class DESUtils {
    public static final String ALGORITHM = "DES";
    public static final String CODE_TYPE = "UTF-8";

    public static String decryptBASE64(String data) throws Exception {
        return new String(Base64.decode(data, Base64.DEFAULT), "UTF-8");
    }

    public static byte[] decryptBase64(String data) throws Exception {
        return Base64.decode(data, Base64.DEFAULT);
    }

    public static String decryptBase64(byte[] data) throws Exception {
        return new String(Base64.decode(data, Base64.DEFAULT), "UTF-8");
    }

    public static byte[] encryptBASE64(String data) throws Exception {
        return Base64.encode(data.getBytes("UTF-8"), Base64.DEFAULT);
    }

    public static String encryptBase64(byte[] data) throws Exception {
        return new String(Base64.encode(data, Base64.DEFAULT),"UTF-8");
    }

    public static String decrypt(String data, String key) throws Exception {
        // DES算法要求有一个可信任的随机数源
        SecureRandom random = new SecureRandom();
        DESKeySpec dks = new DESKeySpec(key.getBytes(CODE_TYPE));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        SecretKey secretKey = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, random);
        return decryptBase64(cipher.doFinal(Base64.decode(data, Base64.DEFAULT)));
    }

    public static String encrypt(String data, String key) throws Exception {
        SecureRandom random = new SecureRandom();
        DESKeySpec dks = new DESKeySpec(key.getBytes(CODE_TYPE));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        SecretKey secretKey = keyFactory.generateSecret(dks);
        //Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        //用密匙初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, random);
        byte[] bytes = cipher.doFinal(encryptBASE64(data));
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }


    /**
     * DES加密
     *
     * @param datasource
     * @return
     */
    public static String encode(String datasource, String key) {
        try {
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(key.getBytes(CODE_TYPE));
            //创建一个密匙工厂，然后用它把DESKeySpec转换成
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            //Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance("DES");
            //用密匙初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
            //现在，获取数据并加密
            return encryptBase64(cipher.doFinal(datasource.getBytes()));
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * DES解密
     *
     * @return
     */
    public static String decode(String src, String key) throws Exception {
        // DES算法要求有一个可信任的随机数源
        SecureRandom random = new SecureRandom();
        // 创建一个DESKeySpec对象
        DESKeySpec desKey = new DESKeySpec(key.getBytes(CODE_TYPE));
        // 创建一个密匙工厂
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        // 将DESKeySpec对象转换成SecretKey对象
        SecretKey securekey = keyFactory.generateSecret(desKey);
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance("DES");
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, random);
        // 真正开始解密操作
        return new String(cipher.doFinal(decryptBase64(src)), "UTF-8");
    }

}
