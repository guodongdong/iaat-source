package com.nokia.ads.common.util;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

/**
 * Miscellaneous object utility methods the class is writting.
 * 
 */
public final class ObjectUtils {

	/**
	 * Return whether the given throwable is a checked exception
	 */
	public static boolean isCheckedException(Throwable ex) {
		return !(ex instanceof RuntimeException || ex instanceof Error);
	}

	/**
	 * return whether the given array is empty
	 */
	public static boolean isEmpty(Object[] array) {
		return (array == null || array.length == 0);
	}

	/**
	 * Check whether the given array contains the given element
	 */
	public static boolean containsElement(Object[] array, Object element) {
		if (array == null) {
			return false;
		}
		boolean cotainsFlag = false;
		for (int i = 0; i < array.length; i++) {
			if (equals(array[i], element)) {
				cotainsFlag = true;
				break;
			}
		}
		return cotainsFlag;
	}

	/**
	 * check if the given objects are equal
	 */
	public static boolean equals(Object o1, Object o2) {
		if (o1 == o2) {
			return true;
		}
		if (o1 == null || o2 == null) {
			return false;
		}
		if (o1.equals(o2)) {
			return true;
		}
		if (o1.getClass().isArray() && o2.getClass().isArray()) {
			if (o1 instanceof Object[] && o2 instanceof Object[]) {
				return Arrays.equals((Object[]) o1, (Object[]) o2);
			}
			if (o1 instanceof boolean[] && o2 instanceof boolean[]) {
				return Arrays.equals((boolean[]) o1, (boolean[]) o2);
			}
			if (o1 instanceof byte[] && o2 instanceof byte[]) {
				return Arrays.equals((byte[]) o1, (byte[]) o2);
			}
			if (o1 instanceof char[] && o2 instanceof char[]) {
				return Arrays.equals((char[]) o1, (char[]) o2);
			}
			if (o1 instanceof double[] && o2 instanceof double[]) {
				return Arrays.equals((double[]) o1, (double[]) o2);
			}
			if (o1 instanceof float[] && o2 instanceof float[]) {
				return Arrays.equals((float[]) o1, (float[]) o2);
			}
			if (o1 instanceof int[] && o2 instanceof int[]) {
				return Arrays.equals((int[]) o1, (int[]) o2);
			}
			if (o1 instanceof long[] && o2 instanceof long[]) {
				return Arrays.equals((long[]) o1, (long[]) o2);
			}
			if (o1 instanceof short[] && o2 instanceof short[]) {
				return Arrays.equals((short[]) o1, (short[]) o2);
			}
		}
		return false;
	}

	/**
	 * Append the given Object to the given array, returning a new array
	 * consisting of the input array contents plus the given Object.
	 * 
	 * @param Object
	 *            []
	 * @param Object
	 */
	public static Object[] addObjectToArray(Object[] array, Object obj) {
		Class<?> compType = Object.class;
		if (array != null) {
			compType = array.getClass().getComponentType();
		} else if (obj != null) {
			compType = obj.getClass();
		}
		int newArrLength = (array != null ? array.length + 1 : 1);
		Object[] newArr = (Object[]) Array.newInstance(compType, newArrLength);
		if (array != null) {
			System.arraycopy(array, 0, newArr, 0, array.length);
		}
		newArr[newArr.length - 1] = obj;
		return newArr;
	}

	public static boolean isEmpty(Collection<?> collection){
		if(collection == null || collection.size() == 0 ){
			return true;
		}else{
			return false;
		}
	}

	public static String[] getStackTraces(Throwable t) {
		StackTraceElement[] stackTrace = t.getStackTrace();
		List<String> traces = new ArrayList<String>();
		for (StackTraceElement e : stackTrace) {
			if (e.isNativeMethod()) {
				break;
			}
			traces.add(e.getClassName() + ":" + e.getMethodName() + "@"
					+ e.getFileName() + ":" + e.getLineNumber());
		}
		return traces.toArray(new String[] {});
	}
	
	public static String getStackTrace(Throwable xThrowable) {
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		Throwable xCause = xThrowable.getCause();
		if (xCause == null)
			xCause = xThrowable;
		xCause.printStackTrace(printWriter);
		return stringWriter.toString();
	}

	public static String getTopLevelStackTrace(Throwable xThrowable) {
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		xThrowable.printStackTrace(printWriter);
		return stringWriter.toString();
	}

	public static String getExceptionCauseMessage(Exception xGeneric) {
		Throwable xCause = xGeneric.getCause();
		if (xCause == null)
			xCause = xGeneric;
		String message = xCause.getMessage();
		if (message == null)
			message = xGeneric.getMessage();
		return message;
	}

	public static String getExceptionCauseMessage(Throwable xThrowable) {
		Throwable xCause = xThrowable.getCause();
		if (xCause == null)
			xCause = xThrowable;
		return xCause.getMessage();
	}

	public static String getPropertiesString(Properties properties) {
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		properties.list(printWriter);
		return stringWriter.toString();
	}
	
	@SuppressWarnings("unchecked")
	public static <T> List<T> toList(List<?> array, Class<T> clz) {
		return (List<T>)array;
	}
}
