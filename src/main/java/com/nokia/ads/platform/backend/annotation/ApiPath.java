package com.nokia.ads.platform.backend.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * define the path of api service
 * 
 * @author kenliu
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ApiPath {
	/**
	 * path of the api service, starts with '/' the value looks like absolute
	 * path, but will merge with servlet context, so it is actually a relative
	 * path
	 * 
	 * @return
	 */
	String value();
}
