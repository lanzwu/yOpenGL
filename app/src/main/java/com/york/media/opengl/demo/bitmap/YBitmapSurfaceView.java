package com.york.media.opengl.demo.bitmap;

import android.content.Context;
import android.util.AttributeSet;

import com.york.media.opengl.egl.YGLSurfaceView;

public class YBitmapSurfaceView extends YGLSurfaceView {

    public YBitmapSurfaceView(Context context) {
        this(context, null);
    }

    public YBitmapSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        YBitmapRender yBitmapRender = new YBitmapRender(context);
        setRender(yBitmapRender);
    }
}
