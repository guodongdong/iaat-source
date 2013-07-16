package com.iaat.business.impl.bounce;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.iaat.business.bounce.BounceBusiness;
import com.iaat.dao.bounce.BounceDao;
import com.iaat.dao.factory.DaoFactory;
import com.iaat.json.SearchBar;
import com.iaat.json.bounce.BounceBean;
import com.iaat.json.bounce.BounceDataBean;
import com.iaat.json.bounce.BounceRateBean;
import com.iaat.json.bounce.BounceType;
import com.iaat.util.DateUtil;
import com.nokia.ads.common.util.Log;

public class BounceBusinessImpl implements BounceBusiness {
	private final static Log log = Log.getLogger(BounceBusinessImpl.class);
	@Override
	public List<BounceDataBean> getBounceRate(SearchBar bar) {
		log.info("[BounceBusinessImpl.getBounceRate][begin]");
		BounceDao  bunceDao = DaoFactory.getInstance(BounceDao.class);
		List<BounceDataBean> bounceDataBeans = new ArrayList<BounceDataBean>();
		List<BounceBean> bounces = bunceDao.getBounceRate(bar);
		for (BounceBean bounceBean : bounces) {
			BounceDataBean bounceDataBean = new BounceDataBean();
			List<BounceRateBean> bounceRates = new ArrayList<BounceRateBean>();
			Calendar recDate = Calendar.getInstance();
			recDate.setTime(bounceBean.getBounceDate());
			String tag = getTag(bar,bounceBean.getBeginDate(),bounceBean.getEndDate(), bounceBean.getBounceDate());
			bounceDataBean.setBounceDate(tag);
			bounceDataBean.setPerople(bounceBean.getUv());
			bounceDataBean.setData(bounceRates);
			addDatas(bounceBean, bounceRates);
			bounceDataBeans.add(bounceDataBean);
		}
		log.info("[BounceBusinessImpl.getBounceRate][end]");
		return bounceDataBeans;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<BounceDataBean> getBounceRateAll(SearchBar bar){
		log.info("[BounceBusinessImpl.getBounceRateAll][begin]");
		BounceDao bounceDao = DaoFactory.getInstance(BounceDao.class);
		BounceType[] bounceTypes = BounceType.values();
		List<BounceBean> bounceCounts = bounceDao.getBounceDataBeanCount(bar);
		Map<String,BounceDataBean> mapBounceDataBeans = new HashMap<String, BounceDataBean>();
		for (BounceBean bounceBean : bounceCounts) {
			BounceDataBean bounceDataBean = new BounceDataBean();
			String bounceDateStr = DateUtil.date2String(bounceBean.getBounceDate()); 
			String tag = getTag(bar, bounceBean.getBeginDate(), bounceBean.getEndDate(), bounceBean.getBounceDate());
			bounceDataBean.setBounceDate(bounceDateStr);
			bounceDataBean.setPerople(bounceBean.getUv());
			List<BounceRateBean> bounceRates = new ArrayList<BounceRateBean>();
			addDatas(bounceBean, bounceRates);
			bounceDataBean.setData(bounceRates);
			mapBounceDataBeans.put(tag, bounceDataBean);
		}
		for (BounceType bounceType : bounceTypes) {
			List<BounceBean> bounces = bounceDao.getBounceDataByIntervoidTime(bar,bounceType.getValue());
				for (BounceBean bounceBean : bounces) {
					String tag = getTag(bar, bounceBean.getBeginDate(), bounceBean.getEndDate(), bounceBean.getBounceDate());
					BounceDataBean bounceDataBean = mapBounceDataBeans.get(tag);
					List<BounceRateBean> rates = bounceDataBean.getData();
					if(rates==null){
						rates = new ArrayList<BounceRateBean>();
						bounceDataBean.setData(rates);
					}
					BounceRateBean rateBean = new BounceRateBean();
					rateBean.setName(bounceType.getValue()); 
					double rate = (double)bounceBean.getUv()/bounceDataBean.getPerople();
					rateBean.setRate(rate);
					rates.add(rateBean);
				}	
		}
		List<BounceDataBean> bounceDataBeans = new ArrayList<BounceDataBean>(mapBounceDataBeans.values());
		BounceDataBean[] arrDataBeans = bounceDataBeans.toArray(new BounceDataBean[]{}); 
		Arrays.sort(arrDataBeans, new Comparator() {
			@Override
			public int compare(Object fromObj, Object toObj) {
				BounceDataBean fromDateBean = (BounceDataBean)fromObj;
				BounceDataBean toDateBean =  (BounceDataBean)toObj;
				Long fromTime = 0l;
				Long toTime = 0l;
				try {
					fromTime = new SimpleDateFormat("yyyy-MM-dd").parse(fromDateBean.getBounceDate()).getTime();
					toTime = new SimpleDateFormat("yyyy-MM-dd").parse(toDateBean.getBounceDate()).getTime();
				} catch (ParseException e) {
					e.printStackTrace();
				}
				return fromTime.compareTo(toTime);
			}
		});
		log.info("[BounceBusinessImpl.getBounceRateAll][end]");
		return Arrays.asList(arrDataBeans);
	}
	/**
	 * 格式显示的时间
	 * @param searchbar
	 * @param b
	 * @return
	 */
	public String getTag(SearchBar searchbar,Date beginTime,Date endTime,Date time){
		//按天统计数据，如果开始时间和结束时间在一天 就按小时统计
		if(searchbar.getType().equals(SearchBar.DAILY)){
					return DateUtil.date2String(time);
		}else {
			return DateUtil.date2String(beginTime)
						+ " ~ "
						+ DateUtil.date2String(endTime);
			
		}
	}
	
	
	private void addDatas(BounceBean bounceBean,List<BounceRateBean> bounceRates) {
		Calendar currentTime = Calendar.getInstance();
		Calendar bounceTime = Calendar.getInstance();
		bounceTime.setTime(bounceBean.getBeginDate());
		long gap = (currentTime.getTimeInMillis()-bounceTime.getTimeInMillis())/(1000*3600*24);//从间隔
		initBounceRates(bounceBean, bounceRates,(int)gap);
	}
	private void initBounceRates(BounceBean bounceBean,List<BounceRateBean> bounceRates, int gap) {
		switch (gap) {
		case 0:
			break;
		case 1:
			bounceRates.add(initBounceRateBean(BounceType.RATE1.getValue(),bounceBean.getRate1()));	
			break;
		case 2:
			bounceRates.add(initBounceRateBean(BounceType.RATE1.getValue(),bounceBean.getRate1()));
			bounceRates.add(initBounceRateBean(BounceType.RATE2.getValue(),bounceBean.getRate2()));
			break;
		case 3:
			bounceRates.add(initBounceRateBean(BounceType.RATE1.getValue(),bounceBean.getRate1()));
			bounceRates.add(initBounceRateBean(BounceType.RATE2.getValue(),bounceBean.getRate2()));
			bounceRates.add(initBounceRateBean(BounceType.RATE3.getValue(),bounceBean.getRate3()));
			break;
		case 4:
			bounceRates.add(initBounceRateBean(BounceType.RATE1.getValue(),bounceBean.getRate1()));
			bounceRates.add(initBounceRateBean(BounceType.RATE2.getValue(),bounceBean.getRate2()));
			bounceRates.add(initBounceRateBean(BounceType.RATE3.getValue(),bounceBean.getRate3()));
			bounceRates.add(initBounceRateBean(BounceType.RATE4.getValue(),bounceBean.getRate4()));
			break;
		case 5:
			bounceRates.add(initBounceRateBean(BounceType.RATE1.getValue(),bounceBean.getRate1()));
			bounceRates.add(initBounceRateBean(BounceType.RATE2.getValue(),bounceBean.getRate2()));
			bounceRates.add(initBounceRateBean(BounceType.RATE3.getValue(),bounceBean.getRate3()));
			bounceRates.add(initBounceRateBean(BounceType.RATE4.getValue(),bounceBean.getRate4()));
			bounceRates.add(initBounceRateBean(BounceType.RATE5.getValue(),bounceBean.getRate5()));
			break;
		case 6:
			bounceRates.add(initBounceRateBean(BounceType.RATE1.getValue(),bounceBean.getRate1()));
			bounceRates.add(initBounceRateBean(BounceType.RATE2.getValue(),bounceBean.getRate2()));
			bounceRates.add(initBounceRateBean(BounceType.RATE3.getValue(),bounceBean.getRate3()));
			bounceRates.add(initBounceRateBean(BounceType.RATE4.getValue(),bounceBean.getRate4()));
			bounceRates.add(initBounceRateBean(BounceType.RATE5.getValue(),bounceBean.getRate5()));
			bounceRates.add(initBounceRateBean(BounceType.RATE6.getValue(),bounceBean.getRate6()));
			break;
		case 7:
			bounceRates.add(initBounceRateBean(BounceType.RATE1.getValue(),bounceBean.getRate1()));
			bounceRates.add(initBounceRateBean(BounceType.RATE2.getValue(),bounceBean.getRate2()));
			bounceRates.add(initBounceRateBean(BounceType.RATE3.getValue(),bounceBean.getRate3()));
			bounceRates.add(initBounceRateBean(BounceType.RATE4.getValue(),bounceBean.getRate4()));
			bounceRates.add(initBounceRateBean(BounceType.RATE5.getValue(),bounceBean.getRate5()));
			bounceRates.add(initBounceRateBean(BounceType.RATE6.getValue(),bounceBean.getRate6()));
			bounceRates.add(initBounceRateBean(BounceType.RATE7.getValue(),bounceBean.getRate7()));
			break;
		case 8:
			bounceRates.add(initBounceRateBean(BounceType.RATE1.getValue(),bounceBean.getRate1()));
			bounceRates.add(initBounceRateBean(BounceType.RATE2.getValue(),bounceBean.getRate2()));
			bounceRates.add(initBounceRateBean(BounceType.RATE3.getValue(),bounceBean.getRate3()));
			bounceRates.add(initBounceRateBean(BounceType.RATE4.getValue(),bounceBean.getRate4()));
			bounceRates.add(initBounceRateBean(BounceType.RATE5.getValue(),bounceBean.getRate5()));
			bounceRates.add(initBounceRateBean(BounceType.RATE6.getValue(),bounceBean.getRate6()));
			bounceRates.add(initBounceRateBean(BounceType.RATE7.getValue(),bounceBean.getRate7()));
			bounceRates.add(initBounceRateBean(BounceType.RATE8.getValue(),bounceBean.getRate8()));
			break;
		case 9:
			bounceRates.add(initBounceRateBean(BounceType.RATE1.getValue(),bounceBean.getRate1()));
			bounceRates.add(initBounceRateBean(BounceType.RATE2.getValue(),bounceBean.getRate2()));
			bounceRates.add(initBounceRateBean(BounceType.RATE3.getValue(),bounceBean.getRate3()));
			bounceRates.add(initBounceRateBean(BounceType.RATE4.getValue(),bounceBean.getRate4()));
			bounceRates.add(initBounceRateBean(BounceType.RATE5.getValue(),bounceBean.getRate5()));
			bounceRates.add(initBounceRateBean(BounceType.RATE6.getValue(),bounceBean.getRate6()));
			bounceRates.add(initBounceRateBean(BounceType.RATE7.getValue(),bounceBean.getRate7()));
			bounceRates.add(initBounceRateBean(BounceType.RATE8.getValue(),bounceBean.getRate8()));
			bounceRates.add(initBounceRateBean(BounceType.RATE9.getValue(),bounceBean.getRate9()));
			break;
		case 10:
			bounceRates.add(initBounceRateBean(BounceType.RATE1.getValue(),bounceBean.getRate1()));
			bounceRates.add(initBounceRateBean(BounceType.RATE2.getValue(),bounceBean.getRate2()));
			bounceRates.add(initBounceRateBean(BounceType.RATE3.getValue(),bounceBean.getRate3()));
			bounceRates.add(initBounceRateBean(BounceType.RATE4.getValue(),bounceBean.getRate4()));
			bounceRates.add(initBounceRateBean(BounceType.RATE5.getValue(),bounceBean.getRate5()));
			bounceRates.add(initBounceRateBean(BounceType.RATE6.getValue(),bounceBean.getRate6()));
			bounceRates.add(initBounceRateBean(BounceType.RATE7.getValue(),bounceBean.getRate7()));
			bounceRates.add(initBounceRateBean(BounceType.RATE8.getValue(),bounceBean.getRate8()));
			bounceRates.add(initBounceRateBean(BounceType.RATE9.getValue(),bounceBean.getRate9()));
			bounceRates.add(initBounceRateBean(BounceType.RATE10.getValue(),bounceBean.getRate10()));
			break;
		case 11:
			bounceRates.add(initBounceRateBean(BounceType.RATE1.getValue(),bounceBean.getRate1()));
			bounceRates.add(initBounceRateBean(BounceType.RATE2.getValue(),bounceBean.getRate2()));
			bounceRates.add(initBounceRateBean(BounceType.RATE3.getValue(),bounceBean.getRate3()));
			bounceRates.add(initBounceRateBean(BounceType.RATE4.getValue(),bounceBean.getRate4()));
			bounceRates.add(initBounceRateBean(BounceType.RATE5.getValue(),bounceBean.getRate5()));
			bounceRates.add(initBounceRateBean(BounceType.RATE6.getValue(),bounceBean.getRate6()));
			bounceRates.add(initBounceRateBean(BounceType.RATE7.getValue(),bounceBean.getRate7()));
			bounceRates.add(initBounceRateBean(BounceType.RATE8.getValue(),bounceBean.getRate8()));
			bounceRates.add(initBounceRateBean(BounceType.RATE9.getValue(),bounceBean.getRate9()));
			bounceRates.add(initBounceRateBean(BounceType.RATE10.getValue(),bounceBean.getRate10()));
			bounceRates.add(initBounceRateBean(BounceType.RATE11.getValue(),bounceBean.getRate11()));
			break;
		case 12:
		default:
			bounceRates.add(initBounceRateBean(BounceType.RATE1.getValue(),bounceBean.getRate1()));
			bounceRates.add(initBounceRateBean(BounceType.RATE2.getValue(),bounceBean.getRate2()));
			bounceRates.add(initBounceRateBean(BounceType.RATE3.getValue(),bounceBean.getRate3()));
			bounceRates.add(initBounceRateBean(BounceType.RATE4.getValue(),bounceBean.getRate4()));
			bounceRates.add(initBounceRateBean(BounceType.RATE5.getValue(),bounceBean.getRate5()));
			bounceRates.add(initBounceRateBean(BounceType.RATE6.getValue(),bounceBean.getRate6()));
			bounceRates.add(initBounceRateBean(BounceType.RATE7.getValue(),bounceBean.getRate7()));
			bounceRates.add(initBounceRateBean(BounceType.RATE8.getValue(),bounceBean.getRate8()));
			bounceRates.add(initBounceRateBean(BounceType.RATE9.getValue(),bounceBean.getRate9()));
			bounceRates.add(initBounceRateBean(BounceType.RATE10.getValue(),bounceBean.getRate10()));
			bounceRates.add(initBounceRateBean(BounceType.RATE11.getValue(),bounceBean.getRate11()));
			bounceRates.add(initBounceRateBean(BounceType.RATE12.getValue(),bounceBean.getRate12()));
			break;
		}
	}
	private BounceRateBean initBounceRateBean(int rateName,double rate) {
		BounceRateBean bounceRateBean = new BounceRateBean();
		bounceRateBean.setName(rateName);
		bounceRateBean.setRate(rate);
		return bounceRateBean;
	}

}
