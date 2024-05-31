package com.learn.opengl.demo.bitmap;

import android.content.Context;
import android.util.AttributeSet;

import com.learn.opengl.demo.bitmap.YBitmapFilterRender;
import com.learn.opengl.egl.YGLSurfaceView;

public class YBitmapFilterSurfaceView extends YGLSurfaceView {

    public YBitmapFilterSurfaceView(Context context) {
        this(context, null);
    }

    public YBitmapFilterSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        com.learn.opengl.demo.bitmap.YBitmapFilterRender yBitmapFilterRender = new YBitmapFilterRender(context);
        setRender(yBitmapFilterRender);
        yBitmapFilterRender.setId(getId());
    }
}
