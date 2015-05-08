package com.linkare.rec.web.controller;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.primefaces.model.DualListModel;

import com.linkare.commons.jpa.security.Role;
import com.linkare.commons.jpa.security.User;
import com.linkare.jsf.utils.JsfUtil;
import com.linkare.rec.web.service.RoleService;
import com.linkare.rec.web.service.RoleServiceLocal;
import com.linkare.rec.web.util.ConstantUtils;

@ManagedBean(name = "roleController")
@RequestScoped
public class RoleController extends AbstractController<Long, Role, RoleService> {

    private static final long serialVersionUID = 1L;

    private DualListModel<User> users;

    @EJB(beanInterface = RoleServiceLocal.class)
    private RoleService service;

    @Override
    protected RoleService getService() {
	return service;
    }

    @Override
    public Role getSelected() {
	if (getCurrent() == null) {
	    setCurrent(new Role());
	}
	return getCurrent();
    }

    @Override
    public final String prepareCreate() {
	setCurrent(new Role());
	return ConstantUtils.CREATE;
    }

    public List<User> getNonMembers() {
	return getService().getNonMembers(getCurrent());
    }

    @FacesConverter(value = "roleConverter", forClass = Role.class)
    public static class RoleControllerConverter implements Converter {

	@Override
	public final Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
	    if (value == null || value.length() == 0) {
		return null;
	    }
	    RoleController controller = (RoleController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null,
														"roleController");
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
	    if (object instanceof Role) {
		Role o = (Role) object;
		return getStringKey(o.getIdInternal());
	    } else {
		throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: "
			+ Role.class.getName());
	    }
	}
    }

    /**
     * @return the users
     */
    public DualListModel<User> getUsers() {
	if (users == null) {
	    users = new DualListModel<User>(getNonMembers(), getCurrent().getAllUsers());
	}
	return users;
    }

    /**
     * @param users
     *            the users to set
     */
    public void setUsers(DualListModel<User> users) {
	this.users = users;
    }

    public String setUsersMembership() {
	getService().setUsersMembership(getSelected(), getUsers().getTarget());
	JsfUtil.addGlobalSuccessMessage(ConstantUtils.BUNDLE, ConstantUtils.LABEL_INFO_KEY, ConstantUtils.INFO_ASSOCIATION_KEY);
	return prepareEdit();
    }
}