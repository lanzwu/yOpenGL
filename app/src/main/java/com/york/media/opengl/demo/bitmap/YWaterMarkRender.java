package com.york.media.opengl.demo.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES30;
import android.opengl.GLUtils;

import com.york.media.opengl.R;
import com.york.media.opengl.egl.TextureUtils;
import com.york.media.opengl.egl.YGLSurfaceView;
import com.york.media.opengl.egl.YShaderUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class YWaterMarkRender implements YGLSurfaceView.YGLRender {
    private final Context mContext;
    private final FloatBuffer vertexBuffer;
    private final FloatBuffer fragmentBuffer;
    private int program;
    private int vPosition;
    private int fPosition;
    private int[] textureIds;
    private int bitmapTextureId;
    private int textTextureId;
    private int vboId;

    //顶点坐标
    float[] vertexData = {
            -1f, -1f,
            1f, -1f,
            -1f, 1f,
            1f, 1f,

            //左上角图片水印
            -1f, 0.8f,
            -0.5f, 0.8f,
            -1f, 1f,
            -0.5f, 1f,

            //右下角文字水印
            0f, -1f,
            1f, -1f,
            0f, -0.8f,
            1f, -0.8f
    };
    //纹理坐标
    float[] fragmentData = {
            0f, 1f,
            1f, 1f,
            0f, 0f,
            1f, 0f
    };

    public YWaterMarkRender(Context context) {
        this.mContext = context;
        //读取顶点坐标
        vertexBuffer = ByteBuffer.allocateDirect(vertexData.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer().put(vertexData);
        vertexBuffer.position(0);

        //读取纹理坐标
        fragmentBuffer = ByteBuffer.allocateDirect(fragmentData.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer().put(fragmentData);
        fragmentBuffer.position(0);


    }

    @Override
    public void onSurfaceCreated() {
        //加载顶点着色器 shader
        String vertexSource = YShaderUtil.getRawResource(mContext, R.raw.vert3);
        //加载片元着色器 shader
        String fragmentSource = YShaderUtil.getRawResource(mContext, R.raw.frag_1_teture);
        //获取源程序
        program = YShaderUtil.createProgram(vertexSource, fragmentSource);

        //设置文字支持透明
        GLES30.glEnable(GLES30.GL_BLEND);
        GLES30.glBlendFunc(GLES30.GL_SRC_ALPHA, GLES30.GL_ONE_MINUS_SRC_ALPHA);

        int [] vbo_s = new int[1];
        GLES30.glGenBuffers(1, vbo_s, 0);
        vboId = vbo_s[0];

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, vboId);
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, vertexData.length * 4 + fragmentData.length * 4, null, GLES30. GL_STATIC_DRAW);
        GLES30.glBufferSubData(GLES30.GL_ARRAY_BUFFER, 0, vertexData.length * 4, vertexBuffer);
        GLES30.glBufferSubData(GLES30.GL_ARRAY_BUFFER, vertexData.length * 4, fragmentData.length * 4, fragmentBuffer);
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, 0);


        //创建 1个纹理,放入到 int [] textureIds
        textureIds = new int[1];
        GLES30.glGenTextures(1, textureIds, 0);//第三个参数是指从哪儿开始取
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textureIds[0]);
        //设置纹理的环绕方式
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_S, GLES30.GL_REPEAT);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_T, GLES30.GL_REPEAT);
        //设置纹理的过滤方式
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_LINEAR);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_LINEAR);
        //解绑纹理 指的是离开对 纹理的配置，进入下一个状态
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, 0);

        bitmapTextureId = TextureUtils.createImageTexture(mContext, R.drawable.moto);

        Bitmap txtBitmap = TextureUtils.createTextImage("测试", 30, "#ffffff", "#00000000", 0);
        textTextureId = TextureUtils.loadBitmapTexture(txtBitmap);
        txtBitmap.recycle();
    }

    @Override
    public void onSurfaceChanged(int width, int height) {
        //设置窗口大小
        GLES30.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame() {
        //清除屏幕，此处用的是红色
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT);
        GLES30.glClearColor(1f, 0f, 0f, 1f);

        //使用着色器源程序
        GLES30.glUseProgram(program);

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, vboId);

        GLES30.glEnableVertexAttribArray(0);
        GLES30.glVertexAttribPointer(0, 2, GLES30.GL_FLOAT, false, 8,0);

        GLES30.glEnableVertexAttribArray(1);
        GLES30.glVertexAttribPointer(1, 2, GLES30.GL_FLOAT, false, 8,vertexData.length * 4);

        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textureIds[0]);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLE_STRIP, 0, 4);

        //绑定 textureIds[0] 到已激活的 2D纹理 GL_TEXTURE0上
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textureIds[0]);
        //获取图片的 bitmap
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.wall);
        //绑定 bitmap 到textureIds[0]纹理
        GLUtils.texImage2D(GLES30.GL_TEXTURE_2D, 0, bitmap, 0);
        bitmap.recycle();//用完及时回收
        //绘制原图片
        GLES30.glDrawArrays(GLES30.GL_TRIANGLE_STRIP, 0, 4);
        //解绑 2D纹理
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, 0);

        //图片水印
        //从VBO中获取图片水印的坐标，并使能
        GLES30.glEnableVertexAttribArray(0);
        GLES30.glVertexAttribPointer(0, 2, GLES30.GL_FLOAT, false, 8,32);
        GLES30.glEnableVertexAttribArray(1);
        GLES30.glVertexAttribPointer(1, 2, GLES30.GL_FLOAT, false, 8,vertexData.length * 4);

        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, bitmapTextureId);
        //绘制图片
        GLES30.glDrawArrays(GLES30.GL_TRIANGLE_STRIP, 0, 4);
        //解绑纹理
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, 0);

        //文字水印
        //从VBO中获取图片水印的坐标，并使能
        GLES30.glEnableVertexAttribArray(0);
        GLES30.glVertexAttribPointer(0, 2, GLES30.GL_FLOAT, false, 8,64);
        GLES30.glEnableVertexAttribArray(1);
        GLES30.glVertexAttribPointer(1, 2, GLES30.GL_FLOAT, false, 8,vertexData.length * 4);

        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textTextureId);
        //绘制水印
        GLES30.glDrawArrays(GLES30.GL_TRIANGLE_STRIP, 0, 4);
        //解绑纹理
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, 0);


        //解绑 VBO
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, 0);
    }


}
