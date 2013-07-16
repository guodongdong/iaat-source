package com.iaat.share;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.nokia.ads.common.util.Log;
import com.nokia.ads.platform.backend.core.UncheckedException;

/**
 * @description 读取系统配置文件
 * @author AlanFan
 * 
 */
public  class SystemConfig {
	private static final Log log = Log.getLogger(SystemConfig.class);
	
	private static final String PATH = "config/systemConfig.properties";
	private static final Map<String, String> MAPPINGS = new HashMap<String, String>();
	private static FileInputStream inputStream;
	private static Properties properties = new Properties();
	static {
		URL url = SystemConfig.class.getClassLoader().getResource(PATH);
		try {
			inputStream = new FileInputStream(url.getFile());
			properties.load(inputStream);
			for (String key : properties.stringPropertyNames()) {
				MAPPINGS.put(key, properties.getProperty(key));
			}
			
		} catch (FileNotFoundException e) {
			log.error("[SystemConfig.static] [ error:{0}]",e.getMessage());
			throw new UncheckedException("",ErrorCode.SYSTEM_CONFIG_NOT_FOUND);
		} catch (IOException e) {
			log.error("[SystemConfig.static] [ error:{0}]",e.getMessage());
			throw new UncheckedException("",ErrorCode.SYSTEM_CONFIG_INIT_ERROR);
		}finally{
			try {
				inputStream.close();
			} catch (IOException e) {
				log.error("[SystemConfig.static] [ error:{0}]",e.getMessage());
			}
		}
	}

	public static String getSystemConfig(String key) {
		String value = MAPPINGS.get(key);
		return value;
	}
	
	//email config
	public static final String EMAIL="email";
	public static final String PASSWORD="password";
	public static final String EMAILTYPE="emailType";
	public static final String EMAIL_TEMPLATE="email_template";
	//config
	public static final String MCHUNKSERVER="mchunkserver";
	public static final String MCHUNK="mchunk";
	public static final String MCHUNKPORT="mchunkport";
	public static final String LOGDSERVER="logdserver";
	public static final String LOGD="logd";
	public static final String LOGDPORT="logdport";
	public static final String FRAUDSERVER="fraudserver";
	public static final String FRAUD="fraud";
	public static final String FRAUDPORT="fraudport";
	public static final String ANALYSISSERVER="analysisserver";
	public static final String ANALYSIS="analysis";
	public static final String ANALYSISPORT="analysisport";
	
	//file save path
	public static final String FILEUPLOADPATH="file_upload_temp";
	//report message
	public static final String SD="SD";
	public static final String MP="MP";
	public static final String PERSONALDEVELOPERVENDERCODE="personalDeveloperVenderCode";
	public static final String FINANCEEMAIL="financeEmail";
	public static final String FINANCENAME="financeName";
	public static final String REASON="reason";
	//download sdk by platform
	public static final String ANDROID="ANDROID";
	public static final String WINDOWS_PHONE="WINDOWS_PHONE";
	public static final String IOS="IOS";
	public static final String S40="S40";
	//cpa代码邮件
	public static final String SERVER_NAME="server_name";
	//find password 
	public static final String FIND_PASSWORD_SEND_EMAIL_MESSAGE = "find_password_send_email_message";
	public static final String FIND_PASSWORD_TITLE = "find_password_title";
	public static final String FIND_PASSWORD_PARAMETER_ACTIVE="find_password_parameter_active";
	public static final String FIND_PASSWORD_PARAMETER_AUTHTOKEN="find_password_parameter_authToken";
	public static final String NOKIA_ADVERTISER = "nokia.advertiser";
	public static final String NOKIA_RESELLER = "nokia.reseller";
	public static final String NOKIA_DEVELOPER = "nokia.developer";
	public static final String IS_VERIFICATION = "is_verification";
	public static final String VERIFICATION_CODE = "verificationcode";
	public static final String RECHARGED_MONEY = "recharged_money";
	public static final String MAX_BID = "max_bid";
	public static final String cpmJava_MIN = "cpmJava_min";
	public static final String cpcJava_MIN = "cpcJava_min";
	public static final String cpmS40_MIN = "cpmS40_min";
	public static final String cpcS40_MIN = "cpcS40_min";
	public static final String cpmIphone_MIN = "cpmIphone_min";
	public static final String cpcIphone_MIN = "cpcIphone_min";
	public static final String cpmAndroid_MIN = "cpmAndroid_min";
	public static final String cpcAndroid_MIN = "cpcAndroid_min";
	public static final String cpmIpad_MIN = "cpmIpad_min";
	public static final String cpcIpad_MIN = "cpcIpad_min";
	public static final String cpmSYMBIAN_MIN = "cpmSymbian_min";
	public static final String cpcSYMBIAN_MIN = "cpcSymbian_min";
	
	public static final String cpmMeego_MIN = "cpmMeego_min";
	public static final String cpcMeego_MIN = "cpcMeego_min";
	public static final String cpmWINDOWS_PHONE_MIN = "cpmWinodws_phone_min";
	public static final String cpcWINDOWS_PHONE_MIN = "cpcWinodws_phone_min";
	public static final String cpmWap_MIN = "cpmWap_min";
	public static final String cpcWap_MIN = "cpcWap_min";
	public static final String cpmIOS_MIN = "cpmIos_min";
	public static final String cpcIOS_MIN = "cpcIos_min";
	public static final String cpmIphone_MAX = "cpmIphone_max";
	public static final String cpcIphone_MAX = "cpcIphone_max";
	public static final String cpmAndroid_MAX = "cpmAndroid_max";
	public static final String cpcAndroid_MAX = "cpcAndroid_max";
	public static final String cpmIpad_MAX = "cpmIpad_max";
	public static final String cpcIpad_MAX = "cpcIpad_max";
	public static final String cpmSYMBIAN_MAX = "cpmSymbian_max";
	public static final String cpcSYMBIAN_MAX = "cpcSymbian_max";
	
	public static final String cpmMeego_MAX = "cpmMeego_max";
	public static final String cpcMeego_MAX = "cpcMeego_max";
	public static final String cpmWINDOWS_PHONE_MAX = "cpmWinodws_phone_max";
	public static final String cpcWINDOWS_PHONE_MAX = "cpmWinodws_phone_max";
	public static final String cpmWap_MAX = "cpmWap_max";
	public static final String cpcWap_MAX = "cpcWap_max";
	public static final String cpmIOS_MAX = "cpmIos_max";
	public static final String cpcIOS_MAX = "cpcIos_max";
	public static final String cpmJava_MAX = "cpmJava_max";
	public static final String cpcJava_MAX = "cpcJava_max";
	public static final String cpmS40_MAX = "cpmS40_max";
	public static final String cpcS40_MAX = "cpcS40_max";
}