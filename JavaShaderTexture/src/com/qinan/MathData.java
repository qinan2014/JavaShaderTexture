package com.qinan;

public class MathData {
	static {
		System.loadLibrary("mathfunc");
	}
	public static float gRotateDegree = (float) 0.0;
	
	public static native float degreeByCircle(float centerX, float centerY, float posaX, float posaY, float posbX, float posbY);
}
