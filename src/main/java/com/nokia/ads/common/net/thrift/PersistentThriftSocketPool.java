package com.nokia.ads.common.net.thrift;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.pool.KeyedPoolableObjectFactory;
import org.apache.commons.pool.impl.GenericKeyedObjectPool;
import org.apache.log4j.Logger;
import org.apache.thrift.transport.TSocket;

public class PersistentThriftSocketPool {

	protected static final Logger logger = Logger.getLogger(PersistentThriftSocketPool.class);

	public static final int POOL_MAX_ACTIVE = 2000;

	private Map<PoolName, String> serverHosts;
	private Map<PoolName, Integer> serverPort;
	private Map<PoolName, List<TNode>> serverToTNodeMap;
	private GenericKeyedObjectPool socketPool;
	private boolean usePersistentConnections;
	private static volatile boolean initialized;
	private static volatile PersistentThriftSocketPool instance;

	public static PersistentThriftSocketPool getInstance() {
		if (instance == null) {
			synchronized (PersistentThriftSocketPool.class) {
				if (instance == null) {
					instance = new PersistentThriftSocketPool();
				}
			}
		}
		return instance;
	}

	private PersistentThriftSocketPool() {
		serverHosts = new HashMap<PoolName, String>();
		serverPort = new HashMap<PoolName, Integer>();
	}

	public static class PoolName {

		private static Map<String, PoolName> points = new ConcurrentHashMap<String, PoolName>();

		public static PoolName getInstance(String name) {
			if (points.containsKey(name))
				return points.get(name);

			synchronized (points) {
				PoolName p = new PoolName();
				p.name = name;
				points.put(name, p);
				return p;
			}
		}

		private String name;

		public String getName() {
			return this.name;
		}

		public static PoolName[] values() {
			return points.values().toArray(new PoolName[] {});
		}
	}

	public static class TWrapper {
		TNode node;
		TSocket socket;

		TWrapper(TNode tN, TSocket tS) {
			node = tN;
			socket = tS;
		}

		public TNode getNode() {
			return node;
		}

		public TSocket getSocket() {
			return socket;
		}
	}

	public static class TNode {
		private PoolName serverPoint;
		private String host;
		private int port;

		@Override
		public String toString() {
			return serverPoint + ":" + host + ":" + port;
		}

		public TNode(PoolName point, String host, int port) {
			this.serverPoint = point;
			this.host = host;
			this.port = port;
		}

		public PoolName getPoolName() {
			return serverPoint;
		}

		public void setPoolName(PoolName point) {
			this.serverPoint = point;
		}

		public String getHost() {
			return host;
		}

		public void setHost(String host) {
			this.host = host;
		}

		public int getPort() {
			return port;
		}

		public void setPort(int port) {
			this.port = port;
		}
	}

	private static class ThriftSocketPoolFactory implements KeyedPoolableObjectFactory {
		@Override
		public void activateObject(Object arg0, Object arg1) throws Exception {
			TNode tn = (TNode) arg0;
			TSocket ts = (TSocket) arg1;
			if (ts == null || !ts.isOpen()) {
				if (ts != null) {
					ts.close();
				}
				ts = new TSocket(tn.getHost(), tn.getPort(), 600000);
				ts.open();
			}
		}

		@Override
		public void destroyObject(Object arg0, Object arg1) throws Exception {
			TSocket ts = (TSocket) arg1;
			ts.close();
		}

		@Override
		public Object makeObject(Object arg0) throws Exception {
			TNode tn = (TNode) arg0;
			TSocket ts = new TSocket(tn.getHost(), tn.getPort(), 600000);
			ts.open();
			return ts;
		}

		@Override
		public void passivateObject(Object arg0, Object arg1) throws Exception {
		}

		@Override
		public boolean validateObject(Object arg0, Object arg1) {
			TSocket ts = (TSocket) arg1;
			return ts != null && ts.isOpen();
		}
	}

	public TWrapper getTSocket(PoolName a) throws Exception {
		if (!usePersistentConnections) {
			logger.error("Calling getTSocket when usePersistentConnections is false");
			return null;
		}
		if (!initialized) {
			synchronized (instance) {
				if (!initialized) {
					serverToTNodeMap = new HashMap<PoolName, List<TNode>>();
					socketPool = new GenericKeyedObjectPool(new ThriftSocketPoolFactory());
					socketPool.setMaxActive(POOL_MAX_ACTIVE);
					socketPool.setWhenExhaustedAction(GenericKeyedObjectPool.WHEN_EXHAUSTED_GROW);
					socketPool.setMinEvictableIdleTimeMillis(1000 * 30);
					socketPool.setTimeBetweenEvictionRunsMillis(1000 * 60 * 5);
					for (PoolName app : PoolName.values()) {
						List<TNode> appList = new ArrayList<TNode>();
						serverToTNodeMap.put(app, appList);
						String hosts = this.serverHosts.get(app);
						if (hosts == null)
							continue;
						for (String host : hosts.split(",")) {
							appList.add(new TNode(app, host, this.serverPort.get(app)));
						}
					}
					logger.info("Using persistent thrift connections:" + describeSocketPool());
					initialized = true;
				}
			}
		}
		List<TNode> hList = serverToTNodeMap.get(a);
		int index = new Random().nextInt(hList.size());
		TWrapper tw = new TWrapper(hList.get(index), (TSocket) socketPool.borrowObject(hList.get(index)));
		return tw;
	}

	public void releaseTSocket(TWrapper tw) throws Exception {
		if (!usePersistentConnections || !initialized) {
			logger.error("Calling releaseTSocket when usePersistentConnections is false or instance is not initialized");
			return;
		}
		socketPool.returnObject(tw.getNode(), tw.getSocket());
	}

	public void setHost(PoolName app, String host) {
		serverHosts.put(app, host);
	}

	public void setPort(PoolName app, int port) {
		serverPort.put(app, port);
	}

	public boolean getUsePersistentConnections() {
		return usePersistentConnections;
	}

	public void setUsePersistentConnections(boolean usePersistentConnections) {
		this.usePersistentConnections = usePersistentConnections;
	}

	public String describeSocketPool() {
		if (socketPool == null) {
			return null;
		}
		StringBuilder b = new StringBuilder();
		b.append(" lifo: ").append(socketPool.getLifo());
		b.append(" maxActive: ").append(socketPool.getMaxActive());
		b.append(" maxIdle: ").append(socketPool.getMaxIdle());
		b.append(" maxTotal: ").append(socketPool.getMaxTotal());
		b.append(" maxWait: ").append(socketPool.getMaxWait());
		b.append(" minEvictableIdleTimeMillis: ").append(socketPool.getMinEvictableIdleTimeMillis());
		b.append(" minIdle: ").append(socketPool.getMinIdle());
		b.append(" numActive: ").append(socketPool.getNumActive());
		b.append(" numIdle: ").append(socketPool.getNumIdle());
		b.append(" numTestsPerEvictionRun: ").append(socketPool.getNumTestsPerEvictionRun());
		b.append(" testOnBorrow: ").append(socketPool.getTestOnBorrow());
		b.append(" testOnReturn: ").append(socketPool.getTestOnReturn());
		b.append(" testWhileIdle: ").append(socketPool.getTestWhileIdle());
		b.append(" timeBetweenEvictionRunsMillis: ").append(socketPool.getTimeBetweenEvictionRunsMillis());
		b.append(" whenExaustedAction: ").append(socketPool.getWhenExhaustedAction());
		return b.toString();
	}
}
