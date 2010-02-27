package com.linkare.rec.am.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

/**
 *
 * @author Joao
 */
@Entity
public class UserGroup implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String name;
    @ManyToMany(mappedBy = "groups")
    private Set<UserPrincipal> members = new HashSet<UserPrincipal>();
    @Basic
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "group")
    private List<Reservation> reservations = new ArrayList<Reservation>();

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (name != null ? name.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof UserGroup)) {
            return false;
        }
        UserGroup other = (UserGroup) object;
        if ((this.name == null && other.name != null) || (this.name != null && !this.name.equals(other.name))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name;
    }

    public boolean addMember(UserPrincipal user) {
        return members.add(user);
    }

    public boolean removeMember(UserPrincipal user) {
        return members.remove(user);
    }

    public boolean isMember(UserPrincipal member) {
        return members.contains(member);
    }

    /**
     * @return the members
     */
    public Set<UserPrincipal> members() {
        return members;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the reservations
     */
    public List<Reservation> getReservations() {
        return reservations;
    }

    /**
     * @param reservations the reservations to set
     */
    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    /**
     * @param reservations the reservations to set
     */
    public void addReservations(Reservation reservation) {
        this.reservations.add(reservation);
    }
}
