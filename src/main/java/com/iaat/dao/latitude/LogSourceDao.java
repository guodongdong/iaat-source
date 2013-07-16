package com.iaat.dao.latitude;

import java.util.List;

import com.iaat.model.LogSourceBean;

/**    
 * @name LogSourceDao
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
public interface LogSourceDao {

	/**    
	 * getAllApBeans(get all logSources)   
	 * 
	 * @return 
	 * 
	 * List<LogSourceBean>
	 */
	public List<LogSourceBean> getAllLogSourceBeans();
	
}
