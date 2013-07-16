package com.nokia.ads.common.net.thrift;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransportException;

import com.nokia.ads.common.stats.ConversionType;
import com.nokia.ads.common.stats.FrequencyStatistic;
import com.nokia.ads.common.stats.PrecisePercentageStatistic;
import com.nokia.ads.common.stats.ThroughputStatistic;
import com.nokia.ads.common.stats.Statistic.RRDType;

public class TSocketPool {
	private static boolean statsEnabled = false;
	// these maps map the hostname to an object - the port is not used - we
	// assume for convenience that a host never runs two services
	// we make this assumption so that graphite names don't need a port
	private static final Map<String, PrecisePercentageStatistic> CONNECT_TIMEOUT_RATE = new HashMap<String, PrecisePercentageStatistic>();
	private static final Map<String, PrecisePercentageStatistic> READ_TIMEOUT_RATE = new HashMap<String, PrecisePercentageStatistic>();
	private static final Map<String, FrequencyStatistic> FAILURE_FREQUENCY = new HashMap<String, FrequencyStatistic>();
	private static final Map<String, ThroughputStatistic> RPS = new HashMap<String, ThroughputStatistic>();
	private static Map<String, Integer> consecutiveFailuresMap_ = new ConcurrentHashMap<String, Integer>();
	private static Map<String, Long> lastFailTimeMap_ = new ConcurrentHashMap<String, Long>();
	private static final ReentrantLock rlock = new ReentrantLock();

	public static boolean isStatsEnabled() {
		return statsEnabled;
	}

	public static void setStatsEnabled(boolean statsEnabled) {
		TSocketPool.statsEnabled = statsEnabled;
	}

	private static PrecisePercentageStatistic getConnectTimeout(String host) {
		PrecisePercentageStatistic stat = CONNECT_TIMEOUT_RATE.get(host);
		if (stat == null) {
			rlock.lock();
			try {
				stat = CONNECT_TIMEOUT_RATE.get(host);
				if (stat == null) {
					stat = new PrecisePercentageStatistic("",
							"services.tsocketpool.connect_timeout_rate."
									+ host.split("\\.")[0].toLowerCase(),
							"Percentage of the time the connect times out.",
							ConversionType.NONE, ConversionType.NONE, true,
							RRDType.GRAPHITE);
					CONNECT_TIMEOUT_RATE.put(host, stat);
				}
			} finally {
				rlock.unlock();
			}
		}
		return stat;
	}

	private static ThroughputStatistic getRPS(String host) {
		ThroughputStatistic stat = RPS.get(host);
		if (stat == null) {
			rlock.lock();
			try {
				stat = RPS.get(host);
				if (stat == null) {
					stat = new ThroughputStatistic("",
							"services.tsocketpool.connects_per_second."
									+ host.split("\\.")[0].toLowerCase(),
							"Connects per second.", 1, ConversionType.NONE,
							ConversionType.NONE, true, RRDType.GRAPHITE);
					RPS.put(host, stat);
				}
			} finally {
				rlock.unlock();
			}
		}
		return stat;
	}

	private static PrecisePercentageStatistic getReadTimeout(String host) {
		PrecisePercentageStatistic stat = READ_TIMEOUT_RATE.get(host);
		if (stat == null) {
			rlock.lock();
			try {
				stat = READ_TIMEOUT_RATE.get(host);
				if (stat == null) {
					stat = new PrecisePercentageStatistic("",
							"services.tsocketpool.read_timeout_rate."
									+ host.split("\\.")[0].toLowerCase(),
							"Percentage of the time the read times out.",
							ConversionType.NONE, ConversionType.NONE, true,
							RRDType.GRAPHITE);
					READ_TIMEOUT_RATE.put(host, stat);
				}
			} finally {
				rlock.unlock();
			}
		}
		return stat;
	}

	private static FrequencyStatistic getFailureFrequency(String host) {
		FrequencyStatistic stat = FAILURE_FREQUENCY.get(host);
		if (stat == null) {
			rlock.lock();
			try {
				stat = FAILURE_FREQUENCY.get(host);
				if (stat == null) {
					stat = new FrequencyStatistic("",
							"services.tsocketpool.failure_frequency."
									+ host.split("\\.")[0].toLowerCase(),
							"Failure frequency of the host",
							ConversionType.NONE, ConversionType.NONE, true,
							RRDType.GRAPHITE);
					FAILURE_FREQUENCY.put(host, stat);
				}
			} finally {
				rlock.unlock();
			}
		}
		return stat;
	}

	public void readTimeoutHit() {
		if (TSocketPool.isStatsEnabled()) {
			getReadTimeout(getCurrentServerHost()).hit();
		}
	}

	public void readTimeoutMiss() {
		if (TSocketPool.isStatsEnabled()) {
			getReadTimeout(getCurrentServerHost()).miss();
		}
	}

	private class TSocketPoolServer extends TSocket {
		// Host name
		public String host_ = null;

		// Port to connect on
		public int port_ = -1;

		/**
		 * Constructor for TSocketPool server
		 */
		public TSocketPoolServer(String host, int port, int timeout) {
			super(host, port, timeout);
			host_ = host;
			port_ = port;
		}

		@Override
		public String toString() {
			return host_ + ":" + port_;
		}

		@Override
		public boolean equals(Object o) {
			if (o == this) {
				return true;
			} else if (!(o instanceof TSocketPoolServer)) {
				return false;
			} else {
				TSocketPoolServer other = (TSocketPoolServer) o;
				return other.port_ == this.port_
						&& other.host_.equals(this.host_);
			}
		}

		@Override
		public int hashCode() {
			HashCodeBuilder builder = new HashCodeBuilder(17, 31);
			builder.append(port_);
			builder.append(host_);
			return builder.toHashCode();
		}
	}

	int numRetries_ = 1;
	long retryInterval_ = 60;
	int maxConsecutiveFailures_ = 1;
	boolean randomize_ = true;
	boolean alwaysTryLast_ = true;
	List<TSocketPoolServer> servers_;
	int currentServer_ = 0;
	int connectTimeouts = 0;

	/**
	 * Socket pool constructor
	 * 
	 * @param hosts
	 *            list of host names
	 * @param port
	 *            port names
	 * @param timeout
	 *            Socket timeout
	 * @param connectTimeout
	 *            Connect timeout
	 */
	public TSocketPool(String[] hosts, int port, int timeout) {
		servers_ = new ArrayList<TSocketPoolServer>();

		for (int index = 0; index < hosts.length; index++) {
			addServer(hosts[index], port, timeout);
		}
	}

	/**
	 * Socket pool constructor
	 * 
	 * @param host
	 *            single host
	 * @param port
	 *            single port
	 * @param timeout
	 *            Socket timeout
	 * @param connectTimeout
	 *            Connect timeout
	 */
	public TSocketPool(String host, int port, int timeout) {
		servers_ = new ArrayList<TSocketPoolServer>();
		addServer(host, port, timeout);
	}

	/**
	 * Add a server to the pool
	 */
	public void addServer(String host, int port, int timeout) {
		TSocketPoolServer newServer = new TSocketPoolServer(host, port, timeout);
		servers_.add(newServer);
	}

	/**
	 * Sets how many times to keep retrying a host in the connect function.
	 */
	public void setNumRetries(int numRetries) {
		numRetries_ = numRetries;
	}

	/**
	 * Sets how long to wait until retrying a host if it was marked down
	 */
	public void setRetryInterval(int retryInterval) {
		retryInterval_ = retryInterval;
	}

	/**
	 * Sets how many times to keep retrying a host before marking it as down.
	 */
	public void setMaxConsecutiveFailures(int maxConsecutiveFailures) {
		maxConsecutiveFailures_ = maxConsecutiveFailures;
	}

	/**
	 * Turns randomization in connect order on or off.
	 */
	public void setRandomize(boolean randomize) {
		randomize_ = randomize;
	}

	/**
	 * Whether to always try the last server.
	 */
	public void setAlwaysTryLast(boolean alwaysTryLast) {
		alwaysTryLast_ = alwaysTryLast;
	}

	public int getConnectTimeouts() {
		return connectTimeouts;
	}

	public void open() throws TTransportException {
		connectTimeouts = 0;
		if (randomize_) {
			Collections.shuffle(servers_);
		}

		int numServers = servers_.size();
		for (int i = 0; i < numServers; ++i) {
			currentServer_ = i;
			TSocketPoolServer server = getCurrentServer();
			String host = getCurrentServerHost();

			Long lastFailTime = TSocketPool.lastFailTimeMap_.get(host);

			if (lastFailTime == null) {
				lastFailTime = new Long(0);
			}

			boolean retryIntervalPassed = lastFailTime == 0;
			boolean isLastServer = alwaysTryLast_ ? (i == (numServers - 1))
					: false;

			if (server.isOpen()) {
				// already open means we're done
				return;
			}

			if (lastFailTime > 0) {
				// The server was marked as down, so check if enough time has
				// elapsed to retry
				long elapsedTime = (System.currentTimeMillis() - lastFailTime) / 1000;
				if (elapsedTime > retryInterval_) {
					retryIntervalPassed = true;
				}
			}

			if (retryIntervalPassed || isLastServer) {
				TSocketPool.getRPS(host).update();
				for (int j = 0; j < numRetries_; j++) {
					try {
						server.open();

						// reset lastFailTime_ is required
						if (lastFailTime != 0) {
							TSocketPool.lastFailTimeMap_.put(host, new Long(0));
						}

						// success
						if (TSocketPool.isStatsEnabled()) {
							TSocketPool.getConnectTimeout(host).miss();
						}

						return;
					} catch (TTransportException e) {
						if (TSocketPool.isStatsEnabled()) {
							TSocketPool.getConnectTimeout(host).hit();
						}
						connectTimeouts++;
					}
				}

				Integer consecutiveFailures = TSocketPool.consecutiveFailuresMap_
						.get(host);

				if (consecutiveFailures == null) {
					consecutiveFailures = new Integer(0);
				}

				++consecutiveFailures;

				if (consecutiveFailures > maxConsecutiveFailures_) {
					// Mark server as down
					if (TSocketPool.isStatsEnabled()) {
						TSocketPool.getFailureFrequency(host).increment();
					}
					TSocketPool.consecutiveFailuresMap_.put(host,
							new Integer(0));
					TSocketPool.lastFailTimeMap_.put(host,
							System.currentTimeMillis());
				} else {
					TSocketPool.consecutiveFailuresMap_.put(host,
							consecutiveFailures);
				}
			}
		}

		throw new TTransportException(TTransportException.NOT_OPEN,
				"TSocketPool: All hosts in pool are down. (" + servers_ + ")");
	}

	public TSocketPoolServer getCurrentServer() {
		if (servers_ != null && servers_.get(currentServer_) != null) {
			return servers_.get(currentServer_);
		}
		return null;
	}

	public String getCurrentServerHost() {
		if (servers_ != null && servers_.get(currentServer_) != null) {
			return servers_.get(currentServer_).host_;
		}
		return null;
	}

	public int getCurrentServerPort() {
		if (servers_ != null && servers_.get(currentServer_) != null) {
			return servers_.get(currentServer_).port_;
		}
		return -1;
	}
}
