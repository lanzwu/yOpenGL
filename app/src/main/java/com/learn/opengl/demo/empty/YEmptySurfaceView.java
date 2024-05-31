package com.learn.opengl.demo.empty;

import android.content.Context;
import android.util.AttributeSet;

import com.learn.opengl.demo.empty.YEmptyRender;
import com.learn.opengl.egl.YGLSurfaceView;

public class YEmptySurfaceView extends YGLSurfaceView {

    public YEmptySurfaceView(Context context) {
        this(context, null);
    }

    public YEmptySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        com.learn.opengl.demo.empty.YEmptyRender yEmptyRender = new YEmptyRender();
        setRender(yEmptyRender);

    }
}
