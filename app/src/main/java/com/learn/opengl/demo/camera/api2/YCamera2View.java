package com.learn.opengl.demo.camera.api2;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.util.AttributeSet;

import com.learn.opengl.demo.camera.api2.YCamera2;
import com.learn.opengl.egl.YGLSurfaceView;
import com.learn.opengl.demo.camera.YCameraRender;
import com.learn.opengl.utils.DisplayUtil;


public class YCamera2View extends YGLSurfaceView {

    private com.learn.opengl.demo.camera.api2.YCamera2 yCamera2;
    private final YCameraRender yCameraRender;

    public YCamera2View(Context context) {
        this(context, null);
    }

    public YCamera2View(Context context, AttributeSet attrs) {
        super(context, attrs);
        int width = DisplayUtil.getScreenWidth(context);
        int height = DisplayUtil.getScreenHeight(context);
        yCamera2 = new YCamera2( context);
        yCameraRender = new YCameraRender(context, width, height);
        setRender(yCameraRender);
        yCameraRender.setOnSurfaceCreateListener(new YCameraRender.OnSurfaceCreateListener() {
            @Override
            public void onSurfaceCreate(SurfaceTexture surfaceTexture, int textureID) {
                yCamera2.initCamera(surfaceTexture, yCameraRender);
            }
        });
    }

    public void onDestroy() {
        yCamera2.onDestroy();
        yCamera2 = null;
    }
}
