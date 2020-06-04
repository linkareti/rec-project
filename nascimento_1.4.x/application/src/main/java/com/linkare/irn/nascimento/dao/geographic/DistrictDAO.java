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
import com.linkare.irn.nascimento.model.geographic.District;
import com.linkare.irn.nascimento.model.geographic.District_;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public class DistrictDAO extends GenericDAO<District> {

    /**
     * @param entityManager
     */
    public DistrictDAO(EntityManager entityManager) {
	super(entityManager);
    }

    @Override
    protected List<Order> getOrderBy(final Path<District> root, final CriteriaBuilder cb) {
	return Collections.singletonList(cb.asc(root.get(District_.code)));
    }

    @Override
    protected EntityGraph<? extends DomainObject> getFetchGraph() {
	final EntityGraph<District> fetchGraph = getEntityManager().createEntityGraph(District.class);
	fetchGraph.addSubgraph(District_.country);
	return fetchGraph;
    }

    public District findByCode(final String code) {
	try {
	    final TypedQuery<District> query = getEntityManager().createNamedQuery(District.QUERY_NAME_FIND_BY_CODE, District.class);
	    query.setParameter(District.QUERY_PARAM_CODE, code);
	    return query.getSingleResult();
	} catch (final NoResultException | NonUniqueResultException e) {
	    return null;
	}
    }
}