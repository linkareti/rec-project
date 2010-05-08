package com.linkare.rec.am.service;

import java.io.Serializable;
import java.util.List;

import com.linkare.commons.jpa.Deletable;
import com.linkare.commons.jpa.Identifiable;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 * @param <T>
 * @param <ID>
 */
public interface BusinessService<T extends Identifiable<ID> & Deletable, ID extends Serializable> {

    public void create(T t);

    public T edit(T t);

    public void remove(T t);

    public T find(ID id);

    public List<T> findAll();

    public List<T> findRange(int[] range);

    public int count();
}