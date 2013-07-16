package com.nokia.ads.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @name ValidatorNumber
 * 
 * @description CLASS_DESCRIPTION
 * 
 *              MORE_INFORMATION
 * 
 * @author shixiaowei
 * 
 * @since 2011-2-21
 * 
 * @version 1.0
 */
public class NumericValidator
{
	/**
	 * 
	 * isDecimal(验证是否为小数,包括整数 ;)   
	 * 
	 * TODO(Here describes this method to be suitable the condition - to be possible to elect)
	 * 
	 * @param str
	 * @return 
	 * 
	 * boolean
	 */
	public static boolean isDecimal(String str)
	{
		boolean flag = false;
		Pattern pattern = Pattern.compile("^(-?\\d+)(\\.\\d+)?$");
		// 字符串不为空,不排除0,如果把0除外,后面不要加?,用"\\d+(.\\d+)$"就可以了;
		if (str.length() > 0)
		{
			Matcher matcher = pattern.matcher(str);
			if (matcher.matches() == true)
			{
				flag = true;
				// 多于两个字符时,小数最多以一个0开头;
				if (str.length() > 1)
				{
					if ((str.charAt(0) == '0') && (str.charAt(1) == '0'))
					{
						flag = false;
					}

				}
			}
		}
		return flag;
	}

	/**
	 * 
	 * isInteger(验证是否为非负整数,>= 0;)   
	 * 
	 * TODO(Here describes this method to be suitable the condition - to be possible to elect)
	 * 
	 * @param str
	 * @return 
	 * 
	 * boolean
	 */
	public static boolean isInteger(String str)
	{
		boolean flag = false;
		Pattern pattern = Pattern.compile("\\d*$");
		// 字符串不为空;
		if (str.length() > 0)
		{
			Matcher matcher = pattern.matcher(str);
			if (matcher.matches() == true)
			{
				flag = true;
				// 除去以0开头的情况;
				if (str.length() > 1)
				{
					if ((str.charAt(0) == '0'))
					{
						flag = false;
					}
				}
			}
		}
		return flag;
	}

	/**
	 * 
	 * isZeroToOne(验证是否是0-1的小数,包括0和1;)   
	 * 
	 * TODO(Here describes this method to be suitable the condition - to be possible to elect)
	 * 
	 * @param str
	 * @return 
	 * 
	 * boolean
	 */
	public static boolean isZeroToOne(String str)
	{
		boolean flag = false;
		// 字符串不为空;
		Pattern pattern = Pattern.compile("^[0]{1}(.\\d+)?$");
		if (str.length() > 0)
		{
			Matcher matcher = pattern.matcher(str);
			// 1、以0开头,符合规则的情况;
			if (matcher.matches() == true)
			{
				flag = true;
			}
			// 2、输入的是1时的情况;
			if (str.length() == 1)
			{
				if (str.charAt(0) == '1')
				{
					flag = true;
				}
			}
			// 3、输入的是1.0时的情况;
			if (str.length() > 2)
			{
				if (str.charAt(2) == '0')
				{
					flag = true;
				}
			}
		}
		return flag;
	}

	/**
	 * 
	 * isTelephoneNumber(验证电话号码;)   
	 * 
	 * TODO(Here describes this method to be suitable the condition - to be possible to elect)
	 * 
	 * @param str
	 * @return 
	 * 
	 * boolean
	 */
	public static boolean isTelephoneNumber(String str)
	{
		boolean flag = false;
		// 字符串不为空;
		Pattern pattern = Pattern.compile("^(\\d{3,4}-)(\\d{7,8})$");
		Pattern pattern1 = Pattern.compile("^[1]{1}(\\d{10})$");
		if (str.length() > 0)
		{
			Matcher matcher = pattern.matcher(str);
			Matcher matcher2 = pattern1.matcher(str);
			// 1、验证固定电话;
			if (matcher.matches() == true)
			{
				flag = true;
			}
			// 2、输入的是11位手机时的情况;
			if (matcher2.matches() == true)
			{
				flag = true;
			}

		}
		return flag;
	}
}
