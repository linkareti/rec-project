package com.linkare.rec.am.web.controller;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.linkare.commons.jpa.security.User;
import com.linkare.rec.am.service.UserService;
import com.linkare.rec.am.service.UserServiceLocal;
import com.linkare.rec.am.web.util.ConstantUtils;

@ManagedBean(name = "userController")
@RequestScoped
public class UserController extends AbstractController<Long, User, UserService> {

    private static final long serialVersionUID = 1L;

    @EJB(beanInterface = UserServiceLocal.class)
    private UserService service;

    @Override
    public final User getSelected() {
	if (getCurrent() == null) {
	    setCurrent(new User());
	}
	return getCurrent();
    }

    @Override
    protected final UserService getService() {
	return service;
    }

    @Override
    public final String prepareCreate() {
	setCurrent(new User());
	return ConstantUtils.CREATE;
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