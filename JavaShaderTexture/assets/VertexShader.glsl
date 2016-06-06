
uniform mat4 uMVPMatrix;

attribute vec4 a_position; 
attribute vec2 a_texCoord; 
attribute vec4 aColor;

varying vec4 vColor;
varying vec2 v_texCoord; 

void main() 
{ 
	gl_Position = uMVPMatrix * a_position; 
	vColor = aColor;
	v_texCoord = a_texCoord; 
}