package com.iaat.dao.impl.latitude;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.iaat.dao.latitude.ApDao;
import com.iaat.dbutils.BaseDAO;
import com.iaat.model.ApBean;
import com.iaat.share.Params;
import com.nokia.ads.common.util.Log;


/**    
 * @name ApDaoImpl
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
public class ApDaoImpl implements ApDao {


	private final static Log log = Log.getLogger(ApDaoImpl.class);
	@SuppressWarnings("unchecked")
	@Override
	public List<ApBean> getAllApBeans(String regionName) {
		log.info("[ApDaoImpl.getAllApBeans] [begin]");
		List<ApBean> list = null;
		Connection conn = null;
		try {
			conn=BaseDAO.openConnection(Params.REGION_NAME);
			QueryRunner qr = new QueryRunner();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT ");
			sql.append("    AP_FK as apFk, ");
			sql.append("    ap, ");
			sql.append("    sequence, ");
			sql.append("    IsAvailable, ");
			sql.append("    P_AP as PAp, ");
			sql.append("    updateTime, ");
			sql.append("    description ");
			sql.append("FROM ");
			sql.append("    d_ap_m ");
			sql.append("WHERE ");
			sql.append("    IsAvailable = 1 ");
			sql.append("ORDER BY ");
			sql.append("    sequence asc ");
			list = qr.query(conn, sql.toString(), new BeanListHandler(ApBean.class),new Object[]{});
			conn.close();
		} catch (SQLException e) {
			log.error("[ApDaoImpl.getInputUVCount] [{0}]", e.getMessage());
		}finally{
			try {
				BaseDAO.close(conn);
			} catch (SQLException e) {
				log.error("[ApDaoImpl.getInputUVCount] [{0}]", e.getMessage());
			}
		}
		log.info("[ApDaoImpl.getAllApBeans] [end]");
		return list;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<ApBean> getDistinctApBeans(String regionName) {
		log.info("[ApDaoImpl.getAllApBeans] [begin]");
		List<ApBean> list = null;
		Connection conn = null;
		try {
			conn=BaseDAO.openConnection(Params.REGION_NAME);
			QueryRunner qr = new QueryRunner();
			StringBuffer sqlStr = new StringBuffer();
			sqlStr.append("SELECT ");
			sqlStr.append("    ap,sequence ");
			sqlStr.append("FROM ");
			sqlStr.append("    d_ap_m ");
			sqlStr.append("WHERE ");
			sqlStr.append("    IsAvailable = 1 ");
			sqlStr.append("GROUP BY ");
			sqlStr.append("    ap ");
			sqlStr.append(" ORDER BY sequence ");
			
			list = qr.query(conn, sqlStr.toString(), new BeanListHandler(ApBean.class),new Object[]{});
			conn.close();
		} catch (SQLException e) {
			log.error("[ApDaoImpl.getInputUVCount] [{0}]", e.getMessage());
		}finally{
			try {
				BaseDAO.close(conn);
			} catch (SQLException e) {
				log.error("[ApDaoImpl.getInputUVCount] [{0}]", e.getMessage());
			}
		}
		log.info("[ApDaoImpl.getAllApBeans] [end]");
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ApBean> getOperatorApBeans(String regionName) {
		log.info("[ApDaoImpl.getTerminalApBeans] [begin]");
		List<ApBean> list = null;
		Connection conn = null;
		try {
			conn=BaseDAO.openConnection(Params.REGION_NAME);
			QueryRunner qr = new QueryRunner();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT ");
			sql.append("    AP_FK as apFk, ");
			sql.append("    ap, ");
			sql.append("    sequence, ");
			sql.append("    IsAvailable, ");
			sql.append("    P_AP as PAp, ");
			sql.append("    updateTime, ");
			sql.append("    description ,");
			sql.append("    operator_fk as operatorFk");
			sql.append(" FROM ");
			sql.append("    d_ap_m ");
			sql.append(" WHERE ");
			sql.append("    IsAvailable = 1 ");
			sql.append(" ORDER BY ");
			sql.append("    operator_fk,sequence ");
			list = qr.query(conn, sql.toString(), new BeanListHandler(ApBean.class),new Object[]{});
			conn.close();
		} catch (SQLException e) {
			log.error("[ApDaoImpl.getTerminalApBeans] [{0}]", e.getMessage());
		}finally{
			try {
				BaseDAO.close(conn);
			} catch (SQLException e) {
				log.error("[ApDaoImpl.getTerminalApBeans] [{0}]", e.getMessage());
			}
		}
		log.info("[ApDaoImpl.getTerminalApBeans] [end]");
		return list;
	}

}
