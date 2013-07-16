package com.nokia.ads.platform.backend.dao.config;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.nokia.ads.platform.backend.model.ModelObject;
import com.nokia.ads.platform.backend.util.Paging;

public interface GenericDAO<T extends ModelObject<ID>, ID extends Serializable> {

	/**
	 * find item by the primary ID
	 * 
	 * @param id
	 * @param lock
	 * @return
	 */
	T findById(ID id, boolean lock);

	public List<T> findByIds(ID[] ids);

	/**
	 * return all if paging is null
	 * 
	 * @param paging
	 * @return
	 */
	List<T> findAll(Paging paging);

	public Integer updateModelObjInfo(List<String> propertyNames, List<Object> values, Map<String, Object> conditions);

	public Integer updateModelObjInfoById(List<String> propertyNames, List<Object> values, String conditionsPropertyNames, List<Long> conditions);

	/**
	 * find instances by example, return all if paging is null
	 * 
	 * @param exampleInstance
	 * @param excludeProperty
	 * @param paging
	 * @return
	 */
	List<T> findByExample(T exampleInstance, String[] excludeProperty, boolean isLike, Paging paging);

	List<T> findByTop(String[] propertys, Object[] values, boolean isLike, String[] orderPropertys, String[] orderTypes, int limit, Paging paging);

	/**
	 * save entity to persistence storage
	 * 
	 * @param entity
	 * @return
	 */
	public <M extends ModelObject<ID>> M makePersistent(M modelObj);

	/**
	 * delete entity from persistence storage
	 * 
	 * @param entity
	 */
	public <M extends ModelObject<ID>> void makeTransient(M modelObj);

	/**
	 * delete a couple of entities who has the ids
	 * 
	 * @param ids
	 * @return
	 */
	void makeTransient(ID[] ids);

	/**
	 * flush any cached operation
	 */
	void flush();

	/**
	 * clear any cache
	 */
	void clear();

	/**
	 * clear up the target obj
	 * 
	 * @param obj
	 */
	void initialize(T obj);

	/**
	 * clear obj cache
	 * 
	 * @param obj
	 */
	void evict(T obj);

	/**
	 * find objects by properties
	 * 
	 * @param propertys
	 * @param values
	 * @param isLike
	 * @param paging
	 * @return
	 */
	List<T> findByPropertys(String[] propertys, Object[] values, boolean isLike, Paging paging);

	/**
	 * find objects by customized filter
	 * 
	 * @param propertys
	 * @param values
	 * @param isLike
	 * @param paging
	 * @param filter
	 * @return
	 */
	public List<T> findByFilter(String[] propertys, Object[] values, boolean isLike, Paging paging, IFilter filter);

	public int getCount(String propertys[], Object values[], boolean isLike);

	public int executeUpdate(String hql, Map<String, Object> conditions);

	public void executeUpdateBySql(String sql, Map<String, Object> conditions);

	//public List<?> selectBySQL(String sql);
	
	public List<?> selectBySQL(String sql, Map<String, Object> conditions);

}
