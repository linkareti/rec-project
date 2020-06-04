package com.linkare.irn.nascimento.model.security;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.linkare.irn.nascimento.model.DomainObject;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 *
 */
@Entity
@NamedQueries({
	/* @formatter:off */
	@NamedQuery(name = Ticket.QUERY_NAME_FIND_VALID_BY_USERNAME_AND_TOKEN, query = "select t from Ticket t "
		+ "where t.status = com.linkare.irn.nascimento.model.security.TicketStatus.PENDING "
		+ "and (t.expirationDate is null or t.expirationDate > CURRENT_TIMESTAMP) "
		+ "and t.username = :" + Ticket.QUERY_PARAM_USERNAME + " and t.token = :" + Ticket.QUERY_PARAM_TOKEN) 
	/* @formatter:off */
	})
@Table(name = "ticket")
public class Ticket extends DomainObject {

    private static final long serialVersionUID = 6587857736083908310L;

    public static final String QUERY_NAME_FIND_VALID_BY_USERNAME_AND_TOKEN = "User.findValidByUsernameAndType";

    public static final String QUERY_PARAM_USERNAME = "username";

    public static final String QUERY_PARAM_TOKEN = "token";

    @Column(name = "TOKEN")
    @NotNull
    @JsonIgnore
    private String token;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    @NotNull
    private TicketStatus status = TicketStatus.PENDING;

    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    @NotNull
    private TicketType type = TicketType.PASSWORD_RECOVERY;

    @Column(name = "EXPIRATION_DATE")
    private Date expirationDate;

    public Ticket() {
	super();
    }

    public Ticket(final String token, final String username, final TicketType type) {
	super();
	this.token = token;
	this.username = username;
	this.type = type;
    }

    /**
     * @return the token
     */
    public String getToken() {
	return token;
    }

    /**
     * @param token
     *            the token to set
     */
    public void setToken(String token) {
	this.token = token;
    }

    /**
     * @return the username
     */
    public String getUsername() {
	return username;
    }

    /**
     * @param username
     *            the username to set
     */
    public void setUsername(String username) {
	this.username = username;
    }

    /**
     * @return the status
     */
    public TicketStatus getStatus() {
	return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(TicketStatus status) {
	this.status = status;
    }

    /**
     * @return the type
     */
    public TicketType getType() {
	return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(TicketType type) {
	this.type = type;
    }

    /**
     * @return the expirationDate
     */
    public Date getExpirationDate() {
	return expirationDate;
    }

    /**
     * @param expirationDate
     *            the expirationDate to set
     */
    public void setExpirationDate(Date expirationDate) {
	this.expirationDate = expirationDate;
    }
}