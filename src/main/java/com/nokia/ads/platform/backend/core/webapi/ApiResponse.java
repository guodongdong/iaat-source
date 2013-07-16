package com.nokia.ads.platform.backend.core.webapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import com.nokia.ads.common.util.Log;
import com.nokia.ads.platform.backend.core.webapi.html.HtmlApiResponse;
import com.nokia.ads.platform.backend.core.webapi.json.JsonApiResponse;
import com.nokia.ads.platform.backend.profile.DataFormat;

abstract public class ApiResponse {

	static {
		Register(DataFormat.JSON, JsonApiResponse.class);
		Register(DataFormat.HTML, HtmlApiResponse.class);
	}

	private static final Log log = Log.getLogger(ApiResponse.class);

	private static Map<DataFormat, Class<? extends ApiResponse>> factory;

	public static void Register(DataFormat format,
			Class<? extends ApiResponse> clz) {
		if (factory == null)
			factory = new HashMap<DataFormat, Class<? extends ApiResponse>>();
		factory.put(format, clz);
	}

	public static ApiResponse getInstance(DataFormat dataType, ApiResult result) {
		Class<? extends ApiResponse> clz = factory.get(dataType);
		if (clz != null) {
			try {
				ApiResponse resp = clz.newInstance();
				resp.setResult(result);
				return resp;
			} catch (Exception ex) {
				log.error(ex);
			}
		}
		throw new IllegalArgumentException("unsupport data format");
	}

	abstract public DataFormat getFormat();

	abstract public String getContentType();

	abstract public Reader getReader();

	private ApiResult result = null;

	public ApiResult getResult() {
		return this.result;
	}

	public void setResult(ApiResult result) {
		this.result = result;
	}

	@Deprecated
	public void write(OutputStream out, String charset) throws IOException {
		char[] charArray = new char[1024 * 512]; // 512kb
		int readLength = 0;
		BufferedReader bufferedApiResponse = new BufferedReader(getReader());
		while ((readLength = bufferedApiResponse.read(charArray)) > -1) {
			byte[] responseBytes = null;
			if (charset == null || "".equals(charset)) {
				responseBytes = (new String(charArray, 0, readLength))
						.getBytes();
			} else {
				responseBytes = (new String(charArray, 0, readLength))
						.getBytes(charset);
			}
			out.write(responseBytes);
			out.flush();
		}
	}

	public void write(Writer writer) throws IOException {
		char[] charArray = new char[1024 * 512]; // 512kb
		int readLength = 0;
		BufferedReader bufferedApiResponse = new BufferedReader(getReader());
		while ((readLength = bufferedApiResponse.read(charArray)) > -1) {
			String str = new String(charArray, 0, readLength);
			writer.write(str);
			writer.flush();
		}
	}

}
