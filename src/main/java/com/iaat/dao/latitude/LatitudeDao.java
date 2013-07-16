package com.iaat.dao.latitude;

import com.iaat.json.LatitudeBean;

/**    
 * @name LatitudeDao
 * 
 * @description CLASS_DESCRIPTION
 * 
 * 纬度查询的Dao
 * 
 * @author JackGuo
 * 
 * @since 2013-6-24
 *       
 * @version 1.0
 */
public interface LatitudeDao {
	
	/**    
	 * getAps(获取所有的接入点)   
	 * 
	 * @return 
	 * 
	 * LatitudeBean
	 */
	public LatitudeBean getAps();
	
	/**    
	 * getCategorys(获取所有的分类)
	 * 
	 * @return 
	 * 
	 * LatitudeBean
	 */
	public LatitudeBean getCategorys();
	
	/**    
	 * getChannels(获得多有的渠道)   
	 * 
	 * @return 
	 * 
	 * LatitudeBean
	 */
	public LatitudeBean getChannels();
	
	/**    
	 * getOperators(获得所有的运营商)   
	 * 
	 * @return 
	 * 
	 * LatitudeBean
	 */
	public LatitudeBean getOperators();
	
	/**    
	 * getPaltforms(获得所有的平台)   
	 * 
	 * @return 
	 * 
	 * LatitudeBean
	 */
	public LatitudeBean getPaltforms();
	
	/**    
	 * getRegions(获得所有的地域)   
	 * 
	 * @return 
	 * 
	 * LatitudeBean
	 */
	public LatitudeBean getRegions();
	
	/**    
	 * getTerminals(获取所以的终端)   
	 * 
	 * @return 
	 * 
	 * LatitudeBean
	 */
	public LatitudeBean getTerminals();
	
}
