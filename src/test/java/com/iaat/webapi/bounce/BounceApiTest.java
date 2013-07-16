package com.iaat.webapi.bounce;

import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.iaat.json.LatitudeBean;
import com.iaat.share.Params;
import com.iaat.share.RequestUrl;
import com.iaat.util.DateUtil;
import com.iaat.webapi.ApiAbstractTestCase;

public class BounceApiTest extends ApiAbstractTestCase<BounceApi> {
	@Before
	public void addData(){
		LatitudeBean lat = LatitudeBean.getInstance();
		lat.setReload(true);
		lat.reLoadData();
	}
	@Test
	public void testGetBounceRate() {
		this.setUp(this.getUrl(RequestUrl.ANALYSE_BOUNCE_RATE));
		Calendar beginDate = DateUtil.getCalendar(-16);
		Calendar endDate = DateUtil.getCalendar(0);
		this.addParam(Params.START_DATETIME, beginDate);
		this.addParam(Params.END_DATETIME, endDate);
		this.addParam(Params.START_TIME, 0); 
		this.addParam(Params.END_TIME, 24);
		this.addParam(Params.CHANNEL, -1);
		this.addParam(Params.PLATFORM, -1); 
		this.addParam(Params.AP, -1);
		this.addParam(Params.OPERATOR, -1);
		this.addParam(Params.TERMINAL_TYPE, -1);
		this.addParam(Params.LOG_SOURCE, 4);
		this.addParam(Params.PROVINCE, "Hunan"); 
		this.addParam(Params.CITY, "Changsha");
		this.addParam(Params.STATS_TYPE, "daily"); 
		this.testResonse(this.createApiRequest());
		try {
			this.testResonse(this.createApiRequest());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		/*Calendar beginDate = DateUtil.getCalendar(-2);
		Calendar endDate = DateUtil.getCalendar(0);*/
		/*Date date  = new Date(1325347200000l);
		System.out.println(date);*/
		
		Calendar beginDate = DateUtil.getCalendar(-31);
		Calendar endDate = DateUtil.getCalendar(0);
		 long gap = (beginDate.getTimeInMillis()-endDate.getTimeInMillis())/(1000*3600*24);//从间隔
		System.out.println(gap);
	}


}
