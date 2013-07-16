package com.nokia.ads.platform.backend.core.webapi.json.gson;

import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class GsonObject {

	private JsonElement element;
	
	public Set<String> keys() {
		if (element.isJsonObject()) {
			JsonObject obj = element.getAsJsonObject();
			Set<String> keys = new HashSet<String>();
			for (Entry<String, JsonElement> entry : obj.entrySet()) {
				keys.add(entry.getKey());
			}
			return keys;
		}
		return null;
	}
	
	public GsonObject get(String key) {
		if (element.isJsonObject()) {
			JsonObject obj = element.getAsJsonObject();
			for (Entry<String, JsonElement> entry : obj.entrySet()) {
				if (key.equals(entry.getKey())) {
					return new GsonObject(entry.getValue());
				}
			}
		}
		return null;
	}
	
	public <T> T get(String key, Class<T> t) {
		GsonObject o = get(key);
		if (o!=null && o.getElement()!=null) {
			return GsonHelper.buildGson().fromJson(o.getElement(), t);
		}
		return null;
	}
	
	public GsonObject(String str) {
		this.setJsonString(str);
	}
	
	public GsonObject(JsonElement elem) {
		this.setElement(elem);
	}

	public String getJsonString() {
		return this.element.toString();
	}

	public void setJsonString(String jsonString) {
		JsonPrimitive p = new JsonPrimitive(jsonString);
		if (p.isJsonObject()) {
			element = p.getAsJsonObject();
		} else {
			element = p;
		}
	}

	public JsonElement getElement() {
		return this.element;
	}

	public void setElement(JsonElement element) {
		this.element = element;
	}
	
	
}
