package com.learn.opengl.demo.bitmap;

import android.content.Context;
import android.util.AttributeSet;

import com.learn.opengl.demo.bitmap.YBitmapRender;
import com.learn.opengl.egl.YGLSurfaceView;

public class YBitmapSurfaceView extends YGLSurfaceView {

    public YBitmapSurfaceView(Context context) {
        this(context, null);
    }

    public YBitmapSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        com.learn.opengl.demo.bitmap.YBitmapRender yBitmapRender = new YBitmapRender(context);
        setRender(yBitmapRender);
    }
}
