package com.linkare.rec.am.service;

import static com.linkare.rec.am.model.Laboratory.COUNT_ALL_QUERYNAME;
import static com.linkare.rec.am.model.Laboratory.FIND_ALL_ACTIVE_QUERYNAME;
import static com.linkare.rec.am.model.Laboratory.FIND_ALL_QUERYNAME;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.Query;

import com.linkare.rec.am.model.Laboratory;

/**
 * 
 * @author Joao
 */
@Local(LaboratoryServiceLocal.class)
@Stateless(name = "LaboratoryService")
public class LaboratoryServiceBean extends BusinessServiceBean<Laboratory, Long> implements LaboratoryService {

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
	Query q = getEntityManager().createNamedQuery(FIND_ALL_QUERYNAME);
	if (!all) {
	    q.setMaxResults(maxResults);
	    q.setFirstResult(firstResult);
	}
	return q.getResultList();
    }

    @Override
    public int count() {
	final Query query = getEntityManager().createNamedQuery(COUNT_ALL_QUERYNAME);
	return ((Long) query.getSingleResult()).intValue();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Laboratory> findAllActive() {
	final Query q = getEntityManager().createNamedQuery(FIND_ALL_ACTIVE_QUERYNAME);
	return q.getResultList();
    }
}