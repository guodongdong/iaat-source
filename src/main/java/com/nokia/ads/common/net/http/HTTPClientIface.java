package com.nokia.ads.common.net.http;

import java.io.IOException;

public interface HTTPClientIface {

  /**
   * Sets a HTTP header.  If the header already exists it will be replaced.
   *
   * @param key is the HTTP header field such as "User-Agent".
   * @param value is the value for the HTTP header.
   */
  public void setValue( String key, String value );

  /**
   * @see HTTPClientIface#setValue(String, String)
   */
  public void setValue( String key, int value );

  /**
   * @see HTTPClientIface#setValue(String, String)
   */
  public void setValue( String key, long value );

  /**
   * @see HTTPClientIface#setValue(String, String)
   */
  public void setValue( String key, double value );

  /**
   * @see HTTPClientIface#setValue(String, String)
   */
  public void setValue( String key, float value );

  /**
   * @see HTTPClientIface#setValue(String, String)
   */
  public void setValue( String key, char value );

  /**
   * Reset all query values
   */
  public void resetValues();

  /**
   * Makes a HTTP request to the HTTP service at <code>endpoint</code> combined with all the
   * query strings set by <code>setValue</code>.  If the call fails and <code>backupEndpoint</code>
   * is not <code>null</code> then it will be tried too.
   *
   * @param endpoint is a URL such as "http://server.com/endpoint.php".
   * @param backupEndpoint is a backup URL used only if <code>endpoint</code> fails.  If there
   *  is no backup set this to <code>null</code>.
   * @return The HTTP response body as a string.
   * @throws IOException if any problem happens communicating with the server.
   * @throws SocketTimeoutException if the connection timed out.  This is a subclass of
   *  <code>IOException</code>.
   */
  public String call( String endpoint, String backupEndpoint ) throws IOException;
}
