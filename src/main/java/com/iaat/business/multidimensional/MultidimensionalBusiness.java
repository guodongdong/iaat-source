package com.iaat.business.multidimensional;

import java.util.List;
import java.util.Map;

import com.iaat.json.SearchBar;
import com.iaat.json.profile.ProfileBean;

/**
 * 
 * @name MultidimensionalBusiness
 * 
 * @description CLASS_DESCRIPTION
 * 
 * MORE_INFORMATION
 * 
 * @author zhaozhi
 * 
 * @since 2013-7-10
 *       
 * @version 1.0
 */
public interface MultidimensionalBusiness {
	
	List<ProfileBean> getChart(SearchBar bar);
	
	Map<String, List<ProfileBean>> getList(SearchBar bar);
	
}
