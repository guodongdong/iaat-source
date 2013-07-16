package com.iaat.servlet;

import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.iaat.scheduler.JobProvider;
import com.nokia.ads.common.util.Log;
import com.nokia.ads.platform.backend.core.scheduler.IJobConfig;
import com.nokia.ads.platform.backend.core.scheduler.SchedulableJob;
import com.nokia.ads.platform.backend.core.scheduler.SchedulerService;

public class StartupServlet extends HttpServlet implements Servlet {

	private static final long serialVersionUID = 1L;

	private static final Log log = Log.getLogger(StartupServlet.class);

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		// initialize quartz
		List<IJobConfig<? extends SchedulableJob>> jobSettings = JobProvider.getInstance().getJobSettings();
		if (jobSettings != null) {
			for (IJobConfig<? extends SchedulableJob> job : jobSettings) {
				SchedulerService.getInstance().initJob(job);
				log.info("initialized job: {0}", job.getName());
			}
		}
		// initialize proxies
	}
}
