package com.nokia.ads.common.stats;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.*;

/**
 * Keeps track of some percentile of times.  For example the median, or 50th percentile, request time
 * could be captured by one of these statistics.
 */
public class PercentileStatistic
  extends Statistic
{
  private long resetPeriodInMillis = 300000;
  
  private long timeOfLastReset = 0;
  
  /**
   * Estimater of the time for this percentile.  Calculating percentiles exactly requires maintaining
   * a long history of values, sorting them, and picking the value from the sorted list.  This is expensive
   * so we use a quantiler which does a good enough job estimating 
   */
  protected AtomicReference<Quantiler> quantiler;
  
  /**
   * The percentile being recorded.  It is between 0.00 and 1.00.
   */
  private final float percentile;
  
  public long getResetPeriodInMillis() {
    return resetPeriodInMillis;
  }

  public void setResetPeriodInMillis(long resetPeriodInMillis) {
    this.resetPeriodInMillis = resetPeriodInMillis;
  }

  @Deprecated
  public PercentileStatistic( String name, String graphiteName, String description, double percentile, 
      ConversionType cactiConversionType, ConversionType graphiteConversionType, 
      boolean returnZeroForNoUpdates)
  {
    this(name, graphiteName, description, percentile, cactiConversionType, graphiteConversionType,
        returnZeroForNoUpdates, RRDType.CACTI_AND_GRAPHITE);
  }

  @Deprecated
  public PercentileStatistic( String name, String graphiteName, String description, double percentile ) {
    this(name, graphiteName, description, percentile, RRDType.CACTI_AND_GRAPHITE);
  }
  
  @Deprecated
  public PercentileStatistic( String name, String description, double percentile ) {
    this(name, description, percentile, RRDType.CACTI);
  }

  /**
   * Creates a percentile statistic set to 0 and places it into the <code>StatisticsManager</code>.
   * 
   * @param name is the label for this statistic which is used by Cacti for graphing.
   *  It must therefore conform to Cacti label naming restrictions meaning it is
   *  1 to 15 capital letters, numbers, or underscores (e.g. LATENCY_99_CPC).
   * @param description is a string that other people will understand when debugging this service.
   * @param percentile is the percentile of times to capture such as 0.95 for the 95th percentile.
   */
  public PercentileStatistic( String name, String graphiteName, String description, double percentile, 
      ConversionType cactiConversionType, ConversionType graphiteConversionType, 
      boolean returnZeroForNoUpdates, RRDType rrdType)
  {
    super( name, graphiteName, description, cactiConversionType, graphiteConversionType,returnZeroForNoUpdates, rrdType );
    
    // Make sure a valid percentile was passed in.
    if ( (percentile < 0.0) || (percentile > 1.0) )
    {
      throw new IllegalArgumentException( "Percentile (" + percentile + ") must be between 0.00 and 1.00 inclusive." );
    }
    this.quantiler = new AtomicReference<Quantiler>();
    this.percentile = (float) percentile;
    reset();
  }
  
  public PercentileStatistic( String name, String graphiteName, String description, double percentile, RRDType rrdType ) {
    this(name, graphiteName, description, percentile,
        ConversionType.NANOSECONDS_TO_SECONDS,
        ConversionType.NANOSECONDS_TO_MILLISECONDS, 
       false, rrdType);
  }
  
  public PercentileStatistic( String name, String description, double percentile, RRDType rrdType )
  {
    this( name, name, description, percentile, rrdType );
  }

  public PercentileStatistic( String name, String description, double percentile, ConversionType conversionType, RRDType rrdType )
  {
    this(name, name, description, percentile, conversionType, conversionType, false, rrdType);
  }

  public PercentileStatistic( String name, String description, double percentile, ConversionType conversionType, 
      boolean returnZeroForNoUpdates, RRDType rrdType )
  {
    this(name, name, description, percentile, conversionType, conversionType, returnZeroForNoUpdates, rrdType);
  }
  
  /**
   * @return The percentile being recorded such as 0.50 for the 50th percentile.
   */
  public double percentile()
  {
    return percentile;
  }
  
  /**
   * @return The percentile measurement, in nanoseconds, right now.  For example the 50th percentile would
   *  return the median of all values passed to <code>update</code>.
   */
  public long get()
  {
    long result = (long) quantiler.get().estimate();
    
    // Sampling the data should also reset this quantiler.  The Quantiler class quickly gets
    // stuck at a certain value which is an approximation of the percentile it has measured.
    // By resetting it we'll get it unstuck.
    
    reset();
    
    return result;
  }
  
  /**
   * @return The percentile measurement, in seconds, right now.  For example the 50th percentile would
   *  return the median of all values passed to <code>update</code>.
   */
  public double getSeconds()
  {
    return get() / 1000000000.0;
  }
  
  /**
   * @param requestTimeInNanoseconds is the time it took the last request in nanoseconds.
   */
  public void update( long requestTimeInNanoseconds )
  {
    quantiler.get().update( requestTimeInNanoseconds );
  }
  
  /**
   * Method override.
   *
   */
  @Override
  public synchronized void reset()
  {
    long now = System.currentTimeMillis();
    long msSinceLastReset = (now - timeOfLastReset);
    
    if (msSinceLastReset > this.resetPeriodInMillis) {
      quantiler.set(new Quantiler( percentile ));
      timeOfLastReset = now;
    }
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

/**
 * A thread safe quantile estimator that enables incremental updates. In other words,
 * the estimator processes samples sequentially in one pass, and does not
 * require all samples to be sorted, or even partially sorted.
 * <p/>
 * The quantile estimate is probably not useful for fewer than 10 samples.
 * <p/>
 * The estimate is most accurate for cumulative distribution functions
 * that are smooth in the neighborhood of the desired quantile q. For
 * such distributions, the accuracy of the estimate improves with
 * successive updates.
 * <p/>
 * This class is an implementation of the algorithm published by Jain,
 * R. and Chlamtac, I., 1985, The P2 algorithm for dynamic calculation of
 * quantiles and histograms without storing observations:  Comm. ACM,
 * v. 28, n. 10.
 */
class Quantiler {

    private float quantile; // the desired quantile
    private double _m0, _m1, _m2, _m3, _m4; // marker positions
    private double _q0, _q1, _q2, _q3, _q4; // marker heights
    private double _f0, _f1, _f2, _f3, _f4; // desired marker positions
    private double _d0, _d1, _d2, _d3, _d4; // desired marker position increments
    private boolean initialized; // true if estimator has been initialized
    private ReentrantLock myLock; // my lock

    /**
     * Constructs a quantiler for the specified quantile fraction.
     *
     * @param q the quantile fraction; 0 &lt;= q &lt;= 1 is required.
     */
    public Quantiler(float q) {
        if (q < 0.0f) {
            throw new IllegalArgumentException("Quantile fraction is less than 0.00f");
        } else if (q > 1.0f) {
            throw new IllegalArgumentException("Quantile fraction is more than 1.00f");
        }
        myLock = new ReentrantLock();
        quantile = q;
        _m0 = -1.0;
        _q2 = 0.0f;
        initialized = (quantile == 0.0 || quantile == 1.0);
    }

    /**
     * Returns the current quantile estimate.
     *
     * @return the current quantile estimate.
     */
    public float estimate() {
        return (float) _q2;
    }

    /**
     * Updates the quantile estimate with the specified sample.
     *
     * @param f the sample used to update the estimate.
     * @return the updated quantile estimate.
     */
    public float update(float f) {
        myLock.lock();
        try {
            if (!initialized) {
                initOne(f);
            } else {
                updateOne(f);
            }
            return estimate();
        } finally {
            myLock.unlock();
        }
    }

    private void initOne(float f) {
        // If fewer than 5 (non-null) samples, may not complete initialization.
        if (_m0 < 0.0) {
            _m0 = 0.0;
            _q0 = f;
        } else if (_m1 == 0.0) {
            _m1 = 1.0;
            _q1 = f;
        } else if (_m2 == 0.0) {
            _m2 = 2.0;
            _q2 = f;
        } else if (_m3 == 0.0) {
            _m3 = 3.0;
            _q3 = f;
        } else if (_m4 == 0.0) {
            _m4 = 4.0;
            _q4 = f;
        }
        if (_m4 == 0.0)
            return;

        // Initialize marker heights to five samples sorted.
        double[] y = {_q0, _q1, _q2, _q3, _q4};
        for (int i = 1; i < 5; ++i) {
            for (int j = i; j > 0 && y[j - 1] > y[j]; --j) {
                double ytemp = y[j - 1];
                y[j - 1] = y[j];
                y[j] = ytemp;
            }
        }
        _q0 = y[0];
        _q1 = y[1];
        _q2 = y[2];
        _q3 = y[3];
        _q4 = y[4];

        // Initialize desired marker positions.
        _f0 = 0.0;
        _f1 = 2.0 * quantile;
        _f2 = 4.0 * quantile;
        _f3 = 2.0 + 2.0 * quantile;
        _f4 = 4.0;

        // Compute increments in desired marker positions.
        _d0 = 0.0;
        _d1 = quantile / 2.0;
        _d2 = quantile;
        _d3 = (1.0 + quantile) / 2.0;
        _d4 = 1.0;

        // The estimator is now initialized and the current estimate is q2.
        initialized = true;
    }

    private void updateOne(float f) {
        if (!initialized) {
            throw new IllegalStateException("Quantile is not initialized");
        }

        // If min or max, handle as special case; otherwise, ...
        if (quantile == 0.0f) {
            if (f < _q2)
                _q2 = f;
        } else if (quantile == 1.0f) {
            if (f > _q2)
                _q2 = f;
        } else {

            // Increment marker locations and update min and max.
            if (f < _q0) {
                _m1 += 1.0;
                _m2 += 1.0;
                _m3 += 1.0;
                _m4 += 1.0;
                _q0 = f;
            } else if (f < _q1) {
                _m1 += 1.0;
                _m2 += 1.0;
                _m3 += 1.0;
                _m4 += 1.0;
            } else if (f < _q2) {
                _m2 += 1.0;
                _m3 += 1.0;
                _m4 += 1.0;
            } else if (f < _q3) {
                _m3 += 1.0;
                _m4 += 1.0;
            } else if (f < _q4) {
                _m4 += 1.0;
            } else {
                _m4 += 1.0;
                _q4 = f;
            }

            // Increment desired marker positions.
            _f0 += _d0;
            _f1 += _d1;
            _f2 += _d2;
            _f3 += _d3;
            _f4 += _d4;

            // If necessary, adjust height and location of markers 1, 2, and 3.
            double mm, mp;
            mm = _m1 - 1.0;
            mp = _m1 + 1.0;
            if (_f1 >= mp && _m2 > mp) {
                _q1 = qp(mp, _m0, _m1, _m2, _q0, _q1, _q2);
                _m1 = mp;
            } else if (_f1 <= mm && _m0 < mm) {
                _q1 = qm(mm, _m0, _m1, _m2, _q0, _q1, _q2);
                _m1 = mm;
            }
            mm = _m2 - 1.0;
            mp = _m2 + 1.0;
            if (_f2 >= mp && _m3 > mp) {
                _q2 = qp(mp, _m1, _m2, _m3, _q1, _q2, _q3);
                _m2 = mp;
            } else if (_f2 <= mm && _m1 < mm) {
                _q2 = qm(mm, _m1, _m2, _m3, _q1, _q2, _q3);
                _m2 = mm;
            }
            mm = _m3 - 1.0;
            mp = _m3 + 1.0;
            if (_f3 >= mp && _m4 > mp) {
                _q3 = qp(mp, _m2, _m3, _m4, _q2, _q3, _q4);
                _m3 = mp;
            } else if (_f3 <= mm && _m2 < mm) {
                _q3 = qm(mm, _m2, _m3, _m4, _q2, _q3, _q4);
                _m3 = mm;
            }
        }
    }

    private static double qp(double mp, double m0, double m1, double m2, double q0, double q1, double q2) {
        double qt = q1 + ((mp - m0) * (q2 - q1) / (m2 - m1) + (m2 - mp) * (q1 - q0) / (m1 - m0)) / (m2 - m0);
        return (qt <= q2) ? qt : q1 + (q2 - q1) / (m2 - m1);
    }

    private static double qm(double mm, double m0, double m1, double m2, double q0, double q1, double q2) {
        double qt = q1 - ((mm - m0) * (q2 - q1) / (m2 - m1) + (m2 - mm) * (q1 - q0) / (m1 - m0)) / (m2 - m0);
        return (q0 <= qt) ? qt : q1 + (q0 - q1) / (m0 - m1);
    }
}
