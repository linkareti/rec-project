package com.linkare.rec.am.model;

import javax.persistence.Embedded;

import com.linkare.commons.jpa.security.Subject;
import com.linkare.vt.Address;
import com.linkare.vt.EmailAddress;
import com.linkare.vt.PersonName;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
public class Person extends Subject {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Embedded
    private PersonName name;

    @Embedded
    private EmailAddress email;

    @Embedded
    private Address address;

    /**
     * @return the name
     */
    public PersonName getName() {
	return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(PersonName name) {
	this.name = name;
    }

    /**
     * @return the email
     */
    public EmailAddress getEmail() {
	return email;
    }

    /**
     * @param email
     *            the email to set
     */
    public void setEmail(EmailAddress email) {
	this.email = email;
    }

    /**
     * @return the address
     */
    public Address getAddress() {
	return address;
    }

    /**
     * @param address
     *            the address to set
     */
    public void setAddress(Address address) {
	this.address = address;
    }
}