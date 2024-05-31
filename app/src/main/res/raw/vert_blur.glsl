#version 300 es
layout (location = 0) in vec4 vPosition;
//新增的接收纹理坐标的变量
layout (location = 1) in vec2 fPosition;
//纹理坐标输出给片段着色器使用
out vec2 TexCoord;

const int GAUSSIAN_SAMPLES = 9;
out vec2 blurCoordinates[GAUSSIAN_SAMPLES];

void main() {
    //直接把传入的坐标值作为传入渲染管线。gl_Position是OpenGL内置的
    gl_Position = vPosition;
    //纹理坐标传给片段着色器
    TexCoord = fPosition;

    //横向和纵向的步长
    vec2 widthStep = vec2(10.0/1080.0, 0.0);
    vec2 heightStep = vec2(0.0, 10.0/1920.0);
    //计算出当前片段相邻像素的纹理坐标
    blurCoordinates[0] = TexCoord.xy - heightStep - widthStep; // 左上
    blurCoordinates[1] = TexCoord.xy - heightStep; // 上
    blurCoordinates[2] = TexCoord.xy - heightStep + widthStep; // 右上
    blurCoordinates[3] = TexCoord.xy - widthStep; // 左中
    blurCoordinates[4] = TexCoord.xy; // 中
    blurCoordinates[5] = TexCoord.xy + widthStep; // 右中
    blurCoordinates[6] = TexCoord.xy + heightStep - widthStep; // 左下
    blurCoordinates[7] = TexCoord.xy + heightStep; // 下
    blurCoordinates[8] = TexCoord.xy + heightStep + widthStep; //右下
}
