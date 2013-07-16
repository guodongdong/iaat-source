package com.iaat.util;
public class TimerUtils {

	private static TimerUtils ins;
	public volatile String UUID_="737364e6-7d24-4f02-84a2-4f4fae40ba25";
	public String getUUID_() {
		return UUID_;
	}
	public static synchronized TimerUtils getIns(){
		if(ins==null)ins = new TimerUtils();
		return ins;
	}
}
