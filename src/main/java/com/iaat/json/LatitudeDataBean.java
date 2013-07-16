package com.iaat.json;

import java.util.ArrayList;
import java.util.List;

public class LatitudeDataBean {

	private String name;
	private String value;
	private transient Integer sequence;
	private List<LatitudeDataBean>  children = new ArrayList<LatitudeDataBean>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Integer getSequence() {
		return sequence;
	}
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	
	public List<LatitudeDataBean> getChildren() {
		return children;
	}
	public void setChildren(List<LatitudeDataBean> children) {
		this.children = children;
	}
	
}
