package com.iaat.business.stats;

import java.util.List;
import java.util.Map;
import com.iaat.json.SearchBar;
import com.iaat.model.LogSourceType;
import com.iaat.model.PlatBean;
import com.iaat.model.PlatformObj;
import com.nokia.ads.platform.backend.util.Paging;
public interface PlatBusiness {

	/**
	 * get data for pie
	 * @param bar
	 * @return
	 */
	public List<PlatBean> getPlatUsers(SearchBar bar,LogSourceType log);
	/**
	 * get data for list
	 * @param bar
	 * @return
	 */
	public PlatformObj getPlatList(SearchBar bar,LogSourceType log,Paging paging);
	/**
	 * get data for line
	 * @param bar
	 * @param type
	 * @return
	 */
	public Map<String,List<PlatBean>> getPlatLine(SearchBar bar,String type,LogSourceType log);
}
