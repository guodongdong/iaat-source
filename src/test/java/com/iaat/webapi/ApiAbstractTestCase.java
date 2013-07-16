package com.iaat.webapi;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

import com.iaat.model.AccountBean;
import com.nokia.ads.common.util.Log;
import com.nokia.ads.common.util.StringUtils;
import com.nokia.ads.platform.backend.annotation.ApiPath;
import com.nokia.ads.platform.backend.annotation.Authorization;
import com.nokia.ads.platform.backend.core.auth.IAuthScope;
import com.nokia.ads.platform.backend.core.webapi.ApiMaps;
import com.nokia.ads.platform.backend.core.webapi.ApiMaps.ApiMethod;
import com.nokia.ads.platform.backend.core.webapi.ApiRequest;
import com.nokia.ads.platform.backend.core.webapi.ApiResponse;
import com.nokia.ads.platform.backend.core.webapi.ApiResult;
import com.nokia.ads.platform.backend.core.webapi.ApiService;
import com.nokia.ads.platform.backend.core.webapi.ApiUser;
import com.nokia.ads.platform.backend.core.webapi.json.gson.GsonHelper;
import com.nokia.ads.platform.backend.profile.AdNetwork;
import com.nokia.ads.platform.backend.profile.DataFormat;
import com.nokia.ads.platform.backend.util.Paging;

public abstract class ApiAbstractTestCase<T extends ApiService> {

	protected Log log = Log.getLogger(ApiAbstractTestCase.class);

//	private String contextPath = "/madmanager";
	private String contextPath = "/";
	protected ApiMethod apiMethod = null;

	protected DataFormat dataFormat = null;
	private Class<T> apiClass = null;
	private ApiUser apiUser = null;
	
	protected Map<String, Object> parameters;
	
	@SuppressWarnings("unchecked")
	protected ApiAbstractTestCase() {
		this.apiClass = (Class<T>) ((ParameterizedType) this.getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public void setUp(String requestURI) {
		this.apiUser = new ApiUser("101");
		AccountBean accountBean = new AccountBean();
		accountBean.setId(101L);
//		accountBean.setAccountType(AccountType.RESELLER);
//		accountBean.setDeveloperType(DeveloperType.COMPANY);
		
		/*
		this.apiUser.setData(accountBean);
		//1.KeywordApi
		ApiMaps.Register(this.apiClass);
		String ext = requestURI.substring(requestURI.lastIndexOf(".") + 1);
		//2.JSON
		dataFormat = DataFormat.JSON;
		String apiAction =ext;
		//3
		*/		
		this.apiUser.setData(accountBean);
		//1.KeywordApi
		ApiMaps.Register(this.apiClass);
		String ext = requestURI.substring(requestURI.lastIndexOf(".") + 1);
		//2.JSON
		dataFormat = DataFormat.valueOf(ext.toUpperCase());
		String apiAction = requestURI.substring(contextPath.length(),
				requestURI.lastIndexOf("."));
		//3
		
		apiMethod = ApiMaps.findApiMethod(apiAction);
		this.parameters = new HashMap<String, Object>();
		this.parameters.put("locale", "zh_CN");
		this.parameters.put("network", AdNetwork.NOKIA);
		this.parameters.put("params", new HashMap<String, Object>());
	}

	/**
	 * addParam(Here describes this method function with a few words)
	 * 
	 * add paramter
	 * 
	 * @param keyName
	 * @param value
	 * 
	 *            void
	 */
	@SuppressWarnings("unchecked")
	protected void addParam(String keyName, Object value) {
		if (null != this.parameters) {
			if (this.parameters.containsKey("params")) {
				Object mapOfObject = this.parameters.get("params");
				if (mapOfObject instanceof Map) {
					Map<String, Object> paramOfMap = (Map<String, Object>) mapOfObject;
					paramOfMap.put(keyName, value);
					this.parameters.put("params", paramOfMap);
				}
			}
		}
	}

//	protected void testResonse(ApiRequest request) {
//		if (null == request)
//			throw new IllegalArgumentException("request is null");
//		if (null != this.apiMethod) {
//			try {
//				ApiResult result = (ApiResult) this.apiMethod.getMethod()
//						.invoke(null, request,this.apiUser);
//				ApiResponse response = ApiResponse.getInstance(this.dataFormat,
//						result);
//				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//				Writer writer=new OutputStreamWriter(outputStream, "UTF-8");
//				response.write(writer);
////				response.write(outputStream, "UTF-8");
//				String responseOfString = new String(
//						outputStream.toByteArray(), "UTF-8");
//				log.debug(responseOfString);
//				System.out.println("responseOfString:\n"+responseOfString);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}
	
	protected void testResonse(ApiRequest request) {
		if (null == request)
			throw new IllegalArgumentException("request is null");
		if (null != this.apiMethod) {
			try {
				/**添加验证****/
				String paramKey = "";
				boolean isCheck = false;
				IAuthScope<ApiUser, ApiRequest> scope = null;
				if (apiMethod.getAuth() != null) {
					Authorization auth = apiMethod.getAuth();
					paramKey = auth.paramKey();
					scope = (IAuthScope<ApiUser, ApiRequest>) auth.value().newInstance();
					isCheck = scope.checkScope(apiUser, request, paramKey);
				}
				
				ApiResult result = null;
				try {
					result = (ApiResult) this.apiMethod.getMethod()
							.invoke(null, request,this.apiUser);
				} catch (Exception e) {
					e.printStackTrace();
				}
				ApiResponse response = ApiResponse.getInstance(this.dataFormat,
						result);
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				Writer writer=new OutputStreamWriter(outputStream, "UTF-8");
				log.debug("check:"+isCheck);
				response.write(writer);
//				response.write(outputStream, "UTF-8");
				String responseOfString = new String(
						outputStream.toByteArray(), "UTF-8");
				log.error(responseOfString);
				log.debug(responseOfString);
				System.out.println("responseOfString:\n"+responseOfString);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	protected ApiRequest createApiRequest() {
		if (null == this.parameters)
			throw new IllegalArgumentException("the paramters is null");
		String json = GsonHelper.buildGson().toJson(this.parameters);
		System.out.println("json:\n"+json);
		ApiPath apiPath = new ApiPath()
		{
			
			@Override
			public Class<? extends Annotation> annotationType()
			{
				return null;
			}
			
			@Override
			public String value()
			{
				return null;
			}
		};
		ApiRequest request = ApiRequest.getInstance(this.dataFormat, apiPath, json);
		return request;
	}
	
	protected String getUrl(String url) {
		if (!StringUtils.hasLength(url))
			throw new IllegalArgumentException("url is blank");
		return this.contextPath + url + ".json";
	}

	protected Paging getPaging() {
		Paging paging = new Paging();
		paging.setPageSize(7);
		paging.setCurrentPage(2);
		return paging;
	}
	
	protected Paging getNullPaging() {
		Paging paging = new Paging();
		paging.setPageSize(-1);
		paging.setCurrentPage(0);
		return paging;
	}

}
