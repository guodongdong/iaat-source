package com.nokia.ads.platform.backend.core.scheduler;

import java.util.Calendar;

public class TaskEvent {
	
	public enum Status {
		SUCCESS, FAILURE, PENDING,
	}
	
	private Long taskId;
	private Long parentTaskId;
	private Calendar schedule;
	private String priority;
	private Status status;
	private String description;
	
	public Long getTaskId() {
		return this.taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	public Long getParentTaskId() {
		return this.parentTaskId;
	}
	public void setParentTaskId(Long parentTaskId) {
		this.parentTaskId = parentTaskId;
	}
	public Calendar getSchedule() {
		return this.schedule;
	}
	public void setSchedule(Calendar schedule) {
		this.schedule = schedule;
	}
	public String getPriority() {
		return this.priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public Status getStatus() {
		return this.status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public String getDescription() {
		return this.description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
