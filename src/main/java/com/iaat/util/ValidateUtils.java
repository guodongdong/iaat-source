package com.iaat.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

/**
 * 
 * @name ValidateUtils
 * 
 * @description CLASS_DESCRIPTION 验证的工具类
 * 
 * MORE_INFORMATION
 * 
 * @author zhaozhi
 * 
 * @since 2012-5-17
 *       
 * @version 1.0
 */
public class ValidateUtils {
	
//	public static void main(String[] args) {
//		System.out.println(HashUtil.md5HexString("360360"));
//	}
	
	public static void main(String[] args) {
		System.out.println(isValidateRegex("taobao.net%sfs", URL_SHORT_NAME));
	}
	/**
	 * 3-24位的数字、字母和汉字
	 */
	public static final String NAME_REGIX = "^[\u4e00-\u9fa5a-zA-Z0-9]{4,25}$";
	
	/**
	 * IMEI号
	 */
	public static final String IMEI = "^[a-zA-Z0-9]{10,100}$";
	
	/**
	 * AppId
	 */
	public static final String APPID = "^[a-zA-Z0-9]{15}$";
	
	/**
	 * 数字Ipv4号码
	 */
	public static final String IPV4_NUMBER = "^((\\*|25[0-5]|2[0-4]\\d|[01]?\\d\\d?).){3}(\\*|25[0-5]|2[0-4]\\d|[01]?\\d\\d?)$";
	
	/**
	 * 数字Ipv6号码
	 */
	public static final String IPV6_NUMBER = "^([\\da-fA-F]{1,4}:){7}[\\da-fA-F]{1,4}$";
	
	/**
	 * 银行12位人行大额转帐号
	 */
	public static final String CNAPS = "^[0-9]{12}$";
	/**
	 * 银行账号
	 */
	public static final String BANK_ACCOUNT = "^[0-9]{1}[0-9\\-]{6,30}$";//银行账户为7~30位数字和-组合，必须以数字开头
	/**
	 * 传真的正则
	 */
	public static final String FAX = "^(([0\\+]\\d{2,3}/)?(0\\d{2,3})-)(\\d{7,8})(-(\\d{1,4}))?$";
	/**
	 * 邮政编码的正则
	 */
	public static final String ZIP_CODE = "^\\d{6}(?!d)$";
	/**
	 * 身份证的正则表达式(必须是正确的身份证号码)
	 */
	public static final String IDCard = "^((11|12|13|14|15|21|22|23|31|32|33|34|35|36|37|41|42|43|44|45|46|50|51|52|53|54|61|62|63|64|65|71|81|82|91)\\d{4})((((19|20)(([02468][048])|([13579][26]))0229))|((20[0-9][0-9])|(19[0-9][0-9]))((((0[1-9])|(1[0-2]))((0[1-9])|(1\\d)|(2[0-8])))|((((0[1,3-9])|(1[0-2]))(29|30))|(((0[13578])|(1[02]))31))))((\\d{3}(x|X))|(\\d{4}))$";
	/**
	 * 邮箱的正则表达式
	 */
	final public static String EMAIL = "^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$";
//	final public static String EMAIL = "^[a-zA-Z0-9]*@[a-zA-Z0-9]*\\.(?:(?:cn)|(?:com)|(?:mobi)|(?:co)|(?:net)|(?:so)|(?:tel)|(?:tv)|(?:biz)|(?:cc)|(?:hk)|(?:name)|(?:info)|(?:asia)|(?:me)|(?:org)|(?:com\\.cn)|(?:net\\.cn)|(?:gov\\.cn)|(?:org\\.cn))$";
	/**
	 * 手机的正则表达式(郭豹提供)
	 */
	final public static String MOBLIE = "^([0\\+]\\d{2,3}/)?(13[0-9]|15([0-3]|[5-9])|18([25-9]|0))[0-9]{8}$";
//	final public static String MOBLIE = "^0{0,1}(13[0-9]|15([0-3]|[5-9])|18([25-9]|0))[0-9]{8}$";
//	final public static String MOBLIE = "^0{0,1}(13[0-9]|15[7-9]|153|156|18[7-9])[0-9]{8}$";
	/**
	 * 固定电话的正则表达式
	 */
	final public static String PHONE = "^([0\\+]\\d{2,3}/)?(\\d{3}-|\\d{4}-)?(\\d{8}|\\d{7})$";

	/**
	 * 金额正则表达式 
	 */
	public final static String MONEY = "^\\d{1,}(?:\\.\\d{1,2}|)$";
	
	/**
	 * 无HTTP头
	 */
	public final static String URL_SHORT_NAME = "^[a-zA-Z0-9\\-\\_]+\\..*$";
	
	/**
	 * http全称
	 */
	public final static String URL_FULL_NAME = "^(?:http|https|ftp)\\://[a-zA-Z0-9\\-\\_]+\\..*$";
	
	/**
	 * 正整数
	 */
	public final static String POSITIVEINTEGER = "^[1-9]\\d{0,}$";

	/**
	 * 用户名的最小长度
	 */
	final public static Integer MINNAME = 4;
	/**
	 * 用户名的最大长度
	 */
	final public static Integer MAXNAME = 12;
	/**
	 * 密码的最小长度
	 */
	final public static Integer MINPWD = 6;
	/**
	 * 密码的最大长度
	 */
	final public static Integer MAXPWD = 21;
	
	/**
	 * 判断list是否为空且是否有值
	 * 
	 * @param list
	 * @return
	 */
	public static <E> boolean isNotNull(List<E> list) {
		if (null != list && list.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * isListNull( 判断list是否为空且是否有值 )
	 * 
	 * @param list
	 * 
	 *            boolean
	 */
	public static <T> boolean isNull(List<T> list) {
		if (null == list || list.size() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * isListNull( 判断list是否为空且是否有值 )
	 * 
	 * @param set
	 * 
	 *            boolean
	 */
	public static <E> boolean isNull(Set<E> set) {
		if (null == set || set.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断set是否为空
	 * 
	 * @param set
	 * @return
	 */
	public static <E> boolean isNotNull(Set<E> set) {
		if (null != set && set.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断是否为空 如果是空则返true 如果不是空，则返回false;
	 * 
	 * @param object
	 * @return
	 */
	public static boolean isNull(Object object) {
		if (null != object) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * 
	 * isNull(判断数组为空,空返回true,否则返回false)   
	 * 
	 * @param objects
	 * @return 
	 * 
	 * boolean
	 */
	public static boolean isNull(Object[] objects){
		if (objects == null || objects.length == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * isNull(判断字符串是否为空 如果是空则返回true 如果不为空 则返回false)
	 * 
	 * @param str
	 * @return
	 * 
	 *         boolean
	 */
	public static boolean isNull(String str) {
		if (null == str || str.trim().length() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * isNotNull(对象是否为空，如果为空则返回false 否则返回 true
	 * 
	 * @param object
	 * @return
	 * 
	 *         boolean
	 */
	public static boolean isNotNull(Object object) {
		if (null != object) {
			return true;
		}
		return false;
	}
	
	public static boolean isNotNull(Integer i){
		if (null != i && i > 0) {
			return true;
		}
		return false;
	}

	public static boolean isNotNull(Long i){
		if (null != i && i > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 字符串不为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotNull(String str) {
		if (str == null) {
			return false;
		}
		if ("".equals(str.trim())) {
			return false;
		}
		if (str.trim().length() == 0) {
			return false;
		}
		return true;
	}
	
	public static boolean isNotNull(Calendar calendar){
		if (calendar == null) {
			return false;
		}
		if (calendar.getTime().getTime() <= 0) {
			return false;
		}
		return true;
	}

	/**
	 * 字符串为空
	 * 
	 * @return
	 */
	// public static boolean isNull(String str){
	// if(isNotNull(str) && !str.equals("null") && !str.equals("UNKNOWN")){
	// return true;
	// }else{
	// return false;
	// }
	// }

	/**
	 * 判断字符串长度
	 * 
	 * @return
	 */
	public static boolean judgeLength(String str, Integer min, Integer max) {
		if (!isNotNull(str)) {
			return false;
		} else {
			if (str.length() >= min && str.length() <= max) {
				return true;
			}
			return false;
		}
	}

	/**
	 * 判断两个对象是否相等
	 * 
	 * @param obj1
	 * @param obj2
	 * @return
	 */
	public static boolean equals(Object obj1, Object obj2) {
		if (isNull(obj1)) {
			return false;
		}
		if (isNull(obj2)) {
			return false;
		}
		return obj1.equals(obj2);
	}

	/**
	 * 判断数组是否为空
	 * 
	 * @param objectArray
	 * @return
	 */
	public static boolean isNotNull(Object[] objectArray) {
		if (null != objectArray && objectArray.length > 0) {
			return true;
		}
		return false;
	}

	public static boolean notNullArrayForeach(Object[] objectArray) {
		if (isNotNull(objectArray)) {
			for (Object object : objectArray) {
				if (ValidateUtils.isNull(object)) {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断数字是否为null
	 */
	public static String numberIsNotNull(Number number) {
		if (ValidateUtils.isNotNull(number)) {
			return number + "";
		} else {
			return "未知";
		}

	}

	/**
	 * 
	 * @param number
	 * @return
	 */
	public static String numberIsNotNull(String number) {
		if (ValidateUtils.isNull(number)) {
			return "未知";
		}
		return number;
	}

	/**
	 * 判断email是否符合格式要求
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		return isValidateRegex(email, EMAIL);
	}

	/**
	 * 判断手机号码是否符合格式要求
	 * 
	 * @param moblie
	 * @return
	 */
	public static boolean isMoblie(String moblie) {
		return isValidateRegex(moblie, MOBLIE);
	}
	
	/**
	 * 判断固定电话的格式是否符合格式要求
	 * 
	 * @param phone
	 * @return
	 */
	public static boolean isPhone(String phone) {
		return isValidateRegex(phone, PHONE);
	}
	
	/**
	 * 
	 * isMobileOrPhone(判断号码是电话或手机)
	 * 
	 * @param number
	 * @return 
	 * 
	 * boolean
	 */
	public static boolean isMobileOrPhone(String number){
		if (isValidateRegex(number, MOBLIE) || isValidateRegex(number, PHONE)) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * isMoney(判断是否是金额)
	 * 
	 * @param money
	 * @return
	 * 
	 *         boolean
	 */
	public static boolean isMoney(String money) {
		return isValidateRegex(money, MONEY);
	}

	/**
	 * isUrl(判断是否是URL)
	 * 
	 * @param url
	 * @return
	 * 
	 *         boolean
	 */
	public static boolean isUrl(String url) {
		if (isValidateRegex(url, URL_FULL_NAME)) {
			return true;
		}
		
		if (isValidateRegex(url, URL_SHORT_NAME)) {
			return true;
		}
		
		return false;
	}
	
	
	/**
	 * 
	 * isUrlShortName(判断是否为url的简称--不带http)   
	 * 
	 * @param url
	 * @return 
	 * 
	 * boolean
	 */
	public static boolean isUrlShortName(String url){
		return isValidateRegex(url, URL_SHORT_NAME);
	}
	
	/**
	 * 
	 * isUrlFullName(判断是否为url的全称--带http)   
	 * 
	 * @param url
	 * @return 
	 * 
	 * boolean
	 */
	public static boolean isUrlFullName(String url){
		return isValidateRegex(url, URL_FULL_NAME);
	}
	
	/**
	 * 
	 * isPositiveInteger(是不是正整数)   
	 * 
	 * @param positiveInteger
	 * @return 
	 * 
	 * boolean
	 */
	public static boolean isPositiveInteger(String positiveInteger) {
		return isValidateRegex(positiveInteger, POSITIVEINTEGER);
	}

	/**
	 * 
	 * isValidateRegex(验证字符串是否符合正则)
	 * 
	 * @param src
	 * @param regex
	 * @return
	 * 
	 *         boolean
	 */
	public static boolean isValidateRegex(String src, String regex) {
		if (!isNotNull(src)) {
			return false;
		} else if (src.matches(regex)) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * compareDate(比较两个日期的大小)   
	 * 
	 * @param date
	 * @param compareDate
	 * @return 
	 * 
	 * boolean
	 */
	public static boolean compareDate(String date, String compareDate) {
		return compareDate(date, compareDate, true);
	}
	
	/**
	 * 
	 * compareDate(比较2个日期)   
	 * 
	 * @param date
	 * @param compareDate
	 * @param hasEqual
	 * @return 
	 * 
	 * boolean
	 */
	public static boolean compareDate(Calendar date, Calendar compareDate,Boolean hasEqual) {
		if (isNotNull(date) && isNotNull(compareDate)) {
			if (date.before(compareDate)) {
				return true;
			}else {
				if (hasEqual && date.equals(compareDate)) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	/**
	 * 
	 * compareDateInDayLevel(比较2个日期--在“天”的级别)   
	 * 
	 * @param date
	 * @param compareDate
	 * @param hasEqual
	 * @return 
	 * 
	 * boolean
	 */
	public static boolean compareDateInDayLevel(Calendar date, Calendar compareDate,Boolean hasEqual){
		if (isNotNull(date) && isNotNull(compareDate)) {
			if (date.get(Calendar.YEAR) == compareDate.get(Calendar.YEAR) && date.get(Calendar.MONTH) == compareDate.get(Calendar.MONTH)) {
				if (hasEqual && date.get(Calendar.DATE) == compareDate.get(Calendar.DATE)) {
					return true;
				}
				if (date.get(Calendar.DATE) < compareDate.get(Calendar.DATE)) {
					return true;
				}
			}else if(date.before(compareDate)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * compareDate(比较2个日期)   
	 * 
	 * @param date 
	 * @param compareDate
	 * @param hasEqual 是否包含等于的情况
	 * @return 
	 * 
	 * boolean
	 */
	public static boolean compareDate(String date, String compareDate,Boolean hasEqual) {
		if (isNull(date)) {
			return false;
		} else if (isNull(compareDate)) {
			return false;
		}else {
			if (hasEqual) {
				if (date.compareTo(compareDate) >= 0) {
					return true;
				}
			}else {
				if (date.compareTo(compareDate) > 0) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	/**
	 * 
	 * compareData(比较2个数的大小)   
	 * 
	 * @param data
	 * @param compareData
	 * @return 
	 * 
	 * boolean
	 */
	public static boolean compareData(Long data,Long compareData){
		if (isNotNull(data) && isNotNull(compareData)) {
			return data.compareTo(compareData) >= 0;
		}
		
		if (isNotNull(data) && data >= 0 && isNull(compareData)) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * 
	 * isIDCard(判断身份证号是否符合格式要求)   
	 * 
	 * @param idCard 身份证
	 * @return 
	 * 
	 * boolean
	 */
	public static boolean isIDCard(String idCard){
		return isValidateRegex(idCard, IDCard);
	}
	
	/**
	 * 
	 * isZipCode(判断邮政编码是否符合格式要求)   
	 * 
	 * @param zipCode 邮编
	 * @return 
	 * 
	 * boolean
	 */
	public static boolean isZipCode(String zipCode){
		return isValidateRegex(zipCode, ZIP_CODE);
	}
	
	/**
	 * 
	 * isFax(判断fax是否符合格式要求)   
	 * 
	 * @param fax
	 * @return 
	 * 
	 * boolean
	 */
	public static boolean isFax(String fax){
		return isValidateRegex(fax, FAX);
	}
	public static boolean isAliasName(String aliasName){
		if(isNull(aliasName)||aliasName.length()<4||aliasName.length()>32)
				return false;
		return true;  
	}
	
	/**
	 * 
	 * getFullUrl(获得带http：//的url)   
	 * 
	 * @param url
	 * @return 
	 * 
	 * String
	 */
	public static String getFullUrl(String url){
		if (isNull(url)){
			return null;
		}
		try {
			url = URLDecoder.decode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (isValidateRegex(url, URL_SHORT_NAME)) {
			url = "http://" + url;
		}
		return url;
	}
	
	
	/**
	 * 号码格式是否是以+86开始的
	 * @param code
	 * @return
	 */
	public static boolean existStateCode(String code){
		  if(code!=null){
			  return code.startsWith("+86/");
		  }
		return false;
	}
//	
//	public static void main(String[] args) {
//		System.out.println(new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime()));
//	}
//	
}
