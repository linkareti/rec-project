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
import com.linkare.rec.am.model.ReservationFacade;
import com.linkare.rec.am.model.moodle.MoodleRecord;
import com.linkare.rec.am.web.controller.AbstractController;
import com.linkare.rec.am.web.moodle.SessionHelper;
import com.linkare.rec.am.web.util.ConstantUtils;
import com.linkare.rec.am.web.util.JsfUtil;

@ManagedBean(name = "reservationController")
@RequestScoped
public class ReservationController extends AbstractController<Reservation, ReservationFacade> {

    private static final long serialVersionUID = 1L;

    @EJB
    private ReservationFacade ejbFacade;

    private static final int MINUTE_STEP = 30;

    public final Reservation getSelected() {
	if (current == null) {
	    current = new Reservation();
	}
	return current;
    }

    @Override
    protected ReservationFacade getFacade() {
	return ejbFacade;
    }

    @Override
    public final String prepareCreate() {
	current = new Reservation();
	return ConstantUtils.CREATE;
    }

    public final String prepareCreateExternal() {
	final String externalUser = SessionHelper.getUsername();
	final String externalCourse = JsfUtil.getRequestParameter("externalCourse");
	final String domain = SessionHelper.getLoginDomain();
	current = new Reservation(externalUser, externalCourse, domain);
	return ConstantUtils.CREATE;
    }

    public final void processEndDateAndEndTimeSlot() {
	Calendar cal = Calendar.getInstance();
	cal.setTime(current.getStartDate());
	current.setStartDate(cal.getTime());
	current.setEndDate(cal.getTime());
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
	    return controller.ejbFacade.find(getKey(value));
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
	    return new MoodleRecord(valueElements[0], valueElements[1], valueElements[2]);
	}

	@Override
	public final String getAsString(FacesContext facesContext, UIComponent component, Object object) {
	    if (object == null) {
		return null;
	    }
	    if (object instanceof MoodleRecord) {
		final MoodleRecord record = (MoodleRecord) object;
		return record.getExternalUser() + "_" + record.getExternalCourseId() + "_" + record.getDomain();
	    } else {
		throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: "
			+ Reservation.class.getName());
	    }
	}
    }
}