precision lowp float;

uniform float angle;
 
varying vec2 v_texCoord; 
varying vec4 vColor;
varying vec4 vPos;

uniform sampler2D u_samplerTexture; 

void main() 
{ 
	float curAngle = atan(vPos.y, vPos.x);
	if (curAngle < angle)
		discard;
	gl_FragColor = texture2D(u_samplerTexture, v_texCoord); 
} 