#version 130

in vec3		position;
in vec3		color;
//in vec2 texCoordIn; // incoming texcoord from the texcoord array
//out vec2 texCoord; // outgoing interpolated texcoord to fragshader
out vec3	outColor;
//uniform mat4 projectionMatrix; 


void main() 
{
	gl_Position = gl_ModelViewProjectionMatrix*gl_Vertex;
	outColor = color; 
}