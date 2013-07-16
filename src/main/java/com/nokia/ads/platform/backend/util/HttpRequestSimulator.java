package com.nokia.ads.platform.backend.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

import com.nokia.ads.common.util.Log;

public class HttpRequestSimulator {

	private static Log log = Log.getLogger(HttpRequestSimulator.class);

	private String server = "localhost";
	private int port = 8080;
	private String path = "/api";
	private String protocol = "http";
	private Locale locale = Locale.US;
	private String charset = "UTF-8";
	private String headerCookie = "";

	public HttpRequestSimulator(String postUrl, Locale locale) {
		try {
			URL url = new URL(postUrl);
			this.server = url.getHost();
			this.port = url.getPort();
			this.path = url.getPath();
			this.protocol = url.getProtocol();
			if (locale!=null)
				this.locale = locale;
		} catch (MalformedURLException ex) {
			log.error(ex);
		}
	}
	
	public String postData(String data, String cookie) {
		return postData(data, cookie, charset);
	}

	protected String postData(String data, String cookie, String charset) {
		log.debug("Sending HTTP POST to " + server + ":" + port);
		log.debug("JSON data: " + data);

		String responseData = null;

		try {
			// Send POST data
			URL url = new URL(protocol + "://" + server + ":" + port + path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// conn.setInstanceFollowRedirects(false); // Do not follow server
			// side redirects
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded; charset=UTF-8");
			conn.setRequestProperty("Content-Length",
					String.valueOf(data.length()));

			cookie = headerCookie + ";" + (cookie == null ? "" : cookie);
			conn.setRequestProperty("Cookie", cookie);

			OutputStreamWriter wr = new OutputStreamWriter(
					conn.getOutputStream(), charset);
			wr.write(data);
			wr.flush();
			
			headerCookie = conn.getHeaderField("Set-Cookie");

			log.debug("getContent, encoding: {0}, type: {1}, data: {2}",
					conn.getContentEncoding(), conn.getContentType(),
					conn.getContent());

			// Log the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), charset));
			StringBuffer sb = new StringBuffer();
			String line;

			while ((line = rd.readLine()) != null) {
				sb.append(line);
			}
			responseData = sb.toString();
			rd.close();
			wr.close();
		} catch (IOException e) {
			log.error(e);
		}
		return responseData;
	}

	// /////////////

	public String getHeaderCookie() {
		return this.headerCookie;
	}

	public void setHeaderCookie(String headerCookie) {
		this.headerCookie = headerCookie;
	}

	public String getServer() {
		return this.server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public int getPort() {
		return this.port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getProtocol() {
		return this.protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public Locale getLocale() {
		return this.locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

}
