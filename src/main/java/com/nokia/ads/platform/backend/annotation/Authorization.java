package com.nokia.ads.platform.backend.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.nokia.ads.platform.backend.core.auth.IAuthScope;

/**
 * use <code>@Authorization(scopeCheck=IAuthScope.class)</code> to check the
 * authorization
 * 
 * @author kenliu
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Authorization {
	/**
	 * the authorization and scope validation class, this class must implement
	 * {@link IAuthScope} interface
	 * 
	 * @return
	 */
	Class<? extends IAuthScope<?, ?>> value();
	
	String paramKey() default "";
}
