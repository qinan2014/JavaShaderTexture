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

import com.qinan.MathData;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.opengl.Matrix;
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
	/*attributes 分别为顶点坐标数组0  文理坐标数组1  文理采样器1 模型视图投影矩阵*/
	private int[] attributes = new int[5];  // 
	private float[] mProjMatrix = new float[16];
	private float[] mVMatrix = new float[16];
	private float[] mMVPMatrix = new float[16];
	
	public MyRender(Context context) {
		gAct = context;
	}
	
	@Override
	public void onDrawFrame(GL10 arg0) {
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTexture[0]);
	    vertex.position(0);  
	    // load the position  
	    // 3(x , y , z)  
	    // (2 + 3 )* 4 (float size) = 20  
	    GLES20.glVertexAttribPointer(attributes[DefinesStatic.VERTEXPOSITION],   
	                                 3, GLES20.GL_FLOAT,   
	                                 false, 20, vertex);  
	    vertex.position(3);  
	    // load the texture coordinate  
	    GLES20.glVertexAttribPointer(attributes[DefinesStatic.TEXTUREPOSITION],   
	                                  2, GLES20.GL_FLOAT,  
	                                  false, 20, vertex);
	    
	 // 设置相机的位置(视口矩阵)
	    Matrix.setLookAtM(mVMatrix,0,0,0,5,0f,0f,0f,0f,1.0f,0.0f);
	 // 模型矩阵变换
	    Matrix.rotateM(mVMatrix, 0, MathData.gRotateDegree, 0, 0, 1);
	    ++MathData.gRotateDegree;
	 // 计算投影和视口变换
	    Matrix.multiplyMM(mMVPMatrix,0, mProjMatrix,0, mVMatrix,0);
	 // 应用投影和视口变换
	    GLES20.glUniformMatrix4fv(attributes[DefinesStatic.MVPMATRIX],1,false, mMVPMatrix,0);
	   
	    GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_SHORT, index);
	}

	@Override
	public void onSurfaceChanged(GL10 arg0, int width, int height) {	
		GLES20.glViewport(0,0, width, height);

		float ratio =(float) width / height;

		// 此投影矩阵在onDrawFrame()中将应用到对象的坐标
		Matrix.frustumM(mProjMatrix, 0, -ratio, ratio,-1,1,3,7);
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
        Bitmap bitmap = ShaderHelper.loadBitmap(gAct, R.drawable.pop_9bg_yellow);  
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
		String vertexSource = ShaderHelper.getFromAssets(gAct, "VertexShader.glsl"); 
		String fragmentSource = ShaderHelper.getFromAssets(gAct, "FragmentShader.glsl"); 

		int program = ShaderHelper.createProgram(vertexSource, fragmentSource); 
		attributes[DefinesStatic.VERTEXPOSITION] = GLES20.glGetAttribLocation(program, "a_position");  
		attributes[DefinesStatic.TEXTUREPOSITION] = GLES20.glGetAttribLocation(program, "a_texCoord");  
		attributes[DefinesStatic.TEXTURESAMPLE] = GLES20.glGetUniformLocation(program, "u_samplerTexture"); 
		attributes[DefinesStatic.MVPMATRIX] = GLES20.glGetUniformLocation(program, "uMVPMatrix"); 
	    GLES20.glUseProgram(program); 
		GLES20.glEnableVertexAttribArray(attributes[DefinesStatic.VERTEXPOSITION]); 
		GLES20.glEnableVertexAttribArray(attributes[DefinesStatic.TEXTUREPOSITION]); 
//		// Set the sampler to texture unit 0 
		GLES20.glUniform1i(attributes[DefinesStatic.TEXTURESAMPLE], 0); 
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
}
