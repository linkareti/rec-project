package com.linkare.rec.am.model;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import org.primefaces.model.ScheduleEvent;

/**
 * 
 * @author Joao
 */
@Stateless
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
	em.remove(em.merge(reservation));
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

    public List<ScheduleEvent> fetchLazy(Date start, Date end, UserPrincipal user) {
	List<ScheduleEvent> eventList = em.createQuery(
						       "SELECT r FROM Reservation r " + "WHERE r.userPrincipal=:user "
							       + "AND r.startDate BETWEEN :start AND :end").setParameter("user", user).setParameter("start",
																		    start)
					  .setParameter("end", end).getResultList();

	return eventList;
    }
}
