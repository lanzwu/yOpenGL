package com.learn.opengl.demo.shape;

import android.content.Context;
import android.util.AttributeSet;

import com.learn.opengl.demo.shape.YQuadrilateralRender;
import com.learn.opengl.egl.YGLSurfaceView;

public class YQuadrilateralSurface extends YGLSurfaceView {
    public YQuadrilateralSurface(Context context) {
        this(context,null);
    }

    public YQuadrilateralSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
        com.learn.opengl.demo.shape.YQuadrilateralRender yQuadrilateralRender = new YQuadrilateralRender(context);
        setRender(yQuadrilateralRender);
    }
}
