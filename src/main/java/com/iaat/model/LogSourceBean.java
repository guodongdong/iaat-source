package com.iaat.model;


/**    
 * @name LogSourceBean
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
public class LogSourceBean implements java.io.Serializable {
	
	private static final long serialVersionUID = -1209454754833375190L;
	private Long logId;
	private String logName;
	public Long getLogId() {
		return logId;
	}
	public void setLogId(Long logId) {
		this.logId = logId;
	}
	public String getLogName() {
		return logName;
	}
	public void setLogName(String logName) {
		this.logName = logName;
	}
	
}
