package com.linkare.rec.am.web;

import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.SelectItem;

import com.linkare.rec.am.model.Reservation;
import com.linkare.rec.am.model.ReservationFacade;
import com.linkare.rec.am.web.controller.AbstractController;
import com.linkare.rec.am.web.util.JsfUtil;

@ManagedBean(name = "reservationController")
@RequestScoped
public class ReservationController extends AbstractController<Reservation, ReservationFacade> {

    private static final long serialVersionUID = 1L;

    @EJB
    private com.linkare.rec.am.model.ReservationFacade ejbFacade;

    private static final int MINUTE_STEP = 30;

    @Override
    public Reservation getCurrent() {
	if (current == null || current.getPk() == null) {
	    current = (Reservation) JsfUtil.getObjectFromRequestParameter("current", new ReservationConverter());
	}
	return current;
    }

    @Override
    public void setCurrent(Reservation current) {
	this.current = current;
    }

    public final Reservation getSelected() {
	if (current == null) {
	    current = new Reservation();
	    selectedItemIndex = -1;
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
	selectedItemIndex = -1;
	return CREATE;
    }

    @Override
    public final String create() {
	try {
	    processEndDateAndEndTimeSlot();
	    getFacade().create(current);
	    JsfUtil.addSuccessMessage(ResourceBundle.getBundle(BUNDLE).getString("ReservationCreated"));
	    return prepareCreate();
	} catch (Exception e) {
	    JsfUtil.addErrorMessage(e, ResourceBundle.getBundle(BUNDLE).getString("PersistenceErrorOccured"));
	    return null;
	}
    }

    public final void processEndDateAndEndTimeSlot() {
	String timeSlot = current.getStartTimeSlot();
	StringTokenizer st = new StringTokenizer(timeSlot, ":");
	Calendar cal = Calendar.getInstance();
	cal.setTime(current.getStartDate());
	cal.set(Calendar.HOUR, Integer.parseInt(st.nextToken()));
	cal.set(Calendar.MINUTE, Integer.parseInt(st.nextToken()));
	current.setStartDate(cal.getTime());
	current.setStartTimeSlot(timeSlot);
	if (cal.get(Calendar.MINUTE) == MINUTE_STEP) {
	    cal.roll(Calendar.HOUR, true);
	    cal.set(Calendar.MINUTE, 0);
	} else if (cal.get(Calendar.MINUTE) == 0) {
	    cal.set(Calendar.MINUTE, MINUTE_STEP);
	}

	current.setEndDate(cal.getTime());
	current.setEndTimeSlot(cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE));
    }

    @Override
    public final String update() {
	try {
	    processEndDateAndEndTimeSlot();
	    getFacade().edit(current);
	    JsfUtil.addSuccessMessage(ResourceBundle.getBundle(BUNDLE).getString("ReservationUpdated"));
	    return VIEW;
	} catch (Exception e) {
	    JsfUtil.addErrorMessage(e, ResourceBundle.getBundle(BUNDLE).getString("PersistenceErrorOccured"));
	    return null;
	}
    }

    @Override
    protected void performDestroy() {
	try {
	    getFacade().remove(current);
	    JsfUtil.addSuccessMessage(ResourceBundle.getBundle(BUNDLE).getString("ReservationDeleted"));
	} catch (Exception e) {
	    JsfUtil.addErrorMessage(e, ResourceBundle.getBundle(BUNDLE).getString("PersistenceErrorOccured"));
	}
    }

    public final SelectItem[] getTimeSlotSelectOne() {
	return JsfUtil.getTimeSlotItems();
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
		return getStringKey(o.getPk());
	    } else {
		throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: "
			+ Reservation.class.getName());
	    }
	}
    }
}
