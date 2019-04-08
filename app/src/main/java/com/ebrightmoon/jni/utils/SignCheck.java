package com.ebrightmoon.jni.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Process;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * 作者：create by  Administrator on 2019/1/24
 * 邮箱：2315813288@qq.com
 * 获取签名文件的sha值和MD5
 * keytool -list -v -keystore *.jks
 */
public class SignCheck {

    private Context context;
    private String cer = null;
    private String realCer = null;
    private static final String TAG = "SignCheck";

    public SignCheck(Context context) {
        this.context = context;
        this.cer = getCertificateSHA1Fingerprint();
    }

    public SignCheck(Context context, String realCer) {
        this.context = context;
        this.realCer = realCer;
        this.cer = getCertificateSHA1Fingerprint();
    }

    public String getRealCer() {
        return realCer;
    }

    /**
     * 设置正确的签名
     *
     * @param realCer
     */
    public void setRealCer(String realCer) {
        this.realCer = realCer;
    }

    /**
     * 获取应用的签名
     *
     * @return
     */
    public String getCertificateSHA1Fingerprint() {
        //获取包管理器
        PackageManager pm = context.getPackageManager();

        //获取当前要获取 SHA1 值的包名，也可以用其他的包名，但需要注意，
        //在用其他包名的前提是，此方法传递的参数 Context 应该是对应包的上下文。
        String packageName = context.getPackageName();

        //返回包括在包中的签名信息
        int flags = PackageManager.GET_SIGNATURES;

        PackageInfo packageInfo = null;

        try {
            //获得包的所有内容信息类
            packageInfo = pm.getPackageInfo(packageName, flags);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        //签名信息
        Signature[] signatures = packageInfo.signatures;
        byte[] cert = signatures[0].toByteArray();

        //将签名转换为字节数组流
        InputStream input = new ByteArrayInputStream(cert);

        //证书工厂类，这个类实现了出厂合格证算法的功能
        CertificateFactory cf = null;

        try {
            cf = CertificateFactory.getInstance("X509");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //X509 证书，X.509 是一种非常通用的证书格式
        X509Certificate c = null;

        try {
            c = (X509Certificate) cf.generateCertificate(input);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String hexString = null;

        try {
            //加密算法的类，这里的参数可以使 MD4,MD5 等加密算法
            MessageDigest md = MessageDigest.getInstance("SHA1");

            //获得公钥
            byte[] publicKey = md.digest(c.getEncoded());

            //字节到十六进制的格式转换
            hexString = byte2HexFormatted(publicKey);

        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (CertificateEncodingException e) {
            e.printStackTrace();
        }
        return hexString;
    }

    //这里是将获取到得编码进行16 进制转换
    private String byte2HexFormatted(byte[] arr) {

        StringBuilder str = new StringBuilder(arr.length * 2);

        for (int i = 0; i <arr.length; i++) {
            String h = Integer.toHexString(arr[i]);
            int l =h.length();
            if (l == 1)
                h = "0" + h;
            if (l > 2)
                h = h.substring(l - 2, l);
            str.append(h.toUpperCase());
            if (i < (arr.length - 1))
                str.append(':');
        }
        return str.toString();
    }

    /**
     * 检测签名是否正确
     * @return true 签名正常 false 签名不正常
     */
    public boolean check() {

        if (this.realCer != null) {
            cer = cer.trim();
            realCer = realCer.trim();
            if (this.cer.equals(this.realCer)) {
                return true;
            }
        }else {
            Log.e(TAG, "未给定真实的签名 SHA-1 值");
        }
        return false;
    }

    /**
     * 通过检查apk包的MD5摘要值来判断代码文件是否被篡改
     *
     * @param orginalMD5 原始Apk包的MD5值
     */
    public static void apkVerifyWithMD5(Context context, String orginalMD5) {
        String apkPath = context.getPackageCodePath(); // 获取Apk包存储路径
        try {
            MessageDigest dexDigest = MessageDigest.getInstance("MD5");
            byte[] bytes = new byte[1024];
            int byteCount;
            FileInputStream fis = new FileInputStream(new File(apkPath)); // 读取apk文件
            while ((byteCount = fis.read(bytes)) != -1) {
                dexDigest.update(bytes, 0, byteCount);
            }
            BigInteger bigInteger = new BigInteger(1, dexDigest.digest()); // 计算apk文件的哈希值
            String sha = bigInteger.toString(16);
            fis.close();
            if (!sha.equals(orginalMD5)) { // 将得到的哈希值与原始的哈希值进行比较校验
                Process.killProcess(Process.myPid()); // 验证失败则退出程序
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 通过检查签名文件classes.dex文件的哈希值来判断代码文件是否被篡改
     *
     * @param orginalSHA 原始Apk包的SHA-1值
     */
    public static void apkVerifyWithSHA(Context context, String orginalSHA) {
        String apkPath = context.getPackageCodePath(); // 获取Apk包存储路径
        try {
            MessageDigest dexDigest = MessageDigest.getInstance("SHA-1");
            byte[] bytes = new byte[1024];
            int byteCount;
            FileInputStream fis = new FileInputStream(new File(apkPath)); // 读取apk文件
            while ((byteCount = fis.read(bytes)) != -1) {
                dexDigest.update(bytes, 0, byteCount);
            }
            BigInteger bigInteger = new BigInteger(1, dexDigest.digest()); // 计算apk文件的哈希值
            String sha = bigInteger.toString(16);
            fis.close();
            if (!sha.equals(orginalSHA)) { // 将得到的哈希值与原始的哈希值进行比较校验
                Process.killProcess(Process.myPid()); // 验证失败则退出程序
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过检查classes.dex文件的CRC32摘要值来判断文件是否被篡改
     *
     * @param orginalCRC 原始classes.dex文件的CRC值
     */
    public static void apkVerifyWithCRC(Context context, String orginalCRC) {
        String apkPath = context.getPackageCodePath(); // 获取Apk包存储路径
        try {
            ZipFile zipFile = new ZipFile(apkPath);
            ZipEntry dexEntry = zipFile.getEntry("classes.dex"); // 读取ZIP包中的classes.dex文件
            String dexCRC = String.valueOf(dexEntry.getCrc()); // 得到classes.dex文件的CRC值
            if (!dexCRC.equals(orginalCRC)) { // 将得到的CRC值与原始的CRC值进行比较校验
                Process.killProcess(Process.myPid()); // 验证失败则退出程序
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
