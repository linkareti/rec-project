package com.linkare.rec.am.model;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table (name="EXPERIMENT")
public class Experiment extends Resource{

	@Id 
	@GeneratedValue
	private long id;
	
	@Basic
	private String name;
	
	@Basic
	private String description;
	
	@Basic
	@ManyToOne (fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private Laboratory laboratory;

	@Basic
	@OneToMany (mappedBy="experiment")
	@Column (nullable=true)
	private List<Reservation> reservations;

	@Embedded
	private State state;
	
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

	public Laboratory getLaboratory() {
		return laboratory;
	}

	public void setLaboratory(Laboratory laboratory) {
		this.laboratory = laboratory;
	}

	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
}
