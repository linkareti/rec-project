package com.linkare.rec.am.aop;

import com.linkare.commons.jpa.exceptions.DomainException;
import com.linkare.commons.utils.StringUtils;
import com.linkare.jsf.utils.JsfUtil;
import com.linkare.rec.am.model.Reservation;
import com.linkare.rec.am.web.controller.ScheduleController;
import com.linkare.rec.am.web.util.ConstantUtils;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
public class ScheduleControllerCreateOrUpdateExceptionHandler extends LoggerExceptionHandler {

    @Override
    public Object execute(final Object target, final Object methodResult, Throwable throwable) {
	super.execute(target, methodResult, throwable);
	if (throwable.getCause() instanceof DomainException) {
	    JsfUtil.addGlobalErrorMessage(ConstantUtils.BUNDLE, ConstantUtils.LABEL_ERROR_KEY, throwable.getCause().getMessage());
	} else {
	    JsfUtil.addGlobalErrorMessage(ConstantUtils.BUNDLE, ConstantUtils.LABEL_ERROR_KEY, ConstantUtils.ERROR_PERSISTENCE_KEY);
	}
	// Remove the newly added event if an exception was captured
	ScheduleController scheduleController = (ScheduleController) target;
	final Reservation event = scheduleController.getEvent();
	if (StringUtils.isBlank(event.getId())) {
	    scheduleController.getEventModel().deleteEvent(scheduleController.getEvent());
	} else {
	    scheduleController.setEvent(scheduleController.getReservationService().find(event.getIdInternal()));
	    scheduleController.getEventModel().updateEvent(event);
	}
	return null;
    }
}