package com.iaat.business.latitude;

import java.util.List;
import java.util.Map;

import com.iaat.json.LatitudeDataBean;


/**    
 * @name LatitudeBusiness
 * 
 * @description CLASS_DESCRIPTION
 * 
 * 纬度的业务逻辑
 * 
 * @author JackGuo
 * 
 * @since 2013-6-24
 *       
 * @version 1.0
 */
public interface LatitudeBusiness {

	public  Map<String, List<LatitudeDataBean>>  getAllLatitudes();
	
}
