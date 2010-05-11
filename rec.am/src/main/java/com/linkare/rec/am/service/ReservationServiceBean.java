package com.linkare.rec.am.service;

import static com.linkare.rec.am.model.Reservation.COUNT_ALL_QUERYNAME;
import static com.linkare.rec.am.model.Reservation.FIND_ALL_QUERYNAME;
import static com.linkare.rec.am.model.Reservation.FIND_BY_EXPERIMENT_NAME_IN_INTERVAL_QUERYNAME;
import static com.linkare.rec.am.model.Reservation.FIND_FOR_DOMAIN_IN_INTERVAL_QUERYNAME;
import static com.linkare.rec.am.model.Reservation.FIND_FOR_USER_AFTER_DATE_QUERYNAME;
import static com.linkare.rec.am.model.Reservation.FIND_FOR_USER_IN_DATE_QUERYNAME;
import static com.linkare.rec.am.model.Reservation.FIND_FOR_USER_QUERYNAME;
import static com.linkare.rec.am.model.Reservation.QUERY_PARAM_DOMAIN;
import static com.linkare.rec.am.model.Reservation.QUERY_PARAM_END_DATE;
import static com.linkare.rec.am.model.Reservation.QUERY_PARAM_EXPERIMENT_NAME;
import static com.linkare.rec.am.model.Reservation.QUERY_PARAM_START_DATE;
import static com.linkare.rec.am.model.Reservation.QUERY_PARAM_USERNAME;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.primefaces.model.ScheduleEvent;

import com.linkare.commons.jpa.exceptions.DomainException;
import com.linkare.commons.jpa.security.Group;
import com.linkare.commons.jpa.security.User;
import com.linkare.commons.utils.BooleanResult;
import com.linkare.rec.am.model.Reservation;
import com.linkare.rec.am.model.moodle.ExternalCourse;
import com.linkare.rec.am.model.moodle.ExternalUser;
import com.linkare.rec.am.web.auth.UserView;
import com.linkare.rec.am.web.moodle.SessionHelper;

/**
 * 
 * @author Joao
 */
@Local(ReservationServiceLocal.class)
@Stateless(name = "ReservationService")
public class ReservationServiceBean extends BusinessServiceBean<Reservation, Long> implements ReservationService {

    @EJB(beanInterface = GroupServiceLocal.class)
    private GroupService groupService;

    @EJB(beanInterface = UserServiceLocal.class)
    private UserService userService;

    @Override
    public void create(final Reservation reservation) {
	final BooleanResult operationResult = canCreate(reservation);
	if (operationResult.getResult() == Boolean.TRUE) {
	    if (!reservation.isInternal()) {
		final ExternalCourse externalCourse = reservation.getExternalCourse();
		// Generate some unique name, relating this group with the unique registration uuid
		final Group reservationGroup = new Group(externalCourse.getShortname() + "@" + reservation.getId());
		groupService.create(reservationGroup);
		reservation.setGroup(reservationGroup);
		final List<String> studentsUsernames = getStudentsUsernames(externalCourse.getStudents());
		final List<User> users = userService.getOrCreateUsers(studentsUsernames);
		groupService.setUsersMembership(reservationGroup, users);
	    }
	    getEntityManager().persist(reservation);
	} else {
	    throw new DomainException(operationResult.getMessage());
	}
    }

    private List<String> getStudentsUsernames(final List<ExternalUser> externalUsers) {
	final List<String> result = new ArrayList<String>();
	for (final ExternalUser externalUser : externalUsers) {
	    result.add(externalUser.getFullUsername());
	}
	return result;
    }

    protected BooleanResult canCreate(final Reservation reservation) {
	final User user = userService.findByUsername(SessionHelper.getUserView().getFullUsername());
	final BooleanResult ownershipResult = reservation.checkOwnership(user);
	return ownershipResult.getResult() == Boolean.FALSE ? ownershipResult : isValid(reservation);
    }

    public Reservation edit(final Reservation reservation) {
	final BooleanResult operationResult = canEdit(reservation);
	if (operationResult.getResult() == Boolean.TRUE) {
	    return getEntityManager().merge(reservation);
	} else {
	    throw new DomainException(operationResult.getMessage());
	}
    }

    protected BooleanResult canEdit(final Reservation reservation) {
	final User user = userService.findByUsername(SessionHelper.getUserView().getFullUsername());
	final BooleanResult ownershipResult = reservation.checkOwnership(user);
	return ownershipResult.getResult() == Boolean.FALSE ? ownershipResult : isValid(reservation);
    }

    @SuppressWarnings("unchecked")
    private BooleanResult isValid(final Reservation reservation) {
	final Query query = getEntityManager().createNamedQuery(FIND_BY_EXPERIMENT_NAME_IN_INTERVAL_QUERYNAME).setParameter(QUERY_PARAM_START_DATE,
															    reservation.getStartDate())
					      .setParameter(QUERY_PARAM_END_DATE, reservation.getEndDate()).setParameter(QUERY_PARAM_EXPERIMENT_NAME,
															 reservation.getExperiment().getName());
	final List<Reservation> reservationsForExperimentNameInInterval = query.getResultList();
	return reservation.isValid(reservationsForExperimentNameInInterval);
    }

    @Override
    public void remove(final Reservation reservation) {
	final Reservation mergedReservation = getEntityManager().merge(reservation);
	final BooleanResult operationResult = canRemove(mergedReservation);
	if (operationResult.getResult() == Boolean.TRUE) {
	    mergedReservation.delete();
	    getEntityManager().remove(mergedReservation);
	} else {
	    throw new DomainException(operationResult.getMessage());
	}
    }

    protected BooleanResult canRemove(final Reservation reservation) {
	final User user = userService.findByUsername(SessionHelper.getUserView().getFullUsername());
	return reservation.checkOwnership(user);
    }

    @Override
    public Reservation find(final Long id) {
	return getEntityManager().find(Reservation.class, id);
    }

    @Override
    public List<Reservation> findRange(final int[] range) {
	return find(false, range[0], range[1]);
    }

    @Override
    public List<Reservation> findAll() {
	return find(true, -1, -1);
    }

    @SuppressWarnings("unchecked")
    public List<Reservation> find(final boolean all, final int firstResult, final int maxResults) {
	Query q = getEntityManager().createNamedQuery(FIND_ALL_QUERYNAME);
	if (!all) {
	    q.setMaxResults(maxResults);
	    q.setFirstResult(firstResult);
	}
	return q.getResultList();
    }

    @Override
    public int count() {
	final Query query = getEntityManager().createNamedQuery(COUNT_ALL_QUERYNAME);
	return ((Long) query.getSingleResult()).intValue();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ScheduleEvent> findReservationsFor(final Date start, final Date end, final UserView userView) {
	final List<ScheduleEvent> events = new ArrayList<ScheduleEvent>();
	if (end == null) {
	    events.addAll(getEntityManager().createNamedQuery(FIND_FOR_USER_AFTER_DATE_QUERYNAME)
					    .setParameter(QUERY_PARAM_USERNAME, userView.getFullUsername()).setParameter(QUERY_PARAM_START_DATE, start,
															 TemporalType.TIMESTAMP)
					    .getResultList());
	} else {
	    events.addAll(getEntityManager().createNamedQuery(FIND_FOR_USER_IN_DATE_QUERYNAME).setParameter(QUERY_PARAM_USERNAME, userView.getFullUsername())
					    .setParameter(QUERY_PARAM_START_DATE, start, TemporalType.TIMESTAMP).setParameter(QUERY_PARAM_END_DATE, end,
															      TemporalType.TIMESTAMP)
					    .getResultList());
	}
	addStyleclassesToEvents(userView, events);
	return events;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ScheduleEvent> findAllReservationsFor(final Date start, final Date end, final UserView userView) {
	final List<ScheduleEvent> events = getEntityManager().createNamedQuery(FIND_FOR_DOMAIN_IN_INTERVAL_QUERYNAME).setParameter(QUERY_PARAM_DOMAIN,
																   userView.getDomain())
							     .setParameter(QUERY_PARAM_START_DATE, start, TemporalType.TIMESTAMP)
							     .setParameter(QUERY_PARAM_END_DATE, end, TemporalType.TIMESTAMP).getResultList();
	addStyleclassesToEvents(userView, events);
	return events;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ScheduleEvent> findReservationsFor(UserView userView) {
	final List<ScheduleEvent> events = getEntityManager().createNamedQuery(FIND_FOR_USER_QUERYNAME).setParameter(QUERY_PARAM_USERNAME,
														     userView.getFullUsername())
							     .getResultList();
	addStyleclassesToEvents(userView, events);
	return events;
    }

    private void addStyleclassesToEvents(UserView userView, final List<ScheduleEvent> events) {
	for (final ScheduleEvent scheduleEvent : events) {
	    final Reservation reservation = (Reservation) scheduleEvent;
	    if (userView.getFullUsername().equals(reservation.getUser().getUsername())) {
		reservation.setStyleClass("myevents");
	    } else {
		reservation.setStyleClass("others_events");
	    }
	}
    }
}