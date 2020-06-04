package com.linkare.irn.nascimento.service.security;

import java.util.Date;
import java.util.UUID;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import com.linkare.irn.nascimento.dao.security.TicketDAO;
import com.linkare.irn.nascimento.model.security.Ticket;
import com.linkare.irn.nascimento.model.security.TicketType;
import com.linkare.irn.nascimento.service.BaseStatelessServiceImpl;
import com.linkare.irn.nascimento.web.ConfigurationManager;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class TicketService extends BaseStatelessServiceImpl<Ticket, TicketDAO> {

    @Inject
    private ConfigurationManager configurationManager;

    @Override
    public TicketDAO getDAO() {
	return new TicketDAO(getEntityManager());
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Ticket create(final String username, final TicketType type) {
	final Ticket ticket = new Ticket(UUID.randomUUID().toString(), username, type);
	ticket.setExpirationDate(getExpirationDate(type));
	return super.create(ticket);
    }

    protected Date getExpirationDate(final TicketType type) {
	switch (type) {
	case PASSWORD_RECOVERY:
	    final long currentTime = System.currentTimeMillis();
	    final long expirationTime = currentTime + configurationManager.getRecoverPasswordTimeoutMillis();
	    return new Date(expirationTime);
	default:
	    return null;
	}
    }

    public Ticket findValidByUsernameAndToken(final String username, final String token) {
	return getDAO().findValidByUsernameAndToken(username, token);
    }
}