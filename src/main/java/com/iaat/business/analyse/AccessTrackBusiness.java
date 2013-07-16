package com.iaat.business.analyse;

import java.util.List;

import com.iaat.json.FocusUrlsBean;
import com.iaat.json.SearchBar;
import com.iaat.json.analyse.AccessBean;
import com.iaat.json.analyse.AccessTrackBean;
import com.nokia.ads.platform.backend.util.Paging;

public interface AccessTrackBusiness {

	public List<AccessTrackBean> getUrlsList(SearchBar bar);

	public AccessTrackBean getInputUVCount(SearchBar searchBar,String focusUrl);
	
	public AccessTrackBean getOutputUVCount(SearchBar searchBar,String focusUrl);
	
	public List<AccessBean> getInputUrlsByFocus(SearchBar searchBar,String focusUrl,Paging paging);
	
	public List<AccessBean> getOuputUrlsByFocus(SearchBar searchBar,String focusUrl,Paging paging);
	
	public List<FocusUrlsBean> getTopFocusUrls(SearchBar searchBar);
}
