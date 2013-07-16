package com.nokia.ads.platform.backend.core.validator;

import com.nokia.ads.platform.backend.annotation.Validation;

/**
 * find the validator for target object, and helper class for object validation
 * 
 * @author kenliu
 * 
 */
public class RoyalValidator {

	/**
	 * validate object by using the validator in annotation
	 * 
	 * @param <T>
	 * @param o
	 * @throws ValidationException
	 */
	public static <T> void validate(T o) throws ValidationException {
		IValidator<T> validator = getValidator(o);
		if (validator != null && validator.validate(o) == false) {
			// validation failure
			ValidationException ex = new ValidationException();
			ex.setMessages(validator.getMessages());
			throw ex;
		}
	}

	/**
	 * get the validator of target object
	 * 
	 * @param <T>
	 * @param o
	 * @return validator
	 */
	@SuppressWarnings("unchecked")
	public static <T> IValidator<T> getValidator(T o) {
		try {
			if (o != null && o.getClass() != null) {
				Validation v = o.getClass().getAnnotation(Validation.class);
				Class<? extends IValidator<?>> clz = v.value();
				// create a instance of validator
				return (IValidator<T>) clz.newInstance();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
