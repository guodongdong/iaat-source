package com.iaat.business.impl.heat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.iaat.business.heat.HeatBusiness;
import com.iaat.dao.factory.DaoFactory;
import com.iaat.dao.heat.HeatDao;
import com.iaat.json.ChartBean;
import com.iaat.json.SearchBar;
import com.iaat.json.heat.HeatRegionBean;
import com.iaat.json.heat.OperatorRatioBean;
import com.iaat.json.heat.RegionChartBean;
import com.iaat.util.ValidateUtils;
import com.nokia.ads.common.util.Log;

public class HeatBusinessImpl implements HeatBusiness {
	private final static Log log = Log.getLogger(HeatBusinessImpl.class);
	
	private final static HeatDao DAO = DaoFactory.getInstance(HeatDao.class);
	
	@Override
	public List<RegionChartBean> getNationMapChart(SearchBar bar) {
		log.info("[HeatBusinessImpl.getNationMapChart] [begin]");
		List<RegionChartBean> list = DAO.getNationMapChart(bar);
		log.info("[HeatBusinessImpl.getNationMapChart] [end]");
		return list;
	}

	@Override
	public Map<String, List<HeatRegionBean>> getRegionDetailList(SearchBar bar) {
		log.info("[HeatBusinessImpl.getRegionDetailList] [begin]");
		List<HeatRegionBean> list = DAO.getRegionDetailList(bar);
		Map<String, List<HeatRegionBean>> map = new HashMap<String, List<HeatRegionBean>>();
		if (ValidateUtils.isNotNull(list)) {
			HeatRegionBean total = new HeatRegionBean();
			total.setRegion("合计");
			for (HeatRegionBean b : list) {
				total.setIncreaseUser(b.getIncreaseUser()+total.getIncreaseUser());
				total.setOldUser(b.getOldUser()+total.getOldUser());
				total.setUv(b.getUv()+total.getUv());
			}
			map.put("list", list);
			List<HeatRegionBean> totalList = new ArrayList<HeatRegionBean>();
			totalList.add(total);
			map.put("total", totalList);
		}
		log.info("[HeatBusinessImpl.getRegionDetailList] [end]");
		return map;
	}

	@Override
	public List<HeatRegionBean> getRegionDetailListExport(SearchBar bar) {
		log.info("[HeatBusinessImpl.getRegionDetailList] [begin]");
		List<HeatRegionBean> list = DAO.getRegionDetailList(bar);
		log.info("[HeatBusinessImpl.getRegionDetailList] [end]");
		return list;
	}

	@Override
	public List<OperatorRatioBean> getOperatorRatioList(SearchBar bar) {
		log.info("[HeatBusinessImpl.getOperatorRatioList] [begin]");
		List<OperatorRatioBean> list = DAO.getOperatorRatioList(bar);
		if (ValidateUtils.isNotNull(list)) {
			int total = 0;
			for (OperatorRatioBean b : list) {
				total += b.getCount();
			}
			if (ValidateUtils.isNotNull(total)) {
				for (OperatorRatioBean b : list) {
					Float ratio = (float)b.getCount()/(float)total;
					b.setRatio(ratio);
				}
			}
		}
		log.info("[HeatBusinessImpl.getOperatorRatioList] [end]");
		return list;
	}

	@Override
	public List<ChartBean> getHourlyChart(SearchBar bar) {
		log.info("[HeatBusinessImpl.getHourlyChart] [begin]");
		List<ChartBean> list = DAO.getHourlyChart(bar);
		log.info("[HeatBusinessImpl.getHourlyChart] [end]");
		return list;
	}

	@Override
	public List<ChartBean> getDailyChart(SearchBar bar) {
		log.info("[HeatBusinessImpl.getDailyChart] [begin]");
		List<ChartBean> list = DAO.getDailyChart(bar);
		log.info("[HeatBusinessImpl.getDailyChart] [end]");
		return list;
	}

}
