package com.linkare.rec.am.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

import com.linkare.commons.jpa.DefaultDomainObject;
import com.linkare.commons.utils.EqualityUtils;

/**
 * 
 * @author Joao
 */
@Entity
@Table(name = "LABORATORY")
@NamedQueries( {
	@NamedQuery(name = "Laboratory.findAll", query = "Select l from Laboratory l"),
	@NamedQuery(name = "Laboratory.countAll", query = "Select count(l) from Laboratory l"),
	@NamedQuery(name = "findByName", query = "SELECT lab FROM Laboratory lab WHERE lab.name=:name"),
	/* @NamedQuery(name = "findExperiments", query = "SELECT experiments FROM Laboratory lab WHERE lab.name=:name"), */
	@NamedQuery(name = "findExperiment", query = "SELECT experiment FROM Laboratory lab, Experiment experiment WHERE lab.name=:namelab and experiment.laboratory=lab and experiment.name=:nameexp") })
@XmlAccessorType(XmlAccessType.FIELD)
public class Laboratory extends DefaultDomainObject {

    private static final long serialVersionUID = 1L;

    @Column(name = "NAME", unique = true, insertable = true, updatable = true)
    private String name;

    @Basic
    @Column(name = "DESCRIPTION", insertable = true, updatable = true)
    private String description;

    @Embedded
    private State state = new State();

    @XmlTransient
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "laboratory")
    private List<Experiment> experiments = new ArrayList<Experiment>();

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
     * Get the value of statel
     * 
     * @return the value of statel
     */
    public State getState() {
	return state;
    }

    /**
     * Set the value of statel
     * 
     * @param statel
     *            new value of statel
     */
    public void setState(State state) {
	this.state = state;
    }

    /**
     * Get the value of experiments
     * 
     * @return the value of experiments
     */
    public List<Experiment> getExperiments() {
	return experiments;
    }

    /**
     * Set the value of experiments
     * 
     * @param experiments
     *            new value of experiments
     */
    public void setExperiments(List<Experiment> experiments) {
	this.experiments = experiments;
    }

    @Override
    public boolean equals(final Object other) {
	if (!(other instanceof Laboratory)) {
	    return false;
	}
	return equalsTo((Laboratory) other);
    }

    @Override
    public int hashCode() {
	int result = 14;
	result = 29 * result + (getName() != null ? getName().hashCode() : 0);
	return result;
    }

    private boolean equalsTo(final Laboratory other) {
	return EqualityUtils.equals(getName(), other.getName());
    }

    @Override
    public String toString() {
	return getName();
    }
}