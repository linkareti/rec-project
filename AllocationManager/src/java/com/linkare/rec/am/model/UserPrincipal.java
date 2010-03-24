package com.linkare.rec.am.model;

import java.io.Serializable;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

/**
 *
 * @author Joao
 */
@Entity
public class UserPrincipal extends Resource implements Principal ,Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String name;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "userPrincipal")
    private List<Reservation> reservations = new ArrayList<Reservation>();

    @JoinTable(name = "USERPRINCIPAL_USERGROUP", joinColumns =
    @JoinColumn(name = "USERPRINCIPAL_ID", referencedColumnName = "NAME"),
    inverseJoinColumns =
    @JoinColumn(name = "USERGROUP_ID", referencedColumnName = "NAME"))
    @ManyToMany(mappedBy="members", cascade=CascadeType.ALL)
    private List<UserGroup> groups = new ArrayList<UserGroup>();

    /**
     * @return the groups
     */
    public List<UserGroup> getGroups() {
        return groups;
    }

    /**
     * @param groups the groups to set
     */
    public void setGroups(List<UserGroup> groups) {
        this.groups = groups;
    }

    @Override
    public String getName() {
        return name;
    }

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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (name != null ? name.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof UserPrincipal)) {
            return false;
        }
        UserPrincipal other = (UserPrincipal) object;
        if ((this.name == null && other.name != null) || (this.name != null && !this.name.equals(other.name))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name;
    }
}
