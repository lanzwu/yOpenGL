#version 300
layout (location = 0) in vec4 vPosition;
layout (location = 0) in vec2 fPosition;
out vec2 ft_Position;

void main(){
    gl_Position = vPosition;
    ft_Position = fPosition;
}