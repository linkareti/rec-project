package com.linkare.rec.am.service;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.primefaces.model.ScheduleEvent;

import com.linkare.commons.jpa.exceptions.DomainException;
import com.linkare.commons.jpa.security.User;
import com.linkare.commons.utils.BooleanResult;
import com.linkare.rec.am.model.Reservation;

/**
 * 
 * @author Joao
 */
@Local(ReservationServiceLocal.class)
@Stateless(name = "ReservationService")
public class ReservationServiceBean extends BusinessServiceBean<Reservation, Long> implements ReservationService {

    @Override
    public void create(final Reservation reservation) {
	final BooleanResult operationResult = canCreate(reservation);
	if (operationResult.getResult() == Boolean.TRUE) {
	    getEntityManager().persist(reservation);
	} else {
	    throw new DomainException(operationResult.getMessage());
	}
    }

    protected BooleanResult canCreate(final Reservation reservation) {
	return isValid(reservation);
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
	return isValid(reservation);
    }

    @SuppressWarnings("unchecked")
    private BooleanResult isValid(final Reservation reservation) {
	final Query query = getEntityManager().createNamedQuery("Reservation.findByExperimentNameInInterval").setParameter("startDate",
															   reservation.getStartDate())
					      .setParameter("endDate", reservation.getEndDate()).setParameter("experimentName",
													      reservation.getExperiment().getName());
	final List<Reservation> reservationsForExperimentNameInInterval = query.getResultList();
	return reservation.isValid(reservationsForExperimentNameInInterval);
    }

    @Override
    public void remove(final Reservation reservation) {
	final Reservation mergedReservation = getEntityManager().merge(reservation);
	mergedReservation.delete();
	getEntityManager().remove(mergedReservation);
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
	Query q = getEntityManager().createNamedQuery("Reservation.findAll");
	if (!all) {
	    q.setMaxResults(maxResults);
	    q.setFirstResult(firstResult);
	}
	return q.getResultList();
    }

    @Override
    public int count() {
	final Query query = getEntityManager().createNamedQuery("Reservation.countAll");
	return ((Long) query.getSingleResult()).intValue();
    }

    @SuppressWarnings("unchecked")
    public List<ScheduleEvent> findReservationsFor(final Date start, final Date end, final User user) {
	final List<ScheduleEvent> events = getEntityManager().createNamedQuery("Reservation.findReservationsForInternalUserInDate")
							     .setParameter("username", user.getUsername()).setParameter("start", start, TemporalType.TIMESTAMP)
							     .setParameter("end", end, TemporalType.TIMESTAMP).getResultList();
	return events;
    }

    @SuppressWarnings("unchecked")
    public List<ScheduleEvent> findReservationsFor(final User user) {
	final List<ScheduleEvent> events = getEntityManager().createNamedQuery("Reservation.findReservationsForInternalUser").setParameter("username",
																	   user.getUsername())
							     .getResultList();
	return events;
    }

    @SuppressWarnings("unchecked")
    public List<ScheduleEvent> findReservationsFor(final Date start, final Date end, final String externalUser, final String loginDomain) {
	final List<ScheduleEvent> events = getEntityManager().createNamedQuery("Reservation.findReservationsForExternalUserInDate")
							     .setParameter("externalUser", externalUser).setParameter("loginDomain", loginDomain)
							     .setParameter("start", start, TemporalType.TIMESTAMP).setParameter("end", end,
																TemporalType.TIMESTAMP)
							     .getResultList();
	return events;
    }

    @SuppressWarnings("unchecked")
    public List<ScheduleEvent> findReservationsFor(final String externalUser, final String loginDomain) {
	final List<ScheduleEvent> events = getEntityManager().createNamedQuery("Reservation.findReservationsForExternalUser").setParameter("externalUser",
																	   externalUser)
							     .setParameter("loginDomain", loginDomain).getResultList();
	return events;
    }

    @SuppressWarnings("unchecked")
    public List<ScheduleEvent> findAllReservations() {
	return getEntityManager().createNamedQuery("Reservation.findAll").getResultList();
    }
}