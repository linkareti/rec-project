package com.linkare.rec.am.model;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;

import org.primefaces.model.ScheduleEvent;

import com.linkare.commons.jpa.exceptions.DomainException;
import com.linkare.commons.jpa.security.User;
import com.linkare.commons.utils.BooleanResult;

/**
 * 
 * @author Joao
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ReservationFacade extends Facade<Reservation, Long> {

    @Override
    public void create(final Reservation reservation) {
	final BooleanResult operationResult = canCreate(reservation);
	if (operationResult.getResult() == Boolean.TRUE) {
	    em.persist(reservation);
	} else {
	    throw new DomainException(operationResult.getMessage());
	}
    }

    protected BooleanResult canCreate(final Reservation reservation) {
	return isValid(reservation);
    }

    @Override
    public Reservation edit(final Reservation reservation) {
	final BooleanResult operationResult = canEdit(reservation);
	if (operationResult.getResult() == Boolean.TRUE) {
	    return em.merge(reservation);
	} else {
	    throw new DomainException(operationResult.getMessage());
	}
    }

    protected BooleanResult canEdit(final Reservation reservation) {
	return isValid(reservation);
    }

    @SuppressWarnings("unchecked")
    private BooleanResult isValid(final Reservation reservation) {
	final Query query = em.createNamedQuery("Reservation.findByExperimentNameInInterval").setParameter("startDate", reservation.getStartDate())
			      .setParameter("endDate", reservation.getEndDate()).setParameter("experimentName", reservation.getExperiment().getName());
	final List<Reservation> reservationsForExperimentNameInInterval = query.getResultList();
	return reservation.isValid(reservationsForExperimentNameInInterval);
    }

    @Override
    public void remove(final Reservation reservation) {
	final Reservation mergedReservation = em.merge(reservation);
	mergedReservation.delete();
	em.remove(mergedReservation);
    }

    @Override
    public Reservation find(final Long id) {
	return em.find(Reservation.class, id);
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
	Query q = em.createNamedQuery("Reservation.findAll");
	if (!all) {
	    q.setMaxResults(maxResults);
	    q.setFirstResult(firstResult);
	}
	return q.getResultList();
    }

    @Override
    public int count() {
	final Query query = em.createNamedQuery("Reservation.countAll");
	return ((Long) query.getSingleResult()).intValue();
    }

    @SuppressWarnings("unchecked")
    public List<ScheduleEvent> findReservationsFor(final Date start, final Date end, final User user) {
	final List<ScheduleEvent> events = em.createNamedQuery("Reservation.findReservationsForInternalUserInDate")
					     .setParameter("username", user.getUsername()).setParameter("start", start).setParameter("end", end)
					     .getResultList();
	return events;
    }

    @SuppressWarnings("unchecked")
    public List<ScheduleEvent> findReservationsFor(final User user) {
	final List<ScheduleEvent> events = em.createNamedQuery("Reservation.findReservationsForInternalUser").setParameter("username", user.getUsername())
					     .getResultList();
	return events;
    }

    @SuppressWarnings("unchecked")
    public List<ScheduleEvent> findReservationsFor(final Date start, final Date end, final String externalUser, final String loginDomain) {
	final List<ScheduleEvent> events = em.createNamedQuery("Reservation.findReservationsForExternalUserInDate").setParameter("externalUser", externalUser)
					     .setParameter("loginDomain", loginDomain).setParameter("start", start).setParameter("end", end).getResultList();
	return events;
    }

    public List<ScheduleEvent> findReservationsFor(final String externalUser, final String loginDomain) {
	final List<ScheduleEvent> events = em.createNamedQuery("Reservation.findReservationsForExternalUser").setParameter("externalUser", externalUser)
					     .setParameter("loginDomain", loginDomain).getResultList();
	return events;
    }
}