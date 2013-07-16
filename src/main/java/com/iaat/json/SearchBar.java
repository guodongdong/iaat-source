package com.iaat.json;

import java.util.Calendar;

import com.iaat.model.LogSourceType;
import com.iaat.share.Params;
import com.iaat.util.ValidateUtils;
import com.nokia.ads.common.util.Log;
import com.nokia.ads.platform.backend.core.UncheckedException;
import com.nokia.ads.platform.backend.core.webapi.ApiRequest;

/**
 * 
 * @name SearchBar
 * 
 * @description CLASS_DESCRIPTION
 * 
 * MORE_INFORMATION
 * 
 * @author zhaozhi
 * 
 * @since 2013-6-24
 *       
 * @version 1.0
 */
public class SearchBar {
	private final static Log log = Log.getLogger(SearchBar.class);
	
	public static final int ALL_DEFAULT = -1;
	public static final String DAILY = "daily";
	public static final String WEEKLY = "weekly";
	public static final String MONTHLY = "monthly";
	public static final String XPRESS = "s40_xpress";
	
	public static final String BY_PLATFORM = "platform";
	public static final String BY_TERMINALTYPE = "terminal";
	public static final String BY_OPERATOR = "operator";
	public static final String BY_AP = "ap";
	
	
//	startDateTime 日期 毫秒 
//	endDateTime 日期 毫秒 
//	startTime 时间 数字
//	endTime 时间 数字
//	channel 渠道 字符串
//	platform 平台 字符串
//	AP 接入点 字符串
//	logSource 日志来源 字符串
//	local1 地域 字符串
//	local2 地域 字符串
//	operator 运营商 字符串
//	terminalType 终端型号 字符串

	private Calendar startDateTime;
	private int startYear;
	private int startMonth;
	private int startDay;
	private Calendar endDateTime;
	private int endYear;
	private int endMonth;
	private int endDay;
	private String province;
	private Integer provinceFk;
	private String city;
	private Integer cityFk;
	private String logSource;
	
//	private String channel;
//	private String platform;
//	private String AP;
//	private String operator;
//	private String terminalType;

	/**
	 * 外键
	 */
	private int channelKey;
	private int platformKey;
	private int APKey;
	private int operatorKey;
	private int terminalTypeKey;
	private int startTime;
	private int endTime;
	
	private boolean channelKeyVerify = true;
	private boolean platformKeyVerify = true;
	private boolean APKeyVerify = true;
	private boolean operatorKeyVerify = true;
	private boolean terminalTypeKeyVerify = true;
	private boolean startTimeVerify = true;
	private boolean endTimeVerify = true;
	
	
	/**
	 * ===================== 私有的参数  =======================
	 */
	//daily,weekly,monthly
	private String type;
	private int minCount;
	private int increment;
	private int maxCount;
	private String category;
	
	/**
	 * 按哪些类型传递参数  ：platform、terminalType、operator、ap
	 */
	private String byType;
	private Integer[] terminalFk; 
	
	
	public boolean isChannelKeyVerify() {
		return channelKeyVerify;
	}
	public void setChannelKeyVerify(boolean channelKeyVerify) {
		this.channelKeyVerify = channelKeyVerify;
	}
	public boolean isPlatformKeyVerify() {
		return platformKeyVerify;
	}
	public void setPlatformKeyVerify(boolean platformKeyVerify) {
		this.platformKeyVerify = platformKeyVerify;
	}
	public boolean isAPKeyVerify() {
		return APKeyVerify;
	}
	public void setAPKeyVerify(boolean aPKeyVerify) {
		APKeyVerify = aPKeyVerify;
	}
	public boolean isOperatorKeyVerify() {
		return operatorKeyVerify;
	}
	public void setOperatorKeyVerify(boolean operatorKeyVerify) {
		this.operatorKeyVerify = operatorKeyVerify;
	}
	public boolean isTerminalTypeKeyVerify() {
		return terminalTypeKeyVerify;
	}
	public void setTerminalTypeKeyVerify(boolean terminalTypeKeyVerify) {
		this.terminalTypeKeyVerify = terminalTypeKeyVerify;
	}
	public boolean isStartTimeVerify() {
		return startTimeVerify;
	}
	public void setStartTimeVerify(boolean startTimeVerify) {
		this.startTimeVerify = startTimeVerify;
	}
	public boolean isEndTimeVerify() {
		return endTimeVerify;
	}
	public void setEndTimeVerify(boolean endTimeVerify) {
		this.endTimeVerify = endTimeVerify;
	}
	public int getChannelKey() {
		return channelKey;
	}
	public void setChannelKey(int channelKey) {
		this.channelKey = channelKey;
	}
	public int getPlatformKey() {
		return platformKey;
	}
	public void setPlatformKey(int platformKey) {
		this.platformKey = platformKey;
	}
	public int getAPKey() {
		return APKey;
	}
	public void setAPKey(int aPKey) {
		APKey = aPKey;
	}
	public int getOperatorKey() {
		return operatorKey;
	}
	public void setOperatorKey(int operatorKey) {
		this.operatorKey = operatorKey;
	}
	public int getTerminalTypeKey() {
		return terminalTypeKey;
	}
	public void setTerminalTypeKey(int terminalTypeKey) {
		this.terminalTypeKey = terminalTypeKey;
	}
	public int getMaxCount() {
		return maxCount;
	}
	public void setMaxCount(int maxCount) {
		this.maxCount = maxCount;
	}
	public int getMinCount() {
		return minCount;
	}
	public void setMinCount(int minCount) {
		this.minCount = minCount;
	}
	public int getIncrement() {
		return increment;
	}
	public void setIncrement(int increment) {
		this.increment = increment;
	}
	public int getStartYear() {
		return startYear;
	}
	public void setStartYear(int startYear) {
		this.startYear = startYear;
	}
	public int getStartMonth() {
		return startMonth;
	}
	public void setStartMonth(int startMonth) {
		this.startMonth = startMonth;
	}
	public int getStartDay() {
		return startDay;
	}
	public void setStartDay(int startDay) {
		this.startDay = startDay;
	}
	public int getEndYear() {
		return endYear;
	}
	public void setEndYear(int endYear) {
		this.endYear = endYear;
	}
	public int getEndMonth() {
		return endMonth;
	}
	public void setEndMonth(int endMonth) {
		this.endMonth = endMonth;
	}
	public int getEndDay() {
		return endDay;
	}
	public void setEndDay(int endDay) {
		this.endDay = endDay;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Calendar getStartDateTime() {
		return startDateTime;
	}
	public void setStartDateTime(Calendar startDateTime) {
		startDateTime.set(Calendar.HOUR_OF_DAY, 0);
		this.startDateTime = startDateTime;
		this.startYear = startDateTime.get(Calendar.YEAR);
		this.startMonth = startDateTime.get(Calendar.MONTH)+1;
		this.startDay = startDateTime.get(Calendar.DAY_OF_MONTH);
	}
	public Calendar getEndDateTime() {
		return endDateTime;
	}
	public void setEndDateTime(Calendar endDateTime) {
		endDateTime.set(Calendar.HOUR_OF_DAY, 23);
		this.endDateTime = endDateTime;
		this.endYear = endDateTime.get(Calendar.YEAR);
		this.endMonth = endDateTime.get(Calendar.MONTH)+1;
		this.endDay = endDateTime.get(Calendar.DAY_OF_MONTH);
	}
	public int getStartTime() {
		return startTime;
	}
	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}
	public int getEndTime() {
		return endTime;
	}
	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}
	public String getLogSource() {
		return logSource;
	}
	public void setLogSource(String logSource) {
		this.logSource = logSource;
	}
	public String getProvince() {
		return province;
	}
	public Integer getProvinceFk() {
		return provinceFk;
	}
	public void setProvinceFk(Integer provinceFk) {
		this.provinceFk = provinceFk;
	}
	public Integer getCityFk() {
		return cityFk;
	}
	public void setCityFk(Integer cityFk) {
		this.cityFk = cityFk;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	
	public String getByType() {
		return byType;
	}
	public void setByType(String byType) {
		this.byType = byType;
	}
	
	public Integer[] getTerminalFk() {
		return terminalFk;
	}
	public void setTerminalFk(Integer[] terminalFk) {
		this.terminalFk = terminalFk;
	}
	//	public String getChannel() {
//		return channel;
//	}
//	public void setChannel(String channel) {
//		this.channel = channel;
//	}
//	public String getPlatform() {
//		return platform;
//	}
//	public void setPlatform(String platform) {
//		this.platform = platform;
//	}
//	public String getAP() {
//		return AP;
//	}
//	public void setAP(String aP) {
//		AP = aP;
//	}
//	public String getOperator() {
//		return operator;
//	}
//	public void setOperator(String operator) {
//		this.operator = operator;
//	}
//	public String getTerminalType() {
//		return terminalType;
//	}
//	public void setTerminalType(String terminalType) {
//		this.terminalType = terminalType;
//	}
	public boolean getDateEqual(){
		if (this.startYear == this.endYear && this.startMonth == this.endMonth && this.startDay == this.endDay) {
			return true;
		}
		return false;
	}
	
	public static SearchBar getSearchBar(ApiRequest request){
		return getSearchBar(request, false);
	}
	
	public static SearchBar getStatsTypeBar(ApiRequest request){
		SearchBar bar = getBaseBar(request);
		String type = request.getParam(Params.STATS_TYPE, String.class);
		bar.setType(type);
		return bar;
	}	
	
	/**
	 * 按哪些类型传递参数  ：platform、terminalType、operator、ap
	 * @param request
	 * @return
	 */
	public static SearchBar getByTypeBar(ApiRequest request){
		SearchBar bar = getStatsTypeBar(request);
		bar.setByType(request.getParam(Params.BY_TYPE, String.class));
		bar.setTerminalFk(request.getParam(Params.TERMINAL_FK,Integer[].class));
		return bar;
	}
	
	public static SearchBar getSearchBar(ApiRequest request,boolean isColumnChart){
		SearchBar bar = getBaseBar(request);
		
		if (isColumnChart) {
			Integer minCount = request.getParam(Params.MIN_COUNT, Integer.class);
			Integer increment = request.getParam(Params.INCREMENT, Integer.class);
			bar.setMinCount(minCount);
			bar.setIncrement(increment);
			if (minCount != null && increment != null) {
				if (minCount < 0 || increment <= 0) {
					log.error("[ SeachBar.getBaseBar ] , params errors","params errors");
					throw new UncheckedException("参数错误，起始次数应该大于等于0，分组应该大于0！", "参数错误，起始次数应该大于等于0，分组应该大于0！");
				}
				bar.setMaxCount(minCount+increment*9);
			}
		}
		return bar;
	}
	public boolean isOneDay(){
		return com.iaat.util.DateUtil.dayDiff(getStartDateTime(),getEndDateTime())==0;
	}
	
	public static SearchBar getBaseBar(ApiRequest request) {
		Calendar startDateTime = request.getParam(Params.START_DATETIME, Calendar.class);
		Calendar endDateTime = request.getParam(Params.END_DATETIME, Calendar.class);
		Integer startTime = request.getParam(Params.START_TIME, Integer.class);
		
		Integer endTime = request.getParam(Params.END_TIME, Integer.class); 
		LatitudeBean latBean = LatitudeBean.getInstance();
		String province = request.getParam(Params.PROVINCE, String.class);
		String city = request.getParam(Params.CITY, String.class);
		
		Integer channelKey = request.getParam(Params.CHANNEL, Integer.class);
		Integer platformKey = request.getParam(Params.PLATFORM, Integer.class);
		String platform = null;
		if(platformKey!=null && platformKey != -1){
			platform = latBean.getPlatFormBean(platformKey).getPlatform();
		}
		Integer APKey = request.getParam(Params.AP, Integer.class);
		Integer operatorKey = request.getParam(Params.OPERATOR, Integer.class);
		Integer terminalTypeKey = request.getParam(Params.TERMINAL_TYPE,Integer.class);
		
//		Integer logKey = request.getParam(Params.LOG_SOURCE, Integer.class);
//		String channel = latBean.getChannelBean(channelKey).getChannel();
//		String AP  = latBean.getApBeanByKey(APKey).getAp();
//		String operator = latBean.getOperatoryBean(operatorKey).getOperator();
//		String terminalType = latBean.getTerminalBean(terminalTypeKey).getTerminal();

		if (startTime != null && endTime != null) {
			endTime = endTime - 1;
			if (startTime > endTime) {
				log.error("[ SeachBar.getBaseBar ] , startTime equals endTime","startTime equals endTime");
				throw new UncheckedException("开始时间点和结束时间点相同，没有数据！", "开始时间点和结束时间点相同，没有数据！");
			}
		}
		
		SearchBar bar = new SearchBar();
		if (ValidateUtils.isNotNull(platform)) {
			String logSource  = "";
			if (XPRESS.toLowerCase().equals(platform.toLowerCase())) {
				logSource  = LogSourceType.findByValue(5).name();
			}else {
				logSource  = LogSourceType.findByValue(4).name();
			}
			if(ValidateUtils.isNotNull(logSource)){
				bar.setLogSource(logSource.toLowerCase());	
			}
		}else {
			//TODO 临时方案
			bar.setLogSource(LogSourceType.findByValue(4).name().toLowerCase());
		}
		if(startDateTime!=null){
			bar.setStartDateTime(startDateTime);	
		}
		if(endDateTime!=null){
			bar.setEndDateTime(endDateTime);
		}
		
		if(startTime!=null){
			bar.setStartTime(startTime);	
		}
		if(endTime!=null){
			bar.setEndTime(endTime);
		}
		LatitudeBean latiBean = LatitudeBean.getInstance();
		if(ValidateUtils.isNotNull(province)){
			bar.setProvinceFk(latiBean.getRegionBeanByProvinKey(province).getRegionFk());
			bar.setProvince(province.toLowerCase());	
		}
		if(ValidateUtils.isNotNull(city)){
			bar.setCityFk((latiBean.getRegionBeanByCityKey(city).getRegionFk()));
			bar.setCity(city);	
		}
//		bar.setChannel(channel);
//		bar.setPlatform(platform);
//		bar.setAP(AP);
//		bar.setOperator(operator);
//		bar.setTerminalType(terminalType);
		if(channelKey!=null){
			bar.setChannelKey(channelKey);
		}
		if(platformKey!=null){
			bar.setPlatformKey(platformKey);
		}
		if(APKey!=null){
			bar.setAPKey(APKey);
		}
		if(operatorKey!=null){
			bar.setOperatorKey(operatorKey);
		}
		if(terminalTypeKey!=null){
			bar.setTerminalTypeKey(terminalTypeKey);
		}
		if (channelKey !=null&&channelKey == ALL_DEFAULT) {
			bar.setChannelKeyVerify(false);
		}
		if (platformKey!=null&&platformKey == ALL_DEFAULT) {
			bar.setPlatformKeyVerify(false);
		}
		if (APKey!=null&&APKey == ALL_DEFAULT) {
			bar.setAPKeyVerify(false);
		}
		if (operatorKey!=null&&operatorKey == ALL_DEFAULT) {
			bar.setOperatorKeyVerify(false);
		}
		if (terminalTypeKey!=null&&terminalTypeKey == ALL_DEFAULT) {
			bar.setTerminalTypeKeyVerify(false);
		}
		if (startTime != null && startTime == ALL_DEFAULT) {
			bar.setStartTimeVerify(false);
		}
		if (endTime != null && endTime == ALL_DEFAULT) {
			bar.setEndTimeVerify(false);
		}
		return bar;
	}
	
}
