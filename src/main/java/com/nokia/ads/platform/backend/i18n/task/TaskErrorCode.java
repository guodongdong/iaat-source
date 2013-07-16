package com.nokia.ads.platform.backend.i18n.task;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nokia.ads.common.util.ObjectUtils;
import com.nokia.ads.common.util.StringUtils;

public class TaskErrorCode {
	
	//8102,8103
	private final static List<String> errorCodeInit = Arrays.asList(new String[]{"BadAuthentication", "QuotaCheckError.INVALID_TOKEN_HEADER",
			"BAIDU_801", "BAIDU_802", "BAIDU_8101", "BAIDU_8102", "BAIDU_8103", "BAIDU_8104", "BAIDU_8201", "BAIDU_8202", "BAIDU_8301", 
			"BAIDU_8303", "BAIDU_8401", "BAIDU_8402", "BAIDU_8403", "BAIDU_8404", "BAIDU_8405", "BAIDU_8406", "BAIDU_8407", "BAIDU_8408", 
			"BAIDU_8409", "BAIDU_8410", "BAIDU_8411", "BAIDU_8412", "BAIDU_8501", "SOGOU_1", "SOGOU_2", "SOGOU_3", "SOGOU_4", "SOGOU_5", 
			"SOGOU_6", "SOGOU_7", "SOGOU_8", "SOGOU_9", "SOGOU_10", "SOGOU_18", "SOGOU_22", "SOGOU_23", "SOGOU_24", "SOGOU_25", "SOGOU_26", 
			"SOGOU_53", "SOGOU_54", "SOGOU_55"});
	
	private final static List<String> notAllowImportList = Arrays.asList(new String[]{"QuotaCheckError.INVALID_TOKEN_HEADER",
			"BAIDU_801", "BAIDU_802", "BAIDU_8104", "BAIDU_8201",
			"BAIDU_8303", "BAIDU_8404", "BAIDU_8405", "BAIDU_8406", "BAIDU_8407", "BAIDU_8408", 
			"BAIDU_8409", "BAIDU_8410", "BAIDU_8411", "BAIDU_8412", "BAIDU_8501", "SOGOU_2", "SOGOU_3", "SOGOU_18", "SOGOU_25", "SOGOU_26", 
			"SOGOU_53", "SOGOU_54", "SOGOU_55"});
	
	public final static String BADAUTHENTICATION = "BadAuthentication";
	public final static String INVALIDTOKEN = "QuotaCheckError.INVALID_TOKEN_HEADER";
//	
	private static final Map<String,Integer> taskCode = new HashMap<String,Integer>();
	static{
		taskCode.put(BADAUTHENTICATION, 1101);
		taskCode.put(INVALIDTOKEN, 1102);
	}
	
	public static Integer getTaskErrorCode(String key){
		if(StringUtils.hasLength(key) && taskCode.containsKey(key)){
			return taskCode.get(key);
		}else{
			return 1000;
		}
	}

	
	/**
	 * allow or not import account.
	 * @param inputErrorCodes
	 * @return
	 */
	public static boolean isAllowImport(String inputErrorCodes){
		if(!StringUtils.hasLength(inputErrorCodes)){
			return true;
		}
		if(notAllowImportList.contains(inputErrorCodes)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * allow or not import account.
	 * @param inputErrorCodes Collection.
	 * @return
	 */
	public static boolean isAllowImport(String[] inputErrorCodes){
		if(ObjectUtils.isEmpty(inputErrorCodes)){
			return true;
		}
		for(String inputErrorCode : inputErrorCodes){
			if(!notAllowImportList.contains(inputErrorCode)){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 
	 * @param inputErrorCodes
	 * @return
	 */
	public static boolean checkCodes(List<String> inputErrorCodes){
		if(ObjectUtils.isEmpty(inputErrorCodes)){
			return false;
		}
		for(String inputErrorCode : inputErrorCodes){
			if(errorCodeInit.contains(inputErrorCode)){
				return true;
			}
		}
		return false;
	}
	/**
	 * 
	 * @param inputErrorCode
	 * @return
	 */
	public static boolean checkCodes(String inputErrorCode){
		if(errorCodeInit.contains(inputErrorCode)){
			return true;
		}
		return false;
	}
	
}
