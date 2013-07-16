package com.iaat.model;


/**    
 * @name OuterUserSituationBean
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
public class OuterUserSituationBean implements java.io.Serializable {


	
	private static final long serialVersionUID = 6958193386511869747L;
	private String uid;
	private Integer wapPv;
	private Integer webPv;
	private Integer isNewUser;
	private Integer loginNum;
	private Integer intervalTime;
	private Integer apFk;
	private Integer platformFk;
	private Integer operatorFk;
	private Long imeiFk;
	private Integer dateFk;
	private Integer channelFk;
	private Integer categoryFk;
	private Integer regionFk;
	private Integer terminalFk;

	public String getUid() {
		return this.uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Integer getWapPv() {
		return this.wapPv;
	}

	public void setWapPv(Integer wapPv) {
		this.wapPv = wapPv;
	}

	public Integer getWebPv() {
		return this.webPv;
	}

	public void setWebPv(Integer webPv) {
		this.webPv = webPv;
	}

	public Integer getIsNewUser() {
		return this.isNewUser;
	}

	public void setIsNewUser(Integer isNewUser) {
		this.isNewUser = isNewUser;
	}

	public Integer getLoginNum() {
		return this.loginNum;
	}

	public void setLoginNum(Integer loginNum) {
		this.loginNum = loginNum;
	}

	public Integer getIntervalTime() {
		return this.intervalTime;
	}

	public void setIntervalTime(Integer intervalTime) {
		this.intervalTime = intervalTime;
	}

	public Integer getApFk() {
		return this.apFk;
	}

	public void setApFk(Integer apFk) {
		this.apFk = apFk;
	}

	public Integer getPlatformFk() {
		return this.platformFk;
	}

	public void setPlatformFk(Integer platformFk) {
		this.platformFk = platformFk;
	}

	public Integer getOperatorFk() {
		return this.operatorFk;
	}

	public void setOperatorFk(Integer operatorFk) {
		this.operatorFk = operatorFk;
	}

	public Long getImeiFk() {
		return this.imeiFk;
	}

	public void setImeiFk(Long imeiFk) {
		this.imeiFk = imeiFk;
	}

	public Integer getDateFk() {
		return this.dateFk;
	}

	public void setDateFk(Integer dateFk) {
		this.dateFk = dateFk;
	}

	public Integer getChannelFk() {
		return this.channelFk;
	}

	public void setChannelFk(Integer channelFk) {
		this.channelFk = channelFk;
	}

	public Integer getCategoryFk() {
		return this.categoryFk;
	}

	public void setCategoryFk(Integer categoryFk) {
		this.categoryFk = categoryFk;
	}

	public Integer getRegionFk() {
		return this.regionFk;
	}

	public void setRegionFk(Integer regionFk) {
		this.regionFk = regionFk;
	}

	public Integer getTerminalFk() {
		return this.terminalFk;
	}

	public void setTerminalFk(Integer terminalFk) {
		this.terminalFk = terminalFk;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof OuterUserSituationBean))
			return false;
		OuterUserSituationBean castOther = (OuterUserSituationBean) other;

		return ((this.getUid() == castOther.getUid()) || (this.getUid() != null
				&& castOther.getUid() != null && this.getUid().equals(
				castOther.getUid())))
				&& ((this.getWapPv() == castOther.getWapPv()) || (this
						.getWapPv() != null
						&& castOther.getWapPv() != null && this.getWapPv()
						.equals(castOther.getWapPv())))
				&& ((this.getWebPv() == castOther.getWebPv()) || (this
						.getWebPv() != null
						&& castOther.getWebPv() != null && this.getWebPv()
						.equals(castOther.getWebPv())))
				&& ((this.getIsNewUser() == castOther.getIsNewUser()) || (this
						.getIsNewUser() != null
						&& castOther.getIsNewUser() != null && this
						.getIsNewUser().equals(castOther.getIsNewUser())))
				&& ((this.getLoginNum() == castOther.getLoginNum()) || (this
						.getLoginNum() != null
						&& castOther.getLoginNum() != null && this
						.getLoginNum().equals(castOther.getLoginNum())))
				&& ((this.getIntervalTime() == castOther.getIntervalTime()) || (this
						.getIntervalTime() != null
						&& castOther.getIntervalTime() != null && this
						.getIntervalTime().equals(castOther.getIntervalTime())))
				&& ((this.getApFk() == castOther.getApFk()) || (this.getApFk() != null
						&& castOther.getApFk() != null && this.getApFk()
						.equals(castOther.getApFk())))
				&& ((this.getPlatformFk() == castOther.getPlatformFk()) || (this
						.getPlatformFk() != null
						&& castOther.getPlatformFk() != null && this
						.getPlatformFk().equals(castOther.getPlatformFk())))
				&& ((this.getOperatorFk() == castOther.getOperatorFk()) || (this
						.getOperatorFk() != null
						&& castOther.getOperatorFk() != null && this
						.getOperatorFk().equals(castOther.getOperatorFk())))
				&& ((this.getImeiFk() == castOther.getImeiFk()) || (this
						.getImeiFk() != null
						&& castOther.getImeiFk() != null && this.getImeiFk()
						.equals(castOther.getImeiFk())))
				&& ((this.getDateFk() == castOther.getDateFk()) || (this
						.getDateFk() != null
						&& castOther.getDateFk() != null && this.getDateFk()
						.equals(castOther.getDateFk())))
				&& ((this.getChannelFk() == castOther.getChannelFk()) || (this
						.getChannelFk() != null
						&& castOther.getChannelFk() != null && this
						.getChannelFk().equals(castOther.getChannelFk())))
				&& ((this.getCategoryFk() == castOther.getCategoryFk()) || (this
						.getCategoryFk() != null
						&& castOther.getCategoryFk() != null && this
						.getCategoryFk().equals(castOther.getCategoryFk())))
				&& ((this.getRegionFk() == castOther.getRegionFk()) || (this
						.getRegionFk() != null
						&& castOther.getRegionFk() != null && this
						.getRegionFk().equals(castOther.getRegionFk())))
				&& ((this.getTerminalFk() == castOther.getTerminalFk()) || (this
						.getTerminalFk() != null
						&& castOther.getTerminalFk() != null && this
						.getTerminalFk().equals(castOther.getTerminalFk())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getUid() == null ? 0 : this.getUid().hashCode());
		result = 37 * result
				+ (getWapPv() == null ? 0 : this.getWapPv().hashCode());
		result = 37 * result
				+ (getWebPv() == null ? 0 : this.getWebPv().hashCode());
		result = 37 * result
				+ (getIsNewUser() == null ? 0 : this.getIsNewUser().hashCode());
		result = 37 * result
				+ (getLoginNum() == null ? 0 : this.getLoginNum().hashCode());
		result = 37
				* result
				+ (getIntervalTime() == null ? 0 : this.getIntervalTime()
						.hashCode());
		result = 37 * result
				+ (getApFk() == null ? 0 : this.getApFk().hashCode());
		result = 37
				* result
				+ (getPlatformFk() == null ? 0 : this.getPlatformFk()
						.hashCode());
		result = 37
				* result
				+ (getOperatorFk() == null ? 0 : this.getOperatorFk()
						.hashCode());
		result = 37 * result
				+ (getImeiFk() == null ? 0 : this.getImeiFk().hashCode());
		result = 37 * result
				+ (getDateFk() == null ? 0 : this.getDateFk().hashCode());
		result = 37 * result
				+ (getChannelFk() == null ? 0 : this.getChannelFk().hashCode());
		result = 37
				* result
				+ (getCategoryFk() == null ? 0 : this.getCategoryFk()
						.hashCode());
		result = 37 * result
				+ (getRegionFk() == null ? 0 : this.getRegionFk().hashCode());
		result = 37
				* result
				+ (getTerminalFk() == null ? 0 : this.getTerminalFk()
						.hashCode());
		return result;
	}

}