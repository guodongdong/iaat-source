package com.nokia.ads.platform.backend.core.webapi;

import java.io.Serializable;

import com.nokia.ads.platform.backend.core.auth.IAuthUser;

public class ApiUser implements IAuthUser {

	private String uniqueId = null;
	private Serializable dataObject = null;
	private String verificationCode = null;
	
	public ApiUser(String userUniqueId) {
		uniqueId = userUniqueId;
	}
	
	public String getUserId() {
		return uniqueId;
	}

	public Serializable getData() {
		return this.dataObject;
	}

	public void setData(Serializable dataObject) {
		this.dataObject = dataObject;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}
}
