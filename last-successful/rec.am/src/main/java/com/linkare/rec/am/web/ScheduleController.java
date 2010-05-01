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
import com.linkare.rec.am.model.ExternalCourseFacade;
import com.linkare.rec.am.model.Reservation;
import com.linkare.rec.am.model.ReservationFacade;
import com.linkare.rec.am.model.UserFacade;
import com.linkare.rec.am.model.moodle.MoodleRecord;
import com.linkare.rec.am.web.auth.UserView;
import com.linkare.rec.am.web.moodle.SessionHelper;
import com.linkare.rec.am.web.util.ConstantUtils;
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
	    createOrUpdate();
	} else {
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
	if (StringUtils.isBlank(event.getId())) {
	    try {
		// It is necessary to first update the model so that the event has a reservation id
		eventModel.addEvent(event);
		ejbFacade.create(event);
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
		event = ejbFacade.edit(event);
		eventModel.updateEvent(event);
	    } catch (Exception e) {
		if (e.getCause() instanceof DomainException) {
		    JsfUtil.addErrorMessage(ResourceBundle.getBundle(ConstantUtils.BUNDLE).getString(e.getCause().getMessage()));
		} else {
		    JsfUtil.addErrorMessage(ResourceBundle.getBundle(ConstantUtils.BUNDLE).getString(ConstantUtils.ERROR_PERSISTENCE_KEY));
		}
	    }
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
	event = (Reservation) selectEvent.getScheduleEvent();
	createOrUpdate();
	JsfUtil.addSuccessMessage("Day delta:" + selectEvent.getDayDelta() + ", Minute delta:" + selectEvent.getMinuteDelta());
    }

    public void onEventResize(ScheduleEntryResizeEvent selectEvent) {
	event = (Reservation) selectEvent.getScheduleEvent();
	createOrUpdate();
	JsfUtil.addSuccessMessage("Event resized", "Day delta:" + selectEvent.getDayDelta() + ", Minute delta:" + selectEvent.getMinuteDelta());
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
	this.externalCourses = Arrays.copyOf(externalCourses, externalCourses.length);
    }
}