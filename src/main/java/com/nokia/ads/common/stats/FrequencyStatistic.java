package com.nokia.ads.common.stats;

/**
 * Tracks how ofent an event, such as timeouts, happen over a period of time.  For example if
 * the period is set to 5 minutes (the Cacti polling interval) a frequency statistic will show
 * how many times we timed out each 5 minute period.
 */
public class FrequencyStatistic
  extends IncrementingStatistic
{
  @Deprecated
  public FrequencyStatistic( String name, String description ) {
    this(name, description, RRDType.CACTI);
  }

  @Deprecated
  public FrequencyStatistic( 
      String name, 
      String graphiteName, 
      String description,
      ConversionType cactiConversionType, 
      ConversionType graphiteConversionType, 
      boolean returnZeroForNoUpdates ) {
    this(name, graphiteName, description, cactiConversionType, graphiteConversionType, 
        returnZeroForNoUpdates, RRDType.CACTI_AND_GRAPHITE);
  }

  @Deprecated
  public FrequencyStatistic( String name, String graphiteName, String description ) {
    this(name, graphiteName, description, RRDType.CACTI_AND_GRAPHITE);
  }

  /**
   * Creates a frequency statistic and places it into the <code>StatisticsManager</code>.
   * 
   * @param name is the label for this statistic which is used by Cacti for graphing.
   *  It must therefore conform to Cacti label naming restrictions meaning it is
   *  1 to 15 capital letters, numbers, or underscores (e.g. LATENCY_99_CPC).
   * @param description is a string that other people will understand when debugging this service.
   */
  public FrequencyStatistic( String name, String description, RRDType rrdType)
  {
    this( name, name, description, rrdType );
  }
  
  public FrequencyStatistic( 
      String name, 
      String graphiteName, 
      String description,
      ConversionType cactiConversionType, 
      ConversionType graphiteConversionType, 
      boolean returnZeroForNoUpdates,
      RRDType rrdType) {
    super(name, 
        graphiteName, 
        description,
        cactiConversionType,
        graphiteConversionType, 
        returnZeroForNoUpdates,
        rrdType);
  }
  
  public FrequencyStatistic( String name, String graphiteName, String description, RRDType rrdType )
  {
    this( name, graphiteName, description, ConversionType.NONE , ConversionType.NONE, true, rrdType );
  }
  
  public FrequencyStatistic( String name, String graphiteName, String description, ConversionType conversionType, RRDType rrdType )
  {
    this( name, name, description, conversionType, conversionType, true, rrdType );
  }
  
  public void updateForMultipleRequests(long delta){
    this.count.addAndGet(delta);
  }
  
  /**
   * @return The current value of the statistic.
   */
  public long get()
  {
    long frequency = super.get();
    reset();
    return frequency;
  }
}
