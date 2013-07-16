package com.iaat.json.analyse;

import java.util.List;

public class AccessTrackBean {
	private String name;
	private Integer size = new Integer(0);
	private List<String> children;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getSize() {
		return size;
	}
	public List<String> getChildren() {
		return children;
	}
	public void setChildren(List<String> children) {
		this.children = children;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
}
