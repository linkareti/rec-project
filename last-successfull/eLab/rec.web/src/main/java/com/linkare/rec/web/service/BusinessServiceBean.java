/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.linkare.rec.web.service;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.linkare.commons.dao.Deletable;
import com.linkare.commons.dao.Identifiable;

/**
 * 
 * It provides the methods that should be implemented by all entity facades.
 * 
 * @author Paulo Zenida - Linkare TI
 */
public abstract class BusinessServiceBean<T extends Identifiable<ID> & Deletable, ID extends Serializable> implements BusinessService<T, ID> {

    @PersistenceContext(unitName = "RecPU")
    private EntityManager entityManager;

    /**
     * @return the entityManager
     */
    public EntityManager getEntityManager() {
	return entityManager;
    }

    /**
     * @param entityManager
     *            the entityManager to set
     */
    public void setEntityManager(EntityManager entityManager) {
	this.entityManager = entityManager;
    }

    public void create(T t) {
	getEntityManager().persist(t);
    }

    public T edit(T t) {
	return getEntityManager().merge(t);
    }

    public void remove(T t) {
	final T mergedEntity = getEntityManager().merge(t);
	mergedEntity.delete();
	getEntityManager().remove(mergedEntity);
    }

    public abstract T find(ID id);

    public abstract List<T> findAll();

    public abstract List<T> findRange(int[] range);

    public abstract int count();
}