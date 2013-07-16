package com.iaat.dao.profile;

import java.util.List;

import com.iaat.json.SearchBar;
import com.iaat.json.profile.ProfileBean;
import com.iaat.json.profile.ProfileChartBean;

/**
 * 
 * @name ProfileDao
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
public interface ProfileDao {
	
	ProfileBean getProfile(SearchBar bar);
	
	int getPV(SearchBar bar);
	
	int getUV(SearchBar bar);
	
	int getIncreaseUser(SearchBar bar);
	
	int getOnceUser(SearchBar bar);
	
	int getOnlyLoginUser(SearchBar bar);
	
	float getAvgUserAccess(SearchBar bar);
	
	float getAvgUserLogin(SearchBar bar);
	
	float getAvgLastTime(SearchBar bar);
	
	List<ProfileChartBean> getPVChart(SearchBar bar);
	
	List<ProfileChartBean> getUVChart(SearchBar bar);
	
	List<ProfileChartBean> getIncreaseUserChart(SearchBar bar);
	
	List<ProfileChartBean> getOnceUserChart(SearchBar bar);
	
	List<ProfileChartBean> getOnlyLoginUserChart(SearchBar bar);
	
	List<ProfileChartBean> getUserLoginCountChart(SearchBar bar);

	List<ProfileChartBean> getUserAccessCountChart(SearchBar bar);
	
//	int getSingleImeiMaxLogin(SearchBar bar);
//
//	int getSingleImeiMinLogin(SearchBar bar);
//	
//	int getSingleImeiMaxAccess(SearchBar bar);
//	
//	int getSingleImeiMinAccess(SearchBar bar);
	
	int getLoginImeiCount(SearchBar bar);
	
	int getAccessImeiCount(SearchBar bar);
	
	
	
}
