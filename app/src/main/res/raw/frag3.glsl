#version 300 es
//精度限定为mediump
precision mediump float;
//新增的接收纹理坐标的变量
in vec2 TexCoord;
out vec4 FragColor;
//传入的纹理
uniform sampler2D ourTexture;

void main() {
    //texture方法执行具体的采样
    FragColor = texture(ourTexture, TexCoord);
}