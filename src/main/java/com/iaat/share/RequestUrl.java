package com.iaat.share;


/**
 * @name RequestUrl
 * 
 * @description CLASS_DESCRIPTION
 * 
 *              MORE_INFORMATION
 * 
 * @author JackGuo
 * 
 * @since 2012-3-18
 * 
 * @version 1.0
 */

// TODO : 统一命名规则
public class RequestUrl
{	

	/**
	 * 获得搜索框的列表
	 * JackGuo 2013-06-24
	 */
	public final static String GET_SEARCHBAR_LIST = "/searchbar/list";
	public final static String GET_SEARCHBAR_IS_FRESH = "/searchbar/isfresh";

	/**    
	 * 访问轨迹弦图   
	 *   
	 * @since Ver 1.1   
	 */   
	public final static String  ANALYSE_AS_URLS_LIST= "/analyse/accesstrack/urls/list";
	
	public final static String  ANALYSE_INPUT_UV_COUNT= "/analyse/accesstrack/input/uv/count";
	
	public final static String  ANALYSE_UV_COUNT= "/analyse/accesstrack/uv/count";
	
	public final static String  ANALYSE_OUTPUT_UV_COUNT= "/analyse/accesstrack/output/uv/count";
	
	public final static String  ANALYSE_INPUT_URLS= "/analyse/accesstrack/input/urls";
	
	public final static String  ANALYSE_OUTPUT_URLS= "/analyse/accesstrack/output/urls";
	
	public final static String  ANALYSE_OUTPUT_TOP_URLS= "/analyse/accesstrack/top/urls";
	
	//http://www.xxxx.com/iaat/browser/api/analyse/accesstrack/uv/count.j
	
	
	
	/**
	 * 期间用户概况
	 */
	public final static String STATS_USER_PROFILE = "/stats/user/profile";
	
	public final static String STATS_PV_CHART = "/stats/pv/chart";

	public final static String STATS_UV_CHART = "/stats/uv/chart";

	public final static String STATS_INCREASE_USER_CHART = "/stats/increase/user/chart";
	
	public final static String STATS_ONCE_USER_CHART = "/stats/once/user/chart";

	public final static String STATS_ONLY_LOGIN_USER_CHART = "/stats/only/login/user/chart";
	
	public final static String STATS_AVG_USER_LOGIN_CHART = "/stats/avg/user/login/chart";
	
	public final static String STATS_AVG_USER_ACCESS_CHART = "/stats/avg/user/access/chart";
	
	/**
	 * 导出
	 */
	public final static String STATS_PV_CHART_EXPORT = "/stats/pv/chart/export";
	
	public final static String STATS_INCREASE_USER_CHART_EXPORT = "/stats/increase/user/chart/export";
	
	public final static String STATS_ONCE_USER_CHART_EXPORT = "/stats/once/user/chart/export";
	
	public final static String STATS_ONLY_LOGIN_USER_CHART_EXPORT = "/stats/only/login/user/chart/export";
	
	public final static String STATS_AVG_USER_LOGIN_CHART_EXPORT = "/stats/avg/user/login/chart/export";
	
	public final static String STATS_AVG_USER_ACCESS_CHART_EXPORT = "/stats/avg/user/access/chart/export";
	
	/**
	 * 平台数据
	 */
	
	
	public final static String STATS_PLAT_LIST_PIE="/stats/plat/list/pipe";
	public final static String STATS_PLAT_LIST_LINE="/stats/plat/list/line";
	public final static String STATS_PLAT_LIST="/stats/plat/list";
	
	/**
	 * life of p
	 */
	
	public final static String STATS_LIFE_OF_P_IMEI_LIST="/stats/imei/list";
	
	/**================流量趋势====================*/
	/**
	 * 统计流量趋势
	 * Lixw 2013-06-24
	 */
	public final static String  STATS_QUANTITY_TREND_CHART = "/stats/quantity/trend/chart";
	public final static String  STATS_QUANTITY_TREND_LIST = "/stats/quantity/trend/list";
	public final static String  STATS_QUANTITY_TREND_LIST_EXPORT = "/stats/quantity/trend/list/export";
	
	
	/**================访客留存====================*/
	public final static String  ANALYSE_RETENTION_CHART = "/analyse/retention/chart";
	
	/**
	 * 用户分布热力图
	 */
	public final static String STATS_HEAT_NATION_MAP_CHART = "/stats/heat/nation/map/chart";
	public final static String STATS_HEAT_REGION_DETAIL_LIST = "/stats/heat/region/list";
	public final static String STATS_HEAT_OPERATOR_RATIO_LIST = "/stats/operator/ratio/list";
	public final static String STATS_HEAT_REGION_DETAIL_LIST_EXPORT = "/stats/heat/region/list/export";
	public final static String STATS_HEAT_OPERATOR_RATIO_LIST_EXPORT = "/stats/operator/ratio/list/export";
	
	public final static String STATS_HEAT_HOURLY_CHART = "/stats/heat/hourly/chart";
	public final static String STATS_HEAT_DAILY_CHART = "/stats/heat/daily/chart";
	
	/**
	 * 交叉对比
	 */
	public final static String STATS_CROSS_CONTRAST_CASE_LIST = "/stats/cross/contrast/case/list";
	
	/**
	 * 多维度分析
	 */
	public final static String ANALYSE_MULTIDIMENSIONAL_CHART = "/analyse/multidimensional/chart";
	public final static String ANALYSE_MULTIDIMENSIONAL_LIST = "/analyse/multidimensional/list";
	
	/**
	 * 用户访问关联 
	 */
	public final static String ANALYSE_RELATIONSHIP_CHART = "/analyse/relationship/chart";
	
	/**
	 * 访客回访率 
	 */
	public final static String ANALYSE_BOUNCE_RATE = "/analyse/bounce/rate";
	
}
