package com.iaat.dao.relateionship;

import java.util.List;

import com.iaat.json.SearchBar;
import com.iaat.model.Relationship;
/**
 * 
 * @name Rellationship
 * 
 * @description CLASS_DESCRIPTION
 * 
 * MORE_INFORMATION
 * 
 * @author lixw
 * 
 * @since 2013-7-10
 *       
 * @version 1.0
 */
public interface RelationshipDao {

	
	
	List<Relationship> getRelationshipAll(SearchBar bar);
	
	
	
	
}
