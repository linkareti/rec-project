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

import com.linkare.rec.am.model.UserGroup;
import com.linkare.rec.am.model.UserGroupFacade;
import com.linkare.rec.am.model.UserPrincipal;
import com.linkare.rec.am.model.UserPrincipalFacade;
import com.linkare.rec.am.web.controller.AbstractController;
import com.linkare.rec.am.web.util.JsfUtil;

@ManagedBean(name = "userGroupController")
@RequestScoped
public class UserGroupController extends AbstractController<UserGroup, UserGroupFacade> {

    private static Logger logger = Logger.getLogger("UserGroupController");

    @EJB
    private UserGroupFacade ejbFacade;

    @EJB
    private UserPrincipalFacade userFacade;

    public UserGroupController() {
    }

    @Override
    protected UserGroupFacade getFacade() {
	return ejbFacade;
    }

    public void setCurrent(UserGroup current) {
	this.current = current;
    }

    public UserGroup getCurrent() {
	if (current == null || current.getPk() == null) {
	    current = (UserGroup) JsfUtil.getObjectFromRequestParameter("current", new UserGroupControllerConverter());
	}
	return current;
    }

    @Override
    public UserGroup getSelected() {
	if (getCurrent() == null) {
	    current = new UserGroup();
	    selectedItemIndex = -1;
	}
	return current;
    }

    @Override
    public final String prepareCreate() {
	current = new UserGroup();
	selectedItemIndex = -1;
	return CREATE;
    }

    @Override
    public final String create() {
	try {
	    getFacade().create(current);
	    JsfUtil.addSuccessMessage(ResourceBundle.getBundle(BUNDLE).getString("UserGroupCreated"));
	    return prepareCreate();
	} catch (Exception e) {
	    JsfUtil.addErrorMessage(e, ResourceBundle.getBundle(BUNDLE).getString("PersistenceErrorOccured"));

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
	    JsfUtil.addSuccessMessage(ResourceBundle.getBundle(BUNDLE).getString("UserGroupUpdated"));
	    return VIEW;
	} catch (Exception e) {
	    JsfUtil.addErrorMessage(e, ResourceBundle.getBundle(BUNDLE).getString("PersistenceErrorOccured"));
	    return null;
	}
    }

    @Override
    protected void performDestroy() {
	try {
	    getFacade().remove(current);
	    JsfUtil.addSuccessMessage(ResourceBundle.getBundle(BUNDLE).getString("UserGroupDeleted"));
	} catch (Exception e) {
	    JsfUtil.addErrorMessage(e, ResourceBundle.getBundle(BUNDLE).getString("PersistenceErrorOccured"));
	}
    }

    public String addUser() {
	final String userId = JsfUtil.getRequestParameter("userId");
	getFacade().addUser(getSelected(), Long.valueOf(userId));
	return null;
    }

    public String removeUser() {
	final String userId = JsfUtil.getRequestParameter("userId");
	getFacade().removeUser(getSelected(), Long.valueOf(userId));
	return null;
    }

    public List<UserPrincipal> getNonMembers() {
	if (getCurrent() == null) {
	    return Collections.<UserPrincipal> emptyList();
	}
	final List<UserPrincipal> result = userFacade.findAll();
	result.removeAll(getCurrent().getMembers());
	return result;
    }

    @FacesConverter(value = "userGroupConverter", forClass = UserGroup.class)
    public static class UserGroupControllerConverter implements Converter {

	@Override
	public final Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
	    if (value == null || value.length() == 0) {
		return null;
	    }
	    UserGroupController controller = (UserGroupController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null,
															  "userGroupController");
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
	    if (object instanceof UserGroup) {
		UserGroup o = (UserGroup) object;
		return getStringKey(o.getPk());
	    } else {
		throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: "
			+ UserGroup.class.getName());
	    }
	}
    }

    /**
     * @return the logger
     */
    public static final Logger getLogger() {
	return logger;
    }
}