package com.iaat.dao.latitude;

import java.util.List;

import com.iaat.model.ChannelBean;

/**    
 * @name ChannelDao
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
public interface ChannelDao {
	
	/**    
	 * getAllApBeans(get all channels)   
	 * 
	 * @return 
	 * 
	 * List<ChannelBean>
	 */
	public List<ChannelBean> getAllChannelBeans(String regionName);

}
