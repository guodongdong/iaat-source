package com.nokia.ads.platform.backend.model;

import java.text.DecimalFormat;


public class Money {

	public static final long MICRO = 1000 * 1000;

	public static Money valueOf(String str) {
		double d = Double.parseDouble(str);
		if (d >= 0) {
			long cent = Math.round(d * 100);
			Money m = new Money();
			m.setMicroValue(cent * MICRO / 100);
			return m;
		}
		// money could not be negative
		return null;
	}

	public static Money valueOf(Long value) {
		if(value == null) {
			return null;
		}
		Money m = new Money();
		m.setMicroValue(value);
		return m;
	}

	private Long microValue;

	public Long getMicroValue() {
		return microValue;
	}

	public void setMicroValue(Long value) {
		this.microValue = value;
	}

	public String getStringValue() {
		long cent = microValue * 100 / MICRO;
		Double d = Double.valueOf(((double)cent)/100);
		DecimalFormat dFormat = new DecimalFormat("0.00");
		return dFormat.format(d);
	}
	
	/**
	 * 
	 * getLongFromDB(把金额数的小数位数整理：去掉第二位小数点后的内容)   
	 * 
	 * @param longValue
	 * @return 
	 * 
	 * Long
	 */
	public static Long getLongFromDB(Long longValue){
		if (longValue == null) {
			return Long.valueOf(0L);
		}
		longValue = ((int)(longValue/10000))*10000L;
		return longValue;
	}
	
}
