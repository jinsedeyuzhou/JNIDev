package com.ebrightmoon.jni;

import android.app.Application;

/**
 * Time: 2019-12-22
 * Author:wyy
 * Description:
 */
public class JNIApplication extends Application {
    private static JNIApplication context;

    public static JNIApplication getApplication() {
        return context;
    }

    public static void setContext(JNIApplication context) {
        JNIApplication.context = context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public JNIApplication() {
        super();
        context = this;
    }
}
