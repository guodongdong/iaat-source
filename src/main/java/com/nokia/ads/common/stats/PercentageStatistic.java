package com.nokia.ads.common.stats;


/**
 * Keeps track of the percentage of the time some event occurs.  Useful for tracking cache hit rates.
 */
public class PercentageStatistic
  extends TimingStatistic
{
  @Deprecated
  public PercentageStatistic( String name, String description, long period ) {
    this(name, description, period, RRDType.CACTI);
  }

  @Deprecated
  public PercentageStatistic( String name, String description ) {
    this(name, description, RRDType.CACTI);
  }

  @Deprecated
  public PercentageStatistic( String name, String graphiteName, String description, long period) {
    this(name, graphiteName, description, period, RRDType.CACTI_AND_GRAPHITE);
  }

  @Deprecated
  public PercentageStatistic( String name, String graphiteName, String description, long period,
      ConversionType cactiConversionType,
      ConversionType graphiteConversionType,  boolean returnZeroForNoUpdates)
  {
    this(name, graphiteName, description, period, cactiConversionType, graphiteConversionType,
        returnZeroForNoUpdates, RRDType.CACTI_AND_GRAPHITE);
  }

  @Deprecated
  public PercentageStatistic( String name, String graphiteName, String description,
      ConversionType cactiConversionType,
      ConversionType graphiteConversionType,  boolean returnZeroForNoUpdates)
  {
    this(name, graphiteName, description, cactiConversionType, graphiteConversionType,
        returnZeroForNoUpdates, RRDType.CACTI_AND_GRAPHITE);
  }
      
  @Deprecated
  public PercentageStatistic( String name, String graphiteName, String description ) {
    this(name, graphiteName, description, RRDType.CACTI_AND_GRAPHITE);
  }
  
  /**
   * Creates a percentage statistic and places it into the <code>StatisticsManager</code>.
   * 
   * @param name is the label for this statistic which is used by Cacti for graphing.
   *  It must therefore conform to Cacti label naming restrictions meaning it is
   *  1 to 15 capital letters, numbers, or underscores (e.g. LATENCY_99_CPC).
   * @param description is a string that other people will understand when debugging this service.
   * @param period is the number of seconds over which to record the slowest time.
   */
  public PercentageStatistic( String name, String description, long period, RRDType rrdType )
  {
    this( name, name, description, period, 
        ConversionType.NONE, ConversionType.NONE, true, rrdType);
  }

  public PercentageStatistic( String name, String description, RRDType rrdType )
  {
    this( name, description, 300, rrdType);
  }

  public PercentageStatistic( String name, String description, ConversionType conversionType, RRDType rrdType )
  {
    this( name, name, description, 300, conversionType, conversionType, true, rrdType);
  }
  
  public PercentageStatistic( String name, String graphiteName, String description, long period, RRDType rrdType) {
    this(name, graphiteName, description, period, ConversionType.NONE, ConversionType.NONE, true, rrdType );
  }

  public PercentageStatistic( String name, String graphiteName, String description, long period,
      ConversionType cactiConversionType,
      ConversionType graphiteConversionType,  boolean returnZeroForNoUpdates, RRDType rrdType)
  {
    super( name, graphiteName, description, period,  cactiConversionType,
        graphiteConversionType,
        returnZeroForNoUpdates, rrdType);
  }

  public PercentageStatistic( String name, String graphiteName, String description,
      ConversionType cactiConversionType,
      ConversionType graphiteConversionType,  boolean returnZeroForNoUpdates,
      RRDType rrdType)
  {
    super( name, graphiteName, description, 300,  cactiConversionType,
        graphiteConversionType,
        returnZeroForNoUpdates,
        rrdType);
  }
  
  public PercentageStatistic( String name, String graphiteName, String description, RRDType rrdType )
  {
    this( name, graphiteName, description, 300, ConversionType.NONE, ConversionType.NONE, true, rrdType );
  }
  
  
  /**
   * Returns the average response time.
   * 
   * @param lastProcessedValueNanos is the value returned from the previous call to
   *  <code>processUpdate</code> or a negative number if it hasn't been called yet.
   * @param newValueNanos is the timing value being processed right now.
   * @return What is the new timing to store.
   */
  @Override
  protected long processUpdate( long lastProcessedValueNanos, long newValueNanos )
  {
    // Keep the total time it took to process all requests.  In processGet() we'll
    // divide that time by how many requests there were.
    if ( lastProcessedValueNanos < 0 )
    {
      return newValueNanos;
    }
    else
    {
      return lastProcessedValueNanos + newValueNanos;
    }
  }

  /**
   * Returns the average value of processed response times.
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
    // Sum up all the requests.
    long totalHits = 0;
    long totalGets = 0;
    
    for ( int i = 0; i < values.length; i++ )
    {
      totalHits += values[i];
      totalGets += totalRequests[i];
    }
    
    // Calculate the percent
    long pct = 0;
    
    if ( totalGets > 0 )
    {
      pct = (long)(100*((double)totalHits / totalGets));
    }
    
    return pct;
  }
  
  public void updateForMultipleRequests(long hits, long misses) {
    super.updateForMultipleRequests(hits, misses+hits);
  }
  
  /**
   * Records a "hit" which will increase the percentage measured.
   */
  public void hit()
  {
    super.update(1);
  }
  
  /**
   * Records a "miss" which will increase the percentage measured.
   */
  public void miss()
  {
    super.update(0);
  }
  
}
