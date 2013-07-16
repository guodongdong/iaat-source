package com.iaat.model;

import java.util.List;

public class PlatformObj {
	private int totalNewUsers;
	private int totalOldUsers;
	private int totalUsers;
	private List<PlatBean> data;
	public int getTotalNewUsers() {
		return totalNewUsers;
	}
	public void setTotalNewUsers(int totalNewUsers) {
		this.totalNewUsers = totalNewUsers;
	}
	public int getTotalOldUsers() {
		return totalOldUsers;
	}
	public void setTotalOldUsers(int totalOldUsers) {
		this.totalOldUsers = totalOldUsers;
	}
	public int getTotalUsers() {
		return totalUsers;
	}
	public void setTotalUsers(int totalUsers) {
		this.totalUsers = totalUsers;
	}
	public List<PlatBean> getData() {
		return data;
	}
	public void setData(List<PlatBean> data) {
		this.data = data;
	}
}
