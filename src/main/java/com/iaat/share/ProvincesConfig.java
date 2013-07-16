package com.iaat.share;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import com.nokia.ads.common.util.Log;

public class ProvincesConfig {
	private static final Log log = Log.getLogger(ProvincesConfig.class);
	private static final String PATH = "config/provinces.properties";
	private static Map<String, String> MAPPINGS = new HashMap<String, String>();
	private static InputStream inputStream = null;
	private static Properties properties = new Properties();
	static {
		try {
			URL url = ProvincesConfig.class.getClassLoader().getResource(PATH);
			inputStream = url.openStream();
			properties.load(inputStream);
			for (String name : properties.stringPropertyNames()) {
				MAPPINGS.put(name, properties.get(name).toString());
			}
		} catch (IOException e) {
			log.error("[SystemConfig.static] [ error:{0}]", e.getMessage());
			e.printStackTrace();
		} finally {
			if (null != inputStream)
				try {
					inputStream.close();
				} catch (IOException e) {
					log.error("[SystemConfig.static] [ error:{0}]", e
							.getMessage());
				}
		}
	}

	public static String getProvinceValue(String key) {
		return MAPPINGS.get(key);
	}
}
