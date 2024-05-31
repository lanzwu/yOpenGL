#version 300 es
precision mediump float;

in vec2 TexCoord;
out vec4 FragColor;
uniform sampler2D sTexture;

void main() {
    FragColor = vec4(vec3(1.0 - texture(sTexture, TexCoord)), 1.0);
}
