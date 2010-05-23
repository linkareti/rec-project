package com.linkare.rec.am.web.controller;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.event.ActionEvent;

import com.linkare.commons.jpa.exceptions.DomainException;
import com.linkare.commons.jpa.security.User;
import com.linkare.commons.utils.StringUtils;
import com.linkare.jsf.utils.JsfUtil;
import com.linkare.rec.am.model.Reservation;
import com.linkare.rec.am.service.ReservationService;
import com.linkare.rec.am.service.ReservationServiceLocal;
import com.linkare.rec.am.service.UserService;
import com.linkare.rec.am.service.UserServiceLocal;
import com.linkare.rec.am.web.util.ConstantUtils;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
@ManagedBean(name = "reservationController")
@RequestScoped
public class ReservationController extends AbstractController<Long, Reservation, ReservationService> {

    private static final String CONTEXT_USERNAME_INPUT = "context:username_input";

    private static final long serialVersionUID = 1L;

    @EJB(beanInterface = ReservationServiceLocal.class)
    private ReservationService service;

    @EJB(beanInterface = UserServiceLocal.class)
    private UserService userService;

    public final Reservation getSelected() {
	if (getCurrent() == null) {
	    setCurrent(new Reservation());
	}
	return getCurrent();
    }

    @Override
    protected ReservationService getService() {
	return service;
    }

    public Reservation getCurrentDetails() {
	if (getCurrent() != null) {
	    return service.getReservationDetails(getCurrent().getIdInternal());
	}
	return null;
    }

    public void setCurrentDetails(final Reservation reservation) {
	setCurrent(reservation);
    }

    @Override
    public final String prepareCreate() {
	setCurrent(new Reservation());
	return ConstantUtils.CREATE;
    }

    public void prepareUpdateEvent(ActionEvent actionEvent) {
	final Long reservationId = Long.valueOf(JsfUtil.getRequestParameter("reservationId"));
	setCurrent(service.getReservationDetails(reservationId));
    }

    public String addUser() {
	try {
	    String username = JsfUtil.getRequestParameter(CONTEXT_USERNAME_INPUT);
	    if (StringUtils.isBlank(username)) {
		JsfUtil.addGlobalErrorMessage(ConstantUtils.BUNDLE, ConstantUtils.LABEL_ERROR_KEY, ConstantUtils.ERROR_USERNAME_CANNOT_BE_EMPTY_KEY);
		return null;
	    }
	    User user = userService.findByUsername(username);
	    if (user == null) {
		user = new User(username);
		userService.create(user);
	    }
	    service.addUser(getCurrent(), user);
	    JsfUtil.addGlobalSuccessMessage(ConstantUtils.BUNDLE, ConstantUtils.LABEL_INFO_KEY, ConstantUtils.INFO_ASSOCIATION_KEY);
	} catch (Exception e) {
	    if (e.getCause() instanceof DomainException) {
		JsfUtil.addGlobalErrorMessage(ConstantUtils.BUNDLE, ConstantUtils.LABEL_ERROR_KEY, e.getCause().getMessage());
	    } else {
		JsfUtil.addGlobalErrorMessage(ConstantUtils.BUNDLE, ConstantUtils.LABEL_ERROR_KEY, ConstantUtils.ERROR_PERSISTENCE_KEY);
	    }
	}
	return null;
    }

    public String removeUser() {
	try {
	    String username = JsfUtil.getRequestParameter(CONTEXT_USERNAME_INPUT);
	    if (StringUtils.isBlank(username)) {
		JsfUtil.addGlobalErrorMessage(ConstantUtils.BUNDLE, ConstantUtils.LABEL_ERROR_KEY, ConstantUtils.ERROR_USERNAME_CANNOT_BE_EMPTY_KEY);
		return null;
	    }
	    User user = userService.findByUsername(username);
	    if (user != null) {
		service.removeUser(getCurrent(), user);
		JsfUtil.addGlobalSuccessMessage(ConstantUtils.BUNDLE, ConstantUtils.LABEL_INFO_KEY, ConstantUtils.INFO_DISSOCIATION_KEY);
	    }
	} catch (Exception e) {
	    if (e.getCause() instanceof DomainException) {
		JsfUtil.addGlobalErrorMessage(ConstantUtils.BUNDLE, ConstantUtils.LABEL_ERROR_KEY, e.getCause().getMessage());
	    } else {
		JsfUtil.addGlobalErrorMessage(ConstantUtils.BUNDLE, ConstantUtils.LABEL_ERROR_KEY, ConstantUtils.ERROR_PERSISTENCE_KEY);
	    }
	}
	return null;
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
}