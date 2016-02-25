package com.linkare.rec.web.service;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.Query;

import com.linkare.rec.web.model.ErrorMessage;

/**
 * 
 * @author Bruno Catarino - Linkare TI
 */
@Local(ErrorMessageServiceLocal.class)
@Stateless(name = "ErrorMessageService")
public class ErrorMessageServiceBean extends BusinessServiceBean<ErrorMessage, Long> implements ErrorMessageServiceLocal {

    @Override
    public void create(final ErrorMessage errorMessage) {
	getEntityManager().persist(errorMessage);
    }

    @Override
    public ErrorMessage edit(final ErrorMessage errorMessage) {
	return getEntityManager().merge(errorMessage);
    }

    @Override
    public ErrorMessage find(final Long id) {
	return getEntityManager().find(ErrorMessage.class, id);
    }

    @Override
    public List<ErrorMessage> findRange(final int[] range) {
	return find(false, range[0], range[1]);
    }

    @Override
    public List<ErrorMessage> findAll() {
	return find(true, -1, -1);
    }

    @SuppressWarnings("unchecked")
    public List<ErrorMessage> find(final boolean all, final int firstResult, final int maxResults) {
	Query q = getEntityManager().createNamedQuery(ErrorMessage.FIND_ALL_QUERYNAME);
	if (!all) {
	    q.setMaxResults(maxResults);
	    q.setFirstResult(firstResult);
	}
	return q.getResultList();
    }

    @Override
    public int count() {
	final Query query = getEntityManager().createNamedQuery(ErrorMessage.COUNT_ALL_QUERYNAME);
	return ((Long) query.getSingleResult()).intValue();
    }
}