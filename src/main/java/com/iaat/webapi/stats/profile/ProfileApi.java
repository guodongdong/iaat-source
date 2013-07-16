package com.iaat.webapi.stats.profile;

import java.util.List;

import com.iaat.business.factory.BusinessFactory;
import com.iaat.business.profile.ProfileBusiness;
import com.iaat.json.SearchBar;
import com.iaat.json.profile.ProfileBean;
import com.iaat.json.profile.ProfileChartBean;
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

/**
 * 
 * @name ProfileApi
 * 
 * @description CLASS_DESCRIPTION
 * 
 * MORE_INFORMATION
 * 
 * @author zhaozhi
 * 
 * @since 2013-6-24
 *       
 * @version 1.0
 */
public class ProfileApi extends BaseWebApi implements ApiService {
	private final static Log log = Log.getLogger(ProfileApi.class);
	
	private final static ProfileBusiness business = BusinessFactory.getInstance(ProfileBusiness.class);
	
	@ApiPath(RequestUrl.STATS_USER_PROFILE)
	public static ApiResult getUserProfile(ApiRequest request, ApiUser user) {
		ApiResult apiResult = new ApiResult();
		try
		{	
			SearchBar bar = SearchBar.getBaseBar(request);
			ProfileBean bean = business.getProfileBean(bar);
			apiResult.setResult(bean);
		} catch (UncheckedException e){
			log.error("[ProfileApi.getUserProfile] [{0}]",e.getMessage());
			apiResult.setError(e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			log.error("[ProfileApi.getUserProfile] [{0}]",e.getMessage());
			apiResult.setError(ErrorCode.API_PARAMS_ERROR);
		}
		return apiResult;
	}

	@ApiPath(RequestUrl.STATS_PV_CHART)
	public static ApiResult getPVChart(ApiRequest request, ApiUser user) {
		ApiResult apiResult = new ApiResult();
		try
		{
			SearchBar bar = SearchBar.getStatsTypeBar(request);
			List<ProfileChartBean> list = business.getPVChart(bar);
			apiResult.setResult(list);
		} catch (UncheckedException e){
			log.error("[ProfileApi.getPVChart] [{0}]",e.getMessage());
			apiResult.setError(e.getMessage());
		}catch (Exception e) {
			log.error("[ProfileApi.getPVChart] [{0}]",e.getMessage());
			apiResult.setError(ErrorCode.API_PARAMS_ERROR);
		}
		return apiResult;
	}

	@ApiPath(RequestUrl.STATS_UV_CHART)
	public static ApiResult getUVChart(ApiRequest request, ApiUser user) {
		ApiResult apiResult = new ApiResult();
		try
		{
			SearchBar bar = SearchBar.getStatsTypeBar(request);
			List<ProfileChartBean> list = business.getUVChart(bar);
			apiResult.setResult(list);
		} catch (UncheckedException e){
			log.error("[ProfileApi.getUVChart] [{0}]",e.getMessage());
			apiResult.setError(e.getMessage());
		}catch (Exception e) {
			log.error("[ProfileApi.getUVChart] [{0}]",e.getMessage());
			apiResult.setError(ErrorCode.API_PARAMS_ERROR);
		}
		return apiResult;
	}
	
	@ApiPath(RequestUrl.STATS_INCREASE_USER_CHART)
	public static ApiResult getIncreaseUserChart(ApiRequest request, ApiUser user) {
		ApiResult apiResult = new ApiResult();
		try
		{
			SearchBar bar = SearchBar.getStatsTypeBar(request);
			List<ProfileChartBean> list = business.getIncreaseUserChart(bar);
			apiResult.setResult(list);
		} catch (UncheckedException e){
			log.error("[ProfileApi.getIncreaseUserChart] [{0}]",e.getMessage());
			apiResult.setError(e.getMessage());
		}catch (Exception e) {
			log.error("[ProfileApi.getIncreaseUserChart] [{0}]",e.getMessage());
			apiResult.setError(ErrorCode.API_PARAMS_ERROR);
		}
		return apiResult;
	}

	@ApiPath(RequestUrl.STATS_ONCE_USER_CHART)
	public static ApiResult getOnceUserChart(ApiRequest request, ApiUser user) {
		ApiResult apiResult = new ApiResult();
		try
		{
			SearchBar bar = SearchBar.getStatsTypeBar(request);
			List<ProfileChartBean> list = business.getOnceUserChart(bar);
			apiResult.setResult(list);
		} catch (UncheckedException e){
			log.error("[ProfileApi.getOnceUserChart] [{0}]",e.getMessage());
			apiResult.setError(e.getMessage());
		}catch (Exception e) {
			log.error("[ProfileApi.getOnceUserChart] [{0}]",e.getMessage());
			apiResult.setError(ErrorCode.API_PARAMS_ERROR);
		}
		return apiResult;
	}
	
	@ApiPath(RequestUrl.STATS_ONLY_LOGIN_USER_CHART)
	public static ApiResult getOnlyLoginUserChart(ApiRequest request, ApiUser user) {
		ApiResult apiResult = new ApiResult();
		try
		{
			SearchBar bar = SearchBar.getStatsTypeBar(request);
			List<ProfileChartBean> list = business.getOnlyLoginUserChart(bar);
			apiResult.setResult(list);
		} catch (UncheckedException e){
			log.error("[ProfileApi.getOnlyLoginUserChart] [{0}]",e.getMessage());
			apiResult.setError(e.getMessage());
		}catch (Exception e) {
			log.error("[ProfileApi.getOnlyLoginUserChart] [{0}]",e.getMessage());
			apiResult.setError(ErrorCode.API_PARAMS_ERROR);
		}
		return apiResult;
	}
	
	@ApiPath(RequestUrl.STATS_AVG_USER_LOGIN_CHART)
	public static ApiResult getAvgUserLoginChart(ApiRequest request, ApiUser user) {
		ApiResult apiResult = new ApiResult();
		try
		{
			SearchBar bar = SearchBar.getSearchBar(request,true);
			List<ProfileChartBean> list = business.getUserLoginCountChart(bar);
			apiResult.setResult(list);
		} catch (UncheckedException e){
			log.error("[ProfileApi.getAvgUserLoginChart] [{0}]",e.getMessage());
			apiResult.setError(e.getMessage());
		}catch (Exception e) {
			log.error("[ProfileApi.getAvgUserLoginChart] [{0}]",e.getMessage());
			apiResult.setError(ErrorCode.API_PARAMS_ERROR);
		}
		return apiResult;
	}

	@ApiPath(RequestUrl.STATS_AVG_USER_ACCESS_CHART)
	public static ApiResult getAvgUserAccessChart(ApiRequest request, ApiUser user) {
		ApiResult apiResult = new ApiResult();
		try
		{
			SearchBar bar = SearchBar.getSearchBar(request,true);
			List<ProfileChartBean> list = business.getUserAccessCountChart(bar);
			apiResult.setResult(list);
		} catch (UncheckedException e){
			log.error("[ProfileApi.getAvgUserAccessChart] [{0}]",e.getMessage());
			apiResult.setError(e.getMessage());
		}catch (Exception e) {
			log.error("[ProfileApi.getAvgUserAccessChart] [{0}]",e.getMessage());
			apiResult.setError(ErrorCode.API_PARAMS_ERROR);
		}
		return apiResult;
	}
	
	
}
