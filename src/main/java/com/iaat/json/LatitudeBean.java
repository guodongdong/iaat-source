package com.iaat.json;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.iaat.dao.factory.DaoFactory;
import com.iaat.dao.latitude.ApDao;
import com.iaat.dao.latitude.CategoryDao;
import com.iaat.dao.latitude.ChannelDao;
import com.iaat.dao.latitude.LogSourceDao;
import com.iaat.dao.latitude.OperatorDao;
import com.iaat.dao.latitude.PlatformDao;
import com.iaat.dao.latitude.RegionDao;
import com.iaat.dao.latitude.TerminalDao;
import com.iaat.model.ApBean;
import com.iaat.model.CategoryBean;
import com.iaat.model.ChannelBean;
import com.iaat.model.LogSourceBean;
import com.iaat.model.OperatorBean;
import com.iaat.model.PlatformBean;
import com.iaat.model.RegionBean;
import com.iaat.model.TerminalBean;
import com.nokia.ads.share.type.LatitudeType;

/**    
 * @name LatitudeBean
 * 
 * @description CLASS_DESCRIPTION
 * 
 * MORE_INFORMATION
 * 
 * @author zhaozhi
 * 
 * @since 2013-6-25
 *       
 * @version 1.0
 */
/**    
 * @name LatitudeBean
 * 
 * @description CLASS_DESCRIPTION
 * 
 * MORE_INFORMATION
 * 
 * @author JackGuo
 * 
 * @since 2013-6-25
 *       
 * @version 1.0
 */
/**    
 * @name LatitudeBean
 * 
 * @description CLASS_DESCRIPTION
 * 
 * MORE_INFORMATION
 * 
 * @author JackGuo
 * 
 * @since 2013-6-26
 *       
 * @version 1.0
 */
public class LatitudeBean {
	
	private static final String REGION_NAME = "Beijing";

	private static ApDao apDao = DaoFactory.getInstance(ApDao.class);
	
	private static CategoryDao categoryDao = DaoFactory.getInstance(CategoryDao.class);

	private static ChannelDao channelDao = DaoFactory.getInstance(ChannelDao.class);
	
	private static LogSourceDao logSourceDao = DaoFactory.getInstance(LogSourceDao.class);
	
	private static OperatorDao operatorDao = DaoFactory.getInstance(OperatorDao.class);
	
	private static PlatformDao platformDao = DaoFactory.getInstance(PlatformDao.class);
	
	private static TerminalDao terminalDao = DaoFactory.getInstance(TerminalDao.class);
	
	private static RegionDao regionDao = DaoFactory.getInstance(RegionDao.class);
	
	private Map<String,List<ApBean>> apBeans;
	
	private Map<String,List<CategoryBean>> categoryBeans;
	
	private Map<String,List<ChannelBean>> channelBeans;
		
	private Map<String,List<LogSourceBean>> logSourceBeans;
	
	private Map<String,List<OperatorBean>> operatorBeans;
	
	private Map<String,List<PlatformBean>> platformBeans;
	
	private Map<String,List<TerminalBean>> terminalBeans;
	
	private Map<String,List<LatitudeDataBean>>  results = null; 
	
	private Map<String,List<RegionLinkBean>> regionLinkBeans;
	
	
	
	private static LatitudeBean latitudeBean = null;
	
	
	public Map<String, List<TerminalBean>> getTerminalBeans() {
		return terminalBeans;
	}
	public void setTerminalBeans(Map<String, List<TerminalBean>> terminalBeans) {
		this.terminalBeans = terminalBeans;
	}
	
	public Map<String, List<LatitudeDataBean>> getResults() {
		reLoadData();
		return results;
	}
	public void setResults(Map<String, List<LatitudeDataBean>> results) {
		this.results = results;
	}

	private  boolean isReload=true;
	
	private static byte[] lock = new byte[0];
	
    public static LatitudeBean getInstance() {
    	if (latitudeBean == null) {
        	synchronized (lock) {
        		latitudeBean = new LatitudeBean();	
			}
        }  
       return latitudeBean;
    }
    private LatitudeBean(){
    	apBeans = new HashMap<String,List<ApBean>>();
    	categoryBeans = new HashMap<String, List<CategoryBean>>();
    	channelBeans = new HashMap<String,List<ChannelBean>>();
    	logSourceBeans = new HashMap<String,List<LogSourceBean>>();
    	operatorBeans = new HashMap<String,List<OperatorBean>>();
    	platformBeans = new HashMap<String,List<PlatformBean>>();
    	terminalBeans = new HashMap<String,List<TerminalBean>>();
    	regionLinkBeans = new HashMap<String,List<RegionLinkBean>>();
    	
    	
    }

	private void loadData() {
		results = new HashMap<String, List<LatitudeDataBean>>();

		// 接入点
		List<ApBean> apLists = apDao.getAllApBeans(REGION_NAME);
		
		apBeans.put(LatitudeType.AP.name(), apLists);
		
		// 分类
		List<CategoryBean> cateLists = categoryDao.getAllCategoryBeans(REGION_NAME);
		
		categoryBeans.put(LatitudeType.CATEGORY.toString(), cateLists);
		
		results.put(LatitudeType.CATEGORY.name(), convertToKeyValue(cateLists));
		
		

		// 渠道
		List<ChannelBean> channels = channelDao.getAllChannelBeans(REGION_NAME);

		channelBeans.put(LatitudeType.CHANNEL.name(), channels);

		results.put(LatitudeType.CHANNEL.name(), convertToKeyValue(channels));

		// 日志来源
		List<LogSourceBean> logSources = logSourceDao.getAllLogSourceBeans();

		logSourceBeans.put(LatitudeType.LOGS.name(), logSources);

		results.put(LatitudeType.LOGS.name(), convertToKeyValue(logSources));

		// 运营商
		List<OperatorBean> operators = operatorDao
				.getAllOperatorBeans(REGION_NAME);

		operatorBeans.put(LatitudeType.OPERATOR.name(), operators);


		// 平台
		List<PlatformBean> platforms = platformDao
				.getAllPlatformBeans(REGION_NAME);

		platformBeans.put(LatitudeType.PLATFORM.name(), platforms);


		// 终端
		List<TerminalBean> termins = terminalDao
				.getAllTerminalBeans(REGION_NAME);

		terminalBeans.put(LatitudeType.TERMINAL.name(), termins);


		// 平台终端数据
		List<LatitudeDataBean> terminLinks = convertToTerminLink(
				termins, platforms);

		results.put(LatitudeType.PLATFORM.name()+LatitudeType.TERMINAL.name(), terminLinks);
		
		// 地域
		List<RegionBean> allRegions = regionDao.getAllRegionBeans(REGION_NAME);
		
		List<RegionBean> proRegions = regionDao.getAllProvinBeans(REGION_NAME);
		
		List<RegionLinkBean> regLinkBeans = convertToRegLinkBeans(proRegions,allRegions);
		
		regionLinkBeans.put(LatitudeType.REGION.name(), regLinkBeans);
		
		//运营商接入点
		List<ApBean> operatorAps = apDao.getOperatorApBeans(REGION_NAME);
		
		List<LatitudeDataBean> operatorBeans = convertToOperatorLink(operatorAps, operators);
		
		results.put(LatitudeType.OPERATOR.name()+LatitudeType.AP.name(), operatorBeans);
		
		
		
}
	
	private List<RegionLinkBean> convertToRegLinkBeans(List<RegionBean> provinces,List<RegionBean> allRegions){
		List<RegionLinkBean> regionLinks = new ArrayList<RegionLinkBean>();
		for (RegionBean regionBean : provinces) {
			RegionLinkBean regionLinkBean = new RegionLinkBean();
			regionLinkBean.setProvince(regionBean);	
			List<RegionBean> regionBeans = new ArrayList<RegionBean>();
				for (RegionBean cityBean: allRegions) {
					if(!cityBean.getPCode().equals("ROOT")&&cityBean.getPRegion().equals(regionBean.getRegion())){
						regionBeans.add(cityBean);
					}
				}
				regionLinkBean.setCities(regionBeans);
			regionLinks.add(regionLinkBean);
		}
		return regionLinks;
	}
	
	/**    
	 * getRegionBeanByProvinKey(get the regionBean by key)   
	 * 
	 * @param key
	 * @return 
	 * 
	 * RegionBean
	 */
	public RegionBean getRegionBeanByProvinKey(String code){
		for (RegionLinkBean regionLinkBean :regionLinkBeans.get(LatitudeType.REGION.name())) {
			RegionBean province = regionLinkBean.getProvince();
			if(province.getCode().equalsIgnoreCase(code)){
				return province;
			}
		}
		return null;
	}
	
	/**    
	 * getRegionBeanByCityKey(get the regionBean by key)   
	 * 
	 * @param key
	 * @return 
	 * 
	 * RegionBean
	 */
	public RegionBean getRegionBeanByCityKey(String code){
		for (RegionLinkBean regionLinkBean :regionLinkBeans.get(LatitudeType.REGION.name())) {
			List<RegionBean> cities = regionLinkBean.getCities();
			for (RegionBean cityBean : cities) {
				if(cityBean.getCode().equalsIgnoreCase(code)){
					return cityBean;
				}		
			}
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private List<LatitudeDataBean> convertToKeyValue(List objects){
		List<LatitudeDataBean> latitudes = new ArrayList<LatitudeDataBean>();
		if(objects==null){
			return latitudes;
		}
		for (Object obj : objects) {
			LatitudeDataBean latBean = null;
			if(obj instanceof ApBean){
				ApBean apBean = (ApBean)obj;
				latBean = new LatitudeDataBean();
				latBean.setName(apBean.getAp());
				latBean.setValue(getStringValue(apBean.getApFk()));
				latBean.setSequence(apBean.getSequence());
				latitudes.add(latBean);
			}
			if(obj instanceof ChannelBean){
				ChannelBean channelBean = (ChannelBean)obj;
				latBean = new LatitudeDataBean();
				latBean.setName(channelBean.getChannel());
				latBean.setValue(getStringValue(channelBean.getChannelFk()));
				latBean.setSequence(channelBean.getSequence());
				latitudes.add(latBean);
			}
			if(obj instanceof LogSourceBean){
				LogSourceBean logSourceBean = (LogSourceBean)obj;
				latBean = new LatitudeDataBean();
				latBean.setName(logSourceBean.getLogName());
				latBean.setValue(getStringValue(logSourceBean.getLogId().intValue()));
				latBean.setSequence(logSourceBean.getLogId().intValue());
				latitudes.add(latBean);
			}
			if(obj instanceof OperatorBean){
				OperatorBean operatorBean = (OperatorBean)obj;
				latBean = new LatitudeDataBean();
				latBean.setName(operatorBean.getOperator());
				latBean.setValue(getStringValue(operatorBean.getOperatorFk()));
				latBean.setSequence(operatorBean.getSequence());
				latitudes.add(latBean);
			}
			if(obj instanceof PlatformBean){
				PlatformBean platformBean = (PlatformBean)obj;
				latBean = new LatitudeDataBean();
				latBean.setName(platformBean.getPlatform());
				latBean.setValue(getStringValue(platformBean.getPlatformFk()));
				latBean.setSequence(platformBean.getSequence());
				latitudes.add(latBean);
			}
			if(obj instanceof TerminalBean){
				TerminalBean terminalBean = (TerminalBean)obj;
				latBean = new LatitudeDataBean();
				latBean.setName(terminalBean.getTerminal());
				latBean.setValue(getStringValue(terminalBean.getTerminalFk()));
				latBean.setSequence(terminalBean.getTerminalFk());
				latitudes.add(latBean);
			}
			if(obj instanceof CategoryBean){
				CategoryBean categoryBean = (CategoryBean)obj;
				latBean = new LatitudeDataBean();
				latBean.setName(categoryBean.getCategoryValue());
				latBean.setValue(getStringValue(categoryBean.getCategoryFk()));
				latBean.setSequence(categoryBean.getSequence());
				latitudes.add(latBean);
			}
			
		}
		if(latitudes==null||latitudes.size()<1){
			return latitudes;
		}
		LatitudeDataBean[] latituArrays = latitudes.toArray(new LatitudeDataBean[]{});
		Arrays.sort(latituArrays, new Comparator() {
			@Override
			public int compare(Object fromObj, Object toObj) {
				LatitudeDataBean logFrom = (LatitudeDataBean)fromObj;
				LatitudeDataBean toFrom = (LatitudeDataBean)toObj;
				return logFrom.getSequence().compareTo(toFrom.getSequence());
			}
		});
		return Arrays.asList(latituArrays);
	}
	private String getStringValue(Integer intValue) {
		return intValue+"";
	}
	@SuppressWarnings("unchecked")
	private List<LatitudeDataBean> convertToOperatorLink(List<ApBean> apBeans,List<OperatorBean> operatorBeans){
		Map<String,List<ApBean>> aps = new HashMap<String,List<ApBean>>();
		String oper_fk =null;
		for (ApBean apBean :apBeans) {
			 oper_fk = getStringValue(apBean.getOperatorFk());
			 List<ApBean> apListBeans = aps.get(oper_fk);
			 if(apListBeans ==null){
				 apListBeans  = new ArrayList<ApBean>();
				 apListBeans.add(apBean);
				 aps.put(oper_fk, apListBeans);
			 }else{
				 apListBeans.add(apBean);
			 }
		}
		List<LatitudeDataBean> operatorLinkApBeans = new ArrayList<LatitudeDataBean>();
		for (OperatorBean operatorBean : operatorBeans) {
				LatitudeDataBean latitudeDataBean = new LatitudeDataBean();
				latitudeDataBean.setName(operatorBean.getOperator());
				latitudeDataBean.setValue(getStringValue(operatorBean.getOperatorFk()));
				latitudeDataBean.setSequence(operatorBean.getSequence());
				List<LatitudeDataBean> childBeans = new ArrayList<LatitudeDataBean>();
				Integer operatorFk = operatorBean.getOperatorFk();
				List<ApBean> apBeanChilds = aps.get(getStringValue(operatorFk));
				if(apBeanChilds!=null){
					for (ApBean apBean : apBeanChilds) {
						LatitudeDataBean childDataBean = null; 
						if(apBean.getOperatorFk().equals(operatorBean.getOperatorFk())){
							childDataBean = new LatitudeDataBean();
							childDataBean.setName(apBean.getAp());
							childDataBean.setValue(getStringValue(apBean.getApFk()));
							childDataBean.setSequence(apBean.getSequence());
							childBeans.add(childDataBean);
						}
					}	
				}
				
				//全部的时候加载所有的
				if(operatorBean.getOperatorFk().intValue()==-1){
					List<ApBean> apBeanLists = apDao.getDistinctApBeans(REGION_NAME);
					for (ApBean apBean:apBeanLists) {
							LatitudeDataBean childDataBean = null; 
							childDataBean = new LatitudeDataBean();
							childDataBean.setName(apBean.getAp());
							childDataBean.setValue(apBean.getAp());
							childDataBean.setSequence(apBean.getSequence());
							childBeans.add(childDataBean);
					}
				}
				LatitudeDataBean[] latituArrays = childBeans.toArray(new LatitudeDataBean[]{});
				Arrays.sort(latituArrays, new Comparator() {
					@Override
					public int compare(Object fromObj, Object toObj) {
						LatitudeDataBean logFrom = (LatitudeDataBean)fromObj;
						LatitudeDataBean toFrom = (LatitudeDataBean)toObj;
						return logFrom.getSequence().compareTo(toFrom.getSequence());
					}
				});
				latitudeDataBean.setChildren(Arrays.asList(latituArrays));
				operatorLinkApBeans.add(latitudeDataBean);
				
		}
		LatitudeDataBean[] latituArrays = operatorLinkApBeans.toArray(new LatitudeDataBean[]{});
		Arrays.sort(latituArrays, new Comparator() {
			@Override
			public int compare(Object fromObj, Object toObj) {
				LatitudeDataBean logFrom = (LatitudeDataBean)fromObj;
				LatitudeDataBean toFrom = (LatitudeDataBean)toObj;
				return logFrom.getSequence().compareTo(toFrom.getSequence());
			}
		});
		return Arrays.asList(latituArrays);
	}
	@SuppressWarnings("unchecked")
	private List<LatitudeDataBean> convertToTerminLink(List<TerminalBean> terminals,List<PlatformBean> platForms){
		List<LatitudeDataBean> latidueLists = new ArrayList<LatitudeDataBean>();
		for (PlatformBean platform : platForms) {
			LatitudeDataBean latitudeDataBean = new LatitudeDataBean();
			latitudeDataBean.setName(platform.getPlatform());
			latitudeDataBean.setValue(getStringValue(platform.getPlatformFk()));
			latitudeDataBean.setSequence(platform.getSequence());
			List<LatitudeDataBean> childBeans = new ArrayList<LatitudeDataBean>();
			for (TerminalBean terminalBean:terminals) {
				LatitudeDataBean childDataBean = null; 
				if(platform.getPlatformFk().equals(terminalBean.getPlatformFK())){
					childDataBean = new LatitudeDataBean();
					childDataBean.setName(terminalBean.getTerminal());
					childDataBean.setValue(getStringValue(terminalBean.getTerminalFk()));
					childDataBean.setSequence(terminalBean.getSequence());
					childBeans.add(childDataBean);
				}
			}
			//全部的时候加载所有的
			if(platform.getPlatformFk().intValue()==-1){
				List<TerminalBean> terminalBeans = terminalDao.getAllTerminalDisBeans(REGION_NAME);
				for (TerminalBean terminalBean:terminalBeans) {
						LatitudeDataBean childDataBean = null; 
						childDataBean = new LatitudeDataBean();
						childDataBean.setName(terminalBean.getTerminal());
						childDataBean.setValue(getStringValue(terminalBean.getTerminalFk()));
						childDataBean.setSequence(terminalBean.getSequence());
						childBeans.add(childDataBean);
				}
			}
			LatitudeDataBean[] latituArrays = childBeans.toArray(new LatitudeDataBean[]{});
			Arrays.sort(latituArrays, new Comparator() {
				@Override
				public int compare(Object fromObj, Object toObj) {
					LatitudeDataBean logFrom = (LatitudeDataBean)fromObj;
					LatitudeDataBean toFrom = (LatitudeDataBean)toObj;
					return logFrom.getSequence().compareTo(toFrom.getSequence());
				}
			});
			
			latitudeDataBean.setChildren(Arrays.asList(latituArrays));
			latidueLists.add(latitudeDataBean);
		}
		LatitudeDataBean[] latituArrays = latidueLists.toArray(new LatitudeDataBean[]{});
		Arrays.sort(latituArrays, new Comparator() {
			@Override
			public int compare(Object fromObj, Object toObj) {
				LatitudeDataBean logFrom = (LatitudeDataBean)fromObj;
				LatitudeDataBean toFrom = (LatitudeDataBean)toObj;
				return logFrom.getSequence().compareTo(toFrom.getSequence());
			}
		});
		return Arrays.asList(latituArrays);
	};
	/**    
	 * getCategoryBean(get the categoryBean by key)   
	 * 
	 * @param key
	 * @return 
	 * 
	 * CategoryBean
	 */
	public CategoryBean getCategoryBean(Integer key){
		for (CategoryBean categroyBean : categoryBeans.get(LatitudeType.CATEGORY)) {
			if(categroyBean.getCategoryFk().equals(key)){
				return categroyBean;
			}
		}
		return null;
		
	}
	
	/**    
	 * getChannelBean(get the channelBean by key)   
	 * 
	 * @param key
	 * @return 
	 * 
	 * ChannelBean
	 */
	public ChannelBean getChannelBean(Integer key){
		for (ChannelBean channelBean : channelBeans.get(LatitudeType.CHANNEL.name())) {
			if(key!=null&&channelBean.getChannelFk().equals(key))
				return channelBean;
		}
		return null;
	}
	/**    
	 * getLogSourceBean(get the logSourceBean by key)   
	 * 
	 * 
	 * @param key
	 * @return 
	 * 
	 * LogSourceBean
	 */
	public LogSourceBean getLogSourceBean(Integer key){
		for (LogSourceBean logSourceBean : logSourceBeans.get(LatitudeType.LOGS.name())) {
			if(key!=null&&logSourceBean.getLogId().intValue()==key.intValue())
				return logSourceBean;
		}
		return null;
	}
	
	/**    
	 * getOperatoryBean(get the operatorBean by key)   
	 * 
	 * @param key
	 * @return 
	 * 
	 * OperatorBean
	 */
	public OperatorBean getOperatoryBean(Integer key){
		for (OperatorBean opeBean : operatorBeans.get(LatitudeType.OPERATOR.name())) {
			if(key!=null&&opeBean.getOperatorFk().equals(key))
				return opeBean;
		}
		return null;
	}
	/**    
	 * getPlatFormBean(get the paltform by key)   
	 * 
	 * @param key
	 * @return 
	 * 
	 * PlatformBean
	 */
	public PlatformBean getPlatFormBean(Integer key){
		for (PlatformBean platBean : platformBeans.get(LatitudeType.PLATFORM.name())) {
			if(key!=null&&platBean.getPlatformFk().equals(key))
				return platBean;
		}
		return null;
	}
	
	
	/**    
	 * getTerminalBean(get the terminBean by key)   
	 * 
	 * @param key
	 * @return 
	 * 
	 * TerminalBean
	 */
	public TerminalBean getTerminalBean(Integer key){
		for (TerminalBean terminBean : terminalBeans.get(LatitudeType.TERMINAL.name())) {
			if(terminBean.getTerminalFk().equals(key))
				return terminBean;
		}
		return null;
	}
	
	public List<TerminalBean> getTerminalBean(Integer... key){
		List<TerminalBean> beans = new ArrayList<TerminalBean>();
		for (TerminalBean terminBean : terminalBeans.get(LatitudeType.TERMINAL.name())) {
			loop:
			for(Integer fk : key){
				if(terminBean.getTerminalFk().equals(fk)){
					beans.add(terminBean);
					break loop;
				}
			}
		}
		return beans;
	}
	
	public void reLoadData(){
    	if(this.isReload){
	    	loadData();
	    	this.setReload(false);
    	}
    }

	public boolean isReload() {
		return isReload;
	}
	public void setReload(boolean isReload) {
		this.isReload = isReload;
	}

	public Map<String, List<ChannelBean>> getChannelBeans() {
		return channelBeans;
	}

	public void setChannelBeans(Map<String, List<ChannelBean>> channelBeans) {
		this.channelBeans = channelBeans;
	}

	public Map<String, List<LogSourceBean>> getLogSourceBeans() {
		return logSourceBeans;
	}

	public void setLogSourceBeans(Map<String, List<LogSourceBean>> logSourceBeans) {
		this.logSourceBeans = logSourceBeans;
	}

	public Map<String, List<OperatorBean>> getOperatorBeans() {
		return operatorBeans;
	}

	public void setOperatorBeans(Map<String, List<OperatorBean>> operatorBeans) {
		this.operatorBeans = operatorBeans;
	}

	public Map<String, List<PlatformBean>> getPlatformBeans() {
		return platformBeans;
	}

	public void setPlatformBeans(Map<String, List<PlatformBean>> platformBeans) {
		this.platformBeans = platformBeans;
	}

	public Map<String, List<TerminalBean>> getRerminalBeans() {
		return terminalBeans;
	}

	public void setRerminalBeans(Map<String, List<TerminalBean>> rerminalBeans) {
		this.terminalBeans = rerminalBeans;
	}
	
	public Map<String, List<RegionLinkBean>> getRegionLinkBeans() {
		return regionLinkBeans;
	}
	public void setRegionLinkBeans(Map<String, List<RegionLinkBean>> regionLinkBeans) {
		this.regionLinkBeans = regionLinkBeans;
	}
	public Map<String, List<ApBean>> getApBeans() {
		return apBeans;
	}
	public void setApBeans(Map<String, List<ApBean>> apBeans) {
		this.apBeans = apBeans;
	}	
	
}
