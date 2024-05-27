package com.york.media.opengl.utils;

import android.opengl.GLES30;
import android.util.Log;

public class LogUtil {

    private static final String TAG = "Log";

    public static void i(String msg) {
        Log.i(TAG, msg);
    }

    public static void d(String msg) {
        Log.d(TAG, msg);
    }

    public static void e(String msg) {
        Log.e(TAG, msg);
    }

    public static void debug(String msg) {
        String error;
        switch (GLES30.glGetError()){
            case 0:
                error = "GL_NO_ERROR";
                break;
            case 0x0500:
                error = "GL_INVALID_ENUM";
                break;
            case 0x0501:
                error = "GL_INVALID_VALUE";
                break;
            case 0x0502:
                error = "GL_INVALID_OPERATION";
                break;
            case 0x0505:
                error = "GL_OUT_OF_MEMORY";
                break;
            case 0x0506:
                error = "GL_INVALID_FRAMEBUFFER_OPERATION";
                break;
            default:
                error = String.valueOf(GLES30.glGetError());
                break;

        }
        Log.e(TAG, msg + " ERROR code--" + error);
    }

}
