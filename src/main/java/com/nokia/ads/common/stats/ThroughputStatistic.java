package com.nokia.ads.common.stats;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicLongArray;

/**
 * Keeps track of the throughput of some event. Useful for seeing how many
 * requests per second are being handled.
 * <p>
 * Throughput is recorded as an average over the last several periods. So if
 * requests per second are being monitored the returned value will be an average
 * of the last few seconds. This is both efficient to compute and a good measure
 * since it is less susceptible to outliers than measuring just the last period.
 */
public class ThroughputStatistic extends Statistic {

  private static final int NUM_SAMPLING_WINDOW = 10;

  protected int currentIndex;

  protected AtomicLongArray countArray = null;

  protected AtomicLong nextResetTime = null;

  protected long period;

  @Deprecated
  public ThroughputStatistic(String name, String graphiteName, String description, int period, 
      ConversionType cactiConversionType, ConversionType graphiteConversionType, boolean returnZeroForNoUpdates) {
    this(name, graphiteName, description, period, cactiConversionType, graphiteConversionType,
        returnZeroForNoUpdates, RRDType.CACTI_AND_GRAPHITE);
  }

  @Deprecated
  public ThroughputStatistic(String name, String graphiteName, String description, int period) {
    this(name, graphiteName, description, period, RRDType.CACTI_AND_GRAPHITE);
  }

  @Deprecated
  public ThroughputStatistic(String name, String description, int period) {
    this(name, description, period, RRDType.CACTI);
  }
  
  @Deprecated
  public ThroughputStatistic(String name, String description) {
    this(name, description, RRDType.CACTI);
  }

  @Deprecated
  public ThroughputStatistic(String name, String graphiteName, String description) {
    this(name, graphiteName, description, RRDType.CACTI_AND_GRAPHITE);
  }
  
  /**
   * Creates a throughput statistic and places it into the
   * <code>StatisticsManager</code>.
   *
   * @param name
   *          is the label for this statistic which is used by Cacti for
   *          graphing. It must therefore conform to Cacti label naming
   *          restrictions meaning it is 1 to 15 capital letters, numbers, or
   *          underscores (e.g. LATENCY_99_CPC).
   * @param description
   *          is a string that other people will understand when debugging this
   *          service.
   * @param period
   *          is the number of seconds over which to record the slowest time.
   *          Set this to 0 to record since the start of the service.
   */

    public ThroughputStatistic(String name, String graphiteName, String description, int period, 
        ConversionType cactiConversionType, ConversionType graphiteConversionType, boolean returnZeroForNoUpdates,
        RRDType rrdType) {
    super(name, graphiteName, description, cactiConversionType, graphiteConversionType, returnZeroForNoUpdates, rrdType);
    this.currentIndex = 0;
    this.countArray = new AtomicLongArray(NUM_SAMPLING_WINDOW);
    this.nextResetTime = new AtomicLong();
    this.period = period * 1000;
    this.reset();
  }

  public ThroughputStatistic(String name, String graphiteName, String description, int period, RRDType rrdType) {
    this(name, graphiteName, description, period, ConversionType.NONE, ConversionType.NONE, true, rrdType);
  }

  public ThroughputStatistic(String name, String description, int period, RRDType rrdType) {
    this( name, name, description, period, rrdType );
  }

  public ThroughputStatistic(String name, String description, int period, ConversionType conversionType, RRDType rrdType) {
    this(name, name, description, period, conversionType, conversionType, true, rrdType);
  }
  
  /**
   * Creates a throughput statistic measuring per second and places it into the
   * <code>StatisticsManager</code>.
   *
   * @param name
   *          is the label for this statistic which is used by Cacti for
   *          graphing. It must therefore conform to Cacti label naming
   *          restrictions meaning it is 1 to 15 capital letters, numbers, or
   *          underscores (e.g. LATENCY_99_CPC).
   * @param description
   *          is a string that other people will understand when debugging this
   *          service.
   */
  public ThroughputStatistic(String name, String description, RRDType rrdType) {
    this(name, name, description, 1, rrdType);
  }

  public ThroughputStatistic(String name, String graphiteName, String description, RRDType rrdType) {
    this(name, graphiteName, description, 1, rrdType);
  }
  
  /**
   * @deprecated use update() instead
   */
  @Deprecated
  public synchronized void update(long notUsed) {
    advanceIndex();
    this.countArray.getAndIncrement(currentIndex);
  }

  
  public synchronized void updateForMultipleRequests(long numRequests) {
    advanceIndex();
    this.countArray.getAndAdd(currentIndex, numRequests);
  }
  
  public synchronized void update() {
    advanceIndex();
    this.countArray.getAndIncrement(currentIndex);
  }

  public float get() {
    long totalCount = 0L;
    long windowLength = 0L;
    long sampleSize = this.period/1000; // the number of seconds a sample is over
    
    synchronized (this){
      advanceIndex();
      for (int i = 0; i < this.countArray.length(); i++) {
        long oneCount = this.countArray.get(i);
        if (oneCount > 0) {
          totalCount += oneCount;
          windowLength += sampleSize;
        }
      }
    }
    
    if (windowLength > 0L) {
      return ((float) (totalCount) / windowLength);
    }
    
    return (float)0.0;
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
  
  private void advanceIndex() {
    long now = System.currentTimeMillis();
    
    if (now > this.nextResetTime.get()) {
      int intervalsPast = (int)((now - this.nextResetTime.get())/this.period) + 1;
      long nextAdvanceTime = now + this.period - (now % this.period);
      this.nextResetTime.set(nextAdvanceTime);
      
      if (intervalsPast > NUM_SAMPLING_WINDOW) {
        for (int i = 0; i < NUM_SAMPLING_WINDOW; i++) { 
          this.countArray.set(i, 0L);
        }
      } else {
        int i = (currentIndex + 1) % NUM_SAMPLING_WINDOW;
        for (int cnt = 0; cnt < intervalsPast; cnt++) { 
          this.countArray.set(i, 0L);
          i = (i + 1) % NUM_SAMPLING_WINDOW;
        }
      }
      int nextIndex = (int) ((nextAdvanceTime % (this.period * NUM_SAMPLING_WINDOW)) / this.period);
      currentIndex = nextIndex;
      this.countArray.getAndSet(nextIndex, 0);
    }
  }
  
  @Override
  public synchronized void reset() {
    for (int i = 0; i < countArray.length(); i++) {
      this.countArray.set(i, -1L);
    }
    long now = System.currentTimeMillis();
    this.nextResetTime.set(now + this.period - (now % this.period));
    currentIndex = (int) ((this.nextResetTime.get() % (this.period * NUM_SAMPLING_WINDOW)) / this.period);
    this.countArray.getAndSet(currentIndex, 0);
  }
}
