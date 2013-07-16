package com.iaat.dao.latitude;

import java.util.List;

import com.iaat.model.TerminalBean;

/**    
 * @name TerminalDao
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
public interface TerminalDao {

	/**    
	 * getAllApBeans(get all terminals)   
	 * 
	 * @return 
	 * 
	 * List<TerminalBean>
	 */
	public List<TerminalBean> getAllTerminalBeans(String regionName);
	
	
	/**    
	 * getAllTerminalDisBeans(get all distinct terminals)   
	 * 
	 * @param regionName
	 * @return 
	 * 
	 * List<TerminalBean>
	 */
	public List<TerminalBean> getAllTerminalDisBeans(String regionName);
	
}
