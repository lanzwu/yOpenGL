package com.york.media.opengl.demo.bitmap;

import android.content.Context;
import android.util.AttributeSet;

import com.york.media.opengl.egl.YGLSurfaceView;

public class YBitmapOrthogonalSurface extends YGLSurfaceView {

    public YBitmapOrthogonalSurface(Context context) {
        this(context, null);
    }

    public YBitmapOrthogonalSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
        YBitmapOrthogonalRender yBitmapOrthogonalRender = new YBitmapOrthogonalRender(context);
        setRender(yBitmapOrthogonalRender);
    }
}
