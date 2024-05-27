package com.york.media.opengl.demo.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.GLUtils;
import android.opengl.Matrix;

import com.york.media.opengl.R;
import com.york.media.opengl.egl.YGLSurfaceView;
import com.york.media.opengl.egl.YShaderUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * desc   : OpenGL VBO 即顶点缓冲对象 ，目的是提高顶点坐标获取的效率
 * 不使用 VBO时，每次绘制（ glDrawArrays ）图形时都是从本地内存处获取顶点数据然后传输给 OpenGL来绘制，这样就会频繁的操作 CPU->GPU增大开销，从而降低效率。
 * 使用 VBO时，能把顶点数据缓存到GPU开辟的一段内存中，然后使用时不必再从本地获取，而是直接从显存中获取，这样就能提升绘制的效率。
 */
public class MixRender implements YGLSurfaceView.YGLRender {

    private final Context mContext;
    private final FloatBuffer vertexBuffer;
    private final FloatBuffer fragmentBuffer;
    private int program;
    private int[] textureIds;
    private final float[] matrix = new float[16];


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


    public MixRender(Context context) {
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
        String vertexSource = YShaderUtil.getRawResource(mContext, R.raw.vert_matrix);
        //加载片元着色器 shader
        String fragmentSource = YShaderUtil.getRawResource(mContext, R.raw.frag_2_texture);
        //获取源程序
        program = YShaderUtil.createProgram(vertexSource, fragmentSource);

        //创建 VBO
        int[] vbo = new int[1];
        GLES30.glGenBuffers(1, vbo, 0);
        int vboID = vbo[0];

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, vboID);
        //分配 VBO需要的缓存大小
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, vertexData.length * 4 + fragmentData.length * 4, null, GLES30.GL_STATIC_DRAW);
        //设置顶点坐标数据的值到 VBO
        GLES30.glBufferSubData(GLES30.GL_ARRAY_BUFFER, 0, vertexData.length * 4, vertexBuffer);
        //设置纹理坐标数据的值到 VBO
        GLES30.glBufferSubData(GLES30.GL_ARRAY_BUFFER, vertexData.length * 4, fragmentData.length * 4, fragmentBuffer);
        //使能顶点属性数组，使之有效
        GLES30.glEnableVertexAttribArray(0);
        //使能之后，为顶点属性赋值，从VBO里获取 绑定顶点坐标; 注意：最后一个参数如果是 vertexBuffer，那么就没有用到 VBO，那就还是从CPU里取顶点
        GLES30.glVertexAttribPointer(0, 2, GLES30.GL_FLOAT, false, 8, 0);
        //使能片元属性数组，使之有效
        GLES30.glEnableVertexAttribArray(1);
        //使能之后，为片元属性赋值，从VBO里获取 绑定纹理坐标; 最后一个参数为 VBO里的偏移量
        GLES30.glVertexAttribPointer(1, 2, GLES30.GL_FLOAT, false, 8, vertexData.length * 4);
        //解绑 VBO，离开对 VBO的配置，进入下一个状态
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, 0);

        textureIds = new int[3];

        //创建1个纹理对象
        GLES30.glGenTextures(1, textureIds, 0);
        GLES30.glActiveTexture(GLES30.GL_TEXTURE0);
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textureIds[0]);
        //设置纹理的环绕方式
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_S, GLES30.GL_REPEAT);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_T, GLES30.GL_REPEAT);
        //设置纹理的过滤方式
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_LINEAR);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_LINEAR);
        //获取到的图片数据传给纹理对象
        Bitmap bitmap1 = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.grass);
        GLUtils.texImage2D(GLES30.GL_TEXTURE_2D, 0, bitmap1, 0);
        bitmap1.recycle();
        //解绑纹理,离开对纹理的配置，进入下一个状态
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, 0);

        //创建第二个纹理对象
        GLES30.glGenTextures(1, textureIds, 1);
        GLES30.glActiveTexture(GLES30.GL_TEXTURE1);
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textureIds[1]);
        //设置纹理的环绕方式
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_S, GLES30.GL_REPEAT);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_T, GLES30.GL_REPEAT);
        //设置纹理的过滤方式
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_LINEAR);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_LINEAR);
        //获取到的图片数据传给纹理对象
        Bitmap bitmap2 = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.durt);
        GLUtils.texImage2D(GLES30.GL_TEXTURE_2D, 0, bitmap2, 0);
        bitmap2.recycle();
        //解绑纹理,离开对纹理的配置，进入下一个状态
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, 0);

        //创建第二个纹理对象
        GLES30.glGenTextures(1, textureIds, 2);
        GLES30.glActiveTexture(GLES30.GL_TEXTURE2);
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textureIds[2]);
        //设置纹理的环绕方式
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_S, GLES30.GL_REPEAT);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_T, GLES30.GL_REPEAT);
        //设置纹理的过滤方式
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_LINEAR);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_LINEAR);
        //获取到的图片数据传给纹理对象
        Bitmap bitmap3 = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.noise);
        GLUtils.texImage2D(GLES30.GL_TEXTURE_2D, 0, bitmap3, 0);
        bitmap3.recycle();
        //解绑纹理,离开对纹理的配置，进入下一个状态
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, 0);
    }

    @Override
    public void onSurfaceChanged(int width, int height) {
        //设置窗口大小
        GLES30.glViewport(0, 0, width, height);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(mContext.getResources(), R.drawable.noise, options);
        int imageHeight = options.outHeight;
        int imageWidth = options.outWidth;

        if (width > height) {
            Matrix.orthoM(matrix, 0,
                    -width / ((height / (imageHeight * 1f)) * imageWidth * 1f), width / ((height / (imageHeight * 1f)) * imageWidth * 1f),
                    -1f, 1f,
                    -1f, 1f);
        } else {
            Matrix.orthoM(matrix, 0,
                    -1, 1,
                    -height / ((width / (imageWidth * 1f)) * imageHeight * 1f), height / ((width / (imageWidth * 1f)) * imageHeight * 1f),
                    -1f, 1f);
        }
    }

    @Override
    public void onDrawFrame() {
        //清除屏幕，此处用的是红色
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT);
        GLES30.glClearColor(1f, 0f, 0f, 1f);
        //使用着色器源程序
        GLES30.glUseProgram(program);

        GLES20.glUniformMatrix4fv(2, 1, false, matrix, 0);

        //指定哪个采样器对应哪个纹理单元，glUniform1i代表一个数字和int类型
        GLES30.glUniform1i(GLES30.glGetUniformLocation(program, "Texture0"), 0);
        GLES30.glUniform1i(GLES30.glGetUniformLocation(program, "Texture1"), 1);
        GLES30.glUniform1i(GLES30.glGetUniformLocation(program, "noise"), 2);

        //激活纹理单元0号
        GLES30.glActiveTexture(GLES30.GL_TEXTURE0);
        //绑定 bitmapTexture 到纹理对象0
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textureIds[0]);

        //激活纹理单元1号
        GLES30.glActiveTexture(GLES30.GL_TEXTURE1);
        //绑定 bitmapTexture 到纹理对象1
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textureIds[1]);

        //激活纹理单元2号
        GLES30.glActiveTexture(GLES30.GL_TEXTURE2);
        //绑定 bitmapTexture 到纹理对象1
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textureIds[2]);

        //绘制
        GLES30.glDrawArrays(GLES30.GL_TRIANGLE_STRIP, 0, 4);
        //解绑 2D纹理，退出对纹理的使用
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, 0);
    }

}
