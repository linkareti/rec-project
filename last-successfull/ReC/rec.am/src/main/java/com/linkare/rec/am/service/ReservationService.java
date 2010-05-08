package com.linkare.rec.am.service;

import java.util.Date;
import java.util.List;

import org.primefaces.model.ScheduleEvent;

import com.linkare.commons.jpa.security.User;
import com.linkare.rec.am.model.Reservation;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
public interface ReservationService extends BusinessService<Reservation, Long> {

    public List<ScheduleEvent> findReservationsFor(final Date start, final Date end, final User user);

    public List<ScheduleEvent> findReservationsFor(final User user);

    public List<ScheduleEvent> findReservationsFor(final Date start, final Date end, final String externalUser, final String loginDomain);

    public List<ScheduleEvent> findReservationsFor(final String externalUser, final String loginDomain);

    public List<ScheduleEvent> findAllReservations();
}