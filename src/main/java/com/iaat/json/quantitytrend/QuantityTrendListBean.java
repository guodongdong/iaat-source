package com.iaat.json.quantitytrend;

import java.util.Date;

import com.iaat.util.ValidateUtils;



public class QuantityTrendListBean {

	
	 
	private int wapPv;
	private int webPv;
	private int loginPv;//登录PV tom表统计
	private int startPv;//起始页PV tom表统计
	private int uv;	//UV数
	private volatile int newUserNum;	//新用户数
	private String period;		//时间段
	
	private volatile int week;
	private volatile int hour;
	private volatile Date time;

	
	public int getWapPv() {
		return wapPv;
	}
	public void setWapPv(int wapPv) {
		this.wapPv = wapPv;
	}
	public int getWebPv() {
		return webPv;
	}
	public void setWebPv(int webPv) {
		this.webPv = webPv;
	}
	
	
	public int getLoginPv() {
		return loginPv;
	}
	public void setLoginPv(int loginPv) {
		this.loginPv = loginPv;
	}
	public int getStartPv() {
		return startPv;
	}
	public void setStartPv(int startPv) {
		this.startPv = startPv;
	}
	public int getUv() {
		return uv;
	}
	public void setUv(int uv) {
		this.uv = uv;
	}
	 
	public int getNewUserNum() {
		return newUserNum;
	}
	public void setNewUserNum(int newUserNum) {
		this.newUserNum = newUserNum;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	
	
	public int getWeek() {
		return week;
	}
	public void setWeek(int week) {
		this.week = week;
	}
	public int getHour() {
		return hour;
	}
	public void setHour(int hour) {
		this.hour = hour;
	}
	
	
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public void setStartTime(String startTime){
		String time = startTime.substring(0,10) + " ~ ";
		if(ValidateUtils.isNotNull(period)){
			this.period = time + period;
		}else{
			this.period = time;
		}
	}
	
	public void setEndTime(String endTime){
		if(ValidateUtils.isNotNull(period)){
			this.period += endTime.substring(0,10);
		}else{
			this.period = endTime.substring(0,10);
		}
	}
	
}
