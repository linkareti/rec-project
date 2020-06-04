package com.linkare.irn.nascimento.dao;

import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.CacheRetrieveMode;
import javax.persistence.CacheStoreMode;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import com.linkare.irn.nascimento.model.DomainObject;
import com.linkare.irn.nascimento.util.SelectionRange;

/**
 * 
 * This class is a generic and abstract implementation for the Data Access Object pattern that provides CRUD operations related to domain entities that extend
 * from DomainObject (all domain related entities should extend from that). The idea behind this implementation is to support the developer with some of the
 * most common operations we need for any domain object. Nevertheless, anytime a new method is necessary such as a finder, this class should be extended with a
 * specific DAO for that entity and the new method may be implemented in that subclass.
 * 
 * @author Paulo Zenida - Linkare TI
 *
 */
public abstract class GenericDAO<ENTITY extends DomainObject> {

    public static final int DEFAULT_COUNT_RECORDS = 25;

    private final transient EntityManager entityManager;

    private Logger log = Logger.getLogger(this.getClass().getName());

    private Class<ENTITY> entityTypeClass;

    /**
     * 
     * @param entityManager
     *            the entityManager this DAO is necessary to use
     */
    public GenericDAO(final EntityManager entityManager) {
	super();
	this.entityManager = entityManager;
    }

    public EntityManager getEntityManager() {
	return entityManager;
    }

    /**
     * @return the log
     */
    public Logger getLog() {
	return log;
    }

    public void create(final ENTITY t) {
	log.fine("Creating " + t);
	getEntityManager().persist(t);
    }

    public ENTITY update(final ENTITY t) {
	log.fine("Updating " + t);
	ENTITY mergedEntity = getEntityManager().merge(t);
	return mergedEntity;
    }

    public void remove(final ENTITY t) {
	log.fine("Removing " + t);
	final ENTITY mergedEntity = getEntityManager().merge(t);
	if (mergedEntity != null) {
	    getEntityManager().remove(mergedEntity);
	}
    }

    public void remove(final long id) {
	final ENTITY t = find(id);
	if (t != null) {
	    remove(t);
	}
    }

    public final List<ENTITY> findRange(final SelectionRange range) {
	final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
	final CriteriaQuery<ENTITY> criteriaQueryAll = cb.createQuery(getEntityTypeClass());
	final Root<ENTITY> root = criteriaQueryAll.from(getEntityTypeClass());

	final List<Order> orderBy = getOrderBy(root, cb);
	if (!orderBy.isEmpty()) {
	    criteriaQueryAll.orderBy(orderBy);
	}

	final TypedQuery<ENTITY> q = getEntityManager().createQuery(criteriaQueryAll);

	final EntityGraph<? extends DomainObject> fetchGraph = getFetchGraph();
	if (fetchGraph != null) {
	    q.setHint("javax.persistence.loadgraph", fetchGraph);
	}

	q.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.USE);
	q.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
	if (range != null) {
	    if (range.getStart() < 0) {
		range.setStart(0);
	    }
	    if (range.getCount() < 0) {
		range.setCount(DEFAULT_COUNT_RECORDS);
	    }
	    q.setMaxResults(range.getCount());
	    q.setFirstResult(range.getStart());
	}
	return q.getResultList();
    }

    protected List<Order> getOrderBy(final Path<ENTITY> root, final CriteriaBuilder cb) {
	return Collections.emptyList();
    }

    protected EntityGraph<? extends DomainObject> getFetchGraph() {
	return null;
    }

    public List<ENTITY> findAll() {
	return findRange(null);
    }

    public final long count() {
	final CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
	final CriteriaQuery<Long> criteriaQueryAll = criteriaBuilder.createQuery(Long.class);
	final Root<ENTITY> root = criteriaQueryAll.from(getEntityTypeClass());
	criteriaQueryAll.select(criteriaBuilder.count(root));
	return getEntityManager().createQuery(criteriaQueryAll).getSingleResult();
    }

    public final ENTITY findByUUID(final String uuid) {
	try {
	    final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
	    final CriteriaQuery<ENTITY> criteriaQuery = cb.createQuery(getEntityTypeClass());

	    final Root<ENTITY> root = criteriaQuery.from(getEntityTypeClass());
	    criteriaQuery.where(cb.equal(root.get("uuid"), cb.parameter(String.class, "uuid")));

	    final TypedQuery<ENTITY> q = getEntityManager().createQuery(criteriaQuery);
	    q.setParameter("uuid", uuid);
	    return q.getSingleResult();
	} catch (final NoResultException | NonUniqueResultException e) {
	    return null;
	}
    }

    public ENTITY find(final long id) {
	try {
	    return getEntityManager().find(getEntityTypeClass(), id);
	} catch (NoResultException e) {
	    log.warning("Not found entity with id " + id);
	    return null;
	}
    }

    public void lock(final ENTITY t) {
	getEntityManager().lock(t, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
    }

    public void lock(final ENTITY t, final LockModeType lockModeType) {
	getEntityManager().lock(t, lockModeType);
    }

    public void refresh(final ENTITY t) {
	getEntityManager().refresh(t);
    }

    /**
     * Initialises the internal <code>entityTypeClass</code> generic type.
     * 
     * @return the generic type from the generic type Entity
     */
    @SuppressWarnings("unchecked")
    protected Class<ENTITY> initGenericType() {
	ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
	return ((Class<ENTITY>) parameterizedType.getActualTypeArguments()[0]);
    }

    /**
     * 
     * @return the class of the generic type configured for the specific DAO class
     */
    public Class<ENTITY> getEntityTypeClass() {
	if (entityTypeClass == null) {
	    entityTypeClass = initGenericType();
	}
	return entityTypeClass;
    }
}