package com.iaat.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class NumberUtil {
	private static final String _v="0.00";
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
	
	private static DecimalFormat dFormat;
	
	public static String getFormatData(double num){
    	dFormat = new DecimalFormat("0.00");
    	return dFormat.format(num);
    }

	public static String getFormatData(BigDecimal num){
		double d = num.doubleValue();
		return getFormatData(d);
	}
	
	public static String getFormatData(Float num){
		dFormat = new DecimalFormat("0.00");
		return dFormat.format(num);
	}
	
	public static String getFormatData(int num){
		dFormat = new DecimalFormat("00");
		return dFormat.format(num);
	}
	public static void main(String[] args) {
//		NumberFormat nFormat = NumberFormat.getInstance();
//		System.out.println(nFormat.format(3.1D));
		System.out.println(getFormatData(1));
	}
	
	
}
