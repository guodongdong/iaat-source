package com.nokia.ads.common.stats;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Incrementing statistics keep track of how many times an event has occured.  For example the
 * number of times an API method has been called or number of timeouts.
 */
public class IncrementingStatistic
  extends Statistic
{
  /**
   * Our ever-increasing statistic.
   */
  protected AtomicLong count = new AtomicLong();

  @Deprecated
  public IncrementingStatistic(String name, String description) {
    this(name, description, RRDType.CACTI);
  }

  @Deprecated
  public IncrementingStatistic(String name, String graphiteName, String description) {
    this(name, graphiteName, description, RRDType.CACTI_AND_GRAPHITE);
  }

  @Deprecated
  public IncrementingStatistic( 
      String name, 
      String graphiteName, 
      String description,
      ConversionType cactiConversionType,
      ConversionType graphiteConversionType,
      boolean returnZeroForNoUpdates) {
    this(name, graphiteName, description, cactiConversionType, graphiteConversionType, returnZeroForNoUpdates, RRDType.CACTI_AND_GRAPHITE);
  }
  
  /**
   * Creates an incrementing statistic set to 0 and places it into the <code>StatisticsManager</code>.
   * 
   * @param name is the label for this statistic which is used by Cacti for graphing.
   *  It must therefore conform to Cacti label naming restrictions meaning it is
   *  1 to 15 capital letters, numbers, or underscores (e.g. LATENCY_99_CPC).
   * @param description is a string that other people will understand when debugging this service.
   */
  public IncrementingStatistic(String name, String description, RRDType rrdType)
  {
    this(name, name, description, rrdType);
  }
  
  public IncrementingStatistic(String name, String description, ConversionType conversionType, RRDType rrdType)
  {
    this(name, name, description, conversionType, conversionType, true, rrdType);
  }

  public IncrementingStatistic(String name, String graphiteName, String description, RRDType rrdType)
  {
    this(name, graphiteName, description, ConversionType.NONE, ConversionType.NONE, true, rrdType);
  }
  
  public IncrementingStatistic( 
      String name, 
      String graphiteName, 
      String description,
      ConversionType cactiConversionType,
      ConversionType graphiteConversionType,
      boolean returnZeroForNoUpdates, 
      RRDType rrdType) {
    super(name, graphiteName, description,
        cactiConversionType,  
        graphiteConversionType, returnZeroForNoUpdates, rrdType);
  }
  
  /**
   * @return The current value of the statistic.
   */
  public long get()
  {
    return count.get();
  }
  
  /**
   * Increments the statistic by 1.
   * 
   * @return The new value of the statistic after incrementing.
   */
  public long increment()
  {
    return count.incrementAndGet();
  }

  /**
   * Resets the statistic to 0.
   */
  @Override
  public void reset()
  {
    count.set( 0 );
  }

  
  /**
   * @return The value of this statistic.  If <code>null</code> or the empty string this
   *  will be assumed to have no data.
   *  
   */
  @Override
  public String value(ConversionType conversionType)
  {
    String value = null;
    long v = (long)convert(get(), conversionType);
    
    if ( v > 0 || 
        conversionType == ConversionType.NONE || 
        this.isReturnZeroForNoUpdates())
    {
      value = String.valueOf( v );
    }
    return value;
  }
}
