package com.iaat.model;

import java.util.Date;


/**    
 * @name ChannelBean
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
public class ChannelBean implements java.io.Serializable {
	
	private static final long serialVersionUID = -5923180433731346207L;
	private Integer channelFk;
	private String channel;
	private Integer sequence;
	private Short isAvailable;
	private String PChannel;
	private Date updateTime;
	private String description;

	
	public Integer getChannelFk() {
		return this.channelFk;
	}

	public void setChannelFk(Integer channelFk) {
		this.channelFk = channelFk;
	}

	public String getChannel() {
		return this.channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
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

	public String getPChannel() {
		return this.PChannel;
	}

	public void setPChannel(String PChannel) {
		this.PChannel = PChannel;
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
		if (!(other instanceof ChannelBean))
			return false;
		ChannelBean castOther = (ChannelBean) other;

		return ((this.getChannelFk() == castOther.getChannelFk()) || (this
				.getChannelFk() != null
				&& castOther.getChannelFk() != null && this.getChannelFk()
				.equals(castOther.getChannelFk())))
				&& ((this.getChannel() == castOther.getChannel()) || (this
						.getChannel() != null
						&& castOther.getChannel() != null && this.getChannel()
						.equals(castOther.getChannel())))
				&& ((this.getSequence() == castOther.getSequence()) || (this
						.getSequence() != null
						&& castOther.getSequence() != null && this
						.getSequence().equals(castOther.getSequence())))
				&& ((this.getIsAvailable() == castOther.getIsAvailable()) || (this
						.getIsAvailable() != null
						&& castOther.getIsAvailable() != null && this
						.getIsAvailable().equals(castOther.getIsAvailable())))
				&& ((this.getPChannel() == castOther.getPChannel()) || (this
						.getPChannel() != null
						&& castOther.getPChannel() != null && this
						.getPChannel().equals(castOther.getPChannel())))
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
				+ (getChannelFk() == null ? 0 : this.getChannelFk().hashCode());
		result = 37 * result
				+ (getChannel() == null ? 0 : this.getChannel().hashCode());
		result = 37 * result
				+ (getSequence() == null ? 0 : this.getSequence().hashCode());
		result = 37
				* result
				+ (getIsAvailable() == null ? 0 : this.getIsAvailable()
						.hashCode());
		result = 37 * result
				+ (getPChannel() == null ? 0 : this.getPChannel().hashCode());
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