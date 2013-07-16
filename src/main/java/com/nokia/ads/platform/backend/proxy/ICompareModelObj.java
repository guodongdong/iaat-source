package com.nokia.ads.platform.backend.proxy;

import java.util.List;

import com.nokia.ads.platform.backend.model.ModelObject;

public interface ICompareModelObj<T> {
	public List<ModelObject<Long>> compareTo(T localVariable);
}
