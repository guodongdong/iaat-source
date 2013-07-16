package com.iaat.share;


/**    
 * @name Params
 * 
 * @description CLASS_DESCRIPTION
 * 
 * MORE_INFORMATION
 * 
 * @author lijing
 * 
 * @since 2012-3-18
 *       
 * @version 1.0
 */
public class Params
{	
	public static final String START_DATETIME = "startDateTime";
	public static final String END_DATETIME = "endDateTime";
	public static final String START_TIME = "startTime";
	public static final String END_TIME = "endTime";
	public static final String CHANNEL = "channel";
	public static final String PLATFORM = "platform";
	public static final String AP = "ap";
	public static final String LOG_SOURCE = "logSource";
	public static final String PROVINCE = "province";
	public static final String CITY = "city";
	public static final String OPERATOR = "operator";
	public static final String TERMINAL_TYPE = "terminalType";	
	public static final String CATEGORY = "category";	
	public static final String D_PLAT_FORM="dpf";
	public static final String D_AP="dap";
	public static final String D_OPERATOR="doper";
	public static final String D_DATE="ddate";
	public static final String D_IMEI="dimei";
	public static final String D_REGION="dreg";
	public static final String D_CHANNEL="dcn";
	public static final String D_CATEGORY="dca";
	public static final String D_TERMINAL="dterm";
	public static final String REGION_NAME="Beijing";
	public static final String D_REGOIN_ROOT="ROOT";
	
	public final static String DISPLAY_LENGTH = "iDisplayLength";
	public final static String DISPLAY_START = "iDisplayStart";
	/**
	 * 日周月,daily,weekly,monthly
	 */
	public static final String STATS_TYPE = "statsType";
	public static final String MIN_COUNT = "minCount";
	public static final String INCREMENT = "increment";
	
	
	/**
	 * 按哪些类型传递参数  ：platform、terminalType、operator、ap
	 */
	public static final String BY_TYPE = "byType"; 
	public static final String TERMINAL_FK = "terminal_fk";
	
	/***************************************导出table的编号******************************************/
	
	//流量趋势	table index
	public final static String QUANTITYTREND_EXPORT_LIST_INDEX1 = "1";
	public final static String QUANTITYTREND_EXPORT_LIST_INDEX2 = "2";
	
	//用户热力分布图
	public final static String HEAT_REGION_EXPORT_LIST = "12";
	public final static String HEAT_OPERATOR_RATIO_EXPORT_LIST = "13";
	
	
	/**    
	 * 焦点URL
	 *    
	 */   
	public final static String FOCUS_URLS="focusUrl";
	
	/**
	 * 交叉对比
	 */
	public final static String SEARCH_CONSTRAST="searchBeans";
	public final static String RESULT_CONSTRAST="resultBeans";
}
