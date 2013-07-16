package com.nokia.ads.platform.backend.model;

import com.nokia.ads.platform.backend.core.scheduler.model.Task;
import com.nokia.ads.platform.backend.core.scheduler.model.TaskError;
import com.nokia.ads.platform.backend.profile.AdNetwork;

/**
 * 
 * @name NetworkObject
 * 
 * @description CLASS_DESCRIPTION
 * 
 * MORE_INFORMATION
 * 
 * @author WangXL 
 * 
 * @since 2011-1-27
 *       
 * @version 1.0
 */
abstract public class NetworkObject extends ModelObject<Long>  {
	
	protected AdNetwork network;
	protected Task task;
	protected TaskError failure;
	protected NetworkObject() {
		this.network = getNetwork();
	}
	
	public NetworkObject(Long id) {
		super(id);
		this.network = getNetwork();
	}
	
	public Task getTask() {
		return this.task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public TaskError getFailure() {
		return failure;
	}

	public void setFailure(TaskError failure) {
		this.failure = failure;
	}

	/**
	 * 
	 * 
	 * getNetwork
	 * 
	 * get Network
	 * 
	 * @return Network
	 * 
	 * @since 1.0
	 */
	abstract public AdNetwork getNetwork();
	abstract public Long getRemoteID();

}
