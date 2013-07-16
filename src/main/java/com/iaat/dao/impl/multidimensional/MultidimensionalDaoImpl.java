package com.iaat.dao.impl.multidimensional;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.iaat.dao.DaoUtils;
import com.iaat.dao.multidimensional.MultidimensionalDao;
import com.iaat.dbutils.BaseDAO;
import com.iaat.json.SearchBar;
import com.iaat.json.profile.ProfileBean;
import com.iaat.share.ErrorCode;
import com.nokia.ads.common.util.Log;
import com.nokia.ads.platform.backend.core.UncheckedException;

/**
 * 
 * @name MultidimensionalDaoImpl
 * 
 * @description CLASS_DESCRIPTION
 * 
 * MORE_INFORMATION
 * 
 * @author zhaozhi
 * 
 * @since 2013-7-10
 *       
 * @version 1.0
 */
public class MultidimensionalDaoImpl implements MultidimensionalDao {
	private final static Log log = Log.getLogger(MultidimensionalDaoImpl.class);

	@Override
	public List<ProfileBean> getChart(SearchBar bar) {
		List<ProfileBean> list = null;
		Connection c = null;
		try {
			c = BaseDAO.openConnection(bar.getProvince());
			QueryRunner qr= new QueryRunner();
			StringBuffer sql = new StringBuffer();
//			String tableName = DaoUtils.getTableName(bar);
//			sql.append("SELECT SUM(f.web_PV+f.wap_PV) AS `count`,MIN(d.time) AS `beginDate`,MAX(d.time) AS `endDate`,d.hour_of_day AS `hour` ");
//			DaoUtils.setType(bar, sql);
//			sql.append(" FROM d_date d LEFT JOIN " + tableName + " f ON d.Date_FK = f.Date_FK ");
//			sql.append("  LEFT JOIN d_region re ON re.Region_FK = f.Region_FK ");
//			DaoUtils.setConditions(bar, sql);
			//setDateTime(bar, sql,"f");
			sql.append(" where 1=1 ");
			DaoUtils.setDateTime(bar, sql,"d");
//			System.out.println(sql.toString());
			BeanListHandler<ProfileBean> rsh = new BeanListHandler<ProfileBean>(ProfileBean.class);
			list = qr.query(c, sql.toString(), rsh);		
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("[MultidimensionalDaoImpl.getChart] [{0}]", e.getMessage());
			throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
		}finally{
				try {
					
						BaseDAO.close(c);
				} catch (SQLException e) {
					e.printStackTrace();
					log.error("[MultidimensionalDaoImpl.getChart] [{0}]", e.getMessage());
					throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
				}
		}
		return list;
	}

	@Override
	public Map<String, List<ProfileBean>> getList(SearchBar bar) {
		
		return null;
	}
	
	
	
}
