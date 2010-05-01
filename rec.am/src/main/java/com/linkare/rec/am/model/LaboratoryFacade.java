package com.linkare.rec.am.model;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 * 
 * @author Joao
 */
@Stateless(name = "LaboratoryFacade")
public class LaboratoryFacade extends Facade<Laboratory, Long> {

    @Override
    public void create(final Laboratory laboratory) {
	getEntityManager().persist(laboratory);
    }

    @Override
    public Laboratory find(final Long id) {
	return getEntityManager().find(Laboratory.class, id);
    }

    @Override
    public List<Laboratory> findRange(final int[] range) {
	return find(false, range[0], range[1]);
    }

    @Override
    public List<Laboratory> findAll() {
	return find(true, -1, -1);
    }

    @SuppressWarnings("unchecked")
    public List<Laboratory> find(final boolean all, final int firstResult, final int maxResults) {
	Query q = getEntityManager().createNamedQuery("Laboratory.findAll");
	if (!all) {
	    q.setMaxResults(maxResults);
	    q.setFirstResult(firstResult);
	}
	return q.getResultList();
    }

    @Override
    public int count() {
	final Query query = getEntityManager().createNamedQuery("Laboratory.countAll");
	return ((Long) query.getSingleResult()).intValue();
    }
}