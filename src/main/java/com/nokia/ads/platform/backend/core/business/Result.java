package com.nokia.ads.platform.backend.core.business;

import com.nokia.ads.platform.backend.core.scheduler.TaskEvent;
import com.nokia.ads.platform.backend.util.Paging;


public class Result<T> {

	public enum Status {
		SUCCESS, FAILURE, PENDING,
	}

	private T data;
	private Paging paging;
	private String error;
	private TaskEvent event;
	
	public T getData() {
		return this.data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public Paging getPaging() {
		return this.paging;
	}
	public void setPaging(Paging paging) {
		this.paging = paging;
	}
	public String getError() {
		return this.error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public TaskEvent getEvent() {
		return this.event;
	}
	public void setEvent(TaskEvent event) {
		this.event = event;
	}

}
