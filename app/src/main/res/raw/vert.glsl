#version 300 es
//顶点坐标变量,布局限定符layout,顶点着色器输入可以用布局限定符声明，
//以显式绑定着色器源代码中的位置，而不需要调用 API glGetAttribLocation
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
