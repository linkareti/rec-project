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

import com.linkare.irn.nascimento.dao.GenericDAO;
import com.linkare.irn.nascimento.model.DomainObject;
import com.linkare.irn.nascimento.model.geographic.County;
import com.linkare.irn.nascimento.model.geographic.County_;
import com.linkare.irn.nascimento.model.geographic.District_;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public class CountyDAO extends GenericDAO<County> {

    /**
     * @param entityManager
     */
    public CountyDAO(EntityManager entityManager) {
	super(entityManager);
    }

    @Override
    protected List<Order> getOrderBy(final Path<County> root, final CriteriaBuilder cb) {
	return Collections.singletonList(cb.asc(root.get(County_.code)));
    }

    @Override
    protected EntityGraph<? extends DomainObject> getFetchGraph() {
	final EntityGraph<County> fetchGraph = getEntityManager().createEntityGraph(County.class);
	fetchGraph.addSubgraph(County_.district).addSubgraph(District_.country);
	return fetchGraph;
    }

    public County findByCode(final String code) {
	try {
	    final TypedQuery<County> query = getEntityManager().createNamedQuery(County.QUERY_NAME_FIND_BY_CODE, County.class);
	    query.setParameter(County.QUERY_PARAM_CODE, code);
	    return query.getSingleResult();
	} catch (final NoResultException | NonUniqueResultException e) {
	    return null;
	}
    }
}