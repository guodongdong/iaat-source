package com.nokia.ads.common.job;

import java.util.*;
import org.apache.log4j.*;

/**
 * Runs a job on a background thread.
 */
public class JobRunner
	extends TimerTask
{
	/**
	 * The log instance.
	 */
	private static Logger log = Logger.getLogger( JobRunner.class );
	
	/**
	 * The job to run.
	 */
	private final Job job;
	
	/**
	 * Creates an object that runs a job.
	 * 
	 * @param job is the job to run.
	 */
	public JobRunner( Job job )
	{
		this.job = job;
	}

	/**
	 * Runs the job on a background thread.
	 *
	 * @see java.util.TimerTask#run()
	 */
	@Override
	public void run()
	{
		String name = "";
		
		try
		{
			// Give everyone else, who is handling requests, priority of this job.
			Thread current = Thread.currentThread();
			current.setPriority( Thread.MIN_PRIORITY );
	
			// Name this thread for debugging.
			current.setName( "Job Runner" );

			// Name the job.
			name = "Job (" + job.getName() + ")";
			
			long time = System.currentTimeMillis();
			log.debug( "" + name + " starting." );
		
			// Actuall run the job.  This will likely take a long time.
			job.doWork();
			
			time = System.currentTimeMillis() - time;
			log.debug( name + " finished in " + time + "ms." );
		}
		catch (Throwable t)
		{
			log.error( "Unhandled Exception in " + name, t );
		}
	}
}
