package com.learn.opengl.demo.fbo;

import android.content.Context;
import android.util.AttributeSet;

import com.learn.opengl.demo.fbo.YUsedFboRender;
import com.learn.opengl.egl.YGLSurfaceView;

public class YFboSurfaceView extends YGLSurfaceView {

    public YFboSurfaceView(Context context) {
        this(context, null);
    }

    public YFboSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        com.learn.opengl.demo.fbo.YUsedFboRender yUsedFboRender = new YUsedFboRender(context,1080,1840);
        setRender(yUsedFboRender);
    }
}