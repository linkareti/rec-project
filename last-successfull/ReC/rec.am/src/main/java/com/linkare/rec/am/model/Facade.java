/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.linkare.rec.am.model;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.linkare.commons.jpa.Identifiable;

/**
 * 
 * This abstract class provides the methods that should be implemented by all entity facades.
 * 
 * @author Paulo Zenida - Linkare TI
 */
public abstract class Facade<T extends Identifiable<?>> {

    @PersistenceContext(unitName = "AllocationManagerPU")
    protected EntityManager em;

    public abstract void create(T t);

    public abstract T edit(T t);

    public abstract void remove(T t);

    public abstract T find(Object id);

    public abstract List<T> findAll();

    public abstract List<T> findRange(int[] range);

    public abstract int count();
}
