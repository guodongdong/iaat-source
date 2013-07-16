package com.nokia.ads.common.net.thrift;

import java.util.concurrent.TimeUnit;

import org.apache.thrift.TProcessor;
import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.THsHaServer.Args;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;

/**
 * Runs a non-blocking I/O (NIO) server for a Thrift service. This NIO server is
 * suitable for thousands of simultaneous clients. It creates too much overhead
 * if there are fewer than that and you should use the
 * <code>ThriftBlockingServerThread</code> class for those cases.
 */
public class ThriftHsHaServerThread extends ThriftServerThread {

	/**
	 * Constructs a thread to run a Thrift non-blocking server whose thread pool
	 * size is bounded by minClients and maxClients.
	 * 
	 * @param handler
	 *            is the server implementation of the Thrift-generated API
	 *            interface.
	 * @param port
	 *            is the TCP port this server listens for client requests on.
	 * @param minClients
	 *            is the min number of clients to accept requests which should
	 *            be set to the number of cores on the server plus one.
	 * @param maxClients
	 *            is the number of clients to accept before throwing exceptions.
	 *            This should be higher than the expected load. If there is a
	 *            problem clients may start backing up and this gives a way to
	 *            shut the server down.
	 * @param socketTimeoutMs
	 *            is the number of milliseconds before socket connection times
	 *            out. This is a safety mechanism since the clients should not
	 *            connect timeout.
	 * @throws TTransportException
	 *             if there was a problem creating the socket server.
	 */
	public ThriftHsHaServerThread(TProcessor handler, int port, int workerThreads, int socketTimeoutMs) throws TTransportException {
		super("ThriftHsHaServerThread-BoundedQ");

		TNonblockingServerSocket serverTransport = new TNonblockingServerSocket(
				port, socketTimeoutMs);

		// Set the TCP socket server options.
		Args args = new THsHaServer.Args(serverTransport)
				.transportFactory(new TFramedTransport.Factory())
				.protocolFactory(new TBinaryProtocol.Factory())
				.processorFactory(new TProcessorFactory(handler))
				.workerThreads(workerThreads)
				.processor(handler)
				.stopTimeoutUnit(TimeUnit.SECONDS)
				.stopTimeoutVal(30);
		
		//FIXME: cannot use due to read() exception
		
		// Create a Thrift server object that handles client connections.
		server = new THsHaServer(args);
	}

}
