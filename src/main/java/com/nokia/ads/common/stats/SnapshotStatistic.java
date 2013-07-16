package com.nokia.ads.common.stats;


/**
 * Snapshot statistics record the current value of something.  For example the hit rate of a cache
 * could be set to the current value on every cache access.  What makes a statistic a snapshot vs.
 * some other type is that this object's value is completely replaced each time <code>set</code> is
 * called whereas other statistics are based on the history of their values.
 */
public class SnapshotStatistic
  extends IncrementingStatistic
{
  public interface SnapshotHandler {
    public long get();
  }
  
  protected final SnapshotHandler handler;

  @Deprecated
  public SnapshotStatistic( String name, String graphiteName, String description ) {
    this(name, graphiteName, description, RRDType.CACTI_AND_GRAPHITE);
  }

  @Deprecated
  public SnapshotStatistic( String name, String description ) {
    this(name, description, RRDType.CACTI);
  }
  
  @Deprecated
  public SnapshotStatistic( String name, String graphiteName, String description, SnapshotHandler handler, 
      ConversionType cactiConversionType, ConversionType graphiteConversionType,boolean returnZeroForNoUpdates) {
    this(name, graphiteName, description, handler, cactiConversionType, graphiteConversionType,
        returnZeroForNoUpdates, RRDType.CACTI_AND_GRAPHITE);
  }

  @Deprecated
  public SnapshotStatistic( String name, String graphiteName, String description, SnapshotHandler handler ) {
    this(name, graphiteName, description, handler, RRDType.CACTI_AND_GRAPHITE);
  }

  @Deprecated
  public SnapshotStatistic( String name, String description, SnapshotHandler handler ) {
    this( name, description, handler, RRDType.CACTI );
  }

  /**
   * Creates an snapshot statistic set to 0 and places it into the <code>StatisticsManager</code>.
   * 
   * @param name is the label for this statistic which is used by Cacti for graphing.
   *  It must therefore conform to Cacti label naming restrictions meaning it is
   *  1 to 15 capital letters, numbers, or underscores (e.g. LATENCY_99_CPC).
   * @param description is a string that other people will understand when debugging this service.
   */
  public SnapshotStatistic( String name, String graphiteName, String description, RRDType rrdType )
  {
    this( name, graphiteName, description, null, rrdType );
  }

  public SnapshotStatistic( String name, String description, RRDType rrdType )
  {
    this( name, name, description, rrdType );
  }

  public SnapshotStatistic( String name, String description, ConversionType conversionType, RRDType rrdType )
  {
    this( name, name, description, null, conversionType, conversionType, true, rrdType);
  }
  
  /**
   * Creates a snapshot statistic default to 0 and places it into the <code>StatisticsManager</code>.
   * If the <code>SnapshotHandler</code> is provided, <method>get</method> will invoke <code>SnapshotHandler.get()</code>
   * to set the stats and then return the value.
   * 
   * @param name is the label for this statistic which is used by Cacti for graphing.
   *  It must therefore conform to Cacti label naming restrictions meaning it is
   *  1 to 15 capital letters, numbers, or underscores (e.g. LATENCY_99_CPC).
   * @param description is a string that other people will understand when debugging this service.
   * @param handler is an instance of <code>SnapshotHandler</code> which takes a snapshot and returns the value.
   */
  public SnapshotStatistic( String name, String graphiteName, String description, SnapshotHandler handler, 
       ConversionType cactiConversionType, ConversionType graphiteConversionType,boolean returnZeroForNoUpdates,
       RRDType rrdType) {
    super(name, graphiteName, description,  cactiConversionType,graphiteConversionType, returnZeroForNoUpdates,
        rrdType);
    this.handler = handler;
  }
  
  public SnapshotStatistic( String name, String graphiteName, String description, SnapshotHandler handler,
      RRDType rrdType)
  {
    this( name, graphiteName, description, handler, ConversionType.NONE, ConversionType.NONE, true, rrdType);
  }

  public SnapshotStatistic( String name, String description, SnapshotHandler handler, RRDType rrdType )
  {
    this( name, name, description, handler, rrdType );
  }

  /**
   * Sets the statistic's value.
   * 
   * @param newValue is the new value of the statistic.
   * @return The previous value of the statistic.
   */
  public long set( long newValue )
  {
    long previous = count.get();
    count.set( newValue );
    return previous;
  }

  @Override
  public long get() {
    if (this.handler != null) {
      set(handler.get());
    }
    return super.get();
  }
  
}
