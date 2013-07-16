package com.nokia.ads.platform.backend.core.validator;

/**
 * define the interface of a validator for particular object type
 * 
 * @author kenliu
 * 
 * @param <T>
 *            the type of target object
 */
public interface IValidator<T> {
	/**
	 * validate the object
	 * 
	 * @param object
	 * @return check if the target object is valid
	 */
	boolean validate(T object);

	/**
	 * validate the object in specific level, level should be defined in current
	 * validator
	 * 
	 * @param object
	 * @param level
	 * @return check if the target object is valid
	 */
	boolean validate(T object, String level);

	/**
	 * get error messages
	 * 
	 * @return error messages
	 */
	String[] getMessages();
}
