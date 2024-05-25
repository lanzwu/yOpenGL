package com.york.media.opengl.demo.bitmap;

import android.content.Context;
import android.util.AttributeSet;

import com.york.media.opengl.egl.YGLSurfaceView;

public class YBitmapFilterSurfaceView extends YGLSurfaceView {

    public YBitmapFilterSurfaceView(Context context) {
        this(context, null);
    }

    public YBitmapFilterSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        YBitmapFilterRender yBitmapFilterRender = new YBitmapFilterRender(context);
        setRender(yBitmapFilterRender);
        yBitmapFilterRender.setId(getId());
    }
}
