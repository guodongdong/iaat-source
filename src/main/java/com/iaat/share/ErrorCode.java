package com.iaat.share;

/**    
 * @name ErrorCode
 * 
 * @description CLASS_DESCRIPTION
 * 
 * MORE_INFORMATION
 * 
 * @author lijing
 * 
 * @since 2012-3-19
 *       
 * @version 1.0
 */
public class ErrorCode
{
	public final static String VERIFICATION_CODE_ERROR = "验证码输入错误";
	/************************************
	 *
	 * 未知错误10000 
	 *
	 ************************************/
	public final static String UNKONWN_ERROR = "网络异常,数据传输错误(10000)";
	public final static String USER_NAME_WAS_TAKEN = "该用户名已被占用 ！(10001)";
	public final static String EXPORT_CVS_ERROR = "导出cvs文件失败 ！(10002)";
	public final static String OLD_PASSWORD_ERROR="原始密码错误!(10003)";
	public final static String APP_NAME_WAS_TAKEN = "该应用名已被占用 ！(10004)";
	public final static String PASSWORD_ERROR="密码错误!(10005)";
	public final static String ACCOUNT_NOT_PASS_AUDIT = "账户未通过审核(10006)";
	
	public final static String SYSTEM_CONFIG_INIT_ERROR = "配置文件初始化异常(10007)";
	public final static String SYSTEM_CONFIG_NOT_FOUND = "指定配置文件不存在(10008)";
//	public final static String UNKONWN_ERROR = "10000";
	
	/************************************
	 *
	 * api层错误 从1000开始 
	 *
	 ************************************/
	public final static String LOGIN_VCODE_ERROR = "验证码不存在或验证码已过期！";
	public final static String API_ERROR = "网络异常,数据传输错误(1000)";
	public final static String API_PARAMS_ERROR = "网络异常,数据传输错误(1001)";
	public final static String API_NOT_LOGIN = "网络异常,数据传输错误(1002)";
//	public final static String API_ERROR = "1000";
//	public final static String API_PARAMS_ERROR = "1001";
//	public final static String API_NOT_LOGIN = "1002";
	public final static String OUI_IS_NULL = "广告主的oui为空，不能修改信息！";
	public final static String API_NO_REPORT_DATE="无导出的数据";
	/************************************
	 *
	 * Biz层错误 从2000开始 
	 *
	 ************************************/
	public final static String BIZ_ERROR = "网络异常,数据传输错误(2000)";
	public final static String BIZ_PARAMS_ERROR = "网络异常,数据传输错误(2001)";
	public final static String BIZ_REGISTER_ERROR = "网络异常,数据传输错误(2002)";
	public final static String BIZ_LOGIN_ERROR = "网络异常,数据传输错误(2003)";
	public final static String BIZ_EMAIL_ERROR = "Email数据不完整，请完善个人信息。(2004)";
	public final static String BIZ_APPLY_HAS_SUBMIT = "已提交过申请。(2005)";
	public final static String BIZ_NO_REPORT_DATE="无导出的数据";
	public final static String BIZ_INCUFFICIENT_BALANCE = "账户余额小于100元，无法提现！";
	
	
//	public final static String BIZ_ERROR = "2000";
//	public final static String BIZ_PARAMS_ERROR = "2001";
//	public final static String BIZ_REGISTER_ERROR = "2002";
//	public final static String BIZ_LOGIN_ERROR = "2003";
	
	
	/************************************
	 *
	 * Dao层错误 从3000开始 
	 *
	 ************************************/
	/**
	 * dao层未知异常
	 */
	public final static String DAO_ERROR = "网络异常,数据传输错误(3000)";
//	public final static String DAO_ERROR = "3000";
	/**
	 * dao层插入数据异常
	 */
	public final static String DAO_INSERT_ERROR = "网络异常,数据传输错误(3001)";
//	public final static String DAO_INSERT_ERROR = "3001";
	
	
	/************************************
	 *
	 * 其他错误 从4000开始 
	 *
	 ************************************/
	/**
	 * 数据不合法
	 */
	public final static String DATA_NOT_LEGAL = "网络异常,数据传输错误(4000)";
//	public final static String DATA_NOT_LEGAL = "4000";
	/**
	 * 对象未找到
	 */
	public static final String OBJECT_NOT_FOUND_EXCEPTION = "网络异常,数据传输错误(4001)";
//	public static final String OBJECT_NOT_FOUND_EXCEPTION = "4001";
	/**
	 * 用户未登录
	 */
	public static final String USER_NOT_LOGIN = "网络异常,数据传输错误(4002)";
	public final static String USERNAME_NOT_EXIST = "用户名不存在！(40005)";
	public final static String PASSWORD_IS_NOT_MATCHING = "密码错误(40006)";
//	public static final String USER_NOT_LOGIN = "4002";
	/**
	 * 对象数据转移出现异常
	 */
	public static final String CONVERT_MODEL_ERROR = "网络异常,数据传输错误(4003)";
//	public static final String CONVERT_MODEL_ERROR = "4003";
	
	public static final String INITIAL_EXPORT_FIELD_ERROR = "初始化数据失败！ （4004）";
	
	// account级别错误
	public static final String INSUFFICIENT_ACCOUNT_BALANCE = "账户余额不足 （90001）";
	
	public static final String UPPER_ACCOUNT_BALANCE = "账户余额不能超过1000000000!";
	
	/**
	 * 生成报告中，无可生成数据
	 */
	public static final String GENERATE_REPORT_NODATA="无可生成数据";
	
	/**
	 * 生成报告失败
	 */
	public static final String GENERATE_REPORT_ERROR="生成报告失败";
	
	public static final String FILE_NOT_FOUND = "指定文件不存在";
	
	public static final String FILE_IO_ERROR = "处理文件失败";
	
	public static final String ACCOUNT_AUDIT = "信息正在审核中 (2006)";
}
