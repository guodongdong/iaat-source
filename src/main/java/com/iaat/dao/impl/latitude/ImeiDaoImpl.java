package com.iaat.dao.impl.latitude;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.iaat.dao.latitude.ImeiDao;
import com.iaat.dbutils.BaseDAO;
import com.iaat.model.ImeiBean;
import com.iaat.share.Params;
import com.nokia.ads.common.util.Log;
import com.nokia.ads.common.util.StringUtils;

/**    
 * @name ImeiDaoImpl
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
public class ImeiDaoImpl implements ImeiDao {
	private final static Log log = Log.getLogger(ImeiDaoImpl.class);
	@SuppressWarnings("unchecked")
	@Override
	public List<ImeiBean> getAllImeiBeans(String regionName) {
		log.info("[ImeiDaoImpl.getAllImeiBeans][begin]");
		List<ImeiBean> list = null;
		Connection conn = null;
		try {
			if(regionName!=null&&StringUtils.hasLength(regionName)){
				conn = BaseDAO.openConnection(regionName);	
			}else{
				conn = BaseDAO.openConnection(Params.REGION_NAME);
			}
			QueryRunner qr = new QueryRunner();
			StringBuffer sql = new StringBuffer();
			sql.append("select IMEI_FK as imeiFk,IMEI_NUM as imeiNum,createTime from d_imei where IsAvailable = 1 ORDER BY sequence asc");
			list = qr.query(conn, sql.toString(), new BeanListHandler(ImeiBean.class),new Object[]{});
			conn.close();
		} catch (SQLException e) {
			log.error("[ImeiDaoImpl.getAllImeiBeans] [{0}]", e.getMessage());
		}finally{
			try {
				 BaseDAO.close(conn);
			} catch (SQLException e) {
				log.error("[ImeiDaoImpl.getAllImeiBeans] [{0}]", e.getMessage());
			}
		}
		log.info("[ImeiDaoImpl.getAllImeiBeans][end]");
		return list;
   }
}
