package com.nokia.ads.common.stats;

/** 
 * Used to determine how the values passed into the Statistics class will be normalized when given to cacti
 * For example, if you are passing nanoseconds into the Statistics package, then you probably want to use
 * NANOSECONDS_TO_MILLISECONDS instead of the default NANOSECONDS_TO_SECONDS.
 */
public enum ConversionType {
  NANOSECONDS_TO_SECONDS, // divide by 10^9
  NANOSECONDS_TO_MILLISECONDS, // divide by 10^6
  NONE // don't normalize
}
