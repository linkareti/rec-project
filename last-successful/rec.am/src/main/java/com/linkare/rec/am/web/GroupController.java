package com.linkare.rec.am.web;

import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

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
import com.linkare.rec.am.model.GroupFacade;
import com.linkare.rec.am.model.UserFacade;
import com.linkare.rec.am.web.controller.AbstractController;
import com.linkare.rec.am.web.util.ConstantUtils;
import com.linkare.rec.am.web.util.JsfUtil;

@ManagedBean(name = "groupController")
@RequestScoped
public class GroupController extends AbstractController<Long, Group, GroupFacade> {

    private static final long serialVersionUID = 1L;

    private DualListModel<User> users;

    @EJB
    private GroupFacade ejbFacade;

    @EJB
    private UserFacade userFacade;

    @Override
    protected GroupFacade getFacade() {
	return ejbFacade;
    }

    public Group getCurrent() {
	return current;
    }

    public void setCurrent(final Group current) {
	this.current = current;
    }

    @Override
    public Group getSelected() {
	if (current == null) {
	    current = new Group();
	}
	return current;
    }

    @Override
    public final String prepareCreate() {
	current = new Group();
	return ConstantUtils.CREATE;
    }

    public List<User> getNonMembers() {
	if (current == null) {
	    return Collections.<User> emptyList();
	}
	final List<User> result = userFacade.findAll();
	result.removeAll(current.getAllUsers());
	return result;
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
	    users = new DualListModel<User>(getNonMembers(), current.getAllUsers());
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
	getFacade().setUsersMembership(getSelected(), getUsers().getTarget());
	JsfUtil.addSuccessMessage(ResourceBundle.getBundle(ConstantUtils.BUNDLE).getString("info.association"));
	return prepareEdit();
    }
}