package com.linkare.rec.am.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.linkare.commons.jpa.DefaultDomainObject;

/**
 * 
 * @author Joao
 */
@Entity
@Table(name = "EXPERIMENT")
@NamedQueries( { @NamedQuery(name = "Experiment.findAll", query = "Select e from Experiment e"),
	@NamedQuery(name = "Experiment.countAll", query = "Select count(e) from Experiment e"),
	@NamedQuery(name = "findByExperimentName", query = "SELECT exp FROM Experiment exp WHERE exp.name=:name") })
public class Experiment extends DefaultDomainObject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Basic
    @Column(name = "NAME")
    private String name;

    @Basic
    @Column(name = "DESCRIPTION")
    private String description;

    @JoinColumn(name = "KEY_LABORATORY")
    @ManyToOne(fetch = FetchType.EAGER)
    private Laboratory laboratory;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "experiment")
    private List<Reservation> reservations = new ArrayList<Reservation>();

    @Embedded
    private State state = new State();

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
     * @param name
     *            new value of name
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
     * @param description
     *            new value of description
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
     * @param laboratory
     *            new value of laboratory
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
     * @param reservations
     *            new value of reservations
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
     * @param statee
     *            new value of statee
     */
    public void setState(State state) {
	this.state = state;
    }

    @Override
    public final int hashCode() {
	int hash = 0;
	hash += (getIdInternal() != null ? getIdInternal().hashCode() : 0);
	return hash;
    }

    @Override
    public final boolean equals(Object object) {
	if (!(object instanceof Experiment)) {
	    return false;
	}
	Experiment other = (Experiment) object;
	if ((this.getIdInternal() == null && other.getIdInternal() != null)
		|| (this.getIdInternal() != null && !this.getIdInternal().equals(other.getIdInternal()))) {
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
	    return getIdInternal().toString();
	}
    }

    public static final Experiment findByName(String experiencia, EntityManager em) {
	return (Experiment) em.createNamedQuery("findByExperimentName").setParameter("name", experiencia).getResultList().get(0);
    }
}
