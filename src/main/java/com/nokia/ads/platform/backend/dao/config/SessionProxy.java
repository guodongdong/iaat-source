package com.nokia.ads.platform.backend.dao.config;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.StatelessSession;
import org.hibernate.Transaction;


@Deprecated
final public class SessionProxy {

	private Session session;
	private StatelessSession statelessSession;

	public void close() {
		session.close();
		statelessSession.close();
	}

	public Transaction beginTransaction() {
		session.beginTransaction();
		return statelessSession.beginTransaction();
	}

	public Transaction getTransaction() {
		session.getTransaction();
		return statelessSession.getTransaction();
	}

	public SQLQuery createSQLQuery(String sql) throws HibernateException {
		session.createSQLQuery(sql);
		return statelessSession.createSQLQuery(sql);
	}

	public Query createQuery(String query) {
		session.createQuery(query);
		return statelessSession.createQuery(query);
	}

	public Query getNamedQuery(String query) {
		session.getNamedQuery(query);
		return statelessSession.getNamedQuery(query);
	}

	public Criteria createCriteria(Class<?> clz) {
		session.createCriteria(clz);
		return statelessSession.createCriteria(clz);
	}

	public Criteria createCriteria(Class<?> clz, String arg1) {
		session.createCriteria(clz, arg1);
		return statelessSession.createCriteria(clz, arg1);
	}

	public Criteria createCriteria(String arg0) {
		session.createCriteria(arg0);
		return statelessSession.createCriteria(arg0);
	}

	public Criteria createCriteria(String arg0, String arg1) {
		session.createCriteria(arg0, arg1);
		return statelessSession.createCriteria(arg0, arg1);
	}

	public void refresh(Object obj) {
		session.refresh(obj);
		statelessSession.refresh(obj);
	}

	public Object get(String arg0, java.io.Serializable arg1) {
		session.get(arg0, arg1);
		return statelessSession.get(arg0, arg1);
	}

	public Object get(Class<?> arg0, java.io.Serializable arg1) {
		session.get(arg0, arg1);
		return statelessSession.get(arg0, arg1);
	}

	public void update(Object arg0) {
		session.update(arg0);
		statelessSession.update(arg0);
	}

	public void update(String arg0, Object arg1) {
		session.update(arg0, arg1);
		statelessSession.update(arg0, arg1);
	}

	public void delete(Object arg0) {
		session.delete(arg0);
		statelessSession.delete(arg0);
	}

	public void delete(String arg0, Object arg1) {
		session.delete(arg0, arg1);
		statelessSession.delete(arg0, arg1);
	}

	public Object insert(Object arg0) {
		session.persist(arg0);
		return statelessSession.insert(arg0);
	}

	public Object insert(String arg0, Object arg1) {
		session.persist(arg0, arg1);
		return statelessSession.insert(arg0, arg1);
	}

}
