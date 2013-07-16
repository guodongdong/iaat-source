package com.iaat.business.impl.profile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.iaat.business.profile.ProfileBusiness;
import com.iaat.dao.factory.DaoFactory;
import com.iaat.dao.profile.ProfileDao;
import com.iaat.json.SearchBar;
import com.iaat.json.profile.ProfileBean;
import com.iaat.json.profile.ProfileChartBean;
import com.iaat.util.ValidateUtils;
import com.nokia.ads.common.util.Log;

/**
 * 
 * @name ProfileBusinessImpl
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
public class ProfileBusinessImpl implements ProfileBusiness {
	private final static Log log = Log.getLogger(ProfileBusinessImpl.class);

	private final static ProfileDao dao = DaoFactory.getInstance(ProfileDao.class);

	@Override
	public ProfileBean getProfileBean(SearchBar bar) {
		log.info("[ProfileBusinessImpl.getProfileBean] [begin]");
		ProfileBean bean = dao.getProfile(bar);

//		bean.setPv(dao.getPV(bar));
//		bean.setUv(dao.getUV(bar));
//		bean.setAvgLastTime(dao.getAvgLastTime(bar));
//		bean.setAvgUserAccess(dao.getAvgUserAccess(bar));
//		bean.setAvgUserLogin(dao.getAvgUserLogin(bar));
//		bean.setIncreaseUser(dao.getIncreaseUser(bar));
//		bean.setOnceUser(dao.getOnceUser(bar));
//		bean.setOnlyLoginUser(dao.getOnlyLoginUser(bar));
		log.info("[ProfileBusinessImpl.getProfileBean] [end]");
		return bean;
	}

	@Override
	public List<ProfileChartBean> getPVChart(SearchBar bar) {
		log.info("[ProfileBusinessImpl.getPVChart] [begin]");
		List<ProfileChartBean> list = dao.getPVChart(bar);
		log.info("[ProfileBusinessImpl.getPVChart] [end]");
		return list;
	}

	@Override
	public List<ProfileChartBean> getUVChart(SearchBar bar) {
		log.info("[ProfileBusinessImpl.getUVChart] [begin]");
		List<ProfileChartBean> list = dao.getUVChart(bar);
		log.info("[ProfileBusinessImpl.getUVChart] [end]");
		return list;
	}

	@Override
	public List<ProfileChartBean> getIncreaseUserChart(SearchBar bar) {
		log.info("[ProfileBusinessImpl.getIncreaseUserChart] [begin]");
		List<ProfileChartBean> list = dao.getIncreaseUserChart(bar);
		log.info("[ProfileBusinessImpl.getIncreaseUserChart] [end]");
		return list;
	}

	@Override
	public List<ProfileChartBean> getOnceUserChart(SearchBar bar) {
		log.info("[ProfileBusinessImpl.getOnceUserChart] [begin]");
		List<ProfileChartBean> list = dao.getOnceUserChart(bar);
		log.info("[ProfileBusinessImpl.getOnceUserChart] [end]");
		return list;
	}

	@Override
	public List<ProfileChartBean> getUserLoginCountChart(SearchBar bar) {
		log.info("[ProfileBusinessImpl.getUserLoginCountChart] [begin]");
		String logSource = bar.getLogSource();
		List<ProfileChartBean> rs = null;
		if (ValidateUtils.isNotNull(logSource) && logSource.indexOf("tom") >= 0) {
			List<ProfileChartBean> list = dao.getUserLoginCountChart(bar);
			rs = getResult(bar, list);
			ProfileChartBean tenBean = rs.get(rs.size()-1);
			tenBean.setSecondCount(dao.getLoginImeiCount(bar));
		}
		log.info("[ProfileBusinessImpl.getUserLoginCountChart] [end]");
		return rs;
	}

	private void updateCount(Map<Integer, ProfileChartBean> map,int i) {
		ProfileChartBean bean = map.get(i);
		bean.setSecondCount(bean.getSecondCount()+1);
	}

	private void updateTitle(ProfileChartBean bean,int begin,int end){
		String title = begin + "-" + end + "次";
		if ((end - begin) == 1) {
			title = begin + "次";
		}
		bean.setTitle(title);
	}

	@Override
	public List<ProfileChartBean> getUserAccessCountChart(SearchBar bar) {
		log.info("[ProfileBusinessImpl.getUserAccessCountChart] [begin]");
		List<ProfileChartBean> rs = null;
		List<ProfileChartBean> list = dao.getUserAccessCountChart(bar);
		rs = getResult(bar, list);
		ProfileChartBean tenBean = rs.get(rs.size()-1);
		tenBean.setSecondCount(dao.getAccessImeiCount(bar));
		log.info("[ProfileBusinessImpl.getUserAccessCountChart] [end]");
		return rs;
	}

	private List<ProfileChartBean>  getResult(SearchBar bar, List<ProfileChartBean> list) {
		List<ProfileChartBean> rs = new ArrayList<ProfileChartBean>();
		Map<Integer, ProfileChartBean> map = new HashMap<Integer, ProfileChartBean>();
		int min = bar.getMinCount();
		int incr = bar.getIncrement();
		int one = min+incr*1;
		int two = min+incr*2;
		int three = min+incr*3;
		int four = min+incr*4;
		int five = min+incr*5;
		int six = min+incr*6;
		int seven = min+incr*7;
		int eight = min+incr*8;
		for (int i = 1; i < 11; i++) {
			ProfileChartBean mapBean = new ProfileChartBean();
			switch (i) {
			case 1:
				updateTitle(mapBean, min, one);
				break;
			case 2:
				updateTitle(mapBean, one, two);
				break;
			case 3:
				updateTitle(mapBean, two, three);
				break;
			case 4:
				updateTitle(mapBean, three, four);
				break;
			case 5:
				updateTitle(mapBean, four, five);
				break;
			case 6:
				updateTitle(mapBean, five, six);
				break;
			case 7:
				updateTitle(mapBean, six, seven);
				break;
			case 8:
				updateTitle(mapBean, seven, eight);
				break;
			case 9:
				updateTitle(mapBean, eight, bar.getMaxCount());
				break;
			case 10:
				mapBean.setTitle(bar.getMaxCount() + "次及以上");
				break;
			default:
				break;
			}
			map.put(i, mapBean);
		}

		if (ValidateUtils.isNotNull(list)) {
			for (ProfileChartBean bean : list) {
				int pv = bean.getSecondCount();
				if (pv >= min && pv < one) {
					updateCount(map,1);
				}else if (pv >= one && pv < two) {
					updateCount(map,2);
				}else if (pv >= two && pv < three) {
					updateCount(map,3);
				}else if (pv >= three && pv < four) {
					updateCount(map,4);
				}else if (pv >= four && pv < five) {
					updateCount(map,5);
				}else if (pv >= five && pv < six) {
					updateCount(map,6);
				}else if (pv >= six && pv < seven) {
					updateCount(map,7);
				}else if (pv >= seven && pv < eight) {
					updateCount(map,8);
				}else if (pv >= eight && pv < bar.getMaxCount()) {
					updateCount(map,9);
				}
			}
		}
		for (int i = 1; i < 11; i++) {
			rs.add(map.get(i));
		}
		return rs;
	}

	@Override
	public List<ProfileChartBean> getOnlyLoginUserChart(SearchBar bar) {
		log.info("[ProfileBusinessImpl.getOnlyLoginUserChart] [begin]");
		List<ProfileChartBean> list = dao.getOnlyLoginUserChart(bar);
		log.info("[ProfileBusinessImpl.getOnlyLoginUserChart] [end]");
		return list;
	}

}
