package com.nokia.ads.platform.backend.core.webapi.json;

import java.io.Reader;
import java.io.StringReader;
import java.util.Calendar;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.nokia.ads.common.util.Log;
import com.nokia.ads.platform.backend.core.webapi.ApiResponse;
import com.nokia.ads.platform.backend.core.webapi.ApiResult;
import com.nokia.ads.platform.backend.core.webapi.json.gson.GsonHelper;
import com.nokia.ads.platform.backend.profile.DataFormat;

public class JsonApiResponse extends ApiResponse {

	private static final Log log = Log.getLogger(JsonApiResponse.class);
	
	private static final String CONTENT_TYPE_APPLICATION_JSON_UTF8 = "application/json; charset=utf-8";

	public JsonApiResponse() {
	}

	@Override
	public String getContentType() {
		return CONTENT_TYPE_APPLICATION_JSON_UTF8;
	}

	@Override
	public DataFormat getFormat() {
		return DataFormat.JSON;
	}

	@Override
	public Reader getReader() {
		return new StringReader(formatResult(getResult()));
	}

	private static String formatResult(ApiResult result) {
		
		Class<?> resultType = null;
		
		Object jsonResult = result.getResult();
		
		if (jsonResult==null) {
			log.debug("result.getResult() is null");
			Object[] array = result.getResults();
			if(array == null)
				log.debug("result.getResults() is null");
			else
				resultType = array.getClass().getComponentType();
			jsonResult = array;
		}
		else
		{
			resultType = jsonResult.getClass();
			log.debug("formatResult with type: {0} - {1}", resultType, TypeToken.get(resultType).getType());
		}
		
		
		Gson gson = GsonHelper.buildGson();
		
		JsonObject jsonResponse = new JsonObject();
		jsonResponse.add("timestamp", gson.toJsonTree(Calendar.getInstance().getTime()
				.getTime()));
		jsonResponse.add("error", gson.toJsonTree(result.getError()));
		jsonResponse.add("paging", gson.toJsonTree(result.getPaging()));
		jsonResponse.add("result", gson.toJsonTree(jsonResult));
		
		String jsonData = GsonHelper.buildGson().toJson(jsonResponse);
		
		log.debug("jsonData: {0}", jsonData);
		
		return jsonData;
	}

}
