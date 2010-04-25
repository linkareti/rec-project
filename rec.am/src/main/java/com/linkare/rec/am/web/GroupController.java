package com.linkare.rec.am.web;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

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
import com.linkare.rec.am.web.util.JsfUtil;

@ManagedBean(name = "groupController")
@RequestScoped
public class GroupController extends AbstractController<Group, GroupFacade> {

    private static final long serialVersionUID = 1L;

    private static Logger logger = Logger.getLogger("GroupController");

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

	    StringWriter sw = new StringWriter();
	    e.printStackTrace(new PrintWriter(sw));
	    String stacktrace = sw.toString();
	    getLogger().severe("Stack Trace: \n" + stacktrace);

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

    /**
     * @return the logger
     */
    public static final Logger getLogger() {
	return logger;
    }

    public String setUsersMembership() {
	getFacade().setUsersMembership(getSelected(), getUsers().getTarget());
	JsfUtil.addSuccessMessage(ResourceBundle.getBundle(BUNDLE).getString("info.association"));
	return prepareEdit();
    }
}