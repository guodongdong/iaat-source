package com.iaat.dao.analyse;

import java.util.List;

import com.iaat.json.FocusUrlsBean;
import com.iaat.json.SearchBar;
import com.iaat.model.TrackInput;
import com.iaat.model.TrackOutput;
import com.nokia.ads.platform.backend.util.Paging;

public interface AccessTrackDao {

	/**    
	 * getUrlsList(get all urls by searchBar)   
	 * 
	 * 
	 * @param searchBar
	 * @return 
	 * 
	 * List<TrackInput>
	 */
	public List<TrackInput> getUrlsList(SearchBar searchBar);
	
	/**    
	 * getInputUVCount(Here describes this method function with a few words)   
	 * 
	 * TODO(Here describes this method to be suitable the condition - to be possible to elect)
	 * 
	 * @param searchBar
	 * @param focusUrl
	 * @return 
	 * 
	 * TrackInput
	 */
	public TrackInput getInputUVCount(SearchBar searchBar,String focusUrl);
	
	public TrackOutput getOutputUVCount(SearchBar searchBar,String focusUrl);
	
	public List<TrackInput> getInputUrlsByFocus(SearchBar searchBar,String focusUrl,Paging paging);
	
	public List<TrackOutput> getOuputUrlsByFocus(SearchBar searchBar,String focusUrl,Paging paging);

	/**    
	 * getTopFocusUrls(get top all focusUrls)
	 * 
	 * @param searchBar
	 * @return 
	 * 
	 * List<FocusUrlsBean>
	 */
	public List<FocusUrlsBean> getTopFocusUrls(SearchBar searchBar);
}
