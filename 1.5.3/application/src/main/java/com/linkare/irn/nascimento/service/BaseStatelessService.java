package com.linkare.irn.nascimento.service;

import java.util.List;

import javax.persistence.LockModeType;

import com.linkare.irn.nascimento.dao.GenericDAO;
import com.linkare.irn.nascimento.model.DomainException;
import com.linkare.irn.nascimento.model.DomainObject;
import com.linkare.irn.nascimento.util.SelectionRange;

/**
 * @author Paulo Zenida - Linkare TI
 *
 * @param <ENTITY>
 * @param <DAO>
 */
public interface BaseStatelessService<ENTITY extends DomainObject, DAO extends GenericDAO<ENTITY>> {

    public ENTITY create(ENTITY t, final Class<?>... validationGroups) throws DomainException;

    public ENTITY update(ENTITY t, final Class<?>... validationGroups) throws DomainException;

    public void remove(ENTITY t) throws DomainException;

    public void remove(long id) throws DomainException;

    public List<ENTITY> findRange(SelectionRange range);

    public long count();

    public ENTITY find(long id);

    public ENTITY findByUUID(final String uuid);

    public void lock(ENTITY t);

    public void lock(ENTITY t, LockModeType lockModeType);
}