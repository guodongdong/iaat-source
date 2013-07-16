package com.iaat.dao.impl.latitude;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.iaat.dao.latitude.TerminalDao;
import com.iaat.dbutils.BaseDAO;
import com.iaat.model.TerminalBean;
import com.iaat.share.Params;
import com.nokia.ads.common.util.Log;
import com.nokia.ads.common.util.StringUtils;

/**    
 * @name TerminalDaoImpl
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
public class TerminalDaoImpl implements TerminalDao {

	private final static Log log = Log.getLogger(TerminalDaoImpl.class);
	@SuppressWarnings("unchecked")
	@Override
	public List<TerminalBean> getAllTerminalBeans(String regionName) {
			log.info("[TerminalDaoImpl.getAllTerminalBeans][begin]");
			List<TerminalBean> list = null;
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
				sql.append("    Terminal_FK as terminalFk, ");
				sql.append("    Platform_FK as platformFK, ");
				sql.append("    terminal, ");
				sql.append("    sequence, ");
				sql.append("    IsAvailable, ");
				sql.append("    P_Terminal, ");
				sql.append("    updateTime, ");
				sql.append("    appear_time appearTime, ");
				sql.append("    description ");
				sql.append("FROM ");
				sql.append("    d_terminal_m ");
				sql.append("WHERE ");
				sql.append("    IsAvailable = 1 ");
				sql.append("ORDER BY ");
				sql.append("    sequence");
				list = qr.query(conn, sql.toString(), new BeanListHandler(TerminalBean.class),new Object[]{});
				conn.close();
			} catch (SQLException e) {
				log.error("[TerminalDaoImpl.getAllTerminalBeans] [{0}]", e.getMessage());
			}finally{
				try {
					BaseDAO.close(conn);
				} catch (SQLException e) {
					log.error("[TerminalDaoImpl.getAllTerminalBeans] [{0}]", e.getMessage());
				}
			}
			log.info("[TerminalDaoImpl.getAllTerminalBeans][end]");
		 return list;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<TerminalBean> getAllTerminalDisBeans(String regionName) {
			log.info("[TerminalDaoImpl.getAllTerminalDisBeans][begin]");
			List<TerminalBean> list = null;
			Connection conn = null;
			try {
				if(regionName!=null&&StringUtils.hasLength(regionName)){
					conn = BaseDAO.openConnection(regionName);	
				}else{
					conn = BaseDAO.openConnection(Params.REGION_NAME);
				}
				QueryRunner qr = new QueryRunner();
				StringBuffer sqlStr = new StringBuffer();
				sqlStr.append("SELECT ");
				sqlStr.append("    terminalFk, ");
				sqlStr.append("    platformFK, ");
				sqlStr.append("    terminal, ");
				sqlStr.append("    sequence, ");
				sqlStr.append("    IsAvailable, ");
				sqlStr.append("    P_Terminal, ");
				sqlStr.append("    updateTime, ");
				sqlStr.append("    appearTime, ");
				sqlStr.append("    description ");
				sqlStr.append("FROM ");
				sqlStr.append("    (SELECT ");
				sqlStr.append("        Terminal_FK as terminalFk, ");
				sqlStr.append("        Platform_FK as platformFK, ");
				sqlStr.append("        terminal, ");
				sqlStr.append("        sequence, ");
				sqlStr.append("        IsAvailable, ");
				sqlStr.append("        P_Terminal, ");
				sqlStr.append("        updateTime, ");
				sqlStr.append("        appear_time appearTime, ");
				sqlStr.append("        description ");
				sqlStr.append("    FROM ");
				sqlStr.append("        d_terminal_m ");
				sqlStr.append("    WHERE ");
				sqlStr.append("        IsAvailable = 1 ");
				sqlStr.append("    ORDER BY ");
				sqlStr.append("        sequence DESC ");
				sqlStr.append("    ) d ");
				sqlStr.append("GROUP BY ");
				sqlStr.append("    terminal ");
				list = qr.query(conn, sqlStr.toString(), new BeanListHandler(TerminalBean.class),new Object[]{});
				conn.close();
			} catch (SQLException e) {
				log.error("[TerminalDaoImpl.getAllTerminalDisBeans] [{0}]", e.getMessage());
			}finally{
				try {
					BaseDAO.close(conn);
				} catch (SQLException e) {
					log.error("[TerminalDaoImpl.getAllTerminalDisBeans] [{0}]", e.getMessage());
				}
			}
			log.info("[TerminalDaoImpl.getAllTerminalDisBeans][end]");
			return list;
	}

}
