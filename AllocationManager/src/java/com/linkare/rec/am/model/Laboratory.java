package com.linkare.rec.am.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Joao
 */
@Entity
@Table(name = "LABORATORY")
@NamedQueries({
    @NamedQuery(name = "findByName", query = "SELECT lab FROM Laboratory lab WHERE lab.name=:name"),
    /*@NamedQuery(name = "findExperiments", query = "SELECT experiments FROM Laboratory lab WHERE lab.name=:name"),*/
    @NamedQuery(name = "findExperiment", query = "SELECT experiment FROM Laboratory lab, Experiment experiment WHERE lab.name=:namelab and experiment.laboratory=lab and experiment.name=:nameexp")})
public class Laboratory extends Resource implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String name;
    @Basic
    private String description;
    @Embedded
    private State state = new State();
    @Basic
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "laboratory")
    private List<Experiment> experiments  = new ArrayList<Experiment>();

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
     * @param statel new value of statel
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
     * @param experiments new value of experiments
     */
    public void setExperiments(List<Experiment> experiments) {
        this.experiments = experiments;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (name != null ? name.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Laboratory)) {
            return false;
        }
        Laboratory other = (Laboratory) object;
        if ((this.name == null && other.name != null) || (this.name != null && !this.name.equals(other.name))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name;
    }

    public static Laboratory findByName(String laboratory, EntityManager em) {
        return (Laboratory) em.createNamedQuery("findByName").setParameter("name", laboratory).getResultList().get(0);
    }

//    public static Experiment findExperiments(String laboratory, EntityManager em) {
//        return (Experiment) em.createNamedQuery("findExperiments").setParameter("name", laboratory).getResultList().get(0);
//    }

    public static Experiment findExperiment(String laboratory, String experiment, EntityManager em) {

        Experiment result = null;
        List<Experiment> experimentList = (List<Experiment>) em.createNamedQuery("findExperiment").setParameter("namelab", laboratory).setParameter("nameexp", experiment).getResultList();
        if (experimentList.size() > 0) {
            result = experimentList.get(0);
        }
        return result;

    }
}
