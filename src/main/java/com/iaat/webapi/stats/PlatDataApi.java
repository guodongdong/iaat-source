package com.iaat.webapi.stats;
import java.util.List;
import java.util.Map;
import com.iaat.business.factory.BusinessFactory;
import com.iaat.business.stats.PlatBusiness;
import com.iaat.json.SearchBar;
import com.iaat.model.LogSourceType;
import com.iaat.model.PlatBean;
import com.iaat.model.PlatformObj;
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
public class PlatDataApi extends BaseWebApi implements ApiService {

	private final static Log log = Log.getLogger(PlatDataApi.class);
	private static PlatBusiness bis = BusinessFactory.getInstance(PlatBusiness.class);
	/**
	 * 饼状图
	 * @param request
	 * @param user
	 * @return
	 */
	@ApiPath(RequestUrl.STATS_PLAT_LIST_PIE)
	public static ApiResult getPlatPie(ApiRequest request, ApiUser user) {
		ApiResult apiResult = new ApiResult();
		try
		{
			SearchBar bar = SearchBar.getBaseBar(request);
			LogSourceType log = getLog(bar);
			List<PlatBean> beans= bis.getPlatUsers(bar,log);
			apiResult.setResult(beans);
		} catch (UncheckedException e){
			log.error("[PlatDataApi.getPlatPie] [{0}]",e.getMessage());
			apiResult.setError(e.getMessage()); 
		}catch (Exception e) {
			log.error("[PlatDataApi.getPlatPie] [{0}]",e.getMessage());
			apiResult.setError(ErrorCode.API_PARAMS_ERROR);
		}
		return apiResult;
	}
	/**
	 * 曲线图
	 * @param request
	 * @param user
	 * @return
	 */
	@ApiPath(RequestUrl.STATS_PLAT_LIST_LINE)
	public static ApiResult getPlatLine(ApiRequest request, ApiUser user) {
		ApiResult apiResult = new ApiResult();
		try
		{
			SearchBar bar = SearchBar.getBaseBar(request);
			String type = request.getParam(Params.STATS_TYPE,String.class);
			LogSourceType log = getLog(bar);
			Map<String,List<PlatBean>> beans= bis.getPlatLine(bar,type,log);
			apiResult.setResult(beans);
		} catch (UncheckedException e){
			log.error("[PlatDataApi.getPlatLine] [{0}]",e.getMessage());
			apiResult.setError(e.getMessage());
		}catch (Exception e) {
			log.error("[PlatDataApi.getPlatLine] [{0}]",e.getMessage());
			apiResult.setError(ErrorCode.API_PARAMS_ERROR);
		}
		return apiResult;
	}
	/**
	 * 列表
	 * @param request
	 * @param user
	 * @return
	 */
	@ApiPath(RequestUrl.STATS_PLAT_LIST)
	public static ApiResult getPlatList(ApiRequest request, ApiUser user) {
		ApiResult apiResult = new ApiResult();
		try
		{
			SearchBar bar = SearchBar.getBaseBar(request);
			Paging paging = getPaging(request);
			LogSourceType log = getLog(bar);
			PlatformObj bean= bis.getPlatList(bar,log,paging);
			apiResult.setPaging(paging);
			apiResult.setResult(bean);
		} catch (UncheckedException e){
			log.error("[PlatDataApi.getPlatList] [{0}]",e.getMessage());
			apiResult.setError(e.getMessage());
		}catch (Exception e) {
			log.error("[PlatDataApi.getPlatList] [{0}]",e.getMessage());
			apiResult.setError(ErrorCode.API_PARAMS_ERROR);
		}
		return apiResult;
	}
	//获取日志来源
	private static LogSourceType getLog(SearchBar bar){
		if(LogSourceType.XPRESS.name().equalsIgnoreCase(bar.getLogSource()))return LogSourceType.XPRESS;
		return LogSourceType.TOM;
	}
	
}
