package com.nokia.ads.platform.backend.core.validator;

/**
 * exception when encounter invalid object, the {@code getMessages()} contains
 * the exact error messages
 * 
 * @author kenliu
 * 
 */
public class ValidationException extends Exception {

	private static final long serialVersionUID = 1L;

	private String[] messages;

	public String[] getMessages() {
		return this.messages;
	}

	public void setMessages(String[] messages) {
		this.messages = messages;
	}

}
