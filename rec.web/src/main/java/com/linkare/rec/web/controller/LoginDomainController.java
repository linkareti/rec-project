package com.linkare.rec.web.controller;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.linkare.rec.web.model.LoginDomain;
import com.linkare.rec.web.service.LoginDomainService;
import com.linkare.rec.web.service.LoginDomainServiceLocal;
import com.linkare.rec.web.util.ConstantUtils;

@ManagedBean(name = "loginDomainController")
@RequestScoped
public class LoginDomainController extends AbstractController<Long, LoginDomain, LoginDomainService> {

    @EJB(beanInterface = LoginDomainServiceLocal.class)
    private LoginDomainService service;

    @Override
    public LoginDomain getSelected() {
	if (getCurrent() == null) {
	    setCurrent(new LoginDomain());
	}
	return getCurrent();
    }

    @Override
    protected LoginDomainService getService() {
	return service;
    }

    @Override
    public String prepareCreate() {
	setCurrent(new LoginDomain());
	return ConstantUtils.CREATE;
    }

    @FacesConverter(value = "loginDomainConverter", forClass = LoginDomain.class)
    public static class LoginDomainConverter implements Converter {

	@Override
	public final Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
	    if (value == null || value.length() == 0) {
		return null;
	    }
	    LoginDomainController controller = (LoginDomainController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(),
															      null, "loginDomainController");
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
	    if (object instanceof LoginDomain) {
		LoginDomain o = (LoginDomain) object;
		return getStringKey(o.getIdInternal());
	    } else {
		throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: "
			+ LoginDomain.class.getName());
	    }
	}
    }
}