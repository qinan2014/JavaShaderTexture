package com.example.javashadertexture;  
  
import org.xmlpull.v1.XmlPullParser;

import android.app.Activity;  
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;  
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity{  
  
    MyGLSurfaceView mView;  
    private final String Tag = "qinanMainActivity";
    private int winWidth, winHeight;
  
    @Override 
    protected void onCreate(Bundle icicle) {  
        super.onCreate(icicle);  
        Resources res = this.getResources();
		XmlPullParser parser = res.getXml(R.layout.glsurface_overlay);
		AttributeSet attributes = Xml.asAttributeSet(parser);
		
        mView = new MyGLSurfaceView(getApplication(), attributes); 
        setContentView(mView);  
        
		TextView textView = new TextView(this);
		textView.setContentDescription("北京在这里ALL--IN--HERE");
		
		//--------------------add ImageButtons-------------------------------------
		LinearLayout imageButtonLinearLayout = new LinearLayout(this);
		imageButtonLinearLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		imageButtonLinearLayout.setOrientation(LinearLayout.VERTICAL);
		// add imagebuttons
		LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout imagebuttonLinearLayout = (LinearLayout) inflater.inflate(R.layout.imagebutton, imageButtonLinearLayout, false);
		imageButtonLinearLayout.addView(imagebuttonLinearLayout);
		
		addContentView(textView, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		addContentView(imageButtonLinearLayout, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    
		// 获取窗口宽度
		WindowManager winManager=(WindowManager)getSystemService(Context.WINDOW_SERVICE);
		winWidth = winManager.getDefaultDisplay().getWidth();
		winHeight = winManager.getDefaultDisplay().getHeight();
    }  
  
    @Override protected void onPause() {  
        super.onPause();  
        mView.onPause();  
    }  
  
    @Override protected void onResume() {  
        super.onResume();  
        mView.onResume();  
    }

	@SuppressWarnings("deprecation")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
	    int count = event.getPointerCount();
	    Log.e(Tag, "event : " + event.getAction() + "  count: " + count);
	    switch (event.getAction())
	    {
	    case MotionEvent.ACTION_MOVE:
	    	break;
	    case MotionEvent.ACTION_POINTER_2_DOWN:
	    	if (count > 1){
//	    		Log.e(Tag, "centerpt: " + winWidth / 2 + "  " + winHeight / 2);
//	    		Log.e(Tag, "pt1: " + event.getX(0) + "  " + event.getY(0));
//	    		Log.e(Tag, "pt2: " + event.getX(1) + "  " + event.getY(1));
	    	}
	    	break;
	    case MotionEvent.ACTION_UP:
	    	Log.e(Tag, "pt1: " + event.getX(0) + "  " + event.getY(0));
	    	break;
	    default:
	    	break;
	    }
		return super.onTouchEvent(event);
	}
}