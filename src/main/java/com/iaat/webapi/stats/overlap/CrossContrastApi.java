package com.iaat.webapi.stats.overlap;

import java.util.List;

import com.iaat.business.factory.BusinessFactory;
import com.iaat.business.overlap.CrossContrastBusiness;
import com.iaat.json.overlap.ContrastResultBean;
import com.iaat.json.overlap.ContrastSearchBean;
import com.iaat.json.overlap.CrossDataBean;
import com.iaat.share.ErrorCode;
import com.iaat.share.Params;
import com.iaat.share.RequestUrl;
import com.iaat.webapi.BaseWebApi;
import com.iaat.webapi.analyse.track.AccessTrackApi;
import com.nokia.ads.common.util.Log;
import com.nokia.ads.platform.backend.annotation.ApiPath;
import com.nokia.ads.platform.backend.core.UncheckedException;
import com.nokia.ads.platform.backend.core.webapi.ApiRequest;
import com.nokia.ads.platform.backend.core.webapi.ApiResult;
import com.nokia.ads.platform.backend.core.webapi.ApiService;
import com.nokia.ads.platform.backend.core.webapi.ApiUser;

public class CrossContrastApi extends BaseWebApi implements ApiService{
	private final static Log log = Log.getLogger(AccessTrackApi.class);
	@ApiPath(RequestUrl.STATS_CROSS_CONTRAST_CASE_LIST)
	public static ApiResult caseContrastList(ApiRequest request,ApiUser apiUser){
		log.info("[CrossContrastApi.caseContrastList] [begin]");
		ApiResult apiResult = new ApiResult();
		try{
			ContrastSearchBean[] resultBean = request.getParam(Params.SEARCH_CONSTRAST, ContrastSearchBean[].class);
			ContrastResultBean searchBean = request.getParam(Params.RESULT_CONSTRAST, ContrastResultBean.class);
			CrossContrastBusiness crossContrastBiz = BusinessFactory.getInstance(CrossContrastBusiness.class);
			List<CrossDataBean> result = crossContrastBiz.getCorssConstrasts(resultBean,searchBean);
			apiResult.setResult(result);
		} catch (UncheckedException e){
			log.error("[CrossContrastApi.caseContrastList] [{0}]",e.getMessage());
			apiResult.setError(e.getMessage());
		}catch (Exception e) {
			log.error("[CrossContrastApi.caseContrastList] [{0}]",e.getMessage());
			apiResult.setError(ErrorCode.API_PARAMS_ERROR);
		}
			log.info("[CrossContrastApi.caseContrastList] [end]");
		return apiResult;
	}
	/*public static ApiResult caseContrastList(ApiRequest request,ApiUser apiUser){
		log.info("[CrossContrastApi.caseContrastList] [begin]");
		ApiResult apiResult = new ApiResult();
		try{
			List<CrossDataBean> crossResultDatas = new ArrayList<CrossDataBean>();
//			List<CrossOuterDataBean> outerDatas = new ArrayList<CrossOuterDataBean>();
			List<CrossCaseBean> logUser = new ArrayList<CrossCaseBean>();
			CrossCaseBean crossCase = new CrossCaseBean();
			crossCase.setName("case1");
			crossCase.setY(10d);
			CrossCaseBean crossCase2 = new CrossCaseBean();
			crossCase2.setName("case2");
			crossCase2.setY(11d);
			logUser.add(crossCase);
			logUser.add(crossCase2);
			List<CrossCaseBean> avgUser = new ArrayList<CrossCaseBean>();
			CrossCaseBean avgCrossCase = new CrossCaseBean();
			avgCrossCase.setName("case1");
			avgCrossCase.setY(10.5);
			CrossCaseBean avgCrossCase2 = new CrossCaseBean();
			avgCrossCase2.setName("case2");
			avgCrossCase2.setY(11.5d);
			avgUser.add(avgCrossCase);
			avgUser.add(avgCrossCase2);
			
			
			CrossDataBean actData = new CrossDataBean();
			actData.setTitle(CrossContrastType.ACTIVEUSER.name());
			actData.setData(logUser);
			crossResultDatas.add(actData);
			
			
			CrossDataBean avgData = new CrossDataBean();
			avgData.setTitle(CrossContrastType.AVGACCESSUSER.name());
			avgData.setData(avgUser);
			crossResultDatas.add(avgData);
//			outerDatas.add(avgCross);
			apiResult.setResult(crossResultDatas);
		} catch (UncheckedException e){
			log.error("[CrossContrastApi.caseContrastList] [{0}]",e.getMessage());
			apiResult.setError(e.getMessage());
		}catch (Exception e) {
			log.error("[CrossContrastApi.caseContrastList] [{0}]",e.getMessage());
			apiResult.setError(ErrorCode.API_PARAMS_ERROR);
		}
		log.info("[CrossContrastApi.caseContrastList] [end]");
		return apiResult;
	}*/

}
