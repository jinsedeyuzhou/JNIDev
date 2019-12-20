package com.ebrightmoon.jni.calculator;

/**
 * Time: 2019-11-26
 * Author:wyy
 * Description:
 */
public class Calculator {

    static {
        System.loadLibrary("crypto");
    }

    public static native int add(int a, int b);

    public static native int sub(int a, int b);

    public static native int div(int a, int b);

    public static native int mul(int a, int b);


}
