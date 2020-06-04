package com.linkare.irn.nascimento.dao.geographic;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;

import org.apache.commons.lang.StringUtils;

import com.linkare.irn.nascimento.dao.GenericDAO;
import com.linkare.irn.nascimento.model.DomainObject;
import com.linkare.irn.nascimento.model.geographic.County_;
import com.linkare.irn.nascimento.model.geographic.District_;
import com.linkare.irn.nascimento.model.geographic.Parish;
import com.linkare.irn.nascimento.model.geographic.Parish_;
import com.linkare.irn.nascimento.util.SelectionRange;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public class ParishDAO extends GenericDAO<Parish> {

    /**
     * @param entityManager
     */
    public ParishDAO(EntityManager entityManager) {
	super(entityManager);
    }

    @Override
    protected List<Order> getOrderBy(final Path<Parish> root, final CriteriaBuilder cb) {
	return Collections.singletonList(cb.asc(root.get(Parish_.code)));
    }

    @Override
    protected EntityGraph<? extends DomainObject> getFetchGraph() {
	final EntityGraph<Parish> fetchGraph = getEntityManager().createEntityGraph(Parish.class);
	fetchGraph.addSubgraph(Parish_.county).addSubgraph(County_.district).addSubgraph(District_.country);
	return fetchGraph;
    }

    public Parish findByCode(final String code) {
	try {
	    final TypedQuery<Parish> query = getEntityManager().createNamedQuery(Parish.QUERY_NAME_FIND_BY_CODE, Parish.class);
	    query.setParameter(Parish.QUERY_PARAM_CODE, code);
	    return query.getSingleResult();
	} catch (final NoResultException | NonUniqueResultException e) {
	    return null;
	}
    }

    public Parish findByName(final String name) {
	try {
	    final TypedQuery<Parish> query = getEntityManager().createNamedQuery(Parish.QUERY_NAME_FIND_BY_NAME, Parish.class);
	    query.setParameter(Parish.QUERY_PARAM_NAME, name);
	    return query.getSingleResult();
	} catch (final NoResultException | NonUniqueResultException e) {
	    return null;
	}
    }

    public List<Parish> findAllWithNameLikeInFullPath(final String name, final SelectionRange range) {
	final TypedQuery<Parish> q = getEntityManager().createNamedQuery(Parish.QUERY_NAME_FIND_ALL_LIKE_NAME_IN_FULL_PATH, Parish.class);
	q.setParameter(Parish.QUERY_PARAM_NAME, "%" + StringUtils.defaultString(name).toLowerCase() + "%");

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

    public List<Parish> findActiveWithNameLikeInFullPath(final String name, final SelectionRange range) {
	final TypedQuery<Parish> q = getEntityManager().createNamedQuery(Parish.QUERY_NAME_FIND_ACTIVE_LIKE_NAME_IN_FULL_PATH, Parish.class);
	q.setParameter(Parish.QUERY_PARAM_NAME, "%" + StringUtils.defaultString(name).toLowerCase() + "%");

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
}