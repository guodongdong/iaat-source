package com.iaat.dao.impl.latitude;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.iaat.dao.latitude.PlatformDao;
import com.iaat.dbutils.BaseDAO;
import com.iaat.model.PlatformBean;
import com.iaat.share.Params;
import com.nokia.ads.common.util.Log;
import com.nokia.ads.common.util.StringUtils;

/**    
 * @name PlatformDaoImpl
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
public class PlatformDaoImpl implements PlatformDao {
	private final static Log log = Log.getLogger(PlatformDaoImpl.class);
	@SuppressWarnings("unchecked")
	@Override
	public List<PlatformBean> getAllPlatformBeans(String regionName) {
			log.info("[PlatformDaoImpl.getAllPlatformBeans][begin]");
			List<PlatformBean> list = null;
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
				sql.append("    Platform_FK as platformFk, ");
				sql.append("    platform, ");
				sql.append("    sequence, ");
				sql.append("    IsAvailable, ");
				sql.append("    P_Platform as PPlatform, ");
				sql.append("    updateTime, ");
				sql.append("    description ");
				sql.append("FROM ");
				sql.append("    d_platform_m ");
				sql.append("WHERE ");
				sql.append("    IsAvailable = 1 ");
				sql.append("ORDER BY ");
				sql.append("    sequence asc ");
				list = qr.query(conn, sql.toString(), new BeanListHandler(PlatformBean.class),new Object[]{});
				conn.close();
			} catch (SQLException e) {
				log.error("[PlatformDaoImpl.getAllPlatformBeans] [{0}]", e.getMessage());
			}finally{
				try {
					BaseDAO.close(conn);
				} catch (SQLException e) {
					log.error("[PlatformDaoImpl.getAllPlatformBeans] [{0}]", e.getMessage());
				}
			}
			log.info("[PlatformDaoImpl.getAllPlatformBeans][end]");
			return list;
	 }
}
