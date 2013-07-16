package com.iaat.business.impl;

import java.util.List;
import java.util.Map;
import com.iaat.business.stats.PlatBusiness;
import com.iaat.dao.factory.DaoFactory;
import com.iaat.dao.stats.PlatDao;
import com.iaat.json.SearchBar;
import com.iaat.model.LogSourceType;
import com.iaat.model.PlatBean;
import com.iaat.model.PlatformObj;
import com.nokia.ads.platform.backend.util.Paging;

public class PlatBusinessImpl implements PlatBusiness {
	
	private static PlatDao dao =DaoFactory.getInstance(PlatDao.class);
	/**
	 * 饼状图
	 */
	@Override
	public List<PlatBean> getPlatUsers(SearchBar bar,LogSourceType log) {
		return dao.getPlatPie(bar,log);
	}
	/**
	 * 曲线图
	 */
	@Override
	public Map<String,List<PlatBean>> getPlatLine(SearchBar bar,String type,LogSourceType log){
		return dao.getPlatLine(bar,type,log);
	}
	/**
	 * 列表
	 */
	@Override
	public PlatformObj getPlatList(SearchBar bar,LogSourceType log,Paging paging) {
		return process(dao.getPlatList(bar,log,paging));
		
	}
	
	
	/**
	 * 老用户
	 * @param list
	 * @return
	 */
	private PlatformObj process(List<PlatBean> list){
		int nuser=0,ouser=0,user=0;
		PlatformObj obj = new PlatformObj();
		if(list!=null)
		for(PlatBean pb:list){
			pb.setOlduser(pb.getUsernum()-pb.getNewuser());
			ouser+=pb.getOlduser();
			nuser+=pb.getNewuser();
			user+=pb.getUsernum();
		}
		obj.setData(list);
		obj.setTotalNewUsers(nuser);
		obj.setTotalOldUsers(ouser);
		obj.setTotalUsers(user);
		return obj;
	}
	
}
