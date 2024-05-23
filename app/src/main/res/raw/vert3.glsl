#version 300 es
layout (location = 0) in vec4 vPosition;
//新增的接收纹理坐标的变量
layout (location = 1) in vec2 fPosition;
//纹理坐标输出给片段着色器使用
out vec2 TexCoord;

void main() {
    //直接把传入的坐标值作为传入渲染管线。gl_Position是OpenGL内置的
    gl_Position = vPosition;
    //纹理坐标传给片段着色器
    TexCoord = fPosition;
}
