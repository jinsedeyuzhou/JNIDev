package com.ebrightmoon.jni.utils;

import android.os.Debug;

public class DebugUtil {

    private DebugUtil()
    {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public  static  boolean isDebuging()
    {
        return Debug.isDebuggerConnected();
    }

}
