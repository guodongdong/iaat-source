package com.iaat.dao;

import com.iaat.json.SearchBar;
import com.iaat.share.Params;
import com.iaat.util.DateUtil;
import com.iaat.util.ValidateUtils;

/**
 * 
 * @name DaoUtils
 * 
 * @description CLASS_DESCRIPTION
 * 
 * MORE_INFORMATION
 * 
 * @author zhaozhi
 * 
 * @since 2013-7-4
 *       
 * @version 1.0
 */
public class DaoUtils {
	
	public static void initSql(SearchBar bar, StringBuffer sql) {
		sql.append(
				"INNER JOIN d_date d ON d.Date_FK = f.Date_FK " +
				"INNER JOIN d_region re ON re.Region_FK = f.Region_FK " +
//				"INNER JOIN d_ap ap ON ap.AP_FK = f.AP_FK " +
//				"INNER JOIN d_platform pf ON pf.Platform_FK = f.Platform_FK " +
//				"INNER JOIN d_operator op ON op.Operator_FK = f.Operator_FK " +
//				"INNER JOIN d_channel ch ON ch.Channel_FK = f.Channel_FK " +
//				"INNER JOIN d_category ca ON ca.Category_FK = f.Category_FK " +
//				"INNER JOIN d_terminal te ON te.Terminal_FK = f.Terminal_FK " +
				" WHERE 1=1 ");
		setDateTime(bar, sql,"d");
		setConditions(bar, sql);
	}

	public static void setConditions(SearchBar bar, StringBuffer sql) {
//				if (ValidateUtils.isNotNull(bar.getCity())) {
//					sql.append(" AND re.code = '" + bar.getCity() + "' ");
//				}
//				if (ValidateUtils.isNotNull(bar.getProvince())) {
//					sql.append(" AND re.P_code = '" + bar.getProvince() + "' ");
//				}
				if (ValidateUtils.isNotNull(bar.getProvinceFk())) {
					sql.append(" AND re.P_code = '" + bar.getProvince() + "' ");
				}
				if (ValidateUtils.isNotNull(bar.getCityFk())) {
					sql.append(" AND f.Region_FK = '" + bar.getCityFk() + "' ");
				}
				if (bar.isPlatformKeyVerify()) {
					sql.append(" AND f.Platform_FK = '" + bar.getPlatformKey() + "' ");
				}
				if (bar.isAPKeyVerify()) {
					sql.append(" AND f.AP_FK = '" + bar.getAPKey() + "'  ");
				}
				if (bar.isOperatorKeyVerify()) {
					sql.append(" AND f.Operator_FK = '" + bar.getOperatorKey() + "' ");
				}
				if (bar.isChannelKeyVerify()) {
					sql.append(" AND f.Channel_FK = '" + bar.getChannelKey() + "' ");
				}
				if (bar.isTerminalTypeKeyVerify()) {
					sql.append(" AND f.Terminal_FK = '" + bar.getTerminalTypeKey() + "' ");
				}
	}

	public static void setDateTime(SearchBar bar, StringBuffer sql,String alias) {
		if (ValidateUtils.isNotNull(bar.getStartDateTime()) && ValidateUtils.isNotNull(bar.getEndDateTime())) {
//			String tc = " AND d.time BETWEEN '" + bar.getStartDateTime() + "' AND '" + bar.getEndDateTime() + "'";
//			String tc = " AND d.time BETWEEN '" + bar.getStartDateTime().getTime() + "' AND '" + bar.getEndDateTime().getTime() + "'";
			String tc = " AND "+alias+".time BETWEEN '" + DateUtil.c2Str(bar.getStartDateTime()) + "' AND '" + DateUtil.c2Str(bar.getEndDateTime()) + "'";
//			" AND d.`year` BETWEEN '" + bar.getStartYear() + "' AND '" + bar.getEndYear() + "'"
//			+" AND d.month_of_year BETWEEN '" + bar.getStartMonth() + "' AND '" + bar.getEndMonth() + "'"
//			+" AND d.day_of_month BETWEEN '" + bar.getStartDay() + "' AND '" + bar.getEndDay() + "' ";
			sql.append(tc);
		}
		if (bar.isStartTimeVerify() && bar.isEndTimeVerify()) {
			sql.append(" and "+alias+".hour_of_day BETWEEN '" + bar.getStartTime() + "' AND '" + bar.getEndTime() + "' ");
		}
	}
	
	public static void setType(SearchBar bar, StringBuffer sql) {
		if (!SearchBar.DAILY.equals(bar.getType())) {
			sql.append(" ,"+ Boolean.FALSE +" AS `daily` ");
		}else {
			sql.append(" ,"+ Boolean.TRUE +" AS `daily` ");
			if (bar.getDateEqual()) {
				sql.append(" ,"+ Boolean.TRUE +" AS `hourly` ");
			}
		}
	}
	
	public static String getTableName(SearchBar bar) {
		String tableName =  "f_" + bar.getLogSource() + "_user_situation";
		return tableName;
	}
	
}
