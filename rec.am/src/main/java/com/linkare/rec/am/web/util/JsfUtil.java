package com.linkare.rec.am.web.util;

import java.util.Iterator;
import java.util.List;

import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.convert.Converter;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public final class JsfUtil {

    private static final int MAX_ITEM_INDEX = 49;

    private JsfUtil() {
    }

    public static <T> SelectItem[] getSelectItems(List<T> entities, boolean selectOne) {
	int size = selectOne ? entities.size() + 1 : entities.size();
	SelectItem[] items = new SelectItem[size];
	int i = 0;
	if (selectOne) {
	    items[0] = new SelectItem(null, "");
	    i++;
	}
	for (final T x : entities) {
	    items[i++] = new SelectItem(x, x.toString());
	}
	return items;
    }

    public static void addErrorMessage(final String summary, final String detail) {
	FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail);
	FacesContext.getCurrentInstance().addMessage("errorInfo", facesMsg);
    }

    public static void addErrorMessage(String detail) {
	addErrorMessage(ConstantUtils.LABEL_ERROR, detail);
    }

    public static void addWarningMessage(final String summary, final String detail) {
	FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, summary, detail);
	FacesContext.getCurrentInstance().addMessage("warnInfo", facesMsg);
    }

    public static void addWarningMessage(String detail) {
	addWarningMessage(ConstantUtils.LABEL_WARN, detail);
    }

    public static void addSuccessMessage(String summary, String detail) {
	FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
	FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
    }

    public static void addSuccessMessage(String detail) {
	addSuccessMessage(ConstantUtils.LABEL_INFO, detail);
    }

    public static String getRequestParameter(String key) {
	return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(key);
    }

    public static Object getObjectFromRequestParameter(String requestParameterName, Converter converter, UIComponent component) {
	String theId = JsfUtil.getRequestParameter(requestParameterName);
	return converter.getAsObject(FacesContext.getCurrentInstance(), component, theId);
    }

    public static Object getObjectFromRequestParameter(String requestParameterName, Converter converter) {
	final UIComponent component = JsfUtil.findComponent(FacesContext.getCurrentInstance().getViewRoot(), requestParameterName);
	final String clientId = component == null ? null : component.getClientId();
	final String value = JsfUtil.getRequestParameter(clientId);
	return converter.getAsObject(FacesContext.getCurrentInstance(), component, value);
    }

    public static UIComponent findComponent(UIComponent c, String id) {
	if (id.equals(c.getId())) {
	    return c;
	}
	Iterator<UIComponent> kids = c.getFacetsAndChildren();
	while (kids.hasNext()) {
	    UIComponent found = findComponent(kids.next(), id);
	    if (found != null) {
		return found;
	    }
	}
	return null;
    }

    public static Object getSessionMapValue(String key) {
	return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(key);
    }

    public static void setSessionMapValue(String key, Object value) {
	FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(key, value);
    }

    public static FacesContext getFacesContext(HttpServletRequest request, HttpServletResponse response) {
	FacesContext facesContext = FacesContext.getCurrentInstance();
	if (facesContext == null) {

	    FacesContextFactory contextFactory = (FacesContextFactory) FactoryFinder.getFactory(FactoryFinder.FACES_CONTEXT_FACTORY);
	    LifecycleFactory lifecycleFactory = (LifecycleFactory) FactoryFinder.getFactory(FactoryFinder.LIFECYCLE_FACTORY);
	    Lifecycle lifecycle = lifecycleFactory.getLifecycle(LifecycleFactory.DEFAULT_LIFECYCLE);

	    facesContext = contextFactory.getFacesContext(request.getSession().getServletContext(), request, response, lifecycle);

	    // Set using our inner class
	    InnerFacesContext.setFacesContextAsCurrentInstance(facesContext);

	    // set a new viewRoot, otherwise context.getViewRoot returns null
	    UIViewRoot view = facesContext.getApplication().getViewHandler().createView(facesContext, "");
	    facesContext.setViewRoot(view);
	}
	return facesContext;
    }

    protected static Application getApplication(FacesContext facesContext) {
	return facesContext.getApplication();
    }

    public static Object getManagedBean(String beanName, FacesContext facesContext) {
	return getApplication(facesContext).getELResolver().getValue(facesContext.getELContext(), null, beanName);
    }

    public static HttpSession getSession() {
	return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    }

    // You need an inner class to be able to call FacesContext.setCurrentInstance
    // since it's a protected method
    private abstract static class InnerFacesContext extends FacesContext {

	protected static void setFacesContextAsCurrentInstance(FacesContext facesContext) {
	    FacesContext.setCurrentInstance(facesContext);
	}
    }
}