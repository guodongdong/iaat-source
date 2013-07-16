package com.iaat.dao.impl.relateionship;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.iaat.dao.relateionship.RelationshipDao;
import com.iaat.dbutils.BaseDAO;
import com.iaat.json.SearchBar;
import com.iaat.model.Relationship;
import com.iaat.share.ErrorCode;
import com.iaat.util.DateUtil;
import com.iaat.util.ValidateUtils;
import com.nokia.ads.common.util.Log;
import com.nokia.ads.platform.backend.core.UncheckedException;

public class RelationshipDaoImpl implements RelationshipDao{
	private final static Log log = Log.getLogger(RelationshipDaoImpl.class);
	
	
	
	private List<String> getCategoryAll(SearchBar bar){
		Connection con = null;
		try {
			con =	BaseDAO.openConnection(bar.getProvince());
			QueryRunner qr = new QueryRunner();
			StringBuffer sql = new StringBuffer();
			sql.append(" select category_fk from f_tom_user_situation ");
			sql.append(" group by category_fk");
			sql.append(" order by category_fk");
			return qr.query(con, sql.toString(),new BeanListHandler<String>(String.class));
		} catch (SQLException e) {
			log.error("[RelationshipDaoImpl.getCategoryAll] [{0}]", e.getMessage());
			throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
		}finally{
			try {
				BaseDAO.close(con);
			} catch (SQLException e) {
				e.printStackTrace();
				log.error("[RelationshipDaoImpl.getCategoryAll] [{0}]", e.getMessage());
				throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
			}
		}
	}
	
	
	@Override
	public List<Relationship> getRelationshipAll(SearchBar bar) {
		Connection con = null;
		try {
			con =	BaseDAO.openConnection(bar.getProvince());
			QueryRunner qr = new QueryRunner();

			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT  c.category_value value,ca.category_value inputValue, sum(r.pv) as pv  from tom_user_relationship r ");
			concatCondition(bar,sql);
			sql.append(" group by r.category_fk,	r.input_category_fk ");
			sql.append(" order by r.category_fk, r.input_category_fk");
			System.out.println(sql.toString());
			return qr.query(con, sql.toString(),new BeanListHandler<Relationship>(Relationship.class));
		} catch (SQLException e) {
			log.error("[RelationshipDaoImpl.getRelationshipAll] [{0}]", e.getMessage());
			throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
		}finally{
			try {
				BaseDAO.close(con);
			} catch (SQLException e) {
				e.printStackTrace();
				log.error("[RelationshipDaoImpl.getRelationshipAll] [{0}]", e.getMessage());
				throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
			}
		}
		
	}

	/**
	 * 拼接表关联条件
	 * @param searchbar
	 * @param sql
	 */
	public void concatCondition(SearchBar searchbar,StringBuffer sql){
		sql.append(" INNER JOIN  d_date d ON d.date_FK = r.date_FK   ");
	 	sql.append(" INNER JOIN  d_region re ON re.Region_FK = r.Region_FK   ");
	 	sql.append(" INNER JOIN d_category c on c.Category_FK = r.category_fk   ");
	 	sql.append(" INNER JOIN d_category ca  on ca.category_fk = r.input_category_fk   ");
		sql.append(" WHERE 1=1 ");		
		setProperty(searchbar, sql, "r");
		if (ValidateUtils.isNotNull(searchbar.getStartDateTime()) && ValidateUtils.isNotNull(searchbar.getEndDateTime())) {
			String tc = " AND time BETWEEN '" + DateUtil.calendar2String(searchbar.getStartDateTime()) + "' AND '" + DateUtil.calendar2String(searchbar.getEndDateTime()) + "'";
			sql.append(tc);
		}
		if (searchbar.isStartTimeVerify() && searchbar.isEndTimeVerify()) {
			sql.append(" and hour_of_day BETWEEN '" + searchbar.getStartTime() + "' AND '" + searchbar.getEndTime() + "' ");
		}
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
		if(ValidateUtils.isNotNull(searchbar.getCity())) 
		choosePropertyCondition("re.code",searchbar.getCity(),sql);
		if(ValidateUtils.isNotNull(searchbar.getProvince()))
		choosePropertyCondition("re.P_Code",searchbar.getProvince(),sql);
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
}
