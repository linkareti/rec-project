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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic
    @Column (insertable=true, updatable=true, unique=true, nullable=false)
    private String name;
    @Basic
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "reservation", cascade=CascadeType.ALL)
    private List<Reservation> reservations;
    @JoinTable(name = "USER_GROUP", joinColumns =
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID"),
    inverseJoinColumns =
    @JoinColumn(name = "GROUP_ID", referencedColumnName = "ID"))
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
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof UserPrincipal)) {
            return false;
        }
        UserPrincipal other = (UserPrincipal) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        if (name != null && !name.trim().equals("")) {
                return name;
        } else {
            return id.toString();
        }
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }
}
