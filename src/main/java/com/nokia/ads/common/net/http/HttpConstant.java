package com.nokia.ads.common.net.http;

/**
 * HTTP Request Constants used by the HTTP encoder and decoder
 */
public class HttpConstant {

    public static final String HTTP_1_1 = "HTTP/1.1";

    public static final String HTTP_1_0 = "HTTP/1.0";

    public static final String CONTENT_LENGTH = "Content-Length";

    public static final byte[] CONTENT_LENGTH_LOWER = (CONTENT_LENGTH.toLowerCase() + ":").getBytes();

    public static final byte[] CONTENT_LENGTH_UPPER = (CONTENT_LENGTH.toUpperCase() + ":").getBytes();

    public static final byte[] EXPECT_LOWER = "expect:".getBytes();

    public static final byte[] EXPECT_UPPER = "EXPECT:".getBytes();

    public static final String EXPECT_100_CONTINUE = "100-continue";

    public static final String EXPECT_CONTINUE_RESPONSE = "HTTP/1.1 100 Continue";

    public static final byte EXPECT_REPLACE_WITH_BYTE = (byte) 'z';

    public static final byte[] CRLF = new byte[]{0x0D, 0x0A};

    public static final String CHUNKED = "chunked";

    public static final String CONNECTION = "Connection";

    public static final String CLOSE = "close";

    public static final String LOCATION = "Location";

    public static final String TRANSFER_ENCODING = "Transfer-Encoding";

    public static final String DEFAULT_CHARSET = "UTF-8";

    public static final String ISO_CHARSET = "ISO-8859-1";

    public static final String CONTENT_TYPE = "Content-Type";

    public static final String USER_AGENT = "User-Agent";

    public static final String USER_AGENT_LOWER = USER_AGENT.toLowerCase();

    public static final String PROTOCOL = "Protocol";

    public static final String URI = "URI";    

    public static final String X_FORWARDED_SERVER = "X-Forwarded-Server";
    
    public static final String X_FORWARDED_FOR = "X-Forwarded-For";

    public static final String TRUE_CLIENT_IP = "True-Client-IP";

    public static final String X_USER_IP = "X-User-IP";

    public static final String HOST = "Host";

    public static final byte CR = 13; // Carriage return character.

    public static final byte LF = 10; // Line feed character.

    public static final char SP = 32; // Single space character.

    public static final char HT = 9; // Horizontal tab character.

}
