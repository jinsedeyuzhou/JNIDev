package com.ebrightmoon.jni.calculator;

/**
 * Time: 2019-11-26
 * Author:wyy
 * Description:
 * 计算器
 */
public class Calculator {

    static {
        System.loadLibrary("calculator");
    }

    public static native int add(int a, int b);

    public static native int sub(int a, int b);

    public static native int div(int a, int b);

    public static native int mul(int a, int b);


}
