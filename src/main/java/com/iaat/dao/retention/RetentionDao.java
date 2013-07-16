package com.iaat.dao.retention;

import java.util.List;
import java.util.Map;

import com.iaat.json.SearchBar;
import com.iaat.json.retention.RetentionBean;

/**
 * 
 * @name RetentionDao 
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
public interface RetentionDao {

	/**
	 * 统计所有留存率
	 * @param searchBar
	 * @return
	 */
	List<RetentionBean> getRetentionAll(SearchBar searchBar);
	
	
	public List<RetentionBean> getRetentionSum(SearchBar searchBar);
	
}
