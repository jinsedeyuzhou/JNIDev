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


    /**
     * AES 加密
     *
     * @param bytes
     * @return
     */
    private static native String jniencrypt(byte[] bytes);

    /**
     * AES 解密
     *
     * @param str
     * @return
     */
    private static native byte[] jnidecrypt(String str);


    private static native String desencrypt(byte[] bytes);

    private static native byte[] desdecrypt(String str);

    private static native String rsaencrypt(byte[] bytes);

    private static native byte[] rsadecrypt(String str);

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
        return jniencrypt(str.getBytes());
    }

    /**
     * 解密
     *
     * @param type 0 AES 1 DES 2 RSA 4 自定义
     * @param str
     * @return
     */
    public static String decrypt(String str, int type) {
        return new String(jnidecrypt(str));
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
    public native String stringFromJNI(Context context);
}
