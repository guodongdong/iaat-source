package com.nokia.ads.platform.backend.core.auth;


/**
 * 
 * @author kenliu
 * 
 */
public interface IAuthScope<TUser extends IAuthUser, TRequest extends IAuthRequest> {
	/**
	 * @param user
	 *            User object in HTTP session
	 * @param request
	 *            Api request made from HTTP request payload
	 * @return boolean true if user's object is in his scope; false otherwise
	 */
	boolean checkScope(TUser user, TRequest request, String paramKey);
}
