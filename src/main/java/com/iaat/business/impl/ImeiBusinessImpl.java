package com.iaat.business.impl;

import java.util.List;

import com.iaat.business.stats.ImeiBusiness;
import com.iaat.dao.factory.DaoFactory;
import com.iaat.dao.stats.ImeiAccessDao;
import com.iaat.json.SearchBar;
import com.iaat.model.ImeiAccess;
import com.iaat.model.LogSourceType;
import com.nokia.ads.platform.backend.util.Paging;
public class ImeiBusinessImpl implements ImeiBusiness {
	private static ImeiAccessDao dao = DaoFactory.getInstance(ImeiAccessDao.class);
	@Override
	public List<ImeiAccess> getImeiList(SearchBar bar, LogSourceType log,
			String imei, Paging paging) {
		return dao.getImeiList(bar, log, imei, paging);
	}

}
