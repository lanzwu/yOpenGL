package com.learn.opengl.demo.bitmap;

import android.content.Context;
import android.util.AttributeSet;

import com.learn.opengl.demo.bitmap.MixRender;
import com.learn.opengl.egl.YGLSurfaceView;

public class MixSurfaceView extends YGLSurfaceView {

    public MixSurfaceView(Context context) {
        this(context, null);
    }

    public MixSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        com.learn.opengl.demo.bitmap.MixRender yVboRender = new MixRender(context);
        setRender(yVboRender);
    }
}
