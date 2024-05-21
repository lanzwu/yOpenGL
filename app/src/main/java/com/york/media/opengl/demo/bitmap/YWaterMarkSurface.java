package com.york.media.opengl.demo.bitmap;

import android.content.Context;
import android.util.AttributeSet;

import com.york.media.opengl.egl.YGLSurfaceView;

public class YWaterMarkSurface extends YGLSurfaceView {
    public YWaterMarkSurface(Context context) {
        this(context, null);
    }

    public YWaterMarkSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
        YWaterMarkRender yWaterMarkRender=new YWaterMarkRender(context);
        setRender(yWaterMarkRender);
    }
}
