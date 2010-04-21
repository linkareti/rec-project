package com.linkare.rec.am.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.linkare.commons.jpa.security.Group;
import com.linkare.commons.jpa.security.User;
import com.linkare.commons.utils.EqualityUtils;

/**
 * 
 * @author Joao
 */
@Entity
@NamedQueries( { @NamedQuery(name = "UserGroup.findAll", query = "Select ug from UserGroup as ug order by ug.name"),
	@NamedQuery(name = "UserGroup.countAll", query = "Select count(ug) from UserGroup ug") })
public class UserGroup extends Group implements Serializable {

    private static final long serialVersionUID = 1L;

    @Transient
    private List<UserPrincipal> nonMembers;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userGroup")
    private List<Reservation> reservations = new ArrayList<Reservation>();

    @Override
    public boolean equals(final Object other) {
	if (!(other instanceof UserGroup)) {
	    return false;
	}
	return equalsTo((UserGroup) other);
    }

    @Override
    public int hashCode() {
	int result = 14;
	result = 29 * result + (getName() != null ? getName().hashCode() : 0);
	return result;
    }

    private boolean equalsTo(final UserGroup other) {
	return EqualityUtils.equals(getName(), other.getName());
    }

    public boolean hasMembers() {
	return hasAnyChild();
    }

    public void addMember(UserPrincipal user) {
	addDirectChild(user);
    }

    public void removeMember(UserPrincipal user) {
	removeDirectChild(user);
    }

    public boolean isMember(UserPrincipal member) {
	return super.isMemberOf(member);
    }

    /**
     * @return the members
     */
    public List<UserPrincipal> getMembers() {
	final List<UserPrincipal> result = new ArrayList<UserPrincipal>();
	for (final User user : getAllUsers()) {
	    if (user instanceof UserPrincipal) {
		result.add((UserPrincipal) user);
	    }
	}
	return result;
    }

    /**
     * @return the members
     */
    public void setMembers(final List<UserPrincipal> users) {
	getDirectChilds().addAll(users);
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

    /**
     * @param reservations
     *            the reservations to set
     */
    public void addReservations(Reservation reservation) {
	this.reservations.add(reservation);
    }

    /**
     * @return the nonMembers
     */
    public List<UserPrincipal> getNonMembers() {
	return nonMembers;
    }

    /**
     * @param nonMembers
     *            the nonMembers to set
     */
    public void setNonMembers(List<UserPrincipal> nonMembers) {
	this.nonMembers = nonMembers;
    }
}