package com.nokia.ads.platform.backend.core.scheduler.type;
import com.nokia.ads.platform.backend.model.type.IEnumCode;
public enum TaskStatus  implements IEnumCode {
	READY, COMPLETE, PENDING, ERROR;
	public static TaskStatus fromCode(String s) {
		return TaskStatus.valueOf(s.toUpperCase());
	}

	@Override
	public String getCode() {
		return this.name();
	}

	@Override
	public boolean equals(String s) {
		return this.name().equalsIgnoreCase(s);
	}
}