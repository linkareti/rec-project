/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.linkare.rec.am.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Basic;
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
public class UserPrincipal implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private String name;

    @Basic
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "reservation")
    private List<Reservation> reservations = new ArrayList<Reservation>();

    @JoinTable(name = "USER_GROUPS", joinColumns=
            @JoinColumn(name="USER_ID", referencedColumnName="NAME"),
        inverseJoinColumns=
            @JoinColumn(name="GROUP_ID", referencedColumnName="NAME")
)
    @ManyToMany
    private Set<UserGroup> groups = new HashSet<UserGroup>();

    /**
     * @return the groups
     */
    public Set getGroups() { return groups; }

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

    
    /**
     * @param usergroups the usergroups to set
     */
    public void setGroups(HashSet<UserGroup> groups) {
        this.groups = groups;
    }

}
