package com.iaat.webapi.analyse.retention;

import java.util.List;

import com.iaat.business.factory.BusinessFactory;
import com.iaat.business.retention.RetentionBusiness;
import com.iaat.json.SearchBar;
import com.iaat.json.retention.RetentionBean;
import com.iaat.share.ErrorCode;
import com.iaat.share.Params;
import com.iaat.share.RequestUrl;
import com.iaat.webapi.BaseWebApi;
import com.nokia.ads.common.util.Log;
import com.nokia.ads.platform.backend.annotation.ApiPath;
import com.nokia.ads.platform.backend.core.UncheckedException;
import com.nokia.ads.platform.backend.core.webapi.ApiRequest;
import com.nokia.ads.platform.backend.core.webapi.ApiResult;
import com.nokia.ads.platform.backend.core.webapi.ApiService;
import com.nokia.ads.platform.backend.core.webapi.ApiUser;

public class RetentionApi extends BaseWebApi implements ApiService{

	
	private final static Log log = Log.getLogger(RetentionApi.class);
	
	private final static RetentionBusiness retentionBusiness = BusinessFactory.getInstance(RetentionBusiness.class);
	
	@ApiPath(RequestUrl.ANALYSE_RETENTION_CHART)
	public static ApiResult getRetentionAllApi(ApiRequest request,ApiUser user){
		log.info("[RetentionApi.getRetentionAllApi] [begin]");
		ApiResult apiResult = new ApiResult();
		try
		{
			SearchBar bar = SearchBar.getByTypeBar(request);
			List retentions = retentionBusiness.getRetentionAll(bar);
			apiResult.setResult(retentions);
		} catch (UncheckedException e){
			log.error("[RetentionApi.getRetentionAllApi] [{0}]",e.getMessage());
			apiResult.setError(e.getMessage());
		}catch (Exception e) {
			log.error("[RetentionApi.getRetentionAllApi] [{0}]",e.getMessage());
			apiResult.setError(ErrorCode.API_PARAMS_ERROR);
		}
		log.info("[RetentionApi.getRetentionAllApi] [end]");
		return apiResult;
	}
	
	
	
}
