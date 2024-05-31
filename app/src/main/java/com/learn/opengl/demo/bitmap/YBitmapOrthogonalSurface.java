package com.learn.opengl.demo.bitmap;

import android.content.Context;
import android.util.AttributeSet;

import com.learn.opengl.demo.bitmap.YBitmapOrthogonalRender;
import com.learn.opengl.egl.YGLSurfaceView;

public class YBitmapOrthogonalSurface extends YGLSurfaceView {

    public YBitmapOrthogonalSurface(Context context) {
        this(context, null);
    }

    public YBitmapOrthogonalSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
        com.learn.opengl.demo.bitmap.YBitmapOrthogonalRender yBitmapOrthogonalRender = new YBitmapOrthogonalRender(context);
        setRender(yBitmapOrthogonalRender);
    }
}
