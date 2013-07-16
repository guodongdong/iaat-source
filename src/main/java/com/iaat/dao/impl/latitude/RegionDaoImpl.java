package com.iaat.dao.impl.latitude;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.iaat.dao.latitude.RegionDao;
import com.iaat.dbutils.BaseDAO;
import com.iaat.model.RegionBean;
import com.iaat.share.Params;
import com.nokia.ads.common.util.Log;
import com.nokia.ads.common.util.StringUtils;

/**    
 * @name RegionDaoImpl
 * 
 * @description CLASS_DESCRIPTION
 * 
 * MORE_INFORMATION
 * 
 * @author JackGuo
 * 
 * @since 2013-6-29
 *       
 * @version 1.0
 */
public class RegionDaoImpl implements RegionDao {
	private final static Log log = Log.getLogger(RegionDaoImpl.class);
	@SuppressWarnings("unchecked")
	@Override
	public List<RegionBean> getAllRegionBeans(String regionName) {
			log.info("[RegionDaoImpl.getAllRegionBeans][begin]");
			List<RegionBean> list = null;
			Connection conn = null;
			try {
				if(regionName!=null&&StringUtils.hasLength(regionName)){
					conn = BaseDAO.openConnection(regionName);	
				}else{
					conn = BaseDAO.openConnection(Params.REGION_NAME);
				}
				QueryRunner qr = new QueryRunner();
				StringBuffer sql = new StringBuffer();
				sql.append("SELECT ");
				sql.append("    Region_FK as regionFk, ");
				sql.append("    Region, ");
				sql.append("    code, ");
				sql.append("    sequence, ");
				sql.append("    IsAvailable, ");
				sql.append("    P_Region as PRegion, ");
				sql.append("    P_code as PCode, ");
				sql.append("    updateTime, ");
				sql.append("    description ");
				sql.append("FROM ");
				sql.append("    d_region ");
				sql.append("WHERE ");
				sql.append("    IsAvailable = 1 ");
				sql.append("ORDER BY ");
				sql.append("    sequence asc ");
				list = qr.query(conn, sql.toString(), new BeanListHandler(RegionBean.class),new Object[]{});
				conn.close();
			} catch (SQLException e) {
				log.error("[RegionDaoImpl.getAllRegionBeans] [{0}]", e.getMessage());
			}finally{
				try {
					BaseDAO.close(conn);
				} catch (SQLException e) {  
					log.error("[RegionDaoImpl.getAllRegionBeans] [{0}]", e.getMessage());
				}
			}
		log.info("[RegionDaoImpl.getAllRegionBeans][end]");
		return list;
	}
	@SuppressWarnings("unchecked")
	public List<RegionBean> getAllProvinBeans(String regionName) {
		log.info("[RegionDaoImpl.getAllProvinBeans][begin]");
		List<RegionBean> list = null;
		Connection conn = null;
		try {
			if(regionName!=null&&StringUtils.hasLength(regionName)){
				conn = BaseDAO.openConnection(regionName);	
			}else{
				conn = BaseDAO.openConnection(Params.REGION_NAME);
			}
			QueryRunner qr = new QueryRunner();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT ");
			sql.append("    d_region.Region_FK as regionFk, ");
			sql.append("    d_region.Region, ");
			sql.append("    d_region.code, ");
			sql.append("    d_region.sequence, ");
			sql.append("    d_region.IsAvailable, ");
			sql.append("    d_region.P_Region as PRegion, ");
			sql.append("    d_region.P_code as PCode, ");
			sql.append("    d_region.updateTime, ");
			sql.append("    d_region.description ");
			sql.append("FROM ");
			sql.append("    (SELECT DISTINCT ");
			sql.append("        p_region ");
			sql.append("    FROM ");
			sql.append("        d_region ");
			sql.append("    )d INNER JOIN d_region ON d.p_region = d_region.region ");
			sql.append("WHERE ");
			sql.append("    d_region.sequence > 0 AND ");
			sql.append("    d_region.P_code = 'ROOT' ");
			sql.append("ORDER BY ");
			sql.append("    Region_fk ASC ");
			sql.append(" ");
			list = qr.query(conn, sql.toString(), new BeanListHandler(RegionBean.class),new Object[]{});
			conn.close();
		} catch (SQLException e) {
			log.error("[RegionDaoImpl.getAllProvinBeans] [{0}]", e.getMessage());
		}finally{
			try {
				 BaseDAO.close(conn);
			} catch (SQLException e) {
				log.error("[RegionDaoImpl.getAllProvinBeans] [{0}]", e.getMessage());
			}
		}
		log.info("[RegionDaoImpl.getAllProvinBeans][end]");
		return list;
	 }	
}
