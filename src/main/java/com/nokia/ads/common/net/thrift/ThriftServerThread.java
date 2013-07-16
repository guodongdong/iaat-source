package com.nokia.ads.common.net.thrift;

import org.apache.log4j.Logger;
import org.apache.thrift.server.TServer;

/**
 * Base class for servers using <a href="http://incubator.apache.org/thrift/">Thrift</a>.
 * By default Thrift's server runs in the master thread.  This is fine when there is
 * just one service in a process.  But when writing tests or running multiple services
 * it is best to give the socket server its own thread.
 */
public abstract class ThriftServerThread extends Thread {

  /**
   * Log4j logger.
   */
  private static final Logger logger = Logger.getLogger(ThriftServerThread.class);

  /**
   * The Thrift socket server.
   */
  protected TServer server;

  /**
   * A temporary store for any socket server startup exceptions.
   */
  private Throwable startupException;

  /**
   * Condition variable to block start() until run() has completed.
   */
  protected boolean runHasBeenCalled;

  /**
   * Constructor for subclasses.
   * 
   * @param name is the JVM thread name for the Thrift server thread that handles
   *        client connections.
   */
  protected ThriftServerThread( String name )
  {
	super( name );

    // The Java process should not exit so long as this thread lives.
    setDaemon(false);

    // Set condition variable; run hasn't been called yet.
    runHasBeenCalled = false;
  }

  /**
   * Server thread's logic.
   * 
   * @see java.lang.Thread#run()
   */
  public void run() {
    // Unblock the master thread's call to start().

    // run()'s been called now. start() may proceed.
    // This operation is outside the synchronized block because
    // this path is the only one that writes and is only called once.
    runHasBeenCalled = true;

    synchronized(this) {
      notifyAll();
    }

    // Start the socket server.
    try {
      server.serve();
    } catch(Throwable t) {
      logger.error("TThreadPoolServer.serve error", t);
      startupException = t;
    }
  }

  /**
   * Starts the Thrift server on a background thread. The process will not exit
   * until <code>kill</code> has been called for this thread.
   * 
   * @see java.lang.Thread#start()
   */
  public void start() {
    if(isAlive() == false) {
      // Start the server thread.
      super.start();

      while (runHasBeenCalled == false) {
        // Block until the socket server thread has started.
        synchronized(this) {
          // Block here until run()'s notifyAll() call.
          // If an InterruptedException is thrown on the wait, we don't want this start() to happen.
          // Startup scripts should catch that the application isn't healthy and retry the start (TS will in particular).
          try {
            wait();
          }
          catch(InterruptedException e) {
            // Ignore the exception, but continue to respect the condition variable.
          }
        }
      }

      try {
        // A bit lame, but we want to avoid the race condition where the
        // server hasn't actually started yet but notifyAll() has been called.
        Thread.sleep(50);

        // Check for any startup exceptions.
        if(startupException != null) {
          logger.error("TThreadPoolServer startup error", startupException);
          throw new RuntimeException("Problem starting Thrift server:  "
                                     + startupException.toString());
        }
      } catch(InterruptedException e) {
        // Ignore and continue.
      }
    }
  }

  /**
   * Stops the Thrift server. This method blocks until the server has completely
   * stopped.
   */
  public void kill() {
    if(isAlive()) {
      // Stop the socket server.
      server.stop();

      // Wait for the server thread to die.
      try {
        join();
      } catch(InterruptedException e) {
        // Ignore and continue.
      }
    }
  }
}
