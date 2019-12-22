package com.ebrightmoon.jni.test;



import com.ebrightmoon.jni.shell.ApkShellUtils;
import com.ebrightmoon.jni.shell.FileUtils;



public class Main {

    private static String primaryApkPath = "/Users/wyy/AndroidProject/JNIDev/demo/app-debug.apk";
    private static String outShellApkPath = "/Users/wyy/AndroidProject/JNIDev/demo/debug.apk";
    private static String unShellApkPath = "/Users/wyy/AndroidProject/JNIDev/demo/app-arm7-debug.apk";
    public static void main(String[] args) {
        try {
            if(!FileUtils.isExit(primaryApkPath, unShellApkPath)){
                System.out.println("file is null");
                return;
            }
//            FileUtils.delete("/Users/wyy/AndroidProject/JNIDev/demo");
//            FileUtils.delete("/Users/wyy/AndroidProject/JNIDev/demo");
//            FileUtils.delete(outShellApkPath);
            ApkShellUtils.apkShell(primaryApkPath,unShellApkPath,outShellApkPath);
//            FileUtils.unZip(primaryApkPath,"G:\\demo\\shell\\demo");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
