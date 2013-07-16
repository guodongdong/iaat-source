package com.nokia.ads.common.util;

/**
 * A simple class that contains name-value string pairs.  It includes basic methods for
 * <code>equals</code>, <code>compareTo</code>, and the like.
 */
public class NameValuePair
	implements Comparable<NameValuePair>
{
	/**
	 * The name.
	 */
	private String name;
	
	/**
	 * The value.
	 */
	private String value;
	
	/**
	 * Constructs a pair where the name and value are both <code>null</code>.
	 */
	public NameValuePair()
	{
	}

	/**
	 * Constructs a populated name-value pair.
	 * 
	 * @param name is the pair's name.
	 * @param value is the pair's value.
	 */
	public NameValuePair( String name, String value )
	{
		super();
		this.name = name;
		this.value = value;
	}
	
	/**
	 * @return The name.
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name overrides the current name.
	 */
	public void setName( String name )
	{
		this.name = name;
	}

	/**
	 * @return The value.
	 */
	public String getValue()
	{
		return value;
	}

	/**
	 * @param value overrides the current value.
	 */
	public void setValue( String value )
	{
		this.value = value;
	}

	/**
	 * @return A duplicate <code>NameValuePair</code> object that is not same as this one.
	 *
	 * @see java.lang.Object#clone()
	 */
	@Override
	protected Object clone()
	{
		NameValuePair clone = new NameValuePair( name, value );
		return clone;
	}

	/**
	 * Does a deep comparison of two <code>NameValuePair</code> objects.
	 * 
	 * @param obj is the other <code>NameValuePair</code> objects.
	 * @return <code>true</code> if the name and value are equal for the two objects;
	 *  <code>false</code>otherwise.
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals( Object obj )
	{
		if ( this == obj )
		{
			return true;
		}
		
		if ( (obj == null) || !(obj instanceof NameValuePair) )
		{
			return false;
		}

		NameValuePair other = (NameValuePair) obj;

		if ( name == null )
		{
			if ( other.name != null )
			{
				return false;
			}
			else  // other.name == null
			{
				return true;
			}
		}
		
		if ( value == null )
		{
			if ( other.value != null )
			{
				return false;
			}
			else  // other.value == null
			{
				return true;
			}
		}

		boolean sameNames = name.equals( other.name );
		boolean sameValues = value.equals( other.value );
		
		return sameNames && sameValues;
	}

	/**
	 * Returns a hash of this <code>NameValuePair</code>.
	 *
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		return toString().hashCode();
	}

	/**
	 * Default string for a <code>NameValuePair</code> that will appear in
	 * the debugger window.
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return name + ":" + value;
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
	public int compareTo( NameValuePair o )
	{
		int c = name.compareTo( o.name );
		
		if ( c == 0 )
		{
			c = value.compareTo( o.value );
		}
		
		return c;
	}
}
