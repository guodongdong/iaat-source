package com.iaat.business.quantitytrend;

import java.util.List;

import com.iaat.json.SearchBar;
import com.iaat.json.quantitytrend.QuantityTrendChartBean;
import com.iaat.json.quantitytrend.QuantityTrendTotalBean;
import com.nokia.ads.platform.backend.util.Paging;
 
public interface QuantityTrendBusiness {

	
	
	/**
	 * 根据searchbar查询时间段的PV、UV
	 * @param searchbar TODO
	 * @return
	 */
	public List<QuantityTrendChartBean> getQuantityTrendChart(SearchBar searchbar) throws Exception;

	
	/**
	 * 根据searchbar查询时间段的PV、UV、新用户数
	 * @param searchbar TODO
	 * @param paging TODO
	 * @return
	 */
	public QuantityTrendTotalBean getQuantityTrendList(SearchBar searchbar, Paging paging) throws Exception;

 
}
