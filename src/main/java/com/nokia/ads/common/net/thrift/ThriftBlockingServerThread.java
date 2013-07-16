package com.nokia.ads.common.net.thrift;

import java.util.concurrent.TimeUnit;

import org.apache.thrift.TProcessor;
import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.server.TThreadPoolServer.Args;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;
import org.apache.thrift.transport.TTransportFactory;

/**
 * Runs a blocking server for a Thrift service. Blocking servers are appropriate
 * when there are fewer than a couple of thousand simultaneous clients. For
 * these servers each request will get its own thread.
 */
public class ThriftBlockingServerThread extends ThriftServerThread {
	/**
	 * Maximum amount of time, in milliseconds, to wait on a socket sending any
	 * data before killing the connection. It is fine if there is a lot of
	 * information and the total transmission time takes several seconds so long
	 * as there is no break in the stream longer than this time interval.
	 */
	public static final int DEFAULT_READ_TIMEOUT_MS = 1500; // 1.5 seconds

	/**
	 * Constructs a thread to run a Thrift server that is CPU bound. The number
	 * of threads available to support clients is equal to the number of cores
	 * plus one. This reduces context switching and improves overall throughput.
	 * 
	 * @param handler
	 *            is the server implementation of the Thrift-generated API
	 *            interface.
	 * @param port
	 *            is the TCP port this server listens for client requests on.
	 * @throws TTransportException
	 *             if there was a problem creating the socket server.
	 */
	public ThriftBlockingServerThread(TProcessor handler, int port)
			throws TTransportException {
		this(handler, port, Runtime.getRuntime().availableProcessors() + 1,
				Integer.MAX_VALUE, DEFAULT_READ_TIMEOUT_MS);
	}

	/**
	 * Constructs a thread to run a Thrift server.
	 * 
	 * @param handler
	 *            is the server implementation of the Thrift-generated API
	 *            interface.
	 * @param port
	 *            is the TCP port this server listens for client requests on.
	 * @param minThreads
	 *            is the minimum number of JVM threads available to handle
	 *            client requests. Usually equal to
	 *            <code>(Runtime.getRuntime().availableProcessors() + 1)</code>.
	 * @param maxThreads
	 *            is the maximum number of threads available to handle client
	 *            requests. If this number is too high context switching costs
	 *            will start to cause performance to drop (we saw this at ~160
	 *            threads on 8 core machines with the Targeting Service).
	 * @param socketTimeoutMs
	 *            is the number of milliseconds to wait while reading client
	 *            data from the socket. This happens a very small amount on our
	 *            network even though it shouldn't.
	 * @throws TTransportException
	 *             if there was a problem creating the socket server.
	 */
	public ThriftBlockingServerThread(TProcessor handler, int port,
			int minThreads, int maxThreads, int socketTimeoutMs)
			throws TTransportException {
		super(ThriftBlockingServerThread.class.getName());

		TServerTransport serverTransport = new TServerSocket(port,
				socketTimeoutMs);

		// Set the TCP socket server options.
		Args args = new TThreadPoolServer.Args(serverTransport)
				.transportFactory(new TTransportFactory())
				.protocolFactory(new TBinaryProtocol.Factory())
				.minWorkerThreads(minThreads).maxWorkerThreads(maxThreads)
				.processorFactory(new TProcessorFactory(handler))
				.processor(handler);

		// Timeout unused JVM threads to free up resources.
		args.stopTimeoutUnit = TimeUnit.SECONDS;
		args.stopTimeoutVal = 30;

		// Create a Thrift server object that handles client connections.
		server = new TThreadPoolServer(args);
	}

}
