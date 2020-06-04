package com.linkare.irn.nascimento.model.message;

import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.InternetAddress;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.linkare.irn.nascimento.model.DomainObject;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 *
 */
@Entity
@Table(name = "email_message")
@NamedQueries({
	@NamedQuery(name = EmailMessage.QUERY_NAME_FIND_PENDING, query = "select m from EmailMessage m where m.status = com.linkare.irn.nascimento.model.message.MessageStatus.PENDING order by m.creationDate")

})
public class EmailMessage extends DomainObject implements IMessage<InternetAddress, InternetAddress> {

    private static final long serialVersionUID = 1L;

    public static final String QUERY_NAME_FIND_PENDING = "EmailMessage.findPending";

    @Column(name = "origin", length = 200, columnDefinition = "varchar(200)")
    @NotNull
    private InternetAddress origin;

    @Column(name = "destinations", length = 1500, columnDefinition = "varchar(1500)")
    @Size(min = 1, max = 5)
    private List<InternetAddress> destinations = new ArrayList<>();

    @Column(name = "subject", length = 150)
    @NotNull
    private String subject;

    @Column(name = "content", columnDefinition = "text")
    @NotNull
    private String content;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @NotNull
    private MessageStatus status;

    @Column(name = "username", length = 75)
    @NotNull
    private String username;

    @Column(name = "display_name", length = 75)
    @NotNull
    private String displayName;
    
    @Column(name = "attempt")
    private Integer attempt;
    
    
    

    public Integer getAttempt() {
		return attempt;
	}

	public void setAttempt(Integer attempt) {
		this.attempt = attempt;
	}

	public EmailMessage() {
	super();
	this.destinations = new ArrayList<>();
	this.status = MessageStatus.PENDING;
    }

    public EmailMessage(InternetAddress origin) {
	this.origin = origin;
	this.destinations = new ArrayList<>();
	this.status = MessageStatus.PENDING;
    }

    /**
     * @return the origin
     */
    @Override
    public InternetAddress getOrigin() {
	return origin;
    }

    /**
     * @param origin
     *            the origin to set
     */
    public void setOrigin(InternetAddress origin) {
	this.origin = origin;
    }

    /**
     * @return the destinations
     */
    @Override
    public List<InternetAddress> getDestinations() {
	return destinations;
    }

    @Override
    public void addDestination(final InternetAddress destination) {
	this.destinations.add(destination);
    }

    /**
     * @return the subject
     */
    public String getSubject() {
	return subject;
    }

    /**
     * @param subject
     *            the subject to set
     */
    public void setSubject(String subject) {
	this.subject = subject;
    }

    /**
     * @return the content
     */
    @Override
    public String getContent() {
	return content;
    }

    /**
     * @param content
     *            the content to set
     */
    @Override
    public void setContent(String content) {
	this.content = content;
    }

    /**
     * @return the status
     */
    @Override
    public MessageStatus getStatus() {
	return status;
    }

    /**
     * @param status
     *            the status to set
     */
    @Override
    public void setStatus(MessageStatus status) {
	this.status = status;
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
     * @return the displayName
     */
    public String getDisplayName() {
	return displayName;
    }

    /**
     * @param displayName
     *            the displayName to set
     */
    public void setDisplayName(String displayName) {
	this.displayName = displayName;
    }

    @Override
    public void prePersist() {
	super.prePersist();
	if (status == null) {
	    status = MessageStatus.PENDING;
	}
    }
}