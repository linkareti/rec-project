package com.linkare.rec.am.model;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 * 
 * @author Joao
 */
@Stateless
public class LaboratoryFacade extends Facade<Laboratory> {

    @Override
    public void create(Laboratory laboratory) {
	em.persist(laboratory);
    }

    @Override
    public void edit(Laboratory laboratory) {
	em.merge(laboratory);
    }

    @Override
    public void remove(Laboratory laboratory) {
	em.remove(em.merge(laboratory));
    }

    @Override
    public Laboratory find(Object id) {
	return em.find(Laboratory.class, id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Laboratory> findAll() {
	final Query query = em.createNamedQuery("Laboratory.findAll");
	return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Laboratory> findRange(int[] range) {
	final Query query = em.createNamedQuery("Laboratory.findAll");
	query.setMaxResults(range[1] - range[0]);
	query.setFirstResult(range[0]);
	return query.getResultList();
    }

    @Override
    public int count() {
	final Query query = em.createNamedQuery("Laboratory.countAll");
	return ((Long) query.getSingleResult()).intValue();
    }
}
