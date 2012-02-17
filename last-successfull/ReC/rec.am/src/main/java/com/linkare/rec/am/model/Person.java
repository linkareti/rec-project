package com.linkare.rec.am.model;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import com.linkare.commons.jpa.security.Subject;
import com.linkare.vt.EmailAddress;
import com.linkare.vt.PersonName;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
@Entity
@NamedQueries( { @NamedQuery(name = Person.FIND_ALL_QUERYNAME, query = Person.FIND_ALL_QUERY),
	@NamedQuery(name = Person.COUNT_ALL_QUERYNAME, query = Person.COUNT_ALL_QUERY) })
public class Person extends Subject {

    private static final long serialVersionUID = 1L;

    public static final String FIND_ALL_QUERYNAME = "Person.findAll";
    public static final String FIND_ALL_QUERY = "Select p from Person p";

    public static final String COUNT_ALL_QUERYNAME = "Person.countAll";
    public static final String COUNT_ALL_QUERY = "Select count(p) from Person p";

    @Embedded
    private PersonName name;

    @Embedded
    private EmailAddress emailAddress;

    /**
     * @return the name
     */
    public PersonName getName() {
	return name = name == null ? new PersonName() : name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(PersonName name) {
	this.name = name;
    }

    /**
     * @return the emailAddress
     */
    public EmailAddress getEmailAddress() {
	return emailAddress = emailAddress == null ? new EmailAddress() : emailAddress;
    }

    /**
     * @param emailAddress
     *            the emailAddress to set
     */
    public void setEmailAddress(EmailAddress emailAddress) {
	this.emailAddress = emailAddress;
    }
}