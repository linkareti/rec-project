package com.linkare.rec.am.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table (name="LABORATORY")
public class Laboratory extends Resource{

	@Id 
	@GeneratedValue
	private long id;
	
	@Basic
	private String name;
	
	@Basic
	private String description;
	
	@Embedded
	private State state;
	
	@Basic
	@OneToMany (fetch=FetchType.LAZY, mappedBy="laboratory")
	private List<Experiment> experiments=new ArrayList<Experiment>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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
}
