package com.york.media.opengl.demo.bitmap;

import android.content.Context;
import android.util.AttributeSet;

import com.york.media.opengl.egl.YGLSurfaceView;

public class MixSurfaceView extends YGLSurfaceView {

    public MixSurfaceView(Context context) {
        this(context, null);
    }

    public MixSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        MixRender yVboRender = new MixRender(context);
        setRender(yVboRender);
    }
}
