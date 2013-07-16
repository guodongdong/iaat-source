package com.nokia.ads.platform.backend.proxy;

import com.nokia.ads.platform.backend.model.ModelObject;

public class LogThreadLocal {

	private static ThreadLocal<ModelObject<Long>> threadLocal = new ThreadLocal<ModelObject<Long>>();

	public static ModelObject<Long> get() {
		return threadLocal.get();
	}

	public static void set(ModelObject<Long> modelObj) {
		threadLocal.set(modelObj);
	}
}
