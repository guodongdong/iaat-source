package com.nokia.ads.platform.backend.core.webapi;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import com.google.common.collect.Multimap;
import com.nokia.ads.common.util.Log;
import com.nokia.ads.platform.backend.annotation.ApiPath;
import com.nokia.ads.platform.backend.annotation.Authorization;

/**
 * contains the api methods serve the requests from front-end
 * 
 * @author kenliu
 * 
 */
final public class ApiMaps {

	private static final Log log = Log.getLogger(ApiMaps.class);

	private static Map<String, ApiMethod> apiMaps = new HashMap<String, ApiMethod>();

	public static void RegisterAll(String... packages) {
		List<Class<? extends ApiService>> subTypes = new ArrayList<Class<? extends ApiService>>();

		List<String> packageList = new ArrayList<String>();
		packageList.add(ApiService.class.getPackage().getName());
		if (packages != null)
			packageList.addAll(Arrays.asList(packages));

		// using Reflections to find all the annotated sub-classes
		FilterBuilder predicate = new FilterBuilder();
		for (String p : packageList) {
			predicate.include(FilterBuilder.prefix(p));
		}
		Set<URL> cp = ClasspathHelper.getUrlsForCurrentClasspath();
		try {
			Reflections r = new Reflections(new ConfigurationBuilder()
					.filterInputsBy(predicate).setUrls(cp)
					.setScanners(new SubTypesScanner()));
			Collection<? extends Class<? extends ApiService>> apis = getSubTypes(
					r, ApiService.class);
			log.info("finding webapi in [{0}], got {1} classes", packageList,
					apis.size());
			subTypes.addAll(apis);
		} catch (Exception e) {
			log.warn("cannot find subTypes in " + packageList, e);
		}

		for (Class<? extends ApiService> clz : subTypes) {
			Register(clz);
		}
	}

	@SuppressWarnings("unchecked")
	private static <T> Collection<? extends Class<? extends T>> getSubTypes(
			Reflections r, Class<T> clz) {

		Collection<Class<? extends T>> subTypes = new ArrayList<Class<? extends T>>();

		Multimap<String, String> map = r.getStore().get(SubTypesScanner.class);
		for (String key : map.keySet()) {
			try {
				log.debug("checking [{0}]", key);
				Class<?> c = Class.forName(key);
				for (Class<?> cc : r.getSubTypesOf(c)) {
					if (clz.isAssignableFrom(cc)) {
						subTypes.add((Class<? extends T>) cc);
					}
				}
			} catch (ClassNotFoundException ex) {
				log.warn("ClassNotFoundException when checking [{0}]", key);
			}
		}

		return subTypes;
		// return r.getSubTypesOf(clz);
	}

	public static void Register(Class<? extends ApiService> clz) {
		if (clz != null) {
			for (Method m : clz.getMethods()) {
				Authorization auth = m.getAnnotation(Authorization.class);
				ApiPath path = m.getAnnotation(ApiPath.class);
				//
				if (path != null) {
					apiMaps.put(path.value(), new ApiMethod(m, path, auth));
				}
			}
		}
	}

	public static Set<String> getApiPaths() {
		return apiMaps.keySet();
	}

	public static ApiMethod findApiMethod(String action) {
		for (String path : apiMaps.keySet()) {
			if (path.equalsIgnoreCase(action)) {
				return apiMaps.get(path);
			}
		}
		return null;
	}

	final public static class ApiMethod {
		private Method method;
		private ApiPath path;
		private Authorization auth;

		public ApiMethod(Method method, ApiPath path, Authorization auth) {
			if (Modifier.isStatic(method.getModifiers())) {
				this.method = method;
				this.path = path;
				this.auth = auth;
			} else {
				throw new IllegalArgumentException(
						"ApiMethod must be static and accessible");
			}
		}

		public Method getMethod() {
			return this.method;
		}

		public ApiPath getPath() {
			return this.path;
		}

		public Authorization getAuth() {
			return this.auth;
		}
	}
}
