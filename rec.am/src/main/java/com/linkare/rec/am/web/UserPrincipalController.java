package com.linkare.rec.am.web;

import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.linkare.rec.am.model.UserPrincipal;
import com.linkare.rec.am.model.UserPrincipalFacade;
import com.linkare.rec.am.web.controller.AbstractController;
import com.linkare.rec.am.web.util.JsfUtil;

@ManagedBean(name = "userPrincipalController")
@RequestScoped
public class UserPrincipalController extends AbstractController<UserPrincipal, UserPrincipalFacade> {

    private static final long serialVersionUID = 1L;

    @EJB
    private UserPrincipalFacade ejbFacade;

    public UserPrincipal getCurrent() {
	if (current == null || current.getPk() == null) {
	    current = (UserPrincipal) JsfUtil.getObjectFromRequestParameter("current", new UserPrincipalConverter());
	}
	return current;
    }

    public void setCurrent(UserPrincipal current) {
	this.current = current;
    }

    @Override
    public final UserPrincipal getSelected() {
	if (current == null) {
	    current = new UserPrincipal();
	    selectedItemIndex = -1;
	}
	return current;
    }

    @Override
    protected final UserPrincipalFacade getFacade() {
	return ejbFacade;
    }

    @Override
    public final String prepareCreate() {
	current = new UserPrincipal();
	selectedItemIndex = -1;
	return CREATE;
    }

    @Override
    public final String create() {
	try {
	    getFacade().create(current);
	    JsfUtil.addSuccessMessage(ResourceBundle.getBundle(BUNDLE).getString("info.create"));
	    return prepareCreate();
	} catch (Exception e) {
	    JsfUtil.addErrorMessage(e, ResourceBundle.getBundle(BUNDLE).getString("error.persistence"));
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

    @FacesConverter(value = "userPrincipalConverter", forClass = UserPrincipal.class)
    public static class UserPrincipalConverter implements Converter {

	@Override
	public final Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
	    if (value == null || value.length() == 0) {
		return null;
	    }
	    UserPrincipalController controller = (UserPrincipalController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(),
																  null,
																  "userPrincipalController");
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
	    if (object instanceof UserPrincipal) {
		UserPrincipal o = (UserPrincipal) object;
		return getStringKey(o.getPk());
	    } else {
		throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: "
			+ UserPrincipal.class.getName());
	    }
	}
    }
}