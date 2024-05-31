package com.learn.opengl.demo.bitmap;

import android.content.Context;
import android.util.AttributeSet;

import com.learn.opengl.demo.bitmap.YWaterMarkRender;
import com.learn.opengl.egl.YGLSurfaceView;

public class YWaterMarkSurface extends YGLSurfaceView {
    public YWaterMarkSurface(Context context) {
        this(context, null);
    }

    public YWaterMarkSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
        com.learn.opengl.demo.bitmap.YWaterMarkRender yWaterMarkRender=new YWaterMarkRender(context);
        setRender(yWaterMarkRender);
    }
}
