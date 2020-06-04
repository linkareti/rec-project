package com.linkare.irn.nascimento.dao.security;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;

import com.linkare.irn.nascimento.dao.GenericDAO;
import com.linkare.irn.nascimento.model.security.Ticket;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public class TicketDAO extends GenericDAO<Ticket> {

    /**
     * @param entityManager
     */
    public TicketDAO(EntityManager entityManager) {
	super(entityManager);
    }

    public Ticket findValidByUsernameAndToken(final String username, final String token) {
	try {
	    final TypedQuery<Ticket> q = getEntityManager().createNamedQuery(Ticket.QUERY_NAME_FIND_VALID_BY_USERNAME_AND_TOKEN, Ticket.class);
	    q.setParameter(Ticket.QUERY_PARAM_USERNAME, username);
	    q.setParameter(Ticket.QUERY_PARAM_TOKEN, token);
	    return q.getSingleResult();
	} catch (final NoResultException | NonUniqueResultException e) {
	    return null;
	}
    }
}