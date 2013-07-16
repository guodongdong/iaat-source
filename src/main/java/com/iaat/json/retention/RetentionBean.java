package com.iaat.json.retention;

import java.util.Date;
import java.util.List;



/**    
 * @name RetentionBean
 * 
 * @description CLASS_DESCRIPTION
 * 
 * MORE_INFORMATION
 * 
 * @author Lixw
 * 
 * @since 2013-6-28
 *       
 * @version 1.0
 */
public class RetentionBean {
	 
	 private String name;
	 private transient  Date beginDate;
	 private transient Date endDate;
	 private transient Date time;
	 private transient int hour;
	 private transient int week_num;
	 private transient int month;
	 private transient int year;
	 
	 
	 
	 private transient double count;
	 private List<RetentionValuesBean> data;
	 
	 
	
	
	public List<RetentionValuesBean> getData() {
		return data;
	}
	public void setData(List<RetentionValuesBean> data) {
		this.data = data;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getCount() {
		return count;
	}
	public void setCount(double count) {
		this.count = count;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public int getHour() {
		return hour;
	}
	public void setHour(int hour) {
		this.hour = hour;
	}
	public int getWeek_num() {
		return week_num;
	}
	public void setWeek_num(int week_num) {
		this.week_num = week_num;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
		
	
} 