package com.nokia.ads.platform.backend.core.scheduler;

public interface IJobConfig<T extends SchedulableJob> {
	Long getJobId();
	String getName();
	int getConcurrency();
	String getCronExpression();
	Class<T> getImplementationClass();
	String getStartupParameter();
	boolean isEnabled();
	int getTimeout();
}
