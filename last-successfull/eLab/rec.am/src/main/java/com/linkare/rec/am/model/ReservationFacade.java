package com.linkare.rec.am.model;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;

import org.primefaces.model.ScheduleEvent;

/**
 * 
 * @author Joao
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ReservationFacade extends Facade<Reservation> {

    @Override
    public void create(Reservation reservation) {
	em.persist(reservation);
    }

    @Override
    public void edit(Reservation reservation) {
	em.merge(reservation);
    }

    @Override
    public void remove(Reservation reservation) {
	reservation = em.merge(reservation);
	reservation.remove();
	em.remove(reservation);
    }

    @Override
    public Reservation find(Object id) {
	return em.find(Reservation.class, id);
    }

    @Override
    public List<Reservation> findAll() {
	final Query query = em.createNamedQuery("Reservation.findAll");
	return query.getResultList();
    }

    @Override
    public List<Reservation> findRange(int[] range) {
	final Query query = em.createNamedQuery("Reservation.findAll");
	query.setMaxResults(range[1] - range[0]);
	query.setFirstResult(range[0]);
	return query.getResultList();
    }

    @Override
    public int count() {
	final Query query = em.createNamedQuery("Reservation.countAll");
	return ((Long) query.getSingleResult()).intValue();
    }

    public List<ScheduleEvent> findReservationsFor(Date start, Date end, UserPrincipal user) {
	final List<ScheduleEvent> events = em.createNamedQuery("Reservation.findReservationsForInternalUserInDate")
					     .setParameter("username", user.getUsername()).setParameter("start", start).setParameter("end", end)
					     .getResultList();
	return events;
    }

    public List<ScheduleEvent> findReservationsFor(UserPrincipal user) {
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