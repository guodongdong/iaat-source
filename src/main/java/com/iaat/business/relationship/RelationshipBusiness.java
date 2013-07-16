package com.iaat.business.relationship;

import java.util.List;

import com.iaat.json.SearchBar;
import com.iaat.json.relationship.RelationshipBean;

public interface RelationshipBusiness {

	List<RelationshipBean> getRelationshipAll(SearchBar bar);
}
