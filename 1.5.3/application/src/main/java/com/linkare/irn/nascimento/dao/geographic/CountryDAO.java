package com.linkare.irn.nascimento.dao.geographic;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;

import com.linkare.irn.nascimento.dao.GenericDAO;
import com.linkare.irn.nascimento.model.geographic.Country;
import com.linkare.irn.nascimento.model.geographic.Country_;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public class CountryDAO extends GenericDAO<Country> {

    /**
     * @param entityManager
     */
    public CountryDAO(EntityManager entityManager) {
	super(entityManager);
    }

    @Override
    protected List<Order> getOrderBy(final Path<Country> root, final CriteriaBuilder cb) {
	return Collections.singletonList(cb.asc(root.get(Country_.name)));
    }

    public Country findByCode(final String code) {
	try {
	    final TypedQuery<Country> query = getEntityManager().createNamedQuery(Country.QUERY_NAME_FIND_BY_CODE, Country.class);
	    query.setParameter(Country.QUERY_PARAM_CODE, code);
	    return query.getSingleResult();
	} catch (final NoResultException | NonUniqueResultException e) {
	    return null;
	}
    }
}