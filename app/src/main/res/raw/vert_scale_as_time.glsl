#version 300 es
//顶点坐标变量
layout (location = 0) in vec4 vPosition;
//新增的接收纹理坐标的变量
layout (location = 1) in vec2 fPosition;
//矩阵
layout (location = 2) uniform mat4 u_Matrix;
layout (location = 3) uniform float time;
//纹理坐标输出给片段着色器使用
out vec2 TexCoord;

void main() {
    float scale = 1.0 / time;
    vec3 position = vPosition.rgb * scale;
    gl_Position = u_Matrix * vec4(position, 1.0);
    //纹理坐标传给片段着色器
    TexCoord = fPosition;
}
