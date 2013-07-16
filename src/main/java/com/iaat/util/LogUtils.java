package com.iaat.util;

import java.util.HashMap;
import java.util.Map;
import com.iaat.model.LogSourceType;
public class LogUtils {

	private static final String OUTER_SITUTAION="f_outer_user_situation";
	private static final String TIME_SITUTAION="f_time_user_situation";
	private static final String TOM_SITUTAION="f_tom_user_situation";
	private static final String TOTAL_SITUTAION="f_total_user_situation";
	private static final String XPRESS_SITUTAION="f_xpress_user_situation";
	
	private static final String OUTER_STATS="f_outer_user_stats";
	private static final String TIME_STATS="f_time_user_stats";
	private static final String TOM_STATS="f_tom_user_stats";
	private static final String TOTAL_STATS="f_total_user_stats";
	private static final String XPRESS_STATS="f_xpress_user_stats";
	private LogUtils(){
		Table out = new Table(OUTER_SITUTAION,OUTER_STATS);
		Table time = new Table(TIME_SITUTAION,TIME_STATS);
		Table tom = new Table(TOM_SITUTAION,TOM_STATS);
		Table total =new Table(TOTAL_SITUTAION,TOTAL_STATS);
		Table xpress = new Table(XPRESS_SITUTAION,XPRESS_STATS);
		tbl.put(LogSourceType.OUTER, out);
		tbl.put(LogSourceType.TIME, time);
		tbl.put(LogSourceType.TOM, tom);
		tbl.put(LogSourceType.TOTAL, total);
		tbl.put(LogSourceType.XPRESS, xpress);
	};
	private static LogUtils logUtil = null;
	public static synchronized LogUtils getIns(){
		if(logUtil==null)logUtil=new LogUtils();
		return logUtil;
	}
	private static Map<LogSourceType,Table> tbl= new HashMap<LogSourceType, Table>();
	public Table getTable(LogSourceType log){
		return tbl.get(log);
	}
	public class Table{
		public Table(){};
		public Table(String situtaion, String stats) {
			this.situtaion = situtaion;
			this.stats = stats;
		}
		private String situtaion;
		private String stats;
		public String getSitutaion() {
			return situtaion;
		}
		public void setSitutaion(String situtaion) {
			this.situtaion = situtaion;
		}
		public String getStats() {
			return stats;
		}
		public void setStats(String stats) {
			this.stats = stats;
		}
		
	}
	
	public static void main(String[] args){
		System.out.println(LogUtils.getIns().getTable(LogSourceType.OUTER).getSitutaion());
	}
}
