package com.linkare.rec.am.web;

import com.linkare.rec.am.model.Reservation;
import com.linkare.rec.am.model.ReservationFacade;
import com.linkare.rec.am.model.UserPrincipal;
import com.linkare.rec.am.web.util.JsfUtil;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import javax.faces.event.ActionEvent;
import java.util.logging.Logger;
import javax.faces.bean.RequestScoped;

import org.primefaces.event.ScheduleDateSelectEvent;
import org.primefaces.event.ScheduleEntrySelectEvent;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

/**
 *
 * @author Joao
 */
@ManagedBean(name = "scheduleController")
@RequestScoped
public class ScheduleController implements Serializable {

    private static Logger logger = Logger.getLogger("ScheduleController");

    private static final int MINUTE_STEP = 30;

    private static ScheduleModel<ScheduleEvent> eventModel;

    private Reservation event = new Reservation();

    @EJB
    private com.linkare.rec.am.model.ReservationFacade ejbFacade;

    @EJB
    private com.linkare.rec.am.model.UserPrincipalFacade ejbUserPrincipalFacade;

    public ScheduleController() {

        getLogger().info("ScheduleController");
        eventModel = new ScheduleModel<ScheduleEvent>() {

            @Override
            public boolean isLazy() {
                return true;
            }

            @Override
            public void fetchEvents(Date start, Date end) {
//                setEvents(getFacade().fetchLazy(start, end, (String)JsfUtil.getSessionMapValue("UserName")));	//clean other periods
                UserPrincipal user = ejbUserPrincipalFacade.find((String)JsfUtil.getSessionMapValue("UserName"));
                setEvents(getFacade().fetchLazy(start, end, user));	//clean other periods
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
            if (cal.get(Calendar.MINUTE) == MINUTE_STEP) {
                cal.roll(Calendar.HOUR, true);
                cal.set(Calendar.MINUTE, 0);
            } else if (cal.get(Calendar.MINUTE) == 0) {
                cal.set(Calendar.MINUTE, MINUTE_STEP);
            }
            event.setEndDate(cal.getTime());
            event.setEndTimeSlot(cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE));
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
        event.setUserPrincipal(getEjbUserPrincipalFacade().find((String)JsfUtil.getSessionMapValue("UserName")));
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
    public com.linkare.rec.am.model.UserPrincipalFacade getEjbUserPrincipalFacade() {
        return ejbUserPrincipalFacade;
    }

    /**
     * @return the logger
     */
    public static Logger getLogger() {
        return logger;
    }

    public void createReservation(ActionEvent actionEvent) {

        getLogger().info("createReservation");
        String userName = (String)JsfUtil.getSessionMapValue("UserName");
        getLogger().info("Reservation created by: " + userName!= null ? userName : "null");
        String name = (String)JsfUtil.getSessionMapValue("name");
        name = name != null ? name : "null";
        getLogger().info("name value: " + name);
        event.setUserPrincipal(getEjbUserPrincipalFacade().find(userName));
        saveEvent(actionEvent);
    }

}
