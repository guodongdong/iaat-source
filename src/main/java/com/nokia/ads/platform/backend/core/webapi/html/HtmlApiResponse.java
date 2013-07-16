package com.nokia.ads.platform.backend.core.webapi.html;

import java.io.Reader;
import java.io.StringReader;

import com.nokia.ads.platform.backend.core.webapi.ApiResponse;
import com.nokia.ads.platform.backend.core.webapi.ApiResult;
import com.nokia.ads.platform.backend.profile.DataFormat;

public class HtmlApiResponse extends ApiResponse {

	private static final String CONTENT_TYPE_HTML_UTF8 = "text/html; charset=utf-8";

	public HtmlApiResponse() {
	}

	@Override
	public String getContentType() {
		return CONTENT_TYPE_HTML_UTF8;
	}

	@Override
	public DataFormat getFormat() {
		return DataFormat.HTML;
	}

	@Override
	public Reader getReader() {
		return new StringReader(formatResult(getResult()));
	}

	private static String formatResult(ApiResult result) {
		
		Object dataResult = result.getResult();
		StringBuffer sbResult = new StringBuffer();
		
		if (dataResult!=null) {
			sbResult.append(dataResult);
		} else {
			Object[] array = result.getResults();
			for (Object o : array) {
				sbResult.append(o);
			}
		}
		
		return sbResult.toString();
	}

}
