package com.linkare.rec.am.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.persistence.Temporal;
import org.primefaces.model.ScheduleEvent;

/**
 *
 * @author Joao
 */
@Entity
@Table(name = "RESERVATION")
@NamedQuery(name = "findReservationsBetweenDatesForLaboratory", query = "SELECT reserved FROM Reservation reserved WHERE reserved.startDate >=:start AND reserved.endDate <=:end and reserved.experiment in(SELECT experiment FROM Laboratory lab, Experiment experiment WHERE lab.name=:namelab and experiment.laboratory=lab)")
public class Reservation implements ScheduleEvent, Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    @Basic
    private String title;
    @Basic
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date startDate;
    @Basic
    private String startTimeSlot;
    @Basic
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date endDate;
    @Basic
    private String endTimeSlot;
    @Basic
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(nullable=false)
    private UserPrincipal user;
    @Basic
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(nullable = false)
    private Experiment experiment;
    @Basic
    private boolean allDay = false;
    @Basic
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(nullable = false)
    private UserGroup group;

    /**
     * Constructot
     *
     * @param title new value of title
     * @param startDate new value of startDate
     * @param endDate new value of endDate
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
     * @param id new value of id
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
     * @param title the title to set
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
     * @param startDate new value of startDate
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
     * @param endDate new value of endDate
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the user
     */
    public UserPrincipal getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(UserPrincipal user) {
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
     * @param experiment new value of experiment
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
     * @param allDay the allDay to set
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
     * @param startTimeSlot the startTimeSlot to set
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
     * @param endTimeSlot the endTimeSlot to set
     */
    public void setEndTimeSlot(String endTimeSlot) {
        this.endTimeSlot = endTimeSlot;
    }

    public static List<Reservation> findReservationsInLab(String laboratorio, String nomeExperiencia, Date startDate,
            Date endDate, EntityManager em) {

        Query query = em.createNamedQuery("findReservationsBetweenDatesForLaboratory");
        query.setParameter("namelab", laboratorio);
        query.setParameter("start", startDate);
        query.setParameter("end", endDate);

//		query.

        return (List<Reservation>) query.getResultList();

    }

    /**
     * @return the group
     */
    public UserGroup getGroup() {
        return group;
    }

    /**
     * @param group the group to set
     */
    public void setGroup(UserGroup group) {
        this.group = group;
    }
}
