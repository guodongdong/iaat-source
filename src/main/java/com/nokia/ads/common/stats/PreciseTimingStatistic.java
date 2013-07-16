package com.nokia.ads.common.stats;

/**
 * Base class for statistics recorded only over recent history (as opposed to the lifetime of the process).
 * For example taking the minimum response time over the past 5 minutes is different than the since the
 * process started.
 * <p>
 * Timings are recorded as an average over the last several periods.  So the minimum of the last 5 minutes
 * will actually be within the last 4:30-5:30.  This is much more efficient to calculate and still accurate
 * enough for service statistics.
 */
public abstract class PreciseTimingStatistic
  extends Statistic
{
  /**
   * The number of periods to break down what we are measuring into.  So if we are monitoring the last
   * 10 minutes and this value is 10 we'd have a bucket for each minute.
   */
  private static final int SAMPLING_PERIODS = 10;

  /**
   * A circular buffer that holds the values processed for each of the <code>SAMPLING_PERIODS</code.
   * These values are dependent on the subclass and are the returned values from <code>processUpdate</code>.
   * For example something monitoring the minimum response time would store the minimum value.
   * <p>
   * Values are initialized to -1.  They are non-negative after the first call to <code>processUpdate</code>.
   */
  private long[] values;

  /**
   * The number of requests for each period.  This array is always the same length as <code>values</code>.
   */
  private long[] numRequests;

  /**
   * The index of <code>values</code> with the oldest measurement.
   */
  private int oldestPeriod;

  /**
   * The index of <code>values</code> with the current measurement.
   */
  private int currentPeriod;

  /**
   * The time interval, in nanoseconds, that each period is.  If this is calculating something per
   * second this would be 1,000,000,000.
   */
  private final long duration;

  /**
   * The system time of when the next call to <code>update</code> should advance the circular buffer.
   */
  private long nextInterval;

  @Deprecated
  public PreciseTimingStatistic( String name, String graphiteName, String description, long duration, 
      ConversionType cactiConversionType, ConversionType graphiteConversionType, boolean returnZeroForNoUpdates) {
    this(name, graphiteName, description, duration, cactiConversionType, graphiteConversionType, returnZeroForNoUpdates,
        RRDType.CACTI_AND_GRAPHITE);
  }

  @Deprecated
  public PreciseTimingStatistic( String name, String graphiteName, String description, long duration ) {
    this(name, graphiteName, description, duration, RRDType.CACTI_AND_GRAPHITE);
  }
  
  @Deprecated
  public PreciseTimingStatistic( String name, String description, long duration ) {
    this(name, description, duration, RRDType.CACTI);
  }
  
  /**
   * Creates a timing statistic and places it into the <code>StatisticsManager</code>.
   *
   * @param name is the label for this statistic which is used by Cacti for graphing.
   *  It must therefore conform to Cacti label naming restrictions meaning it is
   *  1 to 15 capital letters, numbers, or underscores (e.g. LATENCY_99_CPC).
   * @param description is a string that other people will understand when debugging this service.
   * @param duration is the number of seconds to measure over.
   */
  public PreciseTimingStatistic( String name, String graphiteName, String description, long duration, 
      ConversionType cactiConversionType, ConversionType graphiteConversionType, boolean returnZeroForNoUpdates,
      RRDType rrdType)
  {
    super( name, graphiteName, description, cactiConversionType, graphiteConversionType, returnZeroForNoUpdates, rrdType );

    if ( duration < 1 )
    {
      throw new IllegalArgumentException( "Duration (" + duration + ") must be at least 1 second." );
    }

    reset();
    
    this.duration = duration * 1000000000 / SAMPLING_PERIODS;
    this.nextInterval = System.nanoTime() + this.duration;
  }

  public PreciseTimingStatistic( String name, String graphiteName, String description, long duration,
      RRDType rrdType) {
    this(name, graphiteName, description, duration,
        ConversionType.NANOSECONDS_TO_SECONDS,
        ConversionType.NANOSECONDS_TO_MILLISECONDS, 
         false, rrdType);
  }
  
  public PreciseTimingStatistic( String name, String description, long duration, RRDType rrdType )
  {
    this( name, name, description, duration, rrdType );
  }

  public PreciseTimingStatistic( String name, String description, long duration, ConversionType conversionType, RRDType rrdType )
  {
    this(name, name, description, duration, conversionType, conversionType, false, rrdType);
  }
  
  /**
   * Implemented by the subclass to handle an <code>update</code>.  For example if the
   * subclass is measuring the maximum timing then it would return the maximum of
   * <code>lastProcessedValueNanos</code> and <code>newValue</code>.  Next time this method is
   * called the <code>lastProcessedValueNanos</code> would be whatever was returned.
   *
   * @param lastProcessedValueNanos is the value returned from the previous call to
   *  <code>processUpdate</code> or a negative number if it hasn't been called yet.
   * @param newValueNanos is the timing value being processed right now.
   * @return What is the new timing to store.
   */
  protected abstract long processUpdate( long lastProcessedValueNanos, long newValueNanos );

  /**
   * Advances nextInterval, currentPeriod, and oldestPeriod on a get or update, if necessary
   */
  protected void advancePeriod() {
    long now = System.nanoTime();
    if(now > nextInterval) {
      int intervalsPast = (int) ((now - nextInterval) / duration) + 1;
      nextInterval = now + duration;
     
      if (intervalsPast > SAMPLING_PERIODS) {
        // All intervals have past so erase everything and start over.
        reset();
      } else {
        // If we skipped more than one interval erase all buckets we past over.
        boolean circledBuffer = false;
        
        for (int i = 1; i <= intervalsPast; i++) {
          int index = (i + currentPeriod) % SAMPLING_PERIODS;
          values[index] = -1;
          numRequests[index] = 0;
          
          if ( index == oldestPeriod )  {
            circledBuffer = true;
          }
        }
        
        int nextPeriod = (currentPeriod + intervalsPast) % SAMPLING_PERIODS;
        
        // Did we circle the buffer?
        if ( circledBuffer ) {
          oldestPeriod = (nextPeriod + 1) % SAMPLING_PERIODS;
        }
        
        currentPeriod = nextPeriod;
      }
    }
  }
  
  protected synchronized void updateForMultipleRequests(long totalTime, long totalRequests) {
    advancePeriod();
    values[currentPeriod] = processUpdate(values[currentPeriod], totalTime);
    numRequests[currentPeriod] += totalRequests;
  }
  
  /**
   * Records that a timed event took place.
   *
   * @param newValueNanos is the latest timing in nanoseconds.
   */
  public synchronized void update(long newValueNanos) {
    advancePeriod();
    // Record this event.
    values[currentPeriod] = processUpdate(values[currentPeriod], newValueNanos);
    numRequests[currentPeriod] += 1;
  }

  /**
   * Implemented by the subclass to handle a <code>get</code>. For example if
   * the subclass is measuring the minimum time then it would take the minimum
   * of all the values.
   *
   * @param values
   *          is an array of buckets filled with <code>processUpdate</code>
   *          results. It is never <code>null</code> but may be of zero
   *          length.
   * @param requests
   *          is an array of buckets filled with the total number of requests
   *          for that period. It is always as big as <code>values</code> and
   *          the index into both arrays is for the same period.
   * @return The result for <code>get</code>.
   */
  protected abstract double processGet( long[] values, long[] requests );

  /**
   * @return The timing statistic in nanoseconds, number of request per sec, etc.
   */
  public double get()
  {
    // Create an array out of values for the subclass to process.
    long[] tempValues = null;
    long[] tempNumRequests = null;
    long[] validValues = new long[SAMPLING_PERIODS];
    long[] validNumRequests = new long[SAMPLING_PERIODS];
    int periodsToProcess = 0;
    
    synchronized(this) {
      advancePeriod();
      int stop = (currentPeriod + 1 ) % SAMPLING_PERIODS;
      int i = oldestPeriod;
      do {
        if (values[i] != -1) {
          validValues[periodsToProcess] = values[i];
          validNumRequests[periodsToProcess] = numRequests[i];
          periodsToProcess++;
        }
        i = (i + 1) % SAMPLING_PERIODS;
      } while (i != stop);
    }
    
    tempValues = new long[periodsToProcess];
    tempNumRequests = new long[periodsToProcess];
    System.arraycopy(validValues, 0, tempValues, 0, periodsToProcess);
    System.arraycopy(validNumRequests, 0, tempNumRequests, 0, periodsToProcess);

    // Have the subclass process all the periods.
    double result = processGet(tempValues, tempNumRequests );
    return result;
  }

  /**
   * @return The timing measurement, in seconds, right now.
   */
  public double getSeconds()
  {
    return get() / 1000000000.0;
  }

  /**
   * Resets the statistic.
   *
   */
  @Override
  public synchronized void reset()
  {
    values = new long[SAMPLING_PERIODS];
    numRequests = new long[SAMPLING_PERIODS];

    for ( int i = 0; i < SAMPLING_PERIODS; i++ )
    {
      values[i] = -1;
      numRequests[i] = 0;
    }

    oldestPeriod = 0;
    currentPeriod = 0;
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
    double v = convert(get(), conversionType);
    
    if ( v > 0 || 
        conversionType == ConversionType.NONE ||
        this.isReturnZeroForNoUpdates())
    {
      value = double2string( v );
    }
    return value;
  }
  
}
