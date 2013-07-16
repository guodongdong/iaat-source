package com.nokia.ads.share.type;

public enum LatitudeType {
	AP("ap"),//终端
	CATEGORY("category"),//分类
	CHANNEL("channel"),//渠道
	DATE("date"),//时间年月日
	TIME("time"),//时间小时
	IMEI("imei"),//移动设备唯一编码
	OPERATOR("operator"),//运营商
	PLATFORM("platform"),//平台
	REGION("region"),//地域
	TERMINAL("terminal"),//终端
	LOGS("logs");//日志
	private final String value;
	private LatitudeType(String value) {
		this.value = value;
	}
	public String getValue() {
		return value;
	}
}
