package com.nokia.ads.common.stats;

/**
 * The number of megabytes currently used in the JVM's heap.  This is automatically
 * added to any service.
 */
class JvmThreadsStatistic
  extends SnapshotStatistic
{
  public JvmThreadsStatistic()
  {
    super( "JVM_THREADS", "common.jvm_threads", 
        "The thread count within the JVM (usually a rough showing of how many clients are being simultaneously handled).",
        RRDType.CACTI_AND_GRAPHITE );
  }
  
  /**
   * @return The current heap size in megabytes.
   */
  public long get()
  {
    return (long) Thread.activeCount();
  }
}
