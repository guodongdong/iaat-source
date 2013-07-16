package com.nokia.ads.common.util;

import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Collections;

/**
 * This is a simple LRU HashMap. It has a max size after which the least used
 * data entry is removed from the Map.
 */
public class LRUSimpleMap {
	private static final int MAX_ENTRIES = 2000;
	private Map<?,?> localCache = null;

	public LRUSimpleMap() {
		localCache = Collections.synchronizedMap(new LinkedHashMap(MAX_ENTRIES,
				.75F, true) {
			protected boolean removeEldestEntry(Map.Entry eldest) {
				return size() > MAX_ENTRIES;
			}
		});
	}

	public Map getMap() {
		return localCache;
	}
}
