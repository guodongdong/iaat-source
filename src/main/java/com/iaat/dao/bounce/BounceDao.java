package com.iaat.dao.bounce;

import java.util.List;

import com.iaat.json.SearchBar;
import com.iaat.json.bounce.BounceBean;
import com.iaat.json.bounce.BounceDataBean;

public interface BounceDao {

	/**    
	 * getBounceRate(get customer rate)   
	 * 
	 * @param bar
	 * @return 
	 * 
	 * List<BounceBean>
	 */
	public List<BounceBean> getBounceRate(SearchBar bar);
	
	/**    
	 * getBounceDataByRate(get BounceDate by recall date)   
	 * 
	 * @param bar
	 * @param intervalTime
	 * @return 
	 * 
	 * List<BounceBean>
	 */
	public List<BounceBean> getBounceDataByIntervoidTime(SearchBar bar,Integer intervalTime);
	
	
	/**    
	 * getBounceDataBeanCount(get bouncedataBean's count)   
	 * 
	 * 
	 * @param bar
	 * @return 
	 * 
	 * List<BounceDataBean>
	 */
	public List<BounceBean> getBounceDataBeanCount(SearchBar bar);

}
