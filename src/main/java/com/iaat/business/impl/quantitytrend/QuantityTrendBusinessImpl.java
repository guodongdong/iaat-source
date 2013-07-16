package com.iaat.business.impl.quantitytrend;

import java.util.Calendar;
import java.util.List;

import com.iaat.business.quantitytrend.QuantityTrendBusiness;
import com.iaat.dao.factory.DaoFactory;
import com.iaat.dao.quantitytrend.QuantityTrendDao;
import com.iaat.json.SearchBar;
import com.iaat.json.quantitytrend.QuantityTrendChartBean;
import com.iaat.json.quantitytrend.QuantityTrendListBean;
import com.iaat.json.quantitytrend.QuantityTrendTotalBean;
import com.iaat.util.DateUtil;
import com.iaat.util.NumberUtil;
import com.nokia.ads.common.util.Log;
import com.nokia.ads.platform.backend.util.Paging;


/**
 * 
 * @name QuantityTrendBusinessImpl
 * 
 * @description CLASS_DESCRIPTION
 * 
 * MORE_INFORMATION
 * 
 * @author Lixw
 * 
 * @since 2013-6-24
 *       
 * @version 1.0
 */
public class QuantityTrendBusinessImpl implements QuantityTrendBusiness {

	private QuantityTrendDao quantityTrendDao = DaoFactory.getInstance(QuantityTrendDao.class);
	
	private final static Log log = Log.getLogger(QuantityTrendBusinessImpl.class);

	
	/* 
	 * 按日、周、月统计PV、UV数据
	 * (non-Javadoc)
	 * @see com.iaat.business.quantitytrend.QuantityTrendBusiness#getQuantityTrendChart(com.iaat.json.SearchBar)
	 */
	@Override
	public List<QuantityTrendChartBean> getQuantityTrendChart(SearchBar searchbar) throws Exception{
		try {
			
			log.info("[QuantityTrendBusinessImpl.getQuantityTrendChart] [begin]");
			List<QuantityTrendChartBean> list = null ;
			//按天统计数据，如果开始时间和结束时间在一天 就按小时统计
			if(searchbar.getType().equals(SearchBar.DAILY)){
				 list =   quantityTrendDao.getQuantityTrendChartByDaily(searchbar);
				 for(QuantityTrendChartBean bean : list){
					 if(searchbar.getDateEqual()){
						 bean.setTag(NumberUtil.getFormatData(bean.getHour())+":00");
					 }else{
						 bean.setTag(DateUtil.date2String(bean.getTime()));
					 }
					}
			}else {
				list =   quantityTrendDao.getQuantityTrendChartByWeekly(searchbar);
				for(QuantityTrendChartBean bean : list){
					bean.setTag(DateUtil.date2String(bean.getStartTime(),"yyyy-MM-dd")
							+ " ~ "
							+ DateUtil.date2String(bean.getEndTime(),"yyyy-MM-dd"));
				}
				
			}
			return list;
		} catch (Exception e) {
			log.error("[QuantityTrendBusinessImpl.getQuantityTrendChart] [{0}]",e.getMessage());
		}finally{
			log.info("[QuantityTrendBusinessImpl.getQuantityTrendChart] [end]");
		}
		return null;
	}
	 
 
	
	/* 
	 * 统计总Webpv 总Wappv 和 总 新用户 
	 * (non-Javadoc)
	 * @see com.iaat.business.quantitytrend.QuantityTrendBusiness#getQuantityTrendList(com.iaat.json.SearchBar, com.nokia.ads.platform.backend.util.Paging)
	 */
	public QuantityTrendTotalBean getQuantityTrendList(SearchBar searchbar, Paging paging) throws Exception {
		List<QuantityTrendListBean> list = null;
		//如果开始时间和结束时间在同一天则按小时统计
		list = quantityTrendDao.getQuantityTrendListAll(searchbar, paging);
		for (QuantityTrendListBean bean : list) {
			if(SearchBar.DAILY.equals(searchbar.getType())){
				if (searchbar.getDateEqual()) {
					Calendar c = searchbar.getStartDateTime();
					c.set(Calendar.HOUR_OF_DAY, bean.getHour());
					bean.setPeriod(DateUtil.calendar2String(c, "HH:00-HH:59"));
				} else {
					// 如果是周六或周日则显示出星期格式
					if (bean.getWeek() == 1 || bean.getWeek() == 7) {
						bean.setPeriod(DateUtil.date2String(bean.getTime(),
								"yyyy-MM-dd E"));
					} else {
						bean.setPeriod(DateUtil.date2String(bean.getTime()));
					}
				}
			}

		}
		//返回给前端数据
		QuantityTrendTotalBean total = quantityTrendDao.getQuantityTrendTotalAll(searchbar);
		total.setList(list);
		return total;
	}

}
