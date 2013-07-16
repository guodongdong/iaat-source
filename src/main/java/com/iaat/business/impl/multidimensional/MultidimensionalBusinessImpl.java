package com.iaat.business.impl.multidimensional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.iaat.business.multidimensional.MultidimensionalBusiness;
import com.iaat.dao.factory.DaoFactory;
import com.iaat.dao.multidimensional.MultidimensionalDao;
import com.iaat.json.SearchBar;
import com.iaat.json.profile.ProfileBean;
import com.nokia.ads.common.util.Log;

/**
 * 
 * @name MultidimensionalBusinessImpl
 * 
 * @description CLASS_DESCRIPTION
 * 
 * MORE_INFORMATION
 * 
 * @author zhaozhi
 * 
 * @since 2013-7-10
 *       
 * @version 1.0
 */
public class MultidimensionalBusinessImpl implements MultidimensionalBusiness {
	
	private final static Log log = Log.getLogger(MultidimensionalBusinessImpl.class);
	
	private final static MultidimensionalDao DAO = DaoFactory.getInstance(MultidimensionalDao.class);

	@Override
	public List<ProfileBean> getChart(SearchBar bar) {
		log.info("[MultidimensionalBusinessImpl.getChart] [begin]");
		List<ProfileBean> list = DAO.getChart(bar);
		log.info("[MultidimensionalBusinessImpl.getChart] [end]");
		return list;
	}

	@Override
	public Map<String, List<ProfileBean>> getList(SearchBar bar) {
		log.info("[MultidimensionalBusinessImpl.getList] [begin]");
		Map<String, List<ProfileBean>> map = new HashMap<String, List<ProfileBean>>();
		
		log.info("[MultidimensionalBusinessImpl.getList] [end]");
		return map;
	}
	
	
	
}
