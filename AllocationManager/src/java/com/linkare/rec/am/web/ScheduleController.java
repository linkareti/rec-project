package com.linkare.rec.am.web;

import com.linkare.rec.am.model.Reservation;
import com.linkare.rec.am.model.ReservationFacade;
import com.linkare.rec.am.model.UserPrincipal;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;

import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import java.util.logging.Logger;

import org.primefaces.event.ScheduleDateSelectEvent;
import org.primefaces.event.ScheduleEntrySelectEvent;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

/**
 *
 * @author Joao
 */
@ManagedBean(name = "scheduleController")
@SessionScoped
public class ScheduleController implements Serializable {

    static Logger logger = Logger.getLogger("ScheduleController");
    private static ScheduleModel<ScheduleEvent> eventModel;
    private Reservation event = new Reservation();
    @EJB
    private com.linkare.rec.am.model.ReservationFacade ejbFacade;
    @EJB
    private com.linkare.rec.am.model.UserFacade ejbUserFacade;
    // Inject The Credentials Weld bean.
    @Inject
    Credentials credentials;

    public ScheduleController() {

        logger.info("ScheduleController");
        eventModel = new ScheduleModel<ScheduleEvent>() {

            @Override
            public boolean isLazy() {
                return true;
            }

            @Override
            public void fetchEvents(Date start, Date end) {
                setEvents(getFacade().fetchLazy2(start, end));	//clean other periods
            }
        };
    }

    public Date getRandomDate(Date base) {
        Calendar date = Calendar.getInstance();
        date.setTime(base);
        date.add(Calendar.DATE, ((int) (Math.random() * 30)) + 1);	//set random day of month
        return date.getTime();
    }

    public Date getInitialDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), Calendar.FEBRUARY, calendar.get(Calendar.DATE), 0, 0, 0);
        return calendar.getTime();
    }

    public ScheduleModel<ScheduleEvent> getEventModel() {
        return eventModel;
    }

    public Reservation getEvent() {
        return event;
    }

    public void setEvent(Reservation event) {
        this.event = event;
    }

    public void saveEvent(ActionEvent actionEvent) {
        if (event.getId() == null) {
            String timeSlot = event.getStartTimeSlot();
            StringTokenizer st = new StringTokenizer(timeSlot, ":");
            Calendar cal = Calendar.getInstance();
            cal.setTime(event.getStartDate());
            cal.set(Calendar.HOUR, Integer.parseInt(st.nextToken()));
            cal.set(Calendar.MINUTE, Integer.parseInt(st.nextToken()));
            event.setStartDate(cal.getTime());
            event.setStartTimeSlot(timeSlot);
            if (cal.get(Calendar.MINUTE) == 30) {
                cal.roll(Calendar.HOUR, true);
                cal.set(Calendar.MINUTE, 0);
            } else {
                cal.set(Calendar.MINUTE, 30);
            }
            event.setEndDate(cal.getTime());
            event.setEndTimeSlot(Integer.toString(cal.get(Calendar.HOUR_OF_DAY)) + ":" + Integer.toString(Calendar.MINUTE));
            eventModel.addEvent(event);
            getFacade().create(event);
        } else {
            eventModel.updateEvent(event);
            getFacade().edit(event);
        }
    }

    public void addEvent(ActionEvent actionEvent) {
        if (event.getId() == null) {
            eventModel.addEvent(event);
        } else {
            eventModel.updateEvent(event);
        }
        event = new Reservation();
    }

    public void removeEvent(ActionEvent actionEvent) {
        if (event.getId() != null) {
            eventModel.deleteEvent(event);
            getFacade().remove(event);
        }
    }

    public void onEventSelect(ScheduleEntrySelectEvent selectEvent) {
        event = (Reservation) selectEvent.getScheduleEvent();
    }

    public void onDateSelect(ScheduleDateSelectEvent selectEvent) {
        event = new Reservation();
        event.setStartDate(selectEvent.getDate());
        event.setEndDate(selectEvent.getDate());
        String userName = credentials.getUsername();
//        String userName = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName();
        logger.info(userName);
        UserPrincipal user = getEjbUserFacade().find(userName);
        event.setUserPrincipal(user);
    }

    /**
     * @return the ejbFacade
     */
    public ReservationFacade getFacade() {
        return ejbFacade;
    }

    /**
     * @return the ejbUserFacade
     */
    public com.linkare.rec.am.model.UserFacade getEjbUserFacade() {
        return ejbUserFacade;
    }

}
