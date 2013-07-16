package com.iaat.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.iaat.model.AccountBean;
import com.iaat.share.ErrorCode;
import com.nokia.ads.common.util.Log;
import com.nokia.ads.common.util.StringUtils;
import com.nokia.ads.platform.backend.core.UncheckedException;
import com.nokia.ads.platform.backend.core.webapi.ApiUser;

public class ExportUtil {
	private final static Log log = Log.getLogger(ExportUtil.class); 
	final protected static String TOMCATPATH = ExportUtil.class.getClassLoader().getResource("").getPath();
	final protected static String WEBROOT = TOMCATPATH.replace("WEB-INF/classes", "");
	final protected static String CSVPATH = WEBROOT;
//	final private static String TARGET_PATH = TOMCATPATH + "/config/csv/exportData.xml";
	final private static String TARGET_PATH = "/config/csv/exportData.xml";
	private static Map<String, Map<String, String>> EXPORT_FIELDS = null; 
	
	private static void init(){
//		File targetFile = new File(TARGET_PATH);
		SAXReader targetReader = new SAXReader();
		Document targetDom;
		try
		{
			targetDom = targetReader.read(getResourceAsStream(TARGET_PATH));
			Element rootElement = targetDom.getRootElement();
			
			List<?> nodes = rootElement.selectNodes("tabel");
			EXPORT_FIELDS = new HashMap<String, Map<String,String>>();
			for (Object node : nodes)
			{
				Element e = (Element) node;
				String tabelIndex = e.attributeValue("index");
				Map<String, String> fields = new LinkedHashMap<String, String>();
				for (Object child : e.elements())
				{
					Element echild = (Element) child;
					String fieldName = echild.attributeValue("fieldName");
					String description = echild.attributeValue("description");
					fields.put(fieldName, description);
				}
				EXPORT_FIELDS.put(tabelIndex, fields);
			}
			
		}
		catch (DocumentException e)
		{
			log.error("[ExportUtil.static] [ {0} ]",e.getMessage());
			throw new UncheckedException(e.getMessage(),ErrorCode.INITIAL_EXPORT_FIELD_ERROR);
		}
	}
	public static Map<String, String> getExportFields(String key){
		if(null == EXPORT_FIELDS) {
			init();
		}
		return EXPORT_FIELDS.get(key);
	}
	
	private static <T>  String createCSVFile(List<T> exportData, Map<String, String> exportField, String filename,String pattern) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");
		String outPutPath = sdf.format(date);
		outPutPath.replace("/", ""); 
		String csvPath = CSVPATH + outPutPath + "/" + filename + ".csv";
		File csvFile = null;
        BufferedWriter csvFileOutputStream = null;
        try {
            csvFile = new File(csvPath);
            File parent = csvFile.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            csvFile.createNewFile();
            csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(
//                    new FileOutputStream(csvFile), "GB2312"), 1024);
//            new FileOutputStream(csvFile), "UTF-8"), 1024);
                    new FileOutputStream(csvFile), "GBK"), 1024);
            String tableHead = "";
            for(Iterator<Entry<String, String>> iterator = exportField.entrySet().iterator(); iterator.hasNext();) {
            	Entry<String, String> entry = iterator.next();
            	tableHead+="\""+ entry.getValue().replace("\"", "\"\"") +"\",";
            	tableHead.substring(0, tableHead.length()-1);
            }
            //写表头
            csvFileOutputStream.write(tableHead.toString());
            csvFileOutputStream.newLine();
            
            for(int i = 0; i < exportData.size(); i++){
            	Object source = exportData.get(i);
            	
            	 for(Iterator<Entry<String, String>> iterator = exportField.entrySet().iterator(); iterator.hasNext();) {
            		String content = "";
                 	Entry<String, String> entry = iterator.next();
                 	Class<? extends Object> clazz = source.getClass();
                 	
                 	String firstLetter = entry.getKey().substring(0,1).toUpperCase(); //获得字段第一个字母大写
                    String getMethodName = "get" + firstLetter + entry.getKey().substring(1); //转换成字段的get方法 
                    
					Method method = clazz.getMethod(getMethodName);
					
					Object value = method.invoke(source);
					
					if(value != null) {
						if (value instanceof Calendar) {
//							String pattern = "yyyy/MM/dd";
							SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
							value = dateFormat.format(((Calendar)value).getTime());
						}
//						else if (value instanceof Platform && Platform.QT == value) {
//							value = "MEEGO";
//						}
						else if (value instanceof Float) {
							value = NumberUtil.getFormatData((Float)value);
						}
						
					}else{
						value = "";
					}
					content+="\""+ value.toString().replace("\"", "\"\"") +"\",";
					content.substring(0, content.length()-1);
					csvFileOutputStream.write(content);
            	 }
            	csvFileOutputStream.newLine();
            }
            csvFileOutputStream.flush();
        } catch (Exception e) {
        	throw new UncheckedException(e.getMessage(),ErrorCode.EXPORT_CVS_ERROR);
        } finally {
            try {
                csvFileOutputStream.close();
            } catch (IOException e) {
            	throw new UncheckedException(e.getMessage(),ErrorCode.EXPORT_CVS_ERROR);
            }
        }
        return outPutPath + "/" + filename + ".csv";
    }
	
	/**
	 * 
	 * exportToCsv(生成csv文件,返回路径)   
	 * 
	 * @param <T>
	 * @param resourceData
	 * @param tableIndex
	 * @return 
	 * 
	 * String
	 */
	public static <T> String exportToCsv(List<T> resourceData, String tableIndex,ApiUser user) {
		String pattern = "yyyy/MM/dd";
		return exportToCsvDateFormat(resourceData, tableIndex, pattern, user);
	}
	public static <T> String exportToCsv(List<T> resourceData, String tableIndex) {
		String pattern = "yyyy/MM/dd";
		return exportToCsvDateFormat(resourceData, tableIndex, pattern);
	}

	public static <T> String exportToCsvHMS(List<T> resourceData, String tableIndex,ApiUser user) {
		String pattern = "yyyy/MM/dd HH:mm:ss";
		return exportToCsvDateFormat(resourceData, tableIndex, pattern, user);
	}
	public static <T> String exportToCsvHMS(List<T> resourceData,Calendar date, String tableIndex,ApiUser user) {
		String pattern = "yyyy/MM/dd HH:mm:ss";
		return exportToCsvDateFormat(resourceData,date, tableIndex, pattern, user);
	}
	private static <T> String exportToCsvDateFormat(List<T> resourceData, String tableIndex,String pattern,ApiUser user) {
		Map<String, String> fields =ExportUtil.getExportFields(tableIndex);
		AccountBean bean = (AccountBean) user.getData();
		String fileName  = bean.getName()+"_"+new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(Calendar.getInstance().getTime());
		String path = ExportUtil.createCSVFile(resourceData, fields, fileName,pattern);
		return path;
	}
	private static <T> String exportToCsvDateFormat(List<T> resourceData, String tableIndex,String pattern) {
		Map<String, String> fields =ExportUtil.getExportFields(tableIndex);
		String fileName = "IAAT" + "_" + new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(Calendar.getInstance().getTime());
		String path = ExportUtil.createCSVFile(resourceData, fields, fileName,pattern);
		return path;
	}
	private static <T> String exportToCsvDateFormat(List<T> resourceData,Calendar date, String tableIndex,String pattern,ApiUser user) {
		Map<String, String> fields =ExportUtil.getExportFields(tableIndex);
		AccountBean bean = (AccountBean) user.getData();
		String fileName = bean.getName()+"_"+new SimpleDateFormat("yyyy-MM-dd").format(date.getTime())+"_export"+new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(Calendar.getInstance().getTime());
		String path = ExportUtil.createCSVFile(resourceData, fields, fileName,pattern);
		
		return path;
	}
	private static InputStream getResourceAsStream(String resource) {
		String stripped = resource.startsWith("/") ? 
				resource.substring(1) : resource;
	
		InputStream stream = null; 
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if (classLoader!=null) {
			stream = classLoader.getResourceAsStream( stripped );
		}
		if ( stream == null ) {
			ExportUtil.class.getResourceAsStream( resource );
		}
		if ( stream == null ) {
			stream = ExportUtil.class.getClassLoader().getResourceAsStream( stripped );
		}
		if ( stream == null ) {
			throw new UncheckedException( resource + " not found" ,ErrorCode.EXPORT_CVS_ERROR);
		}
		return stream;
	}
	/**
	 * 保留两位小数
	 * @param numValue
	 * @return
	 */
	public static float reservationTwoDecimal(String numValue){
		if(null!=numValue&&StringUtils.hasLength(numValue))
			return new BigDecimal(numValue).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
		return 0f;
	} 
}
