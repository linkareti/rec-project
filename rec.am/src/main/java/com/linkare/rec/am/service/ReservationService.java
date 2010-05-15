package com.linkare.rec.am.service;

import java.util.Date;
import java.util.List;

import org.primefaces.model.ScheduleEvent;

import com.linkare.commons.jpa.security.User;
import com.linkare.rec.am.model.Reservation;
import com.linkare.rec.am.web.auth.UserView;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
public interface ReservationService extends BusinessService<Reservation, Long> {

    public List<ScheduleEvent> findReservationsFor(final UserView userView);

    public List<ScheduleEvent> findReservationsFor(final Date start, final Date end, final UserView userView);

    public List<ScheduleEvent> findAllReservationsFor(final Date start, final Date end, final UserView userView);

    public Reservation getReservationDetails(final Long id);

    public void addUser(final Reservation reservation, final User user);

    public void removeUser(final Reservation reservation, final User user);
}