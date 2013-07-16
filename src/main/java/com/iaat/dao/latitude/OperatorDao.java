package com.iaat.dao.latitude;

import java.util.List;

import com.iaat.model.OperatorBean;

/**    
 * @name OperatorDao
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
public interface OperatorDao {

	/**    
	 * getAllApBeans(get all operatores)   
	 * 
	 * @return 
	 * 
	 * List<OperatorBean>
	 */
	public List<OperatorBean> getAllOperatorBeans(String regionName);
	
}
