package com.iaat.model;

import java.util.Date;

/**    
 * @name ImeiBean
 * 
 * @description CLASS_DESCRIPTION
 * 
 * MORE_INFORMATION
 * 
 * @author JackGuo
 * 
 * @since 2013-6-24
 *       
 * @version 1.0
 */
public class ImeiBean implements java.io.Serializable {


	
	private static final long serialVersionUID = 5164519231193480814L;
	private Long imeiFk;
	private String imeiNum;
	private Date createTime;


	

	public Long getImeiFk() {
		return this.imeiFk;
	}

	public void setImeiFk(Long imeiFk) {
		this.imeiFk = imeiFk;
	}

	public String getImeiNum() {
		return this.imeiNum;
	}

	public void setImeiNum(String imeiNum) {
		this.imeiNum = imeiNum;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}