package com.nokia.ads.platform.backend.proxy;

import java.util.List;
import java.util.Map;

import com.nokia.ads.platform.backend.model.ModelObject;

public interface IMakeProxy {

	public void proxy(boolean isReflect, String levelType,
			String attributeName, ModelObject<Long> instance,
			ModelObject<Long> localVariable, List<String> excludeAttributes,
			List<ModelObject<Long>> changeLogs) throws Exception;

	Map<String, List<String>> getLevelType(ModelObject<Long> instance);

}
