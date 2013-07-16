package com.iaat.json.bounce;

import java.util.List;

public class BounceDataBean {

	private String bounceDate;
	private Integer perople;
	private List<BounceRateBean> data;
	
	public Integer getPerople() {
		return perople;
	}
	public void setPerople(Integer perople) {
		this.perople = perople;
	}
	public List<BounceRateBean> getData() {
		return data;
	}
	public void setData(List<BounceRateBean> data) {
		this.data = data;
	}
	public String getBounceDate() {
		return bounceDate;
	}
	public void setBounceDate(String bounceDate) {
		this.bounceDate = bounceDate;
	}
}
