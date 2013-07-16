package com.nokia.ads.platform.backend.core.dao;

public interface TransactionStrategy {
	void open(boolean stateful);
	void begin();
	void commit();
	void rollback();
	void close();
}
