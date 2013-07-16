package com.nokia.ads.common.stats;

/**
 * Keeps track of the maximum of some event.  Useful for seeing the maximum response time.
 */
public class MaximumStatistic
  extends TimingStatistic
{
  @Deprecated
  public MaximumStatistic( String name, String graphiteName, String description, int period, 
      ConversionType cactiConversionType,
      ConversionType graphiteConversionType,  boolean returnZeroForNoUpdates) {
    this(name, graphiteName, description, period, cactiConversionType, graphiteConversionType,
        returnZeroForNoUpdates, RRDType.CACTI_AND_GRAPHITE);
  }

  @Deprecated
  public MaximumStatistic( String name, String graphiteName, String description, 
      ConversionType cactiConversionType,
      ConversionType graphiteConversionType,  boolean returnZeroForNoUpdates) {
    this(name, graphiteName, description, cactiConversionType, graphiteConversionType, returnZeroForNoUpdates,
        RRDType.CACTI_AND_GRAPHITE);
  }

  @Deprecated
  public MaximumStatistic( String name, String description, int period ) {
    this(name, description, period, RRDType.CACTI);
  }

  @Deprecated
  public MaximumStatistic( String name, String graphiteName, String description, int period ) {
    this(name, graphiteName, description, period, RRDType.CACTI_AND_GRAPHITE);
  }
  
  @Deprecated
  public MaximumStatistic( String name, String description) {
    this(name, description, RRDType.CACTI);
  }

  @Deprecated
  public MaximumStatistic( String name, String graphiteName, String description ) {
    this(name, graphiteName, description, RRDType.CACTI_AND_GRAPHITE);
  }
  
  /**
   * Creates a maximum statistic and places it into the <code>StatisticsManager</code>.
   * 
   * @param name is the label for this statistic which is used by Cacti for graphing.
   *  It must therefore conform to Cacti label naming restrictions meaning it is
   *  1 to 15 capital letters, numbers, or underscores (e.g. LATENCY_99_CPC).
   * @param description is a string that other people will understand when debugging this service.
   * @param period is the number of seconds over which to record the slowest time.
   *  Set this to 0 to record since the start of the service.
   */
  public MaximumStatistic( String name, String graphiteName, String description, int period, 
      ConversionType cactiConversionType,
      ConversionType graphiteConversionType,  boolean returnZeroForNoUpdates, RRDType rrdType)
  {
    super( name, graphiteName, description, period,  cactiConversionType, 
        graphiteConversionType,returnZeroForNoUpdates, rrdType);
  }
  
  public MaximumStatistic( String name, String graphiteName, String description, 
      ConversionType cactiConversionType,
      ConversionType graphiteConversionType,  boolean returnZeroForNoUpdates,
      RRDType rrdType)
  {
    this( name, graphiteName, description, 300,  cactiConversionType, 
        graphiteConversionType,returnZeroForNoUpdates, rrdType);
  }
  public MaximumStatistic( String name, String description, int period, RRDType rrdType )
  {
    this( name, name, description, period, rrdType );
  }

  public MaximumStatistic( String name, String graphiteName, String description, int period,
      RRDType rrdType)
  {
    this( name, graphiteName, description, period,
        ConversionType.NANOSECONDS_TO_SECONDS,
        ConversionType.NANOSECONDS_TO_MILLISECONDS, 
         false, rrdType);
  }

  /**
   * Creates a maximum statistic for the past 5 minutes and places it into the <code>StatisticsManager</code>.
   * 
   * @param name is the label for this statistic which is used by Cacti for graphing.
   *  It must therefore conform to Cacti label naming restrictions meaning it is
   *  1 to 15 capital letters, numbers, or underscores (e.g. LATENCY_99_CPC).
   * @param description is a string that other people will understand when debugging this service.
   */
  
  public MaximumStatistic( String name, String description, RRDType rrdType)
  {
    this( name, name, description, rrdType );
  }
  
  public MaximumStatistic( String name, String description, ConversionType conversionType, RRDType rrdType)
  {
    this( name, name, description, 300, conversionType, conversionType, false, rrdType);
  }
  
  public MaximumStatistic( String name, String graphiteName, String description, RRDType rrdType )
  {
    this( name, graphiteName, description, 300, rrdType );  // 5 minute period is what Cacti polls at
  }

  /**
   * Returns the maximum response time.
   * 
   * @param lastProcessedValueNanos is the value returned from the previous call to
   *  <code>processUpdate</code> or a negative number if it hasn't been called yet.
   * @param newValueNanos is the timing value being processed right now.
   * @return What is the new timing to store.
   */
  @Override
  protected long processUpdate( long lastProcessedValueNanos, long newValueNanos )
  {
    if ( lastProcessedValueNanos > newValueNanos )
    {
      return lastProcessedValueNanos;
    }
    else
    {
      return newValueNanos;
    }
  }

  /**
   * Returns the maximum value of processed response times.
   * 
   * @param values is an array of buckets filled with <code>processUpdate</code>
   *  results.  It is never <code>null</code> but may be of zero length.
   * @param totalRequests is an array of buckets filled with the total number
   *  of requests for that period.  It is always as big as <code>values</code> and
   *  the index into both arrays is for the same period.
   * @return The result for <code>get</code>.
   */
  @Override
  protected long processGet( long[] values, long[] totalRequests )
  {
    long max = 0;
    
    for ( int i = 0; i < values.length; i++ )
    {
      if ( values[i] > max )
      {
        max = values[i];
      }
    }
    
    return max;
  }
}
