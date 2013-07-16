package com.iaat.json;

import java.util.List;

import com.iaat.model.RegionBean;

public class RegionLinkBean {
	
	private RegionBean province;
	
	private List<RegionBean> cities;
	
	public RegionBean getProvince() {
		return province;
	}
	public void setProvince(RegionBean province) {
		this.province = province;
	}
	public List<RegionBean> getCities() {
		return cities;
	}
	public void setCities(List<RegionBean> cities) {
		this.cities = cities;
	}
}
