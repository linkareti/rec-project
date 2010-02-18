package com.linkare.rec.am.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Query;
import javax.persistence.Table;

@Entity
@Table (name="RESERVATION")
@NamedQuery(name = "findReservationsBetweenDatesForLaboratory", query = "SELECT reserved FROM Reservation reserved WHERE reserved.startDate >=:start AND reserved.stopDate <=:end and reserved.experiment in(SELECT experiment FROM Laboratory lab, Experiment experiment WHERE lab.name=:namelab and experiment.laboratory=lab)")
public class Reservation {

	@Id 
	@GeneratedValue
	private long id; 
	
	@Basic
	private Date startDate;
	
	@Basic
	private Date stopDate;
	
	@Basic
	@ManyToOne (cascade=CascadeType.ALL)
	@JoinColumn (nullable=false)
	private Experiment experiment;
	
	@ManyToMany(mappedBy = "reservations")
	private List<User> users;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getStopDate() {
		return stopDate;
	}

	public void setStopDate(Date stopDate) {
		this.stopDate = stopDate;
	}
	

	public Experiment getExperiment() {
		return experiment;
	}

	public void setExperiment(Experiment experiment) {
		this.experiment = experiment;
	}

	public void setReservingUsers(List<User> reservingUsers) {
		this.users = reservingUsers;
	}

	public List<User> getReservingUsers() {
		return users;
	}
	
	public static List<Reservation> findReservationsInLab(String laboratorio,String nomeExperiencia, Date startDate,
			Date endDate, EntityManager em){
		
		Query query = em.createNamedQuery("findReservationsBetweenDatesForLaboratory");
		query.setParameter("namelab", laboratorio);
		query.setParameter("start", startDate);
		query.setParameter("end", endDate);
		
//		query.
		
		return (List<Reservation>)query.getResultList();
		
	}
}
