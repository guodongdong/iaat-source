package com.google.gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;

import com.google.gson.DefaultTypeAdapters.MapTypeAdapter;
import com.nokia.ads.common.util.Log;

public class CustomizedMapTypeAdapter extends MapTypeAdapter {
	
	private static final Log log = Log.getLogger(CustomizedMapTypeAdapter.class);

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public JsonElement serialize(Map src, Type typeOfSrc,
			JsonSerializationContext context) {

		JsonObject map = new JsonObject();
		Type childGenericType = null;

		if (typeOfSrc instanceof ParameterizedType) {
			childGenericType = null; //new TypeInfoMap(typeOfSrc).getValueType();
		}

		for (Map.Entry entry : (Set<Map.Entry>) src.entrySet()) {
			Object value = entry.getValue();

			JsonElement valueElement;
			if (value == null) {
				valueElement = JsonNull.createJsonNull();
			} else {
				Type childType = (childGenericType == null) ? value.getClass()
						: childGenericType;
				
				log.debug("JsonSerialize: {0}[{1}]", entry.getKey(), childType);
				
				valueElement = context.serialize(value, childType);
			}
			map.add(String.valueOf(entry.getKey()), valueElement);
		}
		return map;
	}

}
