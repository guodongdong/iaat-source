package com.nokia.ads.common.stats;

import com.nokia.ads.common.stats.Statistic.RRDType;

/**
 * A collection of statistics normally maintained for requests including total requests,
 * throughput per seconds, min/max/avg and 50th/95th/99th percentile response times.
 * <p>
 * These statistics all appear on the Cacti output HTTP page.
 * <p>
 * Here is an example API using this class:
 * <pre><code>
 *   public class Foo
 *   {
 *     private static final RequestStatistics BAR_STATS =
 *       new RequestStatistics( "BAR", "graphite.stats.name", "Request statistics for Foo.bar()" );
 * 
 *     public String bar()
 *     {
 *       long start = BAR_STATS.requestStart();
 *       String result = null;
 * 
 *       try
 *       {
 *         // Do stuff here.
 *       }
 *       finally
 *       {
 *         long nanoseconds = BAR_STATS.requestEnd( start );
 *       }
 * 
 *       return result;
 *     }
 *   }
 * </code></pre>
 */
public class RequestStatistics
{
  boolean graphiteOnly = false;
  
  /**
   * The total number of requests. Resets each day at midnight.
   */
  protected AutoResetIncrementingStatistic total;
  
  /**
   * How many requests per second are currently being handled.
   */
  protected ThroughputStatistic perSecond;
  
  /**
   * The minimum response time.
   */
  protected MinimumStatistic min;
  
  /**
   * The maximum response time.
   */
  protected MaximumStatistic max;
  
  /**
   * The average response time.
   */
  protected AverageStatistic avg;
  
  /**
   * The mean response time (50th percentile).
   */
  protected PercentileStatistic percentile50;
  
  /**
   * The 95th percentile response time.
   */
  protected PercentileStatistic percentile95;
  
  /**
   * The 99th percentile response time.
   */
  protected PercentileStatistic percentile99;

  @Deprecated
  public RequestStatistics( String name, String graphiteName, String description) {
    this(name, graphiteName, description, RRDType.CACTI_AND_GRAPHITE);
  }

  @Deprecated
  public RequestStatistics( String name, String graphiteName, String description,
      ConversionType cactiConversionType,
      ConversionType graphiteConversionType,
       boolean returnZeroForNoUpdate)
  {
    this(name, graphiteName, description, cactiConversionType, graphiteConversionType,
        returnZeroForNoUpdate, RRDType.CACTI_AND_GRAPHITE);
  }
  
  /**
   * Creates a new statistics object for tracking request performance.
   * 
   * @param name is the label for this statistic which is used by Cacti for graphing.
   *  It must therefore conform to Cacti label naming restrictions meaning it is
   *  1 to 11 capital letters, numbers, or underscores (e.g. LATENCY).
   * @param description is a string that other people will understand when debugging this service.
   * @param rrd graph type to use
   */
  public RequestStatistics( String name, String description, RRDType rrdType )
  {
    if (rrdType == RRDType.CACTI_AND_GRAPHITE) {
      throw new IllegalArgumentException( "Constructor not valid for " + rrdType );
    }
    
    if (rrdType == RRDType.CACTI) {
      if ( (Statistic.isValidCactiName(name) == false) || (name.length() > 11) )
      {
        // name must be less than 15 characters and we use 4 leaving 11 for the user.
        throw new IllegalArgumentException( "Name (" + name + ") must be 11 capital letters, digits, underscores or less." );
      }
    }
    
    switch(rrdType) {
      case CACTI:
        total = new AutoResetIncrementingStatistic( name, description, AutoResetIncrementingStatistic.Frequency.DAY, rrdType);
        perSecond = new ThroughputStatistic( name + "_SEC", "(Requests per second) " + description, 1, rrdType );
        min = new MinimumStatistic( name + "_MIN", "(Minimum response) " + description, rrdType );
        max = new MaximumStatistic( name + "_MAX", "(Maximum response) " + description, rrdType );
        avg = new AverageStatistic( name + "_AVG", "(Average response) " + description, rrdType );
        percentile50 = new PercentileStatistic( name + "_50", "(50th percentile) " + description, 0.50, rrdType );
        percentile95 = new PercentileStatistic( name + "_95", "(95th percentile) " + description, 0.95, rrdType );
        percentile99 = new PercentileStatistic( name + "_99", "(99th percentile) " + description, 0.99, rrdType );
        break;
      case GRAPHITE:
        total = new AutoResetIncrementingStatistic( name + ".total_requests", description, AutoResetIncrementingStatistic.Frequency.DAY, rrdType );
        perSecond = new ThroughputStatistic( name + ".requests_per_second", "(Requests per second) " + description, 1, rrdType );
        min = new MinimumStatistic( name + ".min", "(Minimum response) " + description, rrdType );
        max = new MaximumStatistic( name + ".max", "(Maximum response) " + description, rrdType );
        avg = new AverageStatistic( name + ".avg", "(Average response) " + description, rrdType );
        percentile50 = new PercentileStatistic( name + ".50th", "(50th percentile) " + description, 0.50, rrdType );
        percentile95 = new PercentileStatistic( name + ".95th", "(95th percentile) " + description, 0.95, rrdType );
        percentile99 = new PercentileStatistic( name + ".99th", "(99th percentile) " + description, 0.99, rrdType );
        break;
    }
  }

  public RequestStatistics( String name, String description, ConversionType conversionType, RRDType rrdType )
  {
    if (rrdType == RRDType.CACTI_AND_GRAPHITE) {
      throw new IllegalArgumentException( "Constructor not valid for " + rrdType );
    }

    if (rrdType == RRDType.CACTI) {
      if ( (Statistic.isValidCactiName(name) == false) || (name.length() > 11) )
      {
        // name must be less than 15 characters and we use 4 leaving 11 for the user.
        throw new IllegalArgumentException( "Name (" + name + ") must be 11 capital letters, digits, underscores or less." );
      }
    }

    switch(rrdType) {
      case CACTI:
        total = new AutoResetIncrementingStatistic( name, description, AutoResetIncrementingStatistic.Frequency.DAY, conversionType, rrdType);
        perSecond = new ThroughputStatistic( name + "_SEC", "(Requests per second) " + description, 1, conversionType, rrdType );
        min = new MinimumStatistic( name + "_MIN", "(Minimum response) " + description, conversionType, rrdType );
        max = new MaximumStatistic( name + "_MAX", "(Maximum response) " + description, conversionType, rrdType );
        avg = new AverageStatistic( name + "_AVG", "(Average response) " + description, conversionType, rrdType );
        percentile50 = new PercentileStatistic( name + "_50", "(50th percentile) " + description, 0.50, conversionType, rrdType );
        percentile95 = new PercentileStatistic( name + "_95", "(95th percentile) " + description, 0.95, conversionType, rrdType );
        percentile99 = new PercentileStatistic( name + "_99", "(99th percentile) " + description, 0.99, conversionType, rrdType );
        break;
      case GRAPHITE:
        total = new AutoResetIncrementingStatistic( name + ".total_requests", description, AutoResetIncrementingStatistic.Frequency.DAY, conversionType, rrdType );
        perSecond = new ThroughputStatistic( name + ".requests_per_second", "(Requests per second) " + description, 1, conversionType, rrdType );
        min = new MinimumStatistic( name + ".min", "(Minimum response) " + description, conversionType, rrdType );
        max = new MaximumStatistic( name + ".max", "(Maximum response) " + description, conversionType, rrdType );
        avg = new AverageStatistic( name + ".avg", "(Average response) " + description, conversionType, rrdType );
        percentile50 = new PercentileStatistic( name + ".50th", "(50th percentile) " + description, 0.50, conversionType, rrdType );
        percentile95 = new PercentileStatistic( name + ".95th", "(95th percentile) " + description, 0.95, conversionType, rrdType );
        percentile99 = new PercentileStatistic( name + ".99th", "(99th percentile) " + description, 0.99, conversionType, rrdType );
        break;
    }
  }

  
  public RequestStatistics( String name, String description ) {
    this(name, description, RRDType.CACTI);
  }
  
  /**
   * Creates a new statistics object for tracking request performance.
   * 
   * @param name is the label for this statistic which is used by Cacti for graphing.
   *  It must therefore conform to Cacti label naming restrictions meaning it is
   *  1 to 11 capital letters, numbers, or underscores (e.g. LATENCY).
   * @param graphiteName is the what the statistic will appear as in the tree in Graphite's UI.
   *  It should be lowercase letters, digits, underscores, and periods.  The period is used to
   *  nest this statisitc under a folder such as "group.stat_one".
   * @param description is a string that other people will understand when debugging this service.
   */
  public RequestStatistics( String name, String graphiteName, String description, RRDType rrdType )
  {
    if (rrdType == RRDType.CACTI || rrdType == RRDType.CACTI_AND_GRAPHITE) {
      if ( (Statistic.isValidCactiName(name) == false) || (name.length() > 11) )
      {
        // name must be less than 15 characters and we use 4 leaving 11 for the user.
        throw new IllegalArgumentException( "Name (" + name + ") must be 11 capital letters, digits, underscores or less." );
      }
    }
    
    total = new AutoResetIncrementingStatistic( name, graphiteName + ".total_requests", description, AutoResetIncrementingStatistic.Frequency.DAY, rrdType );
    perSecond = new ThroughputStatistic( name + "_SEC", graphiteName + ".requests_per_second", "(Requests per second) " + description, 1, rrdType );
    min = new MinimumStatistic( name + "_MIN", graphiteName + ".min", "(Minimum response) " + description, rrdType );
    max = new MaximumStatistic( name + "_MAX", graphiteName + ".max", "(Maximum response) " + description, rrdType );
    avg = new AverageStatistic( name + "_AVG", graphiteName + ".avg", "(Average response) " + description, rrdType );
    percentile50 = new PercentileStatistic( name + "_50", graphiteName + ".50th", "(50th percentile) " + description, 0.50, rrdType );
    percentile95 = new PercentileStatistic( name + "_95", graphiteName + ".95th", "(95th percentile) " + description, 0.95, rrdType );
    percentile99 = new PercentileStatistic( name + "_99", graphiteName + ".99th", "(99th percentile) " + description, 0.99, rrdType );
  }

  
  /**
   * Creates a new statistics object for tracking request performance.
   * 
   * @param name is the label for this statistic which is used by Cacti for graphing.
   *  It must therefore conform to Cacti label naming restrictions meaning it is
   *  1 to 11 capital letters, numbers, or underscores (e.g. LATENCY).
   * @param graphiteName is the what the statistic will appear as in the tree in Graphite's UI.
   *  It should be lowercase letters, digits, underscores, and periods.  The period is used to
   *  nest this statisitc under a folder such as "group.stat_one".
   * @param description is a string that other people will understand when debugging this service.
   */
  public RequestStatistics( String name, String graphiteName, String description,
      ConversionType cactiConversionType,
      ConversionType graphiteConversionType,
       boolean returnZeroForNoUpdate,
       RRDType rrdType)
  {
    if (rrdType == RRDType.CACTI || rrdType == RRDType.CACTI_AND_GRAPHITE) {
      if ( (Statistic.isValidCactiName(name) == false) || (name.length() > 11) )
      {
        // name must be less than 15 characters and we use 4 leaving 11 for the user.
        throw new IllegalArgumentException( "Name (" + name + ") must be 11 capital letters, digits, underscores or less." );
      }
    }
    
    total = new AutoResetIncrementingStatistic( name, graphiteName + ".total_requests", description, 
        AutoResetIncrementingStatistic.Frequency.DAY, rrdType );
    perSecond = new ThroughputStatistic( name + "_SEC", graphiteName + ".requests_per_second", 
        "(Requests per second) " + description, 1, rrdType );
    min = new MinimumStatistic( name + "_MIN", graphiteName + ".min", "(Minimum response) " + description,
        cactiConversionType, graphiteConversionType,  returnZeroForNoUpdate, rrdType);
    max = new MaximumStatistic( name + "_MAX", graphiteName + ".max", "(Maximum response) " + description,
        cactiConversionType,  graphiteConversionType,returnZeroForNoUpdate, rrdType);
    avg = new AverageStatistic( name + "_AVG", graphiteName + ".avg", "(Average response) " + description,
        cactiConversionType,graphiteConversionType,  returnZeroForNoUpdate, rrdType);
    percentile50 = new PercentileStatistic( name + "_50", graphiteName + ".50th", "(50th percentile) " + description, 0.50, 
        cactiConversionType,graphiteConversionType,  returnZeroForNoUpdate, rrdType); 
    percentile95 = new PercentileStatistic( name + "_95", graphiteName + ".95th", "(95th percentile) " + description, 0.95 , 
            cactiConversionType,  graphiteConversionType,  returnZeroForNoUpdate, rrdType);
    percentile99 = new PercentileStatistic( name + "_99", graphiteName + ".99th", "(99th percentile) " + description, 0.99 ,
        cactiConversionType,graphiteConversionType,  returnZeroForNoUpdate, rrdType);
  }
  /**
   * Call at the beginning of the request and save the result to pass into
   * <code>requestEnd</code>.
   * 
   * @return A value to pass into <code>requestEnd</code>.
   */
  public long requestStart()
  {
    return System.nanoTime();
  }
  
  /**
   * Call at the end of a request to record its performance.
   * 
   * @param start is the value returned from <code>requestStart</code>.
   * @return The number of nanoseconds it took to process the request.
   */
  public long requestEnd( long start )
  {
    // How long did the request take?
    long nanoseconds = System.nanoTime() - start;
    
    // Record the request.
    total.increment();
    perSecond.update();
    min.update( nanoseconds );
    max.update( nanoseconds );
    avg.update( nanoseconds );
    percentile50.update( nanoseconds );
    percentile95.update( nanoseconds );
    percentile99.update( nanoseconds );
    
    // Return the response time of the request.
    return nanoseconds;
  }
  
  /**
   * Resets all of the statistics to start freshly tracking everything.  Possibly helpful
   * at the day rollover if this object is tracking only today's performance.
   */
  public void reset()
  {
    total.reset();
    perSecond.reset();
    min.reset();
    max.reset();
    avg.reset();
    percentile50.reset();
    percentile95.reset();
    percentile99.reset();
  }
  
  /**
   * @return The total number of requests.
   */
  public long getTotalRequests()
  {
    return total.get();
  }
  
  /**
   * @return How many requests per second are being processed now.
   */
  public float getRequestsPerSecond()
  {
    return perSecond.get();
  }
  
  /**
   * @return The fastest response time, in seconds, in the last 5 minutes.
   */
  public double getMinimumResponseSec()
  {
    return min.getSeconds();
  }
  
  /**
   * @return The slowest response time, in seconds, in the last 5 minutes.
   */
  public double getMaxmimumResponseSec()
  {
    return max.getSeconds();
  }
  
  /**
   * @return The average response time, in seconds, in the last 5 minutes.
   */
  public double getAverageResponseSec()
  {
    return avg.getSeconds();
  }
  
  /**
   * @return The median (50th percentile) response time, in seconds, in the last 5 minutes.
   */
  public double get50thPercentileResponseSec()
  {
    return percentile50.getSeconds();
  }
  
  /**
   * @return The 95th percentile response time, in seconds, in the last 5 minutes.
   */
  public double get95thPercentileResponseSec()
  {
    return percentile95.getSeconds();
  }
  
  /**
   * @return The 99th percentile response time, in seconds, in the last 5 minutes.
   */
  public double get99thPercentileResponseSec()
  {
    return percentile99.getSeconds();
  }
  
  /**
   * Returns a string representation of this statistic suitable for debugging.
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    sb.append( total.cactiName() ).append( "(" ).append( total.description() ).append( ")\n" );
    sb.append( "  Total Requests   = " ).append( total.get() ).append( "\n" );
    sb.append( "  Requests/Second  = " ).append( perSecond.get() ).append( "\n" );
    sb.append( "  Minimum Response = " ).append( min.get() ).append( "\n" );
    sb.append( "  Average Response = " ).append( avg.get() ).append( "\n" );
    sb.append( "  Maximum Response = " ).append( max.get() ).append( "\n" );
    sb.append( "  50th Percentile  = " ).append( percentile50.get() ).append( "\n" );
    sb.append( "  95th Percentile  = " ).append( percentile95.get() ).append( "\n" );
    sb.append( "  99th Percentile  = " ).append( percentile99.get() ).append( "\n" );
    return sb.toString();
  }
  
  /**
   * Sets the conversion type for timing-related statistics
   *
   * @param conversionType
   */
  public void setConversionType(ConversionType conversionType) {
    min.setCactiConversionType(conversionType);
    max.setCactiConversionType(conversionType);
    avg.setCactiConversionType(conversionType);
    percentile50.setCactiConversionType(conversionType);
    percentile95.setCactiConversionType(conversionType);
    percentile99.setCactiConversionType(conversionType);
  }
  
  /**
   * Sets the conversion type for timing-related statistics
   *
   * @param conversionType
   */
  public void setGraphiteConversionType(ConversionType conversionType) {
    min.setGraphiteConversionType(conversionType);
    max.setGraphiteConversionType(conversionType);
    avg.setGraphiteConversionType(conversionType);
    percentile50.setGraphiteConversionType(conversionType);
    percentile95.setGraphiteConversionType(conversionType);
    percentile99.setGraphiteConversionType(conversionType);
  }
  
  @Deprecated
  public boolean isGraphiteOnly() {
    return graphiteOnly;
  }

  @Deprecated
  public void setGraphiteOnly(boolean graphiteOnly) {
    total.setGraphiteOnly(graphiteOnly);
    perSecond.setGraphiteOnly(graphiteOnly);
    min.setGraphiteOnly(graphiteOnly);
    max.setGraphiteOnly(graphiteOnly);
    avg.setGraphiteOnly(graphiteOnly);
    percentile50.setGraphiteOnly(graphiteOnly);
    percentile95.setGraphiteOnly(graphiteOnly);
    percentile99.setGraphiteOnly(graphiteOnly);
    this.graphiteOnly = graphiteOnly;
  }
}
