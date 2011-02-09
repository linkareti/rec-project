package com.linkare.rec.am.web.controller;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import com.linkare.commons.jpa.security.User;
import com.linkare.commons.utils.StringUtils;
import com.linkare.jsf.utils.JsfUtil;
import com.linkare.rec.am.aop.AllocationManagerExceptionHandler;
import com.linkare.rec.am.aop.ExceptionHandle;
import com.linkare.rec.am.aop.ExceptionHandleCase;
import com.linkare.rec.am.aop.ScheduleControllerCreateOrUpdateExceptionHandler;
import com.linkare.rec.am.model.Reservation;
import com.linkare.rec.am.model.moodle.ExternalCourse;
import com.linkare.rec.am.service.ExternalCourseServiceBean;
import com.linkare.rec.am.service.ReservationService;
import com.linkare.rec.am.service.ReservationServiceLocal;
import com.linkare.rec.am.service.UserService;
import com.linkare.rec.am.service.UserServiceLocal;
import com.linkare.rec.am.web.auth.UserView;
import com.linkare.rec.am.web.moodle.MoodleClientHelper;
import com.linkare.rec.am.web.moodle.SessionHelper;
import com.linkare.rec.am.web.util.ConstantUtils;

@ManagedBean(name = "scheduleController")
@ViewScoped
public class ScheduleController implements Serializable {

    private static final long serialVersionUID = 1L;

    private ScheduleModel eventModel;

    private Reservation event;

    @EJB(beanInterface = ReservationServiceLocal.class)
    private ReservationService reservationService;

    @EJB(beanInterface = UserServiceLocal.class)
    private UserService userService;

    private SelectItem[] externalCourses;

    private String theme;

    private ExternalCourse externalCourse;

    public ScheduleModel getEventModel() {
	return eventModel = eventModel == null ? new AllocationManagerScheduleModel() : eventModel;
    }

    /**
     * @return the reservationService
     */
    public ReservationService getReservationService() {
	return reservationService;
    }

    /**
     * @return the userService
     */
    public UserService getUserService() {
	return userService;
    }

    public Reservation getEvent() {
	if (event == null) {
	    event = new Reservation(getUser(), SessionHelper.getLoginDomain());
	    event.setStyleClass("myevents");
	}
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
	event.setStyleClass("myevents");
    }

    @ExceptionHandle(@ExceptionHandleCase(exceptionHandler = AllocationManagerExceptionHandler.class))
    public void removeEvent(ActionEvent actionEvent) {
	if (event.getId() != null) {
	    eventModel.deleteEvent(event);
	    reservationService.remove(event);
	    JsfUtil.addGlobalSuccessMessage(ConstantUtils.BUNDLE, ConstantUtils.LABEL_INFO_KEY, ConstantUtils.INFO_REMOVE_KEY);
	    refreshUserView();

	}
    }

    @ExceptionHandle(@ExceptionHandleCase(exceptionHandler = ScheduleControllerCreateOrUpdateExceptionHandler.class))
    public final String createOrUpdate() {
	if (StringUtils.isBlank(event.getId())) {
	    event.setUser(getUser());
	    event.setDomain(SessionHelper.getLoginDomain());
	    event.setStyleClass("myevents");
	    // It is necessary to first update the model so that the event has a reservation id
	    eventModel.addEvent(event);
	    reservationService.create(event);
	    JsfUtil.addGlobalSuccessMessage(ConstantUtils.BUNDLE, ConstantUtils.LABEL_INFO_KEY, ConstantUtils.INFO_CREATE_KEY);
	    refreshUserView();
	} else {
	    // It is necessary to get the merged event and only after that, update the event model
	    event = reservationService.edit(event);
	    eventModel.updateEvent(event);
	    JsfUtil.addGlobalSuccessMessage(ConstantUtils.BUNDLE, ConstantUtils.LABEL_INFO_KEY, ConstantUtils.INFO_UPDATE_KEY);
	    refreshUserView();
	}
	return null;
    }

    private void refreshUserView() {
	final UserView userView = SessionHelper.getUserView();
	userView.setReservations(reservationService.findReservationsFor(new Date(), null, userView));
	SessionHelper.setUserView(userView);
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

    private final class AllocationManagerScheduleModel extends LazyScheduleModel {

	private static final long serialVersionUID = -4448226960542846688L;

	@Override
	public void loadEvents(Date start, Date end) {
	    clear();
	    final List<ScheduleEvent> events = reservationService.findAllReservationsFor(start, end, SessionHelper.getUserView());
	    for (final ScheduleEvent scheduleEvent : events) {
		addEvent(scheduleEvent);
	    }
	}
    }
}