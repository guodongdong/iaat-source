package com.iaat.dao.impl.profile;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.iaat.dao.DaoUtils;
import com.iaat.dao.profile.ProfileDao;
import com.iaat.dbutils.BaseDAO;
import com.iaat.json.SearchBar;
import com.iaat.json.profile.ProfileBean;
import com.iaat.json.profile.ProfileChartBean;
import com.iaat.share.ErrorCode;
import com.iaat.util.ValidateUtils;
import com.nokia.ads.common.util.Log;
import com.nokia.ads.platform.backend.core.UncheckedException;

/**
 * 
 * @name ProfileDaoImpl
 * 
 * @description CLASS_DESCRIPTION
 * 
 * MORE_INFORMATION
 * 
 * @author zhaozhi
 * 
 * @since 2013-6-24
 *       
 * @version 1.0
 */
public class ProfileDaoImpl implements ProfileDao {
	private final static Log log = Log.getLogger(ProfileDaoImpl.class);
	

	@Override
	public ProfileBean getProfile(SearchBar bar) {
		ProfileBean result = null;
		Connection c = null;
		try {
			c = BaseDAO.openConnection(bar.getProvince());
			QueryRunner qr= new QueryRunner();
			StringBuffer sql = new StringBuffer();
			String tableName = DaoUtils.getTableName(bar);
			sql.append("SELECT sum(wap_PV) AS pv,count(UV)AS uv,sum(CASE WHEN interTime > 0  THEN 1 ELSE 0 END)AS oldUser," +
						"sum(CASE WHEN interTime <= 0 THEN 1 ELSE 0 END)AS increaseUser,sum(CASE WHEN wap_PV = 1 THEN 1 ELSE 0 END )onceUser," +
						"sum(CASE WHEN wap_PV <= 0 AND web_PV >= 0 THEN 1 ELSE 0 END)AS onlyLoginUser,sum(web_PV)/ count(UV)AS avgUserLogin," +
						"sum(wap_PV)/ count(UV)AS avgUserAccess,sum(interTime)/ count(UV)AS avgLastTime FROM" +
						"(SELECT count(DISTINCT IMEI_FK)AS UV,sum(wap_PV)AS wap_PV,sum(web_PV)AS web_PV,sum(loginNum)logNum," +
						"avg(interval_time)interTime,1 AS sequence FROM "+ tableName +" AS f ");
			DaoUtils.initSql(bar, sql);
			sql.append("GROUP BY IMEI_FK )ff GROUP BY ff.sequence;");
			BeanHandler<ProfileBean> rsh = new BeanHandler<ProfileBean>(ProfileBean.class);
			System.out.println(sql.toString());
			result = qr.query(c, sql.toString(), rsh);
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("[ProfileDaoImpl.getProfile] [{0}]", e.getMessage());
			throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
		}finally{
				try {
					BaseDAO.close(c);
				} catch (SQLException e) {
					e.printStackTrace();
					log.error("[ProfileDaoImpl.getProfile] [{0}]", e.getMessage());
					throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
				}
		}
		return result;
	}
	
	@Override
	public int getPV(SearchBar bar) {
		int result = 0;
		Connection c = null;
		try {
			c = BaseDAO.openConnection(bar.getProvince());
			QueryRunner qr= new QueryRunner();
			StringBuffer sql = new StringBuffer();
			String tableName = DaoUtils.getTableName(bar);
			sql.append("SELECT SUM(f.wap_PV) FROM " + tableName + " f ");
			DaoUtils.initSql(bar, sql);
			ScalarHandler<BigDecimal>  rsh = new ScalarHandler<BigDecimal>();
//			System.out.println(sql.toString());
			BigDecimal query = qr.query(c, sql.toString(), rsh);
			if (ValidateUtils.isNotNull(query)) {
				Integer rs = query.intValue();		
				result = rs;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("[ProfileDaoImpl.getPV] [{0}]", e.getMessage());
			throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
		}finally{
				try {
					BaseDAO.close(c);
				} catch (SQLException e) {
					e.printStackTrace();
					log.error("[ProfileDaoImpl.getPV] [{0}]", e.getMessage());
					throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
				}
		}
		return result;
	}

	@Override
	public int getUV(SearchBar bar) {
		int result = 0;
		Connection c = null;
		try {
			c = BaseDAO.openConnection(bar.getProvince());
			QueryRunner qr= new QueryRunner();
			StringBuffer sql = new StringBuffer();
			String tableName = DaoUtils.getTableName(bar);
			sql.append("SELECT COUNT(DISTINCT f.IMEI_FK) FROM " + tableName + " f ");
			DaoUtils.initSql(bar, sql);
//			System.out.println(sql.toString());
			ScalarHandler<Long>  rsh = new ScalarHandler<Long>();  
			Long rs = qr.query(c, sql.toString(), rsh);		
			if (ValidateUtils.isNotNull(rs)) {
				result = rs.intValue();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("[ProfileDaoImpl.getUV] [{0}]", e.getMessage());
			throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
		}finally{
				try {
					
						BaseDAO.close(c);
				} catch (SQLException e) {
					e.printStackTrace();
					log.error("[ProfileDaoImpl.getUV] [{0}]", e.getMessage());
					throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
				}
		}
		return result;
	}
	
	@Override
	public int getIncreaseUser(SearchBar bar) {
		int result = 0;
		Connection c = null;
		try {
			c = BaseDAO.openConnection(bar.getProvince());
			QueryRunner qr= new QueryRunner();
			StringBuffer sql = new StringBuffer();
			String tableName = DaoUtils.getTableName(bar);
			sql.append("SELECT COUNT(DISTINCT f.IMEI_FK) FROM " + tableName + " f ");
			DaoUtils.initSql(bar, sql);
			sql.append("  AND f.isNewUser = 1 ");
//			System.out.println(sql.toString());
			ScalarHandler<Long>  rsh = new ScalarHandler<Long>();
			Long rs = qr.query(c, sql.toString(), rsh);		
			if (ValidateUtils.isNotNull(rs)) {
				result = rs.intValue();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("[ProfileDaoImpl.getIncreaseUser] [{0}]", e.getMessage());
			throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
		}finally{
				try {
					
						BaseDAO.close(c);
				} catch (SQLException e) {
					e.printStackTrace();
					log.error("[ProfileDaoImpl.getIncreaseUser] [{0}]", e.getMessage());
					throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
				}
		}
		return result;
	}

	@Override
	public int getOnceUser(SearchBar bar) {
		int result = 0;
		Connection c = null;
		try {
			c = BaseDAO.openConnection(bar.getProvince());
			QueryRunner qr= new QueryRunner();
			StringBuffer sql = new StringBuffer();
			String tableName = DaoUtils.getTableName(bar);
			sql.append("SELECT COUNT(ff.imei) FROM ( SELECT COUNT(DISTINCT f.IMEI_FK) AS imei,SUM(f.wap_PV) AS pv FROM " + tableName + " f ");
			DaoUtils.initSql(bar, sql);
			sql.append("  GROUP BY f.IMEI_FK HAVING pv = 1) ff ");
//			System.out.println(sql.toString());
			ScalarHandler<Long>  rsh = new ScalarHandler<Long>();
			Long rs = qr.query(c, sql.toString(), rsh);		
			if (ValidateUtils.isNotNull(rs)) {
				result = rs.intValue();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("[ProfileDaoImpl.getOnceUser] [{0}]", e.getMessage());
			throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
		}finally{
				try {
					
						BaseDAO.close(c);
				} catch (SQLException e) {
					e.printStackTrace();
					log.error("[ProfileDaoImpl.getOnceUser] [{0}]", e.getMessage());
					throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
				}
		}
		return result;
	}

	@Override
	public float getAvgUserAccess(SearchBar bar) {
		float result = 0;
		int pv = getPV(bar);
		int uv = getUV(bar);
		if (ValidateUtils.isNotNull(uv)) {
			result = pv*1F/uv*1F;
		}
		return result;
	}
	
	@Override
	public float getAvgUserLogin(SearchBar bar) {
		String logSource = bar.getLogSource();
		float result = 0;
		if (ValidateUtils.isNotNull(logSource) && logSource.indexOf("tom") >= 0) {
			Connection c = null;
			try {
				c = BaseDAO.openConnection(bar.getProvince());
				QueryRunner qr= new QueryRunner();
				StringBuffer sql = new StringBuffer();
				String tableName =  DaoUtils.getTableName(bar);
				sql.append("SELECT SUM(f.loginNum)/COUNT(DISTINCT f.IMEI_FK) FROM " + tableName + " f ");
				DaoUtils.initSql(bar, sql);
				System.out.println(sql.toString());
				ScalarHandler<BigDecimal>  rsh = new ScalarHandler<BigDecimal>();  
				BigDecimal query = qr.query(c, sql.toString(), rsh);
				if (ValidateUtils.isNotNull(query)) {
					result = query.floatValue();		
				}
			} catch (SQLException e) {
				e.printStackTrace();
				log.error("[ProfileDaoImpl.getAvgUserLogin] [{0}]", e.getMessage());
				throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
			}finally{
					try {
						
							BaseDAO.close(c);
					} catch (SQLException e) {
						e.printStackTrace();
						log.error("[ProfileDaoImpl.getAvgUserLogin] [{0}]", e.getMessage());
						throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
					}
			}
		}
		return result;
	}

	@Override
	public float getAvgLastTime(SearchBar bar) {
		String logSource = bar.getLogSource();
		float result = 0;
		if (ValidateUtils.isNotNull(logSource)) {
			Connection c = null;
			try {
				c = BaseDAO.openConnection(bar.getProvince());
				QueryRunner qr= new QueryRunner();
				StringBuffer sql = new StringBuffer();
				String tableName =  DaoUtils.getTableName(bar);
				sql.append("SELECT AVG(temp.i_time) FROM"+"(SELECT AVG(f.interval_time) AS i_time FROM " + tableName + " f ");
				DaoUtils.initSql(bar, sql);
				sql.append(" GROUP BY f.IMEI_FK) temp");
				System.out.println(sql.toString());
				ScalarHandler<BigDecimal>  rsh = new ScalarHandler<BigDecimal>();  
				BigDecimal query = qr.query(c, sql.toString(), rsh);
				if (ValidateUtils.isNotNull(query)) {
					result = query.floatValue();
					result = Math.abs(result);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				log.error("[ProfileDaoImpl.getAvgLastTime] [{0}]", e.getMessage());
				throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
			}finally{
					try {
						
							BaseDAO.close(c);
					} catch (SQLException e) {
						e.printStackTrace();
						log.error("[ProfileDaoImpl.getAvgLastTime] [{0}]", e.getMessage());
						throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
					}
			}
		}
		return result;
	}

	@Override
	public int getOnlyLoginUser(SearchBar bar) {
		String logSource = bar.getLogSource();
		int result = 0;
		if (ValidateUtils.isNotNull(logSource) && logSource.indexOf("tom") >= 0) {
			Connection c = null;
			try {
				c = BaseDAO.openConnection(bar.getProvince());
				QueryRunner qr= new QueryRunner();
				StringBuffer sql = new StringBuffer();
				String tableName = DaoUtils.getTableName(bar);
//				sql.append("SELECT COUNT( IMEI_FK) FROM " + tableName + " f ");
//				DaoUtils.initSql(bar, sql);
//				sql.append(" AND f.web_PV = 0 AND f.wap_PV = 0 AND f.loginNum >0  GROUP BY IMEI_FK ");
				sql.append("SELECT COUNT(ff.IMEI_FK) FROM " 
						+ "(SELECT f.IMEI_FK,SUM(f.wap_PV) AS `access`,SUM(f.loginNum) AS `login` FROM "+ tableName +" f ");
				DaoUtils.initSql(bar, sql);
				sql.append(" GROUP BY f.IMEI_FK HAVING access = 0 AND login > 0) ff ");
				System.out.println(sql.toString());
				ScalarHandler<Long>  rsh = new ScalarHandler<Long>();  
				Long query = qr.query(c, sql.toString(), rsh);
				if (ValidateUtils.isNotNull(query)) {
					Integer rs = query.intValue();		
					result = rs;
				}
			} catch (SQLException e) {
				e.printStackTrace();
				log.error("[ProfileDaoImpl.getOnlyLoginUser] [{0}]", e.getMessage());
				throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
			}finally{
					try {
						
							BaseDAO.close(c);
					} catch (SQLException e) {
						e.printStackTrace();
						log.error("[ProfileDaoImpl.getOnlyLoginUser] [{0}]", e.getMessage());
						throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
					}
			}
		}
		return result;
	}
	
	@Override
	public List<ProfileChartBean> getPVChart(SearchBar bar) {
		List<ProfileChartBean> list = null;
		Connection c = null;
		try {
			c = BaseDAO.openConnection(bar.getProvince());
			QueryRunner qr= new QueryRunner();
			StringBuffer sql = new StringBuffer();
			String tableName = DaoUtils.getTableName(bar);
			sql.append("SELECT SUM(f.web_PV+f.wap_PV) AS `count`,MIN(d.time) AS `beginDate`,MAX(d.time) AS `endDate`,d.hour_of_day AS `hour` ");
			DaoUtils.setType(bar, sql);
			sql.append(" FROM d_date d LEFT JOIN " + tableName + " f ON d.Date_FK = f.Date_FK ");
			sql.append("  LEFT JOIN d_region re ON re.Region_FK = f.Region_FK ");
			DaoUtils.setConditions(bar, sql);
			//setDateTime(bar, sql,"f");
			sql.append(" where 1=1 ");
			DaoUtils.setDateTime(bar, sql,"d");
			setStatsType(bar, sql);
			System.out.println(sql.toString());
			BeanListHandler<ProfileChartBean> rsh = new BeanListHandler<ProfileChartBean>(ProfileChartBean.class);
			list = qr.query(c, sql.toString(), rsh);		
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("[ProfileDaoImpl.getPVChart] [{0}]", e.getMessage());
			throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
		}finally{
				try {
					
						BaseDAO.close(c);
				} catch (SQLException e) {
					e.printStackTrace();
					log.error("[ProfileDaoImpl.getPVChart] [{0}]", e.getMessage());
					throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
				}
		}
		return list;
	}
	
	private void setStatsType(SearchBar bar, StringBuffer sql) {
		setStatsType(bar, sql, "d");
	}
	
	private void setStatsType(SearchBar bar, StringBuffer sql,String alias) {
		if (SearchBar.DAILY.equals(bar.getType())) {
			if (bar.getDateEqual()) {
				sql.append(" GROUP BY "+alias+".hour_of_day ORDER BY "+alias+".hour_of_day");
			}else {
				sql.append(" GROUP BY "+alias+".day_of_year,"+alias+".`year` ORDER BY "+alias+".time");
			}
		}else if (SearchBar.WEEKLY.equals(bar.getType())) {
			sql.append(" GROUP BY "+alias+".week_num ORDER BY "+alias+".time");
		}else if (SearchBar.MONTHLY.equals(bar.getType())) {
			sql.append(" GROUP BY "+alias+".month_of_year,"+alias+".`year` ORDER BY "+alias+".time");
		}
	}
	
	@Override
	public List<ProfileChartBean> getUVChart(SearchBar bar) {
		List<ProfileChartBean> list = null;
		Connection c = null;
		try {
			c = BaseDAO.openConnection(bar.getProvince());
			QueryRunner qr= new QueryRunner();
			StringBuffer sql = new StringBuffer();
			String tableName = DaoUtils.getTableName(bar);
//			sql.append("SELECT COUNT(DISTINCT f.IMEI_FK) as `count`,d.time as `title`,d.hour_of_day as `hour` FROM " + tableName + " f INNER JOIN d_date d ON d.Date_FK = f.Date_FK WHERE 1=1 ");
//			setDateTime(bar, sql);
//			setStatsType(bar, sql);
			sql.append("SELECT COUNT(DISTINCT f.IMEI_FK) AS `count`,MIN(d.time) AS `beginDate`,MAX(d.time) AS `endDate`,d.hour_of_day AS `hour` ");
			DaoUtils.setType(bar, sql);
			sql.append(" FROM d_date d LEFT JOIN " + tableName + " f ON d.Date_FK = f.Date_FK ");
			sql.append("  LEFT JOIN d_region re ON re.Region_FK = f.Region_FK ");
			DaoUtils.setConditions(bar, sql);
			//setDateTime(bar, sql,"f");
			sql.append(" where 1=1 ");
			DaoUtils.setDateTime(bar, sql,"d");
			setStatsType(bar, sql);
			BeanListHandler<ProfileChartBean> rsh = new BeanListHandler<ProfileChartBean>(ProfileChartBean.class);
			list = qr.query(c, sql.toString(), rsh);		
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("[ProfileDaoImpl.getUVChart] [{0}]", e.getMessage());
			throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
		}finally{
				try {
					
						BaseDAO.close(c);
				} catch (SQLException e) {
					e.printStackTrace();
					log.error("[ProfileDaoImpl.getUVChart] [{0}]", e.getMessage());
					throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
				}
		}
		return list;
	}

	@Override
	public List<ProfileChartBean> getIncreaseUserChart(SearchBar bar) {
		List<ProfileChartBean> list = null;
		Connection c = null;
		try {
			c = BaseDAO.openConnection(bar.getProvince());
			QueryRunner qr= new QueryRunner();
			StringBuffer sql = new StringBuffer();
			String tableName = DaoUtils.getTableName(bar);
//			sql.append("SELECT COUNT(DISTINCT f.IMEI_FK) as `count`,d.time as `title`,d.hour_of_day as `hour` FROM " + tableName + " f INNER JOIN d_date d ON d.Date_FK = f.Date_FK WHERE 1=1 ");
//			setDateTime(bar, sql);
//			sql.append("  AND f.isNewUser = 1 ");
//			setStatsType(bar, sql);
			sql.append("SELECT COUNT(DISTINCT f.IMEI_FK) AS `count`,MIN(d.time) AS `beginDate`,MAX(d.time) AS `endDate`,d.hour_of_day AS `hour` ");
			DaoUtils.setType(bar, sql);
			sql.append(" FROM d_date d LEFT JOIN " + tableName + " f ON d.Date_FK = f.Date_FK ");
			sql.append("  LEFT JOIN d_region re ON re.Region_FK = f.Region_FK ");
			DaoUtils.setConditions(bar, sql);
			sql.append("  AND f.isNewUser = 1 ");
			//setDateTime(bar, sql,"f");
			sql.append(" where 1=1 ");
			DaoUtils.setDateTime(bar, sql,"d");
			setStatsType(bar, sql);
			System.out.println(sql.toString());
			BeanListHandler<ProfileChartBean> rsh = new BeanListHandler<ProfileChartBean>(ProfileChartBean.class);
			list = qr.query(c, sql.toString(), rsh);		
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("[ProfileDaoImpl.getIncreaseUserChart] [{0}]", e.getMessage());
			throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
		}finally{
				try {
					
						BaseDAO.close(c);
				} catch (SQLException e) {
					e.printStackTrace();
					log.error("[ProfileDaoImpl.getIncreaseUserChart] [{0}]", e.getMessage());
					throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
				}
		}
		return list;
	}

	@Override
	public List<ProfileChartBean> getOnceUserChart(SearchBar bar) {
		return getIncreaseUserChart(bar);
	}

	@Override
	public List<ProfileChartBean> getOnlyLoginUserChart(SearchBar bar) {
		String logSource = bar.getLogSource();
		List<ProfileChartBean> list = null;
		if (ValidateUtils.isNotNull(logSource) && logSource.indexOf("tom") >= 0) {
			Connection c = null;
			try {
				c = BaseDAO.openConnection(bar.getProvince());
				QueryRunner qr= new QueryRunner();
				StringBuffer sql = new StringBuffer();
				String tableName = DaoUtils.getTableName(bar);
//				sql.append("SELECT COUNT(DISTINCT f.IMEI_FK) as `count`,d.time as `title`,d.hour_of_day as `hour` FROM " + tableName + " f INNER JOIN d_date d ON d.Date_FK = f.Date_FK WHERE 1=1 ");
//				setDateTime(bar, sql);
//				sql.append(" AND f.wap_PV = 0 AND f.web_PV = 0 AND f.loginNum > 0 ");
//				setStatsType(bar, sql);
				sql.append("SELECT COUNT(DISTINCT ff.IMEI_FK) AS `count`,MIN(dd.time) AS `beginDate`,MAX(dd.time) AS `endDate`,dd.hour_of_day AS `hour` ");
				DaoUtils.setType(bar, sql);
				sql.append(" FROM d_date dd " +
						" LEFT JOIN (SELECT f.IMEI_FK,SUM(f.wap_PV) AS `access`,SUM(f.loginNum) AS `login`,f.Date_FK FROM "+ tableName +" f "
				);
				DaoUtils.initSql(bar, sql);
				sql.append(" GROUP BY f.IMEI_FK HAVING access = 0 AND login > 0) ff ON ff.Date_FK = dd.Date_FK ");
				sql.append(" where 1=1 ");
				DaoUtils.setDateTime(bar, sql,"dd");
				setStatsType(bar, sql,"dd");
				System.out.println(sql.toString());
				BeanListHandler<ProfileChartBean> rsh = new BeanListHandler<ProfileChartBean>(ProfileChartBean.class);
				list = qr.query(c, sql.toString(), rsh);		
			} catch (SQLException e) {
				e.printStackTrace();
				log.error("[ProfileDaoImpl.getOnlyLoginUserChart] [{0}]", e.getMessage());
				throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
			}finally{
					try {
						
							BaseDAO.close(c);
					} catch (SQLException e) {
						e.printStackTrace();
						log.error("[ProfileDaoImpl.getOnlyLoginUserChart] [{0}]", e.getMessage());
						throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
					}
			}
		}
		return list;
	}

	@Override
	public List<ProfileChartBean> getUserLoginCountChart(SearchBar bar) {
		List<ProfileChartBean> list = null;
		Connection c = null;
		try {
			c = BaseDAO.openConnection(bar.getProvince());
			QueryRunner qr= new QueryRunner();
			StringBuffer sql = new StringBuffer();
			String tableName = DaoUtils.getTableName(bar);
			sql.append("SELECT SUM(f.loginNum) AS `secondCount`,COUNT(DISTINCT f.IMEI_FK) AS `count` FROM " + tableName + " f INNER JOIN d_date d ON d.Date_FK = f.Date_FK WHERE 1=1 ");
			DaoUtils.setDateTime(bar, sql,"d");
			sql.append(" group by f.IMEI_FK having secondCount >= "+bar.getMinCount()+" and secondCount < " + bar.getMaxCount() + " ORDER BY secondCount");
			BeanListHandler<ProfileChartBean> rsh = new BeanListHandler<ProfileChartBean>(ProfileChartBean.class);
			list = qr.query(c, sql.toString(), rsh);		
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("[ProfileDaoImpl.getAvgUserLoginChart] [{0}]", e.getMessage());
			throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
		}finally{
			try {
				
					BaseDAO.close(c);
			} catch (SQLException e) {
				e.printStackTrace();
				log.error("[ProfileDaoImpl.getAvgUserLoginChart] [{0}]", e.getMessage());
				throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
			}
		}
		return list;
	}

	@Override
	public List<ProfileChartBean> getUserAccessCountChart(SearchBar bar) {
		List<ProfileChartBean> list = null;
		Connection c = null;
		try {
			c = BaseDAO.openConnection(bar.getProvince());
			QueryRunner qr= new QueryRunner();
			StringBuffer sql = new StringBuffer();
			String tableName = DaoUtils.getTableName(bar);
			sql.append("SELECT SUM(f.web_PV + f.wap_PV) AS `secondCount`,COUNT(DISTINCT f.IMEI_FK) AS `count` FROM " + tableName + " f INNER JOIN d_date d ON d.Date_FK = f.Date_FK WHERE 1=1 ");
			DaoUtils.setDateTime(bar, sql,"d");
			sql.append(" group by f.IMEI_FK having secondCount >= "+bar.getMinCount()+" and secondCount < " + bar.getMaxCount() + " ORDER BY secondCount");
			System.out.println(sql.toString());
			BeanListHandler<ProfileChartBean> rsh = new BeanListHandler<ProfileChartBean>(ProfileChartBean.class);
			list = qr.query(c, sql.toString(), rsh);		
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("[ProfileDaoImpl.getAvgUserAccessChart] [{0}]", e.getMessage());
			throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
		}finally{
				try {
					
						BaseDAO.close(c);
				} catch (SQLException e) {
					e.printStackTrace();
					log.error("[ProfileDaoImpl.getAvgUserAccessChart] [{0}]", e.getMessage());
					throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
				}
		}
		return list;
	}
	
	@Override
	public int getLoginImeiCount(SearchBar bar) {
		int result = 0;
		Connection c = null;
		try {
			c = BaseDAO.openConnection(bar.getProvince());
			QueryRunner qr= new QueryRunner();
			StringBuffer sql = new StringBuffer();
			String tableName = DaoUtils.getTableName(bar);
			sql.append("SELECT COUNT(ff.imei) FROM (SELECT f.IMEI_FK AS imei,SUM(f.loginNum) AS num FROM " + tableName + " f ");
			DaoUtils.initSql(bar, sql);
			sql.append(" GROUP BY f.IMEI_FK HAVING num >=" + bar.getMaxCount() +" ORDER BY num) ff");
			System.out.println(sql.toString());
			ScalarHandler<Long>  rsh = new ScalarHandler<Long>();  
			Long rs = qr.query(c, sql.toString(), rsh);		
			if (ValidateUtils.isNotNull(rs)) {
				result = rs.intValue();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("[ProfileDaoImpl.getLoginImeiCount] [{0}]", e.getMessage());
			throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
		}finally{
				try {
					
						BaseDAO.close(c);
				} catch (SQLException e) {
					e.printStackTrace();
					log.error("[ProfileDaoImpl.getLoginImeiCount] [{0}]", e.getMessage());
					throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
				}
		}
		return result;
	}

	@Override
	public int getAccessImeiCount(SearchBar bar) {
		int result = 0;
		Connection c = null;
		try {
			c = BaseDAO.openConnection(bar.getProvince());
			QueryRunner qr= new QueryRunner();
			StringBuffer sql = new StringBuffer();
			String tableName = DaoUtils.getTableName(bar);
			sql.append("SELECT COUNT(ff.imei) FROM (SELECT f.IMEI_FK AS imei,SUM(f.wap_PV) AS pv FROM " + tableName + " f ");
			DaoUtils.initSql(bar, sql);
			sql.append(" GROUP BY f.IMEI_FK HAVING pv >=" + bar.getMaxCount() +" ORDER BY pv) ff");
			System.out.println(sql.toString());
			ScalarHandler<Long>  rsh = new ScalarHandler<Long>();  
			Long rs = qr.query(c, sql.toString(), rsh);		
			if (ValidateUtils.isNotNull(rs)) {
				result = rs.intValue();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("[ProfileDaoImpl.getAccessImeiCount] [{0}]", e.getMessage());
			throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
		}finally{
				try {
					
						BaseDAO.close(c);
				} catch (SQLException e) {
					e.printStackTrace();
					log.error("[ProfileDaoImpl.getAccessImeiCount] [{0}]", e.getMessage());
					throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
				}
		}
		return result;
	}
}
