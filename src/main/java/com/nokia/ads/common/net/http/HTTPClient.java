package com.nokia.ads.common.net.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

/**
 * A utility class to construct HTTP GET queries.
 * Tries to simulate http_build_query() functionality of PHP.
 * <p>
 * To make a GET request to a service:
 * <pre><code>
 * 		// Calls http://server.com/endpoint.php?Parameter=Value%20Here&Foo=Bar
 * 		HTTPClient http = new HTTPClient();
 * 		http.setValue( "Parameter", "Value Here" );
 * 		http.setValue( "Foo", "Bar" );
 * 		String response = http.callURL( "http://server.com/endpoint.php" );
 * </code></pre>
 */
public class HTTPClient implements HTTPClientIface
{
  /**
   * The default number of milliseconds allowed to make a HTTP connection before timing out.
   */
  public static final int DEFAULT_CONNECTION_TIMEOUT = 130000;

  /**
   * The default number of milliseconds allowed to wait for data to read on a connection before
   * timing out.
   */
  public static final int DEFAULT_READ_TIMEOUT = 150000;

  /**
   * The URL encoding code page.
   */
  private static final String ENCODING = "UTF-8";

  // URL field characters.
  private static final char QUESTION_MARK = '?';
  private static final char AND = '&';
  private static final char EQUALS = '=';

  /**
   * The log instance.
   */
  private static final Logger logger = Logger.getLogger( HTTPClient.class );

  /**
   * The query string parameters.  These are stored in the order they are
   * added through the <code>setHeader</code> method.
   */
  private Map<String, String> mappings = new LinkedHashMap<String, String>();

  /**
   * How many milliseconds to allow connections to be established in.
   */
  private int connectionTimeout = DEFAULT_CONNECTION_TIMEOUT;

  /**
   * How many milliseconds to wait for reading data before giving up.
   */
  private int readTimeout = DEFAULT_READ_TIMEOUT;

  /** The pattern that matches control characters.
   */
  private static final Pattern controlPattern = Pattern.compile( "\\p{Cntrl}" );

  /**
   * Constructs a HTTP client object.
   */
  public HTTPClient()
  {
  }

  /**
   * Constructs a HTTP client object with given timeouts.
   *
   * @param connectionTimeout is the number of milliseconds to try to connect to the server.
   * @param readTimeout is the number of milliseconds to allow no data to come in.
   */
  public HTTPClient( int connectionTimeout, int readTimeout )
  {
    this.connectionTimeout = connectionTimeout;
    this.readTimeout = readTimeout;
  }

  /**
   * Sets a HTTP header.  If the header already exists it will be replaced.
   *
   * @param key is the HTTP header field such as "User-Agent".
   * @param value is the value for the HTTP header.
   * @see HTTPClientIface#setValue(String, String)
   */
  public void setValue( String key, String value )
  {
    if ( key == null || value == null )
    {
      return;
    }

    this.mappings.put(key, value);
  }

  /**
   * @see HTTPClient#setValue(String, String)
   */
  public void setValue( String key, int value ) {
    this.setValue(key, String.valueOf(value));
  }

  /**
   * @see HTTPClient#setValue(String, String)
   */
  public void setValue( String key, boolean value ) {
    this.setValue(key, String.valueOf(value));
  }

  /**
   * @see HTTPClient#setValue(String, String)
   */
  public void setValue( String key, double value ) {
    this.setValue(key, String.valueOf(value));
  }

  /**
   * @see HTTPClient#setValue(String, String)
   */
  public void setValue( String key, char value ) {
    this.setValue(key, String.valueOf(value));
  }

  /**
   * @see HTTPClient#setValue(String, String)
   */
  public void setValue( String key, float value ) {
    this.setValue(key, String.valueOf(value));
  }

  /**
   * @see HTTPClient#setValue(String, String)
   */
  public void setValue( String key, long value ) {
    this.setValue(key, String.valueOf(value));
  }

  /**
   * Returns the query string for a HTTP GET request using all the parameters
   * previously set using <code>setValue</code>.
   *
   * @return The HTTP query string for a GET request.
   */
  private String buildHttpQuery()
  {
    Iterator<Entry<String, String>> mapItr = mappings.entrySet().iterator();
    StringBuilder sb = new StringBuilder();

    boolean first = true;

    while (mapItr.hasNext())
    {
      Entry<String, String> e = mapItr.next();
      String name = e.getKey();
      String value = e.getValue();

      String encodedName = null;
      String encodedValue = null;

      try
      {
        encodedName = stripControlChars( name );
        encodedName = URLEncoder.encode( encodedName, ENCODING );

        encodedValue = stripControlChars( value );
        encodedValue = URLEncoder.encode( encodedValue, ENCODING) ;
      }
      catch (UnsupportedEncodingException uce)
      {
        // Java requires UTF-8 so this will never happen.
        logger.error( "UTF-8 encoding/decoding is not supported", uce );
      }

      if (first)
      {
        sb.append(QUESTION_MARK);
        first = false;
      }
      else
      {
        sb.append(AND);
      }
      sb.append(encodedName);
      sb.append(EQUALS);
      sb.append(encodedValue);
    }

    return sb.toString();
  }

  /**
   * Creates a connection to the remote server at <code>url</code>.
   *
   * @param url is the HTTP address to connect to.
   * @return A connection to the address.  This is never <code>null</code>.
   * @throws IOException if the exception could not be established for any reason.
   * @throws SocketTimeoutException if the connection timed out.  This is a subclass of
   *  <code>IOException</code>.
   */
  protected HttpURLConnection createConnection( URL url )
    throws IOException
  {
    return (HttpURLConnection) url.openConnection();
  }

  /**
         * @see HTTPClientIface#call(String, String)
   */
  public String call( String endpoint, String backupEndpoint )
    throws IOException
  {
    if ( endpoint == null || endpoint.length() == 0)
    {
      throw new IllegalArgumentException( "HTTPClient cannot contact a null/empty endpoint.");
    }

    // Try the primary URL.
    try
    {
      return callURL( endpoint );
    }
    catch (IOException e)
    {
      logger.debug( "Could not contact the primary HTTP endpoint:  " + endpoint, e );

      if ( backupEndpoint == null )
      {
        throw e;
      }
      else
      {
        // Try the backup URL.
        try
        {
          return callURL( backupEndpoint );
        }
        catch (IOException e2)
        {
          logger.debug( "Could not contact the backup HTTP endpoint:  " + backupEndpoint, e2 );
          throw e2;
        }
      }
    }
  }

  /**
   * Makes a HTTP request to the URL specified by <code>baseURL</code> combined with all the
   * query strings set by <code>setValue</code>.
   *
   * @param baseURL is a URL such as "http://server.com/endpoint.php".
   * @return The HTTP response body as a string.
   * @throws IOException if any problem happens communicating with the server.
   * @throws SocketTimeoutException if the connection timed out.  This is a subclass of
   *  <code>IOException</code>.
   */
  protected String callURL( String baseURL )
    throws IOException
  {
    // Assemble all of the setValue() calls into the query string.
    String query = buildHttpQuery();

    String fullReq = baseURL + query;

    URL url = null;

    try
    {
      url = new URL(fullReq);

      logger.debug( "HTTPClient making request to " + url );
    }
    catch (MalformedURLException mue)
    {
      logger.warn( "Cannot contact malformed URL:  " + fullReq, mue );
      throw new IOException( "Malformed URL:  " + fullReq );
    }

    // Setup connection options.
    //  Note that in Java keep alives are automatic so this connection will try to
    //  to reuse an existing connection if it can meaning lower overall latency.
    HttpURLConnection connection = createConnection( url );
    connection.setRequestMethod( "GET" );
    connection.setUseCaches( false );
    connection.setAllowUserInteraction( false );
    connection.setConnectTimeout( connectionTimeout );
    connection.setReadTimeout( readTimeout );

    // Connect to the server.
    connection.connect();

    // Was it successful?
    int code = connection.getResponseCode();

    if ( code != HttpURLConnection.HTTP_OK )
    {
      throw new IOException( "Received non-200 response code " + code +
          " (" + connection.getResponseMessage() + ") contacting " + url );
    }

    // Read in the HTTP response.
    StringBuilder outputLine = new StringBuilder( 2 * 1024 );  // Assume most responses are less than 2K
    char[] buffer = new char[512];
    InputStream is = null;

    try
    {
      is = connection.getInputStream();
      InputStreamReader isr = new InputStreamReader( is );
      int read;

      while ( (read = isr.read(buffer)) >= 0 )
      {
        outputLine.append( buffer, 0, read );
      }
    }
    finally
    {
      if ( is != null )
      {
        is.close();
      }
    }

    String response = outputLine.toString();
    return response;
  }

  /**
   * Normalizes a string coming in over HTTP by removing illegal characters and
   * URL decoding the rest.
   *
   * @param input is a HTTP string.
   * @return The normalized version of <code>input</code>.
   */
  public static String normalize( String input )
  {
    input = stripControlChars( input );
    input = multiURLDecode( input );
    return input;
  }

  /**
   * Returns a string stripped of any control characters.  In Java control characters
   * are represented by the sequence "\\c" so control-A is "\\cA".
   *
   * @param input is a string that may contain control characters.
   * @return The string with all control characters removed.
   */
  protected static String stripControlChars( String input )
  {
    String result = null;

    if ( input != null )
    {
      // Replace them with nothing.
      Matcher m = controlPattern.matcher( input );
      result = m.replaceAll( "" );
    }

    return result;
  }

  /**
   * Attempts to URLDecode a String multiple times until URLDecode no longer reduces
   * its size (which means further decoding isn't possible).
   */
  private static String multiURLDecode(String s)
  {
    String prevStr = s;
    String curStr = prevStr;

    if ( s != null )
    {
      int decodes = 0;
      int prevlen = prevStr.length();
      int curlen = prevlen;

      do
      {
        try
        {
          curStr = URLDecoder.decode( prevStr, ENCODING );
          decodes++;
        }
        catch (Exception e)  // UnsupportedEncodingException or IllegalArgumentException
        {
          // Might be OK, might be garbage.  Our policy is garbage in,
          // garbage out.  So just return it whatever it is.
          logger.debug( "Could not HTTP decode '" + prevStr + "'. count: " + decodes, e );
          return prevStr;
        }
        prevlen = curlen;
        curlen = curStr.length();

        if (prevlen == curlen)
        {
          // If URLDecode didn't change the length of the input string
          // probably it wasn't URLEncoded to begin with, in that case
          // legitimate characters like '+' in the input string would
          // be converted to ' ' which may cause undesired behaviour, e.g.
          // the UA
          //"Mozilla/5.0 (iPhone; U; CPU like Mac OS X; en) AppleWebKit/420+
            // (KHTML, like Gecko) Version/3.0 Mobile/1A543a Safari/419.3"
          // would get decoded to
          //"Mozilla/5.0 (iPhone; U; CPU like Mac OS X; en) AppleWebKit/420
            // (KHTML, like Gecko) Version/3.0 Mobile/1A543a Safari/419.3"
          // which results in Profiling Service returning a different wurfl-ID
          // for the device

          curStr = prevStr;
        }
        else
        {
          prevStr = curStr;
        }

      } while (prevlen > curlen);
    }
    return curStr;
  }

  @Override
  public void resetValues() {
    mappings.clear();
  }
}
