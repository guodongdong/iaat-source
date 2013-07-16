package com.iaat.json.quantitytrend;

import java.util.Date;



public class QuantityTrendChartBean {

	
	 
	private transient Date time; //时间
	private int wapPv;	//total表统计
	private int webPv;	//total表统计
	private int loginPv;//登录PV tom表统计
	private int startPv;//开始页PV tom表统计
	private int uv;		//UV tom表统计
	private volatile Integer year;
	private transient Integer month;
	private transient Integer day;
	private transient Integer hour;
	private transient Long milliseconds;
	private transient Integer week;
	
	private transient Date startTime;	//开始时间
	private transient Date endTime;	//结束时间
	
	private String tag;//前台显示的名称	
	
	
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
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	public Integer getDay() {
		return day;
	}
	public void setDay(Integer day) {
		this.day = day;
	}
	public Integer getHour() {
		return hour;
	}
	public void setHour(Integer hour) {
		this.hour = hour;
	}
	public Long getMilliseconds() {
		return milliseconds;
	}
	public void setMilliseconds(Long milliseconds) {
		this.milliseconds = milliseconds;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public Integer getWeek() {
		return week;
	}
	public void setWeek(Integer week) {
		this.week = week;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	
	
	
}
