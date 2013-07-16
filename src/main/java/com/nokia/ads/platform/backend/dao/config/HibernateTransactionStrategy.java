package com.nokia.ads.platform.backend.dao.config;

import java.util.Calendar;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.nokia.ads.common.util.Log;
import com.nokia.ads.platform.backend.core.dao.TransactionStrategy;

public class HibernateTransactionStrategy implements TransactionStrategy
{

	private static final Log log = Log
			.getLogger(HibernateTransactionStrategy.class);

	private static final ThreadLocal<TransactionStrategy> instance = new ThreadLocal<TransactionStrategy>();

	private HibernateTransactionStrategy()
	{
	}

	public static TransactionStrategy getInstance()
	{
		if (instance.get() == null)
		{
			instance.set(new HibernateTransactionStrategy());
		}
		return instance.get();
	}

	private Transaction transaction = null;
	private Calendar transactionTimer = null;

	public static void clearCache() {
		Session s = HibernateManager.getCurrentSession();
		if (s!=null) {
			s.flush();
			s.clear();
		}
	}
	
	@Override
	public void begin()
	{
		try {
			transaction = HibernateManager.getCurrentSession()
					.beginTransaction();
			transactionTimer = Calendar.getInstance();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		log.debug("[{0}] Transaction begin.", Thread.currentThread().getName());
	}

	@Override
	public void commit()
	{
		if (transaction != null)
		{
			long ts = Calendar.getInstance().getTimeInMillis() - transactionTimer.getTimeInMillis();
			transaction.commit();
			log.debug("[{0}] Transaction commit. [{1}ms]", Thread.currentThread().getName(), ts);
			//
			HibernateManager.getCurrentSession().clear();
		}
	}

	@Override
	public void rollback()
	{
		if (transaction != null && transaction.isActive()) {
			long ts = Calendar.getInstance().getTimeInMillis()
					- transactionTimer.getTimeInMillis();
			transaction.rollback();
			log.debug("[{0}] Transaction rollback. [{1}ms]", Thread
					.currentThread().getName(), ts);
			//
			HibernateManager.getCurrentSession().clear();
		}
	}

	@Override
	@Deprecated
	public void close()
	{
		/*
		if (HibernateManager.getCurrentSession().isOpen())
		{
			HibernateManager.getCurrentSession().close();
		}
		*/
	}

	@Override
	@Deprecated
	public void open(boolean stateful)
	{
		if (stateful == false)
			throw new IllegalArgumentException("Do not support stateless session");
	}

}
