package com.linkare.irn.nascimento.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.slf4j.Logger;

import com.linkare.irn.nascimento.dao.GenericDAO;
import com.linkare.irn.nascimento.model.DomainObject;
import com.linkare.irn.nascimento.util.SelectionRange;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 *
 */
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public abstract class BaseStatelessServiceImpl<ENTITY extends DomainObject, DAO extends GenericDAO<ENTITY>> implements BaseStatelessService<ENTITY, DAO> {

    @Inject
    private transient EntityManager em;

    @Inject
    private Validator validator;

    @Inject
    private Logger log;

    public abstract DAO getDAO();

    protected EntityManager getEntityManager() {
	return em;
    }

    /**
     * @return the log
     */
    public Logger getLog() {
	return log;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ENTITY create(final ENTITY t, final Class<?>... validationGroups) {
	getDAO().create(t);
	return t;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ENTITY update(final ENTITY t, final Class<?>... validationGroups) {
	return getDAO().update(t);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void remove(final ENTITY t) {
	getDAO().remove(t);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void remove(final long id) {
	getDAO().remove(id);
    }

    @Override
    public List<ENTITY> findRange(SelectionRange range) {
	return getDAO().findRange(range);
    }

    @Override
    public long count() {
	return getDAO().count();
    }

    @Override
    public ENTITY find(final long id) {
	return getDAO().find(id);
    }

    @Override
    public ENTITY findByUUID(final String uuid) {
	return getDAO().findByUUID(uuid);
    }

    @Override
    public void lock(final ENTITY t) {
	getDAO().lock(t);
    }

    @Override
    public void lock(final ENTITY t, final LockModeType lockModeType) {
	getDAO().lock(t, lockModeType);
    }

    public List<ENTITY> findAll() {
	return getDAO().findAll();
    }

    protected void validate(final ENTITY entity, final Class<?>... validationGroups) {
	final Set<ConstraintViolation<ENTITY>> violations = validator.validate(entity);

	if (!violations.isEmpty()) {
	    throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
	}
    }
}