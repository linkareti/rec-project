package com.linkare.irn.nascimento.dao.mapping;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;

import com.linkare.irn.nascimento.dao.GenericDAO;
import com.linkare.irn.nascimento.model.mapping.Mapper;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 *
 */
public class MapperDAO extends GenericDAO<Mapper> {

    public MapperDAO(final EntityManager entityManager) {
	super(entityManager);
    }

    public Mapper findByModuleAndInternalId(final Class<?> moduleClass, final Long id) {
	return findByModuleAndInternalId(moduleClass.getSimpleName(), id);
    }

    public Mapper findByModuleAndInternalId(final String module, final Long id) {
	try {
	    final TypedQuery<Mapper> query = getEntityManager().createNamedQuery(Mapper.QUERY_NAME_FIND_BY_MODULE_AND_INTERNAL_ID, Mapper.class);
	    query.setParameter(Mapper.QUERY_PARAM_MODULE, module);
	    query.setParameter(Mapper.QUERY_PARAM_INTERNAL_ID, id);
	    return query.getSingleResult();
	} catch (final NoResultException | NonUniqueResultException e) {
	    return null;
	}
    }

    public Mapper findByModuleAndExternalId(final Class<?> moduleClass, final String id) {
	return findByModuleAndExternalId(moduleClass.getSimpleName(), id);
    }

    public Mapper findByModuleAndExternalId(final String module, final String id) {
	try {
	    final TypedQuery<Mapper> query = getEntityManager().createNamedQuery(Mapper.QUERY_NAME_FIND_BY_MODULE_AND_EXTERNAL_ID, Mapper.class);
	    query.setParameter(Mapper.QUERY_PARAM_MODULE, module);
	    query.setParameter(Mapper.QUERY_PARAM_EXTERNAL_ID, id);
	    return query.getSingleResult();
	} catch (final NoResultException | NonUniqueResultException e) {
	    return null;
	}
    }
}