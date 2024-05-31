#version 300 es
precision mediump float;

in vec2 TexCoord;
out vec4 FragColor;
uniform sampler2D sTexture;

const lowp int GAUSSIAN_SAMPLES = 9;
in highp vec2 blurCoordinates[GAUSSIAN_SAMPLES];

//高斯滤波的卷积核
mat3 kernelMatrix = mat3(
0.0947416f, 0.118318f, 0.0947416f,
0.118318f,  0.147761f, 0.118318f,
0.0947416f, 0.118318f, 0.0947416f
);


void main() {
    lowp vec3 sum = (texture(sTexture, blurCoordinates[0]).rgb * kernelMatrix[0][0]);
    sum += (texture(sTexture, blurCoordinates[1]).rgb * kernelMatrix[0][1]);
    sum += (texture(sTexture, blurCoordinates[2]).rgb * kernelMatrix[0][2]);
    sum += (texture(sTexture, blurCoordinates[3]).rgb * kernelMatrix[1][0]);
    sum += (texture(sTexture, blurCoordinates[4]).rgb * kernelMatrix[1][1]);
    sum += (texture(sTexture, blurCoordinates[5]).rgb * kernelMatrix[1][2]);
    sum += (texture(sTexture, blurCoordinates[6]).rgb * kernelMatrix[2][0]);
    sum += (texture(sTexture, blurCoordinates[7]).rgb * kernelMatrix[2][1]);
    sum += (texture(sTexture, blurCoordinates[8]).rgb * kernelMatrix[2][2]);
    FragColor = vec4(sum, 1.0);
}
