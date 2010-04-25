package com.linkare.rec.am.web;

import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.linkare.commons.jpa.exceptions.DomainException;
import com.linkare.commons.jpa.security.User;
import com.linkare.rec.am.model.UserFacade;
import com.linkare.rec.am.web.controller.AbstractController;
import com.linkare.rec.am.web.util.JsfUtil;

@ManagedBean(name = "userController")
@RequestScoped
public class UserController extends AbstractController<User, UserFacade> {

    private static final long serialVersionUID = 1L;

    @EJB
    private UserFacade ejbFacade;

    @Override
    public final User getSelected() {
	if (current == null) {
	    current = new User();
	}
	return current;
    }

    @Override
    protected final UserFacade getFacade() {
	return ejbFacade;
    }

    @Override
    public final String prepareCreate() {
	current = new User();
	return CREATE;
    }

    @Override
    public final String create() {
	try {
	    getFacade().create(current);
	    JsfUtil.addSuccessMessage(ResourceBundle.getBundle(BUNDLE).getString("info.create"));
	    return prepareCreate();
	} catch (Exception e) {
	    if (e.getCause() instanceof DomainException) {
		JsfUtil.addErrorMessage(e, ResourceBundle.getBundle(BUNDLE).getString(e.getCause().getMessage()));
	    } else {
		JsfUtil.addErrorMessage(e, ResourceBundle.getBundle(BUNDLE).getString("error.persistence"));
	    }
	    return null;

	}
    }

    @Override
    public final String update() {
	try {
	    getFacade().edit(current);
	    JsfUtil.addSuccessMessage(ResourceBundle.getBundle(BUNDLE).getString("info.update"));
	    return VIEW;
	} catch (Exception e) {
	    JsfUtil.addErrorMessage(e, ResourceBundle.getBundle(BUNDLE).getString("error.persistence"));
	    return null;
	}
    }

    @Override
    protected void performDestroy() {
	try {
	    getFacade().remove(current);
	    JsfUtil.addSuccessMessage(ResourceBundle.getBundle(BUNDLE).getString("info.remove"));
	} catch (Exception e) {
	    JsfUtil.addErrorMessage(e, ResourceBundle.getBundle(BUNDLE).getString("error.persistence"));
	}
    }

    @FacesConverter(value = "userConverter", forClass = User.class)
    public static class UserConverter implements Converter {

	@Override
	public final Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
	    if (value == null || value.length() == 0) {
		return null;
	    }
	    UserController controller = (UserController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null,
														"userController");
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
	    if (object instanceof User) {
		User o = (User) object;
		return getStringKey(o.getIdInternal());
	    } else {
		throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: "
			+ User.class.getName());
	    }
	}
    }
}