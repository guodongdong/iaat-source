package com.iaat.dao.latitude;

import java.util.List;

import com.iaat.model.RegionBean;

/**    
 * @name RegionDao
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
public interface RegionDao {

	/**    
	 * getAllRegionBeans(get all regions)   
	 * 
	 * @return 
	 * 
	 * List<RegionBean>
	 */
	public List<RegionBean> getAllRegionBeans(String regionName);
	
	/**
	 * 
	 * getAllProvinBeans(get all the provinces)   
	 * 
	 * 
	 * @param regionName
	 * @return 
	 * 
	 * List<RegionBean>
	 */
	public List<RegionBean> getAllProvinBeans(String regionName);
	
}
