package com.iaat.model;

import java.util.Date;

/**
 * DDateId entity. @author MyEclipse Persistence Tools
 */



public class DateBean implements java.io.Serializable {


	private static final long serialVersionUID = 6309512280555575055L;
	

	private Integer dateFk;
	private Integer year;
	private Integer monthOfYear;
	private Integer weekOfYear;
	private Integer dayOfYear;
	private Integer weekOfMonth;
	private Integer dayOfMonth;
	private Integer dayOfWeek;
	private Integer hourOfDay;
	private Date time;
	private Short isAvailable;
	private Date updateTime;
	private String description;

	public Integer getDateFk() {
		return this.dateFk;
	}

	public void setDateFk(Integer dateFk) {
		this.dateFk = dateFk;
	}

	public Integer getYear() {
		return this.year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getMonthOfYear() {
		return this.monthOfYear;
	}

	public void setMonthOfYear(Integer monthOfYear) {
		this.monthOfYear = monthOfYear;
	}

	public Integer getWeekOfYear() {
		return this.weekOfYear;
	}

	public void setWeekOfYear(Integer weekOfYear) {
		this.weekOfYear = weekOfYear;
	}

	public Integer getDayOfYear() {
		return this.dayOfYear;
	}

	public void setDayOfYear(Integer dayOfYear) {
		this.dayOfYear = dayOfYear;
	}

	public Integer getWeekOfMonth() {
		return this.weekOfMonth;
	}

	public void setWeekOfMonth(Integer weekOfMonth) {
		this.weekOfMonth = weekOfMonth;
	}

	public Integer getDayOfMonth() {
		return this.dayOfMonth;
	}

	public void setDayOfMonth(Integer dayOfMonth) {
		this.dayOfMonth = dayOfMonth;
	}

	public Integer getDayOfWeek() {
		return this.dayOfWeek;
	}

	public void setDayOfWeek(Integer dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public Integer getHourOfDay() {
		return this.hourOfDay;
	}

	public void setHourOfDay(Integer hourOfDay) {
		this.hourOfDay = hourOfDay;
	}

	public Date getTime() {
		return this.time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Short getIsAvailable() {
		return this.isAvailable;
	}

	public void setIsAvailable(Short isAvailable) {
		this.isAvailable = isAvailable;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof DateBean))
			return false;
		DateBean castOther = (DateBean) other;

		return ((this.getDateFk() == castOther.getDateFk()) || (this
				.getDateFk() != null
				&& castOther.getDateFk() != null && this.getDateFk().equals(
				castOther.getDateFk())))
				&& ((this.getYear() == castOther.getYear()) || (this.getYear() != null
						&& castOther.getYear() != null && this.getYear()
						.equals(castOther.getYear())))
				&& ((this.getMonthOfYear() == castOther.getMonthOfYear()) || (this
						.getMonthOfYear() != null
						&& castOther.getMonthOfYear() != null && this
						.getMonthOfYear().equals(castOther.getMonthOfYear())))
				&& ((this.getWeekOfYear() == castOther.getWeekOfYear()) || (this
						.getWeekOfYear() != null
						&& castOther.getWeekOfYear() != null && this
						.getWeekOfYear().equals(castOther.getWeekOfYear())))
				&& ((this.getDayOfYear() == castOther.getDayOfYear()) || (this
						.getDayOfYear() != null
						&& castOther.getDayOfYear() != null && this
						.getDayOfYear().equals(castOther.getDayOfYear())))
				&& ((this.getWeekOfMonth() == castOther.getWeekOfMonth()) || (this
						.getWeekOfMonth() != null
						&& castOther.getWeekOfMonth() != null && this
						.getWeekOfMonth().equals(castOther.getWeekOfMonth())))
				&& ((this.getDayOfMonth() == castOther.getDayOfMonth()) || (this
						.getDayOfMonth() != null
						&& castOther.getDayOfMonth() != null && this
						.getDayOfMonth().equals(castOther.getDayOfMonth())))
				&& ((this.getDayOfWeek() == castOther.getDayOfWeek()) || (this
						.getDayOfWeek() != null
						&& castOther.getDayOfWeek() != null && this
						.getDayOfWeek().equals(castOther.getDayOfWeek())))
				&& ((this.getHourOfDay() == castOther.getHourOfDay()) || (this
						.getHourOfDay() != null
						&& castOther.getHourOfDay() != null && this
						.getHourOfDay().equals(castOther.getHourOfDay())))
				&& ((this.getTime() == castOther.getTime()) || (this.getTime() != null
						&& castOther.getTime() != null && this.getTime()
						.equals(castOther.getTime())))
				&& ((this.getIsAvailable() == castOther.getIsAvailable()) || (this
						.getIsAvailable() != null
						&& castOther.getIsAvailable() != null && this
						.getIsAvailable().equals(castOther.getIsAvailable())))
				&& ((this.getUpdateTime() == castOther.getUpdateTime()) || (this
						.getUpdateTime() != null
						&& castOther.getUpdateTime() != null && this
						.getUpdateTime().equals(castOther.getUpdateTime())))
				&& ((this.getDescription() == castOther.getDescription()) || (this
						.getDescription() != null
						&& castOther.getDescription() != null && this
						.getDescription().equals(castOther.getDescription())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getDateFk() == null ? 0 : this.getDateFk().hashCode());
		result = 37 * result
				+ (getYear() == null ? 0 : this.getYear().hashCode());
		result = 37
				* result
				+ (getMonthOfYear() == null ? 0 : this.getMonthOfYear()
						.hashCode());
		result = 37
				* result
				+ (getWeekOfYear() == null ? 0 : this.getWeekOfYear()
						.hashCode());
		result = 37 * result
				+ (getDayOfYear() == null ? 0 : this.getDayOfYear().hashCode());
		result = 37
				* result
				+ (getWeekOfMonth() == null ? 0 : this.getWeekOfMonth()
						.hashCode());
		result = 37
				* result
				+ (getDayOfMonth() == null ? 0 : this.getDayOfMonth()
						.hashCode());
		result = 37 * result
				+ (getDayOfWeek() == null ? 0 : this.getDayOfWeek().hashCode());
		result = 37 * result
				+ (getHourOfDay() == null ? 0 : this.getHourOfDay().hashCode());
		result = 37 * result
				+ (getTime() == null ? 0 : this.getTime().hashCode());
		result = 37
				* result
				+ (getIsAvailable() == null ? 0 : this.getIsAvailable()
						.hashCode());
		result = 37
				* result
				+ (getUpdateTime() == null ? 0 : this.getUpdateTime()
						.hashCode());
		result = 37
				* result
				+ (getDescription() == null ? 0 : this.getDescription()
						.hashCode());
		return result;
	}

}