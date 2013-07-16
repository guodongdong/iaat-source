package com.iaat.business.retention;

import java.util.List;

import com.iaat.json.SearchBar;

/**
 * 
 * @name RetentionBusiness 
 * 
 * @description  留存业务服务类 
 * 
 * MORE_INFORMATION
 * 
 * @author Lixw
 * 
 * @since 2013-6-28
 *       
 * @version 1.0
 */
public interface RetentionBusiness {
	 
	/**
	 * 统计所有留存率
	 * @param searchBar
	 * @return
	 */
	List<?> getRetentionAll(SearchBar searchBar);
	
}
