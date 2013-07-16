package com.iaat.dao.quantitytrend;

import java.util.List;

import com.iaat.json.SearchBar;
import com.iaat.json.quantitytrend.QuantityTrendChartBean;
import com.iaat.json.quantitytrend.QuantityTrendListBean;
import com.iaat.json.quantitytrend.QuantityTrendTotalBean;
import com.nokia.ads.platform.backend.util.Paging;

public interface QuantityTrendDao {
	
	
	/**
	 * 根据searchbar获得每天的PV、UV
	 * @param searchbar
	 * @return
	 * @throws Exception
	 */
	public List<QuantityTrendChartBean> getQuantityTrendChartByDaily(SearchBar searchbar) throws Exception;
	
	/**
	 * 根据searchbar获得每周的PV、UV
	 * @param searchbar
	 * @return
	 * @throws Exception
	 */
	public List<QuantityTrendChartBean> getQuantityTrendChartByWeekly(SearchBar searchbar) throws Exception;
	
	/**
	 * 根据searchbar获得每月的PV、UV
	 * @param searchbar
	 * @return
	 * @throws Exception
	 */
	public List<QuantityTrendChartBean> getQuantityTrendChartByMonthly(SearchBar searchbar) throws Exception;
	
	
	
	
	/**
	 * 根据searchbar查询时间段的PV、UV、新用户数
	 * @param searchbar TODO
	 * @param paging TODO
	 * @return
	 */
	public List<QuantityTrendListBean> getQuantityTrendListAll(SearchBar searchbar, Paging paging) throws Exception;

	
	/**
	 * 统计所有WapPv、WebPV 和 新用户
	 * @param bar
	 * @return
	 */
	public QuantityTrendTotalBean  getQuantityTrendTotalAll(SearchBar bar);
	
	
	/**
	 * 按天统计用户访问 PV、UV、新增用户数
	 * @param searchbar
	 * @param paging TODO
	 * @return
	 */
	public List<QuantityTrendListBean> getQuantityTrendGroupDayList(SearchBar searchbar, Paging paging) throws Exception;
	
	/**
	 * 新增用户数
	 * @param bar
	 * @return
	 */
//	public List<QuantityTrendListBean> getIncreaseUser(SearchBar bar);
}
