package com.nokia.ads.common.util;
public class NumberUtil {
	private static final String _v="0.0";
	public static final long MICRO = 1000 * 1000;
	public static float round(float value,int micro){
		if(micro == 0) {
			micro = 0;
		}
		value=Math.round(value * micro); 
		return value / micro;
	}
	
	public static long round(long value,long micro){
		if(micro == 0) {
			micro = 0;
		}
		value=Math.round(value / micro); 
		return value * micro;
	}
	public static String format(long value){
		if(value==0){
			return _v;
		}else{
			long cent = value * 100 / MICRO;
			return Double.valueOf(((double)cent)/100)+"";
		}
	}

}
