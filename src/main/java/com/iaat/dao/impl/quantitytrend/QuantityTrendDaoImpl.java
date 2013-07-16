package com.iaat.dao.impl.quantitytrend;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.iaat.dao.BaseDaoUtils;
import com.iaat.dao.quantitytrend.QuantityTrendDao;
import com.iaat.dbutils.BaseDAO;
import com.iaat.dbutils.connection.BaseConnection;
import com.iaat.json.SearchBar;
import com.iaat.json.quantitytrend.QuantityTrendChartBean;
import com.iaat.json.quantitytrend.QuantityTrendListBean;
import com.iaat.json.quantitytrend.QuantityTrendTotalBean;
import com.iaat.share.ErrorCode;
import com.iaat.util.DateUtil;
import com.iaat.util.ValidateUtils;
import com.nokia.ads.common.util.Log;
import com.nokia.ads.platform.backend.core.UncheckedException;
import com.nokia.ads.platform.backend.util.Paging;


/**
 * 
 * @name QuantityTrendDaoImpl
 * 
 * @description CLASS_DESCRIPTION
 * 
 * MORE_INFORMATION
 * 
 * @author Lixw
 * 
 * @since 2013-6-24
 *       
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class QuantityTrendDaoImpl extends BaseDaoUtils implements QuantityTrendDao{
	
	private final static Log log = Log.getLogger(QuantityTrendDaoImpl.class);
	
	/* 
	 * 根据SearchBar条件按天统计WapPv WebPv 和  uv
	 * (non-Javadoc)
	 * @see com.iaat.dao.quantitytrend.QuantityTrendDao#getQuantityTrendChart(com.iaat.json.SearchBar)
	 */
	@Override
	public List<QuantityTrendChartBean> getQuantityTrendChartByDaily(SearchBar searchbar) {
	
		List<QuantityTrendChartBean> list = null;
		Connection c = null;
		try {
			c =BaseDAO.openConnection(searchbar.getProvince());
			QueryRunner qr = new QueryRunner();
			StringBuffer sql = new StringBuffer();
			
			sql.append("SELECT");
			sql.append("    da.time,");
			sql.append("    hour_of_day HOUR,");
			sql.append("    SUM(f.wap_PV) wapPv,");
			sql.append("    sum(f.web_PV) webPv,");
			sql.append("    sum(t.web_PV) loginPv,");
			sql.append("    sum(t.wap_PV) startPv,");
			sql.append("    count(DISTINCT t.IMEI_FK) uv");
			sql.append(" 	FROM");
			sql.append("    d_date da ");
			concatCondition(searchbar,sql);
			if (searchbar.getDateEqual()) {
				sql.append(" group by da.hour_of_day order by da.hour_of_day");
			}else {
				sql.append(" group by da.day_of_year order by da.time");
			}
			System.out.println(sql.toString());
			list = qr.query(c, sql.toString(), new BeanListHandler<QuantityTrendChartBean>(QuantityTrendChartBean.class));
		}catch (SQLException e) {
			e.printStackTrace();
			log.error("QuantityTrendDaoImpl.getQuantityTrendChartByDaily] [{0}]", e.getMessage());
			throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
		}finally{
				try {
					if (ValidateUtils.isNotNull(c)) {
						BaseDAO.close(c);
					}
				} catch (Exception e) {
					e.printStackTrace();
					log.error("[ProfileDaoImpl.getQuantityTrendChartByDaily] [{0}]", e.getMessage());
					throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
				}
		}
		return list;
	}
	
	/* 
	 * 根据SearchBar条件按周统计WapPv WebPv 和  uv
	 * (non-Javadoc)
	 * @see com.iaat.dao.quantitytrend.QuantityTrendDao#getQuantityTrendChartByWeekly(com.iaat.json.SearchBar)
	 */
	@Override
	public List<QuantityTrendChartBean> getQuantityTrendChartByWeekly(SearchBar searchbar)
			throws Exception {
		Connection c = null;
		try {
			c = BaseDAO.openConnection(searchbar.getProvince());
			QueryRunner qr = new QueryRunner();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT");
			sql.append("    min(time) startTime,max(time) endTime,");
			sql.append("    SUM(f.wap_PV) wapPv,");
			sql.append("    sum(f.web_PV) webPv,");
			sql.append("    sum(t.web_PV) loginPv,");
			sql.append("    sum(t.wap_PV) startPv,");
			sql.append("    count(DISTINCT t.IMEI_FK) uv");
			sql.append(" 	FROM");
			sql.append("    d_date da ");
//			sql.append("select  min(time) startTime,max(time) endTime,SUM(f.wap_PV) wapPv, sum(f.web_PV) webPv,count(DISTINCT f.IMEI_FK) uv from   d_date da ");
			concatCondition(searchbar,sql);
			
			 if (SearchBar.WEEKLY.equals(searchbar.getType())) {
				 sql.append(" group by week_num ");
			}else if (SearchBar.MONTHLY.equals(searchbar.getType())) {
				sql.append(" group by da.month_of_year,da.year ");
			}
			sql.append(" order by time ");
			System.out.println(sql.toString());
			return qr.query(c, sql.toString(), new BeanListHandler<QuantityTrendChartBean>(QuantityTrendChartBean.class));
		}catch (SQLException e) {
			e.printStackTrace();
			log.error("QuantityTrendDaoImpl.getQuantityTrendChartByWeekly] [{0}]", e.getMessage());
			throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
		}finally{
				try {
					if (ValidateUtils.isNotNull(c)) {
						BaseDAO.close(c);
					}
				} catch (Exception e) {
					e.printStackTrace();
					log.error("[ProfileDaoImpl.getQuantityTrendChartByWeekly] [{0}]", e.getMessage());
					throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
				}
		}
	}
	
	/* 
	 * 根据SearchBar条件按月统计WapPv WebPv 和  uv
	 * (non-Javadoc)
	 * @see com.iaat.dao.quantitytrend.QuantityTrendDao#getQuantityTrendChartByMonthly(com.iaat.json.SearchBar)
	 */
	@Override
	@Deprecated
	public List<QuantityTrendChartBean> getQuantityTrendChartByMonthly(SearchBar searchbar)
			throws Exception {
		List<QuantityTrendChartBean> list = null;
		Connection c = null;
		try {
			c = BaseDAO.openConnection(searchbar.getProvince());
			QueryRunner qr = new QueryRunner();
			StringBuffer sql = new StringBuffer();
//			sql.append("SELECT year,MONTH_of_year month ,min(time) startTime,max(time) endTime,SUM(f.wap_PV) wapPv, sum(f.web_PV) webPv,count(DISTINCT f.IMEI_FK) uv from d_date da ");
			sql.append("SELECT");
			sql.append("    min(time) startTime,max(time) endTime,");
			sql.append("    SUM(f.wap_PV) wapPv,");
			sql.append("    sum(f.web_PV) webPv,");
			sql.append("    sum(t.web_PV) loginPv,");
			sql.append("    sum(t.wap_PV) startPv,");
			sql.append("    count(DISTINCT t.IMEI_FK) uv");
			sql.append(" 	FROM");
			sql.append("    d_date da ");
			concatCondition(searchbar,sql);
			sql.append(" group by da.month_of_year,da.year ");
			sql.append(" order by da.time ");
			list = qr.query(c, sql.toString(), new BeanListHandler<QuantityTrendChartBean>(QuantityTrendChartBean.class));
			System.out.println(sql.toString());
		}catch (SQLException e) {
			e.printStackTrace();
			log.error("QuantityTrendDaoImpl.getQuantityTrendChartByMonthly] [{0}]", e.getMessage());
			throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
		}finally{
				try {
					if (ValidateUtils.isNotNull(c)) {
						BaseDAO.close(c);
					}
				} catch (Exception e) {
					e.printStackTrace();
					log.error("[ProfileDaoImpl.getQuantityTrendChartByMonthly] [{0}]", e.getMessage());
					throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
				}
		}
		return list;
	}
	
	/**
	 * 拼接表关联条件
	 * @param searchbar
	 * @param sql
	 */
	public void concatCondition(SearchBar searchbar,StringBuffer sql){
		String tableName = "f_"+searchbar.getLogSource()+"_user_situation";
		
		sql.append(" LEFT JOIN (SELECT f.* from f_total_user_situation f inner join  d_region re ON re.Region_FK = f.Region_FK  where 1=1  ");
		setCity(searchbar, sql);
		sql.append(" ) f ON da.Date_FK = f.Date_FK");
		setProperty(searchbar, sql, "f"); 		
		
		sql.append(" LEFT JOIN (SELECT t.* from " + tableName + " t inner join  d_region re ON re.Region_FK = t.Region_FK  where 1=1 ");
		setCity(searchbar, sql);
		sql.append(" ) t ON da.Date_FK = t.Date_FK");
		setProperty(searchbar, sql, "t");
		
//		sql.append(" left JOIN f_total_user_situation f  ON da.Date_FK = f.Date_FK "); 	
		 		
//		sql.append(" left JOIN " + tableName + " t  ON da.Date_FK = t.Date_FK ");
//		setProperty(searchbar, sql, "t"); 
		
		//sql.append(" left JOIN d_region re ON re.Region_FK = f.Region_FK and  re.Region_FK = t.Region_FK " );
		sql.append(" WHERE 1=1 ");			
		if (ValidateUtils.isNotNull(searchbar.getStartDateTime()) && ValidateUtils.isNotNull(searchbar.getEndDateTime())) {
//			sql.append(" and da.Date_FK >= " + getDateFk(searchbar, searchbar.getStartYear(), searchbar.getStartMonth(), searchbar.getStartDay(), searchbar.getStartTime()) + " ");
//			sql.append(" and da.Date_FK <= " + getDateFk(searchbar, searchbar.getEndYear(), searchbar.getEndMonth(), searchbar.getEndDay(), searchbar.getEndTime()) + " ");
			String tc = " AND time BETWEEN '" + DateUtil.calendar2String(searchbar.getStartDateTime()) + "' AND '" + DateUtil.calendar2String(searchbar.getEndDateTime()) + "'";
			sql.append(tc);
		}
		if (searchbar.isStartTimeVerify() && searchbar.isEndTimeVerify()) {
			sql.append(" and hour_of_day BETWEEN '" + searchbar.getStartTime() + "' AND '" + searchbar.getEndTime() + "' ");
		}
	}

	private void setCity(SearchBar searchbar, StringBuffer sql) {
		if(ValidateUtils.isNotNull(searchbar.getCity())) 
		choosePropertyCondition("re.code",searchbar.getCity(),sql);
		if(ValidateUtils.isNotNull(searchbar.getProvince()))
		choosePropertyCondition("re.P_Code",searchbar.getProvince(),sql);
	}

	/**
	 * 设置属性
	 * @param searchbar
	 * @param sql
	 * @param alias
	 */
	private void setProperty(SearchBar searchbar, StringBuffer sql, String alias) {
		if(searchbar.isAPKeyVerify())
		choosePropertyCondition(alias + ".AP_FK",searchbar.getAPKey(),sql);
		if(searchbar.isPlatformKeyVerify())
		choosePropertyCondition(alias + ".Platform_FK",searchbar.getPlatformKey(),sql);
		if(searchbar.isOperatorKeyVerify())
		choosePropertyCondition(alias + ".Operator_FK",searchbar.getOperatorKey(),sql);
		if(searchbar.isChannelKeyVerify())
		choosePropertyCondition(alias + ".Channel_FK",searchbar.getChannelKey(),sql);
		if(searchbar.isTerminalTypeKeyVerify())
		choosePropertyCondition(alias + ".Terminal_FK",searchbar.getTerminalTypeKey(),sql);			
	}
	
	/**
	 * 根据时间和小时获得Date_FK  暂不用
	 * @param searchbar TODO
	 * @param year TODO
	 * @param month TODO
	 * @param day TODO
	 * @param hour TODO
	 * @return
	 */
	@Deprecated
	public Integer getDateFk(SearchBar searchbar, int year, int month, int day, int hour){
	 
		Connection c = null;
		try {
			c =BaseDAO.openConnection(searchbar.getProvince());
			QueryRunner qr = new QueryRunner();
			StringBuffer sql = new StringBuffer();
			sql.append("select Date_FK from d_date	where 1=1 ");
			choosePropertyCondition("year",year,sql);
			choosePropertyCondition("month_of_year",month-1,sql);
			choosePropertyCondition("day_of_month",day,sql);
			choosePropertyCondition("hour_of_day",hour,sql);
			System.out.println(sql.toString());
			return (Integer) qr.query(c, sql.toString(), new ScalarHandler<Integer>(1));			
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("QuantityTrendDaoImpl.getDateFk] [{0}]", e.getMessage());
			throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
		}finally{
			try {
				 BaseDAO.close(c);
			} catch (SQLException e) {
				e.printStackTrace();
				log.error("[ProfileDaoImpl.getQuantityTrendChart] [{0}]", e.getMessage());
				throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
			}
		}
		
		
	}
	
	/**
	 * 拼接属性条件
	 * @param property
	 * @param o
	 * @param sql
	 */
	private void choosePropertyCondition(String property,Object o,StringBuffer sql){
		if(ValidateUtils.isNotNull(o)){
			sql.append(" and " + property+ " = '" + o  +"' ");
		}
	}
	
	
	private void setPeriod(SearchBar bar,StringBuffer sql){
		if(SearchBar.DAILY.equals(bar.getType())){
			//如果在一天内按小时分组，否则按天分组
			if(bar.getDateEqual()){
				sql.append(" group by da.hour_of_day ");
				sql.append(" order by da.hour_of_day ");
			}else{
				sql.append(" group by da.day_of_year ");
				sql.append(" order by da.time ");
			}
		}else if (SearchBar.WEEKLY.equals(bar.getType())) {
			 sql.append(" group by week_num  order by da.time ");
		}else if (SearchBar.MONTHLY.equals(bar.getType())) {
			sql.append(" group by da.month_of_year,da.year  order by da.time ");
		}
		
	}

	/* 
	 * 统计所有webpv、wappv loginpv startpv 总数
	 * (non-Javadoc)
	 * @see com.iaat.dao.quantitytrend.QuantityTrendDao#getQuantityTrendListAll(com.iaat.json.SearchBar, com.nokia.ads.platform.backend.util.Paging)
	 */
	@Override
	public List<QuantityTrendListBean> getQuantityTrendListAll(SearchBar searchbar, Paging paging) throws Exception {
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT");
			sql.append("    min(time) startTime,max(time) endTime,");
			sql.append("    day_of_week week,da.hour_of_day hour,time,");
			sql.append("    SUM(f.wap_PV) wapPv,");
			sql.append("    sum(f.web_PV) webPv,");
			sql.append("    sum(t.web_PV) loginPv,");
			sql.append("    sum(t.wap_PV) startPv,");
			sql.append("    count(DISTINCT t.IMEI_FK) uv");
			sql.append(" 	FROM");
			sql.append("    d_date da ");
			concatCondition(searchbar, sql);
			setPeriod(searchbar, sql);			
			return query(searchbar,sql.toString(), new BeanListHandler<QuantityTrendListBean>(QuantityTrendListBean.class),paging,true);
		}catch (SQLException e) {
			e.printStackTrace();
			log.error("QuantityTrendDaoImpl.getQuantityTrendChart] [{0}]", e.getMessage());
			throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
		}
	}
 
	/* 
	 * 暂不用
	 * (non-Javadoc)
	 * @see com.iaat.dao.quantitytrend.QuantityTrendDao#getQuantityTrendGroupDayList(com.iaat.json.SearchBar, com.nokia.ads.platform.backend.util.Paging)
	 */
	@SuppressWarnings("unchecked")
	@Deprecated  
	@Override
	public List<QuantityTrendListBean> getQuantityTrendGroupDayList(SearchBar searchbar, Paging paging) throws Exception {
		 
		List<QuantityTrendListBean> list = null;
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT da.time, SUM(f.wap_PV) wapPv, sum(f.web_PV) webPv ,count(DISTINCT t.IMEI_FK) uv,sum(isNewUser) newUserNum ,day_of_week week  from  d_date da ");
			concatCondition(searchbar, sql);
			sql.append(" group by da.day_of_year ");
			sql.append(" order by da.time ");
			list = query(searchbar,sql.toString(),new BeanListHandler<QuantityTrendListBean>(QuantityTrendListBean.class),paging,true);
//					qr.query(conn.getConnection(), sql.toString(), new BeanListHandler<QuantityTrendListBean>(QuantityTrendListBean.class));
			System.out.println(sql.toString());
		}catch (SQLException e) {
			e.printStackTrace();
			log.error("QuantityTrendDaoImpl.getQuantityTrendGroupDayList] [{0}]", e.getMessage());
			throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
		}
		return list; 
	}
	
	
	/* 
	 * 统计所有WapPv、WebPV 和 新用户
	 * (non-Javadoc)
	 * @see com.iaat.dao.quantitytrend.QuantityTrendDao#getQuantityTrendTotalAll(com.iaat.json.SearchBar)
	 */
	@Override
	public QuantityTrendTotalBean getQuantityTrendTotalAll(SearchBar bar) {
	
		Connection c = null;
		try {
			   c = 	BaseDAO.openConnection(bar.getProvince());
			   StringBuffer sql = new StringBuffer();
			   
			   sql.append("select sum(wapPv) totalWapPv,sum(webPv) totalWebPv,sum(loginPv) totalLoginPv,    sum(t.startPv) totalStartPv,sum(uv) totalUv from( ");
			   sql.append(" SELECT");
				sql.append("    SUM(f.wap_PV) wapPv,");
				sql.append("    sum(f.web_PV) webPv,");
				sql.append("    sum(t.web_PV) loginPv,");
				sql.append("    sum(t.wap_PV) startPv,");
				sql.append("    count(DISTINCT t.IMEI_FK) uv");
				sql.append(" 	FROM");
				sql.append("    d_date da ");
				concatCondition(bar, sql);
				setPeriod(bar, sql);			
//			   sql.append("SELECT SUM(f.wap_PV) totalWapPv, sum(f.web_PV) totalWebPv , sum(t.web_PV) totalLoginPv,sum(t.wap_PV) totalStartPv,count(DISTINCT t.IMEI_FK) totalUv from  d_date da ");
			   sql.append(" ) t");
			   QueryRunner qr = new QueryRunner();
			   System.out.println(sql.toString());
			   return qr.query(c, sql.toString(),new BeanHandler<QuantityTrendTotalBean>(QuantityTrendTotalBean.class));
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("QuantityTrendDaoImpl.getQuantityTrendTotalAll] [{0}]", e.getMessage());
			throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
		}finally{
			try {
					BaseDAO.close(c);
			} catch (Exception e) {
				e.printStackTrace();
				log.error("[ProfileDaoImpl.getQuantityTrendTotalAll] [{0}]", e.getMessage());
				throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
			}
		}
	}
}
