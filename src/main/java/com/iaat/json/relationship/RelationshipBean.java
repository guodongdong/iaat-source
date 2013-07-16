package com.iaat.json.relationship;

import java.util.List;

public class RelationshipBean {
	private String name;
	private Integer size = new Integer(0);
	private List<RelationshipChildrenBean> children;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getSize() {
		return size;
	}
	public List<RelationshipChildrenBean> getChildren() {
		return children;
	}
	public void setChildren(List<RelationshipChildrenBean> children) {
		this.children = children;
	}
	public void setSize(Integer size) {
		this.size = size;
	}

}
