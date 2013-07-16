package com.nokia.ads.platform.backend.dao.config;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.cfg.Configuration;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.stat.Statistics;

import com.nokia.ads.common.util.Log;
import com.nokia.ads.common.util.StringUtils;

public class HibernateManager {

	private static final Log log = Log.getLogger(HibernateManager.class);

	/**
	 * classpath:/config/hibernate/hibernate.cfg.xml
	 */
	private static final String cfgPath;

	private static final SessionFactory sessionFactory;

	static {

		String envCfgPath = System.getenv().get("HIBERNATE_CONFIG");
		if (StringUtils.hasLength(envCfgPath))
			cfgPath = envCfgPath;
		else
			cfgPath = "/config/hibernate/hibernate.cfg.xml";

		try {
			HibernateFetchPolicy.Init();
			// Create the SessionFactory from hibernate.cfg.xml
			sessionFactory = new Configuration().configure(cfgPath).buildSessionFactory();
		} catch (Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			log.error("Initial SessionFactory creation failed.", ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	private static final ThreadLocal<Session> localSession = new ThreadLocal<Session>();

	public static synchronized Session getCurrentSession() {
		log.debug("[{0}] getCurrentSession: {1} ", Thread.currentThread().getName(), localSession);
		Session session = localSession.get();
		if (session == null || !session.isOpen()) {
			openSession();
		}
		return localSession.get();
	}

	public static synchronized Session openSession() {
		Session session = localSession.get();

		if (session != null && session.isOpen()) {
			log.debug("[{0}] openSession in reuse: {1}", Thread.currentThread().getName(), localSession);
			return session;
		}

		localSession.set(sessionFactory.openSession());
		log.debug("[{0}] openSession new: {1}", Thread.currentThread().getName(), localSession);
		return localSession.get();

	}

	public static Statistics getStatistics() {
		return sessionFactory.getStatistics();
	}

	/*
	 * public static synchronized void close() {
	 * log.debug("[{0}] session close: {1}", Thread.currentThread().getName(),
	 * localSession); if (localSession.get() != null) {
	 * localSession.get().close(); localSession.set(null); } }
	 */
	public static synchronized void close() {
		log.info("[{0}] session close: {1}", Thread.currentThread().getName(), localSession);
		Session session = localSession.get();
		if (session != null) {
			try {
				if (session.isOpen()) {
					session.close();
				}
			} catch (Exception ex) {
				log.error("[{0}] session close Exception {1}", Thread.currentThread().getName(), localSession);
			} finally {
				localSession.set(null);
			}
		}
	}

	public static synchronized StatelessSession openStatelessSession() {
		return sessionFactory.openStatelessSession();
	}

	@SuppressWarnings("unchecked")
	public static <T> T getImplementation(T proxy) {
		if (proxy instanceof HibernateProxy) {
			return (T) ((HibernateProxy) proxy).getHibernateLazyInitializer().getImplementation();
		}
		return proxy;
	}

}
