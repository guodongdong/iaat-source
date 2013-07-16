package com.nokia.ads.platform.backend.core.webapi;

public class ApiRequestException extends Exception {

	private static final long serialVersionUID = 1L;

	private String requestData;
	private String requestUser;
	private String requestUrl;

	public String getRequestData() {
		return this.requestData;
	}

	public void setRequestData(String requestData) {
		this.requestData = requestData;
	}

	public String getRequestUser() {
		return this.requestUser;
	}

	public void setRequestUser(String requestUser) {
		this.requestUser = requestUser;
	}

	public String getRequestUrl() {
		return this.requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}
}
