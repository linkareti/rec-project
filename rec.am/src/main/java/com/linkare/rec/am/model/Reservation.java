package com.linkare.rec.am.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.persistence.Temporal;

import org.primefaces.model.ScheduleEvent;

import com.linkare.commons.jpa.DefaultDomainObject;
import com.linkare.rec.am.model.moodle.MoodleRecord;

/**
 * 
 * @author Joao
 */
@Entity
@Table(name = "RESERVATION")
@NamedQueries( {
	@NamedQuery(name = "Reservation.findAll", query = "Select r from Reservation r"),
	@NamedQuery(name = "Reservation.countAll", query = "Select count(r) from Reservation r"),
	@NamedQuery(name = "findReservationsBetweenDatesForLaboratory", query = "SELECT reserved FROM Reservation reserved WHERE reserved.startDate >=:start AND reserved.endDate <=:end and reserved.experiment in(SELECT experiment FROM Laboratory lab, Experiment experiment WHERE lab.name=:namelab and experiment.laboratory=lab)") })
public class Reservation extends DefaultDomainObject implements ScheduleEvent, Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "RESERVATION_ID", unique = true, insertable = true, updatable = false, nullable = false)
    private String id;

    @Basic
    @Column(name = "TITLE")
    private String title;

    @Basic
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(name = "START_DATE")
    private Date startDate;

    @Basic
    @Column(name = "START_TIME_SLOT")
    private String startTimeSlot;

    @Basic
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(name = "END_DATE")
    private Date endDate;

    @Basic
    @Column(name = "END_TIME_SLOT")
    private String endTimeSlot;

    @ManyToOne
    @JoinColumn(name = "KEY_USER", nullable = true)
    private UserPrincipal userPrincipal;

    @ManyToOne
    @JoinColumn(name = "KEY_EXPERIMENT", nullable = true)
    private Experiment experiment;

    @Basic
    @Column(name = "IS_ALL_DAY")
    private boolean allDay = false;

    @ManyToOne
    @JoinColumn(name = "KEY_GROUP", nullable = true)
    private UserGroup userGroup;

    @Embedded
    private MoodleRecord moodleRecord;

    /**
     * Constructor
     * 
     * @param title
     *            new value of title
     * @param startDate
     *            new value of startDate
     * @param endDate
     *            new value of endDate
     */
    public Reservation(String title, Date startDate, Date endDate) {
	this.title = title;
	this.startDate = startDate;
	this.endDate = endDate;
    }

    public Reservation() {
    }

    /**
     * Get the value of id
     * 
     * @return the value of id
     */
    @Override
    public String getId() {
	return id;
    }

    /**
     * Set the value of id
     * 
     * @param id
     *            new value of id
     */
    @Override
    public void setId(String id) {
	this.id = id;
    }

    /**
     * @return the title
     */
    @Override
    public String getTitle() {
	return title;
    }

    /**
     * @param title
     *            the title to set
     */
    public void setTitle(String title) {
	this.title = title;
    }

    /**
     * Get the value of startDate
     * 
     * @return the value of startDate
     */
    @Override
    public Date getStartDate() {
	return startDate;
    }

    /**
     * Set the value of startDate
     * 
     * @param startDate
     *            new value of startDate
     */
    public void setStartDate(Date startDate) {
	this.startDate = startDate;
    }

    /**
     * Get the value of endDate
     * 
     * @return the value of endDate
     */
    @Override
    public Date getEndDate() {
	return endDate;
    }

    /**
     * Set the value of endDate
     * 
     * @param endDate
     *            new value of endDate
     */
    public void setEndDate(Date endDate) {
	this.endDate = endDate;
    }

    /**
     * @return the userPrincipal
     */
    public UserPrincipal getUserPrincipal() {
	return userPrincipal;
    }

    /**
     * @param userPrincipal
     *            the userPrincipal to set
     */
    public void setUserPrincipal(UserPrincipal userPrincipal) {
	this.userPrincipal = userPrincipal;
    }

    /**
     * Get the value of experiment
     * 
     * @return the value of experiment
     */
    public Experiment getExperiment() {
	return experiment;
    }

    /**
     * Set the value of experiment
     * 
     * @param experiment
     *            new value of experiment
     */
    public void setExperiment(Experiment experiment) {
	this.experiment = experiment;
    }

    /**
     * @return the allDay
     */
    @Override
    public boolean isAllDay() {
	return allDay;
    }

    /**
     * @param allDay
     *            the allDay to set
     */
    public void setAllDay(boolean allDay) {
	this.allDay = allDay;
    }

    /**
     * @return the styleClass
     */
    @Override
    public String getStyleClass() {
	return null;
    }

    /**
     * @return the dataObj
     */
    @Override
    public Object getData() {
	return null;
    }

    @Override
    public int hashCode() {
	int hash = 0;
	hash += (id != null ? id.hashCode() : 0);
	return hash;
    }

    @Override
    public boolean equals(Object object) {
	if (!(object instanceof Reservation)) {
	    return false;
	}
	Reservation other = (Reservation) object;
	if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
	    return false;
	}
	return true;
    }

    @Override
    public String toString() {
	if (title != null && !title.trim().equals("")) {
	    return title;
	} else {
	    return id;
	}
    }

    /**
     * @return the startTimeSlot
     */
    public String getStartTimeSlot() {
	return startTimeSlot;
    }

    /**
     * @param startTimeSlot
     *            the startTimeSlot to set
     */
    public void setStartTimeSlot(String startTimeSlot) {
	this.startTimeSlot = startTimeSlot;
    }

    /**
     * @return the endTimeSlot
     */
    public String getEndTimeSlot() {
	return endTimeSlot;
    }

    /**
     * @param endTimeSlot
     *            the endTimeSlot to set
     */
    public void setEndTimeSlot(String endTimeSlot) {
	this.endTimeSlot = endTimeSlot;
    }

    public static List<Reservation> findReservationsInLab(String laboratorio, String nomeExperiencia, Date startDate, Date endDate, EntityManager em) {

	Query query = em.createNamedQuery("findReservationsBetweenDatesForLaboratory");
	query.setParameter("namelab", laboratorio);
	query.setParameter("start", startDate);
	query.setParameter("end", endDate);

	return (List<Reservation>) query.getResultList();

    }

    /**
     * @return the userGroup
     */
    public UserGroup getUserGroup() {
	return userGroup;
    }

    /**
     * @param userGroup
     *            the userGroup to set
     */
    public void setUserGroup(UserGroup userGroup) {
	this.userGroup = userGroup;
    }

    /**
     * @return the moodleRecord
     */
    public MoodleRecord getMoodleRecord() {
	return moodleRecord;
    }

    /**
     * @param moodleRecord
     *            the moodleRecord to set
     */
    public void setMoodleRecord(MoodleRecord moodleRecord) {
	this.moodleRecord = moodleRecord;
    }

    public String getReservedBy() {
	return getUserPrincipal() != null ? getUserPrincipal().getUsername() : getMoodleRecord().getExternalUser();
    }
}