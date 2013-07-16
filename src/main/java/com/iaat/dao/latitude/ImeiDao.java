package com.iaat.dao.latitude;

import java.util.List;

import com.iaat.model.ImeiBean;

/**    
 * @name ImeiDao
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
public interface ImeiDao {

	/**    
	 * getAllApBeans(get all imeis)   
	 * 
	 * @return 
	 * 
	 * List<ImeiBean>
	 */
	public List<ImeiBean> getAllImeiBeans(String regionName);
	
}
