package com.iaat.dao.impl.latitude;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.iaat.dao.latitude.ChannelDao;
import com.iaat.dbutils.BaseDAO;
import com.iaat.model.ChannelBean;
import com.iaat.share.Params;
import com.nokia.ads.common.util.Log;
import com.nokia.ads.common.util.StringUtils;

/**    
 * @name ChannelDaoImpl
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
public class ChannelDaoImpl implements ChannelDao {
	private final static Log log = Log.getLogger(ChannelDaoImpl.class);
	@SuppressWarnings("unchecked")
	@Override
	public List<ChannelBean> getAllChannelBeans(String regionName) {
		log.info("[ChannelDaoImpl.getAllChannelBeans][begin]");
		List<ChannelBean> list = null;
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
			sql.append("    Channel_FK as channelFk, ");
			sql.append("    channel, ");
			sql.append("    sequence, ");
			sql.append("    IsAvailable, ");
			sql.append("    P_Channel as PChannel, ");
			sql.append("    updateTime, ");
			sql.append("    description ");
			sql.append("FROM ");
			sql.append("    d_channel_m ");
			sql.append("WHERE ");
			sql.append("    IsAvailable = 1 ");
			sql.append("ORDER BY ");
			sql.append("    sequence asc ");
			list = qr.query(conn, sql.toString(), new BeanListHandler(ChannelBean.class),new Object[]{});
			conn.close();
		} catch (SQLException e) {
			log.error("[ChannelDaoImpl.getAllChannelBeans] [{0}]", e.getMessage());
		}finally{
			try {
				BaseDAO.close(conn);
			} catch (SQLException e) {
				log.error("[ChannelDaoImpl.getAllChannelBeans] [{0}]", e.getMessage());
			}
		}
		log.info("[ChannelDaoImpl.getAllChannelBeans][end]");
		return list;
   }
}
