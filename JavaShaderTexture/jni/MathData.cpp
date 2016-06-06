#include <jni.h>
#include <math.h>

#ifdef __cplusplus
extern "C" {
#endif

#define PI 3.14159

JNIEXPORT jfloat JNICALL Java_com_qinan_MathData_degreeByCircle
  (JNIEnv *env, jclass obj, jfloat centerX, jfloat centerY, jfloat posaX, jfloat posaY, jfloat posbX, jfloat posbY)
{
	double lax = (double)(posaX - centerX);
	double lay = (double)(posaY - centerY);
	double ra = sqrt(lax * lax + lay * lay);  // 第一个点所在园的半径

	double lbx = (double)(posbX - centerX);
	double lby = (double)(posbY - centerY);
	double rb = sqrt(lbx * lbx + lby * lby);  // 第二个点所在园的半径

	double lenx = (double)(posbX - posaX);
	double leny = (double)(posbY - posaY);
	if (ra > rb)
	{
		lenx = - lenx;
		leny = - leny;
	}
	// 计算角度
	if (lenx == 0)
	{
		if (leny > 0)
			return PI * 0.5;
		else
			return -PI * 0.5;
	}
	double rad = atan(leny / lenx);
	if (lenx < 0)
		rad += PI;
	return (float)rad;
}
#ifdef __cplusplus
}
#endif
