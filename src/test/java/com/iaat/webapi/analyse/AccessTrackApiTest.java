package com.iaat.webapi.analyse;

import java.sql.Date;
import java.util.Calendar;

import org.junit.Test;

import com.iaat.share.Params;
import com.iaat.share.RequestUrl;
import com.iaat.util.DateUtil;
import com.iaat.webapi.ApiAbstractTestCase;
import com.iaat.webapi.analyse.track.AccessTrackApi;


public class AccessTrackApiTest extends ApiAbstractTestCase<AccessTrackApi> {
	@Test
	public void testUrlsList() {
		this.setUp(this.getUrl(RequestUrl.ANALYSE_AS_URLS_LIST));
		this.addParam(Params.LOG_SOURCE, 3);
		Calendar beginDate = DateUtil.getCalendar(-10);
		Calendar endDate = DateUtil.getCalendar(-1);
		this.addParam(Params.START_DATETIME, beginDate);
		this.addParam(Params.END_DATETIME, endDate);
		
		try {
			this.testResonse(this.createApiRequest());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		Date date = new Date(1371744000000l);
		Date dat = new Date(1371744000000l);
		System.out.println(date);
	}
	//ok
	@Test
	public void testInputUrlsApi() {
		this.setUp(this.getUrl(RequestUrl.ANALYSE_INPUT_URLS));
		this.addParam(Params.LOG_SOURCE, 3);
		this.addParam(Params.FOCUS_URLS, "tom.com");
		this.addParam("iDisplayStart", 0);
		this.addParam("iDisplayLength", 5);
		Calendar beginDate = DateUtil.getCalendar(-1);
		Calendar endDate = DateUtil.getCalendar(-1);
		this.addParam(Params.START_DATETIME, beginDate);
		this.addParam(Params.END_DATETIME, endDate);
		try {
			this.testResonse(this.createApiRequest());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//ok
	@Test
	public void testOutputUrlsApi() {
		this.setUp(this.getUrl(RequestUrl.ANALYSE_OUTPUT_URLS));
		this.addParam(Params.LOG_SOURCE, 3);
		this.addParam(Params.FOCUS_URLS, "");
		this.addParam("iDisplayStart", 0);
		this.addParam("iDisplayLength", 5);
		Calendar beginDate = DateUtil.getCalendar(-1);
		Calendar endDate = DateUtil.getCalendar(-1);
		this.addParam(Params.START_DATETIME, beginDate);
		this.addParam(Params.END_DATETIME, endDate);
		try {
			this.testResonse(this.createApiRequest());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//ok
	@Test
	public void testInputUVCountApi() {
		this.setUp(this.getUrl(RequestUrl.ANALYSE_INPUT_UV_COUNT));
		this.addParam(Params.LOG_SOURCE, 3);
		this.addParam(Params.FOCUS_URLS, "tom.com");//
		Calendar beginDate = DateUtil.getCalendar(-1);
		Calendar endDate = DateUtil.getCalendar(-1);
		this.addParam(Params.START_DATETIME, beginDate);
		this.addParam(Params.END_DATETIME, endDate);
		try {
			this.testResonse(this.createApiRequest());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//ok
	@Test
	public void testUVCountApi() {
		this.setUp(this.getUrl(RequestUrl.ANALYSE_UV_COUNT));
		this.addParam(Params.LOG_SOURCE, 4);
		this.addParam(Params.FOCUS_URLS, "");//http://3g.qq.com
		Calendar beginDate = DateUtil.getCalendar(-1);
		Calendar endDate = DateUtil.getCalendar(-1);
		this.addParam(Params.START_DATETIME, beginDate);
		this.addParam(Params.END_DATETIME, endDate);
		try {
			this.testResonse(this.createApiRequest());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	//ok
	@Test
	public void testOutputUVCountApi() {
		this.setUp(this.getUrl(RequestUrl.ANALYSE_OUTPUT_UV_COUNT));
		this.addParam(Params.LOG_SOURCE, 3);
		this.addParam(Params.FOCUS_URLS, "");
		Calendar beginDate = DateUtil.getCalendar(-1);
		Calendar endDate = DateUtil.getCalendar(-1);
		this.addParam(Params.START_DATETIME, beginDate);
		this.addParam(Params.END_DATETIME, endDate);
		try {
			this.testResonse(this.createApiRequest());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//ok
	@Test
	public void testTopUrlsApi() {
		this.setUp(this.getUrl(RequestUrl.ANALYSE_OUTPUT_TOP_URLS));
		this.addParam(Params.LOG_SOURCE, 3);
		this.addParam(Params.FOCUS_URLS, "");
		Calendar beginDate = DateUtil.getCalendar(-1);
		Calendar endDate = DateUtil.getCalendar(-1);
		this.addParam(Params.START_DATETIME, beginDate);
		this.addParam(Params.END_DATETIME, endDate);
		try {
			this.testResonse(this.createApiRequest());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
