package com.nokia.ads.platform.backend.core.scheduler;

import com.nokia.ads.platform.backend.core.scheduler.model.Task;

public abstract class GenericWorker {
	abstract public boolean execute(final Task task);
}
