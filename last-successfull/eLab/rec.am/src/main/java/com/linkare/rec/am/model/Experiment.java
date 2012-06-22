package com.linkare.rec.am.model;

import java.util.ArrayList;
import java.util.Collections;
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
 * @author Bruno Catarino - Linkare TI
 */
@Entity
@Table(name = "EXPERIMENT")
@NamedQueries( { @NamedQuery(name = Experiment.FIND_ALL_QUERYNAME, query = "Select e from Experiment e"),
	@NamedQuery(name = Experiment.COUNT_ALL_QUERYNAME, query = "Select count(e) from Experiment e"),
	@NamedQuery(name = Experiment.FIND_ALL_ACTIVE_QUERYNAME, query = "Select e from Experiment e where e.state.active = '1' order by e.name"),
	@NamedQuery(name = Experiment.FIND_BY_LAB, query = Experiment.FIND_BY_LAB_QRY)})
public class Experiment extends DefaultDomainObject {

    private static final long serialVersionUID = 1L;

    public static final String FIND_ALL_QUERYNAME = "Experiment.findAll";

    public static final String COUNT_ALL_QUERYNAME = "Experiment.countAll";

    public static final String FIND_ALL_ACTIVE_QUERYNAME = "Experiment.findAllActive";

    public static final String FIND_BY_LAB = "Experiment.findByLab";

    public static final String LABORATORY = "LABORATORY";

    public static final String FIND_BY_LAB_QRY = "Select e from Experiment e where e.laboratory.name = :" + LABORATORY;

    public static final String FIND_BY_EXTERNAL_ID = "Experiment.findByExternalID";

    private static final String EXTERNAL_ID_QRY_PARAM = "externalId";

    public static final String FIND_BY_EXTERNAL_ID_QRY = "Select e from Experiment e where e.externalID = :" + EXTERNAL_ID_QRY_PARAM;

    @Basic
    @Column(name = "EXTERNAL_ID", insertable = true, updatable = true, unique = true)
    private String externalId;

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
     * @return the externalId
     */
    public String getExternalId() {
	return externalId;
    }

    /**
     * @param externalId
     *            the externalId to set
     */
    public void setExternalId(String externalId) {
	this.externalId = externalId;
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
	return reservations = reservations == null ? Collections.<Reservation> emptyList() : reservations;
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

    public boolean getHasReservations() {
	return !getReservations().isEmpty();
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

    public boolean getHasLaboratory() {
	return getLaboratory() != null;
    }

    @Override
    public final int hashCode() {
	int hash = 0;
	hash += (getIdInternal() != null ? getIdInternal().hashCode() : 0);
	return hash;
    }

    public String getPresentationName() {
	return getName() + (getHasLaboratory() ? ("@" + getLaboratory().getName()) : "");
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
	return getPresentationName();
    }

    public static final Experiment findByName(String experiencia, EntityManager em) {
	return (Experiment) em.createNamedQuery("findByExperimentName").setParameter("name", experiencia).getResultList().get(0);
    }

    @Override
    public boolean delete() {
	if (getHasReservations()) {
	    return false;
	}
	setLaboratory(null);
	return super.delete();
    }
}