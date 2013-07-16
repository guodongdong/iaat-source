package com.nokia.ads.platform.backend.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.nokia.ads.platform.backend.core.scheduler.GenericWorker;

/**
 * define the path of api service
 * 
 * @author kenliu
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TaskWorker {
	/**
	 * @return
	 */
	Class<? extends GenericWorker> value();
}
