package com.iaat.webapi.relationship;

import java.util.List;

import com.iaat.business.factory.BusinessFactory;
import com.iaat.business.relationship.RelationshipBusiness;
import com.iaat.json.SearchBar;
import com.iaat.json.relationship.RelationshipBean;
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
 * @name RelationshipApi
 * 
 * @description CLASS_DESCRIPTION
 * 
 * MORE_INFORMATION
 * 
 * @author lixw
 * 
 * @since 2013-7-11
 *       
 * @version 1.0
 */
public class RelationshipApi extends BaseWebApi implements ApiService {

	
	private final static Log log = Log.getLogger(RelationshipApi.class);
	
	@ApiPath(RequestUrl.ANALYSE_RELATIONSHIP_CHART)
	public static ApiResult getAllRelationshipApi(ApiRequest request, ApiUser user) {
		log.info("[RelationshipApi.getAllRelationshipApi] [begin]");
		ApiResult apiResult = new ApiResult();
		try
		{
			SearchBar bar = SearchBar.getBaseBar(request);
			RelationshipBusiness relationshipBusiness = BusinessFactory.getInstance(RelationshipBusiness.class);
			List<RelationshipBean> relationbeans= relationshipBusiness.getRelationshipAll(bar);
			apiResult.setResult(relationbeans);
		} catch (UncheckedException e){
			log.error("[RelationshipApi.getAllRelationshipApi] [{0}]",e.getMessage());
			apiResult.setError(e.getMessage());
		}catch (Exception e) {
			log.error("[RelationshipApi.getAllRelationshipApi] [{0}]",e.getMessage());
			apiResult.setError(ErrorCode.API_PARAMS_ERROR);
		}
		log.info("[RelationshipApi.getAllRelationshipApi] [end]");
		return apiResult;
	}
}
