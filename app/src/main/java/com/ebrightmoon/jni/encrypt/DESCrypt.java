package com.ebrightmoon.jni.encrypt;

import android.util.Base64;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * 作者：create by  Administrator on 2019/2/14
 * 邮箱：2315813288@qq.com
 *
 */
public class DESCrypt {
    public static final String ALGORITHM = "desede";
    public static final String ALGORITHM_DES = "desede/CBC/PKCS5Padding";
    // 密钥 长度不得小于24
    private final static String secretKey = "123456789012345678901234" ;
    // 向量 可有可无 终端后台也要约定
    private final static String iv = "01234567" ;
    // 加解密统一使用的编码方式
    private final static String encoding = "utf-8" ;

    /**
     * 3DES加密
     *
     * @param plainText
     *            普通文本
     * @return
     * @throws Exception
     */
    public static String encode(String plainText) throws Exception {
        Key deskey = null ;
        DESedeKeySpec spec = new DESedeKeySpec(secretKey .getBytes());
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance( ALGORITHM);
        deskey = keyfactory.generateSecret(spec);

        Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
        IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher. ENCRYPT_MODE , deskey, ips);
        byte [] encryptData = cipher.doFinal(plainText.getBytes(encoding ));
        return Base64.encodeToString(encryptData,Base64. DEFAULT );
    }

    /**
     * 3DES解密
     *
     * @param encryptText
     *            加密文本
     * @return
     * @throws Exception
     */
    public static String decode(String encryptText) throws Exception {
        Key deskey = null ;
        DESedeKeySpec spec = new DESedeKeySpec( secretKey.getBytes());
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance(ALGORITHM);
        deskey = keyfactory. generateSecret(spec);
        Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
        IvParameterSpec ips = new IvParameterSpec( iv.getBytes());
        cipher. init(Cipher. DECRYPT_MODE, deskey, ips);

        byte [] decryptData = cipher.doFinal(Base64. decode(encryptText, Base64. DEFAULT));

        return new String (decryptData, encoding);
    }
}

//import java.security.Key;
//
//        import javax.crypto.Cipher;
//        import javax.crypto.SecretKeyFactory;
//        import javax.crypto.spec.DESedeKeySpec;
//        import javax.crypto.spec.IvParameterSpec;
//
///**
// * 3DES加密工具类
// */
//public class Des3Util {
//    // 密钥 长度不得小于24
//    private final static String secretKey = "123456789012345678901234" ;
//    // 向量 可有可无 终端后台也要约定
//    private final static String iv = "01234567";
//    // 加解密统一使用的编码方式
//    private final static String encoding = "utf-8";
//
//    /**
//     * 3DES加密
//     *
//     * @param plainText
//     *            普通文本
//     * @return
//     * @throws Exception
//     */
//    public static String encode(String plainText) throws Exception {
//        Key deskey = null;
//        DESedeKeySpec spec = new DESedeKeySpec(secretKey .getBytes());
//        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance( "desede");
//        deskey = keyfactory.generateSecret( spec);
//
//        Cipher cipher = Cipher.getInstance( "desede/CBC/PKCS5Padding");
//        IvParameterSpec ips = new IvParameterSpec( iv.getBytes());
//        cipher.init(Cipher. ENCRYPT_MODE, deskey, ips);
//        byte[] encryptData = cipher.doFinal( plainText.getBytes( encoding));
//        return Base64. encode( encryptData);
//    }
//
//    /**
//     * 3DES解密
//     *
//     * @param encryptText
//     *            加密文本
//     * @return
//     * @throws Exception
//     */
//    public static String decode(String encryptText) throws Exception {
//        Key deskey = null;
//        DESedeKeySpec spec = new DESedeKeySpec( secretKey.getBytes());
//        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance( "desede");
//        deskey = keyfactory. generateSecret( spec);
//        Cipher cipher = Cipher.getInstance( "desede/CBC/PKCS5Padding" );
//        IvParameterSpec ips = new IvParameterSpec( iv.getBytes());
//        cipher. init(Cipher. DECRYPT_MODE, deskey, ips);
//
//        byte[] decryptData = cipher. doFinal(Base64. decode(encryptText ));
//
//        return new String( decryptData, encoding);
//    }
//
//    public static void main(String args[]) throws Exception{
//        String str = "你好" ;
//        System. out.println( "----加密前-----：" + str );
//        String encodeStr = Des3Util. encode( str);
//        System. out.println( "----加密后-----：" + encodeStr );
//        System. out.println( "----解密后-----：" + Des3Util.decode( encodeStr));
//    }
//}