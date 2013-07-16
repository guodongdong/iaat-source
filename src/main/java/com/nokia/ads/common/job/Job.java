package com.nokia.ads.common.job;

import java.util.Date;

/**
 * An agent that does some background work at a regularly scheduled interval.
 */
public interface Job {
	/**
	 * @return The name of this Job. It will be the worker thread's name that
	 *         runs it and may appear in the logs.
	 */
	public String getName();

	/**
	 * @return The time until the very first job run in milliseconds.
	 */
	public long timeUntilFirstRun();

	/**
	 * @return The time between job runs in milliseconds.
	 */
	public long interval();

	/**
	 * Performs the job's work on a background thread. This is called every
	 * <code>inverval</code>.
	 */
	public void doWork();

	public Date firstRunTime();

}
