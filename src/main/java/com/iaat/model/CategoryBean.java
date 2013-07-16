package com.iaat.model;

import java.util.Date;


/**    
 * @name CategoryBean
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
public class CategoryBean implements java.io.Serializable {

	private static final long serialVersionUID = -7143098864924668319L;
	private Integer categoryFk;
	private String categoryKey;
	private String categoryValue;
	private Integer sequence;
	private Short isAvailable;
	private String PCategoryKey;
	private String PCategoryValue;
	private Date updateTime;
	private String description;




	public Integer getCategoryFk() {
		return this.categoryFk;
	}

	public void setCategoryFk(Integer categoryFk) {
		this.categoryFk = categoryFk;
	}

	public String getCategoryKey() {
		return this.categoryKey;
	}

	public void setCategoryKey(String categoryKey) {
		this.categoryKey = categoryKey;
	}

	public String getCategoryValue() {
		return this.categoryValue;
	}

	public void setCategoryValue(String categoryValue) {
		this.categoryValue = categoryValue;
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

	public String getPCategoryKey() {
		return this.PCategoryKey;
	}

	public void setPCategoryKey(String PCategoryKey) {
		this.PCategoryKey = PCategoryKey;
	}

	public String getPCategoryValue() {
		return this.PCategoryValue;
	}

	public void setPCategoryValue(String PCategoryValue) {
		this.PCategoryValue = PCategoryValue;
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
		if (!(other instanceof CategoryBean))
			return false;
		CategoryBean castOther = (CategoryBean) other;

		return ((this.getCategoryFk() == castOther.getCategoryFk()) || (this
				.getCategoryFk() != null
				&& castOther.getCategoryFk() != null && this.getCategoryFk()
				.equals(castOther.getCategoryFk())))
				&& ((this.getCategoryKey() == castOther.getCategoryKey()) || (this
						.getCategoryKey() != null
						&& castOther.getCategoryKey() != null && this
						.getCategoryKey().equals(castOther.getCategoryKey())))
				&& ((this.getCategoryValue() == castOther.getCategoryValue()) || (this
						.getCategoryValue() != null
						&& castOther.getCategoryValue() != null && this
						.getCategoryValue()
						.equals(castOther.getCategoryValue())))
				&& ((this.getSequence() == castOther.getSequence()) || (this
						.getSequence() != null
						&& castOther.getSequence() != null && this
						.getSequence().equals(castOther.getSequence())))
				&& ((this.getIsAvailable() == castOther.getIsAvailable()) || (this
						.getIsAvailable() != null
						&& castOther.getIsAvailable() != null && this
						.getIsAvailable().equals(castOther.getIsAvailable())))
				&& ((this.getPCategoryKey() == castOther.getPCategoryKey()) || (this
						.getPCategoryKey() != null
						&& castOther.getPCategoryKey() != null && this
						.getPCategoryKey().equals(castOther.getPCategoryKey())))
				&& ((this.getPCategoryValue() == castOther.getPCategoryValue()) || (this
						.getPCategoryValue() != null
						&& castOther.getPCategoryValue() != null && this
						.getPCategoryValue().equals(
								castOther.getPCategoryValue())))
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
				+ (getCategoryFk() == null ? 0 : this.getCategoryFk()
						.hashCode());
		result = 37
				* result
				+ (getCategoryKey() == null ? 0 : this.getCategoryKey()
						.hashCode());
		result = 37
				* result
				+ (getCategoryValue() == null ? 0 : this.getCategoryValue()
						.hashCode());
		result = 37 * result
				+ (getSequence() == null ? 0 : this.getSequence().hashCode());
		result = 37
				* result
				+ (getIsAvailable() == null ? 0 : this.getIsAvailable()
						.hashCode());
		result = 37
				* result
				+ (getPCategoryKey() == null ? 0 : this.getPCategoryKey()
						.hashCode());
		result = 37
				* result
				+ (getPCategoryValue() == null ? 0 : this.getPCategoryValue()
						.hashCode());
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