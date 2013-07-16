package com.iaat.webapi.latitude;


import java.util.List;
import java.util.Map;

import com.iaat.business.factory.BusinessFactory;
import com.iaat.business.latitude.LatitudeBusiness;
import com.iaat.json.LatitudeBean;
import com.iaat.json.LatitudeDataBean;
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

public class LatitudeApi extends BaseWebApi implements ApiService{
	private final static Log log = Log.getLogger(LatitudeApi.class);
	@ApiPath(RequestUrl.GET_SEARCHBAR_LIST)
	public static ApiResult getAllLatitudeApi(ApiRequest request, ApiUser user) {
		log.info("[LatitudeApi.getAllLatitudeApi] [begin]");
		ApiResult apiResult = new ApiResult();
		try
		{
			LatitudeBusiness latBiz = BusinessFactory.getInstance(LatitudeBusiness.class);
			Map<String, List<LatitudeDataBean>> latitudes= latBiz.getAllLatitudes();
			apiResult.setResult(latitudes);
		} catch (UncheckedException e){
			log.error("[LatitudeApi.getAllLatitudeApi] [{0}]",e.getMessage());
			apiResult.setError(e.getMessage());
		}catch (Exception e) {
			log.error("[LatitudeApi.getAllLatitudeApi] [{0}]",e.getMessage());
			apiResult.setError(ErrorCode.API_PARAMS_ERROR);
		}
		log.info("[LatitudeApi.getAllLatitudeApi] [end]");
		return apiResult;
	}
	@ApiPath(RequestUrl.GET_SEARCHBAR_IS_FRESH)
	public static ApiResult getAllLatitudeIsFresh(ApiRequest request, ApiUser user) {
		log.info("[LatitudeApi.getAllLatitudeApi] [begin]");
		ApiResult apiResult = new ApiResult();
		try
		{
			
			LatitudeBean latitudeBean = LatitudeBean.getInstance();
			apiResult.setResult(latitudeBean.isReload());
		} catch (UncheckedException e){
			log.error("[LatitudeApi.getAllLatitudeIsFresh] [{0}]",e.getMessage());
			apiResult.setError(e.getMessage());
		}catch (Exception e) {
			log.error("[LatitudeApi.getAllLatitudeIsFresh] [{0}]",e.getMessage());
			apiResult.setError(ErrorCode.API_PARAMS_ERROR);
		}
		log.info("[LatitudeApi.getAllLatitudeIsFresh] [end]");
		return apiResult;
	}
	
	public static void main(String[] args) {
		LatitudeBean latitudeBean = LatitudeBean.getInstance();
		latitudeBean.setReload(false);
		System.out.println(latitudeBean.isReload());
		latitudeBean.getResults();
		System.out.println(latitudeBean.isReload());
		System.out.println(latitudeBean.isReload());
		System.out.println(latitudeBean.getResults());
		latitudeBean.setReload(true);
		System.out.println(latitudeBean.getResults());
	}
}
