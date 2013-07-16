package com.nokia.ads.common.stats;

import com.nokia.ads.common.stats.Statistic.RRDType;

/**
 * A collection of statistics normally maintained for components:
 * 50th/90th/95th/99th percentile response times.
 * <p>
 * These statistics all appear on the Cacti output HTTP page.
 * <p>
 * Here is an example API using this class:
 * <pre><code>
 *   public class Foo
 *   {
 *     private static final LatencyStatistics BAR_STATS =
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
public class LatencyStatistics
{
  
  boolean graphiteOnly = false;
  
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
  public LatencyStatistics( String name, String description ) {
    this(name, description, RRDType.CACTI);
  }
  
  @Deprecated
  public LatencyStatistics( String name, String graphiteName, String description ) {
    this(name, graphiteName, description, RRDType.CACTI_AND_GRAPHITE);
  }

  @Deprecated
  public LatencyStatistics( String name, String graphiteName, String description,
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
   */
  public LatencyStatistics( String name, String description, RRDType rrdType )
  {
    if (rrdType == RRDType.CACTI_AND_GRAPHITE) {
      throw new IllegalArgumentException( "Constructor not valid for " + rrdType );
    }
    
    if (rrdType == RRDType.CACTI) {
      if ( (Statistic.isValidCactiName(name) == false) || (name.length() > 12) )
      {
        // name must be less than 15 characters and we use 3 leaving 12 for the user.
        throw new IllegalArgumentException( "Name (" + name + ") must be 12 capital letters, digits, underscores or less." );
      }
    }
    
    switch(rrdType) {
      case CACTI:
        percentile50 = new PercentileStatistic( name + "_50", "(50th percentile) " + description, 0.50, ConversionType.NANOSECONDS_TO_SECONDS, rrdType );
        percentile95 = new PercentileStatistic( name + "_95", "(95th percentile) " + description, 0.95, ConversionType.NANOSECONDS_TO_SECONDS, rrdType );
        percentile99 = new PercentileStatistic( name + "_99", "(99th percentile) " + description, 0.99, ConversionType.NANOSECONDS_TO_SECONDS, rrdType );
        break;
      case GRAPHITE:
        percentile50 = new PercentileStatistic( name + ".50th", "(50th percentile) " + description, 0.50, ConversionType.NANOSECONDS_TO_MILLISECONDS, rrdType );
        percentile95 = new PercentileStatistic( name + ".95th", "(95th percentile) " + description, 0.95, ConversionType.NANOSECONDS_TO_MILLISECONDS, rrdType );
        percentile99 = new PercentileStatistic( name + ".99th", "(99th percentile) " + description, 0.99, ConversionType.NANOSECONDS_TO_MILLISECONDS, rrdType );
        break;
    }
    
  }

  public LatencyStatistics( String name, String description, 
      ConversionType conversionType,
      boolean returnZeroForNoUpdate,
      RRDType rrdType )
  {
    if (rrdType == RRDType.CACTI_AND_GRAPHITE) {
      throw new IllegalArgumentException( "Constructor not valid for " + rrdType );
    }

    if (rrdType == RRDType.CACTI) {
      if ( (Statistic.isValidCactiName(name) == false) || (name.length() > 12) )
      {
        // name must be less than 15 characters and we use 3 leaving 12 for the user.
        throw new IllegalArgumentException( "Name (" + name + ") must be 12 capital letters, digits, underscores or less." );
      }
    }
    
    switch(rrdType) {
      case CACTI:
        percentile50 = new PercentileStatistic( name + "_50", "(50th percentile) " + description, 0.50, 
            ConversionType.NANOSECONDS_TO_SECONDS, returnZeroForNoUpdate, rrdType );
        percentile95 = new PercentileStatistic( name + "_95", "(95th percentile) " + description, 0.95, 
            ConversionType.NANOSECONDS_TO_SECONDS, returnZeroForNoUpdate, rrdType );
        percentile99 = new PercentileStatistic( name + "_99", "(99th percentile) " + description, 0.99, 
            ConversionType.NANOSECONDS_TO_SECONDS, returnZeroForNoUpdate, rrdType );
        break;
      case GRAPHITE:
        percentile50 = new PercentileStatistic( name + ".50th", "(50th percentile) " + description, 0.50, 
            ConversionType.NANOSECONDS_TO_MILLISECONDS, returnZeroForNoUpdate, rrdType );
        percentile95 = new PercentileStatistic( name + ".95th", "(95th percentile) " + description, 0.95, 
            ConversionType.NANOSECONDS_TO_MILLISECONDS, returnZeroForNoUpdate, rrdType );
        percentile99 = new PercentileStatistic( name + ".99th", "(99th percentile) " + description, 0.99, 
            ConversionType.NANOSECONDS_TO_MILLISECONDS, returnZeroForNoUpdate, rrdType );
        break;
    }
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
  public LatencyStatistics( String name, String graphiteName, String description, RRDType rrdType )
  {
    if (rrdType == RRDType.CACTI || rrdType == RRDType.CACTI_AND_GRAPHITE) {
      if ( (Statistic.isValidCactiName(name) == false) || (name.length() > 12) )
      {
        // name must be less than 15 characters and we use 3 leaving 12 for the user.
        throw new IllegalArgumentException( "Name (" + name + ") must be 12 capital letters, digits, underscores or less." );
      }
    }
    
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
  public LatencyStatistics( String name, String graphiteName, String description,
      ConversionType cactiConversionType,
      ConversionType graphiteConversionType,
       boolean returnZeroForNoUpdate,
       RRDType rrdType )
  {
    if (rrdType == RRDType.CACTI || rrdType == RRDType.CACTI_AND_GRAPHITE) {
      if ( (Statistic.isValidCactiName(name) == false) || (name.length() > 12) )
      {
        // name must be less than 15 characters and we use 3 leaving 12 for the user.
        throw new IllegalArgumentException( "Name (" + name + ") must be 12 capital letters, digits, underscores or less." );
      }
    }
    
    percentile50 = new PercentileStatistic( name + "_50", graphiteName + ".50th", "(50th percentile) " + description, 0.50, 
        cactiConversionType, graphiteConversionType, returnZeroForNoUpdate, rrdType ); 
    percentile95 = new PercentileStatistic( name + "_95", graphiteName + ".95th", "(95th percentile) " + description, 0.95, 
        cactiConversionType, graphiteConversionType, returnZeroForNoUpdate, rrdType );
    percentile99 = new PercentileStatistic( name + "_99", graphiteName + ".99th", "(99th percentile) " + description, 0.99,
        cactiConversionType, graphiteConversionType, returnZeroForNoUpdate, rrdType );
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
    percentile50.update( nanoseconds );
    percentile95.update( nanoseconds );
    percentile99.update( nanoseconds );
    
    // Return the response time of the request.
    return nanoseconds;
  }

  /**
   * Call with duration to record performance
   * 
   * @param duration is the value calculated by the caller if not using <code>requestStart</code>.
   */
  public void update( long duration ) {
    percentile50.update( duration );
    percentile95.update( duration );
    percentile99.update( duration );
  }
  
  /**
   * Resets all of the statistics to start freshly tracking everything.  Possibly helpful
   * at the day rollover if this object is tracking only today's performance.
   */
  public void reset()
  {
    percentile50.reset();
    percentile95.reset();
    percentile99.reset();
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
    percentile50.setGraphiteOnly(graphiteOnly);
    percentile95.setGraphiteOnly(graphiteOnly);
    percentile99.setGraphiteOnly(graphiteOnly);
    this.graphiteOnly = graphiteOnly;
  }
}
