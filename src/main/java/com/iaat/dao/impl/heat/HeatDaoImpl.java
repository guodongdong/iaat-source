package com.iaat.dao.impl.heat;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.iaat.dao.DaoUtils;
import com.iaat.dao.heat.HeatDao;
import com.iaat.dbutils.BaseDAO;
import com.iaat.json.ChartBean;
import com.iaat.json.SearchBar;
import com.iaat.json.heat.HeatRegionBean;
import com.iaat.json.heat.OperatorRatioBean;
import com.iaat.json.heat.RegionChartBean;
import com.iaat.share.ErrorCode;
import com.nokia.ads.common.util.Log;
import com.nokia.ads.platform.backend.core.UncheckedException;

public class HeatDaoImpl implements HeatDao {
	private final static Log log = Log.getLogger(HeatDaoImpl.class);
	
	@Override
	public List<RegionChartBean> getNationMapChart(SearchBar bar) {
		List<RegionChartBean> list = null;
		Connection c = null;
		try {
			c = BaseDAO.openConnection(bar.getProvince());
			QueryRunner qr= new QueryRunner();
			String tableName =  DaoUtils.getTableName(bar);
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT re.P_Region AS `region`,SUM(wap_PV) AS `pv`,COUNT(DISTINCT f.IMEI_FK) AS `uv` FROM d_region re LEFT JOIN "+ tableName
					+" f ON re.Region_FK = f.Region_FK ");
			DaoUtils.setConditions(bar, sql);
			sql.append(" LEFT JOIN d_date dd ON dd.Date_FK = f.Date_FK");
			DaoUtils.setDateTime(bar, sql,"dd");
			sql.append(" GROUP BY re.P_code ORDER BY re.Region_FK");
			System.out.println(sql.toString());
			BeanListHandler<RegionChartBean> rsh = new BeanListHandler<RegionChartBean>(RegionChartBean.class);
			list = qr.query(c, sql.toString(), rsh);		
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("[HeatDaoImpl.getNationMapChart] [{0}]", e.getMessage());
			throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
		}finally{
				try {
					
						BaseDAO.close(c);
				} catch (SQLException e) {
					e.printStackTrace();
					log.error("[HeatDaoImpl.getNationMapChart] [{0}]", e.getMessage());
					throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
				}
		}
		return list;
	}

	@Override
	public List<HeatRegionBean> getRegionDetailList(SearchBar bar) {
		List<HeatRegionBean> list = null;
		Connection c = null;
		try {
			c = BaseDAO.openConnection(bar.getProvince());
			QueryRunner qr= new QueryRunner();
			String tableName =  DaoUtils.getTableName(bar);
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT ff.Region AS `region`,ff.`code` AS `regionCode`,COUNT(DISTINCT ff.IMEI_FK) AS `uv`,SUM(ff.isNewUser) AS `increaseUser`,(COUNT(DISTINCT ff.IMEI_FK) - SUM(ff.isNewUser)) AS `oldUser` FROM "
					+"(SELECT f.IMEI_FK,f.isNewUser,re.Region,re.`code`,re.Region_FK FROM "+ tableName +" f  ");
			DaoUtils.initSql(bar, sql);
			sql.append(" GROUP BY f.IMEI_FK,f.isNewUser ORDER BY f.isNewUser DESC) ff GROUP BY ff.Region ORDER BY ff.Region_FK");
			System.out.println(sql.toString());
			BeanListHandler<HeatRegionBean> rsh = new BeanListHandler<HeatRegionBean>(HeatRegionBean.class);
			list = qr.query(c, sql.toString(), rsh);		
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("[HeatDaoImpl.getRegionDetailList] [{0}]", e.getMessage());
			throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
		}finally{
			try {
				BaseDAO.close(c);
			} catch (SQLException e) {
				e.printStackTrace();
				log.error("[HeatDaoImpl.getRegionDetailList] [{0}]", e.getMessage());
				throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
			}
		}
		return list;
	}

	@Override
	public List<OperatorRatioBean> getOperatorRatioList(SearchBar bar) {
		List<OperatorRatioBean> list = null;
		Connection c = null;
		try {
			c = BaseDAO.openConnection(bar.getProvince());
			QueryRunner qr= new QueryRunner();
			String tableName =  DaoUtils.getTableName(bar);
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT f.Operator_FK,dop.operator AS `name`,COUNT(DISTINCT f.IMEI_FK) AS `count` FROM " + tableName + " f " 
					+"INNER JOIN d_region re ON f.Region_FK = re.Region_FK " 
					+"INNER JOIN d_date d ON d.Date_FK = f.Date_FK " 
					+"INNER JOIN d_operator dop ON dop.Operator_FK = f.Operator_FK WHERE 1=1 ");
			DaoUtils.setConditions(bar, sql);
			DaoUtils.setDateTime(bar, sql, "d");
			sql.append("GROUP BY f.Operator_FK  ORDER BY dop.Operator_FK");
			System.out.println(sql.toString());
			BeanListHandler<OperatorRatioBean> rsh = new BeanListHandler<OperatorRatioBean>(OperatorRatioBean.class);
			list = qr.query(c, sql.toString(), rsh);		
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("[HeatDaoImpl.getOperatorRatioList] [{0}]", e.getMessage());
			throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
		}finally{
			try {
				BaseDAO.close(c);
			} catch (SQLException e) {
				e.printStackTrace();
				log.error("[HeatDaoImpl.getOperatorRatioList] [{0}]", e.getMessage());
				throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
			}
		}
		return list;
	}

	@Override
	public List<ChartBean> getHourlyChart(SearchBar bar) {
		List<ChartBean> list = null;
		Connection c = null;
		try {
			c = BaseDAO.openConnection(bar.getProvince());
			QueryRunner qr= new QueryRunner();
			String tableName =  DaoUtils.getTableName(bar);
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT dd.hour_of_day as `name`,COUNT(DISTINCT f.IMEI_FK) as `count` FROM d_date dd " +
					"LEFT JOIN "+ tableName +" f ON dd.Date_FK = f.Date_FK " +
					"LEFT JOIN d_region re ON re.Region_FK = f.Region_FK "
			);
			DaoUtils.setConditions(bar, sql);
			DaoUtils.setDateTime(bar, sql, "dd");
			sql.append(" WHERE 1=1 GROUP BY dd.hour_of_day ORDER BY dd.hour_of_day");
			System.out.println(sql.toString());
			BeanListHandler<ChartBean> rsh = new BeanListHandler<ChartBean>(ChartBean.class);
			list = qr.query(c, sql.toString(), rsh);		
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("[HeatDaoImpl.getHourlyChart] [{0}]", e.getMessage());
			throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
		}finally{
			try {
				BaseDAO.close(c);
			} catch (SQLException e) {
				e.printStackTrace();
				log.error("[HeatDaoImpl.getHourlyChart] [{0}]", e.getMessage());
				throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
			}
		}
		return list;
	}

	@Override
	public List<ChartBean> getDailyChart(SearchBar bar) {
		List<ChartBean> list = null;
		Connection c = null;
		try {
			c = BaseDAO.openConnection(bar.getProvince());
			QueryRunner qr= new QueryRunner();
			String tableName =  DaoUtils.getTableName(bar);
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT dd.day_of_year,dd.time as `name`,COUNT(DISTINCT f.IMEI_FK) AS `count` FROM d_date dd " +
					"LEFT JOIN "+ tableName +" f ON dd.Date_FK = f.Date_FK " +
					"LEFT JOIN d_region re ON re.Region_FK = f.Region_FK "
			);
			
			DaoUtils.setConditions(bar, sql);
			sql.append("WHERE 1=1 ");
			DaoUtils.setDateTime(bar, sql, "dd");
			sql.append(" GROUP BY dd.time  ORDER BY dd.time");
			System.out.println(sql.toString());
			BeanListHandler<ChartBean> rsh = new BeanListHandler<ChartBean>(ChartBean.class);
			list = qr.query(c, sql.toString(), rsh);		
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("[HeatDaoImpl.getDailyChart] [{0}]", e.getMessage());
			throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
		}finally{
			try {
				BaseDAO.close(c);
			} catch (SQLException e) {
				e.printStackTrace();
				log.error("[HeatDaoImpl.getDailyChart] [{0}]", e.getMessage());
				throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
			}
		}
		return list;
	}

}
