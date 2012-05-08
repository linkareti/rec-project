package com.linkare.rec.am.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.linkare.commons.jpa.DefaultDomainObject;

/**
 * Represents an error message generated from rec.am.
 * 
 * @author Bruno Catarino - Linkare TI
 * 
 */
@Entity
@Table(name = "ERROR_MESSAGE")
@NamedQueries({ @NamedQuery(name = ErrorMessage.FIND_ALL_QUERYNAME, query = ErrorMessage.FIND_ALL_QUERY),
	@NamedQuery(name = ErrorMessage.COUNT_ALL_QUERYNAME, query = ErrorMessage.COUNT_ALL_QUERY) })
public class ErrorMessage extends DefaultDomainObject {

    private static final long serialVersionUID = -6390113356819021981L;

    public static final String FIND_ALL_QUERYNAME = "ErrorMessage.findAll";
    public static final String FIND_ALL_QUERY = "select e from ErrorMessage e";

    public static final String COUNT_ALL_QUERYNAME = "ErrorMessage.countAll";
    public static final String COUNT_ALL_QUERY = "select count(e) from ErrorMessage e";

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private long messageId;

    @Column(name = "MESSAGE", length = 500)
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(name = "CATEGORY")
    private MessageCategory category;

    /**
     * The date when the error message occurred.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_OF_ERROR")
    private Date dateError;

    public long getMessageId() {
	return messageId;
    }

    public String getMessage() {
	return message;
    }

    public void setMessage(String message) {
	this.message = message;
    }

    public MessageCategory getCategory() {
	return category;
    }

    public void setCategory(MessageCategory category) {
	this.category = category;
    }

    public Date getDateError() {
	return dateError;
    }

    public void setDateError(Date dateError) {
	this.dateError = dateError;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + (int) (messageId ^ (messageId >>> 32));
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	ErrorMessage other = (ErrorMessage) obj;
	if (messageId != other.messageId)
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "ErrorMessage [messageId=" + messageId + ", message=" + message + ", category=" + category + ", dateError=" + dateError + "]";
    }
}