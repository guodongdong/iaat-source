package com.nokia.ads.platform.backend.i18n;

/**
 * @name LanguageHelper
 * 
 * @description CLASS_DESCRIPTION
 * 
 *              MORE_INFORMATION
 * 
 * @author lijing
 * 
 * @since 2011-6-9
 * 
 * @version 1.0
 */
public class LanguageHelper {
	// public static final String TOMCATPATH =
	// LanguageHelper.class.getClassLoader().getResource("").getPath().replace("bin/",
	// "")

	private static LanguageResource resource = LanguageResource.getInstance(
			"zh", "cn", "config.i18n.report_task");

	static {

	}

	public static String getI18NValue(String key) {
		return resource.getLocaleString(key);
	}

}
