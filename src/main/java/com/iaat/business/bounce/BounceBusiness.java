package com.iaat.business.bounce;

import java.util.List;

import com.iaat.json.SearchBar;
import com.iaat.json.bounce.BounceDataBean;

public interface BounceBusiness {
	public List<BounceDataBean> getBounceRate(SearchBar bar);
	
	public List<BounceDataBean> getBounceRateAll(SearchBar bar);
}
