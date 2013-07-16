package com.iaat.model;

import java.util.Date;


/**    
 * @name RegionBean
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
public class RegionBean implements java.io.Serializable {

	
	private static final long serialVersionUID = -6664576479125247677L;
	private Integer regionFk;
	private String region;
	private String code;
	private Integer sequence;
	private Short isAvailable;
	private String PRegion;
	private String PCode;
	private Date updateTime;
	private String description;

	// Constructors



	// Property accessors

	public Integer getRegionFk() {
		return this.regionFk;
	}

	public void setRegionFk(Integer regionFk) {
		this.regionFk = regionFk;
	}

	public String getRegion() {
		return this.region;
	}

	public void setRegion(String region) {
		this.region = region;
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

	public String getPRegion() {
		return this.PRegion;
	}

	public void setPRegion(String PRegion) {
		this.PRegion = PRegion;
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
		if (!(other instanceof RegionBean))
			return false;
		RegionBean castOther = (RegionBean) other;

		return ((this.getRegionFk() == castOther.getRegionFk()) || (this
				.getRegionFk() != null
				&& castOther.getRegionFk() != null && this.getRegionFk()
				.equals(castOther.getRegionFk())))
				&& ((this.getRegion() == castOther.getRegion()) || (this
						.getRegion() != null
						&& castOther.getRegion() != null && this.getRegion()
						.equals(castOther.getRegion())))
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
				&& ((this.getPRegion() == castOther.getPRegion()) || (this
						.getPRegion() != null
						&& castOther.getPRegion() != null && this.getPRegion()
						.equals(castOther.getPRegion())))
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
		result = 37 * result
				+ (getRegionFk() == null ? 0 : this.getRegionFk().hashCode());
		result = 37 * result
				+ (getRegion() == null ? 0 : this.getRegion().hashCode());
		result = 37 * result
				+ (getCode() == null ? 0 : this.getCode().hashCode());
		result = 37 * result
				+ (getSequence() == null ? 0 : this.getSequence().hashCode());
		result = 37
				* result
				+ (getIsAvailable() == null ? 0 : this.getIsAvailable()
						.hashCode());
		result = 37 * result
				+ (getPRegion() == null ? 0 : this.getPRegion().hashCode());
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