package com.nokia.ads.common.net.http;

import java.util.concurrent.atomic.*;

/**
 * This is a helper class for backing off a server that is unreachable.  If this client must
 * always contact a server, but the server goes down, those requests will pile up possibly
 * killing the server and making all requests of this client block.
 * <p>
 * This helper class maintains state for "backing off" from the server in these conditions.
 * When this client notices a server is timing out a certain frequency in a row it can stop
 * contacting that server until some time period has elapsed.
 */
public class BackOff
{
	/**
	 * How many milliseconds to back off from the server in error scenarios.
	 */
	private final int backOffPeriod;
	
	/**
	 * The number of times in a row we have to fail before backing off from the server.
	 */
	private final int consecutiveFailuresAllowed;
	
	/**
	 * A count of how many consecutive failures have happened in a row.  Once this number
	 * reaches some threshold we'll back off.
	 */
	private AtomicLong consecutiveFailures = new AtomicLong();

	/**
	 * If we are backing off this is the system time we will wait until before trying the
	 * server again.  If we are not backing off this will be 0.
	 */
	private long waitUntil;

	/**
	 * Constructs an object for tracking when to back off from a server.
	 * 
	 * @param consecutiveFailuresAllowed is the number of times in a row the server must
	 *  not respond for it to be considered down.
	 * @param backOffPeriod is the number of milliseconds to wait before trying the server
	 *  again when it is down.
	 */
	public BackOff( int consecutiveFailuresAllowed, int backOffPeriod )
	{
		this.consecutiveFailuresAllowed = consecutiveFailuresAllowed;
		this.backOffPeriod = backOffPeriod;
	}

	/**
	 * A check to see if the call to a service is allowed or not.
	 * 
	 * @return <code>true</code> if the service can be called; <code>false</code>
	 *  if we are backing off right now and it should not be.
	 */
	public boolean greenLight()
	{
		// Is everything OK?
		if ( waitUntil == 0 )
		{
			// Everything is fine.  This is the normal case.
			return true;
		}
		else
		{
			// We are backing off.
			long wait = waitUntil;  // Hold a local copy of the variable so its value won't change
			
			if ( System.currentTimeMillis() <= wait )
			{
				// No one should talk to the server yet.
				return false;
			}
			else
			{
				// Only let one client through to test if the server is up.
				synchronized ( this )
				{
					if ( wait == waitUntil )
					{
						// I'm the lucky one who tests the server.
						waitUntil = System.currentTimeMillis() + backOffPeriod;
						return true;
					}
					else
					{
						// Somebody beat me to it.  I'm still backing off.
						return false;
					}
				}
			}
		}
	}
	
	/**
	 * Call after every successful request to the server.
	 */
	public void succeeded()
	{
		if ( waitUntil != 0 )  // This check avoid the atomic locking of consecutiveFailures
		{
			consecutiveFailures.set( 0 );
			waitUntil = 0;
		}
	}

	/**
	 * Call after every server failure.
	 */
	public void failed()
	{
		long failures = consecutiveFailures.incrementAndGet();
		
		if ( failures == consecutiveFailuresAllowed )  // Only one person should set the failure time
		{
			// Back off the server.
			waitUntil = System.currentTimeMillis() + backOffPeriod;
		}
	}
}
