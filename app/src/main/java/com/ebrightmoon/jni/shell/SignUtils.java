package com.ebrightmoon.jni.shell;

import java.io.IOException;

/**
 * 使用该工具必须配置好java环境
 */
public class SignUtils {
    public static void V1(String apkPath,Keystore keystore) throws IOException, InterruptedException {
        String cmd = "jarsigner -verbose -keystore %s -storepass %s -keypass %s %s %s -digestalg SHA1 -sigalg SHA1withRSA";
        cmd = String.format(cmd, new String[]{keystore.getPath(),keystore.getAliasPassword(),keystore.getPassword(),apkPath,keystore.getAliasName()});
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
