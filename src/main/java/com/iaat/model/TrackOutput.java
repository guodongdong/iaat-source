package com.iaat.model;

import java.util.Date;


/**    
 * @name TimeTrackOutput
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
public class TrackOutput implements java.io.Serializable {

	private static final long serialVersionUID = -2212302183253303958L;
	private String outid;
	private String outputAddress;
	private Long outputUv;
	private String focusAddress;
	private Date time;

	public String getOutid() {
		return this.outid;
	}

	public void setOutid(String outid) {
		this.outid = outid;
	}

	public String getOutputAddress() {
		return this.outputAddress;
	}

	public void setOutputAddress(String outputAddress) {
		this.outputAddress = outputAddress;
	}

	public Long getOutputUv() {
		return this.outputUv;
	}

	public void setOutputUv(Long outputUv) {
		this.outputUv = outputUv;
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
		if (!(other instanceof TrackOutput))
			return false;
		TrackOutput castOther = (TrackOutput) other;

		return ((this.getOutid() == castOther.getOutid()) || (this.getOutid() != null
				&& castOther.getOutid() != null && this.getOutid().equals(
				castOther.getOutid())))
				&& ((this.getOutputAddress() == castOther.getOutputAddress()) || (this
						.getOutputAddress() != null
						&& castOther.getOutputAddress() != null && this
						.getOutputAddress()
						.equals(castOther.getOutputAddress())))
				&& ((this.getOutputUv() == castOther.getOutputUv()) || (this
						.getOutputUv() != null
						&& castOther.getOutputUv() != null && this
						.getOutputUv().equals(castOther.getOutputUv())))
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
				+ (getOutid() == null ? 0 : this.getOutid().hashCode());
		result = 37
				* result
				+ (getOutputAddress() == null ? 0 : this.getOutputAddress()
						.hashCode());
		result = 37 * result
				+ (getOutputUv() == null ? 0 : this.getOutputUv().hashCode());
		result = 37
				* result
				+ (getFocusAddress() == null ? 0 : this.getFocusAddress()
						.hashCode());
		result = 37 * result
				+ (getTime() == null ? 0 : this.getTime().hashCode());
		return result;
	}

}