package com.iaat.model;

import java.util.Date;


public class OperatorBean implements java.io.Serializable {

	
	private static final long serialVersionUID = 5410648640078136481L;
	
	private Integer operatorFk;
	private String operator;
	private String code;
	private Integer sequence;
	private Short isAvailable;
	private String POperator;
	private String PCode;
	private Date updateTime;
	private String description;

	
	public Integer getOperatorFk() {
		return this.operatorFk;
	}

	public void setOperatorFk(Integer operatorFk) {
		this.operatorFk = operatorFk;
	}

	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public String getPOperator() {
		return this.POperator;
	}

	public void setPOperator(String POperator) {
		this.POperator = POperator;
	}

	public String getPCode() {
		return this.PCode;
	}

	public void setPCode(String PCode) {
		this.PCode = PCode;
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
		if (!(other instanceof OperatorBean))
			return false;
		OperatorBean castOther = (OperatorBean) other;

		return ((this.getOperatorFk() == castOther.getOperatorFk()) || (this
				.getOperatorFk() != null
				&& castOther.getOperatorFk() != null && this.getOperatorFk()
				.equals(castOther.getOperatorFk())))
				&& ((this.getOperator() == castOther.getOperator()) || (this
						.getOperator() != null
						&& castOther.getOperator() != null && this
						.getOperator().equals(castOther.getOperator())))
				&& ((this.getCode() == castOther.getCode()) || (this.getCode() != null
						&& castOther.getCode() != null && this.getCode()
						.equals(castOther.getCode())))
				&& ((this.getSequence() == castOther.getSequence()) || (this
						.getSequence() != null
						&& castOther.getSequence() != null && this
						.getSequence().equals(castOther.getSequence())))
				&& ((this.getIsAvailable() == castOther.getIsAvailable()) || (this
						.getIsAvailable() != null
						&& castOther.getIsAvailable() != null && this
						.getIsAvailable().equals(castOther.getIsAvailable())))
				&& ((this.getPOperator() == castOther.getPOperator()) || (this
						.getPOperator() != null
						&& castOther.getPOperator() != null && this
						.getPOperator().equals(castOther.getPOperator())))
				&& ((this.getPCode() == castOther.getPCode()) || (this
						.getPCode() != null
						&& castOther.getPCode() != null && this.getPCode()
						.equals(castOther.getPCode())))
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

		result = 37
				* result
				+ (getOperatorFk() == null ? 0 : this.getOperatorFk()
						.hashCode());
		result = 37 * result
				+ (getOperator() == null ? 0 : this.getOperator().hashCode());
		result = 37 * result
				+ (getCode() == null ? 0 : this.getCode().hashCode());
		result = 37 * result
				+ (getSequence() == null ? 0 : this.getSequence().hashCode());
		result = 37
				* result
				+ (getIsAvailable() == null ? 0 : this.getIsAvailable()
						.hashCode());
		result = 37 * result
				+ (getPOperator() == null ? 0 : this.getPOperator().hashCode());
		result = 37 * result
				+ (getPCode() == null ? 0 : this.getPCode().hashCode());
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