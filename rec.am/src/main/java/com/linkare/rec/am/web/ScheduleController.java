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

import com.linkare.rec.am.model.ExternalCourseFacade;
import com.linkare.rec.am.model.Reservation;
import com.linkare.rec.am.model.ReservationFacade;
import com.linkare.rec.am.model.UserPrincipal;
import com.linkare.rec.am.model.UserPrincipalFacade;
import com.linkare.rec.am.model.moodle.MoodleRecord;
import com.linkare.rec.am.web.auth.UserView;
import com.linkare.rec.am.web.moodle.SessionHelper;
import com.linkare.rec.am.web.util.JsfUtil;

@ManagedBean(name = "scheduleController")
@ViewScoped
public class ScheduleController implements Serializable {

    private static final long serialVersionUID = 1L;

    private ScheduleModel eventModel;

    private static final int MINUTE_STEP = 30;

    private Reservation event = new Reservation();

    @EJB
    private ReservationFacade ejbFacade;

    @EJB
    private UserPrincipalFacade ejbUserPrincipalFacade;

    private SelectItem[] externalCourses;

    private String theme;

    private List<ScheduleEvent> getEvents() {
	final UserView userView = SessionHelper.getUserView();
	if (userView.isExternal()) {
	    return ejbFacade.findReservationsFor(userView.getUsername());
	}
	final UserPrincipal user = ejbUserPrincipalFacade.findByUsername(userView.getUsername());
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
	    event.getMoodleRecord().setMoodleUrl(userView.getDomain());
	} else {
	    event.setUserPrincipal(ejbUserPrincipalFacade.findByUsername(userView.getUsername()));
	}
    }

    public void onEventMove(ScheduleEntryMoveEvent selectEvent) {
	FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event moved", "Day delta:" + selectEvent.getDayDelta() + ", Minute delta:"
		+ selectEvent.getMinuteDelta());
	event = (Reservation) selectEvent.getScheduleEvent();
	createOrUpdate();
	addMessage(message);
    }

    public void onEventResize(ScheduleEntryResizeEvent selectEvent) {
	FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event resized", "Day delta:" + selectEvent.getDayDelta() + ", Minute delta:"
		+ selectEvent.getMinuteDelta());
	event = (Reservation) selectEvent.getScheduleEvent();
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