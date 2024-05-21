package com.york.media.opengl.utils;

public class LogUtil {

    private static String TAG = "Log";

    public static void i(String msg) {
        android.util.Log.i(TAG, msg);
    }

    public static void d(String msg) {
        android.util.Log.d(TAG, msg);
    }

    public static void e(String msg) {
        android.util.Log.e(TAG, msg);
    }

}
