package com.nokia.ads.common.util;

import java.util.ArrayList;
import java.util.List;

/**
 * A utility class to do base16 - base64 conversions
 *
 */
public class CodecDbId 
{
	private static final String base64 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_-";

	public static String encodeId(String input) throws Exception
	{
		List<String> splitInput = new ArrayList<String>();
		int beginIndex = 0;
		int endIndex = 3;
		int stepSize = 3;
		StringBuilder sb = new StringBuilder();
		
		while (endIndex <= input.length())
		{
			String split = input.substring(beginIndex, endIndex);
			splitInput.add(split);
			beginIndex += stepSize;
			endIndex += stepSize;
		}			
		
		for (String s : splitInput)
		{
			String encodedS = encodeThree(s);
			sb.append(encodedS);
		}
		return sb.toString();
	}
	
	
	public static String encodeThree(String three) throws Exception
	{
	    if (three.length() != 3)
	    {
	    	throw new Exception("Input string not 3 bytes long");
	    }
	 
	    /* convert to decimal */
	    int num = Integer.parseInt(three, 16);

	    int firstHalf = num >> 6;
	    int secondHalf = num & 0x3f;
	    
	    /* Then convert to base 64 */
	    StringBuilder sb = new StringBuilder();
	    
	    sb.append(base64.charAt(firstHalf));
	    sb.append(base64.charAt(secondHalf));
	    
	    String output = sb.toString();
	    
	    if (output.length() != 2)
	    {
	    	throw new Exception("Output string not 2 bytes long");
	    }
	    
	    return output; 
	}
	
	public static String decodeId(String input) throws Exception
	{
		List<String> splitInput = new ArrayList<String>();
		int beginIndex = 0;
		int endIndex = 2;
		int stepSize = 2;
		StringBuilder sb = new StringBuilder();
		
		while (endIndex <= input.length())
		{
			String split = input.substring(beginIndex, endIndex);
			splitInput.add(split);
			beginIndex += stepSize;
			endIndex += stepSize;
		}			
		
		for (String s : splitInput)
		{
			String decodedS = decodeTwo(s);
			sb.append(decodedS);
		}
		return sb.toString();
	}
	
	public static String decodeTwo(String two) throws Exception
	{
	    if (two.length() != 2)
	    {
	    	throw new Exception("Input string not 3 bytes long");
	    }
	 
	    /* convert to decimal */	    
	    char firstChar = two.charAt(0);
	    char secondChar = two.charAt(1);
	    
	    int firstIndex = base64.indexOf(firstChar);
	    
	    if (firstIndex == -1)
	    {
	    	throw new Exception("firstIndex not found in base64 string");
	    }
	    	
	    int secondIndex = base64.indexOf(secondChar);
	    
	    if (secondIndex == -1)
	    {
	    	throw new Exception("secondIndex not found in base64 string");
	    }
	    
	    int decimal = firstIndex * 64 + secondIndex;
	    
	    /* Then convert to base 16 */
	    String hexStr = Integer.toHexString(decimal);
	    
	    int padLength = 3 - hexStr.length();
	    
	    StringBuilder sb = new StringBuilder();
	    
	    for (int i = 0; i < padLength; i++)
	    {
	    	sb.append('0');
	    }
	    
	    sb.append(hexStr);
	    
	    String output = sb.toString();
	    
	    if (output.length() != 3)
	    {
	    	throw new Exception("outputIndex not 3 bytes long");
	    }
	    
	    return output;
	}
}
