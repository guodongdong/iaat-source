package com.nokia.ads.platform.backend.core.webapi.html;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.nokia.ads.platform.backend.core.UncheckedException;
import com.nokia.ads.platform.backend.core.webapi.ApiRequest;
import com.nokia.ads.platform.backend.profile.AdNetwork;
import com.nokia.ads.platform.backend.profile.DataFormat;

/**
 * handle the HTML request
 * 
 * HTML request format: <code>
 * 
 * ?network=AdNetwork&locale=locale&key1=value1&key2=value2
 *
 * </code>
 * 
 * @author kenliu
 * 
 */
public class HtmlApiRequest extends ApiRequest {

	private AdNetwork network = null;
	private Locale locale = null;
	private Map<String, String[]> rawParams;

	public HtmlApiRequest() {
	}

	public HtmlApiRequest(String formData) {
		parse(formData);
	}

	@Override
	public void parse(String data) {

		Map<String, String[]> request = parseUrlQueryString(data);

		network = AdNetwork
				.valueOf(request.get(LABEL_NETWORK)[0].toUpperCase());
		locale = new Locale(request.get(LABEL_LOCALE)[0]);
		rawParams = request;
	}

	@Override
	public DataFormat getFormat() {
		return DataFormat.HTML;
	}

	@Override
	public Locale getLocale() {
		return locale;
	}

	@Override
	public AdNetwork getNetwork() {
		return network;
	}

	@Override
	public String[] getParamKeys() {
		return rawParams.keySet().toArray(new String[] {});
	}

	@Override
	public <T> T getParam(String key, Class<T> t) {
		if (t.isAssignableFrom(String.class)
				|| (t.isArray() && t.getComponentType().
						isAssignableFrom(String.class))) {
			@SuppressWarnings("unchecked")
			T result = (T) rawParams.get(key);
			return result;
		}
		throw new IllegalArgumentException("t mustbe String or String[]");
	}

	//

	/**
	 * Parses an URL query string and returns a map with the parameter values.
	 * The URL query string is the part in the URL after the first '?' character
	 * up to an optional '#' character. It has the format
	 * "name=value&name=value&...". The map has the same structure as the one
	 * returned by javax.servlet.ServletRequest.getParameterMap(). A parameter
	 * name may occur multiple times within the query string. For each parameter
	 * name, the map contains a string array with the parameter values.
	 * 
	 * @param s
	 *            an URL query string.
	 * @return a map containing parameter names as keys and parameter values as
	 *         map values.
	 */
	public static Map<String, String[]> parseUrlQueryString(String s) {
		if (s == null)
			return new HashMap<String, String[]>(0);
		// In map1 we use strings and ArrayLists to collect the parameter
		// values.
		HashMap<String, Object> map1 = new HashMap<String, Object>();
		int p = 0;
		while (p < s.length()) {
			int p0 = p;
			while (p < s.length() && s.charAt(p) != '=' && s.charAt(p) != '&')
				p++;
			String name = urlDecode(s.substring(p0, p));
			if (p < s.length() && s.charAt(p) == '=')
				p++;
			p0 = p;
			while (p < s.length() && s.charAt(p) != '&')
				p++;
			String value = urlDecode(s.substring(p0, p));
			if (p < s.length() && s.charAt(p) == '&')
				p++;
			Object x = map1.get(name);
			if (x == null) {
				// The first value of each name is added directly as a string to
				// the map.
				map1.put(name, value);
			} else if (x instanceof String) {
				// For multiple values, we use an ArrayList.
				ArrayList<String> a = new ArrayList<String>();
				a.add((String) x);
				a.add(value);
				map1.put(name, a);
			} else {
				@SuppressWarnings("unchecked")
				ArrayList<String> a = (ArrayList<String>) x;
				a.add(value);
			}
		}
		// Copy map1 to map2. Map2 uses string arrays to store the parameter
		// values.
		HashMap<String, String[]> map2 = new HashMap<String, String[]>(map1
				.size());
		for (Map.Entry<String, Object> e : map1.entrySet()) {
			String name = e.getKey();
			Object x = e.getValue();
			String[] v;
			if (x instanceof String) {
				v = new String[] { (String) x };
			} else {
				@SuppressWarnings("unchecked")
				ArrayList<String> a = (ArrayList<String>) x;
				v = new String[a.size()];
				v = a.toArray(v);
			}
			map2.put(name, v);
		}
		return map2;
	}

	private static String urlDecode(String s) {
		try {
			return URLDecoder.decode(s, "UTF-8");
		} catch (Exception e) {
			throw new UncheckedException("URL包含特殊字符!","URL包含特殊字符!");
		}
	}

}
