package com.iaat.webapi.analyse.track;

import java.util.ArrayList;
import java.util.List;

import com.iaat.business.analyse.AccessTrackBusiness;
import com.iaat.business.factory.BusinessFactory;
import com.iaat.json.FocusUrlsBean;
import com.iaat.json.SearchBar;
import com.iaat.json.analyse.AccessBean;
import com.iaat.json.analyse.AccessTrackBean;
import com.iaat.json.analyse.UvCountBean;
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
import com.nokia.ads.platform.backend.util.Paging;

public class AccessTrackApi extends BaseWebApi implements ApiService{
	private final static Log log = Log.getLogger(AccessTrackApi.class);
	
	@ApiPath(RequestUrl.ANALYSE_AS_URLS_LIST)
	public static ApiResult getAllUrlsApi(ApiRequest request, ApiUser user) {
		log.info("[AccessTrackApi.getAllUrlsApi] [begin]");
		ApiResult apiResult = new ApiResult();
		try
		{
			SearchBar bar = SearchBar.getBaseBar(request);
			bar.setProvince(Params.REGION_NAME);
			AccessTrackBusiness accessTrack = BusinessFactory.getInstance(AccessTrackBusiness.class);
			List<AccessTrackBean> latitudes= accessTrack.getUrlsList(bar);
			apiResult.setResult(latitudes);
		} catch (UncheckedException e){
			log.error("[AccessTrackApi.getAllUrlsApi] [{0}]",e.getMessage());
			apiResult.setError(e.getMessage());
		}catch (Exception e) {
			log.error("[AccessTrackApi.getAllUrlsApi] [{0}]",e.getMessage());
			apiResult.setError(ErrorCode.API_PARAMS_ERROR);
		}
		log.info("[AccessTrackApi.getAllUrlsApi] [end]");
		return apiResult;
	}
	@ApiPath(RequestUrl.ANALYSE_INPUT_UV_COUNT)
	public static ApiResult getInputUVCountApi(ApiRequest request, ApiUser user) {
		log.info("[AccessTrackApi.getInputUVCountApi] [begin]");
		ApiResult apiResult = new ApiResult();
		try
		{
			String fouceUrls = request.getParam(Params.FOCUS_URLS,String.class);
			SearchBar bar = SearchBar.getBaseBar(request);
			bar.setProvince(Params.REGION_NAME);
			bar.setLogSource(bar.getLogSource());
			AccessTrackBusiness accessTrack = BusinessFactory.getInstance(AccessTrackBusiness.class);
			AccessTrackBean accessTrackBean= accessTrack.getInputUVCount(bar, fouceUrls);
			apiResult.setResult(accessTrackBean);
		} catch (UncheckedException e){
			log.error("[LatitudeApi.getInputUVCountApi] [{0}]",e.getMessage());
			apiResult.setError(e.getMessage());
		}catch (Exception e) {
			log.error("[LatitudeApi.getInputUVCountApi] [{0}]",e.getMessage());
			apiResult.setError(ErrorCode.API_PARAMS_ERROR);
		}
		log.info("[AccessTrackApi.getInputUVCountApi] [end]");
		return apiResult;
	}
	@ApiPath(RequestUrl.ANALYSE_UV_COUNT)
	public static ApiResult getUVCountApi(ApiRequest request, ApiUser user) {
		log.info("[AccessTrackApi.getInputUVCountApi] [begin]");
		ApiResult apiResult = new ApiResult();
		try
		{
			String fouceUrls = request.getParam(Params.FOCUS_URLS,String.class);
			SearchBar bar = SearchBar.getBaseBar(request);
			bar.setProvince(Params.REGION_NAME);
			bar.setLogSource(bar.getLogSource());
			UvCountBean uvCountBean = new UvCountBean();
			uvCountBean.setFocusUrl(fouceUrls);
			AccessTrackBusiness accessTrack = BusinessFactory.getInstance(AccessTrackBusiness.class);
			AccessTrackBean inputBean = accessTrack.getInputUVCount(bar, fouceUrls);
			AccessTrackBean outBean= accessTrack.getOutputUVCount(bar, fouceUrls);
			uvCountBean.setInputUv(inputBean.getSize());
			uvCountBean.setOutputUv(outBean.getSize());
			apiResult.setResult(uvCountBean);
		} catch (UncheckedException e){
			log.error("[LatitudeApi.getInputUVCountApi] [{0}]",e.getMessage());
			apiResult.setError(e.getMessage());
		}catch (Exception e) {
			log.error("[LatitudeApi.getInputUVCountApi] [{0}]",e.getMessage());
			apiResult.setError(ErrorCode.API_PARAMS_ERROR);
		}
		log.info("[AccessTrackApi.getInputUVCountApi] [end]");
		return apiResult;
	}
	
	
	@ApiPath(RequestUrl.ANALYSE_OUTPUT_UV_COUNT)
	public static ApiResult getOutputUVCountApi(ApiRequest request, ApiUser user) {
		log.info("[AccessTrackApi.getOutputUVCountApi] [begin]");
		ApiResult apiResult = new ApiResult();
		try
		{
			String fouceUrls = request.getParam(Params.FOCUS_URLS,String.class);
			SearchBar bar = SearchBar.getBaseBar(request);
			bar.setProvince(Params.REGION_NAME);
			AccessTrackBusiness accessTrack = BusinessFactory.getInstance(AccessTrackBusiness.class);
			AccessTrackBean accessTrackBean= accessTrack.getOutputUVCount(bar, fouceUrls);
			accessTrackBean.setName(fouceUrls);
			accessTrackBean.setName(fouceUrls);
			apiResult.setResult(accessTrackBean);
		} catch (UncheckedException e){
			log.error("[AccessTrackApi.getOutputUVCountApi] [{0}]",e.getMessage());
			apiResult.setError(e.getMessage());
		}catch (Exception e) {
			log.error("[AccessTrackApi.getOutputUVCountApi] [{0}]",e.getMessage());
			apiResult.setError(ErrorCode.API_PARAMS_ERROR);
		}
		log.info("[AccessTrackApi.getOutputUVCountApi] [end]");
		return apiResult;
	}
	@ApiPath(RequestUrl.ANALYSE_INPUT_URLS)
	public static ApiResult getInputUrlsApi(ApiRequest request, ApiUser user) {
		log.info("[AccessTrackApi.getInputUrlsApi] [begin]");
		ApiResult apiResult = new ApiResult();
		try
		{
			String fouceUrls = request.getParam(Params.FOCUS_URLS,String.class);
			SearchBar bar = SearchBar.getBaseBar(request);
			bar.setProvince(Params.REGION_NAME);
			Paging paging = getPaging(request);
			AccessTrackBusiness accessTrack = BusinessFactory.getInstance(AccessTrackBusiness.class);
			List<AccessBean> accessBeans= accessTrack.getInputUrlsByFocus(bar, fouceUrls,paging);
			apiResult.setResult(accessBeans);
			apiResult.setPaging(paging);
		} catch (UncheckedException e){
			log.error("[AccessTrackApi.getInputUrlsApi] [{0}]",e.getMessage());
			apiResult.setError(e.getMessage());
		}catch (Exception e) {
			log.error("[AccessTrackApi.getInputUrlsApi] [{0}]",e.getMessage());
			apiResult.setError(ErrorCode.API_PARAMS_ERROR);
		}
		log.info("[AccessTrackApi.getInputUrlsApi] [end]");
		return apiResult;
	}
	@ApiPath(RequestUrl.ANALYSE_OUTPUT_URLS)
	public static ApiResult getOutputUrlsApi(ApiRequest request, ApiUser user) {
		log.info("[AccessTrackApi.getOutputUrlsApi] [begin]");
		ApiResult apiResult = new ApiResult();
		try
		{
			String fouceUrls = request.getParam(Params.FOCUS_URLS,String.class);
			SearchBar bar = SearchBar.getBaseBar(request);
			bar.setProvince(Params.REGION_NAME);
			Paging paging = getPaging(request);
			AccessTrackBusiness accessTrack = BusinessFactory.getInstance(AccessTrackBusiness.class);
			List<AccessBean> accessBeans= accessTrack.getOuputUrlsByFocus(bar, fouceUrls,paging);
			apiResult.setResult(accessBeans);
			apiResult.setPaging(paging);
		} catch (UncheckedException e){
			log.error("[AccessTrackApi.getOutputUrlsApi] [{0}]",e.getMessage());
			apiResult.setError(e.getMessage());
		}catch (Exception e) {
			log.error("[AccessTrackApi.getOutputUrlsApi] [{0}]",e.getMessage());
			apiResult.setError(ErrorCode.API_PARAMS_ERROR);
		}
		log.info("[AccessTrackApi.getOutputUrlsApi] [end]");
		return apiResult;
	}
	@ApiPath(RequestUrl.ANALYSE_OUTPUT_TOP_URLS)
	public static ApiResult getTopFocusUrlsApi(ApiRequest request,ApiUser apiUser){
		log.info("[AccessTrackApi.getTopFocusUrlsApi] [begin]");
		ApiResult apiResult = new ApiResult();
		try{
			AccessTrackBusiness accessTrack = BusinessFactory.getInstance(AccessTrackBusiness.class);
			SearchBar bar = SearchBar.getBaseBar(request);
			bar.setProvince(Params.REGION_NAME);
			List<FocusUrlsBean> focusUrls = accessTrack.getTopFocusUrls(bar);
			List<String> urlLists = new ArrayList<String>();
			for (FocusUrlsBean urlBean : focusUrls) {
				urlLists.add(urlBean.getFocusUrl());
			}
			apiResult.setResult(urlLists.toArray(new String[]{}));
		} catch (UncheckedException e){
			log.error("[AccessTrackApi.getTopFocusUrlsApi] [{0}]",e.getMessage());
			apiResult.setError(e.getMessage());
		}catch (Exception e) {
			log.error("[AccessTrackApi.getTopFocusUrlsApi] [{0}]",e.getMessage());
			apiResult.setError(ErrorCode.API_PARAMS_ERROR);
		}
		log.info("[AccessTrackApi.getTopFocusUrlsApi] [end]");
		return apiResult;
	}
}
