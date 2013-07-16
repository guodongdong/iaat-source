package com.iaat.json.heat;

/**
 * 
 * @name HeatRegionBean
 * 
 * @description CLASS_DESCRIPTION
 * 
 * MORE_INFORMATION
 * 
 * @author zhaozhi
 * 
 * @since 2013-7-2
 *       
 * @version 1.0
 */
public class HeatRegionBean {
	
	private String region;

	private String regionCode;
	
	private Integer increaseUser = 0;
	
	private Integer oldUser = 0;
	
	private Integer uv = 0;

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public Integer getIncreaseUser() {
		return increaseUser;
	}

	public void setIncreaseUser(Integer increaseUser) {
		this.increaseUser = increaseUser;
	}

	public Integer getOldUser() {
		return oldUser;
	}

	public void setOldUser(Integer oldUser) {
		this.oldUser = oldUser;
	}

	public Integer getUv() {
		return uv;
	}

	public void setUv(Integer uv) {
		this.uv = uv;
	}
	
}
