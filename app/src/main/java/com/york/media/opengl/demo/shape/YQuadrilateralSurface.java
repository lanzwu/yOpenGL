package com.york.media.opengl.demo.shape;

import android.content.Context;
import android.util.AttributeSet;

import com.york.media.opengl.egl.YGLSurfaceView;

public class YQuadrilateralSurface extends YGLSurfaceView {
    public YQuadrilateralSurface(Context context) {
        this(context,null);
    }

    public YQuadrilateralSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
        YQuadrilateralRender yQuadrilateralRender = new YQuadrilateralRender(context);
        setRender(yQuadrilateralRender);
    }
}
