package com.nokia.ads.platform.backend.dao.config;

import org.hibernate.stat.Statistics;

import com.nokia.ads.common.util.Log;
import com.nokia.ads.platform.backend.core.webapi.ApiFilter;

public class HibernateSessionFilter implements ApiFilter
{

	private static Log log = Log.getLogger(HibernateSessionFilter.class);

	@Override
	public boolean doPrefix()
	{

		Statistics stat = HibernateManager.getStatistics();
		log.info("Stat: connection[{0}], sessionOpen[{1}], sessionClose[{2}]", stat.getConnectCount(), stat.getSessionOpenCount(), stat.getSessionCloseCount());

		HibernateManager.openSession();
		log.debug("[{0}] session open", Thread.currentThread().getName());

		return true;
	}

	@Override
	public boolean doPostfix()
	{
		HibernateManager.getCurrentSession().clear();
		HibernateManager.close();
		log.debug("[{0}] session close", Thread.currentThread().getName());

		return true;
	}

}
