package com.nokia.ads.platform.backend.core.scheduler;

import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;

public abstract class SchedulableJob implements InterruptableJob {

	private Thread __current_thread__ = null;
	private int __current_worker_seq__ = 0;

	/**
	 * Main entry point of scheduled job business logic.
	 */
	public void execute(JobExecutionContext jobContext)
			throws JobExecutionException {
		__current_thread__ = Thread.currentThread();
		__current_worker_seq__++;
	}

	public void interrupt() throws UnableToInterruptJobException {
		// terminate thread
		__current_thread__.interrupt();
		__current_worker_seq__ = 0;
	}
}
