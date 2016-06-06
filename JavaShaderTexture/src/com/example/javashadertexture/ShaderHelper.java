package com.example.javashadertexture;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.util.Log;

public class ShaderHelper {
	public static String getFromAssets(Context context, String fileName) {
		try {
			InputStreamReader inputReader = new InputStreamReader(context
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
	
	public static int createShader(int type, String shaderCode){
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
	
	public static int createProgram(String vShader, String fShader){
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
	public static Bitmap loadBitmap(Context context, int resourceId) {
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
