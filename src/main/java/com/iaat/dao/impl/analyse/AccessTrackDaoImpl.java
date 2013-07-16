package com.iaat.dao.impl.analyse;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.iaat.dao.analyse.AccessTrackDao;
import com.iaat.dbutils.BaseDAO;
import com.iaat.json.FocusUrlsBean;
import com.iaat.json.SearchBar;
import com.iaat.model.TrackInput;
import com.iaat.model.TrackOutput;
import com.iaat.share.ErrorCode;
import com.iaat.share.Params;
import com.iaat.util.DateUtil;
import com.nokia.ads.common.util.Log;
import com.nokia.ads.platform.backend.core.UncheckedException;
import com.nokia.ads.platform.backend.util.Paging;

/**    
 * @name AccessTrackDaoImpl
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
public class AccessTrackDaoImpl  implements AccessTrackDao {
	private final static Log log = Log.getLogger(AccessTrackDaoImpl.class);
	@SuppressWarnings("unchecked")
	@Override
	public List<TrackInput> getUrlsList(SearchBar bar) {
		List<TrackInput> accBeans = null;
		Connection c = null;
		try {
			c=BaseDAO.openConnection(Params.REGION_NAME);
			QueryRunner qr= new QueryRunner();
			StringBuffer sqlStr = new StringBuffer();
			String tableName = getInTableName(bar);
			sqlStr.append("SELECT ");
			sqlStr.append("    focus_address as focusAddress, ");
			sqlStr.append("    input_address as inputAddress, ");
			sqlStr.append("    imput_UV as imputUv ");
			sqlStr.append("FROM ");
			sqlStr.append(tableName);
			sqlStr.append(" WHERE  time >= ? and time<=? ");
			sqlStr.append("    ORDER BY focus_address LIMIT 0,100");
			BeanListHandler<TrackInput> rsh = new BeanListHandler(TrackInput.class);
			accBeans = qr.query(c, sqlStr.toString(), rsh,new Object[]{bar.getStartDateTime().getTime(),bar.getEndDateTime().getTime()});
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("[AccessTrackDaoImpl.getUrlsList] [{0}]", e.getMessage());
			throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
		}finally{
				try {
					BaseDAO.close(c);
				} catch (SQLException e) {
					e.printStackTrace();
					log.error("[AccessTrackDaoImpl.getUrlsList] [{0}]", e.getMessage());
					throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
				}
		}
		return accBeans;
	}
	
	@Override
	public TrackInput getInputUVCount(SearchBar searchBar,String focusUrl) {
		TrackInput trackInput = null;
		Connection c = null;
		try{
			c=BaseDAO.openConnection(Params.REGION_NAME);
			trackInput = new TrackInput();
			QueryRunner qr = new QueryRunner();
			StringBuffer sqlStr = new StringBuffer();
			String tableName = getInTableName(searchBar);
			sqlStr.append("SELECT ");
			sqlStr.append("    SUM(imput_UV) as  imputUv, ");
			sqlStr.append("    focus_address as  focusAddress ");
			sqlStr.append("FROM ");
			sqlStr.append(tableName);
			sqlStr.append(" WHERE ");
			sqlStr.append("    focus_address REGEXP ? and time >= ? and time<=? ");
			BeanHandler<TrackInput>  rsh = new BeanHandler<TrackInput>(TrackInput.class);
			DateUtil.getFormatEndDate(searchBar.getEndDateTime());
			trackInput = qr.query(c, sqlStr.toString(), rsh,new Object[]{focusUrl,searchBar.getStartDateTime().getTime(),searchBar.getEndDateTime().getTime()});
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("[AccessTrackDaoImpl.getInputUVCount] [{0}]", e.getMessage());
			throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
		}finally{
				try {
					BaseDAO.close(c);
				} catch (SQLException e) {
					e.printStackTrace();
					log.error("[AccessTrackDaoImpl.getInputUVCount] [{0}]", e.getMessage());
					throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
				}
		}
		return trackInput;
	}
	private String getInTableName(SearchBar bar) {
		String tableName = MessageFormat.format("{0}_track_input",bar.getLogSource());
		return tableName;
	}
	private String getOutTableName(SearchBar bar) {
		String tableName = MessageFormat.format("{0}_track_output",bar.getLogSource());
		return tableName;
	}
	
	@Override
	public TrackOutput getOutputUVCount(SearchBar searchBar,String focusUrl) {
		TrackOutput trackOutput = null;
		Connection c = null;
		try{
			c=BaseDAO.openConnection(Params.REGION_NAME);
			trackOutput = new TrackOutput();
			QueryRunner qr = new QueryRunner();
			StringBuffer sqlStr = new StringBuffer();
			String tableName = getOutTableName(searchBar);
			sqlStr.append("SELECT ");
			sqlStr.append("    SUM(output_UV) as outputUv ");
			sqlStr.append("FROM ");
			sqlStr.append(tableName);
			sqlStr.append(" WHERE ");
			sqlStr.append("    focus_address REGEXP ? and time >=? and time<=?  ");
			BeanHandler<TrackOutput>  rsh = new BeanHandler<TrackOutput>(TrackOutput.class);
			DateUtil.getFormatEndDate(searchBar.getEndDateTime());
			trackOutput = qr.query(c, sqlStr.toString(),rsh,new Object[]{focusUrl,searchBar.getStartDateTime().getTime(),searchBar.getEndDateTime().getTime()});
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("[AccessTrackDaoImpl.getOutputUVCount] [{0}]", e.getMessage());
			throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
		}finally{
				try {
					BaseDAO.close(c);
				} catch (SQLException e) {
					e.printStackTrace();
					log.error("[AccessTrackDaoImpl.getOutputUVCount] [{0}]", e.getMessage());
					throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
				}
		}
		return trackOutput;
	}

	@Override
	public List<TrackInput> getInputUrlsByFocus(SearchBar searchBar,
			String focusUrl,Paging paging) {
		List<TrackInput> trackInputs = null;
		Connection c = null;
		try{
			c=BaseDAO.openConnection(Params.REGION_NAME);
			trackInputs = new ArrayList<TrackInput>();
			QueryRunner qr = new QueryRunner();
			StringBuffer sqlStr = new StringBuffer();
			String tableName = getInTableName(searchBar);
			sqlStr.append("SELECT ");
			sqlStr.append("    input_address as inputAddress,");
			sqlStr.append("    sum(imput_UV) as imputUv ");
			sqlStr.append("FROM ");
			sqlStr.append(tableName);
			sqlStr.append(" WHERE ");
			sqlStr.append("    focus_address REGEXP ? and time >= ? and time<=? GROUP BY input_address ORDER BY imputUv desc");
			BeanListHandler<TrackInput>  rsh = new BeanListHandler<TrackInput>(TrackInput.class);
			DateUtil.getFormatEndDate(searchBar.getEndDateTime());
			setPaging(paging, c, qr, sqlStr,new Object[]{focusUrl,searchBar.getStartDateTime().getTime(),searchBar.getEndDateTime().getTime()});
			trackInputs = qr.query(c, sqlStr.toString(), rsh,new Object[]{focusUrl,searchBar.getStartDateTime().getTime(),searchBar.getEndDateTime().getTime()});
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("[AccessTrackDaoImpl.getInputUrlsByFocus] [{0}]", e.getMessage());
			throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
		}finally{
				try {
					BaseDAO.close(c);
				} catch (SQLException e) {
					e.printStackTrace();
					log.error("[AccessTrackDaoImpl.getInputUrlsByFocus] [{0}]", e.getMessage());
					throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
				}
		}
		return trackInputs;
	}

	private void setPaging(Paging paging, Connection c, QueryRunner qr,
			StringBuffer sqlStr,Object[]  params) throws SQLException {
		if(paging!=null){
			int num = queryCount(sqlStr.toString(),c,qr,params);
			paging.setTotalRecord(num);
			sqlStr.append(" limit "+paging.getCurrentRecord()+","+paging.getPageSize());
		}
	}
	private int queryCount(String sql,Connection conn,QueryRunner runner,Object[] params) throws SQLException{
		String countt= process(sql);
		ScalarHandler<Long>  rsh = new ScalarHandler<Long>();
		return runner.query(conn,countt,rsh,params).intValue();
	}
	private String process(String str){
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT COUNT(1) AS NUM FROM (").append(str).append(" ) as p");
		return sb.toString();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<TrackOutput> getOuputUrlsByFocus(SearchBar searchBar,
			String focusUrl,Paging paging) {
		List<TrackOutput> trackOutputs = null;
		Connection c = null;
		try{
			trackOutputs = new ArrayList<TrackOutput>();
			c=BaseDAO.openConnection(Params.REGION_NAME);
			QueryRunner qr = new QueryRunner();
			StringBuffer sqlStr = new StringBuffer();
			String tableName = getOutTableName(searchBar);
			sqlStr.append("SELECT ");
			sqlStr.append("    output_address as outputAddress, ");
			sqlStr.append("    sum(output_UV) as outputUv ");
			sqlStr.append("FROM ");
			sqlStr.append(tableName);
			sqlStr.append(" WHERE ");
			sqlStr.append("    focus_address REGEXP ? and time >= ? and time <=?  GROUP BY output_address ORDER BY outputUv desc");
			BeanListHandler<TrackOutput> rsh = new BeanListHandler(TrackOutput.class);
			DateUtil.getFormatEndDate(searchBar.getEndDateTime());
			Object[] params = {focusUrl,searchBar.getStartDateTime().getTime(),searchBar.getEndDateTime().getTime()};
			setPaging(paging, c, qr, sqlStr,params);
			trackOutputs = qr.query(c, sqlStr.toString(), rsh,params);
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("[AccessTrackDaoImpl.getOuputUrlsByFocus] [{0}]", e.getMessage());
			throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
		}finally{
				try {
					BaseDAO.close(c);
				} catch (SQLException e) {
					e.printStackTrace();
					log.error("[AccessTrackDaoImpl.getOuputUrlsByFocus] [{0}]", e.getMessage());
					throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
				}
		}
		return trackOutputs;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<FocusUrlsBean> getTopFocusUrls(SearchBar searchBar){
		List<FocusUrlsBean> focusUrls = null;
		Connection c = null;
		try{
			focusUrls = new ArrayList<FocusUrlsBean>();
			c=BaseDAO.openConnection(Params.REGION_NAME);
			QueryRunner qr = new QueryRunner();
			StringBuffer sqlStr = new StringBuffer();
			String tableName = getInTableName(searchBar);
			sqlStr.append("SELECT ");
			sqlStr.append("    sum(imput_UV) imputUV, ");
			sqlStr.append("    focus_address as focusUrl ");
			sqlStr.append(" FROM  ");
			sqlStr.append(tableName);
			sqlStr.append(" GROUP BY ");
			sqlStr.append("    focus_address ");
			sqlStr.append(" ORDER BY ");
			sqlStr.append("    imputUV desc LIMIT 0 , 100");
			BeanListHandler<FocusUrlsBean> rsh = new BeanListHandler(FocusUrlsBean.class);
			focusUrls = qr.query(c, sqlStr.toString(), rsh);
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("[AccessTrackDaoImpl.getTopFocusUrls] [{0}]", e.getMessage());
			throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
		}finally{
				try {
					BaseDAO.close(c);
				} catch (SQLException e) {
					e.printStackTrace();
					log.error("[AccessTrackDaoImpl.getTopFocusUrls] [{0}]", e.getMessage());
					throw new UncheckedException(e.getMessage(), ErrorCode.DAO_ERROR);
				}
		}
		return focusUrls;
		}
}
