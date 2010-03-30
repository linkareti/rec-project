package com.linkare.rec.am.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Joao
 */
@Entity
@Table(name = "EXPERIMENT")
@NamedQuery(name = "findByExperimentName", query = "SELECT exp FROM Experiment exp WHERE exp.name=:name")
public class Experiment extends Resource implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic
    private String name;
    @Basic
    private String description;
    @JoinColumn
    @ManyToOne(fetch = FetchType.EAGER)
    private Laboratory laboratory;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "experiment")
    private List<Reservation> reservations = new ArrayList<Reservation>();
    @Embedded
    private State state = new State();

    /**
     * Get the value of id
     *
     * @return the value of id
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the value of id
     *
     * @param id new value of id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the value of name
     *
     * @return the value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the value of name
     *
     * @param name new value of name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the value of description
     *
     * @return the value of description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the value of description
     *
     * @param description new value of description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get the value of laboratory
     *
     * @return value of laboratory
     */
    public Laboratory getLaboratory() {
        return laboratory;
    }

    /**
     * Set the value of laboratory
     *
     * @param laboratory new value of laboratory
     */
    public void setLaboratory(Laboratory laboratory) {
        this.laboratory = laboratory;
    }

    /**
     * Get the value of reservations
     *
     * @return value of reservations
     */
    public List<Reservation> getReservations() {
        return reservations;
    }

    /**
     * Set the value of reservations
     *
     * @param reservations new value of reservations
     */
    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    /**
     * Get value of statee
     *
     * @return value of statee
     */
    public State getState() {
        return state;
    }

    /**
     * Set the value of statee
     *
     * @param statee new value of statee
     */
    public void setState(State state) {
        this.state = state;
    }

    @Override
    public final int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public final boolean equals(Object object) {
        if (!(object instanceof Experiment)) {
            return false;
        }
        Experiment other = (Experiment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public final String toString() {
        if (name != null && !name.trim().equals("")) {
            if (laboratory != null && !laboratory.toString().trim().equals("")) {
                return name + '@' + laboratory.toString();
            } else {
                return name;
            }
        } else {
            return id.toString();
        }
    }

    public static final Experiment findByName(String experiencia, EntityManager em) {
        return (Experiment) em.createNamedQuery("findByExperimentName").setParameter("name", experiencia).getResultList().get(0);
    }
}
