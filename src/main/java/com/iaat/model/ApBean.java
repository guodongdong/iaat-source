package com.iaat.model;

import java.util.Date;

/**    
 * @name ApBean
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
/**    
 * @name ApBean
 * 
 * @description CLASS_DESCRIPTION
 * 
 * MORE_INFORMATION
 * 
 * @author JackGuO
 * 
 * @since 2013-7-4
 *       
 * @version 1.0
 */
public class ApBean implements java.io.Serializable {


	
	private static final long serialVersionUID = -2442555059525425425L;
	private Integer apFk;
	private Integer operatorFk;
	private String ap;
	private Integer sequence;
	private Short isAvailable;
	private String PAp;
	private Date updateTime;
	private String description;

	

	public Integer getApFk() {
		return this.apFk;
	}

	public void setApFk(Integer apFk) {
		this.apFk = apFk;
	}

	
	public Integer getOperatorFk() {
		return operatorFk;
	}

	public void setOperatorFk(Integer operatorFk) {
		this.operatorFk = operatorFk;
	}

	public String getAp() {
		return this.ap;
	}

	public void setAp(String ap) {
		this.ap = ap;
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

	public String getPAp() {
		return this.PAp;
	}

	public void setPAp(String PAp) {
		this.PAp = PAp;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
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
		if (!(other instanceof ApBean))
			return false;
		ApBean castOther = (ApBean) other;

		return ((this.getApFk() == castOther.getApFk()) || (this.getApFk() != null
				&& castOther.getApFk() != null && this.getApFk().equals(
				castOther.getApFk())))
				&& ((this.getAp() == castOther.getAp()) || (this.getAp() != null
						&& castOther.getAp() != null && this.getAp().equals(
						castOther.getAp())))
				&& ((this.getSequence() == castOther.getSequence()) || (this
						.getSequence() != null
						&& castOther.getSequence() != null && this
						.getSequence().equals(castOther.getSequence())))
				&& ((this.getIsAvailable() == castOther.getIsAvailable()) || (this
						.getIsAvailable() != null
						&& castOther.getIsAvailable() != null && this
						.getIsAvailable().equals(castOther.getIsAvailable())))
				&& ((this.getPAp() == castOther.getPAp()) || (this.getPAp() != null
						&& castOther.getPAp() != null && this.getPAp().equals(
						castOther.getPAp())))
				&& ((this.getUpdateTime() == castOther.getUpdateTime()) || (this
						.getUpdateTime() != null
						&& castOther.getUpdateTime() != null && this
						.getUpdateTime().equals(castOther.getUpdateTime())))
				&& ((this.getDescription() == castOther.getDescription()) || (this
						.getDescription() != null
						&& castOther.getDescription() != null && this
						.getDescription().equals(castOther.getDescription())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getApFk() == null ? 0 : this.getApFk().hashCode());
		result = 37 * result + (getAp() == null ? 0 : this.getAp().hashCode());
		result = 37 * result
				+ (getSequence() == null ? 0 : this.getSequence().hashCode());
		result = 37
				* result
				+ (getIsAvailable() == null ? 0 : this.getIsAvailable()
						.hashCode());
		result = 37 * result
				+ (getPAp() == null ? 0 : this.getPAp().hashCode());
		result = 37
				* result
				+ (getUpdateTime() == null ? 0 : this.getUpdateTime()
						.hashCode());
		result = 37
				* result
				+ (getDescription() == null ? 0 : this.getDescription()
						.hashCode());
		return result;
	}

}