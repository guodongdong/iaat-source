package com.nokia.ads.platform.backend.dao.config;

import java.lang.reflect.Method;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.collection.PersistentList;
import org.hibernate.collection.PersistentSet;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;
import org.hibernate.proxy.pojo.javassist.JavassistLazyInitializer;

import com.nokia.ads.platform.backend.core.dao.FetchPolicy;

public class HibernateFetchPolicy extends FetchPolicy {

	public static void Init() {
		FetchPolicy.Init(HibernateFetchPolicy.class);
	}

	@Override
	protected boolean beginTransaction() {
		boolean transactionNeeded = false;
		Session session = HibernateManager.getCurrentSession();
		if (session.isOpen()) {
			if (!session.getTransaction().isActive()) {
				transactionNeeded = true;
			}
			if (transactionNeeded) {
				session.getTransaction().begin();
			}
		}
		return transactionNeeded;
	}

	@Override
	protected void endTransaction() {
		Session session = HibernateManager.getCurrentSession();
		if (session.isOpen() && session.getTransaction().isActive()) {
			session.getTransaction().rollback();
		}
	}

	@Override
	protected void initialize(Object proxyObject) {
		Hibernate.initialize(proxyObject);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected <T> T newInstance(T obj) {
		if (obj == null)
			return null;
		try {
			return (T) Hibernate.getClass(obj).newInstance();
		} catch (Exception ex) {
			log.warn(ex);
			return null;
		}
	}

	@Override
	protected boolean isIgnoreReturnType(Method method) {
		if (method != null) {
			Class<?> clz = method.getReturnType();
			return LazyInitializer.class.equals(clz)
					|| JavassistLazyInitializer.class.equals(clz);
		}
		return true;
	}

	@Override
	protected boolean isIgnoreFields(String fieldName) {
		// return "remoteID".equals(fieldName) || "network".equals(fieldName);
		return false;
	}

	//

	public static Object getImplementation(Object bean) {
		if (bean instanceof HibernateProxy) {
			LazyInitializer lazyInitializer = ((HibernateProxy) bean)
					.getHibernateLazyInitializer();
			if (lazyInitializer.isUninitialized()) {
				// not initialized yet
				return null;
			} else
				// already initialized, get the real object
				return lazyInitializer.getImplementation();
		} else if (bean instanceof PersistentSet || bean instanceof PersistentList) {
			return null;
		}
		return bean;
	}
}
