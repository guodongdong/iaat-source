package com.iaat.webapi;

import com.iaat.util.ValidateUtils;
import com.nokia.ads.platform.backend.core.auth.IAuthScope;
import com.nokia.ads.platform.backend.core.webapi.ApiRequest;
import com.nokia.ads.platform.backend.core.webapi.ApiUser;

/**
 * @name UserAuthScope
 * 
 * @description CLASS_DESCRIPTION
 * 
 *              MORE_INFORMATION
 * 
 * @author Eric
 * 
 * @since 2011-1-18
 * 
 * @version 1.0
 */
public class UserAuthScope implements IAuthScope<ApiUser, ApiRequest> {
	
	@Override
	public boolean checkScope(ApiUser user, ApiRequest request, String arg2)
	{
		String userIdStr = user.getUserId();
		
		if (!ValidateUtils.isNotNull(userIdStr)||"null".equalsIgnoreCase(userIdStr)) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
	
}