#version 300 es
//精度限定为mediump
precision mediump float;
//新增的接收纹理坐标的变量
in vec2 TexCoord;
out vec4 FragColor;
//传入的纹理,如果当前的渲染只需要一个纹理单元的情况下，OpenGL会默认我们使用的是第一个纹理单元，
//所以片段着色器声明的sampler2D对象就会默认赋值为0, 0对应着和GL_TEXTURE0的纹理关联
uniform sampler2D sTexture;

void main() {
    //texture 为内置的采样函数，TexCoord 为顶点着色器传进来的纹理坐标
    //根据纹理坐标对纹理进行采样，输出采样的 rgba 值（4维向量）
    FragColor = texture(sTexture, TexCoord);
}