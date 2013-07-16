package com.nokia.ads.platform.backend.core.scheduler.type;

import com.nokia.ads.platform.backend.model.type.IEnumCode;

public enum TaskPriority implements IEnumCode {
	REALTIME(1), HIGH(2), NORMAL(3), LOW(4), IDLE(5);

	private TaskPriority(int level) {
		this.level = level;
	}

	private int level;

	public static TaskPriority fromLevel(int level) {
		for (TaskPriority taskPriority : TaskPriority.values()) {
			if (taskPriority.level==level) {
				return taskPriority;
			}
		}
		return null;
	}
	
	public int getLevel() {
		return this.level;
	}

	@Override
	public String getCode() {
		return this.level+"";
	}

	@Override
	public boolean equals(String s) {
		return this.name().equalsIgnoreCase(s);
	}
}