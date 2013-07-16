package com.nokia.ads.common.stats;


/**
 * The number of megabytes currently used in the JVM's heap.  This is automatically
 * added to any service.
 */
class JvmHeapSizeStatistic
  extends SnapshotStatistic
{
  public JvmHeapSizeStatistic()
  {
    super( "JVM_MEGS_USED", "common.jvm_megs_used", 
        "The number of JVM megabytes being used.  This will bounce up and down with the garbage collector.",
        RRDType.CACTI_AND_GRAPHITE );
  }
  
  /**
   * @return The current heap size in megabytes.
   */
  public long get()
  {
    Runtime r = Runtime.getRuntime();
    long available = r.freeMemory();
    long max = r.maxMemory();
    long used = max - available;
    long megs = Math.round( used / (1024.0 * 1024.0) );
    set( megs );
    
    return megs;
  }
}
