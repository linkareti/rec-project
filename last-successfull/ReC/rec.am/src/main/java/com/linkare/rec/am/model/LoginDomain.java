package com.linkare.rec.am.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.linkare.commons.jpa.DefaultDomainObject;
import com.linkare.commons.utils.EqualityUtils;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
@Entity
@Table(name = "LOGIN_DOMAIN")
@NamedQueries( { @NamedQuery(name = "LoginDomain.findAll", query = "Select l from LoginDomain l"),
	@NamedQuery(name = "LoginDomain.countAll", query = "Select count(l) from LoginDomain l") })
public class LoginDomain extends DefaultDomainObject {

    private static final long serialVersionUID = 1L;

    @Column(name = "NAME", insertable = true, updatable = true, nullable = false, unique = true)
    private String name;

    @Column(name = "URL", insertable = true, updatable = true, nullable = true)
    private String url;

    public LoginDomain() {
	super();
    }

    public LoginDomain(String name, String url) {
	this();
	this.name = name;
	this.url = url;
    }

    /**
     * @return the name
     */
    public String getName() {
	return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
	this.name = name;
    }

    /**
     * @return the url
     */
    public String getUrl() {
	return url;
    }

    /**
     * @param url
     *            the url to set
     */
    public void setUrl(String url) {
	this.url = url;
    }

    @Override
    public boolean equals(final Object other) {
	if (!(other instanceof LoginDomain)) {
	    return false;
	}
	return equalsTo((LoginDomain) other);
    }

    @Override
    public int hashCode() {
	int result = 14;
	result = 29 * result + (getName() != null ? getName().hashCode() : 0);
	return result;
    }

    private boolean equalsTo(final LoginDomain other) {
	return EqualityUtils.equals(getName(), other.getName());
    }

    @Override
    public String toString() {
	return getName();
    }
}