package com.linkare.rec.am.model;

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
import com.linkare.commons.jpa.security.Group;
import com.linkare.commons.jpa.security.User;
import com.linkare.commons.utils.EqualityUtils;
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
	@NamedQuery(name = "findReservationsBetweenDatesForLaboratory", query = "Select r from Reservation r where r.startDate >=:start and r.endDate <=:end and r.experiment in (Select e from Laboratory l, Experiment e where l.name=:namelab and e.laboratory=l)"),
	@NamedQuery(name = "Reservation.findReservationsForInternalUserInDate", query = "Select r from Reservation r WHERE r.user.username=:username and r.startDate between :start and :end"),
	@NamedQuery(name = "Reservation.findReservationsForInternalUser", query = "Select r from Reservation r WHERE r.user.username=:username"),
	@NamedQuery(name = "Reservation.findReservationsForExternalUserInDate", query = "Select r from Reservation r WHERE r.moodleRecord.externalUser=:externalUser and r.moodleRecord.domain=:loginDomain and r.startDate between :start and :end"),
	@NamedQuery(name = "Reservation.findReservationsForExternalUser", query = "Select r from Reservation r WHERE r.moodleRecord.externalUser=:externalUser and r.moodleRecord.domain=:loginDomain") })
public class Reservation extends DefaultDomainObject implements ScheduleEvent {

    private static final long serialVersionUID = 1L;

    @Basic
    @Column(name = "TITLE")
    private String title;

    @Basic
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name = "START_DATE")
    private Date startDate;

    @Basic
    @Column(name = "START_TIME_SLOT")
    private String startTimeSlot;

    @Basic
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name = "END_DATE")
    private Date endDate;

    @Basic
    @Column(name = "END_TIME_SLOT")
    private String endTimeSlot;

    @ManyToOne
    @JoinColumn(name = "KEY_USER", nullable = true)
    private User user;

    @ManyToOne
    @JoinColumn(name = "KEY_EXPERIMENT", nullable = true)
    private Experiment experiment;

    @Basic
    @Column(name = "IS_ALL_DAY")
    private boolean allDay = false;

    @ManyToOne
    @JoinColumn(name = "KEY_GROUP", nullable = true)
    private Group group;

    @Column(name = "RESERVATION_ID", insertable = true, updatable = false, unique = true)
    private String reservationId;

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

    public Reservation(final String externalUser, final String externalCourse, final String domain) {
	this();
	setMoodleRecord(new MoodleRecord(externalUser, externalCourse, domain));
    }

    /**
     * @return the id
     */
    public String getId() {
	return reservationId;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id) {
	this.reservationId = id;
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
     * @return the user
     */
    public User getUser() {
	return user;
    }

    /**
     * @param user
     *            the user to set
     */
    public void setUser(User user) {
	this.user = user;
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
	hash += (getId() != null ? getId().hashCode() : 0);
	return hash;
    }

    @Override
    public boolean equals(Object object) {
	if (!(object instanceof Reservation)) {
	    return false;
	}
	Reservation other = (Reservation) object;
	return EqualityUtils.equals(this.getId(), other.getId());
    }

    @Override
    public String toString() {
	if (title != null && !title.trim().equals("")) {
	    return title;
	} else {
	    return getId();
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
     * @return the group
     */
    public Group getGroup() {
	return group;
    }

    /**
     * @param group
     *            the group to set
     */
    public void setGroup(Group group) {
	this.group = group;
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
	return getUser() != null ? getUser().getUsername() : getMoodleRecord().getExternalUser();
    }

    public String getReservedTo() {
	return getGroup() != null ? getGroup().getName() : getMoodleRecord().getExternalCourseId();
    }

    public boolean getHasUser() {
	return getUser() != null;
    }

    public boolean getHasExperiment() {
	return getExperiment() != null;
    }

    public boolean getHasGroup() {
	return getGroup() != null;
    }

    public String getExternalUser() {
	return getMoodleRecord() == null ? null : getMoodleRecord().getExternalUser();
    }

    public void setExternalUser(final String externalUser) {
	if (getMoodleRecord() != null) {
	    getMoodleRecord().setExternalUser(externalUser);
	}
    }

    public String getDomain() {
	return getMoodleRecord() == null ? null : getMoodleRecord().getDomain();
    }

    public void setDomain(final String domain) {
	if (getMoodleRecord() != null) {
	    getMoodleRecord().setDomain(domain);
	}
    }

    public String getExternalCourse() {
	return getMoodleRecord() == null ? null : getMoodleRecord().getExternalCourseId();
    }

    public void setExternalCourse(final String externalCourse) {
	if (getMoodleRecord() != null) {
	    getMoodleRecord().setExternalCourseId(externalCourse);
	}
    }

    public boolean getHasMoodleRecord() {
	return getMoodleRecord() != null;
    }

    @Override
    public boolean delete() {
	if (getHasUser()) {
	    setUser(null);
	}
	if (getHasExperiment()) {
	    setExperiment(null);
	}
	if (getHasGroup()) {
	    setGroup(null);
	}
	return true;
    }
}