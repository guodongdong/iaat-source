package com.nokia.ads.platform.backend.core.scheduler.model;

import java.util.Calendar;

import com.nokia.ads.platform.backend.core.scheduler.type.TaskPriority;
import com.nokia.ads.platform.backend.core.scheduler.type.TaskStatus;
import com.nokia.ads.platform.backend.model.ModelObject;
import com.nokia.ads.platform.backend.profile.AdNetwork;

public abstract class Task extends ModelObject<Long> {

	private Task parent;
	private AdNetwork network;
	private String worker; // worker class
	private int retry;
	private TaskPriority priority;
	private int priorityLevel = -1;
	private TaskStatus status;
	private Calendar dtCreated;
	private Calendar dtStarted;
	private Calendar dtUpdated;
	private Integer workCode;

	public Task getParent() {
		return this.parent;
	}

	public void setParent(Task parent) {
		this.parent = parent;
	}

	public AdNetwork getNetwork() {
		return this.network;
	}

	public void setNetwork(AdNetwork network) {
		this.network = network;
	}

	public String getWorker() {
		return this.worker;
	}

	public void setWorker(String worker) {
		this.worker = worker;
	}

	public int getRetry() {
		return this.retry;
	}

	public void setRetry(int retry) {
		this.retry = retry;
	}

	public TaskPriority getPriority() {
		return this.priority;
	}

	public void setPriority(TaskPriority priority) {
		this.priority = priority;
		this.priorityLevel = this.priority.getLevel();
	}

	public TaskStatus getStatus() {
		return this.status;
	}

	public void setStatus(TaskStatus status) {
		this.status = status;
	}

	public Calendar getDtCreated() {
		return this.dtCreated;
	}

	public void setDtCreated(Calendar dtCreated) {
		this.dtCreated = dtCreated;
	}

	public Calendar getDtStarted() {
		return this.dtStarted;
	}

	public void setDtStarted(Calendar dtStarted) {
		this.dtStarted = dtStarted;
	}

	public Calendar getDtUpdated() {
		return this.dtUpdated;
	}

	public void setDtUpdated(Calendar dtUpdated) {
		this.dtUpdated = dtUpdated;
	}

	public int getPriorityLevel() {
		if (null != this.priority) {
			return this.priority.getLevel();
		}
		return this.priorityLevel;
	}

	public void setPriorityLevel(int level) {
		this.priorityLevel = level;
		this.priority = TaskPriority.fromLevel(level);
	}

	public Integer getWorkCode() {
		return workCode;
	}

	public void setWorkCode(Integer workCode) {
		this.workCode = workCode;
	}

}
