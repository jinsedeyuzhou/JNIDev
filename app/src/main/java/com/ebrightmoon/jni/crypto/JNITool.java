package com.ebrightmoon.jni.crypto;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;

/**
 * Time: 2019-12-18
 * Author:wyy
 * Description:
 */
public class JNITool {

    static {
        System.loadLibrary("crypto");
    }

    public static native String cusEncrypt(String encrypt, int length);

    public static native String cusDecrypt(String decrypt, int length);

    /**
     * AES 加密
     *
     * @param bytes
     * @return
     */
    public static native String aesEncrypt(byte[] bytes);

    /**
     * AES 解密
     *
     * @param str
     * @return
     */
    public static native byte[] aesDecrypt(String str);


    public static native String desencrypt(byte[] bytes);

    public static native byte[] desdecrypt(String str);

    public static native String rsaencrypt(byte[] bytes);

    public static native byte[] rsadecrypt(String str);

    /**
     * 密码进行MD5 加盐进行两次md5
     *
     * @param str
     * @return
     */
    public static native String pwdMD5(String str);

    /**
     * 使用的是AES 加密 密钥在C中也可以。通过网络获取RSA公钥。
     * 加密
     *
     * @param str
     * @param type 0 AES 1 DES 2 RSA 4 自定义
     * @return
     */
    public static String encrypt(String str, int type) {
        String temp = null;
        switch (type) {
            case 0:
                temp = aesEncrypt(str.getBytes());
                break;
            case 1:
                temp = desencrypt(str.getBytes());
                break;
            case 2:
                temp = rsaencrypt(str.getBytes());
                break;
            case 3:
                temp = cusEncrypt(str, 18);
                break;

        }
        return temp;
    }

    /**
     * 解密
     *
     * @param type 0 AES 1 DES 2 RSA 4 自定义
     * @param str
     * @return
     */
    public static String decrypt(String str, int type) {
        byte[] temp = null;
        switch (type) {
            case 0:
                temp = aesDecrypt(str);
                break;
            case 1:
                temp = desdecrypt(str);
                break;
            case 2:
                temp = rsadecrypt(str);
                break;
            case 3:
                temp = cusDecrypt(str, 18).getBytes();
                break;

        }
        return new String(temp);
    }

    /**
     * 获取签名 获取签名hashcode  用来在so库进行对比
     */

    public static int getSignature(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            Signature sign = packageInfo.signatures[0];
            return sign.hashCode();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }


    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     * 获取唯一值
     */
    public  native String getUUID(Context context);
}
