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

public class YBitmapRender implements YGLSurfaceView.YGLRender {

    private final Context mContext;
    private final FloatBuffer vertexBuffer;
    private final FloatBuffer fragmentBuffer;
    private int program;
    private int vPosition;
    private int fPosition;
    private int[] textureIds;
    private int vboID;


    //顶点坐标
    float[] vertexData = {
            -1f, -1f,
            1f, -1f,
            -1f, 1f,
            1f, 1f
    };

    //纹理坐标
    float[] fragmentData = {
            0f, 1f,
            1f, 1f,
            0f, 0f,
            1f, 0f
    };
    float[] fragmentData1 = {
            0f, 0f,
            2f, 0f,
            0f, 1f,
            2f, 1f
    };

    public YBitmapRender(Context context) {
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
        /*OpenGLES 2.0获取属性的方法，3.0在着色器中用layout关键字直接为属性指定了位置
        //从渲染程序中得到着顶点色器中的属性
        vPosition = GLES30.glGetAttribLocation(program, "vPosition");
        //从渲染程序中得到片元着色器中的属性
        fPosition = GLES30.glGetAttribLocation(program, "fPosition");*/

        /* VBO */
        //创建 VBO
        int[] vbo = new int[1];
        GLES30.glGenBuffers(1, vbo, 0);
        vboID = vbo[0];
        //绑定 VBO
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, vboID);

        //分配 VBO需要的缓存大小
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, vertexData.length * 4 + fragmentData.length * 4, null, GLES30.GL_STATIC_DRAW);
        //设置顶点坐标数据的值到 VBO
        GLES30.glBufferSubData(GLES30.GL_ARRAY_BUFFER, 0, vertexData.length * 4, vertexBuffer);
        //设置纹理坐标数据的值到 VBO
        GLES30.glBufferSubData(GLES30.GL_ARRAY_BUFFER, vertexData.length * 4, fragmentData.length * 4, fragmentBuffer);
        //解绑 VBO，离开对 VBO的配置，进入下一个状态
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, 0);


        //创建 1个纹理,放入到 int [] textureIds, 一共有 30多个 纹理
        textureIds = new int[1];
        GLES30.glGenTextures(1, textureIds, 0);//第三个参数是指从哪儿开始取
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textureIds[0]);

        //设置纹理的环绕方式
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_S, GLES30.GL_REPEAT);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_T, GLES30.GL_REPEAT);
        //设置纹理的过滤方式
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_LINEAR);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_LINEAR);

        //离开对纹理的配置，进入下一个状态
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
        GLES30.glClearColor(1f, 0f, 0f, 1f);
        //使用着色器源程序
        GLES30.glUseProgram(program);

        //开始使用 VBO
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, vboID);
        //使能顶点属性数组，使之有效
        GLES30.glEnableVertexAttribArray(0);
        //使能之后，为顶点属性赋值，从VBO里获取 绑定顶点坐标; 注意：最后一个参数如果是 vertexBuffer，那么就没有用到 VBO，那就还是从CPU里取顶点
        GLES30.glVertexAttribPointer(0, 2, GLES30.GL_FLOAT, false, 2 * 4, 0);
        //使能片元属性数组，使之有效
        GLES30.glEnableVertexAttribArray(1);
        //使能之后，为片元属性赋值，从VBO里获取 绑定纹理坐标; 注意：最后一个参数为 VBO里的偏移量
        GLES30.glVertexAttribPointer(1, 2, GLES30.GL_FLOAT, false, 2 * 4, vertexData.length * 4);
        //退出 VBO的使用
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, 0);

        //要开始绘制纹理了，激活纹理 0号， 之所以激活 0号，是因为在没设置点的情况下默认是 0号
        GLES30.glActiveTexture(GLES30.GL_TEXTURE0);
        //绑定 textureIds[0] 到已激活的 2D纹理 GL_TEXTURE0上
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textureIds[0]);
        //获取图片的 bitmap
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.wallpaper);
        //绑定 bitmap 到textureIds[0]纹理
        GLUtils.texImage2D(GLES30.GL_TEXTURE_2D, 0, bitmap, 0);
        TextureUtils.loadBitmapTexture(bitmap);

        bitmap.recycle();//用完及时回收
        //绘制图形
        GLES30.glDrawArrays(GLES30.GL_TRIANGLE_STRIP, 0, 4);
        //解绑 2D纹理
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, 0);
    }
}
