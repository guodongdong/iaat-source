package com.iaat.json.overlap;

import java.util.Calendar;

public class ContrastSearchBean {

	private String name;
	
	private Calendar startDateTime;
	
	private Calendar endDateTime;
	
	private int startTime;
	
	private int endTime;
	
	private int platform;
	
	private int terminalType;
	
	private String province;
	
	private transient Integer provinceFk;

	private String city;
	
	private transient Integer cityFk;
	
	private Integer channel;
	
	private Integer operator;
	
	private Integer ap;
	
	private Integer category;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Calendar getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(Calendar startDateTime) {
		this.startDateTime = startDateTime;
	}

	public Calendar getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(Calendar endDateTime) {
		this.endDateTime = endDateTime;
	}

	public int getStartTime() {
		return startTime;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	public int getEndTime() {
		return endTime;
	}

	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}

	public int getPlatform() {
		return platform;
	}

	public void setPlatform(int platform) {
		this.platform = platform;
	}

	public int getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(int terminalType) {
		this.terminalType = terminalType;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public Integer getProvinceFk() {
		return provinceFk;
	}

	public void setProvinceFk(Integer provinceFk) {
		this.provinceFk = provinceFk;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getCityFk() {
		return cityFk;
	}

	public void setCityFk(Integer cityFk) {
		this.cityFk = cityFk;
	}

	public Integer getChannel() {
		return channel;
	}

	public void setChannel(Integer channel) {
		this.channel = channel;
	}

	public Integer getOperator() {
		return operator;
	}

	public void setOperator(Integer operator) {
		this.operator = operator;
	}

	public Integer getAp() {
		return ap;
	}

	public void setAp(Integer ap) {
		this.ap = ap;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}
	
	
	
}
