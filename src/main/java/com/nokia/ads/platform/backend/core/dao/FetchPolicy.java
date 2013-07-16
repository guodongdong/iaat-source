package com.nokia.ads.platform.backend.core.dao;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;

import com.nokia.ads.common.util.Log;
import com.nokia.ads.platform.backend.model.ModelObject;

/**
 * <pre>
 * 
 * Example how to use the fetch policy:
 * 
 * - to fetch the single object, without relatived objects
 * FetchPolicy.BASE()
 * 
 * - to fetch the single object, and some specific fields which contains other objects
 * FetchPolicy.BASE().INCLUDE("field1", "field1.subField1", "field2")
 * note: if you want to load a subfield of one field of target object, you've to load the field, otherwise the subfield will not be loaded
 * 
 * - to fetch the single object, with all related objects
 * FetchPolicy.DEEP()
 * 
 * - to do a deep fetch, but not want to load some specific fields
 * FetchPolicy.DEEP().EXCLUDE("field1", "field2.subfield3")
 * note: if you only want to exclude the subfield, do not put the parent field of the subfield in the EXCLUDE statement. otherwise the whole filed will be excluded.
 * 
 * - to do a base fetch, but want to load the collection as well
 * FetchPolicy.BASE().COLLECTION("collection1", "map2");
 * 
 * - to do a collection fetch, but want to deep fetch
 * FetchPolicy.BASE().DEEP_COLLECTION("collection1", "map2");
 * note: DO NOT suggest to use deep collection as it may load lots of data, if DEEP_COLLECTION is must, please put EXCLUDE to restrict that
 * 
 * </pre>
 * 
 * @author kenliu
 * 
 */
abstract public class FetchPolicy {

	protected static Log log = Log.getLogger(FetchPolicy.class);

	private static Class<? extends FetchPolicy> policyClass = null;

	public static void Init(Class<? extends FetchPolicy> clz) {
		policyClass = clz;
	}

	/**
	 * only fetch base level of object, no collection, no non-primitive
	 * properties
	 * 
	 * @return
	 */
	public static FetchPolicy BASE() {
		if (policyClass == null)
			throw new RuntimeException("Init() not called, policyClass is null");
		try {
			FetchPolicy policy = policyClass.newInstance();
			policy.policy_base = true;
			return policy;
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * deep fetch all related properties, no collection
	 * 
	 * @return
	 */
	public static FetchPolicy DEEP() {
		if (policyClass == null)
			throw new RuntimeException("Init() not called, policyClass is null");
		try {
			FetchPolicy policy = policyClass.newInstance();
			policy.policy_deep = true;
			return policy;
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * fetch collection with property name
	 * 
	 * @param property
	 * @return
	 */
	public FetchPolicy COLLECTION(String... properties) {
		for (String property : properties) {
			collectionProperties.add(property);
			deepCollectionProperties.remove(property);
		}
		return this;
	}

	/**
	 * deep fetch collection and all sub objects with property name
	 * 
	 * !!CAUTION please be aware it will take a deep loading
	 * 
	 * @param property
	 * @return
	 */
	public FetchPolicy DEEP_COLLECTION(String... properties) {
		for (String property : properties) {
			deepCollectionProperties.add(property);
			collectionProperties.remove(property);
		}
		return this;
	}

	public FetchPolicy INCLUDE(String... properties) {
		for (String property : properties) {
			includeProperties.add(property);
			excludeProperties.remove(property);
		}
		return this;
	}

	/**
	 * exclude particular property
	 * 
	 * @param property
	 * @return
	 */
	public FetchPolicy EXCLUDE(String... properties) {
		for (String property : properties) {
			excludeProperties.add(property);
			includeProperties.remove(property);
			collectionProperties.remove(property);
			deepCollectionProperties.remove(property);
		}
		return this;
	}

	private boolean policy_base = false;
	private boolean policy_deep = false;
	private Set<String> collectionProperties = new HashSet<String>();
	private Set<String> deepCollectionProperties = new HashSet<String>();
	private Set<String> excludeProperties = new HashSet<String>();
	private Set<String> includeProperties = new HashSet<String>();

	protected FetchPolicy() {
	}

	abstract protected boolean beginTransaction();

	abstract protected void endTransaction();

	abstract protected <T> T newInstance(T obj);

	abstract protected void initialize(Object proxyObject);

	abstract protected boolean isIgnoreReturnType(Method method);

	abstract protected boolean isIgnoreFields(String fieldName);

	public <T extends ModelObject<?>> T filter(T obj) {

		if (obj == null) {
			log.debug("obj is null.");
			return null;
		}

		log.debug(
				"fetch object in policy: base[{0}], deep[{1}], collection[{2}], deepCollection[{3}], include[{4}], exclude[{5}]",
				policy_base, policy_deep, collectionProperties,
				deepCollectionProperties, includeProperties, excludeProperties);

		boolean transactionNeeded = beginTransaction();

		try {

			long stopwatch = Calendar.getInstance().getTimeInMillis();

			T newObject = newInstance(obj);

			copyProperties(newObject, obj, policy_base, policy_deep, null);

			if (transactionNeeded) {
				endTransaction();
			}

			log.info("FetchPolicy: [{0}] - {1}ms", obj.getClass().getName(),
					Calendar.getInstance().getTimeInMillis() - stopwatch);

			return newObject;

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	private <T> void copyProperties(T target, T src, boolean copyBase,
			boolean copyDeep, String prefix) {

		prefix = (prefix == null ? "" : prefix);

		if (target != null && src != null) {

			log.debug("copyProperties: [{0}] to [{1}], under prefix: {2}",
					target, src, prefix);

			initialize(src);

			try {
				PropertyDescriptor[] descriptors = BeanUtilsBean.getInstance()
						.getPropertyUtils().getPropertyDescriptors(src);
				for (PropertyDescriptor descriptor : descriptors) {
					String descriptorName = descriptor.getName();
					Method descriptorReadMethod = descriptor.getReadMethod();
					if ("class".equals(descriptorName)
							|| isIgnoreReturnType(descriptorReadMethod)
							|| isIgnoreFields(descriptorName)) {
						continue;
					}
					if (matchDescriptorName(descriptorName, excludeProperties,
							prefix)) {
						continue;
					}
					String newPrefix = ("".equals(prefix) ? descriptorName
							: prefix + "." + descriptorName);
					log.debug("checking method: [{0}]", descriptorName);
					Class<?> clz = descriptor.getPropertyType();
					if (isCollection(clz)) {
						// property is a collection
						if (matchDescriptorName(descriptorName,
								deepCollectionProperties, prefix)) {
							// copy deep collection
							copyCollection(target, src, descriptor, true,
									prefix);
						} else if (matchDescriptorName(descriptorName,
								collectionProperties, prefix)) {
							// copy collection
							copyCollection(target, src, descriptor, false,
									newPrefix);
						}
					} else if (ModelObject.class.isAssignableFrom(clz)) {
						// property is an another managed object
						if (copyDeep == true) {
							// deep copy
							Object o = descriptorReadMethod.invoke(src);
							Object newObj = newInstance(o);
							copyProperties(newObj, o, true, true, newPrefix);
							copyProperty(target, descriptorName, newObj);
						} else if (matchDescriptorName(descriptorName,
								includeProperties, prefix)) {
							Object o = descriptorReadMethod.invoke(src);
							Object newObj = newInstance(o);
							copyProperties(newObj, o, true, false, newPrefix);
							copyProperty(target, descriptorName, newObj);
						}
					} else {
						Object o = descriptorReadMethod.invoke(src);
						copyProperty(target, descriptorName, o);
					}
				}
			} catch (Exception ex) {
				log.debug(ex);
			}
		}
	}

	/**
	 * @param descriptorName
	 * @param copyDeepCollection
	 * @param prefix
	 * @return
	 */
	private boolean matchDescriptorName(String descriptorName,
			Set<String> collection, String prefix) {
		return collection != null
				&& collection
						.contains((prefix == null || "".equals(prefix) ? descriptorName
								: prefix + "." + descriptorName));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private <T> void copyCollection(T target, T src,
			PropertyDescriptor descriptor, boolean deepCopy, String prefix)
			throws IllegalAccessException, InvocationTargetException {
		Class<?> clz = descriptor.getPropertyType();
		String descriptorName = descriptor.getName();
		Method descriptorReadMethod = descriptor.getReadMethod();
		Class<?> itemClz = null;
		if (clz.isArray()) {
			itemClz = clz.getComponentType();
		}
		log.debug("copyCollection: {0} - {1} || {2}", descriptorName, clz,
				itemClz);
		if (itemClz == null || ModelObject.class.isAssignableFrom(itemClz)) {
			Collection c = getCollectionInstance(clz); // FIXME: clz might be
														// Map
			for (Object o : (Iterable<?>) descriptorReadMethod.invoke(src)) {
				// TODO: we need to do the loop and copy the array
				// 'deepCopy' indicate if we need to do a deep copy of item, or
				// base copy
				// TODO: we also need to support Map
				itemClz = o.getClass();
				try {
					Object newObj = null;
					if (ModelObject.class.isAssignableFrom(itemClz)) {
						newObj = newInstance(o);
						copyProperties(newObj, o, true, deepCopy, prefix);
					} else {
						newObj = cloneBean(o);
					}
					c.add(newObj);
				} catch (Exception ex) {
					log.debug(ex);
				}
			}
			copyProperty(target, descriptorName, c);
		} else {
			Object o = descriptorReadMethod.invoke(src);
			copyProperty(target, descriptorName, o);
		}
	}

	/**
	 * @param o
	 * @return
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	private Object cloneBean(Object o) throws IllegalAccessException,
			InstantiationException, InvocationTargetException,
			NoSuchMethodException {
		//check if it is primitive or string
		if (o == null || o.getClass().isPrimitive() || o instanceof String)
			return o;
		//check if it is Enum
		if (o instanceof Enum) {
			return o;
		}
		try {
			Field f = o.getClass().getField("TYPE");
			if (f != null) {
				Class<?> clz = (Class<?>) f.get(null);
				if (clz.isPrimitive())
					return o;
			}
		} catch (Exception ex) {
		}

		return BeanUtils.cloneBean(o);
	}

	@SuppressWarnings("rawtypes")
	private Collection getCollectionInstance(Class<?> clz) {
		if (clz == Set.class) {
			return new HashSet();
		} else if (clz == List.class) {
			return new ArrayList();
		}
		return null;
	}

	private void copyProperty(Object target, String propertyName, Object value) {
		try {
			log.debug("copyProperty: {0} = {1}", propertyName, value);
			// if (value != null)
			if (BeanUtilsBean.getInstance().getPropertyUtils()
					.isWriteable(target, propertyName))
				BeanUtilsBean.getInstance().getPropertyUtils()
						.setSimpleProperty(target, propertyName, value);
		} catch (Exception ex) {
			log.debug(ex);
		}
	}

	private boolean isCollection(Class<?> clz) {
		if (clz == null) {
			return false;
		}
		if (clz.isArray() || Collection.class.isAssignableFrom(clz)
				|| Map.class.isAssignableFrom(clz)) {
			return true;
		}
		return false;
	}

}
