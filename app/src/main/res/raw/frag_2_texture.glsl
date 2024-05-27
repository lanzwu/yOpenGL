#version 300 es
//精度限定为mediump
precision mediump float;
//新增的接收纹理坐标的变量
in vec2 TexCoord;
out vec4 FragColor;

//定义两个采样器
uniform sampler2D Texture0;
uniform sampler2D Texture1;
uniform sampler2D noise;

void main() {
    vec4 grassColor = texture(Texture0, TexCoord);
    vec4 durtColor = texture(Texture1, TexCoord);
    vec4 noiseColor = texture(noise, TexCoord);
    float weight = noiseColor.r;
    vec4 mixColor = grassColor * weight + durtColor * (1.0 - weight);
    FragColor = vec4(mixColor.rgb, 1.0);

    //FragColor = texture(Texture0, TexCoord);
    //mix为OpenGL内置的函数，表示对2个数进行按比例混合叠加,最后一个参数是Texture1的比例，Texture0的比例为0.4（1-0.6）
    //FragColor = mix(texture(Texture0, TexCoord), texture(Texture1, TexCoord), 0.5);
    //FragColor = texture(Texture0, TexCoord) + texture(Texture1, TexCoord);
    //FragColor = texture(Texture0, TexCoord) * texture(Texture1, TexCoord);
}