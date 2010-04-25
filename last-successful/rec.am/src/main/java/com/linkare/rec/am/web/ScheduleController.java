/*
 * Copyright 2009 Prime Technology.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.linkare.rec.am.web;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.primefaces.event.DateSelectEvent;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.ScheduleEntrySelectEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import com.linkare.commons.jpa.security.User;
import com.linkare.rec.am.model.ExternalCourseFacade;
import com.linkare.rec.am.model.Reservation;
import com.linkare.rec.am.model.ReservationFacade;
import com.linkare.rec.am.model.UserFacade;
import com.linkare.rec.am.model.moodle.MoodleRecord;
import com.linkare.rec.am.web.auth.UserView;
import com.linkare.rec.am.web.moodle.SessionHelper;
import com.linkare.rec.am.web.util.JsfUtil;

@ManagedBean(name = "scheduleController")
@ViewScoped
public class ScheduleController implements Serializable {

    private static final long serialVersionUID = 1L;

    private ScheduleModel eventModel;

    private Reservation event = new Reservation();

    @EJB
    private ReservationFacade ejbFacade;

    @EJB
    private UserFacade ejbUserFacade;

    private SelectItem[] externalCourses;

    private String theme;

    private static Calendar cal = Calendar.getInstance();

    private List<ScheduleEvent> getEvents() {
	final UserView userView = SessionHelper.getUserView();
	if (userView.isExternal()) {
	    return ejbFacade.findReservationsFor(userView.getUsername(), userView.getDomain());
	}
	final User user = ejbUserFacade.findByUsername(userView.getUsername());
	return ejbFacade.findReservationsFor(user);
    }

    public ScheduleModel getEventModel() {
	return eventModel = eventModel == null ? new DefaultScheduleModel(getEvents()) : eventModel;
    }

    public Reservation getEvent() {
	return event;
    }

    public void setEvent(Reservation event) {
	this.event = event;
    }

    public void addEvent(ActionEvent actionEvent) {
	if (event.getId() == null) {
	    eventModel.addEvent(event);
	    createOrUpdate();
	} else {
	    eventModel.updateEvent(event);
	    createOrUpdate();
	}
	event = new Reservation();
    }

    public void removeEvent(ActionEvent actionEvent) {
	if (event.getId() != null) {
	    eventModel.deleteEvent(event);
	    ejbFacade.remove(event);
	}
    }

    public final String createOrUpdate() {
	if (event.getId() == null) {
	    final String startTimeSlot = event.getStartTimeSlot();
	    final StringTokenizer startTimeSlotTokenizer = new StringTokenizer(startTimeSlot, ":");

	    cal.setTime(event.getStartDate());
	    cal.set(Calendar.HOUR, Integer.parseInt(startTimeSlotTokenizer.nextToken()));
	    cal.set(Calendar.MINUTE, Integer.parseInt(startTimeSlotTokenizer.nextToken()));
	    event.setStartDate(cal.getTime());
	    event.setStartTimeSlot(startTimeSlot);

	    final String endTimeSlot = event.getEndTimeSlot();
	    final StringTokenizer endTimeSlotTokenizer = new StringTokenizer(endTimeSlot, ":");
	    cal.setTime(event.getEndDate());
	    cal.set(Calendar.HOUR, Integer.parseInt(endTimeSlotTokenizer.nextToken()));
	    cal.set(Calendar.MINUTE, Integer.parseInt(endTimeSlotTokenizer.nextToken()));

	    event.setEndDate(cal.getTime());
	    event.setEndTimeSlot(cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE));

	    eventModel.addEvent(event);
	    ejbFacade.create(event);
	} else {
	    eventModel.updateEvent(event);
	    ejbFacade.edit(event);
	}
	return null;
    }

    public void onEventSelect(ScheduleEntrySelectEvent selectEvent) {
	event = (Reservation) selectEvent.getScheduleEvent();
    }

    public void onDateSelect(DateSelectEvent selectEvent) {
	final UserView userView = SessionHelper.getUserView();
	event = new Reservation("", selectEvent.getDate(), selectEvent.getDate());

	if (userView.isExternal()) {
	    event.setMoodleRecord(new MoodleRecord());
	    event.getMoodleRecord().setExternalUser(userView.getUsername());
	    event.getMoodleRecord().setDomain(userView.getDomain());
	} else {
	    event.setUser(ejbUserFacade.findByUsername(userView.getUsername()));
	}
    }

    public void onEventMove(ScheduleEntryMoveEvent selectEvent) {
	FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event moved", "Day delta:" + selectEvent.getDayDelta() + ", Minute delta:"
		+ selectEvent.getMinuteDelta());
	event = (Reservation) selectEvent.getScheduleEvent();
	moveEventTime(event, selectEvent.getMinuteDelta());
	createOrUpdate();
	addMessage(message);
    }

    private void moveEventTime(final Reservation reservation, final int minuteDelta) {
	cal.setTime(reservation.getStartDate());
	cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE) + minuteDelta);
	reservation.setStartDate(cal.getTime());

	cal.setTime(reservation.getEndDate());
	cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE) + minuteDelta);
	reservation.setEndDate(cal.getTime());
    }

    private void extendEventTime(final Reservation reservation, final int minuteDelta) {
	cal.setTime(reservation.getEndDate());
	cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE) + minuteDelta);
	reservation.setEndDate(cal.getTime());
    }

    public void onEventResize(ScheduleEntryResizeEvent selectEvent) {
	FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event resized", "Day delta:" + selectEvent.getDayDelta() + ", Minute delta:"
		+ selectEvent.getMinuteDelta());
	event = (Reservation) selectEvent.getScheduleEvent();
	extendEventTime(event, selectEvent.getMinuteDelta());
	createOrUpdate();
	addMessage(message);
    }

    private void addMessage(FacesMessage message) {
	FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public String getTheme() {
	return theme;
    }

    public void setTheme(String theme) {
	this.theme = theme;
    }

    /**
     * @return the externalCourses
     */
    public SelectItem[] getExternalCourses() {
	return externalCourses = externalCourses == null ? JsfUtil.getSelectItems(new ExternalCourseFacade().findAll(), true) : externalCourses;
    }

    /**
     * @param externalCourses
     *            the externalCourses to set
     */
    public void setExternalCourses(SelectItem[] externalCourses) {
	this.externalCourses = externalCourses;
    }
}