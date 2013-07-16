package com.iaat.model;

import java.util.Date;


/**    
 * @name DTerminalId
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
public class TerminalBean implements java.io.Serializable {
	
	private static final long serialVersionUID = 7506085446675810104L;
	private Integer terminalFk;
	private Integer platformFK;
	private String terminal;
	private Integer sequence;
	private Short isAvailable;
	private String PTerminal;
	private Date updateTime;
	private Date appearTime;
	private String description;

	
	public Integer getTerminalFk() {
		return this.terminalFk;
	}

	public void setTerminalFk(Integer terminalFk) {
		this.terminalFk = terminalFk;
	}
	

	public Integer getPlatformFK() {
		return platformFK;
	}

	public void setPlatformFK(Integer platformFK) {
		this.platformFK = platformFK;
	}

	public String getTerminal() {
		return this.terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public Integer getSequence() {
		return this.sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public Short getIsAvailable() {
		return this.isAvailable;
	}

	public void setIsAvailable(Short isAvailable) {
		this.isAvailable = isAvailable;
	}

	public String getPTerminal() {
		return this.PTerminal;
	}

	public void setPTerminal(String PTerminal) {
		this.PTerminal = PTerminal;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getAppearTime() {
		return this.appearTime;
	}

	public void setAppearTime(Date appearTime) {
		this.appearTime = appearTime;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TerminalBean))
			return false;
		TerminalBean castOther = (TerminalBean) other;

		return ((this.getTerminalFk() == castOther.getTerminalFk()) || (this
				.getTerminalFk() != null
				&& castOther.getTerminalFk() != null && this.getTerminalFk()
				.equals(castOther.getTerminalFk())))
				&& ((this.getTerminal() == castOther.getTerminal()) || (this
						.getTerminal() != null
						&& castOther.getTerminal() != null && this
						.getTerminal().equals(castOther.getTerminal())))
				&& ((this.getSequence() == castOther.getSequence()) || (this
						.getSequence() != null
						&& castOther.getSequence() != null && this
						.getSequence().equals(castOther.getSequence())))
				&& ((this.getIsAvailable() == castOther.getIsAvailable()) || (this
						.getIsAvailable() != null
						&& castOther.getIsAvailable() != null && this
						.getIsAvailable().equals(castOther.getIsAvailable())))
				&& ((this.getPTerminal() == castOther.getPTerminal()) || (this
						.getPTerminal() != null
						&& castOther.getPTerminal() != null && this
						.getPTerminal().equals(castOther.getPTerminal())))
				&& ((this.getUpdateTime() == castOther.getUpdateTime()) || (this
						.getUpdateTime() != null
						&& castOther.getUpdateTime() != null && this
						.getUpdateTime().equals(castOther.getUpdateTime())))
				&& ((this.getAppearTime() == castOther.getAppearTime()) || (this
						.getAppearTime() != null
						&& castOther.getAppearTime() != null && this
						.getAppearTime().equals(castOther.getAppearTime())))
				&& ((this.getDescription() == castOther.getDescription()) || (this
						.getDescription() != null
						&& castOther.getDescription() != null && this
						.getDescription().equals(castOther.getDescription())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getTerminalFk() == null ? 0 : this.getTerminalFk()
						.hashCode());
		result = 37 * result
				+ (getTerminal() == null ? 0 : this.getTerminal().hashCode());
		result = 37 * result
				+ (getSequence() == null ? 0 : this.getSequence().hashCode());
		result = 37
				* result
				+ (getIsAvailable() == null ? 0 : this.getIsAvailable()
						.hashCode());
		result = 37 * result
				+ (getPTerminal() == null ? 0 : this.getPTerminal().hashCode());
		result = 37
				* result
				+ (getUpdateTime() == null ? 0 : this.getUpdateTime()
						.hashCode());
		result = 37
				* result
				+ (getAppearTime() == null ? 0 : this.getAppearTime()
						.hashCode());
		result = 37
				* result
				+ (getDescription() == null ? 0 : this.getDescription()
						.hashCode());
		return result;
	}

}