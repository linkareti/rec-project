package com.linkare.rec.am.model;

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

@Entity
@Table(name = "LABORATORY")
@NamedQueries( {
		@NamedQuery(name = "findByName", query = "SELECT lab FROM Laboratory lab WHERE lab.name=:name"),
		@NamedQuery(name = "findExperiments", query = "SELECT experiments FROM Laboratory lab WHERE lab.name=:name"),
		@NamedQuery(name = "findExperiment", query = "SELECT experiment FROM Laboratory lab, Experiment experiment WHERE lab.name=:namelab and experiment.laboratory=lab and experiment.name=:nameexp")}
)
public class Laboratory extends Resource {

	@Id
	private String name;

	@Basic
	private String description;

	@Embedded
	private State state;

	@Basic
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "laboratory")
	private List<Experiment> experiments = new ArrayList<Experiment>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Experiment> getExperiments() {
		return experiments;
	}

	public void setExperiments(List<Experiment> experiments) {
		this.experiments = experiments;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public static Laboratory findByName(String laboratorio, EntityManager em) {
		return (Laboratory) em.createNamedQuery("findByName").setParameter("name", laboratorio).getResultList().get(0);
	}
	
	public static Experiment findExperiments(String laboratorio, EntityManager em) {
		return (Experiment) em.createNamedQuery("findExperiments").setParameter("name", laboratorio).getResultList().get(0);
	}
	
	public static Experiment findExperiment(String laboratorio,String experiencia, EntityManager em) {
		
		Experiment result = null;
		List<Experiment> listaExperiencias = (List<Experiment>)em.createNamedQuery("findExperiment").setParameter("namelab", laboratorio).setParameter("nameexp", experiencia).getResultList();
		if(listaExperiencias.size()>0){
			result = listaExperiencias.get(0);
		}
		return result;
			
	}
}
