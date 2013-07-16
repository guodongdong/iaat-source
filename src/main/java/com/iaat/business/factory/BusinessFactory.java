package com.iaat.business.factory;

import java.util.HashMap;
import java.util.Map;

import com.iaat.business.analyse.AccessTrackBusiness;
import com.iaat.business.bounce.BounceBusiness;
import com.iaat.business.heat.HeatBusiness;
import com.iaat.business.impl.ImeiBusinessImpl;
import com.iaat.business.impl.PlatBusinessImpl;
import com.iaat.business.impl.analyse.AccessTrackBusinessImpl;
import com.iaat.business.impl.bounce.BounceBusinessImpl;
import com.iaat.business.impl.heat.HeatBusinessImpl;
import com.iaat.business.impl.latitude.LatitudeBusinessImpl;
import com.iaat.business.impl.multidimensional.MultidimensionalBusinessImpl;
import com.iaat.business.impl.overlap.CrossContrastBusinessImpl;
import com.iaat.business.impl.profile.ProfileBusinessImpl;
import com.iaat.business.impl.quantitytrend.QuantityTrendBusinessImpl;
import com.iaat.business.impl.relationship.RelationshipBusinessImpl;
import com.iaat.business.impl.retention.RetentionBusinessImpl;
import com.iaat.business.latitude.LatitudeBusiness;
import com.iaat.business.multidimensional.MultidimensionalBusiness;
import com.iaat.business.overlap.CrossContrastBusiness;
import com.iaat.business.profile.ProfileBusiness;
import com.iaat.business.quantitytrend.QuantityTrendBusiness;
import com.iaat.business.relationship.RelationshipBusiness;
import com.iaat.business.retention.RetentionBusiness;
import com.iaat.business.stats.ImeiBusiness;
import com.iaat.business.stats.PlatBusiness;
import com.nokia.ads.common.util.Log;

final public class BusinessFactory {
	
	public static final Log log = Log.getLogger(BusinessFactory.class);
	
	private static Map<Class<?>, Class<?>> bizMappings = new HashMap<Class<?>, Class<?>>();
	static{
		//期间用户概况
		bizMappings.put(ProfileBusiness.class, ProfileBusinessImpl.class);
		bizMappings.put(QuantityTrendBusiness.class, QuantityTrendBusinessImpl.class);
		bizMappings.put(LatitudeBusiness.class, LatitudeBusinessImpl.class);
		bizMappings.put(AccessTrackBusiness.class, AccessTrackBusinessImpl.class);
		bizMappings.put(RetentionBusiness.class, RetentionBusinessImpl.class);
		//用户访问关联
		bizMappings.put(RelationshipBusiness.class, RelationshipBusinessImpl.class);
		//plat
		bizMappings.put(PlatBusiness.class, PlatBusinessImpl.class);
		//life of p
		bizMappings.put(ImeiBusiness.class, ImeiBusinessImpl.class);
		//用户热力分布图
		bizMappings.put(HeatBusiness.class, HeatBusinessImpl.class);
		//多维度分析
		bizMappings.put(MultidimensionalBusiness.class, MultidimensionalBusinessImpl.class);
		/**
		 *交叉对比
		 */
		bizMappings.put(CrossContrastBusiness.class,CrossContrastBusinessImpl.class);
		/**
		 *访客回访率
		 */
		bizMappings.put(BounceBusiness.class, BounceBusinessImpl.class);
	}
	
	@SuppressWarnings("unchecked")
	public synchronized static <T> T getInstance(Class<T> businessInterface) {
		try {
			Class<T> clz = (Class<T>) bizMappings.get(businessInterface);
			return clz.newInstance();
		} catch (Exception e) {
			log.error("[BusinessFactory.getInstance] [{0}]",e.getMessage());
			return null;
		}
	}

	@Override
	public String toString() {
		return super.toString();
	}
}
