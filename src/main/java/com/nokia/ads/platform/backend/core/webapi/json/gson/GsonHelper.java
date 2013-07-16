package com.nokia.ads.platform.backend.core.webapi.json.gson;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.CustomizedMapTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.LongSerializationPolicy;
import com.nokia.ads.platform.backend.model.Money;

public class GsonHelper {

	public static boolean QUOTE_LONG = false;

	public static Gson buildGson() {
		Gson gson = new GsonBuilder()
				.excludeFieldsWithModifiers(Modifier.STATIC,
						Modifier.TRANSIENT, Modifier.VOLATILE)
				.registerTypeAdapter(Double.class, new DoubleTypeAdapter())
				.registerTypeAdapter(Long.class, new LongTypeAdapter())
				.registerTypeAdapter(Date.class, new DateTypeAdapter())
				.registerTypeAdapter(Calendar.class, new CalendarTypeAdapter())
				.registerTypeAdapter(GregorianCalendar.class,
						new CalendarTypeAdapter())
				.registerTypeAdapter(GsonObject.class, new StringTypeAdapter())
				.registerTypeAdapter(Map.class, new CustomizedMapTypeAdapter())
				.registerTypeAdapter(HashMap.class,
						new CustomizedMapTypeAdapter())
				.registerTypeAdapter(Money.class, new MoneyTypeAdapter())
				.setLongSerializationPolicy(LongSerializationPolicy.DEFAULT)
				.serializeNulls().setPrettyPrinting().create();
		return gson;
		/*
		 * excludes.add("hibernateLazyInitializer");
		 * excludes.add("getHibernateLazyInitializer");
		 */
	}

	private static class StringTypeAdapter implements
			JsonSerializer<GsonObject>, JsonDeserializer<GsonObject> {
		@Override
		public JsonElement serialize(GsonObject src, Type typeOfSrc,
				JsonSerializationContext context) {
			return src.getElement();
		}

		@Override
		public GsonObject deserialize(JsonElement element, Type typeOfSrc,
				JsonDeserializationContext context) throws JsonParseException {
			return new GsonObject(element.getAsJsonObject());
		}
	}

	private static class DoubleTypeAdapter implements JsonSerializer<Double>,
			JsonDeserializer<Double> {
		@Override
		public JsonElement serialize(Double src, Type typeOfSrc,
				JsonSerializationContext context) {
			BigDecimal dec = new BigDecimal(src.doubleValue());
			return new JsonPrimitive(dec.toString());
		}

		@Override
		public Double deserialize(JsonElement element, Type typeOfSrc,
				JsonDeserializationContext context) throws JsonParseException {
			return Double.valueOf(element.getAsString());
		}
	}

	private static class LongTypeAdapter implements JsonSerializer<Long>,
			JsonDeserializer<Long> {
		@Override
		public JsonElement serialize(Long src, Type typeOfSrc,
				JsonSerializationContext context) {
			JsonPrimitive prim = null;
			if (QUOTE_LONG) {
				BigDecimal dec = new BigDecimal(src.longValue());
				prim = new JsonPrimitive(dec.toString());
			} else {
				prim = new JsonPrimitive(src);
			}
			return prim;
		}

		@Override
		public Long deserialize(JsonElement element, Type typeOfSrc,
				JsonDeserializationContext context) throws JsonParseException {
			return Long.valueOf(element.getAsString());
		}
	}

	private static class DateTypeAdapter implements JsonSerializer<Date>,
			JsonDeserializer<Date> {
		@Override
		public JsonElement serialize(Date src, Type typeOfSrc,
				JsonSerializationContext context) {
			JsonPrimitive prim = null;
			if (QUOTE_LONG) {
				BigDecimal dec = new BigDecimal(src.getTime());
				prim = new JsonPrimitive(dec.toString());
			} else {
				prim = new JsonPrimitive(src.getTime());
			}
			return prim;
		}

		@Override
		public Date deserialize(JsonElement element, Type typeOfSrc,
				JsonDeserializationContext context) throws JsonParseException {
			return new Date(element.getAsLong());
		}
	}

	private static class CalendarTypeAdapter implements
			JsonSerializer<Calendar>, JsonDeserializer<Calendar> {
		@Override
		public JsonElement serialize(Calendar src, Type typeOfSrc,
				JsonSerializationContext context) {
			JsonPrimitive prim = null;
			if (QUOTE_LONG) {
				BigDecimal dec = new BigDecimal(src.getTimeInMillis());
				prim = new JsonPrimitive(dec.toString());
			} else {
				prim = new JsonPrimitive(src.getTimeInMillis());
			}
			return prim;
		}

		@Override
		public Calendar deserialize(JsonElement element, Type typeOfSrc,
				JsonDeserializationContext context) throws JsonParseException {
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(element.getAsLong());
			return cal;
		}
	}

	private static class MoneyTypeAdapter implements
			JsonSerializer<Money>, JsonDeserializer<Money> {
		@Override
		public JsonElement serialize(Money src, Type typeOfSrc,
				JsonSerializationContext context) {
			return new JsonPrimitive(src.getStringValue());
		}

		@Override
		public Money deserialize(JsonElement element, Type typeOfSrc,
				JsonDeserializationContext context) throws JsonParseException {
			return Money.valueOf(element.getAsString());
		}
	}
}
