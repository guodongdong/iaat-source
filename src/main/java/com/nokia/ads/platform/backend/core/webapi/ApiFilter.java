package com.nokia.ads.platform.backend.core.webapi;

public interface ApiFilter {

	public boolean doPrefix();
	
	public boolean doPostfix();
}
