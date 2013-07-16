package com.iaat.dao.latitude;

import java.util.List;

import com.iaat.model.ApBean;

/**    
 * @name ApDao
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
public interface ApDao {

	/**    
	 * getAllApBeans(get all ap)   
	 * 
	 * 
	 * @param regionName
	 * @return 
	 * 
	 * List<ApBean>
	 */
	public List<ApBean> getAllApBeans(String regionName);
	
	/**    
	 * getOperatorApBeans(get all ap order by operator_fk and sequence)   
	 * 
	 * 
	 * @param regionName
	 * @return 
	 * 
	 * List<ApBean>
	 */
	public List<ApBean> getOperatorApBeans(String regionName);
	
	/**    
	 * getDistinctApBeans(get all distinct ap datas)   
	 * 
	 * @param regionName
	 * @return 
	 * 
	 * List<ApBean>
	 */
	public List<ApBean> getDistinctApBeans(String regionName);
}
