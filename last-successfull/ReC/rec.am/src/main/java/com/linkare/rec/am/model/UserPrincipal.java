package com.linkare.rec.am.model;

import java.io.Serializable;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import com.linkare.commons.jpa.security.Group;
import com.linkare.commons.jpa.security.User;
import com.linkare.commons.utils.EqualityUtils;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
@Entity
@NamedQueries( { @NamedQuery(name = "UserPrincipal.findAll", query = "Select up from UserPrincipal up"),
	@NamedQuery(name = "UserPrincipal.countAll", query = "Select count(up) from UserPrincipal up") })
public class UserPrincipal extends User implements Principal, Serializable {

    private static final long serialVersionUID = 1L;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "userPrincipal")
    private List<Reservation> reservations = new ArrayList<Reservation>();

    public UserPrincipal() {
	super();
    }

    public UserPrincipal(final String username) {
	super(null, username);
    }

    /**
     * @return the reservations
     */
    public List<Reservation> getReservations() {
	return reservations;
    }

    /**
     * @param reservations
     *            the reservations to set
     */
    public void setReservations(List<Reservation> reservations) {
	this.reservations = reservations;
    }

    @Override
    public boolean equals(final Object other) {
	if (!(other instanceof UserPrincipal)) {
	    return false;
	}
	return equalsTo((UserPrincipal) other);
    }

    @Override
    public int hashCode() {
	int result = 14;
	result = 29 * result + (getName() != null ? getName().hashCode() : 0);
	return result;
    }

    private boolean equalsTo(final UserPrincipal other) {
	return EqualityUtils.equals(getName(), other.getName());
    }

    @Override
    public String getName() {
	return getUsername();
    }

    public void setName(final String name) {
	setUsername(name);
    }

    public List<Group> getGroups() {
	return super.getAllParentGroups();
    }
}