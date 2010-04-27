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

/**
 * 
 * @author Joao
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ReservationFacade extends Facade<Reservation> {

    @Override
    public void create(Reservation reservation) {
	if (canCreate(reservation)) {
	    em.persist(reservation);
	} else {
	    throw new DomainException("error.laboratory.taken");
	}
    }

    @SuppressWarnings("unchecked")
    protected boolean canCreate(final Reservation reservation) {
	final Query query = em.createNamedQuery("Reservation.findByExperimentNameInInterval").setParameter("startDate", reservation.getStartDate())
			      .setParameter("endDate", reservation.getEndDate()).setParameter("experimentName", reservation.getExperiment().getName());
	final List<Reservation> reservationsForExperimentNameInInterval = query.getResultList();
	return !reservation.hasConflicts(reservationsForExperimentNameInInterval);
    }

    @Override
    public Reservation edit(Reservation reservation) {
	if (canEdit(reservation)) {
	    return em.merge(reservation);
	} else {
	    throw new DomainException("error.laboratory.taken");
	}
    }

    @SuppressWarnings("unchecked")
    protected boolean canEdit(final Reservation reservation) {
	final Query query = em.createNamedQuery("Reservation.findByExperimentNameInInterval").setParameter("startDate", reservation.getStartDate())
			      .setParameter("endDate", reservation.getEndDate()).setParameter("experimentName", reservation.getExperiment().getName());
	final List<Reservation> reservationsForExperimentNameInInterval = query.getResultList();
	return !reservation.hasConflicts(reservationsForExperimentNameInInterval);
    }

    @Override
    public void remove(Reservation reservation) {
	reservation = em.merge(reservation);
	reservation.delete();
	em.remove(reservation);
    }

    @Override
    public Reservation find(Object id) {
	return em.find(Reservation.class, id);
    }

    @Override
    public List<Reservation> findRange(int[] range) {
	return find(false, range[0], range[1]);
    }

    @Override
    public List<Reservation> findAll() {
	return find(true, -1, -1);
    }

    @SuppressWarnings("unchecked")
    public List<Reservation> find(boolean all, int firstResult, int maxResults) {
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

    public List<ScheduleEvent> findReservationsFor(Date start, Date end, User user) {
	final List<ScheduleEvent> events = em.createNamedQuery("Reservation.findReservationsForInternalUserInDate")
					     .setParameter("username", user.getUsername()).setParameter("start", start).setParameter("end", end)
					     .getResultList();
	return events;
    }

    public List<ScheduleEvent> findReservationsFor(User user) {
	final List<ScheduleEvent> events = em.createNamedQuery("Reservation.findReservationsForInternalUser").setParameter("username", user.getUsername())
					     .getResultList();
	return events;
    }

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