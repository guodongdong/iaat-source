package com.nokia.ads.platform.backend.core.scheduler.model;

import com.nokia.ads.platform.backend.model.ModelObject;

public class TaskError extends ModelObject<Long>{
	
	private String reason;
	private String fieldPath;
	private Long targetId;
	private String targetType;
	private String code;
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public int hashCode() {
		return makeHashCode(this.id, this.reason);
	}

	@Override
	public String toString() {
		return makeIdentifiableString(this, this.reason,this.fieldPath,this.targetId,this.targetType);
	}

	public String getReason() {
		return reason;
	}

	public String getFieldPath() {
		return fieldPath;
	}

	public void setFieldPath(String fieldPath) {
		this.fieldPath = fieldPath;
	}

	public Long getTargetId() {
		return targetId;
	}

	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}

	public String getTargetType() {
		return targetType;
	}

	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

}
