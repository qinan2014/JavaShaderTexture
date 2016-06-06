precision lowp float;
 
varying vec2 v_texCoord; 
varying vec4 vColor;

uniform sampler2D u_samplerTexture; 

void main() 
{ 
	gl_FragColor = texture2D(u_samplerTexture, v_texCoord); 
} 