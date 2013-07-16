package com.iaat.json.heat;

import com.iaat.util.NumberUtil;
import com.iaat.util.ValidateUtils;

/**
 * 
 * @name OperatorRatioBean
 * 
 * @description CLASS_DESCRIPTION
 * 
 * MORE_INFORMATION
 * 
 * @author zhaozhi
 * 
 * @since 2013-7-2
 *       
 * @version 1.0
 */
public class OperatorRatioBean {
	
	private String name;
	
	private Long count = 0L;
	
	private Float ratio = 0F;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public Float getRatio() {
		return ratio;
	}

	public void setRatio(Float ratio) {
		String r = NumberUtil.getFormatData(ratio);
		if (ValidateUtils.isNotNull(r)) {
			this.ratio = Float.valueOf(r);
		}
	}
	
}
