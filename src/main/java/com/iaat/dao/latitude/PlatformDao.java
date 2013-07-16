package com.iaat.dao.latitude;

import java.util.List;

import com.iaat.model.PlatformBean;

/**    
 * @name PlatformDao
 * 
 * @description CLASS_DESCRIPTION
 * 
 * MORE_INFORMATION
 * 
 * @author JackGuo
 * 
 * @since 2013-6-24
 *       
 * @version 1.0
 */
public interface PlatformDao {

	/**    
	 * getAllApBeans(get all platforms)   
	 * 
	 * @return 
	 * 
	 * List<PlatformBean>
	 */
	public List<PlatformBean> getAllPlatformBeans(String regionName);
	
}
