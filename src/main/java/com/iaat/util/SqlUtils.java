package com.iaat.util;
import java.util.HashMap;
import java.util.Map;

import com.iaat.model.LogSourceType;
import com.iaat.share.Params;

public class SqlUtils {

	private SqlUtils(){};
	private static SqlUtils ins;
	public static SqlUtils getIns(){
		synchronized (SqlUtils.class) {
			if(ins==null){ins=new SqlUtils();init();}
		}
		return ins;
	}
	private static Map<String,String> map = new HashMap<String, String>();
	//getPlatList
	private static String SQL_TOM_platlist = "SELECT "+Params.D_PLAT_FORM+".platform as name, count(DISTINCT(f.IMEI_FK)) as usernum,sum(isNewUser) as newuser FROM d_platform "+Params.D_PLAT_FORM+",f_tom_user_situation f ,d_date "+Params.D_DATE+",d_region "+Params.D_REGION+
	" where "+Params.D_PLAT_FORM+".Platform_FK = f.Platform_FK AND "+Params.D_REGION+".Region_FK=f.Region_FK and "+Params.D_DATE+".Date_FK= f.Date_FK ";
	private static String SQL_XPRESS_platlist = "SELECT "+Params.D_PLAT_FORM+".platform as name, count(DISTINCT(f.IMEI_FK)) as usernum,sum(isNewUser) as newuser FROM d_platform "+Params.D_PLAT_FORM+",f_xpress_user_situation f ,d_date "+Params.D_DATE+",d_region "+Params.D_REGION+
	" where "+Params.D_PLAT_FORM+".Platform_FK = f.Platform_FK AND "+Params.D_REGION+".Region_FK=f.Region_FK and "+Params.D_DATE+".Date_FK= f.Date_FK ";
	
	//getNewUser
	private static String SQL_TOM_newuser="SELECT "+Params.D_PLAT_FORM+".platform as name, count(DISTINCT(f.IMEI_FK)) as newuser FROM d_platform "+Params.D_PLAT_FORM+",f_tom_user_situation f ,d_date "+Params.D_DATE+",d_region "+Params.D_REGION+
	" where "+Params.D_PLAT_FORM+".Platform_FK = f.Platform_FK AND "+Params.D_REGION+".Region_FK=f.Region_FK and "+Params.D_DATE+".Date_FK= f.Date_FK ";
	private static String SQL_XPRESS_newuser="SELECT "+Params.D_PLAT_FORM+".platform as name, count(DISTINCT(f.IMEI_FK)) as newuser FROM d_platform "+Params.D_PLAT_FORM+",f_xpress_user_situation f ,d_date "+Params.D_DATE+",d_region "+Params.D_REGION+
	" where "+Params.D_PLAT_FORM+".Platform_FK = f.Platform_FK AND "+Params.D_REGION+".Region_FK=f.Region_FK and "+Params.D_DATE+".Date_FK= f.Date_FK ";
	
	//getPlatPie
	private static String SQL_TOM_platpie="SELECT "+Params.D_PLAT_FORM+".platform as name, count(DISTINCT(f.IMEI_FK)) as usernum FROM d_platform "+Params.D_PLAT_FORM+",f_tom_user_situation f,d_date "+Params.D_DATE+",d_region "+Params.D_REGION+
	" where "+Params.D_PLAT_FORM+".Platform_FK = f.Platform_FK AND "+Params.D_REGION+".Region_FK=f.Region_FK and "+Params.D_DATE+".Date_FK= f.Date_FK";
	private static String SQL_XPRESS_platpie="SELECT "+Params.D_PLAT_FORM+".platform as name, count(DISTINCT(f.IMEI_FK)) as usernum FROM d_platform "+Params.D_PLAT_FORM+",f_xpress_user_situation f,d_date "+Params.D_DATE+",d_region "+Params.D_REGION+
	" where "+Params.D_PLAT_FORM+".Platform_FK = f.Platform_FK AND "+Params.D_REGION+".Region_FK=f.Region_FK and "+Params.D_DATE+".Date_FK= f.Date_FK";
	
	//getImeiList
	private static String SQL_TOM_imeilist="SELECT "+Params.D_IMEI+".IMEI_NUM as imei,CONCAT_WS('-',"+Params.D_REGION+".P_Region,"+Params.D_REGION+".Region) as region,"+Params.D_OPERATOR+".operator as operator,count("+Params.D_IMEI+".IMEI_FK) as num FROM d_imei "+Params.D_IMEI+",d_region "+Params.D_REGION+" ,d_operator "+Params.D_OPERATOR+",d_date "+Params.D_DATE+",f_tom_user_situation f " +
	"where f.IMEI_FK = "+Params.D_IMEI+".IMEI_FK and f.Region_FK = "+Params.D_REGION+".Region_FK and  f.Date_FK = "+Params.D_DATE+".Date_FK and f.Operator_FK = "+Params.D_OPERATOR+".Operator_FK ";
	private static String SQL_XPRESS_imeilist="SELECT "+Params.D_IMEI+".IMEI_NUM as imei,CONCAT_WS('-',"+Params.D_REGION+".P_Region,"+Params.D_REGION+".Region) as region,"+Params.D_OPERATOR+".operator as operator,count("+Params.D_IMEI+".IMEI_FK) as num FROM d_imei "+Params.D_IMEI+",d_region "+Params.D_REGION+" ,d_operator "+Params.D_OPERATOR+",d_date "+Params.D_DATE+",f_xpress_user_situation f " +
	"where f.IMEI_FK = "+Params.D_IMEI+".IMEI_FK and f.Region_FK = "+Params.D_REGION+".Region_FK and  f.Date_FK = "+Params.D_DATE+".Date_FK and f.Operator_FK = "+Params.D_OPERATOR+".Operator_FK ";
	
	public String getSQL(String m,LogSourceType type){
		return map.get(m+type);
	}
	private static void init(){
		map.put("getPlatList"+LogSourceType.TOM, SQL_TOM_platlist);
		map.put("getPlatList"+LogSourceType.XPRESS, SQL_XPRESS_platlist);
		
		map.put("getNewUser"+LogSourceType.TOM, SQL_TOM_newuser);
		map.put("getNewUser"+LogSourceType.XPRESS, SQL_XPRESS_newuser);
		
		map.put("getPlatPie"+LogSourceType.TOM, SQL_TOM_platpie);
		map.put("getPlatPie"+LogSourceType.XPRESS, SQL_XPRESS_platpie);
		
		map.put("getImeiList"+LogSourceType.TOM, SQL_TOM_imeilist);
		map.put("getImeiList"+LogSourceType.XPRESS, SQL_XPRESS_imeilist);
	}
	
}
