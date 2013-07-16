package com.iaat.webapi.stats;

import java.util.List;

import com.iaat.business.factory.BusinessFactory;
import com.iaat.business.quantitytrend.QuantityTrendBusiness;
import com.iaat.json.SearchBar;
import com.iaat.json.quantitytrend.QuantityTrendChartBean;
import com.iaat.json.quantitytrend.QuantityTrendTotalBean;
import com.iaat.share.ErrorCode;
import com.iaat.share.Params;
import com.iaat.share.RequestUrl;
import com.iaat.util.ExportUtil;
import com.iaat.webapi.BaseWebApi;
import com.nokia.ads.common.util.Log;
import com.nokia.ads.platform.backend.annotation.ApiPath;
import com.nokia.ads.platform.backend.core.business.Result;
import com.nokia.ads.platform.backend.core.webapi.ApiRequest;
import com.nokia.ads.platform.backend.core.webapi.ApiResult;
import com.nokia.ads.platform.backend.core.webapi.ApiService;
import com.nokia.ads.platform.backend.core.webapi.ApiUser;
import com.nokia.ads.platform.backend.util.Paging;

public class QuantityTrendApi extends BaseWebApi implements ApiService {

	private final static Log log = Log.getLogger(QuantityTrendApi.class);

	private static QuantityTrendBusiness quantityTrendBusiness = BusinessFactory.getInstance(QuantityTrendBusiness.class);
	
	/**
	 * 根据searchbar查询用户PV、UV曲线图
	 * @param request
	 * @param user
	 * @return
	 */
	@ApiPath(RequestUrl.STATS_QUANTITY_TREND_CHART)
	public static ApiResult getQuantityTrendChart(ApiRequest request,ApiUser user){
		log.info("[QuantityTrendApi.getQuantityTrendChart] [begin]");
		ApiResult apiResult = null;
		Result result = new Result();
		try{
			SearchBar bar = SearchBar.getStatsTypeBar(request);
			List<QuantityTrendChartBean>  list =  quantityTrendBusiness.getQuantityTrendChart(bar);
			result.setData(list);
		 	apiResult = getResult(result);
		}catch(Exception e){
			log.error("[QuantityTrendApi.getQuantityTrendChart] [ {0} ]",e.getMessage());
			apiResult = getResult(ErrorCode.UNKONWN_ERROR);
			
		}finally{
			log.info("[QuantityTrendApi.getQuantityTrendChart] [end]");
		}
		return apiResult;
	}
	
	/**
	 * 根据searchbar查询用户PV、UV、新用户数 列表
	 * @param request
	 * @param user
	 * @return
	 */
	@ApiPath(RequestUrl.STATS_QUANTITY_TREND_LIST)
	public static ApiResult getQuantityTrendList(ApiRequest request,ApiUser user){
		log.info("[QuantityTrendApi.getQuantityTrendList] [begin]");
		ApiResult apiResult = null;
		try{
			SearchBar bar = SearchBar.getStatsTypeBar(request);
			Paging paging = getPaging(request);
			QuantityTrendTotalBean obj =  quantityTrendBusiness.getQuantityTrendList(bar, paging);
			Result<QuantityTrendTotalBean> result = new Result<QuantityTrendTotalBean>();
			result.setData(obj);
			result.setPaging(paging);
		 	apiResult = getResult(result);
		}catch(Exception e){
			log.error("[QuantityTrendApi.getQuantityTrendList] [ {0} ]",e.getMessage());
			apiResult = getResult(ErrorCode.UNKONWN_ERROR);
			
		}finally{
			log.info("[QuantityTrendApi.getQuantityTrendList] [end]");
		}
		return apiResult;
	}
	
	/**
	 * 根据searchbar查询用户PV、UV、新用户数 列表 导出到CSV
	 * @param request
	 * @param user
	 * @return
	 */
	@ApiPath(RequestUrl.STATS_QUANTITY_TREND_LIST_EXPORT)
	public static ApiResult getQuantityTrendListExport(ApiRequest request,ApiUser user){
		log.info("[QuantityTrendApi.getQuantityTrendListExport] [begin]");
		Result<String> result = new Result<String>();
		try{
			SearchBar bar = SearchBar.getStatsTypeBar(request);
			Paging paging = getPaging(request);
			QuantityTrendTotalBean total =  quantityTrendBusiness.getQuantityTrendList(bar, paging);
			String path;
			if(bar.getDateEqual()){
				path = ExportUtil.exportToCsv(total.getList(),Params.QUANTITYTREND_EXPORT_LIST_INDEX1);	
			}else{
				path = ExportUtil.exportToCsv(total.getList(),Params.QUANTITYTREND_EXPORT_LIST_INDEX2);
			}
			result.setData(path);
			result.setPaging(paging);
		}catch(Exception e){
			log.error("[QuantityTrendApi.getQuantityTrendListExport] [ {0} ]",e.getMessage());
			return getResult(ErrorCode.UNKONWN_ERROR); 
		}finally{
			log.info("[QuantityTrendApi.getQuantityTrendListExport] [end]");
		}
		return  getResult(result);
	}
	
}
