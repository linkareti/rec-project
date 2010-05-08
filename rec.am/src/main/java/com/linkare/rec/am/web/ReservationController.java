package com.linkare.rec.am.web;

import java.util.Calendar;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.linkare.rec.am.model.Reservation;
import com.linkare.rec.am.model.moodle.MoodleRecord;
import com.linkare.rec.am.service.ReservationService;
import com.linkare.rec.am.service.ReservationServiceLocal;
import com.linkare.rec.am.web.controller.AbstractController;
import com.linkare.rec.am.web.moodle.SessionHelper;
import com.linkare.rec.am.web.util.ConstantUtils;
import com.linkare.rec.am.web.util.JsfUtil;

@ManagedBean(name = "reservationController")
@RequestScoped
public class ReservationController extends AbstractController<Long, Reservation, ReservationService> {

    private static final long serialVersionUID = 1L;

    @EJB(beanInterface = ReservationServiceLocal.class)
    private ReservationService service;

    public final Reservation getSelected() {
	if (getCurrent() == null) {
	    setCurrent(new Reservation(SessionHelper.getLoginDomain()));
	}
	return getCurrent();
    }

    @Override
    protected ReservationService getService() {
	return service;
    }

    @Override
    public final String prepareCreate() {
	setCurrent(new Reservation());
	return ConstantUtils.CREATE;
    }

    public final String prepareCreateExternal() {
	final String externalUser = SessionHelper.getUsername();
	final String externalCourse = JsfUtil.getRequestParameter("externalCourse");
	final String domain = SessionHelper.getLoginDomain();
	setCurrent(new Reservation(externalUser, externalCourse));
	return ConstantUtils.CREATE;
    }

    public final void processEndDateAndEndTimeSlot() {
	Calendar cal = Calendar.getInstance();
	cal.setTime(getCurrent().getStartDate());
	getCurrent().setStartDate(cal.getTime());
	getCurrent().setEndDate(cal.getTime());
    }

    @FacesConverter(value = "reservationConverter", forClass = Reservation.class)
    public static class ReservationConverter implements Converter {

	@Override
	public final Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
	    if (value == null || value.length() == 0) {
		return null;
	    }
	    ReservationController controller = (ReservationController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(),
															      null, "reservationController");
	    return controller.service.find(getKey(value));
	}

	private Long getKey(String value) {
	    return Long.valueOf(value);
	}

	private String getStringKey(Long value) {
	    return value.toString();
	}

	@Override
	public final String getAsString(FacesContext facesContext, UIComponent component, Object object) {
	    if (object == null) {
		return null;
	    }
	    if (object instanceof Reservation) {
		Reservation o = (Reservation) object;
		return getStringKey(o.getIdInternal());
	    } else {
		throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: "
			+ Reservation.class.getName());
	    }
	}
    }

    @FacesConverter(forClass = MoodleRecord.class)
    public static class MoodleRecordConverter implements Converter {

	@Override
	public final Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
	    if (value == null || value.length() == 0) {
		return null;
	    }
	    final String[] valueElements = value.split("_");
	    return new MoodleRecord(valueElements[0], valueElements[1]);
	}

	@Override
	public final String getAsString(FacesContext facesContext, UIComponent component, Object object) {
	    if (object == null) {
		return null;
	    }
	    if (object instanceof MoodleRecord) {
		final MoodleRecord record = (MoodleRecord) object;
		return record.getExternalUser() + "_" + record.getExternalCourseId();
	    } else {
		throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: "
			+ Reservation.class.getName());
	    }
	}
    }
}