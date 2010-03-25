package com.linkare.rec.am.model;

import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.primefaces.model.ScheduleEvent;

/**
 *
 * @author Joao
 */
@Stateless
public class ReservationFacade {

    @PersistenceContext(unitName = "AllocationManagerPU")
    private EntityManager em;

    public void create(Reservation reservation) {
        em.persist(reservation);
    }

    public void edit(Reservation reservation) {
        em.merge(reservation);
    }

    public void remove(Reservation reservation) {
        em.remove(em.merge(reservation));
    }

    public Reservation find(Object id) {
        return em.find(Reservation.class, id);
    }

    public List<Reservation> findAll() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Reservation.class));
        return em.createQuery(cq).getResultList();
    }

    public List<Reservation> findRange(int[] range) {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Reservation.class));
        Query q = em.createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<Reservation> rt = cq.from(Reservation.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    public List<ScheduleEvent> fetchLazy(Date start, Date end, UserPrincipal user) {
        List<ScheduleEvent> eventList = em.createQuery(
                "SELECT r FROM Reservation r "
                + "WHERE r.userPrincipal=:user "
                + "AND r.startDate BETWEEN :start AND :end").setParameter("user", user).setParameter("start", start).setParameter("end", end).getResultList();

        return eventList;
    }
}
