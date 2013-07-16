package com.iaat.business.heat;

import java.util.List;
import java.util.Map;

import com.iaat.json.ChartBean;
import com.iaat.json.SearchBar;
import com.iaat.json.heat.HeatRegionBean;
import com.iaat.json.heat.OperatorRatioBean;
import com.iaat.json.heat.RegionChartBean;

/**
 * 
 * @name HeatBusiness
 * 
 * @description 用户热力分布图
 * 
 * MORE_INFORMATION
 * 
 * @author zhaozhi
 * 
 * @since 2013-7-2
 *       
 * @version 1.0
 */
public interface HeatBusiness {
	
	/**
	 * 
	 * getNationMapChart(中国地图上的地区数据)   
	 * 
	 * @param bar
	 * @return 
	 * 
	 * List<RegionChartBean>
	 */
	public List<RegionChartBean> getNationMapChart(SearchBar bar);
	
	/**
	 * 
	 * getRegionDetailList(地区数据列表)   
	 * 
	 * @param bar
	 * @return 
	 * 
	 * List<HeatRegionBean>
	 */
	public Map<String, List<HeatRegionBean>> getRegionDetailList(SearchBar bar);

	public List<HeatRegionBean> getRegionDetailListExport(SearchBar bar);
	
	/**
	 * 
	 * getOperatorRatioList(运营商数据列表)   
	 * 
	 * @param bar
	 * @return 
	 * 
	 * List<OperatorRatioBean>
	 */
	public List<OperatorRatioBean> getOperatorRatioList(SearchBar bar);
	
	/**
	 * 
	 * getHourlyChart(热力小时分布图)   
	 * 
	 * @param bar
	 * @return 
	 * 
	 * List<ChartBean>
	 */
	public List<ChartBean> getHourlyChart(SearchBar bar);
	
	/**
	 * 
	 * getDailyChart(热力天分布图)   
	 * 
	 * @param bar
	 * @return 
	 * 
	 * List<ChartBean>
	 */
	public List<ChartBean> getDailyChart(SearchBar bar);
	
}
