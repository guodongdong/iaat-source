package com.iaat.webapi.stats;
import java.util.List;
import com.iaat.business.factory.BusinessFactory;
import com.iaat.business.stats.ImeiBusiness;
import com.iaat.json.SearchBar;
import com.iaat.model.ImeiAccess;
import com.iaat.model.LogSourceType;
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
import com.nokia.ads.platform.backend.util.Paging;
public class ImeiApi extends BaseWebApi implements ApiService{

	private final static Log log = Log.getLogger(ImeiApi.class);
	private static ImeiBusiness bis = BusinessFactory.getInstance(ImeiBusiness.class);
	/**
	 * 饼状图
	 * @param request
	 * @param user
	 * @return
	 */
	@ApiPath(RequestUrl.STATS_LIFE_OF_P_IMEI_LIST)
	public static ApiResult getImeis(ApiRequest request, ApiUser user) {
		ApiResult apiResult = new ApiResult();
		try
		{
			SearchBar bar = SearchBar.getBaseBar(request);
			Paging paging = getPaging(request);
			String key = request.getParam("key",String.class);
			LogSourceType log = getLog(bar);
			List<ImeiAccess> data = bis.getImeiList(bar, log, key, paging);
			apiResult.setPaging(paging);
			apiResult.setResult(data);
		} catch (UncheckedException e){
			log.error("[ImeiApi.getImeis] [{0}]",e.getMessage());
			apiResult.setError(e.getMessage()); 
		}catch (Exception e) {
			log.error("[ImeiApi.getImeis] [{0}]",e.getMessage());
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
