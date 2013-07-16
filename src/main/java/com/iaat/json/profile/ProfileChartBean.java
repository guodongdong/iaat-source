package com.iaat.json.profile;

import com.iaat.util.ValidateUtils;


/**
 * 
 * @name ProfileChartBean
 * 
 * @description CLASS_DESCRIPTION
 * 
 * MORE_INFORMATION
 * 
 * @author zhaozhi
 * 
 * @since 2013-6-24
 *       
 * @version 1.0
 */
public class ProfileChartBean {
//	private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	
	private String title;
	
//	private boolean isDaily = true;
	
//	private boolean hourly = false;
	
	private int count;
	
	private int secondCount;
	
	private int hour;
	
	private String dateTime;
	
//	public boolean isHourly() {
//		return hourly;
//	}

	public void setHourly(boolean hourly) {
//		this.hourly = hourly;
		if (hourly) {
			if (hour < 10) {
				this.dateTime = "0" + hour + ":00";
			}else {
				this.dateTime = hour + ":00";
			}
		}
	}

//	public boolean isDaily() {
//		return isDaily;
//	}

	public void setDaily(boolean isDaily) {
//		this.isDaily = isDaily;
		if (isDaily) {
			if (ValidateUtils.isNotNull(this.dateTime)) {
				this.dateTime = this.dateTime.substring(0,10);
			}
		}
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateRange) {
		this.dateTime = dateRange;
	}

	public void setBeginDate(String beginDate) {
		String str = beginDate.substring(0,10) + " ~ ";
		if (ValidateUtils.isNotNull(this.dateTime)) {
			this.dateTime = str + this.dateTime;
		}else {
			this.dateTime = str;
		}
	}

	public void setEndDate(String endDate) {
		if (ValidateUtils.isNotNull(dateTime)) {
			this.dateTime += endDate.substring(0,10);
		}else {
			this.dateTime = endDate.substring(0,10);
		}
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getSecondCount() {
		return secondCount;
	}

	public void setSecondCount(int secondCount) {
		this.secondCount = secondCount;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	
}
