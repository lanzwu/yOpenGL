package com.learn.opengl.demo.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES30;
import android.opengl.GLUtils;

import com.learn.opengl.R;
import com.learn.opengl.egl.YGLSurfaceView;
import com.learn.opengl.egl.YShaderUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class YBitmapRender implements YGLSurfaceView.YGLRender {

    private final Context mContext;
    private final FloatBuffer vertexBuffer;
    private final FloatBuffer fragmentBuffer;
    private int program;
    private int[] textureIds;

    //顶点坐标
    float[] vertexData = {
            -1f, 1f,
            1f, 1f,
            -1f, -1f,
            1f, -1f
    };

    //纹理坐标
    float[] fragmentData = {
            0f, 0f,
            1f, 0f,
            0f, 1f,
            1f, 1f

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
        String vertexSource = YShaderUtil.getRawResource(mContext, R.raw.vert_normal);
        //加载片元着色器 shader
        String fragmentSource = YShaderUtil.getRawResource(mContext, R.raw.frag_1_texture);
        //获取源程序
        program = YShaderUtil.createProgram(vertexSource, fragmentSource);
        /*OpenGLES 2.0获取属性的方法，3.0在着色器中用layout关键字直接为属性指定了位置
        //从渲染程序中得到着顶点色器中的属性
        vPosition = GLES30.glGetAttribLocation(program, "vPosition");
        //从渲染程序中得到片元着色器中的属性
        fPosition = GLES30.glGetAttribLocation(program, "fPosition");*/


        //使能顶点属性数组
        GLES30.glEnableVertexAttribArray(0);
        //使能之后，为顶点属性赋值
        GLES30.glVertexAttribPointer(0, 2, GLES30.GL_FLOAT, false, 2 * 4, vertexBuffer);
        //使能片元属性数组
        GLES30.glEnableVertexAttribArray(1);
        //使能之后，为片元属性赋值，从VBO里获取 绑定纹理坐标; 注意：最后一个参数为 VBO里的偏移量
        GLES30.glVertexAttribPointer(1, 2, GLES30.GL_FLOAT, false, 2 * 4, fragmentBuffer);


        //创建1个纹理单元
        textureIds = new int[1];
        GLES30.glGenTextures(1, textureIds, 0);//第三个参数是指从哪儿开始取
        //只有一个纹理单元时默认绑定到单元0号，可以不写
        //GLES30.glActiveTexture(GLES30.GL_TEXTURE0);
        //绑定纹理对象
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textureIds[0]);
        //设置纹理的环绕方式
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_S, GLES30.GL_REPEAT);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_T, GLES30.GL_REPEAT);
        //设置纹理的过滤方式
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_LINEAR);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_LINEAR);
        //获取图片的 bitmap
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.dollar);
        //将bitmap数据传输 到textureIds[0]纹理
        GLUtils.texImage2D(GLES30.GL_TEXTURE_2D, 0, bitmap, 0);
        bitmap.recycle();//用完及时回收
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

        //激活纹理
        //只有一个纹理单元时默认激活纹理单元0号，可以不写
        //GLES30.glActiveTexture(GLES30.GL_TEXTURE0);
        //绑定 textureIds[0] 到已激活的 2D纹理 GL_TEXTURE0上
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textureIds[0]);
        //绘制图形
        GLES30.glDrawArrays(GLES30.GL_TRIANGLE_STRIP, 0, 4);
        //解绑 2D纹理
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, 0);
    }
}
