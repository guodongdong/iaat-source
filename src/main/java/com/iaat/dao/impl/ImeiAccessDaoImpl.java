package com.iaat.dao.impl;

import java.util.List;

import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.iaat.dao.BaseDaoUtils;
import com.iaat.dao.stats.ImeiAccessDao;
import com.iaat.json.SearchBar;
import com.iaat.model.ImeiAccess;
import com.iaat.model.LogSourceType;
import com.iaat.share.Params;
import com.iaat.util.SqlUtils;
import com.nokia.ads.common.util.Log;
import com.nokia.ads.platform.backend.util.Paging;

public class ImeiAccessDaoImpl extends BaseDaoUtils<ImeiAccess> implements ImeiAccessDao {

	private final static Log logger = Log.getLogger(ImeiAccessDaoImpl.class);
	/**
	 * 获取imei的访问信息
	 */
	@Override
	public List<ImeiAccess> getImeiList(SearchBar bar, LogSourceType log,
			String imei, Paging paging) {
		logger.info("ImeiAccessDaoImpl.getImeiList begin ");
		StringBuffer sb = new StringBuffer();
		String like = imei==null?" ":" and Params.D_IMEI.IMEI_NUM like '%"+imei+"%' ";
		List<ImeiAccess> imeis= null;
		sb.append(SqlUtils.getIns().getSQL("getImeiList", log));
		sb.append(getConditions(bar, true, " and ")).append(like+" GROUP BY "+Params.D_IMEI+".IMEI_FK,"+Params.D_OPERATOR+".Operator_FK,"+Params.D_REGION+".Region_FK ORDER BY NUM DESC ");
		try {
			imeis= query(bar,sb.toString(), new BeanListHandler<ImeiAccess>(ImeiAccess.class),paging,true);
		} catch (Exception e) {
			logger.error("ImeiAccessDaoImpl.getImeiList error:"+e.getMessage());
		}
		logger.info("ImeiAccessDaoImpl.getImeiList end ");
		return imeis;
	}

}
