package com.york.media.opengl.demo.vbo;

import android.content.Context;
import android.util.AttributeSet;

import com.york.media.opengl.demo.bitmap.YBitmapRender;
import com.york.media.opengl.egl.YGLSurfaceView;

public class YVboSurfaceView extends YGLSurfaceView {

    public YVboSurfaceView(Context context) {
        this(context, null);
    }

    public YVboSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        YVboRender yVboRender = new YVboRender(context);
        setRender(yVboRender);
    }
}
