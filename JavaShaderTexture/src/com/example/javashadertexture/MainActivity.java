package com.example.javashadertexture;  
  
import android.app.Activity;  
import android.os.Bundle;  

public class MainActivity extends Activity {  
  
    MyGLSurfaceView mView;  
  
    @Override 
    protected void onCreate(Bundle icicle) {  
        super.onCreate(icicle);  
        mView = new MyGLSurfaceView(getApplication());  
        setContentView(mView);  
    }  
  
    @Override protected void onPause() {  
        super.onPause();  
        mView.onPause();  
    }  
  
    @Override protected void onResume() {  
        super.onResume();  
        mView.onResume();  
    }  
  
}