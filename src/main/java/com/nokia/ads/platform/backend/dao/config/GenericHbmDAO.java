package com.nokia.ads.platform.backend.dao.config;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.nokia.ads.common.util.Log;
import com.nokia.ads.common.util.StringUtils;
import com.nokia.ads.platform.backend.core.UncheckedException;
import com.nokia.ads.platform.backend.i18n.report.LanguageKey;
import com.nokia.ads.platform.backend.model.ModelObject;
import com.nokia.ads.platform.backend.util.Paging;

public abstract class GenericHbmDAO<T extends ModelObject<ID>, ID extends Serializable> implements GenericDAO<T, ID> {

	private static Log log = Log.getLogger(GenericHbmDAO.class);

	private Class<T> persistentClass;
	private final String CLASS_TAG;

	public GenericHbmDAO() {
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		this.CLASS_TAG = persistentClass.getSimpleName();
	}

	protected synchronized Session getSession() {
		return HibernateManager.getCurrentSession();
	}

	public Class<T> getPersistentClass() {
		return persistentClass;
	}

	public String converIdArrayToString(Long[] ids) {
		return Arrays.toString(ids).replaceAll("[\\[\\]]", "");
	}

	public Integer updateModelObjInfo(List<String> propertyNames, List<Object> values, Map<String, Object> conditions) {
		String tableName = getPersistentClass().getSimpleName();
		StringBuffer buffer = new StringBuffer(" UPDATE ").append(tableName).append(" SET ");
		int index = 0;
		for (String propertyName : propertyNames) {
			buffer.append(propertyName).append("=? ");
			if (index < propertyNames.size() - 1) {
				buffer.append(",");
			}
			index++;
		}
		buffer.append(" WHERE ");
		index = 0;
		for (Map.Entry entry : conditions.entrySet()) {
			buffer.append((String) entry.getKey()).append("=? ");
			if (index < conditions.size() - 1) {
				buffer.append(" AND ");
			}
			index++;
		}
		values.addAll(conditions.values());
		return Integer.valueOf(updateByHQL(buffer.toString(), values.toArray(new Object[0])));
	}

	@Override
	public Integer updateModelObjInfoById(List<String> propertyNames, List<Object> values, String conditionsPropertyNames, List<Long> conditions) {
		String tableName = getPersistentClass().getSimpleName();
		StringBuffer buffer = new StringBuffer(" update ").append(tableName).append(" set ");
		int index = 0;
		for (String propertyName : propertyNames) {
			buffer.append(propertyName).append("=? ");
			if (index < propertyNames.size() - 1) {
				buffer.append(",");
			}
			index++;
		}
		buffer.append(" where ");
		index = 0;

		if (null == conditions || conditions.size() == 0) {
			return null;
		}
		buffer.append(conditionsPropertyNames).append("  in ( ");
		for (Long id : conditions) {

			buffer.append(id.longValue());
			if (index + 1 == conditions.size()) {
				buffer.append(" )");
			} else {
				buffer.append(" , ");
			}
			index++;
		}

		System.err.println(buffer.toString());
		return Integer.valueOf(updateByHQL(buffer.toString(), values.toArray(new Object[0])));
	}

	/**
	 * find a record by local id
	 * 
	 * @param id
	 * @param lock
	 *            - if lock is true,select a record for update.if lock is
	 *            false,just select a record
	 * 
	 * @return T
	 */
	@Override
	public T findById(ID id, boolean lock) {
		log.debug("findById:{0},lock={1}", id, lock);
		try {
			T entity;
			if (lock) {
				entity = (T) getSession().get(getPersistentClass(), id, LockOptions.UPGRADE);
			} else {
				entity = (T) getSession().get(getPersistentClass(), id);
			}
			return entity;
		} catch (HibernateException e) {
			log.error(e.toString());
			throw buildException(ErrorCode.DAO_QBC_ERROR, e);
		}
	}

	/**
	 * save or update an entity
	 * 
	 * @param modelObj
	 * @return T
	 */
	@Override
	public <M extends ModelObject<ID>> M makePersistent(M modelObj) {
		try {
			getSession().saveOrUpdate(modelObj);
			return modelObj;
		} catch (HibernateException e) {
			log.error(e.toString());
			e.printStackTrace();
			throw buildException(ErrorCode.DAO_PERSISTENT_ERROR, e);
		}
	}

	/**
	 * delete an entity
	 * 
	 * @param modelObj
	 * @return void
	 */
	@Override
	public <M extends ModelObject<ID>> void makeTransient(M modelObj) {
		log.debug("makeTransient: {0}", modelObj.getId());
		try {
			getSession().delete(modelObj);
		} catch (HibernateException e) {
			log.error(e.toString());
			throw buildException(ErrorCode.DAO_TRANSIENT_ERROR, e);
		}
	}

	/**
	 * delete records by batch
	 * 
	 * @param ids
	 * 
	 * @return the deleted record count
	 */
	@Override
	public void makeTransient(ID[] ids) {
		try {
			if (ids.length == 0) {
				log.warn("the length of ids is 0.");
				return;
			}
			String hql = "DELETE  FROM " + this.getPersistentClass().getName() + " WHERE  id IN (:ids) ";
			this.getSession().createQuery(hql).setParameterList("ids", ids).executeUpdate();
			return;
		} catch (HibernateException e) {
			log.error(e.toString());
			throw buildException(ErrorCode.DAO_DELETE_ERROR, e);
		}
	}

	@Override
	public List<T> findByIds(ID[] ids) {
		try {
			if (ids == null || ids.length <= 0) {
				log.warn("ids is null or the length of ids is equal or less than 0.");
				return new ArrayList<T>();
			}
			String hql = "FROM " + this.getPersistentClass().getName();
			Query query = null;
			if (ids.length > 1) {
				hql += " WHERE id IN (:ids)";
				query = this.getSession().createQuery(hql);
				query.setParameterList("ids", ids);
			} else {
				hql += " WHERE id =" + ids[0];
				query = this.getSession().createQuery(hql);
			}

			return query.list();
		} catch (HibernateException e) {
			log.error(e.toString());
			throw buildException(ErrorCode.DAO_HQL_ERROR, e);
		}
	}

	@Override
	public List<T> findAll(Paging paging) {
		try {
			return findByCriteria(paging);
		} catch (Exception e) {
			log.error(e.toString());
			throw buildException(ErrorCode.DAO_QBC_ERROR, e);
		}
	}

	/**
	 * 
	 * @param exampleInstance
	 * @param excludeProperty
	 * @param paging
	 * @return if exampleInstance is null,return an empty list.
	 * 
	 *         if paging is null,return all matched records.
	 * 
	 */
	@Override
	public List<T> findByExample(T exampleInstance, String[] excludeProperty, boolean isLike, Paging paging) {

		if (exampleInstance == null) {
			log.error("exampleInstance is null.");
			throw new IllegalArgumentException("exampleInstance is null");
		}

		try {
			Criteria crit = getSession().createCriteria(exampleInstance.getClass());
			Example example = Example.create(exampleInstance);
			if (isLike) {
				example.enableLike(MatchMode.ANYWHERE);
			}
			if (excludeProperty != null) {
				log.debug("excludeProperty is not null.");
				for (String exclude : excludeProperty) {
					example.excludeProperty(exclude);
				}
			}
			crit.add(example);
			if (paging != null) {
				log.debug("paging is not null.");
				Criteria critCount = this.getSession().createCriteria(exampleInstance.getClass());
				critCount.setProjection(Projections.rowCount());
				critCount.add(example);
				paging.setTotalRecord(((Number) critCount.uniqueResult()).intValue());
				crit.setFirstResult(paging.getCurrentRecord()).setMaxResults(paging.getPageSize());
			}
			return crit.list();
		} catch (HibernateException e) {
			log.error(e.toString());
			throw buildException(ErrorCode.DAO_QBC_ERROR, e);
		}
	}

	@Override
	public List<T> findByPropertys(String[] propertys, Object[] values, boolean isLike, Paging paging) {
		try {
			List<Criterion> criteria = addConditions(propertys, values, isLike);
			return findByCriteria(paging, criteria.toArray(new Criterion[] {}));
		} catch (Exception e) {
			log.error(e.toString());
			throw buildException(ErrorCode.DAO_QBC_ERROR, e);
		}
	}

	@Override
	public List<T> findByFilter(String[] propertys, Object[] values, boolean isLike, Paging paging, IFilter filter) {
		try {
			List<Criterion> conditions = new ArrayList<Criterion>();
			Criteria criteria = this.createCriteria();
			List<Criterion> cris = this.addConditions(propertys, values, isLike);
			if (null != filter) {
				conditions.addAll(filter.getCriteria(cris.toArray(new Criterion[] {})));
			} else {
				conditions.addAll(cris);
			}

			this.buildCriteria(criteria);
			this.buildCriteria(criteria, paging, conditions.toArray(new Criterion[] {}));

			return criteria.list();
		} catch (HibernateException e) {
			throw buildException(ErrorCode.DAO_QBC_ERROR, e);
		}
	}

	@Override
	public int getCount(String propertys[], Object values[], boolean isLike) {
		return this.getTotalRecords(this.addConditions(propertys, values, isLike).toArray(new Criterion[] {}));
	}

	@Override
	public void flush() {
		try {
			getSession().flush();
		} catch (HibernateException e) {
			throw buildException(ErrorCode.DAO_OTHER_ERROR, e);
		}
	}

	@Override
	public void clear() {
		try {
			getSession().clear();
		} catch (Exception e) {
			throw buildException(ErrorCode.DAO_OTHER_ERROR, e);
		}
	}

	@Override
	public void initialize(T obj) {
		try {
			Hibernate.initialize(obj);
		} catch (HibernateException e) {
			throw buildException(ErrorCode.DAO_OTHER_ERROR, e);
		}
	}

	@Override
	public void evict(T obj) {
		try {
			this.getSession().evict(obj);
		} catch (HibernateException e) {
			throw buildException(ErrorCode.DAO_OTHER_ERROR, e);
		}
	}

	/**
	 * 
	 * createCriteria(Here describes this method function with a few words)
	 * 
	 * @return Criteria
	 */
	protected Criteria createCriteria() {
		Criteria crit = getSession().createCriteria(getPersistentClass());
		return crit;
	}

	/**
	 * get the total matched record number from database
	 * 
	 * @param criterions
	 * @return
	 */
	protected int getTotalRecords(Criterion... criteria) {
		Criteria crit = createCriteria();
		crit.setProjection(Projections.rowCount());
		if (criteria != null) {
			for (Criterion criterion : criteria) {
				crit.add(criterion);
			}
		}
		return ((Number) crit.uniqueResult()).intValue();
	}

	/**
	 * 
	 * @param paging
	 * @param conditions
	 * @return if paging is null,return all matched records.
	 * 
	 *         if criteria is null,return all records.
	 * 
	 */

	protected List<T> findByCriteria(Paging paging, Criterion... conditions) {
		Criteria crit = this.createCriteria();
		buildCriteria(crit, paging, conditions);
		crit.addOrder(Order.desc("id"));
		return crit.list();
	}

	protected List<T> findByCriteria(Criteria crit, Paging paging, Criterion... conditions) {
		buildCriteria(crit, paging, conditions);
		crit.addOrder(Order.desc("id"));
		return crit.list();
	}

	protected List<T> findByCriteria(Paging paging, Order[] orders, Criterion... conditions) {
		Criteria crit = this.createCriteria();
		buildCriteria(crit, paging, conditions);
		for (Order order : orders) {
			crit.addOrder(order);
		}
		return crit.list();
	}

	protected List<T> findByTop(Paging paging, Order[] orders, int limit, Criterion... criteria) {
		return findByTop(paging, orders, limit, null, criteria);
	}

	protected List<T> findByTop(Paging paging, Order[] orders, int limit, LockMode lockMode, Criterion... criteria) {
		Criteria crit = this.createCriteria();
		buildCriteria(crit, paging, criteria);
		if (lockMode != null)
			crit.setLockMode(lockMode);
		crit.setMaxResults(limit);
		if (orders != null) {
			for (Order order : orders) {
				crit.addOrder(order);
			}
		}
		return crit.list();
	}

	protected <M> List<M> findByHQL(String hql, Paging paging, Object... params) {
		if (null == hql || hql.equals("")) {
			throw new IllegalArgumentException("hql is null or blank");
		}
		Query query = this.getSession().createQuery(hql);
		this.buildQuery(params, query);
		this.addPaging(hql, query, params, paging);
		return query.list();
	}

	protected <M> List<M> findByGroupHQL(String hql, Paging paging, Object... params) {
		if (null == hql || hql.equals("")) {
			throw new IllegalArgumentException("hql is null or blank");
		}
		Query query = this.getSession().createQuery(hql);
		this.buildQuery(params, query);
		// this.addPaging(hql, query, params, paging);

		if (paging != null) {
			log.info("paging is not null.");
			String countHql = "select count(*) " + hql.substring(hql.toLowerCase().indexOf("from"), hql.length());
			Query countQuery = this.getSession().createQuery(countHql);
			buildQuery(params, countQuery);
			if (null != countQuery && null != countQuery.list()) {
				paging.setTotalRecord(countQuery.list().size());
			} else {
				paging.setTotalRecord(0);
			}
			query.setFirstResult(paging.getCurrentRecord()).setMaxResults(paging.getPageSize());
		}

		return query.list();
	}

	protected <M> List<M> findByHQL(String hql, String[] keys, Object[] params, Paging paging) {
		if (null == hql || hql.equals("")) {
			throw new IllegalArgumentException("hql is null or blank");
		}
		Query query = this.getSession().createQuery(hql);
		this.buildQuery(keys, params, query);
		this.addPaging(hql, query, keys, params, paging);
		return query.list();
	}

	protected <M> List<M> findByLeftJoinHQL(String hql, String[] keys, Object[] params, Paging paging) {
		if (null == hql || hql.equals("")) {
			throw new IllegalArgumentException("hql is null or blank");
		}
		Query query = this.getSession().createQuery(hql);
		this.buildQuery(keys, params, query);
		this.addPagingLeftJoin(hql, query, keys, params, paging);
		return query.list();
	}
	
	protected void addPagingLeftJoin(String hql, Query query, String[] keys, Object[] params, Paging paging) {
		if (paging != null) {
			log.info("paging is not null.");
			Number nubmer = (Number)query.list().size();
			paging.setTotalRecord(null == nubmer ? 0 : Integer.valueOf(nubmer.toString()));
			query.setFirstResult(paging.getCurrentRecord()).setMaxResults(paging.getPageSize());
		}
	}

	protected int updateByHQL(String hql, Object... params) {
		if (null == hql || hql.equals("")) {
			throw new IllegalArgumentException("hql is null or blank");
		}
		Query query = this.getSession().createQuery(hql);
		this.buildQuery(params, query);
		return query.executeUpdate();
	}

	protected int updateByHQL(String hql, String[] keys, Object... params) {
		if (null == hql || hql.equals("")) {
			throw new IllegalArgumentException("hql is null or blank");
		}
		Query query = this.getSession().createQuery(hql);
		this.buildQuery(keys, params, query);
		return query.executeUpdate();
	}

	protected void addPaging(String hql, Query query, Paging paging) {
		if (paging != null) {
			log.info("paging is not null.");
			String countHql = "select count(*) " + hql.substring(hql.toLowerCase().indexOf("from"), hql.length());
			paging.setTotalRecord(((Long) this.getSession().createQuery(countHql).uniqueResult()).intValue());
			query.setFirstResult(paging.getCurrentRecord()).setMaxResults(paging.getPageSize());
		}
	}

	protected void addPaging(String hql, Query query, String[] keys, Object[] params, Paging paging) {
		if (paging != null) {
			log.info("paging is not null.");
			String countHql = "select count(*) " + hql.substring(hql.toLowerCase().indexOf("from"), hql.length());
			Query countQuery = null;
			if (query instanceof SQLQuery) {
				countQuery = this.getSession().createSQLQuery(countHql);
			} else {
				countQuery = this.getSession().createQuery(countHql);
			}
			buildQuery(keys, params, countQuery);
			Number nubmer = (Number) countQuery.uniqueResult();
			paging.setTotalRecord(null == nubmer ? 0 : Integer.valueOf(nubmer.toString()));
			query.setFirstResult(paging.getCurrentRecord()).setMaxResults(paging.getPageSize());
		}
	}

	protected void addPaging(String hql, String[] keys, Object[] params, Paging paging) {
		if (paging != null) {
			log.info("paging is not null.");
			String countHql = "select count(*) " + hql.substring(hql.toLowerCase().indexOf("from"), hql.length());
			Query countQuery = this.getSession().createSQLQuery(countHql);
			buildQuery(keys, params, countQuery);
			Number nubmer = (Number) countQuery.uniqueResult();
			paging.setTotalRecord(null == nubmer ? 0 : Integer.valueOf(nubmer.toString()));
		}
	}

	protected void addPaging(String hql, Query query, Object[] params, Paging paging) {
		if (paging != null) {
			log.info("paging is not null.");
			String countHql = "select count(*) " + hql.substring(hql.toLowerCase().indexOf("from"), hql.length());
			Query countQuery = this.getSession().createQuery(countHql);
			buildQuery(params, countQuery);
			if (null != countQuery && null != countQuery.uniqueResult()) {
				paging.setTotalRecord(((Long) countQuery.uniqueResult()).intValue());
			} else {
				paging.setTotalRecord(0);
			}
			query.setFirstResult(paging.getCurrentRecord()).setMaxResults(paging.getPageSize());
		}
	}

	protected void buildQuery(Object[] params, Query query) {
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}
	}

	protected void buildQuery(Map<String, Object> map, Query query) {
		if (map != null) {
			Set<String> setParams = map.keySet();
			for (String param : setParams) {
				Object value = map.get(param);
				if (value.getClass().isArray()) {
					query.setParameterList(param, (Object[]) value);
				} else if (value instanceof Collection<?>) {
					query.setParameterList(param, (Collection<?>) value);
				} else {
					query.setParameter(param, value);
				}
			}
		}
	}

	protected void buildQuery(String[] keys, Object[] params, Query query) {
		if (keys != null && params != null) {
			for (int i = 0; i < params.length; i++) {
				if (params[i] != null && params[i].getClass().isArray()) {
					query.setParameterList(keys[i], (Object[]) params[i]);
				} else {
					query.setParameter(keys[i], params[i]);
				}
			}
		}
	}

	protected void buildCriteria(Criteria criteria, Paging paging, Criterion... conditions) {
		if (conditions != null && conditions.length > 0) {
			log.info("criteria is not null and criteria's length is larger than 0.");
			for (Criterion criterion : conditions) {
				criteria.add(criterion);
			}
		}
		if (paging != null) {
			log.info("paging is not null.");
			paging.setTotalRecord(getTotalRecords(criteria, conditions));
			criteria.setProjection(null);
			criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
			criteria.setFirstResult(paging.getCurrentRecord()).setMaxResults(paging.getPageSize());
		}
	}

	protected List<Criterion> addConditions(String[] propertys, Object[] values, boolean isLike) {
		List<Criterion> criteria = new ArrayList<Criterion>();
		if (propertys != null && values != null) {
			for (int i = 0; i < propertys.length; i++) {
				if (values[i] != null && values[i].getClass().isArray()) {
					criteria.add(Restrictions.in(propertys[i], (Object[]) values[i]));
				} else if (values[i] != null && "String".equals(values[i].getClass().getSimpleName()) && isLike) {
					criteria.add(Restrictions.like(propertys[i], LikeMode.MIDDLE.setValue((String) values[i]).getValue()));
				} else {
					criteria.add(Restrictions.eq(propertys[i], values[i]));
				}
			}
		}
		return criteria;
	}

	protected int getTotalRecords(Criteria crit, Criterion... criteria) {
		crit.setProjection(Projections.rowCount());
		if (criteria != null) {
			for (Criterion criterion : criteria) {
				crit.add(criterion);
			}
		}
		return null == crit.uniqueResult() ? 0 : ((Number) crit.uniqueResult()).intValue();
	}

	/**
	 * this function do nothing but some sub-class may override this function
	 * 
	 * @param criteria
	 */
	protected void buildCriteria(Criteria criteria) {
	}

	@Override
	public List<T> findByTop(String[] propertys, Object[] values, boolean isLike, String[] orderPropertys, String[] orderTypes, int limit, Paging paging) {
		return findByTop(paging, buildOrders(orderPropertys, orderTypes), limit, null, buildCriteria(propertys, values, isLike));
	}

	protected List<?> findByTop(Paging paging, Order[] orders, Projection[] projections, int limit, Criterion... conditions) {
		Criteria crit = this.createCriteria();
		buildCriteria(crit, paging, conditions);
		crit.setMaxResults(limit);
		if (orders != null) {
			for (Order order : orders) {
				crit.addOrder(order);
			}
		}
		if (projections != null) {
			ProjectionList list = Projections.projectionList();
			for (Projection projection : projections) {
				list.add(projection);
			}
			crit.setProjection(list);
		}
		return crit.list();
	}

	private Order[] buildOrders(String[] orderPropertys, String[] orderTypes) {
		List<Order> orders = new ArrayList<Order>();
		if (orderPropertys != null && orderTypes != null) {
			for (int i = 0; i < orderPropertys.length; i++) {
				if ("asc".equalsIgnoreCase(orderTypes[i])) {
					orders.add(Order.asc(orderPropertys[i]));
				} else if ("desc".equalsIgnoreCase(orderTypes[i])) {
					orders.add(Order.desc(orderPropertys[i]));
				} else {
					throw new IllegalArgumentException("unknown orderType: " + orderTypes[i]);
				}
			}
		}
		return orders.toArray(new Order[] {});
	}

	protected UncheckedException buildException(String errCode, Exception e) {
		return new UncheckedException(CLASS_TAG + "." + Thread.currentThread().getStackTrace()[1].getMethodName() + ":" + e.getMessage(), errCode, e);
	}

	private Criterion[] buildCriteria(String[] propertys, Object[] values, boolean isLike) {
		List<Criterion> criteria = new ArrayList<Criterion>();
		if (propertys != null && values != null) {
			for (int i = 0; i < propertys.length; i++) {
				if (values[i] != null && values[i].getClass().isArray()) {
					criteria.add(Restrictions.in(propertys[i], (Object[]) values[i]));
				} else if (values[i] != null && "String".equals(values[i].getClass().getSimpleName()) && isLike) {
					criteria.add(Restrictions.like(propertys[i], LikeMode.MIDDLE.setValue((String) values[i]).getValue()));
				} else {
					criteria.add(Restrictions.eq(propertys[i], values[i]));
				}
			}
		}
		return criteria.toArray(new Criterion[] {});
	}

	public int executeUpdate(String hql, Map<String, Object> conditions) {
		if (StringUtils.hasLength(hql) && null != conditions) {
			int executeRow = this.updateByHQL(hql, conditions.keySet().toArray(new String[] {}), conditions.values().toArray());
			return executeRow;
		}
		return 0;
	}

	public void executeUpdateBySql(String sql, Map<String, Object> conditions) {
		if (StringUtils.hasLength(sql) && null != conditions) {
			SQLQuery sqlQuery = this.getSession().createSQLQuery(sql);
			this.buildQuery(conditions, sqlQuery);
			sqlQuery.executeUpdate();
		}
	}

	/*
	 * @Override public List<?> selectBySQL(String sql) { if (null == sql ||
	 * sql.equals("")) throw new UncheckedException(
	 * "GenericHibernateDAO --> selectBySQL :sql is null",
	 * LanguageKey.REPORT_DAO_SQL_ERROR); Query query =
	 * this.getSession().createSQLQuery(sql); return query.list(); }
	 */
	@Override
	public List<?> selectBySQL(String sql, Map<String, Object> conditions) {
		if (null == sql || sql.equals(""))
			throw new UncheckedException("GenericHibernateDAO --> selectBySQL :sql is null", LanguageKey.REPORT_DAO_SQL_ERROR);
		Query query = this.getSession().createSQLQuery(sql);
		if (conditions != null && conditions.size() > 0) {
			buildQuery(conditions, query);
		}
		return query.list();
	}
	
	public Object selectBySQLUnique(String sql, Map<String, Object> conditions) {
		if (null == sql || sql.equals(""))
			throw new UncheckedException("GenericHibernateDAO --> selectBySQL :sql is null", LanguageKey.REPORT_DAO_SQL_ERROR);
		Query query = this.getSession().createSQLQuery(sql);
		if (conditions != null && conditions.size() > 0) {
			buildQuery(conditions, query);
		}
		return query.uniqueResult();
	}
}
