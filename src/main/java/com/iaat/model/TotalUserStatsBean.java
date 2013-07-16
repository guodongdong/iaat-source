package com.iaat.model;


/**    
 * @name TotalUserStatsBean
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
public class TotalUserStatsBean implements java.io.Serializable {

	
	private static final long serialVersionUID = 6529492916122784785L;
	private String uid;
	private Integer wapPv;
	private Integer webPv;
	private Integer uv;
	private Integer increaseUser;
	private Integer onceUser;
	private Integer loginUser;
	private Float avgUserLogin;
	private Float avgUseAccessr;
	private Float avgLastTime;
	private Integer oldUser;
	private Integer totalUser;
	private Integer au;
	private Integer apFk;
	private Integer terminalFk;
	private Integer channelFk;
	private Integer platformFk;
	private Integer dateFk;
	private Integer regionFk;
	private Integer operatorFk;


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

	public Integer getUv() {
		return this.uv;
	}

	public void setUv(Integer uv) {
		this.uv = uv;
	}

	public Integer getIncreaseUser() {
		return this.increaseUser;
	}

	public void setIncreaseUser(Integer increaseUser) {
		this.increaseUser = increaseUser;
	}

	public Integer getOnceUser() {
		return this.onceUser;
	}

	public void setOnceUser(Integer onceUser) {
		this.onceUser = onceUser;
	}

	public Integer getLoginUser() {
		return this.loginUser;
	}

	public void setLoginUser(Integer loginUser) {
		this.loginUser = loginUser;
	}

	public Float getAvgUserLogin() {
		return this.avgUserLogin;
	}

	public void setAvgUserLogin(Float avgUserLogin) {
		this.avgUserLogin = avgUserLogin;
	}

	public Float getAvgUseAccessr() {
		return this.avgUseAccessr;
	}

	public void setAvgUseAccessr(Float avgUseAccessr) {
		this.avgUseAccessr = avgUseAccessr;
	}

	public Float getAvgLastTime() {
		return this.avgLastTime;
	}

	public void setAvgLastTime(Float avgLastTime) {
		this.avgLastTime = avgLastTime;
	}

	public Integer getOldUser() {
		return this.oldUser;
	}

	public void setOldUser(Integer oldUser) {
		this.oldUser = oldUser;
	}

	public Integer getTotalUser() {
		return this.totalUser;
	}

	public void setTotalUser(Integer totalUser) {
		this.totalUser = totalUser;
	}

	public Integer getAu() {
		return this.au;
	}

	public void setAu(Integer au) {
		this.au = au;
	}

	public Integer getApFk() {
		return this.apFk;
	}

	public void setApFk(Integer apFk) {
		this.apFk = apFk;
	}

	public Integer getTerminalFk() {
		return this.terminalFk;
	}

	public void setTerminalFk(Integer terminalFk) {
		this.terminalFk = terminalFk;
	}

	public Integer getChannelFk() {
		return this.channelFk;
	}

	public void setChannelFk(Integer channelFk) {
		this.channelFk = channelFk;
	}

	public Integer getPlatformFk() {
		return this.platformFk;
	}

	public void setPlatformFk(Integer platformFk) {
		this.platformFk = platformFk;
	}

	public Integer getDateFk() {
		return this.dateFk;
	}

	public void setDateFk(Integer dateFk) {
		this.dateFk = dateFk;
	}

	public Integer getRegionFk() {
		return this.regionFk;
	}

	public void setRegionFk(Integer regionFk) {
		this.regionFk = regionFk;
	}

	public Integer getOperatorFk() {
		return this.operatorFk;
	}

	public void setOperatorFk(Integer operatorFk) {
		this.operatorFk = operatorFk;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TotalUserStatsBean))
			return false;
		TotalUserStatsBean castOther = (TotalUserStatsBean) other;

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
				&& ((this.getUv() == castOther.getUv()) || (this.getUv() != null
						&& castOther.getUv() != null && this.getUv().equals(
						castOther.getUv())))
				&& ((this.getIncreaseUser() == castOther.getIncreaseUser()) || (this
						.getIncreaseUser() != null
						&& castOther.getIncreaseUser() != null && this
						.getIncreaseUser().equals(castOther.getIncreaseUser())))
				&& ((this.getOnceUser() == castOther.getOnceUser()) || (this
						.getOnceUser() != null
						&& castOther.getOnceUser() != null && this
						.getOnceUser().equals(castOther.getOnceUser())))
				&& ((this.getLoginUser() == castOther.getLoginUser()) || (this
						.getLoginUser() != null
						&& castOther.getLoginUser() != null && this
						.getLoginUser().equals(castOther.getLoginUser())))
				&& ((this.getAvgUserLogin() == castOther.getAvgUserLogin()) || (this
						.getAvgUserLogin() != null
						&& castOther.getAvgUserLogin() != null && this
						.getAvgUserLogin().equals(castOther.getAvgUserLogin())))
				&& ((this.getAvgUseAccessr() == castOther.getAvgUseAccessr()) || (this
						.getAvgUseAccessr() != null
						&& castOther.getAvgUseAccessr() != null && this
						.getAvgUseAccessr()
						.equals(castOther.getAvgUseAccessr())))
				&& ((this.getAvgLastTime() == castOther.getAvgLastTime()) || (this
						.getAvgLastTime() != null
						&& castOther.getAvgLastTime() != null && this
						.getAvgLastTime().equals(castOther.getAvgLastTime())))
				&& ((this.getOldUser() == castOther.getOldUser()) || (this
						.getOldUser() != null
						&& castOther.getOldUser() != null && this.getOldUser()
						.equals(castOther.getOldUser())))
				&& ((this.getTotalUser() == castOther.getTotalUser()) || (this
						.getTotalUser() != null
						&& castOther.getTotalUser() != null && this
						.getTotalUser().equals(castOther.getTotalUser())))
				&& ((this.getAu() == castOther.getAu()) || (this.getAu() != null
						&& castOther.getAu() != null && this.getAu().equals(
						castOther.getAu())))
				&& ((this.getApFk() == castOther.getApFk()) || (this.getApFk() != null
						&& castOther.getApFk() != null && this.getApFk()
						.equals(castOther.getApFk())))
				&& ((this.getTerminalFk() == castOther.getTerminalFk()) || (this
						.getTerminalFk() != null
						&& castOther.getTerminalFk() != null && this
						.getTerminalFk().equals(castOther.getTerminalFk())))
				&& ((this.getChannelFk() == castOther.getChannelFk()) || (this
						.getChannelFk() != null
						&& castOther.getChannelFk() != null && this
						.getChannelFk().equals(castOther.getChannelFk())))
				&& ((this.getPlatformFk() == castOther.getPlatformFk()) || (this
						.getPlatformFk() != null
						&& castOther.getPlatformFk() != null && this
						.getPlatformFk().equals(castOther.getPlatformFk())))
				&& ((this.getDateFk() == castOther.getDateFk()) || (this
						.getDateFk() != null
						&& castOther.getDateFk() != null && this.getDateFk()
						.equals(castOther.getDateFk())))
				&& ((this.getRegionFk() == castOther.getRegionFk()) || (this
						.getRegionFk() != null
						&& castOther.getRegionFk() != null && this
						.getRegionFk().equals(castOther.getRegionFk())))
				&& ((this.getOperatorFk() == castOther.getOperatorFk()) || (this
						.getOperatorFk() != null
						&& castOther.getOperatorFk() != null && this
						.getOperatorFk().equals(castOther.getOperatorFk())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getUid() == null ? 0 : this.getUid().hashCode());
		result = 37 * result
				+ (getWapPv() == null ? 0 : this.getWapPv().hashCode());
		result = 37 * result
				+ (getWebPv() == null ? 0 : this.getWebPv().hashCode());
		result = 37 * result + (getUv() == null ? 0 : this.getUv().hashCode());
		result = 37
				* result
				+ (getIncreaseUser() == null ? 0 : this.getIncreaseUser()
						.hashCode());
		result = 37 * result
				+ (getOnceUser() == null ? 0 : this.getOnceUser().hashCode());
		result = 37 * result
				+ (getLoginUser() == null ? 0 : this.getLoginUser().hashCode());
		result = 37
				* result
				+ (getAvgUserLogin() == null ? 0 : this.getAvgUserLogin()
						.hashCode());
		result = 37
				* result
				+ (getAvgUseAccessr() == null ? 0 : this.getAvgUseAccessr()
						.hashCode());
		result = 37
				* result
				+ (getAvgLastTime() == null ? 0 : this.getAvgLastTime()
						.hashCode());
		result = 37 * result
				+ (getOldUser() == null ? 0 : this.getOldUser().hashCode());
		result = 37 * result
				+ (getTotalUser() == null ? 0 : this.getTotalUser().hashCode());
		result = 37 * result + (getAu() == null ? 0 : this.getAu().hashCode());
		result = 37 * result
				+ (getApFk() == null ? 0 : this.getApFk().hashCode());
		result = 37
				* result
				+ (getTerminalFk() == null ? 0 : this.getTerminalFk()
						.hashCode());
		result = 37 * result
				+ (getChannelFk() == null ? 0 : this.getChannelFk().hashCode());
		result = 37
				* result
				+ (getPlatformFk() == null ? 0 : this.getPlatformFk()
						.hashCode());
		result = 37 * result
				+ (getDateFk() == null ? 0 : this.getDateFk().hashCode());
		result = 37 * result
				+ (getRegionFk() == null ? 0 : this.getRegionFk().hashCode());
		result = 37
				* result
				+ (getOperatorFk() == null ? 0 : this.getOperatorFk()
						.hashCode());
		return result;
	}

}