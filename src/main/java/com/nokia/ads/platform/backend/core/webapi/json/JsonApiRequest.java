package com.nokia.ads.platform.backend.core.webapi.json;

import java.util.Locale;

import com.nokia.ads.platform.backend.core.webapi.ApiRequest;
import com.nokia.ads.platform.backend.core.webapi.json.gson.GsonHelper;
import com.nokia.ads.platform.backend.core.webapi.json.gson.GsonObject;
import com.nokia.ads.platform.backend.profile.AdNetwork;
import com.nokia.ads.platform.backend.profile.DataFormat;

/**
 * handle the JSON request
 * 
 * JSON request format: <code>
 * {
 *   network: AdNetwork,
 *   locale: Locale,
 *   params:
 *   {
 *     key1: value1,
 *     key2: value2,
 *   }
 * }
 * </code>
 * 
 * @author kenliu
 * 
 */
public class JsonApiRequest extends ApiRequest {

	private AdNetwork network = null;
	private Locale locale = null;
	private GsonObject rawParams = null;

	public JsonApiRequest() {
	}

	public JsonApiRequest(String jsonData) {
		parse(jsonData);
	}

	@Override
	public void parse(String data) {
		GsonObject o = GsonHelper.buildGson().fromJson(data, GsonObject.class);
		network = AdNetwork.valueOf(o.get(LABEL_NETWORK, String.class).toUpperCase());
		locale = new Locale(o.get(LABEL_LOCALE, String.class));
		rawParams = o.get(LABEL_PARAMS);
	}

	@Override
	public DataFormat getFormat() {
		return DataFormat.JSON;
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
		return rawParams.keys().toArray(new String[] {});
	}

	@Override
	public <T> T getParam(String key, Class<T> t) {
		return rawParams.get(key, t);
	}

}
