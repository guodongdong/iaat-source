package com.nokia.ads.platform.backend.util;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.nokia.ads.common.util.Log;
import com.nokia.ads.platform.backend.core.webapi.ApiError;
import com.nokia.ads.platform.backend.core.webapi.json.gson.GsonHelper;
import com.nokia.ads.platform.backend.core.webapi.json.gson.GsonObject;
import com.nokia.ads.platform.backend.profile.AdNetwork;

public class JsonHttpConnector {

	private static final Log log = Log.getLogger(JsonHttpConnector.class);

	private HttpRequestSimulator simulator;

	public JsonHttpConnector(String url) {
		simulator = new HttpRequestSimulator(url, null);
	}

	public GsonObject send(AdNetwork network, Locale locale, Map<String, Object> params) {
		Map<String, Object> jsonRequest = new HashMap<String, Object>();
		jsonRequest.put("locale", locale);
		jsonRequest.put("network", network);
		jsonRequest.put("params", params);
		//
		String requestData = GsonHelper.buildGson().toJson(jsonRequest);
		log.debug("Request: {0}", requestData);
		String respData = simulator.postData(requestData, null);
		log.debug("Response: {0}", respData);
		//
		GsonObject gson = GsonHelper.buildGson().fromJson(respData, GsonObject.class);
		//
		ApiError err = gson.get("error", ApiError.class);
		if (err != null) {
			throw new RuntimeException(err.getCode() + ":" + err.getMessage());
		}
		return gson.get("result");
	}

	/**
	 * send api request with oauth 
	 * @param network
	 * @param locale
	 * @param key
	 * @param secret 
	 * @param params
	 * @return
	 */
	public GsonObject send(AdNetwork network, Locale locale, String key, String secret, Map<String, Object> params) {
		Map<String, Object> jsonRequest = new HashMap<String, Object>();
		jsonRequest.put("locale", locale);
		jsonRequest.put("network", network);
		jsonRequest.put("key", key);
		jsonRequest.put("secret", secret);
		jsonRequest.put("params", params);
		String requestData = GsonHelper.buildGson().toJson(jsonRequest);
		log.debug("Request: {0}", requestData);
		String respData = simulator.postData(requestData, null);
		log.debug("Response: {0}", respData);
		GsonObject gson = GsonHelper.buildGson().fromJson(respData, GsonObject.class);
		//
		ApiError err = gson.get("error", ApiError.class);
		if (err != null) {
			throw new RuntimeException(err.getCode() + ":" + err.getMessage());
		}
		return gson.get("result");
	}
}
