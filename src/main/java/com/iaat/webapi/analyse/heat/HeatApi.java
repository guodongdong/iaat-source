package com.iaat.webapi.analyse.heat;

import java.util.List;
import java.util.Map;

import com.iaat.business.factory.BusinessFactory;
import com.iaat.business.heat.HeatBusiness;
import com.iaat.json.ChartBean;
import com.iaat.json.SearchBar;
import com.iaat.json.heat.HeatRegionBean;
import com.iaat.json.heat.OperatorRatioBean;
import com.iaat.json.heat.RegionChartBean;
import com.iaat.share.ErrorCode;
import com.iaat.share.Params;
import com.iaat.share.RequestUrl;
import com.iaat.util.ExportUtil;
import com.iaat.util.ValidateUtils;
import com.iaat.webapi.BaseWebApi;
import com.nokia.ads.common.util.Log;
import com.nokia.ads.platform.backend.annotation.ApiPath;
import com.nokia.ads.platform.backend.core.UncheckedException;
import com.nokia.ads.platform.backend.core.webapi.ApiRequest;
import com.nokia.ads.platform.backend.core.webapi.ApiResult;
import com.nokia.ads.platform.backend.core.webapi.ApiService;
import com.nokia.ads.platform.backend.core.webapi.ApiUser;
import com.nokia.ads.platform.backend.util.Paging;

public class HeatApi extends BaseWebApi implements ApiService {
	private final static Log log = Log.getLogger(HeatApi.class);
	
	private final static HeatBusiness BUSINESS = BusinessFactory.getInstance(HeatBusiness.class);
	
	@ApiPath(RequestUrl.STATS_HEAT_NATION_MAP_CHART)
	public static ApiResult getNationMapChart(ApiRequest request, ApiUser user) {
		ApiResult apiResult = new ApiResult();
		try
		{	
			SearchBar bar = SearchBar.getBaseBar(request);
			List<RegionChartBean> list = BUSINESS.getNationMapChart(bar);
			apiResult.setResult(list);
		} catch (UncheckedException e){
			log.error("[HeatApi.getNationMapChart] [{0}]",e.getMessage());
			apiResult.setError(e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			log.error("[HeatApi.getNationMapChart] [{0}]",e.getMessage());
			apiResult.setError(ErrorCode.API_PARAMS_ERROR);
		}
		return apiResult;
	}

	@ApiPath(RequestUrl.STATS_HEAT_REGION_DETAIL_LIST)
	public static ApiResult getRegionDetailList(ApiRequest request, ApiUser user) {
		ApiResult apiResult = new ApiResult();
		try
		{	
			SearchBar bar = SearchBar.getBaseBar(request);
			Paging paging = getPaging(request);
			Map<String, List<HeatRegionBean>> list = BUSINESS.getRegionDetailList(bar);
			apiResult.setResult(list);
			apiResult.setPaging(paging);
		} catch (UncheckedException e){
			log.error("[HeatApi.getRegionDetailList] [{0}]",e.getMessage());
			apiResult.setError(e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			log.error("[HeatApi.getRegionDetailList] [{0}]",e.getMessage());
			apiResult.setError(ErrorCode.API_PARAMS_ERROR);
		}
		return apiResult;
	}

	@ApiPath(RequestUrl.STATS_HEAT_REGION_DETAIL_LIST_EXPORT)
	public static ApiResult getRegionDetailListExport(ApiRequest request, ApiUser user) {
		ApiResult apiResult = new ApiResult();
		try
		{	
			SearchBar bar = SearchBar.getBaseBar(request);
			List<HeatRegionBean> list = BUSINESS.getRegionDetailListExport(bar);
			String path = "";
			if (ValidateUtils.isNotNull(list)) {
				path = ExportUtil.exportToCsv(list,Params.HEAT_REGION_EXPORT_LIST);
			}
			apiResult.setResult(path);
		} catch (UncheckedException e){
			log.error("[HeatApi.getRegionDetailListExport] [{0}]",e.getMessage());
			apiResult.setError(e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			log.error("[HeatApi.getRegionDetailListExport] [{0}]",e.getMessage());
			apiResult.setError(ErrorCode.API_PARAMS_ERROR);
		}
		return apiResult;
	}

	@ApiPath(RequestUrl.STATS_HEAT_OPERATOR_RATIO_LIST)
	public static ApiResult getOperatorRatioList(ApiRequest request, ApiUser user) {
		ApiResult apiResult = new ApiResult();
		try
		{	
			SearchBar bar = SearchBar.getBaseBar(request);
			Paging paging = getPaging(request);
			List<OperatorRatioBean> list = BUSINESS.getOperatorRatioList(bar);
			apiResult.setResult(list);
			apiResult.setPaging(paging);
		} catch (UncheckedException e){
			log.error("[HeatApi.getOperatorRatioList] [{0}]",e.getMessage());
			apiResult.setError(e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			log.error("[HeatApi.getOperatorRatioList] [{0}]",e.getMessage());
			apiResult.setError(ErrorCode.API_PARAMS_ERROR);
		}
		return apiResult;
	}

	@ApiPath(RequestUrl.STATS_HEAT_OPERATOR_RATIO_LIST_EXPORT)
	public static ApiResult getOperatorRatioListExport(ApiRequest request, ApiUser user) {
		ApiResult apiResult = new ApiResult();
		try
		{	
			SearchBar bar = SearchBar.getBaseBar(request);
			List<OperatorRatioBean> list = BUSINESS.getOperatorRatioList(bar);
			String path = "";
			if (ValidateUtils.isNotNull(list)) {
				path = ExportUtil.exportToCsv(list,Params.HEAT_OPERATOR_RATIO_EXPORT_LIST);
			}
			apiResult.setResult(path);
		} catch (UncheckedException e){
			log.error("[HeatApi.getOperatorRatioListExport] [{0}]",e.getMessage());
			apiResult.setError(e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			log.error("[HeatApi.getOperatorRatioListExport] [{0}]",e.getMessage());
			apiResult.setError(ErrorCode.API_PARAMS_ERROR);
		}
		return apiResult;
	}
	
	@ApiPath(RequestUrl.STATS_HEAT_HOURLY_CHART)
	public static ApiResult getHourlyChart(ApiRequest request, ApiUser user) {
		ApiResult apiResult = new ApiResult();
		try
		{	
			SearchBar bar = SearchBar.getBaseBar(request);
			List<ChartBean> list = BUSINESS.getHourlyChart(bar);
			apiResult.setResult(list);
		} catch (UncheckedException e){
			log.error("[HeatApi.getHourlyChart] [{0}]",e.getMessage());
			apiResult.setError(e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			log.error("[HeatApi.getHourlyChart] [{0}]",e.getMessage());
			apiResult.setError(ErrorCode.API_PARAMS_ERROR);
		}
		return apiResult;
	}
	
	@ApiPath(RequestUrl.STATS_HEAT_DAILY_CHART)
	public static ApiResult getDailyChart(ApiRequest request, ApiUser user) {
		ApiResult apiResult = new ApiResult();
		try
		{	
			SearchBar bar = SearchBar.getBaseBar(request);
			List<ChartBean> list = BUSINESS.getDailyChart(bar);
			apiResult.setResult(list);
		} catch (UncheckedException e){
			log.error("[HeatApi.getDailyChart] [{0}]",e.getMessage());
			apiResult.setError(e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			log.error("[HeatApi.getDailyChart] [{0}]",e.getMessage());
			apiResult.setError(ErrorCode.API_PARAMS_ERROR);
		}
		return apiResult;
	}
	
	
	
}
