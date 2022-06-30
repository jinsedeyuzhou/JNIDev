package com.ebrightmoon.jni.shell;

import java.io.IOException;

/**
 * 使用该工具必须配置好java环境
 */
public class SignUtils {
    private static final String ZIPALIGN_PATH = "tools/zipalign";
    private static final String OUT_PATH = "/Users/wyy/AndroidProject/JNIDev/demo/zipalign.apk";
    private static final String APKSIGNER_PATH = "tools/apksigner.jar";
    public static void V1(String apkPath,Keystore keystore) throws IOException, InterruptedException {
        String cmd = "jarsigner -verbose -keystore %s -storepass %s -keypass %s %s %s -digestalg SHA1 -sigalg SHA1withRSA";
        cmd = String.format(cmd, (Object) new String[]{keystore.getPath(),keystore.getAliasPassword(),keystore.getPassword(),apkPath,keystore.getAliasName()});
        CmdExecutor.executor(cmd);
    }

    /**
     * 注意：您必须在应用编译过程中的两个特定时间点之一使用 zipalign，具体在哪个时间点使用，取决于您所使用的应用签名工具：
     *
     * 如果您使用的是 apksigner，则只能在为 APK 文件签名之前执行 zipalign。如果您在使用 apksigner 为 APK 签名之后对 APK 进行进一步更改，则签名将会失效。
     * 如果您使用的是 jarsigner，则只能在为 APK 文件签名之后执行 zipalign。
     * @param apkPath
     * @param keystore
     * @throws IOException
     * @throws InterruptedException
     */
    public static void V2(String apkPath,Keystore keystore) throws IOException, InterruptedException {
        String cmd = "java -jar %s sign --ks  %s  --ks-key-alias %s --ks-pass pass:%s --key-pass pass:%s --out %s %s";
        cmd = String.format(cmd, (Object) new String[]{APKSIGNER_PATH,keystore.getPath(),keystore.getAliasName(),keystore.getPassword(),keystore.getAliasPassword(),apkPath,apkPath});
        CmdExecutor.executor(cmd);
    }

    /**
     * 需要配置环境变量
     * @param apkPath
     * @throws IOException
     * @throws InterruptedException
     */
    public static void zipalign(String apkPath) throws IOException, InterruptedException {
        String cmd = "%s -v 4 %s %s";
        cmd = String.format(cmd, (Object) new String[]{ZIPALIGN_PATH,apkPath,OUT_PATH});
        CmdExecutor.executor(cmd);
    }

    /**
     * 需要配置环境变量
     * @throws IOException
     * @throws InterruptedException
     */
    public static void verifyZipalign() throws IOException, InterruptedException {
        String cmd = "%s -c -v 4 %s";
        cmd = String.format(cmd, (Object) new String[]{ZIPALIGN_PATH,OUT_PATH});
        CmdExecutor.executor(cmd);
    }

    public static Keystore getDefaultKeystore(){
        Keystore keystore = new Keystore();
        keystore.setPath("tools/jni.jks");
        keystore.setName("jni");
        keystore.setAliasName("jnis");
        keystore.setPassword("123456");
        keystore.setAliasPassword("123456");
        return keystore;
    }

    public static class Keystore {
        private String path;
        private String name;
        private String password;
        private String aliasName;
        private String aliasPassword;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getAliasName() {
            return aliasName;
        }

        public void setAliasName(String aliasName) {
            this.aliasName = aliasName;
        }

        public String getAliasPassword() {
            return aliasPassword;
        }

        public void setAliasPassword(String aliasPassword) {
            this.aliasPassword = aliasPassword;
        }
    }
}
