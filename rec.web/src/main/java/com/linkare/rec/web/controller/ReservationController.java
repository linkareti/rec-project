package com.linkare.rec.web.controller;

import java.util.Date;

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
import com.linkare.rec.web.aop.ReCWebExceptionHandler;
import com.linkare.rec.web.aop.ExceptionHandle;
import com.linkare.rec.web.aop.ExceptionHandleCase;
import com.linkare.rec.web.model.Reservation;
import com.linkare.rec.web.service.ReservationService;
import com.linkare.rec.web.service.ReservationServiceLocal;
import com.linkare.rec.web.service.UserService;
import com.linkare.rec.web.service.UserServiceLocal;
import com.linkare.rec.web.auth.UserView;
import com.linkare.rec.web.moodle.SessionHelper;
import com.linkare.rec.web.util.ConstantUtils;

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
	if (getCurrent() != null && getCurrent().getIdInternal() != null) {
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

    @Override
    public String create() {
	final String result = super.create();
	refreshUserView();
	return result;
    }

    @Override
    public String update() {
	final String result = super.update();
	refreshUserView();
	return result;
    }

    @Override
    public String destroy() {
	final String result = super.destroy();
	refreshUserView();
	return result;
    }

    private void refreshUserView() {
	final UserView userView = SessionHelper.getUserView();
	userView.setReservations(getService().findReservationsFor(new Date(), null, userView));
	SessionHelper.setUserView(userView);
    }

    public void prepareUpdateEvent(ActionEvent actionEvent) {
	final Long reservationId = Long.valueOf(JsfUtil.getRequestParameter("reservationId"));
	setCurrent(service.getReservationDetails(reservationId));
    }

    @ExceptionHandle(@ExceptionHandleCase(exceptionHandler = ReCWebExceptionHandler.class))
    public String addUser() {
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
	return null;
    }

    @ExceptionHandle(@ExceptionHandleCase(exceptionHandler = ReCWebExceptionHandler.class))
    public String removeUser() {
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