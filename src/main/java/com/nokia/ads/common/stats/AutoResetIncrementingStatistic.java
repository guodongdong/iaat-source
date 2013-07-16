package com.nokia.ads.common.stats;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicLong;

/**
 * This <code>IncrementingStatistic</code> automatically resets at a fixed frequency.
 * @see IncrementingStatistic
 */
public class AutoResetIncrementingStatistic extends IncrementingStatistic {

  /**
   * IncrementingStatistic reset frequency. 
   */
  public enum Frequency {
    DAY,
    HOUR,
    MINUTE,
    SECOND
  }

  protected Frequency frequency;
  protected AtomicLong nextResetTimestamp;

  @Deprecated
  public AutoResetIncrementingStatistic(String name, 
      String description,
      AutoResetIncrementingStatistic.Frequency frequency) {
    this(name, description, frequency, RRDType.CACTI);
  }
  
  @Deprecated
  public AutoResetIncrementingStatistic(String name, 
      String graphiteName, 
      String description, 
      AutoResetIncrementingStatistic.Frequency frequency,
      ConversionType cactiConversionType, 
      ConversionType graphiteConversionType, 
      boolean returnZeroForNoUpdates) {
    this(name, graphiteName, description, frequency, cactiConversionType, graphiteConversionType, 
        returnZeroForNoUpdates, RRDType.CACTI_AND_GRAPHITE);
  }

  @Deprecated
  public AutoResetIncrementingStatistic(String name, 
      String graphiteName,
      String description,
      AutoResetIncrementingStatistic.Frequency frequency) {
    this(name, graphiteName, description, frequency, RRDType.CACTI_AND_GRAPHITE);
  }
  
  /**
   * Creates a reset-able incrementing statistic and places it into the <code>StatisticsManager</code>.
   * 
   * @param name is the label for this statistic which is used by Cacti for graphing.
   *  It must therefore conform to Cacti label naming restrictions meaning it is
   *  1 to 15 capital letters, numbers, or underscores (e.g. LATENCY_99_CPC).
   * @param description is a string that other people will understand when debugging this service.
   * @param frequency is the fixed frequency that the incrementing statistic gets reset
   */
  public AutoResetIncrementingStatistic(String name, 
                                        String description,
                                        AutoResetIncrementingStatistic.Frequency frequency,
                                        RRDType rrdType) {
    this(name, name, description, frequency, rrdType);
  }

  public AutoResetIncrementingStatistic(String name, 
      String description,
      AutoResetIncrementingStatistic.Frequency frequency,
      ConversionType conversionType, RRDType rrdType) {
    this(name, name, description, frequency, conversionType, conversionType, true, rrdType);
  }
  
  public AutoResetIncrementingStatistic(String name, 
      String graphiteName, 
      String description, 
      AutoResetIncrementingStatistic.Frequency frequency,
      ConversionType cactiConversionType, 
      ConversionType graphiteConversionType, 
      boolean returnZeroForNoUpdates,
      RRDType rrdType) {
    super(name, graphiteName, description, cactiConversionType, graphiteConversionType, returnZeroForNoUpdates, rrdType);
    this.frequency = frequency;
    this.nextResetTimestamp = new AtomicLong(getNextResetTimestamp());
  }
  
  public AutoResetIncrementingStatistic(String name, 
          String graphiteName,
          String description,
          AutoResetIncrementingStatistic.Frequency frequency,
          RRDType rrdType) {
    this(name, graphiteName, description, frequency, ConversionType.NONE, ConversionType.NONE, true, rrdType);
  }

  /**
   * Returns the GMT timestamp in milliseconds for next reset. AutoResetIncrementingStatistic always resets on starting of 
   * the day (00:00:00.00), starting of the hour (00:00.00), starting of the minute (00.00) and starting of the second.
   * @return the timestamp for next reset
   */
  private long getNextResetTimestamp() {
    Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
    switch (this.frequency) {
    case DAY:
      cal.add(Calendar.DATE, 1);
      cal.set(Calendar.HOUR, 0);
      cal.set(Calendar.MINUTE, 0);
      cal.set(Calendar.SECOND, 0);
      break;
    case HOUR:
      cal.add(Calendar.HOUR, 1);
      cal.set(Calendar.MINUTE, 0);
      cal.set(Calendar.SECOND, 0);      
      break;
    case MINUTE:
      cal.add(Calendar.MINUTE, 1);
      cal.set(Calendar.SECOND, 0);
      break;
    case SECOND:
      cal.add(Calendar.SECOND, 1);
      break;
    default:
      throw new IllegalArgumentException("Unknown Frequency");
    }
    return cal.getTimeInMillis();
  }

  @Override
  public long increment() {
    if (System.currentTimeMillis() > nextResetTimestamp.get()) {
      nextResetTimestamp.set(getNextResetTimestamp());
      reset();
    }
    return super.increment();
  }

  @Override
  public long get() {
    if (System.currentTimeMillis() > nextResetTimestamp.get()) {
      nextResetTimestamp.set(getNextResetTimestamp());
      reset();
    }

    return super.get();
  }
}
