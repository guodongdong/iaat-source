package com.iaat.business.impl.latitude;

import java.util.List;
import java.util.Map;

import com.iaat.business.latitude.LatitudeBusiness;
import com.iaat.json.LatitudeBean;
import com.iaat.json.LatitudeDataBean;

/**    
 * @name LatitudeBusinessImpl
 * 
 * @description CLASS_DESCRIPTION
 * 
 * MORE_INFORMATION
 * 
 * @author JackGuo
 * 
 * @since 2013-6-25
 *       
 * @version 1.0
 */
public class LatitudeBusinessImpl implements LatitudeBusiness {

	@Override
	public Map<String, List<LatitudeDataBean>> getAllLatitudes() {
		LatitudeBean latitudeBean = LatitudeBean.getInstance();
		return latitudeBean.getResults();
	}

}
