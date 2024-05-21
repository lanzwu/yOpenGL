package com.york.media.opengl.demo.empty;

import android.content.Context;
import android.util.AttributeSet;

import com.york.media.opengl.egl.YGLSurfaceView;

public class YEmptySurfaceView extends YGLSurfaceView {

    public YEmptySurfaceView(Context context) {
        this(context, null);
    }

    public YEmptySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        YEmptyRender yEmptyRender = new YEmptyRender();
        setRender(yEmptyRender);

    }
}
