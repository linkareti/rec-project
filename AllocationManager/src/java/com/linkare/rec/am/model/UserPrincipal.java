/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.linkare.rec.am.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "reservation", cascade=CascadeType.ALL)
    private List<Reservation> reservations;
    @JoinTable(name = "USER_GROUP", joinColumns =
    @JoinColumn(name = "USER_ID", referencedColumnName = "NAME"),
    inverseJoinColumns =
    @JoinColumn(name = "GROUP_ID", referencedColumnName = "NAme"))
    @ManyToMany
    private List<UserGroup> userGroups;

    /**
     * @return the userGroups
     */
    public List<UserGroup> getUserGroups() {
        return userGroups;
    }

    /**
     * @param userGroups the userGroups to set
     */
    public void setUserGroups(List<UserGroup> userGroups) {
        this.userGroups = userGroups;
    }

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
