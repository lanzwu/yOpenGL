#version 300 es
//顶点坐标变量
layout (location = 0) in vec4 vPosition;
//新增的接收纹理坐标的变量
layout (location = 1) in vec2 fPosition;
//矩阵
layout (location = 2) uniform mat4 u_Matrix;
//纹理坐标输出给片段着色器使用
out vec2 TexCoord;

void main() {
    //直接把传入的坐标值作为传入渲染管线。gl_Position是OpenGL内置的
    gl_Position = u_Matrix * vPosition;
    //纹理坐标传给片段着色器
    TexCoord = fPosition;
}
