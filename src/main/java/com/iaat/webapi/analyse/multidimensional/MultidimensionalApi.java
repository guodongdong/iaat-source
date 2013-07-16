package com.iaat.webapi.analyse.multidimensional;

import java.util.List;
import java.util.Map;

import com.iaat.business.factory.BusinessFactory;
import com.iaat.business.multidimensional.MultidimensionalBusiness;
import com.iaat.json.SearchBar;
import com.iaat.json.profile.ProfileBean;
import com.iaat.share.ErrorCode;
import com.iaat.share.RequestUrl;
import com.iaat.webapi.BaseWebApi;
import com.nokia.ads.common.util.Log;
import com.nokia.ads.platform.backend.annotation.ApiPath;
import com.nokia.ads.platform.backend.core.UncheckedException;
import com.nokia.ads.platform.backend.core.webapi.ApiRequest;
import com.nokia.ads.platform.backend.core.webapi.ApiResult;
import com.nokia.ads.platform.backend.core.webapi.ApiService;
import com.nokia.ads.platform.backend.core.webapi.ApiUser;

public class MultidimensionalApi extends BaseWebApi implements ApiService {
	
	private final static Log log = Log.getLogger(MultidimensionalApi.class);
	
	private final static MultidimensionalBusiness BUSINESS = BusinessFactory.getInstance(MultidimensionalBusiness.class);
	
	@ApiPath(RequestUrl.ANALYSE_MULTIDIMENSIONAL_CHART)
	public static ApiResult getChart(ApiRequest request, ApiUser user) {
		ApiResult apiResult = new ApiResult();
		try
		{	
			SearchBar bar = SearchBar.getBaseBar(request);
			List<ProfileBean> list = BUSINESS.getChart(bar);
			apiResult.setResult(list);
		} catch (UncheckedException e){
			log.error("[MultidimensionalApi.getChart] [{0}]",e.getMessage());
			apiResult.setError(e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			log.error("[MultidimensionalApi.getChart] [{0}]",e.getMessage());
			apiResult.setError(ErrorCode.API_PARAMS_ERROR);
		}
		return apiResult;
	}

	@ApiPath(RequestUrl.ANALYSE_MULTIDIMENSIONAL_LIST)
	public static ApiResult getList(ApiRequest request, ApiUser user) {
		ApiResult apiResult = new ApiResult();
		try
		{	
			SearchBar bar = SearchBar.getBaseBar(request);
			Map<String, List<ProfileBean>> map = BUSINESS.getList(bar);
			apiResult.setResult(map);
		} catch (UncheckedException e){
			log.error("[MultidimensionalApi.getList] [{0}]",e.getMessage());
			apiResult.setError(e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			log.error("[MultidimensionalApi.getList] [{0}]",e.getMessage());
			apiResult.setError(ErrorCode.API_PARAMS_ERROR);
		}
		return apiResult;
	}
		
}
