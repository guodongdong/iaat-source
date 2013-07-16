package com.iaat.dao.overlap;

import com.iaat.json.overlap.ContrastSearchBean;
import com.iaat.json.overlap.CrossContrastBean;

public interface CrossContrastDao {

	public CrossContrastBean getCorssConstrasts(ContrastSearchBean searchBar);

	public CrossContrastBean getCorssConstrastsByTotal(ContrastSearchBean searchBar);
}
