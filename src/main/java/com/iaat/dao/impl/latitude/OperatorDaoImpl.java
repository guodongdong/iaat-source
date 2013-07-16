package com.iaat.dao.impl.latitude;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.iaat.dao.latitude.OperatorDao;
import com.iaat.dbutils.BaseDAO;
import com.iaat.model.OperatorBean;
import com.iaat.share.Params;
import com.nokia.ads.common.util.Log;
import com.nokia.ads.common.util.StringUtils;

/**    
 * @name OperatorDaoImpl
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
public class OperatorDaoImpl implements OperatorDao {

	private final static Log log = Log.getLogger(OperatorDaoImpl.class);
	@SuppressWarnings("unchecked")
	@Override
	public List<OperatorBean> getAllOperatorBeans(String regionName) {
			log.info("[OperatorDaoImpl.getAllOperatorBeans][begin]");
			List<OperatorBean> list = null;
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
				sql.append("    Operator_FK as operatorFk, ");
				sql.append("    operator, ");
				sql.append("    code, ");
				sql.append("    sequence, ");
				sql.append("    IsAvailable, ");
				sql.append("    P_operator as POperator, ");
				sql.append("    P_code as PCode, ");
				sql.append("    updateTime, ");
				sql.append("    description ");
				sql.append("FROM ");
				sql.append("    d_operator_m ");
				sql.append("WHERE ");
				sql.append("    IsAvailable = 1 ");
				sql.append("ORDER BY ");
				sql.append("    sequence asc ");
				list = qr.query(conn, sql.toString(), new BeanListHandler(OperatorBean.class),new Object[]{});
				conn.close();
			} catch (SQLException e) {
				log.error("[OperatorDaoImpl.getAllOperatorBeans] [{0}]", e.getMessage());
			}finally{
				try {
					BaseDAO.close(conn);
				} catch (SQLException e) {
					log.error("[OperatorDaoImpl.getAllOperatorBeans] [{0}]", e.getMessage());
				}
			}
			log.info("[OperatorDaoImpl.getAllOperatorBeans][end]");
			return list;
	}


}
