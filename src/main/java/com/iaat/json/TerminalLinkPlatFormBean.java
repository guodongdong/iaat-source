package com.iaat.json;

import java.util.List;

import com.iaat.model.PlatformBean;
import com.iaat.model.TerminalBean;

public class TerminalLinkPlatFormBean {

	private PlatformBean platformBean;
	
	private List<TerminalBean> terminBeans;
	
	public PlatformBean getPlatformBean() {
		return platformBean;
	}
	public void setPlatformBean(PlatformBean platformBean) {
		this.platformBean = platformBean;
	}
	public List<TerminalBean> getTerminBeans() {
		return terminBeans;
	}
	public void setTerminBeans(List<TerminalBean> terminBeans) {
		this.terminBeans = terminBeans;
	}
	
	
}
