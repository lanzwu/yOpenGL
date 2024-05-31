package com.learn.opengl.demo.shape;

import android.content.Context;
import android.util.AttributeSet;

import com.learn.opengl.demo.shape.YTriangleRender;
import com.learn.opengl.egl.YGLSurfaceView;

public class YTriangleSurface extends YGLSurfaceView {
    public YTriangleSurface(Context context) {
        this(context,null);
    }

    public YTriangleSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
        com.learn.opengl.demo.shape.YTriangleRender yTriangleRender = new YTriangleRender(context);
        setRender(yTriangleRender);
    }
}
