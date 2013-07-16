package com.iaat.model;

import java.util.Date;


/**    
 * @name PlatformBean
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
public class PlatformBean implements java.io.Serializable {


	private static final long serialVersionUID = 2587858506125615553L;
	private Integer platformFk;
	private String platform;
	private Integer sequence;
	private Short isAvailable;
	private String PPlatform;
	private Date updateTime;
	private String description;



	public Integer getPlatformFk() {
		return this.platformFk;
	}

	public void setPlatformFk(Integer platformFk) {
		this.platformFk = platformFk;
	}

	public String getPlatform() {
		return this.platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
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

	public String getPPlatform() {
		return this.PPlatform;
	}

	public void setPPlatform(String PPlatform) {
		this.PPlatform = PPlatform;
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
		if (!(other instanceof PlatformBean))
			return false;
		PlatformBean castOther = (PlatformBean) other;

		return ((this.getPlatformFk() == castOther.getPlatformFk()) || (this
				.getPlatformFk() != null
				&& castOther.getPlatformFk() != null && this.getPlatformFk()
				.equals(castOther.getPlatformFk())))
				&& ((this.getPlatform() == castOther.getPlatform()) || (this
						.getPlatform() != null
						&& castOther.getPlatform() != null && this
						.getPlatform().equals(castOther.getPlatform())))
				&& ((this.getSequence() == castOther.getSequence()) || (this
						.getSequence() != null
						&& castOther.getSequence() != null && this
						.getSequence().equals(castOther.getSequence())))
				&& ((this.getIsAvailable() == castOther.getIsAvailable()) || (this
						.getIsAvailable() != null
						&& castOther.getIsAvailable() != null && this
						.getIsAvailable().equals(castOther.getIsAvailable())))
				&& ((this.getPPlatform() == castOther.getPPlatform()) || (this
						.getPPlatform() != null
						&& castOther.getPPlatform() != null && this
						.getPPlatform().equals(castOther.getPPlatform())))
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
				+ (getPlatformFk() == null ? 0 : this.getPlatformFk()
						.hashCode());
		result = 37 * result
				+ (getPlatform() == null ? 0 : this.getPlatform().hashCode());
		result = 37 * result
				+ (getSequence() == null ? 0 : this.getSequence().hashCode());
		result = 37
				* result
				+ (getIsAvailable() == null ? 0 : this.getIsAvailable()
						.hashCode());
		result = 37 * result
				+ (getPPlatform() == null ? 0 : this.getPPlatform().hashCode());
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