package com.example.javashadertexture;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;

//import Renderer;

public class MyRender implements android.opengl.GLSurfaceView.Renderer {
	private FloatBuffer vertex;
	private ShortBuffer index;
	private float[] quadVertex = new float[] { 
			-0.5f, 0.5f, 0.0f, // Position 0
			0, 1.0f, // TexCoord 0
			-0.5f, -0.5f, 0.0f, // Position 1
			0, 0, // TexCoord 1
			0.5f, -0.5f, 0.0f, // Position 2
			1.0f, 0, // TexCoord 2
			0.5f, 0.5f, 0.0f, // Position 3
			1.0f, 1.0f, // TexCoord 3
	};
	private short[] quadIndex = new short[] { 
			(short) (0), // Position 0
			(short) (1), // Position 1
			(short) (2), // Position 2
			(short) (2), // Position 2
			(short) (3), // Position 3
			(short) (0), // Position 0
	};
	private Context gAct;
	private int[] mTexture = new int[1];
	int attribPosition, attribTexCoord, uniformTexture;
	
	public MyRender(Context context) {
		gAct = context;
	}
	@Override
	public void onDrawFrame(GL10 arg0) {
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTexture[0]);
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);  
	    GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTexture[0]);  
	    vertex.position(0);  
	    // load the position  
	    // 3(x , y , z)  
	    // (2 + 3 )* 4 (float size) = 20  
	    GLES20.glVertexAttribPointer(attribPosition,   
	                                 3, GLES20.GL_FLOAT,   
	                                 false, 20, vertex);  
	   
	    vertex.position(3);  
	    // load the texture coordinate  
	    GLES20.glVertexAttribPointer(attribTexCoord,   
	                                  2, GLES20.GL_FLOAT,  
	                                  false, 20, vertex);  
	   
	    GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_SHORT, index);
	}

	@Override
	public void onSurfaceChanged(GL10 arg0, int arg1, int arg2) {	
		
	}

	@Override
	public void onSurfaceCreated(GL10 arg0, EGLConfig arg1) {
		GLES20.glEnable(GLES20.GL_TEXTURE_2D); 
		// Active the texture unit 0 
		GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
		GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		loadVertex(); 
		initShader(); 
		loadTexture();
	}

	private void loadTexture() {
		//生成纹理  
		GLES20.glGenTextures(1, mTexture, 0);  
        //加载Bitmap  
        Bitmap bitmap = loadBitmap(gAct, R.drawable.pop_9bg_yellow);  
        if (bitmap != null) {  
            //如果bitmap加载成功，则生成此bitmap的纹理映射  
        	GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTexture[0]);  
            //设置纹理映射的属性  
        	GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER,  
        			GLES20.GL_NEAREST);  
        	GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER,  
        			GLES20.GL_NEAREST);  
            //生成纹理映射  
            GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);  
            //释放bitmap资源  
            bitmap.recycle();  
        } 
	}

	private void initShader() {
		String vertexSource = getFromAssets("VertexShader.glsl"); 
		String fragmentSource = getFromAssets("FragmentShader.glsl"); 

		int program = createProgram(vertexSource, fragmentSource); 
		attribPosition = GLES20.glGetAttribLocation(program, "a_position");  
	    attribTexCoord = GLES20.glGetAttribLocation(program, "a_texCoord");  
	    uniformTexture = GLES20.glGetUniformLocation(program, "u_samplerTexture"); 
	    GLES20.glUseProgram(program); 
		GLES20.glEnableVertexAttribArray(attribPosition); 
		GLES20.glEnableVertexAttribArray(attribTexCoord); 
//		// Set the sampler to texture unit 0 
		GLES20.glUniform1i(uniformTexture, 0); 
//		Matrix
	}
	
	private void loadVertex() {
		this.vertex = ByteBuffer.allocateDirect(quadVertex.length * 4)
				.order(ByteOrder.nativeOrder()).asFloatBuffer();
		this.vertex.put(quadVertex).position(0);
		// short size = 2
		this.index = ByteBuffer.allocateDirect(quadIndex.length * 2)
				.order(ByteOrder.nativeOrder()).asShortBuffer();
		this.index.put(quadIndex).position(0);
	}

	private String getFromAssets(String fileName) {
		try {
			InputStreamReader inputReader = new InputStreamReader(gAct
					.getResources().getAssets().open(fileName));
			BufferedReader bufReader = new BufferedReader(inputReader);
			String line = "";
			String Result = "";
			while ((line = bufReader.readLine()) != null)
				Result += line;
			return Result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	private int createShader(int type, String shaderCode){
		int shader = GLES20.glCreateShader(type);
		Log.e("create shader", "shade type: " + type);
		if (shader == 0) {  
	        throw new RuntimeException("Error create shader. type: " + type);  
	    }
		// 将源码添加到shader并编译
		GLES20.glShaderSource(shader, shaderCode);
		GLES20.glCompileShader(shader);
		
		int[] compiled = new int[1];
		// Check the compile status  
	    GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled, 0);  
	   
	    if (compiled[0] == 0) {  
	        GLES20.glDeleteShader(shader);  
	        throw new RuntimeException("Error compile shader: " + GLES20.glGetShaderInfoLog(shader));  
	    }  
		
		return shader;
	}
	
	private int createProgram(String vShader, String fShader){
		int tmpProgram = GLES20.glCreateProgram();
		if (tmpProgram == 0) {  
	        throw new RuntimeException("Error create program.");  
	    } 
		int vertexShader = createShader(GLES20.GL_VERTEX_SHADER, vShader);
		int fragmentShader = createShader(GLES20.GL_FRAGMENT_SHADER, fShader);
		GLES20.glAttachShader(tmpProgram, vertexShader);
		GLES20.glAttachShader(tmpProgram, fragmentShader);
		GLES20.glLinkProgram(tmpProgram);
		// Check the link status  
		int[] linked = new int[1];
	    GLES20.glGetProgramiv(tmpProgram, GLES20.GL_LINK_STATUS, linked, 0);  
	    if (linked[0] == 0) {  
	        GLES20.glDeleteProgram(tmpProgram);  
	        throw new RuntimeException("Error linking program: " +  GLES20.glGetProgramInfoLog(tmpProgram));  
	    }  
	 // Free up no longer needed shader resources  
	    GLES20.glDeleteShader(vertexShader);  
	    GLES20.glDeleteShader(fragmentShader); 
		return tmpProgram;
	}
	
	/**
	 * 加载Bitmap的方法， 用来从res中加载Bitmap资源
	 * */
	private Bitmap loadBitmap(Context context, int resourceId) {
		InputStream is = context.getResources().openRawResource(resourceId);
		Bitmap bitmap = null;
		try {// 利用BitmapFactory生成Bitmap
			bitmap = BitmapFactory.decodeStream(is);
		} finally {
			try {// 关闭流
				is.close();
				is = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bitmap;
	}
}
