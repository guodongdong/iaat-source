/**
 * 
 */
package com.nokia.ads.platform.backend.core.scheduler;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import com.nokia.ads.common.util.ObjectUtils;

/**
 * 
 * @author kenliu
 * 
 */
final public class SchedulerService {

	private static Logger log = Logger.getLogger(SchedulerService.class
			.getName());

	// Holds singleton instance.
	protected static SchedulerService schedulerService;
	private HashMap<String, Scheduler> schedulers = new HashMap<String, Scheduler>();
	private static byte[] serviceLock = new byte[0];
	private byte[] schedulerLock = new byte[0];

	private SchedulerService() {
	}

	public static SchedulerService getInstance() {
		if (schedulerService == null) {
			synchronized (serviceLock) {
				if (schedulerService == null) {
					schedulerService = new SchedulerService();
				}
			}
		}
		return schedulerService;
	}

	void addScheduler(String schedulerName, int threadCount) {
		synchronized (schedulerLock) {
			try {
				schedulers.put(schedulerName,
						makeScheduler(schedulerName, threadCount));
			} catch (SchedulerException se) {
				throw new RuntimeException(se);
			}
		}
	}

	public boolean hasScheduler(String schedulerName) {
		synchronized (schedulerLock) {
			if (schedulers.get(schedulerName) == null) {
				return false;
			} else {
				return true;
			}
		}
	}

	void addTask(String schedulerName, String taskName,
			Class<? extends SchedulableJob> jobClass) {
		addTask(schedulerName, taskName, schedulerName, jobClass);
	}

	void addTask(String schedulerName, String taskName, String groupName,
			Class<? extends SchedulableJob> jobClass) {
		Scheduler targetScheduler = getScheduler(schedulerName);
		if (targetScheduler == null) {
			throw new IllegalArgumentException("Scheduler does not exist: "
					+ schedulerName);
		}
		JobDetail newJobDetail = new JobDetail(taskName, groupName, jobClass);
		newJobDetail.setDurability(true);
		try {
			targetScheduler.addJob(newJobDetail, false);
		} catch (SchedulerException e) {
			throw new RuntimeException("Unable to add Task: " + taskName, e);
		}
	}

	public void shutdown(boolean waitForJobsToComplete) {
		Iterator<String> schedulerIterator = schedulers.keySet().iterator();
		String schedulerName = null;
		while (schedulerIterator.hasNext()) {
			try {
				schedulerName = schedulerIterator.next();
				schedulers.get(schedulerName).shutdown(waitForJobsToComplete);
			} catch (SchedulerException e) {
				throw new RuntimeException("Unable to shutdown scheduler: "
						+ schedulerName, e);
			}
		}
	}

	public int getPendingTaskCount(String schedulerName, String taskName) {
		return getPendingTaskCount(schedulerName, taskName, schedulerName);
	}

	public int getPendingTaskCount(String schedulerName, String taskName,
			String groupName) {
		try {
			Trigger[] triggers = getScheduler(schedulerName).getTriggersOfJob(
					taskName, groupName);
			return triggers.length;
		} catch (SchedulerException e) {
			throw new RuntimeException("Unable to get pending task count: "
					+ schedulerName, e);
		}
	}

	public List<JobExecutionContext> getCurrentJobs(String schedulerName) {
		Scheduler currentScheduler = getScheduler(schedulerName);
		if (currentScheduler == null) {
			throw new IllegalArgumentException("Scheduler: " + schedulerName
					+ " does not exist.");
		}
		List<JobExecutionContext> jobContexts = null;
		try {
			jobContexts = ObjectUtils.toList(
					currentScheduler.getCurrentlyExecutingJobs(),
					JobExecutionContext.class);
			return jobContexts;
		} catch (SchedulerException exception) {
			throw new RuntimeException(
					"Unable to get job execution context for: " + schedulerName,
					exception);
		}
	}

	void startScheduler(String schedulerName) {
		Scheduler targetScheduler = getScheduler(schedulerName);
		if (targetScheduler == null) {
			throw new IllegalArgumentException("Scheduler does not exist:"
					+ schedulerName);
		}

		try {
			targetScheduler.start();
		} catch (SchedulerException e) {
			throw new RuntimeException("Unable to start scheduler:"
					+ schedulerName, e);
		}
	}

	public void pause(String schedulerName) {
		Scheduler targetScheduler = getScheduler(schedulerName);
		if (targetScheduler == null) {
			throw new IllegalArgumentException("Scheduler does not exist: "
					+ schedulerName);
		}

		try {
			targetScheduler.pauseAll();
		} catch (SchedulerException e) {
			throw new RuntimeException("Unable to pause scheduler: "
					+ schedulerName, e);
		}
	}

	public void resume(String schedulerName) {
		Scheduler targetScheduler = getScheduler(schedulerName);
		if (targetScheduler == null) {
			throw new IllegalArgumentException("Scheduler does not exist: "
					+ schedulerName);
		}

		try {

			targetScheduler.resumeAll();
		} catch (SchedulerException e) {
			throw new RuntimeException("Unable to resume scheduler: "
					+ schedulerName, e);
		}
	}

	private Scheduler getScheduler(String schedulerName) {
		synchronized (schedulerLock) {
			return schedulers.get(schedulerName);
		}
	}

	private void clearAllTriggers(String schedulerName)
			throws SchedulerException {
		Scheduler targetScheduler = getScheduler(schedulerName);
		targetScheduler.pauseAll();
		String[] triggerNames = targetScheduler.getTriggerNames(schedulerName);
		for (String triggerName : triggerNames) {
			targetScheduler.unscheduleJob(triggerName, schedulerName);
		}
	}

	public void initJob(IJobConfig<? extends SchedulableJob> job) {
		String jobName = job.getName();
		int concurrency = job.getConcurrency();
		Class<? extends SchedulableJob> implementationClass = job
				.getImplementationClass();

		if (jobName == null || implementationClass == null) {
			throw new IllegalArgumentException("Invalid job definition");
		}

		if (schedulerService.hasScheduler(jobName) == false) {
			schedulerService.addScheduler(jobName, concurrency);
			schedulerService.addTask(jobName, jobName, implementationClass);
			schedulerService.updateTriggers(jobName, job);
			schedulerService.startScheduler(jobName);
		} else {
			log.info("Job " + jobName
					+ " exists already, skipping initialization.");
		}

		log.info("Initialized " + jobName + " successfully.");
	}

	void updateJob(IJobConfig<SchedulableJob> job) {
		String jobName = job.getName();
		if ((schedulerService.hasScheduler(jobName) == false)) {
			if (job.isEnabled()) {
				Class<SchedulableJob> implementationClass = job
						.getImplementationClass();
				schedulerService.addScheduler(jobName, job.getConcurrency());
				schedulerService.addTask(jobName, jobName, implementationClass);
				schedulerService.updateTriggers(job.getName(), job);
				schedulerService.startScheduler(job.getName());
			}
		} else {
			// Scheduler already exists.
			schedulerService.updateTriggers(job.getName(), job);
			if (job.isEnabled()) {
				schedulerService.startScheduler(job.getName());
			}
		}
	}

	/**
	 * Update how jobs should be triggered. This is based on Job configuration
	 * data from the JOBS table.
	 * 
	 * @param schedulerName
	 *            defines name of scheduler.
	 * @param job
	 *            defines various scheduler configuration attributes.
	 * @throws ServiceException
	 */
	synchronized void updateTriggers(String schedulerName,
			IJobConfig<? extends SchedulableJob> job) {
		Scheduler targetScheduler = getScheduler(schedulerName);
		if (targetScheduler == null) {
			throw new IllegalArgumentException("Scheduler does not exist: "
					+ schedulerName);
		}

		try {
			clearAllTriggers(schedulerName);
		} catch (SchedulerException e) {
			throw new RuntimeException("Unable to clear triggers of: "
					+ schedulerName);
		}
		if (job.isEnabled()) {
			Trigger triggers[] = makeCronTriggers(job);

			for (Trigger trigger : triggers) {
				log.info("Installing trigger: " + schedulerName);
				try {
					targetScheduler.scheduleJob(trigger);
				} catch (SchedulerException e) {
					throw new RuntimeException("Unable to update trigger: "
							+ trigger + " for:" + schedulerName);
				}
			}
		}
		resume(schedulerName);
	}

	private static Scheduler makeScheduler(String schedulerName, int threadCount)
			throws SchedulerException {
		Properties schedulerConfig = new Properties();
		schedulerConfig.put("org.quartz.threadPool.class",
				"org.quartz.simpl.SimpleThreadPool");
		schedulerConfig.put("org.quartz.threadPool.threadPriority", "5");
		schedulerConfig.put("org.quartz.jobStore.misfireThreshold", "60000");
		schedulerConfig.put("org.quartz.jobStore.class",
				"org.quartz.simpl.RAMJobStore");
		schedulerConfig.put("org.quartz.scheduler.instanceName", schedulerName);
		schedulerConfig.put("org.quartz.threadPool.threadCount",
				Integer.toString(threadCount));
		StdSchedulerFactory factory = new StdSchedulerFactory();
		factory.initialize(schedulerConfig);
		return factory.getScheduler();
	}

	private static Trigger[] makeCronTriggers(
			IJobConfig<? extends SchedulableJob> job) {
		final int concurrency = job.getConcurrency();
		String cronExpression = job.getCronExpression();
		// Store task type parameter which is used as a selector for retrieving
		// TASKS of the desired type.
		JobDataMap startUpParameters = new JobDataMap();
		if (job.getStartupParameter() != null) {
			startUpParameters.put("TASK_TYPE", job.getStartupParameter());
		}
		Trigger[] triggers = new Trigger[concurrency];
		String jobName = job.getName();
		log.info(String.format("Initializing cron trigger for %s with >> %s",
				jobName, cronExpression));
		for (int triggerCounter = 0; triggerCounter < triggers.length; triggerCounter++) {
			try {
				triggers[triggerCounter] = new CronTrigger(
						Integer.toString(triggerCounter), jobName,
						cronExpression);
			} catch (ParseException exception) {
				throw new RuntimeException("Unable to parse cron expression: "
						+ cronExpression);
			}
			triggers[triggerCounter].setJobGroup(jobName);
			triggers[triggerCounter].setJobName(jobName);
			triggers[triggerCounter].setJobDataMap(startUpParameters);
		}
		return triggers;
	}
}
