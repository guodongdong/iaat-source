package com.iaat.dao.impl.latitude;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import com.iaat.dao.latitude.LogSourceDao;
import com.iaat.model.LogSourceBean;
import com.nokia.ads.common.util.Log;

public class LogSourceDaoImpl implements LogSourceDao {
	private final static Log log = Log.getLogger(LogSourceDaoImpl.class);
	protected static ResourceBundle logSourceBundle = null;
	static {
		logSourceBundle = ResourceBundle.getBundle("config.logs",Locale.getDefault());
	}
	@Override
	public List<LogSourceBean> getAllLogSourceBeans() {
		log.info("[LogSourceDaoImpl.getAllLogSourceBeans][begin]");
		List<LogSourceBean> list = null;
		try {
			list = new ArrayList<LogSourceBean>();
			Enumeration<String> keys = logSourceBundle.getKeys();
			LogSourceBean logSourceBean = null;
			while(keys.hasMoreElements()){
				logSourceBean = new LogSourceBean();
				String key = keys.nextElement();
				String value = logSourceBundle.getString(key);
				logSourceBean.setLogName(key);
				logSourceBean.setLogId(Long.valueOf(value));
				list.add(logSourceBean);
			}
		} catch (Exception e) {
			log.error("[LogSourceDaoImpl.getAllLogSourceBeans] [{0}]", e.getMessage());
		}
		log.info("[LogSourceDaoImpl.getAllLogSourceBeans][end]");
		return list;
	}
}
