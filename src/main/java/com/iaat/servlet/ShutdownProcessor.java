package com.iaat.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.nokia.ads.common.util.Log;
import com.nokia.ads.platform.backend.core.scheduler.SchedulerService;

public class ShutdownProcessor implements ServletContextListener {
	private static Log log = Log.getLogger(ShutdownProcessor.class);

	public void contextDestroyed(ServletContextEvent event) {
		log.debug("Received context destroyed event.");
		try {
			// Wait for job completion then shut down.
			SchedulerService.getInstance().shutdown(true);
		} catch (Exception exception) {
			log.error("Unable to properly shutdown SchedulerService: ",
					exception);
		}
		
	}

	public void contextInitialized(ServletContextEvent event) {
		//DO NOTHING
	}
}
