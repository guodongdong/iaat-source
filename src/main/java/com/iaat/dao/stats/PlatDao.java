package com.iaat.dao.stats;

import java.util.List;
import java.util.Map;
import com.iaat.json.SearchBar;
import com.iaat.model.LogSourceType;
import com.iaat.model.PlatBean;
import com.nokia.ads.platform.backend.util.Paging;

public interface PlatDao {
	public List<PlatBean> getPlatPie(SearchBar bar,LogSourceType log);
	public List<PlatBean> getPlatList(SearchBar bar,LogSourceType log,Paging paging);
	public Map<String,List<PlatBean>> getPlatLine(SearchBar bar,String type,LogSourceType log);
	
}
