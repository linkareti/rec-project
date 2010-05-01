/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.linkare.rec.am.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.linkare.commons.jpa.Identifiable;

/**
 * 
 * It provides the methods that should be implemented by all entity facades.
 * 
 * @author Paulo Zenida - Linkare TI
 */
public abstract class Facade<T extends Identifiable<ID>, ID extends Serializable> {

    @PersistenceContext(unitName = "AllocationManagerPU")
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
	getEntityManager().remove(mergedEntity);
    }

    public abstract T find(ID id);

    public abstract List<T> findAll();

    public abstract List<T> findRange(int[] range);

    public abstract int count();
}