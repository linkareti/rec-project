package com.linkare.rec.am.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;

import org.joda.time.Interval;
import org.joda.time.PeriodType;
import org.primefaces.model.ScheduleEvent;

import com.linkare.commons.jpa.DefaultDomainObject;
import com.linkare.commons.jpa.security.Group;
import com.linkare.commons.jpa.security.User;
import com.linkare.commons.utils.BooleanResult;
import com.linkare.commons.utils.EqualityUtils;
import com.linkare.rec.am.model.moodle.ExternalCourse;
import com.linkare.rec.am.web.util.ConstantUtils;

/**
 * 
 * @author Joao
 */
@Entity
@Table(name = "RESERVATION")
@NamedQueries( { @NamedQuery(name = Reservation.FIND_ALL_QUERYNAME, query = Reservation.FIND_ALL_QUERY),
	@NamedQuery(name = Reservation.COUNT_ALL_QUERYNAME, query = Reservation.COUNT_ALL_QUERY),
	@NamedQuery(name = Reservation.FIND_BY_EXPERIMENT_NAME_IN_INTERVAL_QUERYNAME, query = Reservation.FIND_BY_EXPERIMENT_NAME_IN_INTERVAL_QUERY),
	@NamedQuery(name = Reservation.FIND_FOR_USER_IN_DATE_QUERYNAME, query = Reservation.FIND_FOR_USER_IN_DATE_QUERY),
	@NamedQuery(name = Reservation.FIND_FOR_USER_AFTER_DATE_QUERYNAME, query = Reservation.FIND_FOR_USER_AFTER_DATE_QUERY),
	@NamedQuery(name = Reservation.FIND_FOR_USER_QUERYNAME, query = Reservation.FIND_FOR_USER_QUERY),
	@NamedQuery(name = Reservation.FIND_IN_INTERVAL_AND_LAB_QUERYNAME, query = Reservation.FIND_IN_INTERVAL_AND_LAB_QUERY),
	@NamedQuery(name = Reservation.FIND_FOR_DOMAIN_IN_INTERVAL_QUERYNAME, query = Reservation.FIND_FOR_DOMAIN_IN_INTERVAL_QUERY) })
public class Reservation extends DefaultDomainObject implements ScheduleEvent {

    private static final long serialVersionUID = 5501757961910540877L;

    public static final String QUERY_PARAM_START_DATE = "startDate";

    public static final String QUERY_PARAM_END_DATE = "endDate";

    public static final String QUERY_PARAM_EXPERIMENT_NAME = "experimentName";

    public static final String QUERY_PARAM_USERNAME = "username";

    public static final String QUERY_PARAM_LAB_NAME = "laboratoryName";

    public static final String QUERY_PARAM_DOMAIN = "domain";

    public static final String FIND_ALL_QUERYNAME = "Reservation.findAll";
    public static final String FIND_ALL_QUERY = "Select r from Reservation r";

    public static final String COUNT_ALL_QUERYNAME = "Reservation.countAll";
    public static final String COUNT_ALL_QUERY = "Select count(r) from Reservation r";

    public static final String FIND_BY_EXPERIMENT_NAME_IN_INTERVAL_QUERYNAME = "Reservation.findByExperimentNameInInterval";
    public static final String FIND_BY_EXPERIMENT_NAME_IN_INTERVAL_QUERY = "Select r from Reservation r where (r.startDate between :" + QUERY_PARAM_START_DATE
	    + " and :" + QUERY_PARAM_END_DATE + " or r.endDate between :" + QUERY_PARAM_START_DATE + " and :" + QUERY_PARAM_END_DATE
	    + ") and r.experiment.name = :" + QUERY_PARAM_EXPERIMENT_NAME;

    public static final String FIND_FOR_USER_IN_DATE_QUERYNAME = "Reservation.findForUserInDate";
    public static final String FIND_FOR_USER_IN_DATE_QUERY = "Select r from Reservation r WHERE r.user.username = :" + QUERY_PARAM_USERNAME
	    + " and r.startDate between :" + QUERY_PARAM_START_DATE + " and :" + QUERY_PARAM_END_DATE;

    public static final String FIND_FOR_USER_AFTER_DATE_QUERYNAME = "Reservation.findForUserAfterDate";
    public static final String FIND_FOR_USER_AFTER_DATE_QUERY = "Select r from Reservation r WHERE r.user.username = :" + QUERY_PARAM_USERNAME
	    + " and r.startDate >= :" + QUERY_PARAM_START_DATE;

    public static final String FIND_FOR_USER_QUERYNAME = "Reservation.findForUser";
    public static final String FIND_FOR_USER_QUERY = "Select r from Reservation r WHERE r.user.username = :" + QUERY_PARAM_USERNAME;

    public static final String FIND_IN_INTERVAL_AND_LAB_QUERYNAME = "Reservation.findInIntervalAndLab";
    public static final String FIND_IN_INTERVAL_AND_LAB_QUERY = "Select r from Reservation r where ( :" + QUERY_PARAM_START_DATE
	    + " between r.startDate and r.endDate  or :" + QUERY_PARAM_END_DATE
	    + " between r.startDate and r.endDate ) and r.experiment.laboratory.name = :" + QUERY_PARAM_LAB_NAME;

    public static final String FIND_FOR_DOMAIN_IN_INTERVAL_QUERYNAME = "Reservation.findForDomainInDate";
    public static final String FIND_FOR_DOMAIN_IN_INTERVAL_QUERY = "Select r from Reservation r WHERE r.domain = :" + QUERY_PARAM_DOMAIN
	    + " and r.startDate between :" + QUERY_PARAM_START_DATE + " and :" + QUERY_PARAM_END_DATE;

    private static final int MINIMUM_INTERVAL_IN_MINUTES = 15;

    private static final int MAXIMUM_INTERVAL_IN_MINUTES = 120;

    private static final int MINUTE_INTERVAL = 15;

    @Basic
    @Column(name = "TITLE")
    private String title;

    @Basic
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name = "START_DATE")
    private Date startDate;

    @Basic
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name = "END_DATE")
    private Date endDate;

    @ManyToOne
    @JoinColumn(name = "KEY_USER", insertable = true, updatable = true, nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "KEY_EXPERIMENT", insertable = true, updatable = true, nullable = true)
    private Experiment experiment;

    @Basic
    @Column(name = "IS_ALL_DAY")
    private boolean allDay = false;

    @ManyToOne
    @JoinColumn(name = "KEY_GROUP", insertable = true, updatable = false, nullable = false)
    private Group group;

    @Column(name = "RESERVATION_ID", insertable = true, updatable = false, unique = true)
    private String reservationId;

    @Column(name = "DOMAIN", insertable = true, updatable = false, nullable = false)
    private String domain;

    @Transient
    private String styleClass;

    @Transient
    private ExternalCourse externalCourse;

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

    public Reservation(final User reservedBy, final String domain) {
	this();
	this.user = reservedBy;
	this.domain = domain;
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

    public String getReservedBy() {
	final User user = getUser();
	return user == null ? null : user.getUsername();
    }

    public String getReservedTo() {
	final Group group = getGroup();
	if (group == null) {
	    return null;
	}
	final String name = group.getName();
	return name.contains("@") ? name.substring(0, name.indexOf("@")) : name;
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

    public Interval getInterval() {
	return new Interval(getStartDate().getTime(), getEndDate().getTime());
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

    public boolean hasConflicts(List<Reservation> reservationsForExperimentNameInInterval) {
	for (final Reservation reservation : reservationsForExperimentNameInInterval) {
	    if (!EqualityUtils.equals(this.getIdInternal(), reservation.getIdInternal())
		    && EqualityUtils.equals(this.getExperiment().getLaboratory(), reservation.getExperiment().getLaboratory())
		    && this.getInterval().overlaps(reservation.getInterval())) {
		return true;
	    }
	}
	return false;
    }

    public boolean isValidPeriodInterval() {
	int minutesInInterval = getInterval().toPeriod(PeriodType.minutes()).getMinutes();
	return MINIMUM_INTERVAL_IN_MINUTES <= minutesInInterval && minutesInInterval <= MAXIMUM_INTERVAL_IN_MINUTES;
    }

    public boolean isValidDates() {
	return isValidDate(getStartDate()) && isValidDate(getEndDate());
    }

    @SuppressWarnings("deprecation")
    private static boolean isValidDate(final Date date) {
	return date != null && ((date.getMinutes() % MINUTE_INTERVAL) == 0);
    }

    public BooleanResult isValid(final List<Reservation> reservationsForExperimentNameInInterval) {
	if (getEndDate().before(getStartDate())) {
	    return new BooleanResult(Boolean.FALSE, "error.end.before.start");
	}
	if (hasConflicts(reservationsForExperimentNameInInterval)) {
	    return new BooleanResult(Boolean.FALSE, "error.laboratory.taken");
	}
	if (!isValidDates()) {
	    return new BooleanResult(Boolean.FALSE, "error.invalid.chosen.dates");
	}
	if (!isValidPeriodInterval()) {
	    return new BooleanResult(Boolean.FALSE, "error.invalid.period.interval");
	}

	return BooleanResult.TRUE_RESULT;
    }

    public BooleanResult checkOwnership(final User user) {
	if (getUser() != null && !user.equals(getUser())) {
	    return new BooleanResult(Boolean.FALSE, "error.user.not.owner");
	}
	return BooleanResult.TRUE_RESULT;
    }

    public boolean isInternal() {
	return getUser().getUsername().indexOf("@") == -1 || getUser().getUsername().endsWith(ConstantUtils.INTERNAL_DOMAIN_NAME);
    }

    /**
     * @return the styleClass
     */
    @Override
    public String getStyleClass() {
	return styleClass;
    }

    /**
     * @param styleClass
     *            the styleClass to set
     */
    public void setStyleClass(String styleClass) {
	this.styleClass = styleClass;
    }

    /**
     * @return the externalCourse
     */
    public ExternalCourse getExternalCourse() {
	return externalCourse;
    }

    /**
     * @param externalCourse
     *            the externalCourse to set
     */
    public void setExternalCourse(ExternalCourse externalCourse) {
	this.externalCourse = externalCourse;
    }

    /**
     * @return the domain
     */
    public String getDomain() {
	return domain;
    }

    /**
     * @param domain
     *            the domain to set
     */
    public void setDomain(String domain) {
	this.domain = domain;
    }

    @Override
    public void prePersist() {
	if (getDomain() == null) {
	    setDomain(ConstantUtils.INTERNAL_DOMAIN_NAME);
	}
	super.prePersist();
    }
}