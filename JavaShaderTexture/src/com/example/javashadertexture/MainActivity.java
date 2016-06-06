package com.example.javashadertexture;  
  
import org.xmlpull.v1.XmlPullParser;
import android.app.Activity;  
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;  
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {  
  
    MyGLSurfaceView mView;  
  
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