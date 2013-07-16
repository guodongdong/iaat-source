package com.iaat.dao.impl.overlap;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.iaat.dao.overlap.CrossContrastDao;
import com.iaat.dbutils.BaseDAO;
import com.iaat.json.LatitudeBean;
import com.iaat.json.overlap.ContrastSearchBean;
import com.iaat.json.overlap.CrossContrastBean;
import com.iaat.model.PlatformBean;
import com.iaat.share.ErrorCode;
import com.iaat.util.DateUtil;
import com.nokia.ads.common.util.Log;
import com.nokia.ads.platform.backend.core.UncheckedException;

public class CrossContrastDaoImpl implements CrossContrastDao {
	private final static Log log = Log.getLogger(CrossContrastDaoImpl.class);
	@Override
	public CrossContrastBean getCorssConstrasts(ContrastSearchBean searchBar) {
		log.info("[CrossContrastDaoImpl.getCorssConstrasts][begin]");
		Connection conn = null;
		CrossContrastBean crossBean = null;
		try{
			conn = BaseDAO.openConnection(searchBar.getProvince());
			QueryRunner qr= new QueryRunner();
			StringBuffer sqlStr = new StringBuffer();
			String tableName = this.getTableName(searchBar);
			sqlStr.append("SELECT ");
			sqlStr.append("    count(UV)AS UV, ");
			sqlStr.append("    count(UV)AS totalUser,");
			sqlStr.append("    sum( CASE  WHEN interTime > 0 THEN 1 ELSE 0  END) as oldUser, ");
			sqlStr.append("    sum( CASE  WHEN interTime <= 0 THEN 1 ELSE  0 END)AS newUser, ");
			sqlStr.append("    count(UV) as activeUser, ");
			sqlStr.append("    sum( CASE  WHEN wap_PV = 1 THEN 1  ELSE 0 END) onceAccessUser, ");
			sqlStr.append("    sum( CASE  WHEN wap_PV <= 0 and web_PV >=0 THEN  1 ELSE  0 END) as onlyLoginUser, ");
			sqlStr.append("    sum(web_PV)/count(UV) as avgLoginUser, ");
			sqlStr.append("    sum(wap_PV)/count(UV) as avgAccessUser, ");
			sqlStr.append("    sum(interTime)/count(UV) as avgIntervalTime ");
			sqlStr.append("FROM ");
			sqlStr.append("    (SELECT ");
			sqlStr.append("        count(DISTINCT IMEI_FK)AS UV, ");
			sqlStr.append("        sum(wap_PV)AS wap_PV, ");
			sqlStr.append("        sum(web_PV)AS web_PV, ");
			sqlStr.append("        sum(loginNum)logNum, ");
			sqlStr.append("        avg(interval_time)interTime, ");
			sqlStr.append("        1 AS sequence ");
			sqlStr.append("    FROM  ");
			sqlStr.append(tableName+" as f");
			sqlStr.append("    LEFT JOIN d_date   d  ON d.Date_FK = f.Date_FK ");
			sqlStr.append("    LEFT JOIN d_region re ON re.Region_FK = f.Region_FK ");
			sqlStr.append("    WHERE 1=1 ");
			Object[] parames = this.setConditionsByParams(searchBar, sqlStr);
			setDateCondition(searchBar, sqlStr);
			sqlStr.append("    GROUP BY ");
			sqlStr.append("        IMEI_FK ");
			sqlStr.append("    ) d ");
			sqlStr.append("GROUP BY ");
			sqlStr.append("    d.sequence ");
			BeanHandler<CrossContrastBean> rsh = new BeanHandler<CrossContrastBean>(CrossContrastBean.class);
			crossBean = qr.query(conn, sqlStr.toString(), rsh,parames);			
		}catch(Exception e){
			e.printStackTrace();
			log.error("[CrossContrastDaoImpl.getCorssConstrasts] [{0}]", e.getMessage());
			throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
		}finally{
			try {
				BaseDAO.close(conn);
			} catch (SQLException e) {
				e.printStackTrace();
				log.error("[CrossContrastDaoImpl.getCorssConstrasts] [{0}]", e.getMessage());
				throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
			}
		}
		log.info("[CrossContrastDaoImpl.getCorssConstrasts][end]");
		return crossBean;
	}
	@Override
	public CrossContrastBean getCorssConstrastsByTotal(
			ContrastSearchBean searchBar) { 
		log.info("[CrossContrastDaoImpl.getCorssConstrastsByTotal][begin]");
		Connection conn = null;
		CrossContrastBean crossBean = null;
		try{
			conn = BaseDAO.openConnection(searchBar.getProvince());
			QueryRunner qr= new QueryRunner();
			StringBuffer sqlStr = new StringBuffer();
			sqlStr.append(" SELECT ");
			sqlStr.append("   sum(wap_PV) AS wap_PV, ");
			sqlStr.append("   sum(web_PV) AS web_PV,");
			sqlStr.append("   sum(web_PV+wap_PV) AS PV");
			sqlStr.append(" FROM  ");
			sqlStr.append("  f_total_user_situation as f");
			sqlStr.append("  LEFT JOIN d_date d ON d.Date_FK = f.Date_FK ");
			sqlStr.append("  LEFT JOIN d_region re ON re.Region_FK = f.Region_FK ");
			sqlStr.append("  WHERE 1=1 ");
			Object[] parames = this.setConditionsByParams(searchBar, sqlStr);
			setDateCondition(searchBar, sqlStr);
			BeanHandler<CrossContrastBean> rsh = new BeanHandler<CrossContrastBean>(CrossContrastBean.class);
			crossBean = qr.query(conn, sqlStr.toString(), rsh,parames);			
		}catch(SQLException e){
			e.printStackTrace();
			log.error("[CrossContrastDaoImpl.getCorssConstrastsByTotal] [{0}]", e.getMessage());
			throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
		}finally{
			try {
				BaseDAO.close(conn);
			} catch (SQLException e) {
				e.printStackTrace();
				log.error("[CrossContrastDaoImpl.getCorssConstrastsByTotal] [{0}]", e.getMessage());
				throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
			}
		}
		log.info("[CrossContrastDaoImpl.getCorssConstrastsByTotal][end]");
		return crossBean;
	}
	private void setDateCondition(ContrastSearchBean searchBar,
			StringBuffer sqlStr) {
		if(searchBar.getStartDateTime()!=null&&searchBar.getEndDateTime()!=null){
			sqlStr.append(" AND d.time BETWEEN '" + DateUtil.c2Str(searchBar.getStartDateTime()) + "' AND '" + DateUtil.c2Str(searchBar.getEndDateTime()) + "'");
		}
		sqlStr.append(" AND d.hour_of_day BETWEEN '" + searchBar.getStartTime() + "' AND '" + searchBar.getEndTime() + "'");
	}
	public  String getTableName(ContrastSearchBean bar) {
		LatitudeBean  latBean = LatitudeBean.getInstance();
		PlatformBean platBean = latBean.getPlatFormBean(bar.getPlatform());
		String tableName = null;
		if(platBean.getPlatform().toLowerCase().contains("xpress")){
				tableName =  "f_xpress_user_situation";	
		}else{
				tableName =  "f_tom_user_situation";
		}
		return tableName;
	}
	public  Object[] setConditionsByParams(ContrastSearchBean searchBar, StringBuffer sql) {
		List<Object> parames = new ArrayList<Object>();
		if (isNotNull(searchBar.getProvinceFk())) {
			sql.append(" AND re.P_code = ? ");
			parames.add(searchBar.getProvince());
		}
		if (isNotNull(searchBar.getCityFk())) {
			sql.append(" AND f.Region_FK = ? ");
			parames.add(searchBar.getCityFk());
		}
		
		if (isNotNull(searchBar.getPlatform())) {
			sql.append(" AND f.Platform_FK = ? ");
			parames.add(searchBar.getPlatform());
		}
		if (isNotNull(searchBar.getAp())) {
			sql.append(" AND f.AP_FK = ?  ");
			parames.add(searchBar.getAp());
		}
		if (isNotNull(searchBar.getOperator())) {
			sql.append(" AND f.Operator_FK = ? ");
			parames.add(searchBar.getOperator());
		}
		if (isNotNull(searchBar.getChannel())) {
			sql.append(" AND f.Channel_FK = ? ");
			parames.add(searchBar.getChannel());
		}
		if (isNotNull(searchBar.getTerminalType())) {
			sql.append(" AND f.Terminal_FK = ? ");
			parames.add(searchBar.getTerminalType());
		}
		if(isNotNull(searchBar.getCategory())){
			sql.append(" AND f.Category_FK = ? ");
			parames.add(searchBar.getCategory());
		}
		Object[] resultes = parames.toArray(new Object[]{});
		return resultes;
	}

	private Boolean isNotNull(Integer value) {
		if (value != null && value.intValue() != -1) {
			return true;
		}
		return false;
	}

}
