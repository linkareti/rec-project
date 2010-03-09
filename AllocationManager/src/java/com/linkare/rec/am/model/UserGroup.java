package com.linkare.rec.am.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic
    @Column (insertable=true, updatable=true, unique=true, nullable=false)
    private String name;
    @Basic
    @ManyToMany(mappedBy = "groups")
    private List<UserPrincipal> members;
    @Basic
    @OneToMany(mappedBy = "userGroup")
    private List<Reservation> reservations;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof UserGroup)) {
            return false;
        }
        UserGroup other = (UserGroup) object;
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

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }
}
