package com.nokia.ads.common.net.thrift;

import java.net.ConnectException;
import java.net.SocketException;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import com.nokia.ads.common.net.thrift.PersistentThriftSocketPool.PoolName;
import com.nokia.ads.common.net.thrift.PersistentThriftSocketPool.TWrapper;
import com.nokia.ads.common.util.Log;
import com.nokia.ads.rawlog.thrift.query.SearchException;

abstract public class ThriftClient<T> {

	protected static final Log logger = Log.getLogger(ThriftClient.class);

	abstract protected T doWork(TProtocol protocol) throws TException, ConnectException, SocketException, SearchException;

	abstract protected String getPoolTag();

	public T run() {
		TTransport transport = null;
		TWrapper tw = null;

		try {
			logger.info("[ThriftClient.run] [begin] ");
			tw = PersistentThriftSocketPool.getInstance().getTSocket(PoolName.getInstance(getPoolTag()));
			transport = tw.getSocket();
			TProtocol protocol = new TBinaryProtocol(transport);
			return doWork(protocol);
		} catch (TTransportException x) {
			logger.info("[ThriftClient.run] [exceprion] "+ x);
			logger.warn(x);
			System.out.println(x);
			closeTransport(transport);
			try {
				tw = PersistentThriftSocketPool.getInstance().getTSocket(PoolName.getInstance(getPoolTag()));
				transport = tw.getSocket();
				TProtocol protocol = new TBinaryProtocol(transport);
				return doWork(protocol);
			} catch (Exception e) {
				logger.info("[ThriftClient.run] [exceprion] "+ e);
				logger.error(x);
				closeTransport(transport);
				return null;
			}

		} catch (ConnectException x) {
			logger.info("[ThriftClient.run] [exceprion] catch ConnectException"+ x);
			logger.error(x);
			closeTransport(transport);
		} catch (SocketException x) {
			logger.info("[ThriftClient.run] [exceprion] catch SocketException"+ x);
			logger.error(x);
			closeTransport(transport);
		} catch (TException x) {
			logger.info("[ThriftClient.run] [exceprion] catch TException "+ x);
			logger.error(x);
			closeTransport(transport);
		} catch (Exception x) {
			logger.info("[ThriftClient.run] [exceprion] catch Exception "+ x);
			logger.error(x);
		} finally {
			if (transport != null && transport.isOpen()) {
				try {
					PersistentThriftSocketPool.getInstance().releaseTSocket(tw);
				} catch (Exception e) {
					logger.info("[ThriftClient.run] [exceprion] finally "+ e);
					logger.error(e);
					closeTransport(transport);
				}
			}
		}
		return null;
	}

	private void closeTransport(TTransport transport) {
		if (transport != null && transport.isOpen()) {
			transport.close();
		}
		logger.warn("connect exception, close transport.");
	}

}
