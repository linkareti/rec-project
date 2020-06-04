package com.linkare.irn.nascimento.dao.core;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;

import com.linkare.irn.nascimento.dao.GenericDAO;
import com.linkare.irn.nascimento.model.core.Counter;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public class CounterDAO extends GenericDAO<Counter> {

    /**
     * @param entityManager
     */
    public CounterDAO(EntityManager entityManager) {
	super(entityManager);
    }

    public Counter findByCode(final String code) {
	try {
	    final TypedQuery<Counter> q = getEntityManager().createNamedQuery(Counter.QUERY_NAME_FIND_BY_CODE, Counter.class);
	    q.setParameter(Counter.QUERY_PARAM_CODE, code);
	    return q.getSingleResult();
	} catch (final NoResultException | NonUniqueResultException e) {
	    return null;
	}
    }
}