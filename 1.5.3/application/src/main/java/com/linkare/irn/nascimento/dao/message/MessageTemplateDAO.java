package com.linkare.irn.nascimento.dao.message;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;

import com.linkare.irn.nascimento.dao.GenericDAO;
import com.linkare.irn.nascimento.model.message.MessageTemplate;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 *
 */
public class MessageTemplateDAO extends GenericDAO<MessageTemplate> {

    public MessageTemplateDAO(final EntityManager entityManager) {
	super(entityManager);
    }

    public MessageTemplate findByCode(final String code) {
	try {
	    final TypedQuery<MessageTemplate> query = getEntityManager().createNamedQuery(MessageTemplate.QUERY_NAME_FIND_BY_CODE, MessageTemplate.class);
	    query.setParameter(MessageTemplate.QUERY_PARAM_CODE, code);
	    return query.getSingleResult();
	} catch (final NonUniqueResultException | NoResultException e) {
	    return null;
	}
    }
}