package com.iaat.dao.impl.bounce;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.iaat.dao.bounce.BounceDao;
import com.iaat.dbutils.BaseDAO;
import com.iaat.json.SearchBar;
import com.iaat.json.bounce.BounceBean;
import com.iaat.json.bounce.BounceType;
import com.iaat.share.ErrorCode;
import com.iaat.share.Params;
import com.iaat.util.DateUtil;
import com.nokia.ads.common.util.Log;
import com.nokia.ads.platform.backend.core.UncheckedException;

public class BounceDaoImpl implements BounceDao {
	private final static Log log = Log.getLogger(BounceDaoImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<BounceBean> getBounceRate(SearchBar bar) {
		log.info("[BounceDaoImpl.getBounceRate][begin]");
		List<BounceBean> bounceBeans = null;
		Connection c = null;
		try {
			c = BaseDAO.openConnection(Params.REGION_NAME);
			QueryRunner qr = new QueryRunner();
			StringBuffer sqlStr = new StringBuffer();
			String tableName = getInTableName(bar);
			sqlStr.append("SELECT ");
			sqlStr.append("d.time as bounceDate,MIN(d.time) AS 'beginDate',MAX(d.time) AS 'endDate',");
			sqlStr.append("count(uv) as uv, ");
			sqlStr.append(setSumIntervalRate());
			sqlStr.append("  FROM ");
			sqlStr.append("    (SELECT ");
			sqlStr.append("        d.week_num AS week_num,d.month_of_year as month_of_year,d.year as year, ");
			sqlStr.append("        d.time as time, ");
			sqlStr.append("        f.imei_fk as uv, ");
			sqlStr.append(setCaseIntervalTime());
			sqlStr.append("    FROM d_date d ");
			sqlStr.append("    LEFT JOIN  ");
			sqlStr.append(tableName + " AS f ");
			sqlStr.append("  ON d.Date_FK = f.Date_FK");
			sqlStr.append("  LEFT JOIN d_region re ON re.Region_FK = f.Region_FK ");
			List<Object> paramesObjs = this.setConditionsBase(bar, sqlStr);
			Object pcCode = this.setPCodeCondition(bar, sqlStr);
			if(pcCode!=null&&paramesObjs!=null){
				paramesObjs.add(pcCode);
			}
			Object[] parames = paramesObjs.toArray(new Object[] {});
			sqlStr.append("    WHERE ");
			sqlStr.append("        1 = 1  ");
			setDateCondition(bar, sqlStr);
			sqlStr.append("    GROUP BY ");
			sqlStr.append("        d.time, ");
			sqlStr.append("        f.imei_fk ");
			sqlStr.append("    ) d ");
			sqlStr.append(setGroupByPeriod(bar));
			BeanListHandler<BounceBean> rsh = new BeanListHandler(BounceBean.class);
			bounceBeans = qr.query(c, sqlStr.toString(), rsh, parames);
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("[BounceDaoImpl.getBounceRate] [{0}]", e.getMessage());
			throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
		} finally {
			try {
				BaseDAO.close(c);
			} catch (SQLException e) {
				e.printStackTrace();
				log
						.error("[BounceDaoImpl.getBounceRate] [{0}]", e
								.getMessage());
				throw new UncheckedException(e.getMessage(),
						ErrorCode.DAO_ERROR);
			}
		}
		log.info("[BounceDaoImpl.getBounceRate][end]");
		return bounceBeans;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<BounceBean> getBounceDataByIntervoidTime(SearchBar bar,Integer intervalTime) {
		log.info("[BounceDaoImpl.getBounceDataByRate][begin]");
		List<BounceBean> bounceBeans = null;
		Connection c = null;
		try {
			c = BaseDAO.openConnection(Params.REGION_NAME);
			QueryRunner qr = new QueryRunner();
			StringBuffer sqlStr = new StringBuffer();
			String tableName = getInTableName(bar);
			sqlStr.append("SELECT ");
			sqlStr.append("d.time as bounceDate,MIN(d.time) AS 'beginDate',MAX(d.time) AS 'endDate',");
			sqlStr.append("    COUNT(DISTINCT f.imei_fk) as uv ");
			sqlStr.append("        FROM ");
			sqlStr.append(tableName + " AS f ");
			sqlStr.append("    LEFT JOIN d_date d ON  d.Date_fk = f.Date_fk ");
			sqlStr.append("    LEFT JOIN d_region re on re.Region_FK = f.Region_FK ");
			sqlStr.append("WHERE ");
			sqlStr.append("    1=1 ");
			List<Object> paramesObjs = this.setConditionsBase(bar, sqlStr);
			Object pcCode = this.setPCodeCondition(bar, sqlStr);
			if(pcCode!=null&&paramesObjs!=null){
				paramesObjs.add(pcCode);
			}
			sqlStr.append("  and f.interval_time = ? ");
			paramesObjs.add(intervalTime);
			setDateCondition(bar, sqlStr);
			sqlStr.append(setGroupByPeriod(bar));
			BeanListHandler<BounceBean> rsh = new BeanListHandler(BounceBean.class);
			Object[] parames = paramesObjs.toArray(new Object[] {});
			bounceBeans = qr.query(c, sqlStr.toString(), rsh, parames);
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("[BounceDaoImpl.getBounceDataByRate] [{0}]", e.getMessage());
			throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
		} finally {
			try {
				BaseDAO.close(c);
			} catch (SQLException e) {
				e.printStackTrace();
				log
						.error("[BounceDaoImpl.getBounceDataByRate] [{0}]", e
								.getMessage());
				throw new UncheckedException(e.getMessage(),
						ErrorCode.DAO_ERROR);
			}
		}
		log.info("[BounceDaoImpl.getBounceDataByRate][end]");
		return bounceBeans;
	
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BounceBean> getBounceDataBeanCount(SearchBar bar) {
		log.info("[BounceDaoImpl.getBounceDataBeanCount][begin]");
		List<BounceBean> bounceBeans = null;
		Connection c = null;
		try {
			c = BaseDAO.openConnection(Params.REGION_NAME);
			QueryRunner qr = new QueryRunner();
			StringBuffer sqlStr = new StringBuffer();
			String tableName = getInTableName(bar);
			sqlStr.append("SELECT ");
			sqlStr.append("    MIN(d.time)AS 'beginDate', ");
			sqlStr.append("    MAX(d.time)AS 'endDate', ");
			sqlStr.append("    COUNT(DISTINCT f.imei_fk) as uv, ");
			sqlStr.append("    d.time  as bounceDate");
			sqlStr.append(" FROM ");
			sqlStr.append("    d_date d  ");
			sqlStr.append(" LEFT JOIN( ");
			sqlStr.append("        SELECT ");
			sqlStr.append("            f.Date_FK, ");
			sqlStr.append("            f.imei_fk, ");
			sqlStr.append("            Region_FK ");
			sqlStr.append("        FROM ");
			sqlStr.append(tableName + " AS f ");
			sqlStr.append("        WHERE ");
			sqlStr.append("            1 = 1 	 ");
			List<Object> paramesObjs = this.setConditionsBase(bar, sqlStr);
			sqlStr.append("                  )AS f ON d.Date_FK = f.Date_FK  ");
			sqlStr.append(" LEFT JOIN(SELECT ");
			sqlStr.append("                        Region_FK ");
			sqlStr.append("                    FROM ");
			sqlStr.append("                        d_region re ");
			sqlStr.append("                    WHERE ");
			sqlStr.append("                        1 = 1 ");
			paramesObjs.addAll(this.setRetionCondition(bar, sqlStr));
			sqlStr.append("               )re ON re.Region_FK = f.Region_FK ");
			sqlStr.append(" WHERE ");
			sqlStr.append(" 1 = 1 ");
			setDateCondition(bar, sqlStr);
			sqlStr.append(setGroupByPeriod(bar));
			Object[] parames = paramesObjs.toArray(new Object[] {});
			BeanListHandler<BounceBean> rsh = new BeanListHandler(BounceBean.class);
			bounceBeans = qr.query(c, sqlStr.toString(), rsh, parames);
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("[BounceDaoImpl.getBounceDataBeanCount] [{0}]", e.getMessage());
			throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
		} finally {
			try {
				BaseDAO.close(c);
			} catch (SQLException e) {
				e.printStackTrace();
				log
						.error("[BounceDaoImpl.getBounceDataBeanCount] [{0}]", e
								.getMessage());
				throw new UncheckedException(e.getMessage(),
						ErrorCode.DAO_ERROR);
			}
		}
		log.info("[BounceDaoImpl.getBounceDataBeanCount][end]");
		return bounceBeans;
	
	}

	private String setSumIntervalRate() {
		StringBuffer sqlStr = new StringBuffer();
		for (BounceType bounceType : BounceType.values()) {
			sqlStr.append(MessageFormat.format("sum({0})/count(uv)  as rate{1},",
					bounceType.name(), bounceType.getValue()));
		}
		return sqlStr.substring(0, sqlStr.lastIndexOf(","));
	}

	private String setCaseIntervalTime() {
		StringBuffer sqlStr = new StringBuffer();
		for (BounceType bounceType : BounceType.values()) {
			sqlStr.append(MessageFormat.format("(CASE WHEN interval_time = {0}  THEN 1 ELSE 0 END) as {1}, ",
					bounceType.getValue(), bounceType.name()));
		}
		return sqlStr.substring(0, sqlStr.lastIndexOf(","));
	}

	public static void main(String[] args) {
		StringBuffer sqlStr = new StringBuffer();
		for (BounceType bounceType : BounceType.values()) {
			sqlStr.append(MessageFormat.format("(CASE WHEN interval_time = {0}  THEN 1 ELSE 0 END) as {1}, ",
					bounceType.getValue(), bounceType.name()));
		}
		System.out.println(sqlStr.substring(0, sqlStr.lastIndexOf(",")));

	}

	private void setDateCondition(SearchBar searchBar, StringBuffer sqlStr) {
		if (searchBar.getStartDateTime() != null
				&& searchBar.getEndDateTime() != null) {
			sqlStr.append(" AND d.time BETWEEN '"
					+ DateUtil.c2Str(searchBar.getStartDateTime()) + "' AND '"
					+ DateUtil.c2Str(searchBar.getEndDateTime()) + "'");
		}
		sqlStr.append(" AND d.hour_of_day BETWEEN '" + searchBar.getStartTime()
				+ "' AND '" + searchBar.getEndTime() + "'");
	}

	public List<Object> setConditionsBase(SearchBar searchBar, StringBuffer sql) {
		List<Object> parames = new ArrayList<Object>();
		if (isNotNull(searchBar.getCityFk())) {
			sql.append(" AND f.Region_FK = ? ");
			parames.add(searchBar.getCityFk());
		}
		if (isNotNull(searchBar.getPlatformKey())) {
			sql.append(" AND f.Platform_FK = ? ");
			parames.add(searchBar.getPlatformKey());
		}
		if (isNotNull(searchBar.getAPKey())) {
			sql.append(" AND f.AP_FK = ?  ");
			parames.add(searchBar.getAPKey());
		}
		if (isNotNull(searchBar.getOperatorKey())) {
			sql.append(" AND f.Operator_FK = ? ");
			parames.add(searchBar.getOperatorKey());
		}
		if (isNotNull(searchBar.getChannelKey())) {
			sql.append(" AND f.Channel_FK = ? ");
			parames.add(searchBar.getChannelKey());
		}
		if (isNotNull(searchBar.getTerminalTypeKey())) {
			sql.append(" AND f.Terminal_FK = ? ");
			parames.add(searchBar.getTerminalTypeKey());
		}
		return parames;
	}
	private Object setPCodeCondition(SearchBar searchBar, StringBuffer sql) {
		Object  provObj = null;
		if (isNotNull(searchBar.getProvinceFk())) {
			sql.append(" AND re.P_code = ? ");
			provObj = searchBar.getProvince();
		}
		return provObj;
	}
	private List<Object> setRetionCondition(SearchBar searchBar, StringBuffer sql) {
		List<Object> provObjs = new ArrayList<Object>();
		if (isNotNull(searchBar.getProvinceFk())) {
			sql.append(" AND re.P_code = ? ");
			provObjs.add(searchBar.getProvince());
		}
		if(isNotNull(searchBar.getCityFk())){
			sql.append(" AND re.Region_FK = ? ");
			provObjs.add(searchBar.getCityFk());
		}
		return provObjs;
	}

	private Boolean isNotNull(Integer value) {
		if (value != null && value.intValue() != -1) {
			return true;
		}
		return false;
	}

	private String getInTableName(SearchBar bar) {
		String tableName = MessageFormat.format("f_{0}_user_situation_new_t", bar
				.getLogSource());
		return tableName;
	}
	
	/**
	 * 根据daily、weekly、monthly设置分组条件
	 * @param searchBar
	 * @param sql
	 * @param fk
	 */
	private String setGroupByPeriod(SearchBar searchBar){
		StringBuffer sql = new StringBuffer();
		if(searchBar.getType().equals(SearchBar.DAILY)){
			sql.append(" GROUP BY d.time  ORDER BY  d.time ");
		}else if(searchBar.getType().equals(SearchBar.WEEKLY)){
			sql.append(" GROUP BY  d.week_num ORDER BY  d.time");
		}else if(searchBar.getType().equals(SearchBar.MONTHLY)){
			sql.append(" GROUP BY  d.month_of_year,d.year  ORDER BY  d.time");
		}
		return sql.toString();
	}
	
}
