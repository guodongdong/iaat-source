/**
 * 
 */
package com.iaat.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.iaat.util.ValidateUtils;
import com.nokia.ads.common.util.Log;
import com.nokia.ads.platform.backend.annotation.Authorization;
import com.nokia.ads.platform.backend.core.auth.IAuthScope;
import com.nokia.ads.platform.backend.core.webapi.ApiError;
import com.nokia.ads.platform.backend.core.webapi.ApiFilter;
import com.nokia.ads.platform.backend.core.webapi.ApiMaps;
import com.nokia.ads.platform.backend.core.webapi.ApiMaps.ApiMethod;
import com.nokia.ads.platform.backend.core.webapi.ApiRequest;
import com.nokia.ads.platform.backend.core.webapi.ApiResponse;
import com.nokia.ads.platform.backend.core.webapi.ApiResult;
import com.nokia.ads.platform.backend.core.webapi.ApiUser;
import com.nokia.ads.platform.backend.profile.DataFormat;
import com.nokia.ads.platform.backend.profile.DataLabel;
/**
 * @author kenliu
 * 
 */
public class ApiServlet extends HttpServlet implements Servlet {

	private static final long serialVersionUID = 1L;

	private static final Log log = Log.getLogger(ApiServlet.class);

	private static final String CHARSET_UTF8 = "UTF-8";
	private static final String SERVLET_CONFIG_FILTER = "filter";
	private static final String SERVLET_CONFIG_PATH = "path";
	private static final String SERVLET_CONFIG_CLASS_PATH = "classpath";
	// private static final String SERVLET_CONFIG_LOG_INIT = "log_system_init";

	private static ApiFilter filterInstance = null;
	private static ApiFilter logFilterInstance = null;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		if (ApiMaps.getApiPaths().size() <= 0) {
			// initialize apiMaps
			String classpath = config
					.getInitParameter(SERVLET_CONFIG_CLASS_PATH);
			if (classpath != null) {
				String[] paths = classpath.split(",");
				ApiMaps.RegisterAll(paths);
			}
			log.info("initialized ApiMaps[{0}]", ApiMaps.getApiPaths().size());
		}

	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		execute(request, response);
	}

	@SuppressWarnings("unchecked")
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (filterInstance == null) {
			filterInstance = getFilterInstance(getServletConfig()
					.getInitParameter(SERVLET_CONFIG_FILTER));
		}
		//
		// if (logFilterInstance == null) {
		// logFilterInstance = getFilterInstance(getServletConfig()
		// .getInitParameter(SERVLET_CONFIG_LOG_INIT));
		// }
		if (filterInstance != null && filterInstance.doPrefix() == false) {
			log.warn("ApiFilter.doPrefix() checking failure");
			return;
		}

		if (logFilterInstance != null) {
			logFilterInstance.doPrefix();
		}

		// long startTime = System.currentTimeMillis();
		log.perf().start();

		// Set the char encoding to UTF-8
		request.setCharacterEncoding(CHARSET_UTF8);

		ApiResult result = null;
		ApiMethod apiMethod = null;
		DataFormat apiDataType = null;
		ApiUser apiUser = null;
		String apiAction = "";

		// build ApiUser
		apiUser = getApiUser(request);

		{
			String requestURI = request.getRequestURI();
			String contextPath = request.getContextPath();

			if (requestURI.contains(".")) {
				// parse data type
				String ext = requestURI
						.substring(requestURI.lastIndexOf(".") + 1);
				apiDataType = DataFormat.valueOf(ext.toUpperCase());
				// parse action path
				apiAction = requestURI.substring(contextPath.length(),
						requestURI.lastIndexOf("."));

				apiAction = apiAction.substring(getServletConfig()
						.getInitParameter(SERVLET_CONFIG_PATH).length()); // "/api/*"

				apiMethod = ApiMaps.findApiMethod(apiAction);
				
				if (apiUser == null && apiMethod.getAuth() == null) {
					// this is a new user, trying to access a url with no auth
					// needed, we should let him go
				} else if (apiUser == null && apiMethod.getAuth() != null) {
					// this is a new user or session expired, trying to access a
					// url need auth, we should tell him to logon
					result = new ApiResult();
					result.setError(ApiError.INVALID_USER, null);
				}
			}
		}

		if (result == null) {

			// check if it is a validate request URL
			if (apiMethod == null || apiDataType == null) {
				log.warn("invalid request: {0}", request.getRequestURI());
				throw new IllegalArgumentException("invalid request");
			}

			// build ApiRequest
			ApiRequest apiRequest = ApiRequest.getInstance(apiDataType,
					apiMethod.getPath(), decodeHttpRequestPayload(request));

			IAuthScope<ApiUser, ApiRequest> scope = null;
			String paramKey = "";

			try {
				if (apiMethod.getAuth() != null) {
					Authorization auth = apiMethod.getAuth();
					paramKey = auth.paramKey();
					scope = (IAuthScope<ApiUser, ApiRequest>) auth.value()
							.newInstance();
					
				}
				if (scope == null
						|| scope.checkScope(apiUser, apiRequest, paramKey)) {
					String ip = getRemoteAddress(request);
					log.warn("from ip:{0},{1},{2}", null == ip ? "" : ip,
							apiAction, null == apiUser
									|| null == apiUser.getUserId() ? ""
									: apiUser.getUserId());
					// OK, execute the apiMethod
					Method m = apiMethod.getMethod();
					result = (ApiResult) m.invoke(null, apiRequest, apiUser);
				} else {
					// fail to pass the scoping check, return auth error
					result = new ApiResult();
					result.setError(ApiError.INVALID_AUTH, null);
				}
			} catch (Exception e) {
				log.error(e);
				throw new ServletException(e);
			}
		}

		assert (result != null);

		// check if result contains session objects
		setSessionObjects(request, result.getSessionObjects());

		// format result and output
		ApiResponse apiResponse = ApiResponse.getInstance(apiDataType, result);

		response.setStatus(HttpServletResponse.SC_OK);
		response.setCharacterEncoding(CHARSET_UTF8);
		response.setContentType(apiResponse.getContentType());
		apiResponse.write(response.getWriter());
		// long endTime = System.currentTimeMillis();
		// log.info("Request: {0} [{1}ms]", apiMethod.getPath().value(), endTime
		// - startTime);
		log.perf().stop("request: {0}", apiMethod.getPath().value());

		if (filterInstance != null && filterInstance.doPostfix() == false) {
			log.warn("ApiFilter.doPostfix() checking failure");
			response.resetBuffer();
			return;
		}
		if (null != logFilterInstance) {
			logFilterInstance.doPostfix();
		}
		
		response.flushBuffer();
	}

	private void setSessionObjects(HttpServletRequest request,
			Map<String, Object> sessionObjects) {
		if (sessionObjects != null) {
			for (String key : sessionObjects.keySet()) {
				request.getSession().setAttribute(key, sessionObjects.get(key));
			}
		}
	}

	private ApiUser getApiUser(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null || session.isNew()) {
			return null;
		}
		Object sessionUserId = session.getAttribute(DataLabel.SESSION_USERID);
		String userId = null;
		if (sessionUserId != null)
			userId = String.valueOf(sessionUserId);
			// build apiUser
		ApiUser user = new ApiUser(userId);
		Object userdata = session.getAttribute(DataLabel.SESSION_USERDATA);
		if (userdata != null && userdata instanceof Serializable)
			user.setData((Serializable) userdata);
		
		Object verificationCode = session.getAttribute(DataLabel.VERIFICATION_CODE);
		if(verificationCode != null) {
			user.setVerificationCode(verificationCode.toString());
		}
		return user;
	}

	private ApiFilter getFilterInstance(String className) {
		if (className != null) {
			try {
				Class<?> clz = Class.forName(className);
				if (ApiFilter.class.isAssignableFrom(clz)) {
					ApiFilter filter = (ApiFilter) clz.newInstance();
					return filter;
				}
			} catch (Exception ex) {
				log.error("Fail to initialize ApiFilter: {0}", className);
			}
		}
		return null;
	}

	//

	public static String decodeHttpRequestPayload(HttpServletRequest request) {
		InputStream inputStream = null;
		int contentLength = request.getContentLength();
		if (contentLength == -1)
			throw new RuntimeException("invalidate content length in request");

		try {
			int offset = 0;
			int length = contentLength;
			int byteCount;

			inputStream = request.getInputStream();
			length = request.getContentLength();

			byte[] payload = new byte[contentLength];

			while (offset < contentLength) {
				byteCount = inputStream.read(payload, offset, length);
				if (byteCount == -1)
					throw new RuntimeException(
							"invalidate content length in request");

				offset += byteCount;
				length -= byteCount;
			}

			String res = new String(payload, request.getCharacterEncoding());
			if (ValidateUtils.isNotNull(res) && res.indexOf("<script") >= 0) {
				res.replaceAll("<script", "&lt;script");
			}
			return res;
		} catch (IOException ioe) {
			log.error(ioe);
			throw new RuntimeException(ioe);
		} finally {
			try {
				if (inputStream != null)
					inputStream.close();
			} catch (IOException e) {
				// do nothing
			}
		}
	}

	public String getRemoteAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown"))
			ip = request.getHeader("Proxy-Client-IP");
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown"))
			ip = request.getHeader("WL-Proxy-Client-IP");
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown"))
			ip = request.getRemoteAddr();
		return ip;
	}
	
}
