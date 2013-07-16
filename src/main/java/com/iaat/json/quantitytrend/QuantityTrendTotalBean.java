package com.iaat.json.quantitytrend;

import java.util.List;

public class QuantityTrendTotalBean {

	
	private long totalWapPv;//总WapPV数
	private long totalWebPv;//总WebPV数
	private long totalUv;		//总UV数
	private long totalLoginPv;//总登录PV
	private long totalStartPv;//总开始页PV
	private transient long totalNewUserNum; //总新用户总数
	private List<QuantityTrendListBean> list; // 所有统计量
	
	
	public long getTotalWapPv() {
		return totalWapPv;
	}
	public void setTotalWapPv(long totalWapPv) {
		this.totalWapPv = totalWapPv;
	}
	public long getTotalWebPv() {
		return totalWebPv;
	}
	public void setTotalWebPv(long totalWebPv) {
		this.totalWebPv = totalWebPv;
	}
	public long getTotalUv() {
		return totalUv;
	}
	public void setTotalUv(long totalUv) {
		this.totalUv = totalUv;
	}
	public long getTotalNewUserNum() {
		return totalNewUserNum;
	}
	public void setTotalNewUserNum(long totalNewUserNum) {
		this.totalNewUserNum = totalNewUserNum;
	}
	public List<QuantityTrendListBean> getList() {
		return list;
	}
	public void setList(List<QuantityTrendListBean> list) {
		this.list = list;
	}
	public long getTotalLoginPv() {
		return totalLoginPv;
	}
	public void setTotalLoginPv(long totalLoginPv) {
		this.totalLoginPv = totalLoginPv;
	}
	public long getTotalStartPv() {
		return totalStartPv;
	}
	public void setTotalStartPv(long totalStartPv) {
		this.totalStartPv = totalStartPv;
	}
	
	
	
}
