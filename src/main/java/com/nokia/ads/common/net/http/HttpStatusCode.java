package com.nokia.ads.common.net.http;

/**
 * This a list of known HTTP Status Codes
 */
public final class HttpStatusCode {

	public static final int HTTP_OK = 200; // OK
	public static final int HTTP_REDIRECT = 301;
	public static final int HTTP_BAD_REQUEST = 400; // invalid request, maybe bad signature
	public static final int HTTP_UNAUTHORIZED = 401; // unauthorized request, maybe incorrect adSpaceId or udid 
	public static final int HTTP_FORBIDDEN = 403; // request too fast or potential fraud requests
	public static final int HTTP_NOT_FOUND = 404; // no available ads, try fetch the ad later
	public static final int HTTP_NOT_ACCEPTABLE = 406; // missing required params
	public static final int HTTP_PROXY_UNAUTH = 407; // invalid server name
	public static final int HTTP_INTERNAL_ERROR = 500; // server error
	public static final int HTTP_NOT_IMPLEMENTED = 501; // no service support 
	public static final int HTTP_SERVER_BUSY = 503; // server busy
	public static final int HTTP_CLIENT_TIME_ERROR=505;// client time is invalid
	public static final int HTTP_ADTYPE_NOTMATCH=601;// request ad type not matchs inventory ad type
	
	/**
	 * Decode a status to an HTTP Status code
	 * 
	 * @param status
	 *            The status to decode
	 * @return the HTTP Status
	 */
	public static String decode(int status) {
		switch (status) {
		case HTTP_OK:
			return "OK";
		case HTTP_REDIRECT:
			return "Moved Permanently";
		case HTTP_BAD_REQUEST:
			return "Bad Request";
		case HTTP_UNAUTHORIZED:
			return "Unauthorized";
		case HTTP_FORBIDDEN:
			return "Forbidden";
		case HTTP_NOT_FOUND:
			return "Not Found";
		case HTTP_NOT_ACCEPTABLE:
			return "Not Acceptable";
		case HTTP_PROXY_UNAUTH:
			return "Proxy Authorized";
		case HTTP_INTERNAL_ERROR:
			return "Internal Server Error";
		case HTTP_NOT_IMPLEMENTED:
			return "Not Implemented";
		case HTTP_SERVER_BUSY:
			return "Server Busy";
		default:
			return "Unknown Status";
		}
	}
}
