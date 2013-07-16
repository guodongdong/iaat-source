package com.iaat.dao.factory;

import java.util.HashMap;
import java.util.Map;

import com.iaat.dao.analyse.AccessTrackDao;
import com.iaat.dao.bounce.BounceDao;
import com.iaat.dao.heat.HeatDao;
import com.iaat.dao.impl.ImeiAccessDaoImpl;
import com.iaat.dao.impl.PlatDaoImpl;
import com.iaat.dao.impl.analyse.AccessTrackDaoImpl;
import com.iaat.dao.impl.bounce.BounceDaoImpl;
import com.iaat.dao.impl.heat.HeatDaoImpl;
import com.iaat.dao.impl.latitude.ApDaoImpl;
import com.iaat.dao.impl.latitude.CategoryDaoImpl;
import com.iaat.dao.impl.latitude.ChannelDaoImpl;
import com.iaat.dao.impl.latitude.ImeiDaoImpl;
import com.iaat.dao.impl.latitude.LogSourceDaoImpl;
import com.iaat.dao.impl.latitude.OperatorDaoImpl;
import com.iaat.dao.impl.latitude.PlatformDaoImpl;
import com.iaat.dao.impl.latitude.RegionDaoImpl;
import com.iaat.dao.impl.latitude.TerminalDaoImpl;
import com.iaat.dao.impl.multidimensional.MultidimensionalDaoImpl;
import com.iaat.dao.impl.overlap.CrossContrastDaoImpl;
import com.iaat.dao.impl.profile.ProfileDaoImpl;
import com.iaat.dao.impl.quantitytrend.QuantityTrendDaoImpl;
import com.iaat.dao.impl.relateionship.RelationshipDaoImpl;
import com.iaat.dao.latitude.ApDao;
import com.iaat.dao.latitude.CategoryDao;
import com.iaat.dao.latitude.ChannelDao;
import com.iaat.dao.latitude.ImeiDao;
import com.iaat.dao.latitude.LogSourceDao;
import com.iaat.dao.latitude.OperatorDao;
import com.iaat.dao.latitude.PlatformDao;
import com.iaat.dao.latitude.RegionDao;
import com.iaat.dao.latitude.TerminalDao;
import com.iaat.dao.multidimensional.MultidimensionalDao;
import com.iaat.dao.overlap.CrossContrastDao;
import com.iaat.dao.profile.ProfileDao;
import com.iaat.dao.quantitytrend.QuantityTrendDao;
import com.iaat.dao.relateionship.RelationshipDao;
import com.iaat.dao.retention.RetentionDao;
import com.iaat.dao.retention.impl.RetentionDaoImpl;
import com.iaat.dao.stats.ImeiAccessDao;
import com.iaat.dao.stats.PlatDao;
import com.iaat.json.relationship.RelationshipBean;
import com.nokia.ads.common.util.Log;

/**    
 * @name DaoFactory
 * 
 * @description CLASS_DESCRIPTION
 * 
 * MORE_INFORMATION
 * 
 * @author lijing
 * 
 * @since 2012-3-17
 *       
 * @version 1.0
 */
public class DaoFactory
{
	public static final Log log = Log.getLogger(DaoFactory.class);
	
	private static Map<Class<?>, Class<?>> daoMappings = new HashMap<Class<?>, Class<?>>();
	
	static {
		daoMappings.put(ProfileDao.class, ProfileDaoImpl.class);
		daoMappings.put(QuantityTrendDao.class, QuantityTrendDaoImpl.class);
		daoMappings.put(PlatDao.class, PlatDaoImpl.class);
		daoMappings.put(ApDao.class, ApDaoImpl.class);
		daoMappings.put(CategoryDao.class, CategoryDaoImpl.class);
		daoMappings.put(ChannelDao.class, ChannelDaoImpl.class);
		daoMappings.put(ImeiDao.class, ImeiDaoImpl.class);
		daoMappings.put(LogSourceDao.class, LogSourceDaoImpl.class);
		daoMappings.put(OperatorDao.class, OperatorDaoImpl.class);
		daoMappings.put(PlatformDao.class, PlatformDaoImpl.class);
		daoMappings.put(RegionDao.class, RegionDaoImpl.class);
		daoMappings.put(TerminalDao.class, TerminalDaoImpl.class);
		daoMappings.put(AccessTrackDao.class, AccessTrackDaoImpl.class);
		daoMappings.put(RetentionDao.class, RetentionDaoImpl.class);
		daoMappings.put(ImeiAccessDao.class,ImeiAccessDaoImpl.class);
		daoMappings.put(CrossContrastDao.class,CrossContrastDaoImpl.class);
		daoMappings.put(RelationshipDao.class,RelationshipDaoImpl.class);
		/**
		 * 用户热力分布
		 */
		daoMappings.put(HeatDao.class, HeatDaoImpl.class);
		/**
		 * 多维度分析
		 */
		daoMappings.put(MultidimensionalDao.class, MultidimensionalDaoImpl.class);
		
		/**
		 * 访客回访率
		 */
		daoMappings.put(BounceDao.class,BounceDaoImpl.class);
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Object> T getInstance(Class<T> daoInterface) {
		try {
			Class<T> clz = (Class<T>) daoMappings.get(daoInterface);
			return clz.newInstance();
		} catch (Exception e) {
			log.warn(e);
			return null;
		}
	}
}
