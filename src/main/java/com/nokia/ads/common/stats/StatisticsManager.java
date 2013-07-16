package com.nokia.ads.common.stats;

import java.util.*;
import java.util.concurrent.*;
import org.apache.log4j.*;

/**
 * The statistics manager is a singleton that stores every <code>Statistic</code> object.  It has
 * methods for managing all statistics, getting the individual objects, and passing them to Cacti.
 * <p>
 * Cacti is our graphing software setup by Ops.  A web page displays the current snapshot of statistics
 * and Cacti consumes that every 5 minutes.  It records those values and shows historical graphs.
 * After getting your statistics all setup work with Ops (Mark Lin imparticularly) to setup Cacti
 * to record your work.  See <code>CactiStatsPage</code> for details on exposing statistics to Cacti.
 * 
 * @see CactiStatsPage
 */
public final class StatisticsManager
{
  /**
   * The log instance.
   */
  private static final Logger LOG = Logger.getLogger( StatisticsManager.class );
  
  /**
   * The handle to the one and only <code>StatisticsManager</code> object.
   */
  public static final StatisticsManager instance;
  
  /**
   * All of the <code>Statistic</code> objects.
   */
  private static final ConcurrentHashMap<String,Statistic> statistics;

  /**
   * Creates the singleton <code>StatisticsManager</code> object.
   */
  static
  {
    // Create the variables for this object.
    statistics = new ConcurrentHashMap<String,Statistic>();
    instance = new StatisticsManager();
    
    // Add default statistics maintained by all services.
    new JvmHeapSizeStatistic();
    new JvmThreadsStatistic();
    CurrentRevisionStatistic.create();
  }

  /**
   * Resets and Deletes all registered statistics instances.  Should only be used for testing.
   * Unfortunately, test units not belonging to this package need access to this API.
   * Use with caution!
   *
   * Used to mitigate singleton check in StatisticsManager.addStatistics(),
   * which throws an IllegalArgumentException when attempting to test
   * classes that are singleton in production.
   */
  public static void forciblyUnregisterAllStatistics() {
    // Remove all Statistics.
    synchronized (statistics) {
      Set<String> statisticNames = statistics.keySet();

      for (String name : statisticNames) {
        statistics.remove(name);
      }
    }

    // By the end of this function, nothing should be in the map.
  }
  
  /**
   * Hides the constructor so only the singleton can be used.
   */
  private StatisticsManager()
  {
  }
  
  /**
   * Starts recording statistics with a <a href="http://graphite.wikidot.com/">Graphite</a> instance.
   * <p>
   * Graphite is a replacement for Cacti.  Its benefits are:
   * <ul>
   *  <li>Add a stat to your code, it shows up (no need for Moat to configure)
   *  <li>Longer names than 15 characters so it is easier to read
   *  <li>Stats are stored in a heirarchical tree to make them easier to find
   *  <li>Stats can be updated more frequently than every 5 minutes
   *  <li>Charts are easier to manipulate
   * </ul>
   * 
   * @param systemName is the name of this service such as "targeting" or "ad.worker".
   * @param hostname is the location of the Graphite server like "graphite.server".
   * @param port is the port number the server listens on (default is 2003).
   * @param intervalInSeconds is how frequently to send statistics to Graphite.  For example
   *  "5 * 60" is every 5 minutes.
   */
  public static void recordWithGraphite( String systemName, String hostname, short port, int intervalInSeconds )
  {
    if ( hostname != null )
    {
      GraphiteInterface graphite = new GraphiteInterface( hostname, port, systemName );
      graphite.setInterval( intervalInSeconds );
      graphite.start();
      LOG.debug( "Recording statistics as '" + systemName + "' on Graphite instance " + hostname + ":" + port + " every " + intervalInSeconds + " seconds." );
    }
  }
  
  /**
   * Starts recording statistics with a <a href="http://graphite.wikidot.com/">Graphite</a> instance.
   * <p>
   * Graphite is a replacement for Cacti.  Its benefits are:
   * <ul>
   *  <li>Add a stat to your code, it shows up (no need for Moat to configure)
   *  <li>Longer names than 15 characters so it is easier to read
   *  <li>Stats are stored in a heirarchical tree to make them easier to find
   *  <li>Stats can be updated more frequently than every 5 minutes
   *  <li>Charts are easier to manipulate
   * </ul>
   * 
   * @param systemName is the name of this service such as "targeting" or "ad.worker".
   * @param hostname is the location of the Graphite server like "graphite.server".
   */
  public static void recordWithGraphite( String systemName, String hostname )
  {
    recordWithGraphite( systemName, hostname, (short) 2003, 60 );
  }
  
  /**
   * Adds a statistic to this manager.  This is called only by the base class <code>Statistic</code>.
   */
  /* package */ static void addStatistic( Statistic statistic )
  {
    String name = statistic.name();
    
    // Make sure name is not already used.
    if ( getStatistic( name ) != null )
    {
      throw new IllegalArgumentException( "Cannot create a duplicate Statistic with name " + statistic.name() );
    }
      
    statistics.put( name, statistic );
  }
  
  /**
   * Gets a <code>Statistic</code> object given its name.  Typically this should not be necessary
   * since it is faster to hold a reference to the <code>Statistic</code> object from the
   * class that updates it.  For example:
   * <pre><code>
   *   public class MyApi
   *   {
   *     private static final IncrementingStatistic USAGE_STATISTIC =
   *       new IncrementingStatistic( "API_CALLS", "Number of calls to the MyApi.api() method." );
   * 
   *     ...
   * 
   *     public void api()
   *     {
   *       USAGE_STATISTIC.increment();
   *       ...
   *     }
   *   }
   * </code></pre>
   * 
   * @param name is the name of the statistic.
   * @return The statistic object with the given <code>name</code> or <code>null</code>
   *  if it does not exist.
   */
  public static Statistic getStatistic( String name )
  {
    return statistics.get( name );
  }
  
  /**
   * Gets all of the <code>Statistic</code> objects in this service.  If you know the name of
   * the statistic you need you should use <code>getStatistic</code> which is much faster.
   * 
   * @return All of the <code>Statistic</code> objects in this service.
   */
  public static List<Statistic> statistics()
  {
    // Copy the statistics to a List.
    LinkedList<Statistic> list = new LinkedList<Statistic>();
    Enumeration<Statistic> stats = statistics.elements();
    
    while ( stats.hasMoreElements() )
    {
      list.add( stats.nextElement() );
    }
    
    // Alphabetically sort the list.
    Collections.sort( list );
    
    return list;
  }
  
  /**
   * ONLY USE FOR TESTING!
   * <p>
   * Removes all statistics.  There is no way to add past statistics back in once this is done.
   * This should only be used in testing the statistics framework.
   */
  public static void clearForTesting()
  {
    statistics.clear();
  }
  
  /**
   * @return A snapshot of all statistics in a format consumable by Cacti.
   */
  public static String toCactiString()
  {
    synchronized ( statistics )
    {
      StringBuilder sb = new StringBuilder();
      
      for ( Statistic obj : statistics() )
      {
        String s = obj.toCactiString();
        
        if ( s != null )
        {
          sb.append( s ).append( " " );
        }
      }
      
      String result = sb.toString().trim();
      return result;
    }
  }
  
  /**
   * @return A snapshot of all statistics in a format consumable by Graphite.
   */
  public static String toGraphiteString()
  {
    synchronized ( statistics )
    {
      StringBuilder sb = new StringBuilder();
      
      for ( Statistic obj : statistics() )
      {
        String s = obj.toGraphiteString();
        
        if ( s != null )
        {
          sb.append( s ).append( " " );
        }
      }
      
      String result = sb.toString().trim();
      return result;
    }
  }
  
  /**
   * Returns a string representation of the statistics.
   *
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return toCactiString();
  }
}
