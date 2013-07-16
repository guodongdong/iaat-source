package com.iaat.webapi;

import java.io.File;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import com.nokia.ads.common.util.Log;
import com.nokia.ads.common.util.StringUtils;
import com.nokia.ads.platform.backend.core.UncheckedException;
import com.nokia.ads.platform.backend.core.business.Result;
import com.nokia.ads.platform.backend.core.webapi.ApiRequest;
import com.nokia.ads.platform.backend.core.webapi.ApiResult;
import com.nokia.ads.platform.backend.core.webapi.json.gson.GsonHelper;
import com.nokia.ads.platform.backend.model.ModelObject;
import com.nokia.ads.platform.backend.profile.AdNetwork;
import com.nokia.ads.platform.backend.util.Paging;

/**
 * @name BaseWebApi
 * 
 * @description CLASS_DESCRIPTION
 * 
 *              MORE_INFORMATION
 * 
 * @author lijing
 * 
 * @since 2012-3-22
 * 
 * @version 1.0
 */
public abstract class BaseWebApi {
	static {
		GsonHelper.QUOTE_LONG = true;
	}
	protected static ResourceBundle errorBundle = null;
	protected static ResourceBundle configBundle = null;

	static {
		errorBundle = ResourceBundle.getBundle("config.i18n.error",
				Locale.getDefault());
		configBundle = ResourceBundle.getBundle("config.i18n.sysconfig",
				Locale.getDefault());
	}

	private static final Log log = Log.getLogger(BaseWebApi.class);

	public static class ModelObjectMapping {

		private static Map<Class<?>, Map<AdNetwork, Class<?>>> mapping;

		public static Class<?> getNetworkClass(Class<?> clz, AdNetwork network) {
			return mapping.get(clz).get(network);
		}

		@SuppressWarnings("unchecked")
		public static <T extends ModelObject<ID>, ID extends Serializable> T getInstance(
				ID value, Class<T> clz, AdNetwork network) {
			try {
				T tOfInseance = (T) getNetworkClass(clz, network).newInstance();
				tOfInseance.setId(value);
				return tOfInseance;
			} catch (Exception e) {
				throw new UncheckedException();
			}
		}

		@SuppressWarnings("unchecked")
		public static <T extends ModelObject<ID>, ID extends Serializable> T getInstance(
				Class<T> clz, AdNetwork network) {
			try {
				T tOfInseance = (T) getNetworkClass(clz, network).newInstance();
				return tOfInseance;
			} catch (Exception e) {
				throw new UncheckedException();
			}
		}

	}

	protected static ApiResult getResult(Object value) {
		ApiResult apiResult = new ApiResult();
		apiResult.setResult(value);
		return apiResult;
		/*
		 * if (null == value) { log.error("value is null"); throw new
		 * IllegalArgumentException("value is null"); } if (value instanceof
		 * Result<?>) { Result<?> r = (Result<?>) value; return getResult(r); }
		 * 
		 * ApiResult result = new ApiResult(); if (value.getClass().isArray()) {
		 * result.setResult(value); } else { result.setResult(new Object[] {
		 * value }); } return result;
		 */
	}

	protected static ApiResult getResult(Result<?> result) {
		if (null == result) {
			log.error("[] []result is null");
			throw new IllegalArgumentException("result is null");
		}

		ApiResult apiResult = new ApiResult();
		if (StringUtils.hasLength(result.getError())) {
			apiResult.setError(result.getError());
		} else {
			apiResult.setResult(null == result.getData() ? new Object[] {}
					: result.getData());
			apiResult.setPaging(result.getPaging());
		}
		return apiResult;
	}

	protected static ApiResult getResult(String errorCode) {
		ApiResult result = new ApiResult();
		if (errorCode == null) {
			return result;
		}
		String message = null;
		if (errorBundle.containsKey(errorCode)) {
			message = errorBundle.getString(errorCode);
		} else {
			message = errorCode;
		}
		result.setError(message);
		return result;
	}

	protected static Paging getPaging(ApiRequest request) {
		Paging paging = new Paging();
		Integer iDisplayLength = request.getParam("iDisplayLength", Integer.class);
		Integer iDisplayStart = request.getParam("iDisplayStart", Integer.class);
		if(iDisplayLength == null || iDisplayStart == null) {
			// TODO 确认不传的时候是异常还是查询全部
			return null;
		}
		paging.setPageSize(iDisplayLength);
		paging.setCurrentPage((iDisplayLength + iDisplayStart) / iDisplayLength);
		return paging;
	}

	protected static ApiResult getResult(Exception ex) {
		if (null == ex)
			throw new IllegalArgumentException("ex is null.");
		String message = null;
		if (ex instanceof UncheckedException) {
			UncheckedException businessEx = (UncheckedException) ex;
			if (errorBundle.containsKey(businessEx.getCode())) {
				message = errorBundle.getString(businessEx.getCode());
				message = MessageFormat.format(message, null == businessEx
						.getErrorField() ? "" : businessEx.getErrorField());
			} else {
				message = businessEx.getCode();
			}

		} else {
			message = ex.getMessage();
		}
		ApiResult result = new ApiResult();
		result.setError(message);
		return result;
	}

	protected static String getServerPath() {
		return BaseWebApi.class.getClassLoader().getResource("").getPath()
				.replace("WEB-INF/classes/", "");
	}

	public static void deleteFile(String filePath) {
		File file = new File(filePath);
		if (file.exists() && file.isFile()) {
			file.delete();
		}
	}
}
