package com.example.javashadertexture;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;


public class MyGLSurfaceView extends android.opengl.GLSurfaceView {
	private final String Tag = "qinanMyGLSurfaceView";
	public MyGLSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setFocusableInTouchMode(true); 
		// Tell the surface view we want to create an OpenGL ES 2.0-compatible 
		// context, and set an OpenGL ES 2.0-compatible renderer. 
		this.setEGLContextClientVersion(2); 
		this.setRenderer(new MyRender(context)); 
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		final int action = event.getAction();   
	    int count = event.getPointerCount();
	    Log.e(Tag, "onTochEvent point count: " + count);
	    
		return super.onTouchEvent(event);
	}
}
