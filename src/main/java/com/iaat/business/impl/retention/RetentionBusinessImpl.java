package com.iaat.business.impl.retention;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.iaat.business.retention.RetentionBusiness;
import com.iaat.dao.factory.DaoFactory;
import com.iaat.dao.retention.RetentionDao;
import com.iaat.json.SearchBar;
import com.iaat.json.retention.RetentionBean;
import com.iaat.json.retention.RetentionValuesBean;
import com.iaat.share.ErrorCode;
import com.iaat.util.DateUtil;
import com.iaat.util.NumberUtil;
import com.nokia.ads.common.util.Log;
import com.nokia.ads.platform.backend.core.UncheckedException;

/**
 * 
 * @name RetentionBusinessImpl
 * 
 * @description CLASS_DESCRIPTION
 * 
 *              MORE_INFORMATION
 * 
 * @author Lixw
 * 
 * @since 2013-6-28
 * 
 * @version 1.0
 */
public class RetentionBusinessImpl implements RetentionBusiness {

	private final static Log log = Log.getLogger(RetentionBusinessImpl.class);

	private final static RetentionDao dao = DaoFactory
			.getInstance(RetentionDao.class);

	
	/**
	 * 格式显示的时间
	 * @param searchbar
	 * @param b
	 * @return
	 */
	public String getTag(SearchBar searchbar,RetentionBean b){
		//按天统计数据，如果开始时间和结束时间在一天 就按小时统计
		if(searchbar.getType().equals(SearchBar.DAILY)){
				 if(searchbar.getDateEqual()){
					return NumberUtil.getFormatData(b.getHour())+":00";
				 }else{
					 return DateUtil.date2String(b.getTime());
				 }
		}else {
			return DateUtil.date2String(b.getBeginDate())
						+ " ~ "
						+ DateUtil.date2String(b.getEndDate());
			
		}
	}
	
	/* 
	 * 获得用户留存率
	 * (non-Javadoc)
	 * @see com.iaat.business.retention.RetentionBusiness#getRetentionAll(com.iaat.json.SearchBar)
	 */
	@Override
	public List<?> getRetentionAll(SearchBar searchBar) {
		log.info("[RetentionBusinessImpl.getRetentionAll] [begin]");
		//获得所有留存数据
		List<RetentionBean> result = null;
		try {
			result = dao.getRetentionAll(searchBar);
			Map<String,RetentionBean> map = new HashMap<String, RetentionBean>();
			
			//根据日期获得留存的总和
			List<RetentionBean> retentBeans = dao.getRetentionSum(searchBar);
			
			RetentionValuesBean vBean = null;
			RetentionBean temBean = null;
			for(RetentionBean bean : result){
				List<RetentionValuesBean> vBeans = new ArrayList<RetentionValuesBean>();
				temBean = new RetentionBean();
				double  value = getRetentionRatio(searchBar,retentBeans,bean);
//				if(value==0.0) value = Math.random()*100 + 1;
				if(map.get(bean.getName())==null){
					
					temBean.setName(bean.getName());
					vBean = new RetentionValuesBean();
					vBean.setTag(getTag(searchBar, bean));
					vBean.setValue(value);
					vBeans.add(vBean);
					temBean.setData(vBeans);
					map.put(bean.getName(), temBean);
					
				}else{
					RetentionBean v =	map.get(bean.getName());
					vBean = new RetentionValuesBean();
					vBean.setTag(getTag(searchBar, bean));
					vBean.setValue(value); 
					v.getData().add(vBean);				
				}
			}
			Iterator<RetentionBean> iterator = map.values().iterator();
			result.clear();
			while(iterator.hasNext()){
				result.add(iterator.next());
			}
		} catch (Exception e) {
//			e.printStackTrace();
			log.error("RetentionBusinessImpl.getRetentionAll] [{0}]", e.getMessage());
			throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
		}
		log.info("[RetentionBusinessImpl.getRetentionAll] [end]");
		return result;
	}

	/**
	 * 获取留存比率
	 * @param bar
	 * @param beans
	 * @param b
	 * @return
	 */
	private double getRetentionRatio(SearchBar bar,List<RetentionBean> beans, RetentionBean b){
		for(RetentionBean bean : beans){
			if(bar.getType().equals(SearchBar.DAILY)){
				if(bar.getDateEqual()){
					if(b.getHour() == bean.getHour()){
						if(bean.getCount()==0) return 0.0;
						return  b.getCount() / bean.getCount() * 100;
					}
				}else{
					if(b.getTime().getTime() == bean.getTime().getTime()){
						if(bean.getCount()==0) return 0.0;
						return  b.getCount() / bean.getCount() * 100;
					}
				}
			}else if(bar.getType().equals(SearchBar.WEEKLY)){
				if(b.getWeek_num() == bean.getWeek_num()){
					if(bean.getCount()==0) return 0.0;
					return  b.getCount() / bean.getCount() * 100;
				}
			}else if(bar.getType().equals(SearchBar.MONTHLY)){
				if(b.getYear() == bean.getYear() && b.getMonth() == bean.getMonth()){
					if(bean.getCount()==0) return 0.0;
					return  b.getCount() / bean.getCount() * 100;
				}
			} 
		}
		return 0.0;
	}
}
