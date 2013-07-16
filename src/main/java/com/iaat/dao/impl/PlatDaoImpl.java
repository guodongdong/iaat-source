package com.iaat.dao.impl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import com.iaat.dao.BaseDaoUtils;
import com.iaat.dao.stats.PlatDao;
import com.iaat.json.SearchBar;
import com.iaat.model.LogSourceType;
import com.iaat.model.PlatBean;
import com.iaat.model.WeekBean;
import com.iaat.share.Params;
import com.iaat.util.DateUtil;
import com.iaat.util.LogUtils;
import com.iaat.util.SqlUtils;
import com.nokia.ads.common.util.Log;
import com.nokia.ads.platform.backend.util.Paging;
public class PlatDaoImpl extends BaseDaoUtils<PlatBean> implements PlatDao{
	private final static Log log = Log.getLogger(PlatDaoImpl.class);
	public final static String HOUR=Params.D_DATE+".hour_of_day";
	public final static String DAY=Params.D_DATE+".year,"+Params.D_DATE+".time";
	public final static String WEEK=Params.D_DATE+".week_num";
	public final static String MONTH=Params.D_DATE+".year,"+Params.D_DATE+".month_of_year";
	private final static String TIME=Params.D_DATE+".time";
	private final static String MONTH_=Params.D_DATE+".month_of_year";
	private final static String AREA="CONCAT_WS('|',max("+Params.D_DATE+".time),min("+Params.D_DATE+".time))";
	/**
	 *平台数据饼状图
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PlatBean> getPlatPie(SearchBar bar,LogSourceType logtype) {
		List<PlatBean> list=null;
		try{
			StringBuffer sql = new StringBuffer();
			sql.append(SqlUtils.getIns().getSQL("getPlatPie", logtype));
			sql.append(getConditions(bar,true," and")).append(" group by "+Params.D_PLAT_FORM+".Platform_FK");	
			list = this.query(bar,sql.toString(),new BeanListHandler(PlatBean.class),true);
		}catch (Exception e) {
			log.error("[PlatDaoImpl.getPlatPie] [{0}]",e.getMessage());
		}
		return list;
	}
	/**
	 * 平台数据曲线
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String,List<PlatBean>> getPlatLine(SearchBar bar,String type,LogSourceType logtype) {
		List<PlatBean> list=null;
		Map<Integer, WeekBean> m=null;
		try{
			String f_sit = LogUtils.getIns().getTable(logtype).getSitutaion();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT ").append(Params.D_PLAT_FORM).append(".platform as name,count(DISTINCT(f.IMEI_FK)) as usernum,min(")
			.append(Params.D_DATE).append(".time)as min, max(").append(Params.D_DATE).append(".time) as max,")
			.append(weekOrMonth(type,bar.isOneDay())).append(" as date,").append(weekOrMonth(type,bar.isOneDay())).append(" as weeknum FROM d_date ")
			.append(Params.D_DATE).append(" left join ").append(f_sit).append(" f on ").append(Params.D_DATE).append(".Date_FK= f.Date_FK  left join d_platform ")
			.append(Params.D_PLAT_FORM).append(" on "+Params.D_PLAT_FORM+".Platform_FK = f.Platform_FK " ).append("left join d_region ")
			.append(Params.D_REGION).append(" on "+Params.D_REGION+".Region_FK=f.Region_FK ");
			sql.append(getConditions(bar,true," where")).append(" group by "+Params.D_PLAT_FORM+".Platform_FK,")
			.append(get(type,bar.isOneDay())).append(" order by "+Params.D_DATE+".time ");
			list = this.query(bar,sql.toString(),new BeanListHandler(PlatBean.class),true);
			m=getArea(bar,type);
		}catch (Exception e) {
			log.error("[PlatDaoImpl.getPlatLine] [{0}]",e.getMessage());
		}
		return getV(list,type,m,bar.isOneDay(),logtype,bar);
	}
	/**
	 * 获取周月的区间范围
	 * @param bar
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map<Integer, WeekBean> getArea(SearchBar bar,String type){
		StringBuffer sb = new StringBuffer();
		Map<Integer, WeekBean> wmap = null;
		sb.append("SELECT min(ddate.time)as min, max(ddate.time) as max,"+weekOrMonth(type, false)+" as date,"+weekOrMonth(type, false)+" as weeknum FROM d_date ddate where ddate.time  BETWEEN '"+DateUtil.calendar2String(bar.getStartDateTime())+"' and '" +DateUtil.calendar2String(bar.getEndDateTime())+"' group by "+weekOrMonth(type, false)+"");	
		try {
			List<WeekBean> beans=this.queryBean(bar,sb.toString(),new BeanListHandler(WeekBean.class),true);
			wmap = new HashMap<Integer, WeekBean>();
			for(WeekBean w:beans){
				w.setMax(w.getMax().substring(0, 10));
				w.setMin(w.getMin().substring(0, 10));
				wmap.put(w.getWeeknum(), w);
			}
		} catch (Exception e) {
			log.error("[PlatDaoImpl.getArea] [{0}]",e.getMessage());
		}
		return wmap;
	}
	/**
	 * 获取日周月
	 * @param type
	 * @param hour
	 * @return
	 */
	private String get(String type,boolean hour){
		if(hour&&SearchBar.DAILY.equalsIgnoreCase(type)){
			return HOUR;
		}
		else if(SearchBar.DAILY.equalsIgnoreCase(type)){
			return DAY;
		}else if(SearchBar.WEEKLY.equalsIgnoreCase(type)){
			return WEEK;
		}else if(SearchBar.MONTHLY.equalsIgnoreCase(type)){
			return MONTH;
		}else{
			return DAY;
		}
	}
	/**
	 * 判断查询类别
	 * @param type 年|周|月
	 * @param hour
	 * @return
	 */
	private String weekOrMonth(String type,boolean hour){
		if(SearchBar.MONTHLY.equalsIgnoreCase(type)){
			return MONTH_;
		}else if(hour&&SearchBar.DAILY.equalsIgnoreCase(type)){
			return HOUR;
		}
		else if(SearchBar.DAILY.equalsIgnoreCase(type)){
			return TIME;
		}
		else if(SearchBar.WEEKLY.equalsIgnoreCase(type)){
			return WEEK;
		}else{
			return AREA;
		}
	}
	/**
	 * 获取数据的平台集合
	 * @param beans
	 * @return
	 */
	private String[] getPlats(List<PlatBean> beans){
		if(beans==null||beans.size()<1)return null;
		HashSet<String> plats = new HashSet<String>();
		for(PlatBean b:beans){
			if(b.getName()!=null)
			plats.add(b.getName());
		}
		String[] str= new String[plats.size()];
		return (String[])plats.toArray(str);
		
	}
	/**
	 * 获取时间范围
	 * @param beans
	 * @param type
	 * @param bar
	 * @return
	 */
	private String[] getTimes(List<PlatBean> beans,String type,SearchBar bar){
		if(beans==null||beans.size()<1)return null;
		HashSet<String> plats = new HashSet<String>();
		String profix ="SELECT DISTINCT("+weekOrMonth(type,bar.isOneDay())+") as date from d_date "+Params.D_DATE+" where "+Params.D_DATE+".time  BETWEEN '"+DateUtil.calendar2String(bar.getStartDateTime())+"' and '" +DateUtil.calendar2String(bar.getEndDateTime())+"'";
		List<PlatBean> all = null;
		try {
			all = this.query(bar,profix, new BeanListHandler<PlatBean>(PlatBean.class),true);
		} catch (Exception e) {
		}
		for(PlatBean b:all){
				plats.add(b.getDate());
		}
		String[] str= new String[plats.size()];
		return (String[])plats.toArray(str);
		
	}
	/**
	 * 初始化时间集合
	 * @param set
	 * @param beans
	 * @param type
	 */
	private void initData(Set<String> set,List<PlatBean> beans,String type){
		if(beans==null)return;
		for(PlatBean b:beans){
			if(SearchBar.WEEKLY.equalsIgnoreCase(type)){
				set.add(b.getWeeknum()+"");
			}else{
				set.add(b.getDate());
			}
		}
	}
	/**
	 * 格式化时间
	 * @param beans
	 * @param type
	 * @param isoneday
	 * @param time
	 */
	private void setTime(List<PlatBean> beans,String type,boolean isoneday,String time){
		String temp =":00",t="0";;
		for(PlatBean p:beans){	
				if(!isoneday){temp="";t="";}
				if(p.getDate()==null){
					if(time.length()==1)time+=t;
					p.setDate(time+temp, false);
				}
				else{
					if(p.getDate().length()==1){
						p.setDate(t+p.getDate()+temp, false);
					}else{
						p.setDate(p.getDate()+temp, false);
					}				
				}			
		}
	}
	/**
	 * 格式化时间
	 * @param beans
	 * @param type
	 * @param isoneday
	 */
	private void setTime(List<PlatBean> beans,String type,boolean isoneday){
		String temp =":00",t="0";;
		if(isoneday){
			for(PlatBean p:beans){	
					String time = p.getDate();
					if(!isoneday){temp="";t="";}
					if(p.getDate()==null){
						if(time.length()==1)time+=t;
						p.setDate(time+temp, false);
					}
					else{
						if(p.getDate().length()==1){
							p.setDate(t+p.getDate()+temp, false);
						}else{
							p.setDate(p.getDate()+temp, false);
						}
					}
				}
		}else{
			
		}
	}
	/**
	 * 填充时间
	 * @param beans
	 * @param tmap
	 * @param type
	 * @param times
	 * @param isoneday
	 * @param plats
	 * @return
	 */
	private List<PlatBean> formatDate(List<PlatBean> beans, Map<Integer, WeekBean> tmap,String type,String[] times,boolean isoneday,String[] plats){
		Set<String> dates = new HashSet<String>();
		initData(dates, beans,type);
		if(!SearchBar.DAILY.equalsIgnoreCase(type)){
			for(PlatBean b:beans){
				b.setDate(tmap.get(b.getWeeknum()).getMin()+"~"+tmap.get(b.getWeeknum()).getMax(), false);
			}
		}	
		return process(dates,beans,times,plats,type,isoneday,tmap);
		
	}
	/**
	 * 填充数据
	 * @param plat
	 * @param time
	 * @param type
	 * @param isoneday
	 * @param tmap
	 * @return
	 */
	private List<PlatBean> getNewBeans(String plat[],String time,String type,boolean isoneday,Map<Integer, WeekBean> tmap){
		List<PlatBean> temp = new ArrayList<PlatBean>();
		PlatBean pb = null;
		for(String p:plat){
			pb = new PlatBean();
			pb.setName(p);
			if(SearchBar.WEEKLY.equalsIgnoreCase(type)){
				WeekBean w = tmap.get(Integer.parseInt(time));
				pb.setDate(w.getMin()+"~"+w.getMax(), false);
			}else{
				pb.setDate(time,false);
			}
			pb.setOlduser(0);
			pb.setUsernum(0);
			pb.setNewuser(0);
			temp.add(pb);
		}
		setTime(temp,type,isoneday,time);
		return temp;
		
	}
	/**
	 * 处理数据的格式
	 * @param set
	 * @param beans
	 * @param times
	 * @param plats
	 * @param type
	 * @param isoneday
	 * @param tmap
	 * @return
	 */
	private List<PlatBean> process(Set<String> set,List<PlatBean> beans,String[] times,String[] plats,String type,boolean isoneday,Map<Integer, WeekBean> tmap){
		List<PlatBean> rs = new ArrayList<PlatBean>();
		for(String t:times){
			if(!set.contains(t)){
				rs.addAll(getNewBeans(plats,t,type,isoneday,tmap));
			}
		}
		setTime(beans,type,isoneday);
		rs.addAll(beans);
		return rs;

	}
	/**
	 * 过滤数据
	 * @param beans
	 * @param type
	 * @param logtype
	 * @param bar
	 * @return
	 */
	private List<PlatBean> filter(List<PlatBean> beans,String type,LogSourceType logtype,SearchBar bar){
		List<PlatBean> t = new ArrayList<PlatBean>();
		if(SearchBar.DAILY.equalsIgnoreCase(type)){
			return beans;
		}else{
			for(PlatBean p:beans){
				if(p.getName()==null){}else{
					t.add(p);
				}
			}
		}
		return t;
	}
	/**
	 * 获取返回数据并格式化
	 * @param arr
	 * @param type
	 * @param tmap
	 * @param isoneday
	 * @param logtype
	 * @param bar
	 * @return
	 */
	public Map<String,List<PlatBean>> getV(List<PlatBean> arr,String type, Map<Integer, WeekBean> tmap,boolean isoneday,LogSourceType logtype,SearchBar bar){
		if(arr==null||arr.size()<1)return null;
		List<PlatBean> beans=filter(arr,type,logtype,bar);
		String[] temp=getPlats(beans);
		String[] times = getTimes(beans,type,bar);
		List<PlatBean> rs =formatDate(beans,tmap,type,times,isoneday,temp);
		Map<String,List<PlatBean>> map = getMap(rs,temp,type);
		return map;
	}
	/**
	 * 以平台归类数据
	 * @param rs
	 * @param temp
	 * @param type
	 * @return
	 */
	private Map<String,List<PlatBean>> getMap(List<PlatBean> rs,String[] temp,String type){
		Map<String,List<PlatBean>> map = new HashMap<String, List<PlatBean>>();
		for(int in=0;in<temp.length;in++){
			List<PlatBean> l = new LinkedList<PlatBean>();
			for(PlatBean b:rs){
				if(b.getName()==null){
					b.setStats(type);
					l.add(b);
				}
				else if(b.getName().equalsIgnoreCase(temp[in])){
					b.setStats(type);
					l.add(b);
				}		
			}
			Collections.sort(l);
			map.put(temp[in], l);
		}
		return map;
	}
	
	/**
	 * 平台数据列表
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PlatBean> getPlatList(SearchBar bar,LogSourceType logtype,Paging paging) {
		List<PlatBean> list=null;
		try{
			StringBuffer sql = new StringBuffer();
			sql.append(SqlUtils.getIns().getSQL("getPlatList", logtype));
			sql.append(getConditions(bar,true," and")).append(" group by "+Params.D_PLAT_FORM+".Platform_FK");	
			list = query(bar,sql.toString(),new BeanListHandler(PlatBean.class),paging,true);
			Map<String,Integer> map = getNewUser(bar,logtype);
			setUsers(map,list);
		}catch (Exception e) {
			log.error("[PlatDaoImpl.getPlatList] [{0}]",e.getMessage());
		}
		return list;
	}
	/**
	 * 获取新用户数
	 * @param bar
	 * @param logtype
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Integer> getNewUser(SearchBar bar,LogSourceType logtype) {
		List<PlatBean> list=null;
		try{
			StringBuffer sql = new StringBuffer();
			sql.append(SqlUtils.getIns().getSQL("getNewUser", logtype));
			sql.append(getConditions(bar,true," and")).append(" and f.isNewUser = 1 group by "+Params.D_PLAT_FORM+".Platform_FK");
			list=query(bar,sql.toString(),new BeanListHandler(PlatBean.class),true);
		}catch (Exception e) {
			log.error("[PlatDaoImpl.getPlatList] [{0}]",e.getMessage());
		}
		return getUsers(list);
	}
	
	
	private void setUsers(Map<String,Integer> map,List<PlatBean> list){
		if(list==null||list.size()<1)return;
		Integer t=null;
		int num=0;
		for(PlatBean b:list){
			if(map==null)b.setNewuser(0);
			else{
				t=map.get(b.getName());
				if(t==null)num=0;else{num=t;}
				b.setNewuser(num);
			}
		}
	}
	/**
	 * 获取新用户
	 * @param users
	 * @return
	 */
	private Map<String,Integer> getUsers(List<PlatBean> users){
		if(users==null||users.size()<1)return null;
		Map<String,Integer> map = new HashMap<String, Integer>();
		for(PlatBean w:users){
			map.put(w.getName(), w.getNewuser());
		}
		return map;
	}
	
	
	
}
