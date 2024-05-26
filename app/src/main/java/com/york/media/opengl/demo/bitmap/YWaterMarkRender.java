package com.york.media.opengl.demo.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.GLUtils;
import android.util.Log;

import com.york.media.opengl.R;
import com.york.media.opengl.egl.TextureUtils;
import com.york.media.opengl.egl.YGLSurfaceView;
import com.york.media.opengl.egl.YShaderUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class YWaterMarkRender implements YGLSurfaceView.YGLRender {
    private final Context mContext;
    private final FloatBuffer vertexBuffer;
    private final FloatBuffer fragmentBuffer;
    private final IntBuffer indicesBuffer;
    private int program;
    private int[] textureIds;
    private int vboId;
    private int[] ebo;

    int[] vao,vbo;

    //顶点坐标
    float[] vertexData = {
            -0.5f, 0.5f,
            0.5f, 0.5f,
            -0.5f, 1f,
            0.5f, 1f,

            -0.5f, 0f,
            0.5f, 0f,
            -0.5f, 0.5f,
            0.5f, 0.5f,

            -0.5f, -1f,
            0.5f, -1f,
            -0.5f, 0.5f,
            0.5f, 0.5f,

            0f, -1f,
            1f, -1f,
            0f, -0.8f,
            1f, -0.8f
    };

    float[] vertexData1 = {
            -0.5f, 1f,
            0.5f, 1f,
            -0.5f, 0.5f,
            0.5f, 0.5f,
            -0.5f, 0f,
            0.5f, 0f,
            -0.5f, -0.5f,
            0.5f, -0.5f,
            -0.5f, -1f,
            0.5f, -1
    };

    float[] vertexData2 = {
            -1f, -1f,
            1f, -1f,
            -1f, 1f,
            1f, 1f
    };

    int[] indices = {
            0, 1, 2,
            1, 2, 3,

            2, 3, 4,
            3, 4, 5,

            4, 5, 6,
            5, 6, 7,

            6, 7, 8,
            7, 8, 9
    };

    //纹理坐标
    float[] fragmentData = {
            0f, 1f,
            1f, 1f,
            0f, 0f,
            1f, 0f

//            0f, 0f,
//            2.5f, 0f,
//            0f, 2.5f,
//            2.5f, 2.5f
    };

    public YWaterMarkRender(Context context) {
        this.mContext = context;
        //读取顶点坐标
        vertexBuffer = ByteBuffer.allocateDirect(vertexData.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer().put(vertexData);
        vertexBuffer.position(0);

        //读取纹理坐标
        fragmentBuffer = ByteBuffer.allocateDirect(fragmentData.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer().put(fragmentData);
        fragmentBuffer.position(0);

        //读取顶点索引
        indicesBuffer = ByteBuffer.allocateDirect(indices.length * 4).order(ByteOrder.nativeOrder()).asIntBuffer().put(indices);
        indicesBuffer.position(0);
    }

    @Override
    public void onSurfaceCreated() {
        //加载顶点着色器 shader
        String vertexSource = YShaderUtil.getRawResource(mContext, R.raw.vert);
        //加载片元着色器 shader
        String fragmentSource = YShaderUtil.getRawResource(mContext, R.raw.frag_1_texture);
        //获取源程序
        program = YShaderUtil.createProgram(vertexSource, fragmentSource);

        //设置文字支持透明
        GLES30.glEnable(GLES30.GL_BLEND);
        GLES30.glBlendFunc(GLES30.GL_SRC_ALPHA, GLES30.GL_ONE_MINUS_SRC_ALPHA);

//        //创建VAO
//        vao = new int[1];
//        GLES30.glGenVertexArrays(1, vao, 0);

        //创建 EBO
//        ebo = new int[1];
//        GLES30.glGenBuffers(1, ebo, 0);
//        //绑定EBO
//        GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER, ebo[0]);
//        //索引数据
//        GLES30.glBufferData(GLES30.GL_ELEMENT_ARRAY_BUFFER, indices.length * 4, indicesBuffer, GLES30.GL_STATIC_DRAW);
//        GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER, 0);


        //绑定VAO 0
        //GLES30.glBindVertexArray(vao[0]);
        //解绑VAO
        //GLES30.glBindVertexArray(0);

        /* VBO */
        //创建 VBO
        int[] vbo = new int[1];
        GLES30.glGenBuffers(1, vbo, 0);
        //绑定 VBO
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, vbo[0]);

        //分配 VBO需要的缓存大小
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, vertexData.length * 4 + fragmentData.length * 4, null, GLES30.GL_STATIC_DRAW);
        //设置顶点坐标数据的值到 VBO
        GLES30.glBufferSubData(GLES30.GL_ARRAY_BUFFER, 0, vertexData.length * 4, vertexBuffer);
        //设置纹理坐标数据的值到 VBO
        GLES30.glBufferSubData(GLES30.GL_ARRAY_BUFFER, vertexData.length * 4, fragmentData.length * 4, fragmentBuffer);

//        //使能顶点属性数组，使之有效
//        GLES30.glEnableVertexAttribArray(0);
//        //使能之后，为顶点属性赋值，从VBO里获取 绑定顶点坐标; 注意：最后一个参数如果是 vertexBuffer，那么就没有用到 VBO，那就还是从CPU里取顶点
//        GLES30.glVertexAttribPointer(0, 2, GLES30.GL_FLOAT, false, 2 * 4, 0);
//        //使能片元属性数组，使之有效
//        GLES30.glEnableVertexAttribArray(1);
//        //使能之后，为片元属性赋值，从VBO里获取 绑定纹理坐标; 注意：最后一个参数为 VBO里的偏移量
//        GLES30.glVertexAttribPointer(1, 2, GLES30.GL_FLOAT, false, 2 * 4, vertexData.length * 4);
        //解绑 VBO，离开对 VBO的配置，进入下一个状态
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, 0);


        //创建1个纹理单元
        textureIds = new int[3];
        GLES30.glGenTextures(1, textureIds, 0);//第三个参数是指从哪儿开始取
        //绑定纹理对象
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textureIds[0]);
        //设置纹理的环绕方式
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_S, GLES30.GL_REPEAT);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_T, GLES30.GL_REPEAT);
        //设置纹理的过滤方式
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_LINEAR);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_LINEAR);
        //获取图片的 bitmap
        Bitmap bitmap1 = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.wifi);
        //将bitmap数据传输 到textureIds[0]纹理
        GLUtils.texImage2D(GLES30.GL_TEXTURE_2D, 0, bitmap1, 0);
        bitmap1.recycle();//用完及时回收
        //离开对纹理的配置，进入下一个状态
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, 0);


        //创建第2个纹理单元,放入到 int [] textureIds
        GLES30.glGenTextures(1, textureIds, 1);
        GLES30.glActiveTexture(GLES30.GL_TEXTURE0);
        //绑定为 2D纹理
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textureIds[1]);
        //设置环绕模式
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_S, GLES30.GL_CLAMP_TO_EDGE);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_T, GLES30.GL_CLAMP_TO_EDGE);
        //设置过滤模式
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_LINEAR);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_LINEAR);
        //获取图片的 bitmap
        Bitmap bitmap2 = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.moto);
        //绑定 bitmap到 textureIds[1] 这个2D纹理上
        GLUtils.texImage2D(GLES30.GL_TEXTURE_2D, 0, bitmap2, 0);
        bitmap2.recycle();
        //退出 纹理的设置，进入下一环节
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, 0);


        //创建第3个纹理,放入到 int [] textureIds
        //右下角文字
        GLES30.glGenTextures(1, textureIds, 2);
        GLES30.glActiveTexture(GLES30.GL_TEXTURE0);
        //绑定为2D纹理
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textureIds[2]);
        //设置环绕模式
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_S, GLES30.GL_REPEAT);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_T, GLES30.GL_REPEAT);
        //设置过滤模式
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_LINEAR);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_LINEAR);
        Bitmap txtBitmap = TextureUtils.createTextImage("测试文字", 30, "#ffffff", "#00000000", 0);
        GLUtils.texImage2D(GLES30.GL_TEXTURE_2D, 0, txtBitmap, 0);
        txtBitmap.recycle();
        //退出纹理的设置
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

        //綁定VBO
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, vboId);

        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textureIds[0]);
        GLES30.glEnableVertexAttribArray(0);
        GLES30.glVertexAttribPointer(0, 2, GLES30.GL_FLOAT, false, 8, 0);
        GLES30.glEnableVertexAttribArray(1);
        GLES30.glVertexAttribPointer(1, 2, GLES30.GL_FLOAT, false, 8, vertexData.length * 4);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLE_STRIP, 0, 4);
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, 0);

        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textureIds[1]);
        GLES30.glEnableVertexAttribArray(0);
        GLES30.glVertexAttribPointer(0, 2, GLES30.GL_FLOAT, false, 8, 32);
        GLES30.glEnableVertexAttribArray(1);
        GLES30.glVertexAttribPointer(1, 2, GLES30.GL_FLOAT, false, 8, vertexData.length * 4);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLE_STRIP, 0, 4);
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, 0);

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, 0);

//
//        //背景
//        //绑定 textureIds[0] 到默认激活的 2D纹理 GL_TEXTURE0上
//        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textureIds[0]);
//        //从VBO中获取背景图片的坐标，并使能
//        GLES30.glEnableVertexAttribArray(0);
//        GLES30.glVertexAttribPointer(0, 2, GLES30.GL_FLOAT, false, 8, 0);
//        GLES30.glEnableVertexAttribArray(1);
//        GLES30.glVertexAttribPointer(1, 2, GLES30.GL_FLOAT, false, 8, vertexData.length * 4);
//        //绘制图片
//        GLES30.glDrawArrays(GLES30.GL_TRIANGLE_STRIP, 0, 4);
//        //解绑 2D纹理
//        //GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, 0);
//
//        //图片
//        //从VBO中获取图片水印的坐标，并使能
//        GLES30.glEnableVertexAttribArray(0);
//        GLES30.glVertexAttribPointer(0, 2, GLES30.GL_FLOAT, false, 8, 32);
//        GLES30.glEnableVertexAttribArray(1);
//        GLES30.glVertexAttribPointer(1, 2, GLES30.GL_FLOAT, false, 8, vertexData.length * 4);
//        //绑定 textureIds[1] 到默认激活的 2D纹理 GL_TEXTURE0上
//        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textureIds[1]);
//        //绘制图片
//        GLES30.glDrawArrays(GLES30.GL_TRIANGLE_STRIP, 0, 4);
//        //解绑纹理
//        //GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, 0);
//
//        //文字
//        //从VBO中获取图片水印的坐标，并使能
//        GLES30.glEnableVertexAttribArray(0);
//        GLES30.glVertexAttribPointer(0, 2, GLES30.GL_FLOAT, false, 8, 64);
//        GLES30.glEnableVertexAttribArray(1);
//        GLES30.glVertexAttribPointer(1, 2, GLES30.GL_FLOAT, false, 8, vertexData.length * 4);
//        //绑定 textureIds[2] 到默认激活的 2D纹理 GL_TEXTURE0上
//        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textureIds[2]);
//        //绘制水印
//        GLES30.glDrawArrays(GLES30.GL_TRIANGLE_STRIP, 0, 4);
//        //解绑纹理
//        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, 0);

//        //解绑 VBO
//        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, 0);
    }


}
