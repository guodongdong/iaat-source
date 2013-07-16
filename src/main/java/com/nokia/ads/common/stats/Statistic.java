package com.nokia.ads.common.stats;

import java.math.*;

import org.apache.commons.lang.StringUtils;

/**
 * Base class for all statistics.  Statistics objects are automatically placed within the 
 * <code>StatisticsManager</code>.  It can be queried for things like output consumable by Cacti
 * for graphing.
 * <p>
 * Subclasses maintain specific types of statistics.  For exammple the <code>IncrementingStatistic</code>
 * holds data that always increases in value such as number of requests.
 * 
 * @see StatisticsManager
 */
public abstract class Statistic
  implements Comparable<Statistic>
{
  public static enum RRDType {
    CACTI,
    GRAPHITE,
    CACTI_AND_GRAPHITE;
  }
  
  boolean graphiteOnly = false;
  
  private RRDType rrdType = RRDType.CACTI_AND_GRAPHITE;
  
  private ConversionType cactiConversionType = ConversionType.NONE; 
  private ConversionType graphiteConversionType = ConversionType.NONE; 

  /**
   * The unique name of this static.  It must conform to Cacti naming restrictions (i.e. 15 chars or less
   * of capital letters, digits, and underscores).
   */
  private String cactiName;
  
  /**
   * The name this statisitic will appear under within Graphite.  For example "myapi.requests-per-second"
   * will be one of the "myapi" statistics called "requests-per-second".
   */
  private String graphiteName;
  
  /**
   * The human readible name assigned this statistic.  It is always available and never <code>null</code>.
   */
  private String description;

  private boolean returnZeroForNoUpdates = false;


  @Deprecated
  public Statistic( String cactiName, String description ) {
    this(cactiName, cactiName, description, ConversionType.NONE, ConversionType.NONE, true, RRDType.CACTI);
  }
  
  @Deprecated
  public Statistic( String cactiName, String graphiteName, String description, 
      ConversionType cactiConversionType, ConversionType graphiteConversionType, 
      boolean returnZeroForNoUpdates ) {
    this(cactiName, graphiteName, description, cactiConversionType, graphiteConversionType, 
        returnZeroForNoUpdates, RRDType.CACTI_AND_GRAPHITE);
  }
  
  @Deprecated
  public Statistic( String cactiName, String graphiteName, String description )  {
    this(cactiName, graphiteName, description, ConversionType.NONE, ConversionType.NONE, true, RRDType.CACTI_AND_GRAPHITE);
  }
  
  /**
   * Creates a statistic object and places it into the <code>StatisticsManager</code>.
   * 
   * @param name is the label for this statistic which is used by Cacti for graphing.
   *  It must therefore conform to Cacti label naming restrictions meaning it is
   *  1 to 15 capital letters, numbers, or underscores (e.g. LATENCY_99_CPC).
   * @param description is a string that other people will understand when debugging this service.
   */
  public Statistic(String name, String description, RRDType rrdType)
  {
    this(name, name, description, ConversionType.NONE, ConversionType.NONE, true, rrdType);
  }

  public Statistic( String cactiName, String graphiteName, String description, RRDType rrdType)  {
    this(cactiName, graphiteName, description, ConversionType.NONE, ConversionType.NONE, true, RRDType.CACTI_AND_GRAPHITE);
  }
  
  /**
   * Creates a statistic object and places it into the <code>StatisticsManager</code>.
   * 
   * @param cactiName is the label for this statistic which is used by Cacti for graphing.
   *  It must therefore conform to Cacti label naming restrictions meaning it is
   *  1 to 15 capital letters, numbers, or underscores (e.g. LATENCY_99_CPC).
   * @param graphiteName is the name this statisitic will appear under within Graphite.  
   *  For example "myapi.requests-per-second" will be one of the "myapi" statistics called
   *  "requests-per-second".
   * @param description is a string that other people will understand when debugging this service.
   */
  public Statistic( String cactiName, String graphiteName, String description, 
      ConversionType cactiConversionType, ConversionType graphiteConversionType, 
      boolean returnZeroForNoUpdates, RRDType rrdType)
  {
    this.rrdType = rrdType;
    
    // Verify the parameters.
    if (rrdType == RRDType.CACTI || rrdType == RRDType.CACTI_AND_GRAPHITE) {
      if ( (cactiName != null) && isValidCactiName(cactiName) == false )
      {
        throw new IllegalArgumentException( "Name (" + cactiName + ") must be no more than 15 capital letters, digits, or underscores." );
      }
    }

    if (rrdType == RRDType.GRAPHITE || rrdType == RRDType.CACTI_AND_GRAPHITE) {
      if ( (graphiteName != null) && isValidGraphiteName(graphiteName) == false )
      {
        throw new IllegalArgumentException( "graphiteName (" + graphiteName + ") must be lowercase letters, digits, underscores, and periods." );
      }
    }
    
    if ( (description == null) || description.equals("") )
    {
      throw new IllegalArgumentException( "Must supply a \"description\" for the statistic that is human readible." );
    }
    
    // Record the parameters.
    this.description = description;
    this.returnZeroForNoUpdates = returnZeroForNoUpdates;

    switch(rrdType) {
      case CACTI:
        this.cactiName = cactiName;
        this.cactiConversionType = cactiConversionType;
        break;
      case GRAPHITE:
        this.graphiteName = graphiteName;
        this.graphiteConversionType = graphiteConversionType;
        break;
      case CACTI_AND_GRAPHITE:
      default:
        this.cactiName = cactiName;
        this.cactiConversionType = cactiConversionType;
        this.graphiteName = graphiteName;
        this.graphiteConversionType = graphiteConversionType;
        break;
    }

    // Place this statistic into the manager (and verify naming uniqueness).
    StatisticsManager.addStatistic( this );
  }
  
  /**
   * Checks if the given name is consumable by Cacti.  Cacti requires names to
   * be no more than 15 characters all composed of capital letters, numbers, or
   * underscores.
   * 
   * @return <code>true</code> if <code>name</code> is allowed by Cacti;
   *  <code>false</code> otherwise.
   */
  public static boolean isValidCactiName( String name )
  {
    boolean ok = true;
    
    if ( (name == null) || (name.length() <= 0) || (name.length() > 15) )
    {
      ok = false;
    }
    else
    {
      for ( int i = 0; i < name.length(); i++ )
      {
        String s = String.valueOf( name.charAt( i ) );
        if ( s.matches( "[^A-Z0-9_]" ) )
        {
          ok = false;
          break;
        }
      }
    }
    
    return ok;
  }

  public String name() {
    if (rrdType == RRDType.CACTI) {
      return cactiName();
    } else if (rrdType == RRDType.GRAPHITE) {
      return graphiteName();
    } else {
      return cactiName();
    }
  }
  
  /**
   * @return The name of this statistic.
   */
  public String cactiName()
  {
    return cactiName;
  }
  
  /**
   * Checks if the given name conforms to our Graphite naming convensions.  This
   * is all lower case letters, numbers, and underscores.
   * 
   * @return <code>true</code> if <code>name</code> is allowed by Graphite;
   *  <code>false</code> otherwise.
   */
  public static boolean isValidGraphiteName( String name )
  {
    boolean ok = true;

    if ( (name == null) || (name.length() <= 0) )
    {
      ok = false;
    }
    else
    {
      for ( int i = 0; i < name.length(); i++ )
      {
        String s = String.valueOf( name.charAt( i ) );
        if ( s.matches( "[^a-z0-9\\._]" ) )
        {
          ok = false;
          break;
        }
      }
    }
    
    return ok;
  }
  
  /**
   * @return The name this statisitic will appear under within Graphite.  For example
   *  "myapi.requests-per-second".
   */
  public String graphiteName()
  {
    return graphiteName;
  }
  
  /**
   * @return The human readible description of this statistic useful by another developer in debugging.
   */
  public String description()
  {
    return description;
  }
  
  /**
   * @return The value of this statistic.  If <code>null</code> or the empty string this
   *  will be assumed to have no data.
   */
  public abstract String value(ConversionType conversionType);
  
  public String value() {
    if (rrdType == RRDType.CACTI) {
      return value(this.getCactiConversionType());
    } else if (rrdType == RRDType.GRAPHITE) {
      return value(this.getGraphiteConversionType());
    } else {
      return value(this.getCactiConversionType());
    }
  }
  
  /**
   * Resets the statistic.
   */
  public abstract void reset();
  
  
  /**
   * @return This statistic consumable by Cacti such as "LATENCY_99:8.73" or <code>null</code>
   *  if it is not tracked by Cacti.
   */
  public String toCactiString()
  {
    String theCactiName = cactiName();
    String theCactiValue = value(this.getCactiConversionType());
      
    if ( (theCactiName != null) && (theCactiValue != null) 
        && (rrdType == RRDType.CACTI || rrdType == RRDType.CACTI_AND_GRAPHITE))
    {
      return theCactiName + ":" + theCactiValue;
    }
    else
    {
      return null;
    }
  }

  /**
   * @return This statistic consumable by Graphite or <code>null</code>
   *  if it is not tracked by Graphite.
   */
  public String toGraphiteString()
  {
    String theGraphiteName = graphiteName();
    String theGraphiteValue = value(this.getGraphiteConversionType());
      
    if ( (theGraphiteName != null) && (theGraphiteValue != null) 
        && (rrdType == RRDType.GRAPHITE || rrdType == RRDType.CACTI_AND_GRAPHITE))
    {
      return theGraphiteName + ":" + theGraphiteValue;
    }
    else
    {
      return null;
    }
  }

  
  /**
   * Returns a string representation of this statistic suitable for debugging.
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    String cacti = toCactiString();
    if ( cacti == null )
    {
      cacti = "No Info";
    }
    
    return "[" + cacti + "] " + description; 
  }
  
  /**
   * Turns a double into a string suitable for displaying in Cacti results.  For example
   * "92.539801923" would be returned as "92.53980", "0.004167" returns "0.00417".
   * 
   * @param value is some value to convert to a string.
   * @return The value as a string rounded to 5 decimal places.
   * @see #toCactiString()
   */
  protected static String double2string( double value )
  {
    BigDecimal bd = new BigDecimal( value );
    bd = bd.setScale( 5, BigDecimal.ROUND_HALF_UP );
    String result = bd.toString();
    return result;
  }
  
  /**
   * Checks to see if two <code>Statistic</code> objects are the same.
   *
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals( Object obj )
  {
    // Statistic objects have unique names.  And those objects can only be created once.
    // Therefore we only need to do an exact comparison of objects to see if they are equal.
    return ( this == obj );
  }

  /**
   * Compares two <code>NameValuePair</code> objects for sorting them.  First the name
   * is sorted alphabetically and if they are the same the value is sorted alphabetically.
   * 
   * @param o is the other object to compare to this one.
   * @return a negative integer, zero, or a positive integer as this object is less than,
   *  equal to, or greater than the specified object.
   * 
   * @see Comparable#compareTo(Object)
   */
  public int compareTo( Statistic o )
  {
    // Alphabetically sort statistics by name - graphite takes precedence
    if (!StringUtils.isEmpty(this.graphiteName) && 
        !StringUtils.isEmpty(o.graphiteName)) {
      return this.graphiteName.compareTo(o.graphiteName);  
    }
    
    return cactiName.compareTo( o.cactiName );
  }

  @Deprecated
  public ConversionType getConversionType() {
    return getCactiConversionType();
  }

  @Deprecated
  public void setConversionType(ConversionType conversionType) {
    setCactiConversionType(conversionType);
  }
  
  public ConversionType getCactiConversionType() {
    return cactiConversionType;
  }

  public void setCactiConversionType(ConversionType cactiConversionType) {
    this.cactiConversionType = cactiConversionType;
  }
  
  public ConversionType getGraphiteConversionType() {
    return graphiteConversionType;
  }

  public void setGraphiteConversionType(ConversionType graphiteConversionType) {
    this.graphiteConversionType = graphiteConversionType;
  }
  
  
  public boolean isReturnZeroForNoUpdates() {
    return returnZeroForNoUpdates;
  }

  public void setReturnZeroForNoUpdates(boolean returnZeroForNoUpdates) {
    this.returnZeroForNoUpdates = returnZeroForNoUpdates;
  }
  
  protected double convert(double v, ConversionType conversionType) {
    switch(conversionType) {
    case NANOSECONDS_TO_SECONDS:
      return v/1000000000.0;
    case NANOSECONDS_TO_MILLISECONDS:
      return v/1000000.0;
    case NONE:
      return v;
    }
    return v;
  }  
  
  @Deprecated
  public boolean isGraphiteOnly() {
    return (rrdType == RRDType.GRAPHITE);
  }

  @Deprecated
  public void setGraphiteOnly(boolean graphiteOnly) {
    if (graphiteOnly) {
      this.rrdType = RRDType.GRAPHITE;
    }
  }
}
