package com.iaat.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.iaat.share.ErrorCode;
import com.nokia.ads.platform.backend.core.UncheckedException;
import com.nokia.ads.platform.backend.model.Money;

/**
 * 
 * @name DateUtil
 * 
 * @description CLASS_DESCRIPTION 日期工具
 * 
 * MORE_INFORMATION
 * 
 * @author zhaozhi
 * 
 * @since 2012-4-18
 *       
 * @version 1.0
 */
public class DateUtil {
	
//	public static void main(String[] args) {
//		System.out.println("12");
//	}
	
	/**
	 * 1天的毫秒数
	 */
	public static Long day = 86400000L;
//	private static Long day = 24*60*60*1000L;
	private static Long minite=60*1000L;
	/**
	 * 
	 * getDate(获得一个日期)   
	 * 
	 * @param dayCount 距离当前时间的天数
	 * @return 
	 * 
	 * Date
	 */
	public static Date getDate(Integer dayCount){
		Date date = new Date();
		
		date.setTime(date.getTime() + day*dayCount);
		return date;
	}
	
	/**
	 * 
	 * getDate(获得一个日期)
	 * 
	 * @param calendar
	 * @param dayCount
	 * @return 
	 * 
	 * Date
	 */
	public static Date getDate(Calendar calendar,Integer dayCount){
		if (ValidateUtils.isNotNull(calendar)) {
			Date date = new Date();
			date.setTime(calendar.getTimeInMillis() + day*dayCount);
			return date;
		}
		return null;
	}

	public static Calendar getCalendar(Calendar calendar,Integer dayCount){
		if (ValidateUtils.isNotNull(calendar)) {
			Calendar flag = Calendar.getInstance();
			flag.setTimeInMillis(calendar.getTimeInMillis());
			Date date = new Date();
			date.setTime(flag.getTimeInMillis() + day*dayCount);
			flag.setTime(date);
			return flag;
		}
		return null;
	}
	
	/**
	 * 
	 * getCalendar(获得一个日期)   
	 * 
	 * @param dayCount 0--获得现在的时间
	 * @return 
	 * 
	 * Calendar
	 */
	public static Calendar getCalendar(Integer dayCount){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(getDate(dayCount));
		return calendar;
	}
	
	/**
	 * 获取周期的第一天开始时间
	 * 
	 * @param 当前时间
	 * @param 周期类型
	 * @return
	 *//*
	public static Calendar cycleStartDay(Calendar currentTime, StatsType statsType){
		if(currentTime == null) {
			return null;
		}
		int flag = 0;
		switch(statsType) {
		case WEEKLY:
			//国外星期从 星期日开始
			flag = currentTime.get(Calendar.DAY_OF_WEEK) - 1;
			break;
		case MONTHLY:
			flag = currentTime.get(Calendar.DAY_OF_MONTH);
			break;
		}
		//排除当天，所以需要 - 1
		currentTime.add(Calendar.DATE, -(flag - 1));
		return currentTime;
	}
	
	*//**
	 * 获取周期的最后一天时间
	 * 
	 * @param 当前时间
	 * @param 周期类型
	 * @return
	 *//*
	public static Calendar cycleEndDay(Calendar currentTime, StatsType statsType){
		if(null == currentTime) {
			return currentTime;
		}
		int flag = 0;
		switch(statsType) {
		case WEEKLY:
			//Calendar星期从 星期日开始
			currentTime.add(Calendar.WEEK_OF_YEAR, 1);
			flag = currentTime.get(Calendar.DAY_OF_WEEK) - 1;
			break;
		case MONTHLY:
			currentTime.add(Calendar.MONTH, 1);
			flag = currentTime.get(Calendar.DAY_OF_MONTH);
			break;
		}
		currentTime.add(Calendar.DATE, - flag);
		return currentTime;
	}
	*/
	public static String calendar2String(Calendar calendar){
		return calendar2String(calendar,"yyyy-MM-dd");  
	}
	public static String calendar2String(Calendar calendar,String format){
		if(ValidateUtils.isNull(calendar)){
			return null;
		}
		Date tasktime=calendar.getTime();  
		return date2String(tasktime,format);  
	}

	
	public static String date2String(Date tasktime){
		return date2String(tasktime, "yyyy-MM-dd");
	}
	
	public static String date2String(Date tasktime,String format){
		if(ValidateUtils.isNull(tasktime)){
			return null;
		}
		//设置日期输出的格式  
		SimpleDateFormat df=new SimpleDateFormat(format);  
		//格式化输出  
		return df.format(tasktime);  
	}
	
	public static String c2Str(Calendar calendar){
		if(ValidateUtils.isNull(calendar)){
			return null;
		}
		Date tasktime=calendar.getTime();  
		//设置日期输出的格式  
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//格式化输出  
		return df.format(tasktime);  
	}
	
	
	
	/**
	 * 
	 * createEndDate(再所给日期的基础上加上1天)   
	 * 
	 * @param calendar
	 * @return 
	 * 
	 * Calendar
	 */
	public static Calendar getEndDate(Calendar calendar){
		if (ValidateUtils.isNotNull(calendar)) {
			calendar.setTimeInMillis(calendar.getTimeInMillis()+day);
			return calendar;
		}
		return null;
	}
	public static Calendar getDate(Calendar calendar,int d){
		if (ValidateUtils.isNotNull(calendar)) {
			calendar.setTimeInMillis(calendar.getTimeInMillis()+(d*day));
			return calendar;
		}
		return null;
	}
	/**
	 * 
	 * createEndDate(再所给日期的基础上加上1天,减去1秒)   
	 * 
	 * @param calendar
	 * @return 
	 * 
	 * Calendar
	 */
	public static Calendar getFormatEndDate(Calendar endTime){
		if (ValidateUtils.isNotNull(endTime)) {
			endTime.set(Calendar.HOUR_OF_DAY, 23);
			endTime.set(Calendar.MINUTE, 59);
			endTime.set(Calendar.SECOND, 59);
			return endTime;
		}
		return null;
	}
	
	
	/**
	 * dayDiff (计算两个时间差) 
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public static int dayDiff(Calendar beginTime, Calendar endTime) {
		//-----求两个Calendar:   c0,c1相差的天数
        if(beginTime == null || endTime == null) {
        	return -1;
        }
        long flag = endTime.getTimeInMillis() - beginTime.getTimeInMillis();
		return (int) (flag / day);
	}
	
	/**
	 * dayDiff (计算两个时间差) 
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public static int miniteDiff(Calendar beginTime, Calendar endTime) {
		//-----求两个Calendar:   c0,c1相差的天数
        if(beginTime == null || endTime == null) {
        	return -1;
        }
        long flag = endTime.getTimeInMillis() - beginTime.getTimeInMillis();
		return (int) (flag / minite );
	}
	
	  /**
     * 把一个文件转化为字节
     * @param file
     * @return   byte[]
     * @throws Exception
     */
    public static byte[] getByte(File file) throws IOException
    {
        byte[] bytes = null;
        if(file!=null)
        {
            InputStream is = new FileInputStream(file);
            int length = (int) file.length();
            if(length>Integer.MAX_VALUE)   //当文件的长度超过了int的最大值
            {
                System.out.println("this file is max ");
                return null;
            }
            bytes = new byte[length];
            int offset = 0;
            int numRead = 0;
            while(offset<bytes.length&&(numRead=is.read(bytes,offset,bytes.length-offset))>=0)
            {
                offset+=numRead;
            }
            //如果得到的字节长度和file实际的长度不一致就可能出错了
            if(offset<bytes.length)
            {
                System.out.println("file length is error");
                return null;
            }
            is.close();
        }
        return bytes;
    }
    
    public static Calendar cutCalendar(Calendar time, String format){
    	if(time == null || format == null) {
    		return null;
    	}
    	SimpleDateFormat dateFormat = new SimpleDateFormat(format);
    	String formatTime = dateFormat.format(time.getTime());
    	try {
			time.setTime(dateFormat.parse(formatTime));
		} catch (ParseException e) {
			throw new UncheckedException("DataFormat error", ErrorCode.API_ERROR);
		}
    	return time;
    }
    /**
     * 将当天时间最大化，譬如 1900-01-01 TO 1900-01-01 23:59:59
     * @param endTime
     */
    public static void todayTimeMaximize(Calendar endTime) {
		endTime.set(Calendar.HOUR_OF_DAY, 23);
		endTime.set(Calendar.MINUTE, 59);
		endTime.set(Calendar.SECOND, 59);
	}

    public static Calendar todayTimeMinmize(Calendar endTime) {
		endTime.set(Calendar.HOUR_OF_DAY, 0);
		endTime.set(Calendar.MINUTE, 0);
		endTime.set(Calendar.SECOND, 0);
		return endTime;
	}
    public static Calendar getCalendar(String date){
    	Calendar rightNow = Calendar.getInstance();
    	SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
    	 try {
    		 rightNow.setTime(simpleDate.parse(date));
    		 return rightNow;
		} catch (ParseException e) {
			return null;
		}
    }
    
    /**    
     * liveTwoDecimal(保留两位小数)   
     * 
     * 
     * @param microValue
     * @return 
     * 
     * Double
     */
    public static Double liveTwoDecimal(Long microValue) {
		long cent = microValue * 100 / Money.MICRO;
		return Double.valueOf(((double)cent)/100);
	}
    /**
     * 获得指定文件的byte数组
     */
    public static byte[] getBytes(String filePath){
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    /**    
     * getFile(根据byte数组，生成文件)   
     * 
     * 
     * @param bfile
     * @param filePath
     * @param fileName
     * @return 
     * 
     * File
     */
    public static File getFile(byte[] bfile, String filePath,String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        File dir = new File(filePath);
        try {
            if(!dir.exists()){//判断文件目录是否存在
                dir.mkdirs();
            }
            file = new File(filePath+File.separator+fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bfile);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return file;
    }

    /**
     * 
     * deleteFile(递归删除文件)   
     * 
     * 
     * @param file 
     * 
     * void
     */
	public static void deleteFile(File file) {
		if (file.exists()) {
			if (file.isFile()) {
				file.delete();
			} else {
				File files[] = file.listFiles();
				for (File fileChild : files) {
					deleteFile(fileChild);
				}
			}
			file.delete();
		}
	}
	
	public static void clearTime(Calendar start) {
		start.set(Calendar.HOUR_OF_DAY, 0);
		start.set(Calendar.MINUTE, 0);
		start.set(Calendar.SECOND, 0);
		start.set(Calendar.MILLISECOND, 0);
	}
	
	/**    
	 * advIdsIsNull(判断广告主是否有为空)   
	 * 
	 * TODO(Here describes this method to be suitable the condition - to be possible to elect)
	 * 
	 * @param advertiserIds
	 * @return 
	 * 
	 * boolean
	 */
	public static boolean advIdsIsNull(Long[] advertiserIds) {
		if(advertiserIds != null){
			for (Long advId : advertiserIds) {
				if(advId == null){
					return true;
				}
			}	
		}
		return false;
	}
}
