#version 300 es
//精度限定为mediump
precision mediump float;
//新增的接收纹理坐标的变量
in vec2 TexCoord;
out vec4 FragColor;

//定义两个采样器
uniform sampler2D Texture0;
uniform sampler2D Texture1;

void main() {
    //mix为OpenGL内置的函数，表示对2个数进行按比例混合叠加,最后一个参数是Texture1的比例，Texture0的比例为0.4（1-0.6）
    FragColor = mix(texture(Texture0, TexCoord), texture(Texture1, TexCoord), 0.2);
    //FragColor = texture(Texture0, TexCoord) + texture(Texture1, TexCoord);
    //FragColor = texture(Texture0, TexCoord) * texture(Texture1, TexCoord);
}