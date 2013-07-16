package com.iaat.dbutils.source;

import java.util.PropertyResourceBundle;


/**
 * 
 * <p>Title: DataSourceConfig</p>
 * <p>Description: </p>
 * @author    pandeng
 * @version   1.0
 */



public class DataSourceConfig{
	
	
	private static Object propertiesLock = new Object(); 
	private static PropertyResourceBundle  bundle=(PropertyResourceBundle)PropertyResourceBundle.getBundle("com.iaat.dbutils.source.DataSourceConfig");	
	
	  public static String getProperty(String name,String value) {
		  try{
			  synchronized (propertiesLock) {
				  String nvalue = bundle.getString(name);  
				  if(nvalue==null || nvalue.trim().equals("")){
					  nvalue=value;
				  }
				  return nvalue;
			  }
		  }catch(Exception e){
			  e.printStackTrace();
			  return value;
		  }
	  }
	  
	  public static String getProperty(String name) {
		  try{
			  synchronized (propertiesLock) {
				  String nvalue = bundle.getString(name);  
				  return nvalue;
			  }
		  }catch(Exception e){
			  e.printStackTrace();
			  return null;
		  }
	  }
	  
	  public static void main(String[] org){
		  System.out.println(getProperty("Beijing_URL"));
	  }

}
