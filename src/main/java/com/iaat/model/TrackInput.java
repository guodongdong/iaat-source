package com.iaat.model;

import java.util.Date;



/**    
 * @name TimeTrackInput
 * 
 * @description CLASS_DESCRIPTION
 * 
 * MORE_INFORMATION
 * 
 * @author JackGuo
 * 
 * @since 2013-6-26
 *       
 * @version 1.0
 */
public class TrackInput implements java.io.Serializable {

	
	private static final long serialVersionUID = -8407567155493352493L;

	private String inputid;
	private String inputAddress;
	private Long imputUv;
	private String focusAddress;
	private Date time;


	public String getInputid() {
		return this.inputid;
	}

	public void setInputid(String inputid) {
		this.inputid = inputid;
	}

	public String getInputAddress() {
		return this.inputAddress;
	}

	public void setInputAddress(String inputAddress) {
		this.inputAddress = inputAddress;
	}

	public Long getImputUv() {
		return this.imputUv;
	}

	public void setImputUv(Long imputUv) {
		this.imputUv = imputUv;
	}

	public String getFocusAddress() {
		return this.focusAddress;
	}

	public void setFocusAddress(String focusAddress) {
		this.focusAddress = focusAddress;
	}

	public Date getTime() {
		return this.time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TrackInput))
			return false;
		TrackInput castOther = (TrackInput) other;

		return ((this.getInputid() == castOther.getInputid()) || (this
				.getInputid() != null
				&& castOther.getInputid() != null && this.getInputid().equals(
				castOther.getInputid())))
				&& ((this.getInputAddress() == castOther.getInputAddress()) || (this
						.getInputAddress() != null
						&& castOther.getInputAddress() != null && this
						.getInputAddress().equals(castOther.getInputAddress())))
				&& ((this.getImputUv() == castOther.getImputUv()) || (this
						.getImputUv() != null
						&& castOther.getImputUv() != null && this.getImputUv()
						.equals(castOther.getImputUv())))
				&& ((this.getFocusAddress() == castOther.getFocusAddress()) || (this
						.getFocusAddress() != null
						&& castOther.getFocusAddress() != null && this
						.getFocusAddress().equals(castOther.getFocusAddress())))
				&& ((this.getTime() == castOther.getTime()) || (this.getTime() != null
						&& castOther.getTime() != null && this.getTime()
						.equals(castOther.getTime())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getInputid() == null ? 0 : this.getInputid().hashCode());
		result = 37
				* result
				+ (getInputAddress() == null ? 0 : this.getInputAddress()
						.hashCode());
		result = 37 * result
				+ (getImputUv() == null ? 0 : this.getImputUv().hashCode());
		result = 37
				* result
				+ (getFocusAddress() == null ? 0 : this.getFocusAddress()
						.hashCode());
		result = 37 * result
				+ (getTime() == null ? 0 : this.getTime().hashCode());
		return result;
	}

}