package com.nokia.ads.platform.backend.i18n;

import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageResource {

	//默认local
	private String localeCode = "zh_CN";
	
	//存放国际化资源
	private static HashMap<String,LanguageResource> instances = new HashMap<String,LanguageResource>();
	private Locale locale = null;
	private ResourceBundle resourceBundle = null;
	private LanguageResource lnkLingualResource;
	
	private LanguageResource(String language, String region,String file_name) {
		localeCode = makeKey(language, region,file_name);
		locale = new Locale(language, region);
		resourceBundle = ResourceBundle.getBundle(file_name, locale);

		lnkLingualResource = this;
		instances.put(localeCode, lnkLingualResource);

	}

	private LanguageResource() {
	}

	public synchronized static LanguageResource getInstance(String language,
			String region,String file_name) {
		
			if (instances.containsKey(makeKey(language, region,file_name))) {
				return instances.get(makeKey(language,region,file_name));
			} else {
				return new LanguageResource(language, region,file_name);
			}
	}

	public String getLocaleString(String key) {
		String s = resourceBundle.getString(key);
		return s;
	}

	private static String makeKey(String language, String region,String fileName) {
		return fileName + language + "_" + region;
	}

}
