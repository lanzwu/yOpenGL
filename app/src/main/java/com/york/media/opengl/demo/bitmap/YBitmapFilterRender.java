package com.york.media.opengl.demo.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES30;
import android.opengl.GLUtils;
import android.util.Log;

import com.york.media.opengl.R;
import com.york.media.opengl.egl.YGLSurfaceView;
import com.york.media.opengl.egl.YShaderUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class YBitmapFilterRender implements YGLSurfaceView.YGLRender {

    private final Context mContext;
    private final FloatBuffer vertexBuffer;
    private final FloatBuffer fragmentBuffer;
    private int program;
    private int []textureIds;
    private int id;


    public YBitmapFilterRender(Context context) {
        this.mContext = context;

        //顶点坐标
        float[] vertexData = {
                -1f, 1f,
                1f, 1f,
                -1f, -1f,
                1f, -1f
        };
        //读取顶点坐标
        vertexBuffer = ByteBuffer.allocateDirect(vertexData.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer().put(vertexData);
        vertexBuffer.position(0);

        //纹理坐标
        float[] fragmentData = {
                0f, 0f,
                1f, 0f,
                0f, 1f,
                1f, 1f
        };
        //读取纹理坐标
        fragmentBuffer = ByteBuffer.allocateDirect(fragmentData.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer().put(fragmentData);
        fragmentBuffer.position(0);

    }

    public void setId(int id){
        Log.d("zhou", "setID " + id);
        this.id = id;
    }

    @Override
    public void onSurfaceCreated() {
        //加载顶点着色器 shader
        String vertexSource = YShaderUtil.getRawResource(mContext, R.raw.vert_normal);
        //加载片元着色器 shader
        String fragmentSource;
        if(id == R.id.mYGLSurfaceView1){
            fragmentSource = YShaderUtil.getRawResource(mContext, R.raw.frag_filter_negative);
        } else if (id == R.id.mYGLSurfaceView2){
            fragmentSource = YShaderUtil.getRawResource(mContext, R.raw.frag_filter_grey);
        } else {
            vertexSource = YShaderUtil.getRawResource(mContext, R.raw.vert_blur);
            fragmentSource = YShaderUtil.getRawResource(mContext, R.raw.frag_filter_blur);
        }

        //获取源程序
        program = YShaderUtil.createProgram(vertexSource, fragmentSource);

        //创建 1个纹理,放入到 int [] textureIds
        textureIds = new int[1];
        GLES30.glGenTextures(1, textureIds, 0);//第三个参数是指从哪儿开始取
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textureIds[0]);

        //设置纹理的环绕方式
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_S, GLES30.GL_REPEAT);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_T, GLES30.GL_REPEAT);
        //设置纹理的过滤方式
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_NEAREST);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_LINEAR);
        //解绑纹理 指的是离开对 纹理的配置，进入下一个状态

        //获取图片的 bitmap
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.view);
        //绑定 bitmap 到textureIds[0]纹理
        GLUtils.texImage2D(GLES30.GL_TEXTURE_2D, 0, bitmap, 0);
        bitmap.recycle();//用完及时回收

        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, 0);
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
        GLES30.glClearColor(1f,0f, 0f, 1f);
        //使用着色器源程序
        GLES30.glUseProgram(program);

        //使能顶点属性数组
        GLES30.glEnableVertexAttribArray(0);
        //使能之后，为顶点属性赋值，绑定顶点坐标
        GLES30.glVertexAttribPointer(0, 2, GLES30.GL_FLOAT, false, 8, vertexBuffer);
        //使能片元属性数组
        GLES30.glEnableVertexAttribArray(1);
        //使能之后，为片元属性赋值，绑定纹理坐标
        GLES30.glVertexAttribPointer(1, 2, GLES30.GL_FLOAT, false, 8, fragmentBuffer);

        //绑定 textureIds[0] 到已激活的 2D纹理 GL_TEXTURE0上
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textureIds[0]);
        //绘制图形
        GLES30.glDrawArrays(GLES30.GL_TRIANGLE_STRIP, 0, 4);
        //解绑 2D纹理
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, 0);


    }
}
