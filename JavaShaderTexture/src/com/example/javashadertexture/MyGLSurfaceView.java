package com.example.javashadertexture;

import android.content.Context;


public class MyGLSurfaceView extends android.opengl.GLSurfaceView {

	public MyGLSurfaceView(Context context) {
		super(context);
		setFocusableInTouchMode(true); 
		// Tell the surface view we want to create an OpenGL ES 2.0-compatible 
		// context, and set an OpenGL ES 2.0-compatible renderer. 
		this.setEGLContextClientVersion(2); 
		this.setRenderer(new MyRender(context)); 
	}

}
