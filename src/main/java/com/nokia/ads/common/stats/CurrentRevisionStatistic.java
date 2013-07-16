package com.nokia.ads.common.stats;

import java.io.*;

/**
 * A statistic that is set to the Subversion revision number of the current deployment.  This
 * remains constant for the lifetime of the process.  It is automatically created for you by
 * the Statistics framework.
 * <p>
 * The current revision can be superimposed with other statistics to see what versions they
 * correspond to.  Suppose response times jumped at some point.  You could see when that happened
 * and if it tied back to a certain deployment.  And if so what version to rollback to.
 */
class CurrentRevisionStatistic 
  extends SnapshotStatistic
{
  /**
   * Creates a statistic for the current Subversion revision number of the deployment (if available).
   */
  public static void create()
  {
    // Only create a statistic object if the revision number is available.
    String revision = getRevision();
    
    if ( revision != null )
    {
      new CurrentRevisionStatistic( revision, RRDType.CACTI_AND_GRAPHITE );
    }
  }
  
  /**
   * Creates a revision statistic object.
   */
  private CurrentRevisionStatistic( String revision, RRDType rrdType )
  {
    super( "REVISION", "common.revision", "The current Subversion revision number of this deployment.", rrdType );
    
    // Set the current revision.
    long r = Long.valueOf( revision );
    set( r );
  }
  
  /**
   * Gets the current deployments Subversion revision number.
   * 
   * @return The revision, such as 71235, or <code>null</code> if unavailable.
   */
  private static String getRevision()
  {
    String revision = null;
    BufferedReader bufferedReader = null;
    
    try
    {
      // REVISION is a file created by Capistrano during deployment that is put
      // into the current directory.  If present its contents are the deployed
      // revision number.
      FileReader fileReader = new FileReader( "REVISION" );
      bufferedReader = new BufferedReader( fileReader );
      revision = bufferedReader.readLine();
    }
    catch (IOException e)
    {
      // Ignore.
    }
    finally
    {
      try
      {
        if ( bufferedReader != null )
        {
          bufferedReader.close();
        }
      }
      catch (IOException e)
      {
        // Ignore.
      }
    }

    return revision;
  }
}
