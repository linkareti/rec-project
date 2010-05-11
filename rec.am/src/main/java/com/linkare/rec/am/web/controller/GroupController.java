package com.linkare.rec.am.web.controller;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.primefaces.model.DualListModel;

import com.linkare.commons.jpa.security.Group;
import com.linkare.commons.jpa.security.User;
import com.linkare.jsf.utils.JsfUtil;
import com.linkare.rec.am.service.GroupService;
import com.linkare.rec.am.service.GroupServiceLocal;
import com.linkare.rec.am.web.util.ConstantUtils;

@ManagedBean(name = "groupController")
@RequestScoped
public class GroupController extends AbstractController<Long, Group, GroupService> {

    private static final long serialVersionUID = 1L;

    private DualListModel<User> users;

    @EJB(beanInterface = GroupServiceLocal.class)
    private GroupService service;

    @Override
    protected GroupService getService() {
	return service;
    }

    @Override
    public Group getSelected() {
	if (getCurrent() == null) {
	    setCurrent(new Group());
	}
	return getCurrent();
    }

    @Override
    public final String prepareCreate() {
	setCurrent(new Group());
	return ConstantUtils.CREATE;
    }

    public List<User> getNonMembers() {
	return getService().getNonMembers(getCurrent());
    }

    @FacesConverter(value = "groupConverter", forClass = Group.class)
    public static class GroupControllerConverter implements Converter {

	@Override
	public final Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
	    if (value == null || value.length() == 0) {
		return null;
	    }
	    GroupController controller = (GroupController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null,
														  "groupController");
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
	    if (object instanceof Group) {
		Group o = (Group) object;
		return getStringKey(o.getIdInternal());
	    } else {
		throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: "
			+ Group.class.getName());
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
	JsfUtil.addSuccessMessage(ConstantUtils.BUNDLE, ConstantUtils.LABEL_INFO_KEY, ConstantUtils.INFO_ASSOCIATION_KEY);
	return prepareEdit();
    }
}