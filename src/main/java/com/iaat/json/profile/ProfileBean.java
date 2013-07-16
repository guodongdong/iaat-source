package com.iaat.json.profile;

import com.iaat.util.NumberUtil;

/**
 * 
 * @name ProfileBean
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
public class ProfileBean {
	
	private int pv;
	
	private int uv;
	
	private int increaseUser;
	
	private int onceUser;
	
	private int onlyLoginUser;
	
	private String avgUserLogin;
	
	private String avgUserAccess;
	
	private String avgLastTime;
	
	public int getPv() {
		return pv;
	}

	public void setPv(int pv) {
		this.pv = pv;
	}
	
	public int getUv() {
		return uv;
	}

	public void setUv(int uv) {
		this.uv = uv;
	}

	public int getIncreaseUser() {
		return increaseUser;
	}

	public void setIncreaseUser(int increaseUser) {
		this.increaseUser = increaseUser;
	}

	public int getOnceUser() {
		return onceUser;
	}

	public void setOnceUser(int onceUser) {
		this.onceUser = onceUser;
	}

	public int getOnlyLoginUser() {
		return onlyLoginUser;
	}

	public void setOnlyLoginUser(int onlyLoginUser) {
		this.onlyLoginUser = onlyLoginUser;
	}
	
	public void setAvgUserLogin(double avgUserLogin) {
		this.avgUserLogin = NumberUtil.getFormatData(avgUserLogin);
	}

	public void setAvgUserAccess(double avgUserAccess) {
		this.avgUserAccess = NumberUtil.getFormatData(avgUserAccess);
	}

	public void setAvgLastTime(double avgLastTime) {
		this.avgLastTime = NumberUtil.getFormatData(avgLastTime);
	}

//	public String getAvgUserLogin() {
//		return avgUserLogin;
//	}
//
//	public String getAvgUserAccess() {
//		return avgUserAccess;
//	}
//
//	public String getAvgLastTime() {
//		return avgLastTime;
//	}

	public String getAvgUserLoginStr() {
		return avgUserLogin;
	}

	public String getAvgUserAccessStr() {
		return avgUserAccess;
	}

	public String getAvgLastTimeStr() {
		return avgLastTime;
	}

}
