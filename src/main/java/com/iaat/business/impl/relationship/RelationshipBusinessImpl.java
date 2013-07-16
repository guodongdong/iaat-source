package com.iaat.business.impl.relationship;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.iaat.business.relationship.RelationshipBusiness;
import com.iaat.dao.factory.DaoFactory;
import com.iaat.dao.relateionship.RelationshipDao;
import com.iaat.json.SearchBar;
import com.iaat.json.relationship.RelationshipBean;
import com.iaat.json.relationship.RelationshipChildrenBean;
import com.iaat.model.Relationship;
import com.iaat.util.ValidateUtils;
import com.nokia.ads.common.util.Log;
/**
 * 
 * @name RelationshipBusinessImpl
 * 
 * @description CLASS_DESCRIPTION
 * 
 * MORE_INFORMATION
 * 
 * @author Lixw
 * 
 * @since 2013-7-11
 *       
 * @version 1.0
 */
public class RelationshipBusinessImpl implements RelationshipBusiness {

	private RelationshipDao relationshipDao = DaoFactory.getInstance(RelationshipDao.class);
	
	private final static Log log = Log.getLogger(RelationshipBusinessImpl.class);

	
	@Override
	public List<RelationshipBean> getRelationshipAll(SearchBar bar) {
		log.info("[RelationshipBusinessImpl.getRelationshipAll] [begin]");
		List<RelationshipBean> beans = new ArrayList<RelationshipBean>();

		List<Relationship> relationships = relationshipDao.getRelationshipAll(bar);
		Map<String,RelationshipBean> map = new HashMap<String, RelationshipBean>();
		String category = null;
		RelationshipBean bean = null;
		int i = 0;
		Long sumUv = 0l;
		String preUrl = null;
		try{
			 for(Relationship relation : relationships){
				  category = relation.getValue();
				  bean =	map.get(category);

				if (i != 0 && bean == null) {
					RelationshipBean shipBean = map.get(preUrl);
					shipBean.setSize(sumUv.intValue());
					sumUv = 0l;
				}
				  
				  if(bean==null){
					  bean = new RelationshipBean();
					  bean.setName(category);
					  
					  List<RelationshipChildrenBean> children = new ArrayList<RelationshipChildrenBean>();
					  RelationshipChildrenBean b = new RelationshipChildrenBean();
					  
					  String childCategory = relation.getInputValue();
					  b.setName(childCategory);
					  b.setSize(relation.getPv().intValue());
					  children.add(b);
					  bean.setChildren(children);
					  map.put(category, bean);
				  }else{
					  List<RelationshipChildrenBean> children = bean.getChildren();
				  	
					  RelationshipChildrenBean b = new RelationshipChildrenBean();
					  
					  String childCategory = relation.getInputValue();
					  b.setName(childCategory);
					  b.setSize(relation.getPv().intValue());
					  children.add(b);
				  }
				  
					preUrl = category;
					sumUv += relation.getPv();
					i++;
				  
			 }
			 Iterator<RelationshipBean> iterator = map.values().iterator();
			 while(iterator.hasNext()){
				 beans.add(iterator.next());
			 }
		} catch (Exception e) {
			log.error("[RelationshipBusinessImpl.getRelationshipAll] [{0}]",e.getMessage());
		}finally{
			log.info("[RelationshipBusinessImpl.getRelationshipAll] [end]");
		}
		return beans;
	}

}
