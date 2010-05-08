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
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.joda.time.DateTime;
import org.primefaces.event.DateSelectEvent;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.ScheduleEntrySelectEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import com.linkare.commons.jpa.exceptions.DomainException;
import com.linkare.commons.jpa.security.User;
import com.linkare.commons.utils.StringUtils;
import com.linkare.rec.am.model.Reservation;
import com.linkare.rec.am.model.moodle.ExternalCourse;
import com.linkare.rec.am.service.ExternalCourseServiceBean;
import com.linkare.rec.am.service.ReservationService;
import com.linkare.rec.am.service.ReservationServiceLocal;
import com.linkare.rec.am.service.UserService;
import com.linkare.rec.am.service.UserServiceLocal;
import com.linkare.rec.am.web.moodle.MoodleClientHelper;
import com.linkare.rec.am.web.moodle.SessionHelper;
import com.linkare.rec.am.web.util.ConstantUtils;
import com.linkare.rec.am.web.util.JsfUtil;

@ManagedBean(name = "scheduleController")
@ViewScoped
public class ScheduleController implements Serializable {

    private static final long serialVersionUID = 1L;

    private ScheduleModel eventModel;

    private ScheduleModel lazyEventModel;

    private Reservation event = new Reservation();

    @EJB(beanInterface = ReservationServiceLocal.class)
    private ReservationService reservationService;

    @EJB(beanInterface = UserServiceLocal.class)
    private UserService userService;

    private SelectItem[] externalCourses;

    private String theme;

    private ExternalCourse externalCourse;

    private List<ScheduleEvent> getEvents() {
	return reservationService.findReservationsFor(SessionHelper.getUserView());
    }

    private List<ScheduleEvent> getAllEvents() {
	return reservationService.findAllReservations();
    }

    public ScheduleModel getEventModel() {
	return eventModel = eventModel == null ? new DefaultScheduleModel(getEvents()) : eventModel;
    }

    // TODO: It may be necessary to use a lazy event model later!!!
    public ScheduleModel getLazyEventModel() {
	return lazyEventModel = lazyEventModel == null ? new DefaultScheduleModel(getAllEvents()) : lazyEventModel;
    }

    public Reservation getEvent() {
	return event;
    }

    public void setEvent(Reservation event) {
	this.event = event;
    }

    public void addEvent(ActionEvent actionEvent) {
	if (event.getId() == null) {
	    createOrUpdate();
	} else {
	    createOrUpdate();
	}
	event = new Reservation();
    }

    public void removeEvent(ActionEvent actionEvent) {
	if (event.getId() != null) {
	    eventModel.deleteEvent(event);
	    reservationService.remove(event);
	}
    }

    public final String createOrUpdate() {
	if (StringUtils.isBlank(event.getId())) {
	    try {
		event.setUser(getUser());
		// It is necessary to first update the model so that the event has a reservation id
		eventModel.addEvent(event);
		reservationService.create(event);
	    } catch (Exception e) {
		if (e.getCause() instanceof DomainException) {
		    JsfUtil.addErrorMessage(ResourceBundle.getBundle(ConstantUtils.BUNDLE).getString(e.getCause().getMessage()));
		} else {
		    JsfUtil.addErrorMessage(ResourceBundle.getBundle(ConstantUtils.BUNDLE).getString(ConstantUtils.ERROR_PERSISTENCE_KEY));
		}
		// Remove the newly added event if an exception was captured
		eventModel.deleteEvent(event);
	    }
	} else {
	    try {
		// It is necessary to get the merged event and only after that, update the event model
		event = reservationService.edit(event);
		eventModel.updateEvent(event);
	    } catch (Exception e) {
		if (e.getCause() instanceof DomainException) {
		    JsfUtil.addErrorMessage(ResourceBundle.getBundle(ConstantUtils.BUNDLE).getString(e.getCause().getMessage()));
		} else {
		    JsfUtil.addErrorMessage(ResourceBundle.getBundle(ConstantUtils.BUNDLE).getString(ConstantUtils.ERROR_PERSISTENCE_KEY));
		}
		event = reservationService.find(event.getIdInternal());
		eventModel.updateEvent(event);
	    }
	}
	return null;
    }

    private User getUser() {
	return userService.findByUsername(SessionHelper.getUserView().getFullUsername());
    }

    public void onEventSelect(ScheduleEntrySelectEvent selectEvent) {
	event = (Reservation) selectEvent.getScheduleEvent();
	event.setExternalCourse(MoodleClientHelper.findCourse(event.getReservedTo(), SessionHelper.getLoginDomain(), SessionHelper.getLoginReturn()));
    }

    public void onDateSelect(DateSelectEvent selectEvent) {
	event = new Reservation("", selectEvent.getDate(), selectEvent.getDate());
    }

    public void onEventMove(ScheduleEntryMoveEvent selectEvent) {
	event = (Reservation) selectEvent.getScheduleEvent();
	event.setExternalCourse(MoodleClientHelper.findCourse(event.getReservedTo(), SessionHelper.getLoginDomain(), SessionHelper.getLoginReturn()));
	moveEvent(true, event, selectEvent.getMinuteDelta());
	createOrUpdate();
    }

    private void moveEvent(final boolean moveBothDates, final Reservation event, final int minuteDelta) {
	if (moveBothDates) {
	    DateTime startDate = new DateTime(event.getStartDate());
	    startDate = startDate.plusMinutes(minuteDelta);
	    event.setStartDate(startDate.toDate());
	}
	DateTime endDate = new DateTime(event.getEndDate());
	endDate = endDate.plusMinutes(minuteDelta);
	event.setEndDate(endDate.toDate());
    }

    public void onEventResize(ScheduleEntryResizeEvent selectEvent) {
	event = (Reservation) selectEvent.getScheduleEvent();
	moveEvent(false, event, selectEvent.getMinuteDelta());
	createOrUpdate();
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
	return externalCourses = externalCourses == null ? JsfUtil.getSelectItems(new ExternalCourseServiceBean().findAll(), true) : externalCourses;
    }

    /**
     * @param externalCourses
     *            the externalCourses to set
     */
    public void setExternalCourses(SelectItem[] externalCourses) {
	this.externalCourses = Arrays.copyOf(externalCourses, externalCourses.length);
    }

    /**
     * @return the externalCourse
     */
    public ExternalCourse getExternalCourse() {
	return externalCourse;
    }

    /**
     * @param externalCourse
     *            the externalCourse to set
     */
    public void setExternalCourse(ExternalCourse externalCourse) {
	this.externalCourse = externalCourse;
    }
}