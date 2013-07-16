package com.iaat.webapi.profile;

import org.junit.Test;

import com.iaat.share.Params;
import com.iaat.share.RequestUrl;
import com.iaat.webapi.ApiAbstractTestCase;
import com.iaat.webapi.stats.profile.ProfileApi;


public class ProfileApiTest extends ApiAbstractTestCase<ProfileApi> {
	
	@Test
	public void testUserProfile() {
		this.setUp(this.getUrl(RequestUrl.STATS_USER_PROFILE));
		this.addParam(Params.START_DATETIME, 1325347200000L);
		this.addParam(Params.END_DATETIME, 1385827200000L);
		this.addParam(Params.START_TIME, 0); 
		this.addParam(Params.END_TIME, 23);
		this.addParam(Params.CHANNEL, 0);
		this.addParam(Params.PLATFORM, 0); 
		this.addParam(Params.AP, 0);
		this.addParam(Params.LOG_SOURCE, 3);
		this.addParam(Params.PROVINCE, "Beijing"); 
		this.addParam(Params.CITY, "");
		this.addParam(Params.OPERATOR, 0);
		this.addParam(Params.TERMINAL_TYPE, 0); 
		
		
		this.testResonse(this.createApiRequest());
	}

	@Test
	public void testPvchart() {
		this.setUp(this.getUrl(RequestUrl.STATS_PV_CHART));
		this.addParam(Params.START_DATETIME, 1367424000000L);
		this.addParam(Params.END_DATETIME, 1367424000000L);
		this.addParam(Params.START_TIME, 0); 
		this.addParam(Params.END_TIME, 23);
		this.addParam(Params.CHANNEL, 0);
		this.addParam(Params.PLATFORM, 0); 
		this.addParam(Params.AP, 0);
		this.addParam(Params.LOG_SOURCE, 3);
		this.addParam(Params.PROVINCE, "Beijing"); 
		this.addParam(Params.CITY, "");
		this.addParam(Params.OPERATOR, 0);
		this.addParam(Params.TERMINAL_TYPE, 0); 
		
		this.addParam(Params.STATS_TYPE, "daily");
		
		this.testResonse(this.createApiRequest());
	}

	
	@Test
	public void testUvchart() {
		this.setUp(this.getUrl(RequestUrl.STATS_UV_CHART));
		this.addParam(Params.START_DATETIME, 1367424000000L);
		this.addParam(Params.END_DATETIME, 1367424000000L);
		this.addParam(Params.START_TIME, 0); 
		this.addParam(Params.END_TIME, 23);
		this.addParam(Params.CHANNEL, 0);
		this.addParam(Params.PLATFORM, 0); 
		this.addParam(Params.AP, 0);
		this.addParam(Params.LOG_SOURCE, 3);
		this.addParam(Params.PROVINCE, "Beijing"); 
		this.addParam(Params.CITY, "");
		this.addParam(Params.OPERATOR, 0);
		this.addParam(Params.TERMINAL_TYPE, 0); 
		
		this.addParam(Params.STATS_TYPE, "daily");
		
		this.testResonse(this.createApiRequest());
	}
	
	@Test
	public void testIncreaseUserChart() {
		this.setUp(this.getUrl(RequestUrl.STATS_INCREASE_USER_CHART));
		this.addParam(Params.START_DATETIME, 1367424000000L);
		this.addParam(Params.END_DATETIME, 1367424000000L);
		this.addParam(Params.START_TIME, 0); 
		this.addParam(Params.END_TIME, 23);
		this.addParam(Params.CHANNEL, 0);
		this.addParam(Params.PLATFORM, 0); 
		this.addParam(Params.AP, 0);
		this.addParam(Params.LOG_SOURCE, 3);
		this.addParam(Params.PROVINCE, "Beijing"); 
		this.addParam(Params.CITY, "");
		this.addParam(Params.OPERATOR, 0);
		this.addParam(Params.TERMINAL_TYPE, 0); 
		
		this.addParam(Params.STATS_TYPE, "daily");
		
		this.testResonse(this.createApiRequest());
	}

	@Test
	public void testOnceUserChart() {
		this.setUp(this.getUrl(RequestUrl.STATS_ONCE_USER_CHART));
		this.addParam(Params.START_DATETIME, 1367424000000L);
		this.addParam(Params.END_DATETIME, 1367424000000L);
		this.addParam(Params.START_TIME, 0); 
		this.addParam(Params.END_TIME, 23);
		this.addParam(Params.CHANNEL, 0);
		this.addParam(Params.PLATFORM, 0); 
		this.addParam(Params.AP, 0);
		this.addParam(Params.LOG_SOURCE, 3);
		this.addParam(Params.PROVINCE, "Beijing"); 
		this.addParam(Params.CITY, "");
		this.addParam(Params.OPERATOR, 0);
		this.addParam(Params.TERMINAL_TYPE, 0); 
		
		this.addParam(Params.STATS_TYPE, "daily");
		
		this.testResonse(this.createApiRequest());
	}
	
	@Test
	public void testOnlyLoginUserChart() {
		this.setUp(this.getUrl(RequestUrl.STATS_ONLY_LOGIN_USER_CHART));
		this.addParam(Params.START_DATETIME, 1367424000000L);
		this.addParam(Params.END_DATETIME, 1367424000000L);
		this.addParam(Params.START_TIME, 0); 
		this.addParam(Params.END_TIME, 23);
		this.addParam(Params.CHANNEL, 0);
		this.addParam(Params.PLATFORM, 0); 
		this.addParam(Params.AP, 0);
		this.addParam(Params.LOG_SOURCE, 3);
		this.addParam(Params.PROVINCE, "Beijing"); 
		this.addParam(Params.CITY, "");
		this.addParam(Params.OPERATOR, 0);
		this.addParam(Params.TERMINAL_TYPE, 0); 
		
		this.addParam(Params.STATS_TYPE, "daily");
		
		this.testResonse(this.createApiRequest());
	}
	
	@Test
	public void testAvgUserLoginChart() {
		this.setUp(this.getUrl(RequestUrl.STATS_AVG_USER_LOGIN_CHART));
		this.addParam(Params.START_DATETIME, 1367424000000L);
		this.addParam(Params.END_DATETIME, 1367424000000L);
		this.addParam(Params.START_TIME, 0); 
		this.addParam(Params.END_TIME, 23);
		this.addParam(Params.CHANNEL, 0);
		this.addParam(Params.PLATFORM, 0); 
		this.addParam(Params.AP, 0);
		this.addParam(Params.LOG_SOURCE, 3);
		this.addParam(Params.PROVINCE, "Beijing"); 
		this.addParam(Params.CITY, "");
		this.addParam(Params.OPERATOR, 0);
		this.addParam(Params.TERMINAL_TYPE, 0); 
		
		this.addParam(Params.MIN_COUNT, 1);
		this.addParam(Params.INCREMENT, 1);
		
		this.testResonse(this.createApiRequest());
	}
	
	@Test
	public void testAvgUserAccessChart() {
		this.setUp(this.getUrl(RequestUrl.STATS_AVG_USER_ACCESS_CHART));
		this.addParam(Params.START_DATETIME, 1367424000000L);
		this.addParam(Params.END_DATETIME, 1367424000000L);
		this.addParam(Params.START_TIME, 0); 
		this.addParam(Params.END_TIME, 23);
		this.addParam(Params.CHANNEL, 0);
		this.addParam(Params.PLATFORM, 0); 
		this.addParam(Params.AP, 0);
		this.addParam(Params.LOG_SOURCE, 3);
		this.addParam(Params.PROVINCE, "Beijing"); 
		this.addParam(Params.CITY, "");
		this.addParam(Params.OPERATOR, 0);
		this.addParam(Params.TERMINAL_TYPE, 0); 
		this.addParam(Params.MIN_COUNT, 1);
		this.addParam(Params.INCREMENT, 1);
		
		this.testResonse(this.createApiRequest());
	}
}
