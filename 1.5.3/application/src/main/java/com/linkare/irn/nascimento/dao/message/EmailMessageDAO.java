package com.linkare.irn.nascimento.dao.message;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.linkare.irn.nascimento.dao.GenericDAO;
import com.linkare.irn.nascimento.model.message.EmailMessage;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 *
 */
public class EmailMessageDAO extends GenericDAO<EmailMessage> {

    private static final int MAX_MESSAGES_TO_PROCESS = 20;

    public EmailMessageDAO(final EntityManager entityManager) {
	super(entityManager);
    }

    public List<EmailMessage> findPending() {
	try {
	    final TypedQuery<EmailMessage> query = getEntityManager().createNamedQuery(EmailMessage.QUERY_NAME_FIND_PENDING, EmailMessage.class);
	    query.setFirstResult(0);
	    query.setMaxResults(MAX_MESSAGES_TO_PROCESS);
	    return query.getResultList();
	} catch (final Exception e) {
	    return Collections.emptyList();
	}
    }
}