package com.iaat.webapi.bounce;

import java.util.List;

import com.iaat.business.bounce.BounceBusiness;
import com.iaat.business.factory.BusinessFactory;
import com.iaat.json.SearchBar;
import com.iaat.json.bounce.BounceDataBean;
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
 * @name BounceApi
 * 
 * @description customer return rate
 * 
 * MORE_INFORMATION
 * 
 * @author JackGuo
 * 
 * @since 2013-7-12
 *       
 * @version 1.0
 */
public class BounceApi extends  BaseWebApi implements ApiService{
	private final static Log log = Log.getLogger(BounceApi.class);
	
	@ApiPath(RequestUrl.ANALYSE_BOUNCE_RATE)
	public static ApiResult getBounceRate(ApiRequest request, ApiUser user) {
		log.info("[BounceApi.getBounceRate] [begin]");
		ApiResult apiResult = new ApiResult();
		try
		{
			SearchBar bar = SearchBar.getStatsTypeBar(request);
			BounceBusiness bounceBiz = BusinessFactory.getInstance(BounceBusiness.class);
//			List<BounceDataBean> results = bounceBiz.getBounceRate(bar);
			List<BounceDataBean> results = bounceBiz.getBounceRate(bar);
			apiResult.setResult(results);
		} catch (UncheckedException e){
			log.error("[BounceApi.getBounceRate] [{0}]",e.getMessage());
			apiResult.setError(e.getMessage());
		}catch (Exception e) {
			log.error("[BounceApi.getBounceRate] [{0}]",e.getMessage());
			apiResult.setError(ErrorCode.API_PARAMS_ERROR);
		}
		log.info("[AccessTrackApi.getInputUVCountApi] [end]");
		return apiResult;
	}
	
}
