
package com.iaat.webapi.latitude;
import org.junit.Test;

import com.iaat.share.RequestUrl;
import com.iaat.webapi.ApiAbstractTestCase;


public class LatitudeApiTest extends ApiAbstractTestCase<LatitudeApi> {
	
	@Test
	public void testLatitudes() {
		this.setUp(this.getUrl(RequestUrl.GET_SEARCHBAR_LIST));
		try {
			this.testResonse(this.createApiRequest());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	@Test
	public void testLatitudesIsFresh() {
		this.setUp(this.getUrl(RequestUrl.GET_SEARCHBAR_IS_FRESH));
		
		try {
			this.testResonse(this.createApiRequest());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
}
