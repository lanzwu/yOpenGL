package com.learn.opengl.demo.shape;

import android.content.Context;
import android.util.AttributeSet;

import com.learn.opengl.demo.shape.YCircularRender;
import com.learn.opengl.egl.YGLSurfaceView;

public class YCircularSurface extends YGLSurfaceView {

    public YCircularSurface(Context context) {
        this(context,null);
    }

    public YCircularSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
        com.learn.opengl.demo.shape.YCircularRender yCircularRender = new YCircularRender(context);
        setRender(yCircularRender);
    }
}
