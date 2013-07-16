package com.iaat.business.stats;

import java.util.List;

import com.iaat.json.SearchBar;
import com.iaat.model.ImeiAccess;
import com.iaat.model.LogSourceType;
import com.nokia.ads.platform.backend.util.Paging;

public interface ImeiBusiness {

	List<ImeiAccess> getImeiList(SearchBar bar,LogSourceType log,String imei,Paging paging);
	
}
