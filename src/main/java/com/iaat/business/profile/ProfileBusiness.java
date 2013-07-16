package com.iaat.business.profile;

import java.util.List;

import com.iaat.json.SearchBar;
import com.iaat.json.profile.ProfileBean;
import com.iaat.json.profile.ProfileChartBean;


/**
 * 
 * @name ProfileBusiness
 * 
 * @description CLASS_DESCRIPTION
 * 
 * MORE_INFORMATION
 * 
 * @author zhaozhi
 * 
 * @since 2013-6-24
 *       
 * @version 1.0
 */
public interface ProfileBusiness {
	
	/**
	 * 
	 * getProfileBean(期间用户概况--table)   
	 * 
	 * @param startDateTime
	 * @param endDateTime
	 * @param startTime
	 * @param endTime
	 * @param channel
	 * @param platform
	 * @param AP
	 * @param logSource
	 * @param local1
	 * @param local2
	 * @param operator
	 * @param terminalType
	 * @return 
	 * 
	 * ProfileBean
	 */
	ProfileBean getProfileBean(SearchBar bar);
	
	List<ProfileChartBean> getUVChart(SearchBar bar);
	
	List<ProfileChartBean> getPVChart(SearchBar bar);
	
	List<ProfileChartBean> getIncreaseUserChart(SearchBar bar);
	
	List<ProfileChartBean> getOnceUserChart(SearchBar bar);
	
	List<ProfileChartBean> getOnlyLoginUserChart(SearchBar bar);
	
	List<ProfileChartBean> getUserLoginCountChart(SearchBar bar);

	List<ProfileChartBean> getUserAccessCountChart(SearchBar bar);

}
