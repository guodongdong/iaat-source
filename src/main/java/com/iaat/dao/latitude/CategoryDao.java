package com.iaat.dao.latitude;

import java.util.List;

import com.iaat.model.CategoryBean;

/**    
 * @name CategoryDao
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
public interface CategoryDao {

	/**    
	 * getAllCategoryBeans(get all categories)   
	 * 
	 * @return 
	 * 
	 * List<CategoryBean>
	 */
	public List<CategoryBean> getAllCategoryBeans(String regionName);
}
