package com.linkare.rec.am.model;

import java.io.Serializable;
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
public class UserGroup extends Resource implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String name;

    @JoinTable(name = "USERPRINCIPAL_USERGROUP", joinColumns =
    @JoinColumn(name = "USERGROUP_ID", referencedColumnName = "NAME"),
    inverseJoinColumns =
    @JoinColumn(name = "USERPRINCIPAL_ID", referencedColumnName = "NAME"))
    @ManyToMany
    private List<UserPrincipal> members = new ArrayList<UserPrincipal>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userGroup", cascade = CascadeType.ALL)
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

    public boolean hasMembers() {
        return !members.isEmpty();
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
    public List<UserPrincipal> getMembers() {
        return members;
    }

    public void setMembers(List<UserPrincipal> members) {
        this.members = members;
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
