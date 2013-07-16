package com.iaat.business.overlap;

import java.util.List;

import com.iaat.json.overlap.ContrastResultBean;
import com.iaat.json.overlap.ContrastSearchBean;
import com.iaat.json.overlap.CrossDataBean;

public interface CrossContrastBusiness {
	
	/**    
	 * getCorssConstrasts(cross constrast datas)   
	 * 
	 * @return 
	 * 
	 * List<CrossContrastBean>
	 */
	public List<CrossDataBean> getCorssConstrasts(ContrastSearchBean[] cases,ContrastResultBean restultBean);
}
