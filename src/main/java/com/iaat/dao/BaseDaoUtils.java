package com.iaat.dao;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import com.iaat.dbutils.BaseDAO;
import com.iaat.json.SearchBar;
import com.iaat.share.Params;
import com.iaat.util.DateUtil;
import com.nokia.ads.common.util.Log;
import com.nokia.ads.platform.backend.util.Paging;
/**
 * connection and crud utils
 * @author gaojingtao
 * @param <T>
 */
public class BaseDaoUtils<T> {
	private final static Log log = Log.getLogger(BaseDaoUtils.class);
	public Connection getConnection(String province) throws Exception{
		try{
			log.info("BaseDaoUtils get connection.. ");
			return BaseDAO.openConnection(province);
		}catch (Exception e) {
			e.printStackTrace();
			log.error("BaseDaoUtils can not get connection of: "+e.getMessage());
			throw new Exception("BaseDaoUtils error of get connection for "+province);
		}
	}
	private String process(String str){
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT COUNT(1) AS NUM FROM (").append(str).append(" ) as p");
		return sb.toString();
	}
	
	protected List<T> query(SearchBar bar,String sql,BeanListHandler<T> hander,boolean close){
		if(bar==null)return null;
		if(bar.getProvince()==null)return null;
		Connection c=null;
		List<T> t = null;
		try {
			c = this.getConnection(bar.getProvince());
			QueryRunner qr= new QueryRunner();
			t=qr.query(c,sql, hander);
			return t;
		} catch (Exception e) {
			return t;
		}finally{
			if(close){
				try {
					log.info("BaseDaoUtils close connection.. ");
					BaseDAO.close(c);
				} catch (SQLException e) {
				}
			}
		}	
	}
	
	@SuppressWarnings("unchecked")
	protected List queryBean(SearchBar bar,String sql,BeanListHandler hander,boolean close) {
		if(bar==null)return null;
		if(bar.getProvince()==null)return null;
		Connection c=null;
		List t = null;
		try {
			c = this.getConnection(bar.getProvince());
			QueryRunner qr= new QueryRunner();
			t=qr.query(c,sql, hander);
			return t;
		} catch (Exception e) {
			return t;
		}finally{
			if(close){
				try {
					log.info("BaseDaoUtils close connection.. ");
					BaseDAO.close(c);
				} catch (SQLException e) {
				}
			}
		}	
	}
	protected Map<String,Object> query(SearchBar bar,String sql,MapHandler hander,boolean close) throws Exception {
		if(bar==null)return null;
		if(bar.getProvince()==null)return null;
		Map<String,Object> m=null;
		Connection c=null;
		try {
			c = this.getConnection(bar.getProvince());
			QueryRunner qr= new QueryRunner();
			m=qr.query(c,sql, hander);
			return m;
		} catch (Exception e) {
			return m;
		}finally{
			if(close){
				try {
					log.info("BaseDaoUtils close connection.. ");
					BaseDAO.close(c);
				} catch (SQLException e) {
				}
			}
		}	
	}
	private int queryCount(String sql,Connection conn,QueryRunner runner) throws SQLException{
		String countt= process(sql);
		ScalarHandler<Long>  rsh = new ScalarHandler<Long>();  
		return runner.query(conn,countt,rsh).intValue();
	}
	protected List<T> query(SearchBar bar,String sql,BeanListHandler<T> hander,Paging paging,boolean close) throws Exception {
		if(bar==null)return null;
		if(bar.getProvince()==null)return null;
		Connection c=null;
		List<T> t = null;
		try {
			QueryRunner qr= new QueryRunner();
			c = this.getConnection(bar.getProvince());
			if(paging!=null){
				int num = queryCount(sql,c,qr);
				paging.setTotalRecord(num);
				sql+=" limit "+paging.getCurrentRecord()+","+paging.getPageSize();
			}
			t=qr.query(c,sql, hander);
			return t;
		} catch (Exception e) {
			return t;
		}finally{
			if(close){
				try {
					log.info("BaseDaoUtils close connection.. ");
					BaseDAO.close(c);
				} catch (SQLException e) {
				}
			}
		}	
	}
	public String getConditions(SearchBar bar,boolean date,String t) {
		StringBuffer sql = new StringBuffer();
		sql.append(t+" f.Platform_FK is not null ");
		if (SearchBar.ALL_DEFAULT!=bar.getPlatformKey()) {
			sql.append(" AND f.Platform_FK = " +bar.getPlatformKey() + " ");
		}
		if (SearchBar.ALL_DEFAULT!=bar.getAPKey()) {
			sql.append(" AND f.AP_FK = " + bar.getAPKey() + "  ");
		}
		if (SearchBar.ALL_DEFAULT!=bar.getOperatorKey()) {
			sql.append(" AND f.Operator_FK = " + bar.getOperatorKey() + " ");
		}
		if (!"".equalsIgnoreCase(bar.getCity())&&bar.getCity()!=null) {
			sql.append(" AND "+Params.D_REGION+".code = '" + bar.getCity() + "' AND "+Params.D_REGION+".P_code <> '"+Params.D_REGOIN_ROOT+"' ");
		}
		if (date) {
			sql.append(" and "+Params.D_DATE+".time BETWEEN '"+DateUtil.calendar2String(bar.getStartDateTime())+"' and '" +DateUtil.calendar2String(bar.getEndDateTime())+"'");
			sql.append(" and "+Params.D_DATE+".hour_of_day BETWEEN " + bar.getStartTime() + " AND " + bar.getEndTime() + " ");
		}
		return sql.toString();
	}
}
