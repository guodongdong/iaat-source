package com.nokia.ads.platform.backend.core.webapi;

public class ApiError {
	
	final public static ApiError UNKNOWN = new ApiError("UNKNOWN");
	final public static ApiError INVALID_USER = new ApiError("INVALID_USER");
	final public static ApiError INVALID_AUTH = new ApiError("INVALID_AUTH");
	final public static ApiError INVALID_REQUEST = new ApiError("INVALID_REQUEST");
	final public static ApiError INVALID_DATA_SIZE = new ApiError("INVALID_DATA_SIZE");
	final public static ApiError INVALID_DATA_FORMAT = new ApiError("INVALID_DATA_FORMAT");
	final public static ApiError INVALID_PARAMS = new ApiError("INVALID_PARAMS");
	
	private String code;
	private String message;
	private String[] details;
	
	public ApiError() {
		code = "UNKNOWN";
	}
	
	protected ApiError(String code) {
		this.code = code;
	}
	public String getCode() {
		return code;
	}
	public String getMessage() {
		return this.message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String[] getDetails() {
		return this.details;
	}
	public void setDetails(String[] details) {
		this.details = details;
	}
	
}
