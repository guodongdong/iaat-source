package com.iaat.dbutils;

import java.sql.Connection;
import java.sql.SQLException;

import com.iaat.dbutils.connection.AnhuiConnection;
import com.iaat.dbutils.connection.BaseConnection;
import com.iaat.dbutils.connection.BeijingConnection;
import com.iaat.dbutils.connection.ChongqingConnection;
import com.iaat.dbutils.connection.ForeignConnection;
import com.iaat.dbutils.connection.FujianConnection;
import com.iaat.dbutils.connection.GansuConnection;
import com.iaat.dbutils.connection.GuangdongConnection;
import com.iaat.dbutils.connection.GuangxiConnection;
import com.iaat.dbutils.connection.GuizhouConnection;
import com.iaat.dbutils.connection.HainanConnection;
import com.iaat.dbutils.connection.HebeiConnection;
import com.iaat.dbutils.connection.HeilongjiangConnection;
import com.iaat.dbutils.connection.HenanConnection;
import com.iaat.dbutils.connection.HongkongConnection;
import com.iaat.dbutils.connection.HubeiConnection;
import com.iaat.dbutils.connection.HunanConnection;
import com.iaat.dbutils.connection.InnerMongoliaConnection;
import com.iaat.dbutils.connection.JiangsuConnection;
import com.iaat.dbutils.connection.JiangxiConnection;
import com.iaat.dbutils.connection.JilinConnection;
import com.iaat.dbutils.connection.LiaoningConnection;
import com.iaat.dbutils.connection.MacaoConnection;
import com.iaat.dbutils.connection.NingxiaConnection;
import com.iaat.dbutils.connection.OtherConnection;
import com.iaat.dbutils.connection.QinghaiConnection;
import com.iaat.dbutils.connection.ShandongConnection;
import com.iaat.dbutils.connection.ShanghaiConnection;
import com.iaat.dbutils.connection.ShanxiConnection;
import com.iaat.dbutils.connection.SichuanConnection;
import com.iaat.dbutils.connection.TaiwanConnection;
import com.iaat.dbutils.connection.TianjinConnection;
import com.iaat.dbutils.connection.TibetConnection;
import com.iaat.dbutils.connection.XinjiangConnection;
import com.iaat.dbutils.connection.YunnanConnection;
import com.iaat.dbutils.connection.ZhejiangConnection;
import com.nokia.ads.common.util.Log;


/**
 * 
 * <p>Title: BaseDAO</p>
 * <p>Description: </p>
 * @author    pandeng
 * @version   1.0
 */

public class BaseDAO {


	private static final Log logger = Log.getLogger(BaseDAO.class);

	public static  synchronized BaseConnection getConnection(String region)
			throws java.sql.SQLException {
		BaseConnection conn=null;
		try {
			if(region.equalsIgnoreCase("Anhui")){
				conn=AnhuiConnection.getInstance();
			}else if(region.equalsIgnoreCase("Beijing")){
				conn=BeijingConnection.getInstance();
			}else if(region.equalsIgnoreCase("Chongqing")){
				conn=ChongqingConnection.getInstance();
			}else if(region.equalsIgnoreCase("Foreign")){
				conn=ForeignConnection.getInstance();
			}else if(region.equalsIgnoreCase("Fujian")){
				conn=FujianConnection.getInstance();
			}else if(region.equalsIgnoreCase("Gansu")){
				conn=GansuConnection.getInstance();
			}else if(region.equalsIgnoreCase("Guangdong")){
				conn=GuangdongConnection.getInstance();
			}else if(region.equalsIgnoreCase("Guangxi")){
				conn=GuangxiConnection.getInstance();
			}else if(region.equalsIgnoreCase("Guizhou")){
				conn=GuizhouConnection.getInstance();
			}else if(region.equalsIgnoreCase("Hainan")){
				conn=HainanConnection.getInstance();
			}else if(region.equalsIgnoreCase("Hebei")){
				conn=HebeiConnection.getInstance();
			}else if(region.equalsIgnoreCase("Heilongjiang")){
				conn=HeilongjiangConnection.getInstance();
			}else if(region.equalsIgnoreCase("Henan")){
				conn=HenanConnection.getInstance();
			}else if(region.equalsIgnoreCase("Hongkong")){
				conn=HongkongConnection.getInstance();
			}else if(region.equalsIgnoreCase("Hubei")){
				conn=HubeiConnection.getInstance();
			}else if(region.equalsIgnoreCase("Hunan")){
				conn=HunanConnection.getInstance();
			}else if(region.equalsIgnoreCase("InnerMongolia")){
				conn=InnerMongoliaConnection.getInstance();
			}else if(region.equalsIgnoreCase("Jiangsu")){
				conn=JiangsuConnection.getInstance();
			}else if(region.equalsIgnoreCase("Jiangxi")){
				conn=JiangxiConnection.getInstance();
			}else if(region.equalsIgnoreCase("Jilin")){
				conn=JilinConnection.getInstance();
			}else if(region.equalsIgnoreCase("Liaoning")){
				conn=LiaoningConnection.getInstance();
			}else if(region.equalsIgnoreCase("Macao")){
				conn=MacaoConnection.getInstance();
			}else if(region.equalsIgnoreCase("Ningxia")){
				conn=NingxiaConnection.getInstance();
			}else if(region.equalsIgnoreCase("Other")){
				conn=OtherConnection.getInstance();
			}else if(region.equalsIgnoreCase("Qinghai")){
				conn=QinghaiConnection.getInstance();
			}else if(region.equalsIgnoreCase("Shandong")){
				conn=ShandongConnection.getInstance();
			}else if(region.equalsIgnoreCase("Shanghai")){
				conn=ShanghaiConnection.getInstance();
			}else if(region.equalsIgnoreCase("Shanxi")){
				conn=ShanxiConnection.getInstance();
			}else if(region.equalsIgnoreCase("Sichuan")){
				conn=SichuanConnection.getInstance();
			}else if(region.equalsIgnoreCase("Taiwan")){
				conn=TaiwanConnection.getInstance();
			}else if(region.equalsIgnoreCase("Tianjin")){
				conn=TianjinConnection.getInstance();
			}else if(region.equalsIgnoreCase("Tibet")){
				conn=TibetConnection.getInstance();
			}else if(region.equalsIgnoreCase("Xinjiang")){
				conn=XinjiangConnection.getInstance();
			}else if(region.equalsIgnoreCase("Yunnan")){
				conn=YunnanConnection.getInstance();
			}else if(region.equalsIgnoreCase("Zhejiang")){
				conn=ZhejiangConnection.getInstance();
			}else{
				logger.error("unusable region: {0}",region);
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new SQLException("Cannot get connection!" + e.getMessage());
		}
		if (conn == null)
			throw new SQLException("Cannot get connection!");
		
		return conn;
	}

	public static  synchronized Connection openConnection (String region) throws SQLException{
		
		BaseConnection bc=BaseDAO.getConnection(region);
		return bc.getConnection();
		
	}
	
	public static  synchronized  void close (Connection conn) throws SQLException{
		
		if(conn!=null)
			conn.close();
		
	}
}