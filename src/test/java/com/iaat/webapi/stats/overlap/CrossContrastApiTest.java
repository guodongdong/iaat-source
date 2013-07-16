package com.iaat.webapi.stats.overlap;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.iaat.json.LatitudeBean;
import com.iaat.json.overlap.ContrastResultBean;
import com.iaat.json.overlap.ContrastSearchBean;
import com.iaat.share.Params;
import com.iaat.share.RequestUrl;
import com.iaat.util.DateUtil;
import com.iaat.webapi.ApiAbstractTestCase;

public class CrossContrastApiTest extends ApiAbstractTestCase<CrossContrastApi> {
	
	@Before
	public void addData(){
		LatitudeBean lat = LatitudeBean.getInstance();
		lat.setReload(true);
		lat.reLoadData();
	}
	@Test
	public void testCorssConstrasts() {
		Calendar beginDate = DateUtil.getCalendar(-70);
		Calendar endDate = DateUtil.getCalendar(-1);
		
		this.setUp(this.getUrl(RequestUrl.STATS_CROSS_CONTRAST_CASE_LIST));
		List<ContrastSearchBean> consBeans = new ArrayList<ContrastSearchBean>();
		ContrastSearchBean consBean1 = new ContrastSearchBean();
		consBean1.setStartDateTime(beginDate);
		consBean1.setEndDateTime(endDate);
		consBean1.setStartTime(0);
		consBean1.setEndTime(23);
		consBean1.setChannel(0);
		consBean1.setPlatform(0);
		consBean1.setAp(1);
		consBean1.setProvince("Beijing");
		consBean1.setCity("Beijing");
		consBean1.setOperator(1);
		consBean1.setTerminalType(92);
		consBean1.setName("case1");
		consBean1.setCategory(10);
		
		ContrastSearchBean case2 = setConsBeanTotal(beginDate, endDate,"case2");
		ContrastSearchBean case3 = setConsBeanTotal(beginDate, endDate,"case3");
		ContrastSearchBean case4 = setConsBeanTotal(beginDate, endDate,"case4");
		ContrastSearchBean case5 = setConsBeanTotal(beginDate, endDate,"case5");
		ContrastSearchBean case6 = setConsBeanTotal(beginDate, endDate,"case6");
		ContrastSearchBean case7 = setConsBeanTotal(beginDate, endDate,"case7");
		ContrastSearchBean case8 = setConsBeanTotal(beginDate, endDate,"case8");
		ContrastSearchBean case9 = setConsBeanTotal(beginDate, endDate,"case9");
		ContrastSearchBean case10 = setConsBeanTotal(beginDate, endDate,"case10");
		ContrastResultBean resultBean = new ContrastResultBean();
		resultBean.setActiveUser(true);
		resultBean.setAvgAccessUser(true);
		resultBean.setAvgIntervalTime(true);
		resultBean.setAvgLoginUser(true);
		// resultBean.setNewUser(true);
		// resultBean.setOldUser(true);
		// resultBean.setOnceAccessUser(true);
		// resultBean.setOnlyLoginUser(true);
		// resultBean.setPV(true);
		// resultBean.setTotalUser(true);
		// resultBean.setUV(true);
		// resultBean.setWap_PV(true);
		// resultBean.setWeb_PV(true);
		
		consBeans.add(consBean1);
		consBeans.add(case2);
		consBeans.add(case3);
		consBeans.add(case4);
		consBeans.add(case5);
		consBeans.add(case6);
		consBeans.add(case7);
		consBeans.add(case8);
		consBeans.add(case9);
		consBeans.add(case10);
		this.addParam(Params.SEARCH_CONSTRAST,consBeans.toArray(new ContrastSearchBean[]{}));
		this.addParam(Params.RESULT_CONSTRAST,resultBean);
		this.testResonse(this.createApiRequest());
	}
	private ContrastSearchBean setConsBeanTotal(Calendar beginDate,
			Calendar endDate,String caseNumber) {
		ContrastSearchBean consBeanTotal = new ContrastSearchBean();
		consBeanTotal.setStartDateTime(beginDate);
		consBeanTotal.setEndDateTime(endDate);
		consBeanTotal.setStartTime(0);
		consBeanTotal.setEndTime(23);
		consBeanTotal.setChannel(86);
		consBeanTotal.setPlatform(3);
		consBeanTotal.setAp(2);
		consBeanTotal.setProvince("Beijing");
		consBeanTotal.setCity("Beijing");
		consBeanTotal.setOperator(6);
		consBeanTotal.setTerminalType(0);
		consBeanTotal.setName(caseNumber);
		return consBeanTotal;
	}
	
	
}
