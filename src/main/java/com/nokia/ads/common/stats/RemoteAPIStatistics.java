package com.nokia.ads.common.stats;

import com.nokia.ads.common.stats.Statistic.RRDType;

/**
 * A collection of statistics normally maintained for making requests including total requests,
 * throughput per seconds, min/max/avg and 50th/95th/99th percentile response times, failure rate,
 * response size
 * <p>
 * These statistics all appear on the Cacti output HTTP page.
 */
public class RemoteAPIStatistics
{
  /**
   * The total number of requests made. Resets each day at midnight.
   */
  protected AutoResetIncrementingStatistic requestsTotal;
  
  /**
   * How many requests per second are currently being made
   */
  protected ThroughputStatistic requestsPerSecond;
  
  /**
   * The minimum response time.
   */
  protected MinimumStatistic latencyMin;
  
  /**
   * The maximum response time.
   */
  protected MaximumStatistic latencyMax;
  
  /**
   * The average response time.
   */
  protected AverageStatistic latencyAvg;
  
  /**
   * The mean response time (50th percentile).
   */
  protected PercentileStatistic latencyPercentile50;
  
  /**
   * The 95th percentile response time.
   */
  protected PercentileStatistic latencyPercentile95;
  
  /**
   * The 99th percentile response time.
   */
  protected PercentileStatistic latencyPercentile99;
  
  /**
   * The failure rate
   */
  protected PercentageStatistic failureRate;
  
  /**
   * The failure frequency
   */
  protected FrequencyStatistic failureFrequency;
  
  /**
   * The minimum response size
   */
  protected MinimumStatistic responseSizeMin;
  
  /**
   * The maximum response size
   */
  protected MaximumStatistic responseSizeMax;
  
  /**
   * The average response size.
   */
  protected AverageStatistic responseSizeAvg;
  
  /**
   * The 50th percentile response size
   */
  protected PercentileStatistic responseSizePercentile50;
  
  /**
   * The 95th percentile response size
   */
  protected PercentileStatistic responseSizePercentile95;
  
  /**
   * The 99th percentile response size.
   */
  protected PercentileStatistic responseSizePercentile99;
  
  @Deprecated
  public RemoteAPIStatistics( String name, String graphiteName, String description ) {
    this(name, graphiteName, description, RRDType.CACTI_AND_GRAPHITE);
  }
  
  @Deprecated
  public RemoteAPIStatistics( String name, String graphiteName, String description, ConversionType cactiConversionType,
      ConversionType graphiteConversionType,  boolean returnZeroForNoUpdate) {
    this(name, graphiteName, description, cactiConversionType, graphiteConversionType,
        returnZeroForNoUpdate, RRDType.CACTI_AND_GRAPHITE);
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
  public RemoteAPIStatistics( String name, String graphiteName, String description, RRDType rrdType )
  {
    if ( (Statistic.isValidCactiName(name) == false) || (name.length() > 11) )
    {
      // name must be less than 15 characters and we use 4 leaving 11 for the user.
      throw new IllegalArgumentException( "Name (" + name + ") must be 11 capital letters, digits, underscores or less." );
    }
    
    requestsTotal = new AutoResetIncrementingStatistic(name, graphiteName
        + ".total_requests", description,
        AutoResetIncrementingStatistic.Frequency.DAY, rrdType);
    requestsPerSecond = new ThroughputStatistic(name + "_SEC", graphiteName
        + ".requests_per_second", "(Requests per second) " + description, 1, rrdType);
    latencyMin = new MinimumStatistic(name + "_MIN", graphiteName
        + ".latency.min", "(Minimum latency) " + description, rrdType);
    latencyMax = new MaximumStatistic(name + "_MAX", graphiteName
        + ".latency.max", "(Maximum latency) " + description, rrdType);
    latencyAvg = new AverageStatistic(name + "_AVG", graphiteName
        + ".latency.avg", "(Average latency) " + description, rrdType);
    latencyPercentile50 = new PercentileStatistic(name + "_50", graphiteName
        + ".latency.50th", "(latency 50th percentile) " + description, 0.50, rrdType);
    latencyPercentile95 = new PercentileStatistic(name + "_95", graphiteName
        + ".latency.95th", "(latency 95th percentile) " + description, 0.95, rrdType);
    latencyPercentile99 = new PercentileStatistic(name + "_99", graphiteName
        + ".latency.99th", "(latency 99th percentile) " + description, 0.99, rrdType);
    failureRate = new PercentageStatistic(name + "_FR", graphiteName
        + ".failure.rate", "(Failure rate) " + description, 
        ConversionType.NONE, ConversionType.NONE, true, rrdType);
    failureFrequency = new FrequencyStatistic(name + "_FF", graphiteName
        + ".failure.frequency", "(Failure frequency) " + description,
        ConversionType.NONE, ConversionType.NONE, true, rrdType);
    responseSizeMin = new MinimumStatistic(name + "_RMN", graphiteName
        + ".response_size.min", "(Minimum response size) " + description,
        ConversionType.NONE, ConversionType.NONE, true, rrdType);
    responseSizeMax = new MaximumStatistic(name + "_RMX", graphiteName
        + ".response_size.max", "(Maximum response size) " + description,
        ConversionType.NONE, ConversionType.NONE, true, rrdType);
    responseSizeAvg = new AverageStatistic(name + "_RAV", graphiteName
        + ".response_size.avg", "(Average response size) " + description,
        ConversionType.NONE, ConversionType.NONE, true, rrdType);
    responseSizePercentile50 = new PercentileStatistic(name + "_R50",
        graphiteName + ".response_size.50th", "(response size 50th percentile) "
            + description, 0.50, ConversionType.NONE, ConversionType.NONE, true, rrdType);
    responseSizePercentile95 = new PercentileStatistic(name + "_R95",
        graphiteName + ".response_size.95th", "(response size 95th percentile) "
            + description, 0.95, ConversionType.NONE, ConversionType.NONE, true, rrdType);
    responseSizePercentile99 = new PercentileStatistic(name + "_R99",
        graphiteName + ".response_size.99th", "(response size 99th percentile) "
            + description, 0.99, ConversionType.NONE, ConversionType.NONE, true, rrdType);
  }

  public RemoteAPIStatistics( String name, String graphiteName, String description, ConversionType cactiConversionType,
      ConversionType graphiteConversionType, boolean returnZeroForNoUpdate, RRDType rrdType)
  {
    if (rrdType == RRDType.CACTI || rrdType == RRDType.CACTI_AND_GRAPHITE) {
      if ( (Statistic.isValidCactiName(name) == false) || (name.length() > 11) )
      {
        // name must be less than 15 characters and we use 4 leaving 11 for the user.
        throw new IllegalArgumentException( "Name (" + name + ") must be 11 capital letters, digits, underscores or less." );
      }
    }
    
    requestsTotal = new AutoResetIncrementingStatistic(name, graphiteName
        + ".total_requests", description,
        AutoResetIncrementingStatistic.Frequency.DAY, rrdType);
    requestsPerSecond = new ThroughputStatistic(name + "_SEC", graphiteName
        + ".requests_per_second", "(Requests per second) " + description, 1, rrdType);
    latencyMin = new MinimumStatistic(name + "_MIN", graphiteName
        + ".latency.min", "(Minimum latency) " + description,
        cactiConversionType, graphiteConversionType, returnZeroForNoUpdate, rrdType);
    latencyMax = new MaximumStatistic(name + "_MAX", graphiteName
        + ".latency.max", "(Maximum latency) " + description,
        cactiConversionType, graphiteConversionType, returnZeroForNoUpdate, rrdType);
    latencyAvg = new AverageStatistic(name + "_AVG", graphiteName
        + ".latency.avg", "(Average latency) " + description,
        cactiConversionType, graphiteConversionType, returnZeroForNoUpdate, rrdType);
    latencyPercentile50 = new PercentileStatistic(name + "_50", graphiteName
        + ".latency.50th", "(latency 50th percentile) " + description, 0.50,
        cactiConversionType, graphiteConversionType, returnZeroForNoUpdate, rrdType);
    latencyPercentile95 = new PercentileStatistic(name + "_95", graphiteName
        + ".latency.95th", "(latency 95th percentile) " + description, 0.95,
        cactiConversionType, graphiteConversionType, returnZeroForNoUpdate, rrdType);
    latencyPercentile99 = new PercentileStatistic(name + "_99", graphiteName
        + ".latency.99th", "(latency 99th percentile) " + description, 0.99,
        cactiConversionType, graphiteConversionType, returnZeroForNoUpdate, rrdType);
    failureRate = new PercentageStatistic(name + "_FR", graphiteName
        + ".failure.rate", "(Failure rate) " + description,
        ConversionType.NONE, ConversionType.NONE, true, rrdType);
    failureFrequency = new FrequencyStatistic(name + "_FF", graphiteName
        + ".failure.frequency", "(Failure frequency) " + description,
        ConversionType.NONE, ConversionType.NONE, true, rrdType);
    responseSizeMin = new MinimumStatistic(name + "_RMN", graphiteName
        + ".response_size.min", "(Minimum response size) " + description,
        ConversionType.NONE, ConversionType.NONE, true, rrdType);
    responseSizeMax = new MaximumStatistic(name + "_RMX", graphiteName
        + ".response_size.max", "(Maximum response size) " + description,
        ConversionType.NONE, ConversionType.NONE, true, rrdType);
    responseSizeAvg = new AverageStatistic(name + "_RAV", graphiteName
        + ".response_size.avg", "(Average response size) " + description,
        ConversionType.NONE, ConversionType.NONE, true, rrdType);
    responseSizePercentile50 = new PercentileStatistic(name + "_R50",
        graphiteName + ".response_size.50th",
        "(response size 50th percentile) " + description, 0.50,
        ConversionType.NONE, ConversionType.NONE, true, rrdType);
    responseSizePercentile95 = new PercentileStatistic(name + "_R95",
        graphiteName + ".response_size.95th",
        "(response size 95th percentile) " + description, 0.95,
        ConversionType.NONE, ConversionType.NONE, true, rrdType);
    responseSizePercentile99 = new PercentileStatistic(name + "_R99",
        graphiteName + ".response_size.99th",
        "(response size 99th percentile) " + description, 0.99,
        ConversionType.NONE, ConversionType.NONE, true, rrdType);
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
   * @param failure is true if the request failed
   * @param timeouts is the number of timeouts or failures
   * @param responseSize is the number of elements in the response. If failure is true, this
   * parameter is not used
   * @return The number of nanoseconds it took to process the request.
   */
  public long requestEnd( long start, boolean failure, int timeouts, long responseSize )
  {
    // How long did the request take?
    long nanoseconds = System.nanoTime() - start;
    
    // Record the request.
    requestsTotal.increment();
    requestsPerSecond.update();
    latencyMin.update( nanoseconds );
    latencyMax.update( nanoseconds );
    latencyAvg.update( nanoseconds );
    latencyPercentile50.update( nanoseconds );
    latencyPercentile95.update( nanoseconds );
    latencyPercentile99.update( nanoseconds );
   
    failureRate.updateForMultipleRequests(timeouts, !failure ? 1 : 0);
    failureFrequency.updateForMultipleRequests(timeouts);
    
    if (!failure) {
      responseSizeMin.update(responseSize);
      responseSizeMax.update(responseSize);
      responseSizeAvg.update(responseSize);
      responseSizePercentile50.update(responseSize);
      responseSizePercentile95.update(responseSize);
      responseSizePercentile99.update(responseSize);
    }
    
    // Return the response time of the request.
    return nanoseconds;
  }
  
  /**
   * Resets all of the statistics to start freshly tracking everything.  Possibly helpful
   * at the day rollover if this object is tracking only today's performance.
   */
  public void reset()
  {
    requestsTotal.reset();
    requestsPerSecond.reset();
    latencyMin.reset();
    latencyMax.reset();
    latencyAvg.reset();
    latencyPercentile50.reset();
    latencyPercentile95.reset();
    latencyPercentile99.reset();
    failureRate.reset();
    failureFrequency.reset();
    responseSizeMin.reset();
    responseSizeAvg.reset();
    responseSizeMax.reset();
    responseSizePercentile50.reset();
    responseSizePercentile95.reset();
    responseSizePercentile99.reset();
    
  }
  
  /**
   * @return The total number of requests.
   */
  public long getTotalRequests()
  {
    return requestsTotal.get();
  }
  
  /**
   * @return How many requests per second are being processed now.
   */
  public float getRequestsPerSecond()
  {
    return requestsPerSecond.get();
  }
  
  /**
   * @return The fastest response time, in seconds, in the last 5 minutes.
   */
  public double getMinimumResponseSec()
  {
    return latencyMin.getSeconds();
  }
  
  /**
   * @return The slowest response time, in seconds, in the last 5 minutes.
   */
  public double getMaxmimumResponseSec()
  {
    return latencyMax.getSeconds();
  }
  
  /**
   * @return The average response time, in seconds, in the last 5 minutes.
   */
  public double getAverageResponseSec()
  {
    return latencyAvg.getSeconds();
  }
  
  /**
   * @return The median (50th percentile) response time, in seconds, in the last 5 minutes.
   */
  public double get50thPercentileResponseSec()
  {
    return latencyPercentile50.getSeconds();
  }
  
  /**
   * @return The 95th percentile response time, in seconds, in the last 5 minutes.
   */
  public double get95thPercentileResponseSec()
  {
    return latencyPercentile95.getSeconds();
  }
  
  /**
   * @return The 99th percentile response time, in seconds, in the last 5 minutes.
   */
  public double get99thPercentileResponseSec()
  {
    return latencyPercentile99.getSeconds();
  }
  
  /**
   * @return failure rate
   */
  public double getFailureRate()
  {
    return failureRate.get();
  }
  
  /**
   * @return failure frequency
   */
  public long getFailureFrequency()
  {
    return failureFrequency.get();
  }
  
  /**
   * @return min response size
   */
  public long getResponseSizeMin()
  {
    return responseSizeMin.get();
  }
  
  /**
   * @return max response size
   */
  public long getResponseSizeMax()
  {
    return responseSizeMax.get();
  }
  
  /**
   * @return avg response size
   */
  public long getResponseSizeAvg()
  {
    return responseSizeAvg.get();
  }
  
  /**
   * @return response size 50th percentile
   */
  public long getResponseSizePercentile50()
  {
    return responseSizePercentile50.get();
  }
  
  /**
   * @return response size 95th percentile
   */
  public long getResponseSizePercentile95()
  {
    return responseSizePercentile95.get();
  }
  
  
  /**
   * @return response size 99th percentile
   */
  public long getResponseSizePercentile99()
  {
    return responseSizePercentile99.get();
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
    sb.append( requestsTotal.cactiName() ).append( "(" ).append( requestsTotal.description() ).append( ")\n" );
    sb.append( "  Total Requests   = " ).append( requestsTotal.get() ).append( "\n" );
    sb.append( "  Requests/Second  = " ).append( requestsPerSecond.get() ).append( "\n" );
    sb.append( "  Minimum Response = " ).append( latencyMin.get() ).append( "\n" );
    sb.append( "  Average Response = " ).append( latencyAvg.get() ).append( "\n" );
    sb.append( "  Maximum Response = " ).append( latencyMax.get() ).append( "\n" );
    sb.append( "  50th Percentile  = " ).append( latencyPercentile50.get() ).append( "\n" );
    sb.append( "  95th Percentile  = " ).append( latencyPercentile95.get() ).append( "\n" );
    sb.append( "  99th Percentile  = " ).append( latencyPercentile99.get() ).append( "\n" );
    sb.append( "  failure rate = " ).append( failureRate.get() ).append( "\n" );
    sb.append( "  failure frequency = " ).append( failureFrequency.get() ).append( "\n" );
    sb.append( "  Minimum Response Size = " ).append( responseSizeMin.get() ).append( "\n" );
    sb.append( "  Maximum Response Size = " ).append( responseSizeMax.get() ).append( "\n" );
    sb.append( "  Average Response Size = " ).append( responseSizeAvg.get() ).append( "\n" );
    sb.append( "  Response Size 50th Percentile = " ).append( responseSizePercentile50.get() ).append( "\n" );
    sb.append( "  Response Size 95th Percentile = " ).append( responseSizePercentile95.get() ).append( "\n" );
    sb.append( "  Response Size 99th Percentile = " ).append( responseSizePercentile99.get() ).append( "\n" );
    return sb.toString();
  }

  /**
   * Sets the conversion type for timing-related statistics
   *
   * @param conversionType
   */
  public void setConversionType(ConversionType conversionType) {
    latencyMin.setCactiConversionType(conversionType);
    latencyMax.setCactiConversionType(conversionType);
    latencyAvg.setCactiConversionType(conversionType);
    latencyPercentile50.setCactiConversionType(conversionType);
    latencyPercentile95.setCactiConversionType(conversionType);
    latencyPercentile99.setCactiConversionType(conversionType);
  }
  
  /**
   * Sets the conversion type for timing-related statistics
   *
   * @param conversionType
   */
  public void setGraphiteConversionType(ConversionType conversionType) {
    latencyMin.setGraphiteConversionType(conversionType);
    latencyMax.setGraphiteConversionType(conversionType);
    latencyAvg.setGraphiteConversionType(conversionType);
    latencyPercentile50.setGraphiteConversionType(conversionType);
    latencyPercentile95.setGraphiteConversionType(conversionType);
    latencyPercentile99.setGraphiteConversionType(conversionType);
  }
}
