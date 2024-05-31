#version 300 es
precision mediump float;

in vec2 TexCoord;
out vec4 FragColor;
uniform sampler2D sTexture;

void main() {
    vec4 src = texture(sTexture, TexCoord);
    float gray = (src.r + src.g + src.b) / 3.0;
    FragColor = vec4(gray, gray, gray, 1.0);
}
