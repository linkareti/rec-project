package com.linkare.irn.nascimento.dao.organization;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;

import com.linkare.irn.nascimento.dao.GenericDAO;
import com.linkare.irn.nascimento.model.organization.Organization;
import com.linkare.irn.nascimento.model.organization.OrganizationType;
import com.linkare.irn.nascimento.model.organization.Organization_;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public class OrganizationDAO extends GenericDAO<Organization> {

	/**
	 * @param entityManager
	 */
	public OrganizationDAO(final EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	protected List<Order> getOrderBy(final Path<Organization> root, final CriteriaBuilder cb) {
		return Collections.singletonList(cb.asc(root.get(Organization_.name)));
	}

	public List<Organization> findActiveByType(final OrganizationType type) {

		final TypedQuery<Organization> query = getEntityManager()
				.createNamedQuery(Organization.QUERY_NAME_FIND_ACTIVE_BY_TYPE, Organization.class);

		query.setParameter(Organization.QUERY_PARAM_TYPE, type);

		return query.getResultList();
	}

	public Organization findyActiveAndTypeAndEmail(final OrganizationType type, final String email) {

		final TypedQuery<Organization> query = getEntityManager()
				.createNamedQuery(Organization.QUERY_FIND_ACTIVE_BY_TYPE_AND_EMAIL, Organization.class);

		query.setParameter(Organization.QUERY_PARAM_TYPE, type);
		query.setParameter(Organization.QUERY_PARAM_EMAIL, email);

		return query.getSingleResult();
	}
	
	public List<Organization> findyActivesAndTypeAndEmail(final OrganizationType type, final String email) {

		final TypedQuery<Organization> query = getEntityManager()
				.createNamedQuery(Organization.QUERY_FIND_ACTIVE_BY_TYPE_AND_EMAIL, Organization.class);

		query.setParameter(Organization.QUERY_PARAM_TYPE, type);
		query.setParameter(Organization.QUERY_PARAM_EMAIL, email);

		return query.getResultList();
	}
}