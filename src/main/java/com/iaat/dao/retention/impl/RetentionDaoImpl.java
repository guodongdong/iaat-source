package com.iaat.dao.retention.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import com.iaat.dao.retention.RetentionDao;
import com.iaat.dbutils.BaseDAO;
import com.iaat.json.LatitudeBean;
import com.iaat.json.SearchBar;
import com.iaat.json.retention.RetentionBean;
import com.iaat.model.ApBean;
import com.iaat.model.OperatorBean;
import com.iaat.model.PlatformBean;
import com.iaat.model.TerminalBean;
import com.iaat.share.ErrorCode;
import com.iaat.util.DateUtil;
import com.iaat.util.ValidateUtils;
import com.nokia.ads.common.util.Log;
import com.nokia.ads.platform.backend.core.UncheckedException;

/**
 * 
 * @name RetentionDaoImpl 
 * 
 * @description  留存业务服务类 
 * 
 * MORE_INFORMATION
 * 
 * @author Lixw
 * 
 * @since 2013-6-28
 *       
 * @version 1.0
 */
public class RetentionDaoImpl implements RetentionDao {

	
	private final static Log log = Log.getLogger(RetentionDaoImpl.class);
	
	

	
	/**
	 * 根据daily、weekly、monthly设置分组条件
	 * @param searchBar
	 * @param sql
	 * @param fk
	 */
	private void setGroupByPeriod(SearchBar searchBar,StringBuffer sql,String fk){
		if(searchBar.getType().equals(SearchBar.DAILY)){
			if(searchBar.getDateEqual()){
				sql.append(" GROUP BY   p." + searchBar.getByType() + ",hour_of_day  ORDER BY p." + searchBar.getByType() + ",da.hour_of_day ");
			}else{
				sql.append(" GROUP BY  p." + searchBar.getByType() + ",time  ORDER BY p." + searchBar.getByType() + ",da.time ");
			}
		}else if(searchBar.getType().equals(SearchBar.WEEKLY)){
			sql.append(" GROUP BY  p." + searchBar.getByType() + ",week_num ORDER BY p." + searchBar.getByType() + ",da.time");
		}else if(searchBar.getType().equals(SearchBar.MONTHLY)){
			sql.append(" GROUP BY  p." + searchBar.getByType() + ",da.month_of_year,da.year ORDER BY p." + searchBar.getByType() + ",da.time");
		}
	}
	
	

	
	/**
	 * 设置时间条件
	 * @param bar
	 * @param sql
	 */
	private void setDateTime(SearchBar bar,StringBuffer sql){
		if (ValidateUtils.isNotNull(bar.getStartDateTime()) && ValidateUtils.isNotNull(bar.getEndDateTime())) {
			String tc = " AND time BETWEEN '" + DateUtil.calendar2String(bar.getStartDateTime()) + "' AND '" + DateUtil.calendar2String(bar.getEndDateTime()) + "'";
			sql.append(tc);
		}
		if (bar.isStartTimeVerify() && bar.isEndTimeVerify()) {
			sql.append(" and hour_of_day BETWEEN '" + bar.getStartTime() + "' AND '" + bar.getEndTime() + "' ");
		}
	}
	
	/**
	 * 获取主键字段名称
	 * @param type
	 * @return
	 */
	private String getFieldName(String type){
		if(type.equals(SearchBar.BY_PLATFORM)){
			return "platform_fk";
		}else if(type.equals(SearchBar.BY_TERMINALTYPE)){
			return "terminal_fk";
		}else if(type.equals(SearchBar.BY_OPERATOR)){
			return "operator_fk";
		}else if(type.equals(SearchBar.BY_AP)){
			return "ap_fk";
		}
		return "";
	}
	
	/**
	 * 拼接表关联条件
	 * @param searchbar
	 * @param sql
	 */
	public void concatCondition(SearchBar searchbar,StringBuffer sql){
		
		String fk = getFieldName(searchbar.getByType());
		if(searchbar.getByType().equals(SearchBar.BY_PLATFORM)){
			sql.append(" left JOIN d_platform p ON 1 = 1 ");
		}else if(searchbar.getByType().equals(SearchBar.BY_TERMINALTYPE)){
			//终端型号数据较多，如果传递-1 则 查询所有，
			StringBuffer  ids = new StringBuffer();
			if(ValidateUtils.isNotNull(searchbar.getTerminalFk()) && searchbar.getTerminalFk().length>0){
				if(searchbar.getTerminalFk()[0]!=-1){
					ids.append(" and p."+fk + " in (");
					Integer[] fks=  searchbar.getTerminalFk();
					for(int i = 0 ;i<fks.length;i++){
						if(i+1 != fks.length){
							if(ValidateUtils.isNotNull(fks[i])){
								ids.append( fks[i] + ",");
							}
						}
					}
					ids.append( fks[fks.length-1] + "  ) ");
				}
			}
			sql.append(" left JOIN d_terminal p ON 1 = 1  " +ids.toString());
		}else if(searchbar.getByType().equals(SearchBar.BY_OPERATOR)){
			sql.append(" left JOIN d_operator p ON 1 = 1 ");
		}else if(searchbar.getByType().equals(SearchBar.BY_AP)){
			sql.append(" left JOIN d_ap p ON 1 = 1 ");
		} 
		sql.append(" and p."+fk + " <> -1 ");
		
		String tableName = "f_"+searchbar.getLogSource()+"_user_situation";
//		sql.append(" left JOIN " + tableName + " f  ON da.Date_FK = f.Date_FK ");
		
		sql.append(" LEFT JOIN (SELECT t.* from " + tableName + " t inner join  d_region re ON re.Region_FK = t.Region_FK  where 1=1 ");
		setCity(searchbar, sql);
		sql.append(" ) t ON da.Date_FK = t.Date_FK ");
	
		sql.append(" and t." + fk +" = p." + fk );
		
		
		if(searchbar.isChannelKeyVerify())
		choosePropertyCondition(" Channel_FK",searchbar.getChannelKey(),sql);
		
		
		sql.append(" WHERE 1=1 ");			
		setDateTime(searchbar,sql);
	}
	
	/**
	 * 设置region条件
	 * @param searchbar
	 * @param sql
	 */
	private void setCity(SearchBar searchbar, StringBuffer sql) {
		if(ValidateUtils.isNotNull(searchbar.getCity())) 
		choosePropertyCondition("re.code",searchbar.getCity(),sql);
		if(ValidateUtils.isNotNull(searchbar.getProvince()))
		choosePropertyCondition("re.P_Code",searchbar.getProvince(),sql);
	}
	/**
	 * 拼接属性添加
	 * @param property
	 * @param o
	 * @param sql
	 */
	private void choosePropertyCondition(String property,Object o,StringBuffer sql){
		if(ValidateUtils.isNotNull(o)){
			sql.append(" and " + property+ " = '" + o  +"' ");
		}
	}

	/* 
	 * 获得所有平台、接入点、运营商、终端的留存用户数据
	 * (non-Javadoc)
	 * @see com.iaat.dao.retention.RetentionDao#getRetentionAll(com.iaat.json.SearchBar)
	 */
	@Override
	public List<RetentionBean> getRetentionAll(SearchBar searchBar) {
		
		Connection  c = null;
		try {
			c =  BaseDAO.openConnection(searchBar.getProvince());
			QueryRunner qr = new QueryRunner();
			StringBuffer sql = new StringBuffer();
			String fk =	getFieldName(searchBar.getByType());
			sql.append(" SELECT ");
			setSelectFieldByPeriod(searchBar,sql);
			sql.append( searchBar.getByType() +" as name, ");
			sql.append(" 	count(DISTINCT t.imei_fk) as count ");
			sql.append(" FROM ");
			sql.append(" 	d_date da ");
			concatCondition(searchBar,sql);
			setGroupByPeriod(searchBar, sql, fk);
			System.out.println(sql.toString());
			return qr.query(c,sql.toString(),new BeanListHandler<RetentionBean>(RetentionBean.class));
		} catch (SQLException e) {
//			e.printStackTrace();
			log.error("[RetentionDaoImpl.getRetentionAll] [{0}]", e.getMessage());
			throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
		}finally{
			try {
				BaseDAO.close(c);
			} catch (SQLException e) {
//				e.printStackTrace();
				log.error("[RetentionDaoImpl.getRetentionAll] [{0}]", e.getMessage());
				throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
			}
		}
	}
	
	
	/* 
	 * 根据日期分组获得所有平台、接入点、运营商、终端的留存用户数据总和
	 * (non-Javadoc)
	 * @see com.iaat.dao.retention.RetentionDao#getRetentionSum(com.iaat.json.SearchBar)
	 */
	public List<RetentionBean> getRetentionSum(SearchBar searchBar) {
		
		Connection  c = null;
		try {
			c =  BaseDAO.openConnection(searchBar.getProvince());
			QueryRunner qr = new QueryRunner();
			StringBuffer sql = new StringBuffer();
			String fk =	getFieldName(searchBar.getByType());
			sql.append(" select sum(count) as count, ");
			setSelectSumFieldByPeriod(searchBar,sql);
			sql.append(" from ( ");
			sql.append(" SELECT ");
			setSelectFieldByPeriod(searchBar,sql);
			sql.append( searchBar.getByType() +" as name, ");
			sql.append(" 	count(DISTINCT t.imei_fk) as count ");
			sql.append(" FROM ");
			sql.append(" 	d_date da ");
			concatCondition(searchBar,sql);
			setGroupByPeriod(searchBar, sql, fk);
			sql.append(") t ");
			
			if(searchBar.getType().equals(SearchBar.DAILY)){
				if(searchBar.getDateEqual()){
					sql.append(" GROUP BY  hour  ");
				}else{
					sql.append(" GROUP BY  time  ");
				}
			}else if(searchBar.getType().equals(SearchBar.WEEKLY)){
				sql.append(" GROUP BY   week_num ");
			}else if(searchBar.getType().equals(SearchBar.MONTHLY)){
				sql.append(" GROUP BY month,year ");
			} 
			
			System.out.println(sql.toString());
			return  qr.query(c,sql.toString(),new BeanListHandler<RetentionBean>(RetentionBean.class));
		} catch (SQLException e) {
//			e.printStackTrace();
			log.error("[RetentionDaoImpl.getRetentionSum] [{0}]", e.getMessage());
			throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
		}finally{
			try {
				BaseDAO.close(c);
			} catch (SQLException e) {
//				e.printStackTrace();
				log.error("[RetentionDaoImpl.getRetentionSum] [{0}]", e.getMessage());
				throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
			}
		}
	}
	
	
	
	
	
	/**
	 * 根据daily、weekly、monthly类别设置查询的字段
	 * @param bar
	 * @param sql
	 */
	private void setSelectSumFieldByPeriod(SearchBar bar ,StringBuffer sql){
		if (SearchBar.WEEKLY.equals(bar.getType())) {
			sql.append("   beginDate,endDate, week_num ");
		}else if (SearchBar.MONTHLY.equals(bar.getType())) {
			sql.append("   beginDate,endDate, month ,year ");
		}else {
			if (bar.getDateEqual()) {
				sql.append("  hour  ");
			}else{
				sql.append("  time ");
			}
		}
	}
	
	/**
	 * 根据daily、weekly、monthly类别设置查询的字段
	 * @param bar
	 * @param sql
	 */
	private void setSelectFieldByPeriod(SearchBar bar ,StringBuffer sql){
		if (SearchBar.WEEKLY.equals(bar.getType())) {
			sql.append("  MIN(da.time) AS `beginDate`,MAX(da.time) AS `endDate`, week_num,");
		}else if (SearchBar.MONTHLY.equals(bar.getType())) {
			sql.append("  MIN(da.time) AS `beginDate`,MAX(da.time) AS `endDate`, month_of_year as month,year,");
		}else {
			if (bar.getDateEqual()) {
				sql.append("  hour_of_day AS `hour` , ");
			}else{
				sql.append("  da.time as time ,");
			}
		}
	}
	
	
	
}
