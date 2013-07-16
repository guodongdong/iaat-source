package com.nokia.ads.platform.backend.dao.config;

import java.util.List;

import org.hibernate.criterion.Criterion;

public interface IFilter {

	List<Criterion> getCriteria(Criterion... criteria);

	List<Criterion> getCriteria();

}