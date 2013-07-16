package com.iaat.dao.impl.latitude;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.iaat.dao.latitude.CategoryDao;
import com.iaat.dbutils.BaseDAO;
import com.iaat.model.CategoryBean;
import com.iaat.share.Params;
import com.nokia.ads.common.util.Log;
import com.nokia.ads.common.util.StringUtils;

/**    
 * @name CategoryDaoImpl
 * 
 * @description CLASS_DESCRIPTION
 * 
 * MORE_INFORMATION
 * 
 * @author JackGuo
 * 
 * @since 2013-6-29
 *       
 * @version 1.0
 */
public class CategoryDaoImpl implements CategoryDao {
	private final static Log log = Log.getLogger(CategoryDaoImpl.class);
	@SuppressWarnings("unchecked")
	@Override
	public List<CategoryBean> getAllCategoryBeans(String regionName) {
		log.info("[CategoryDaoImpl.getAllCategoryBeans][begin]");
		List<CategoryBean> list = null;
		try {
			Connection conn = null;
			try {
				if(regionName!=null&&StringUtils.hasLength(regionName)){
					conn = BaseDAO.openConnection(regionName);	
				}else{
					conn = BaseDAO.openConnection(Params.REGION_NAME);
				}
				QueryRunner qr = new QueryRunner();
				StringBuffer sql = new StringBuffer();
				sql.append("SELECT ");
				sql.append("    Category_FK as categoryFk, ");
				sql.append("    category_key as categoryKey, ");
				sql.append("    category_value as categoryValue, ");
				sql.append("    sequence, ");
				sql.append("    IsAvailable, ");
				sql.append("    P_category_key as PCategoryKey, ");
				sql.append("    P_category_value as PCategoryValue, ");
				sql.append("    updateTime, ");
				sql.append("    description ");
				sql.append("FROM ");
				sql.append("    d_category_m ");
				sql.append("WHERE ");
				sql.append("    IsAvailable = 1 ");
				sql.append("ORDER BY ");
				sql.append("    sequence asc ");
				list = qr.query(conn, sql.toString(), new BeanListHandler(CategoryBean.class),new Object[]{});
				conn.close();
			} catch (SQLException e) {
				log.error("[CategoryDaoImpl.getAllCategoryBeans] [{0}]", e.getMessage());
			}finally{
				try {
					BaseDAO.close(conn);
				} catch (SQLException e) {
					log.error("[CategoryDaoImpl.getAllCategoryBeans] [{0}]", e.getMessage());
				}
			}
			return list;
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		log.info("[CategoryDaoImpl.getAllCategoryBeans][end]");
		return list;
	}

}
