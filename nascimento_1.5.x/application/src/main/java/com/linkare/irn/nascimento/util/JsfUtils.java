package com.linkare.irn.nascimento.util;

import java.util.Iterator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 *
 */
public class JsfUtils {

    private JsfUtils() {
    }

    public static FacesMessage createFacesMessage(final FacesMessage.Severity severity, final String summary, final String detail) {
	return new FacesMessage(severity, summary, detail);
    }

    public static void addMessage(final String clientId, final FacesMessage message) {
	final FacesContext facesContext = FacesContext.getCurrentInstance();
	facesContext.addMessage(clientId, message);
    }

    public static void addMessage(final FacesMessage.Severity severity, final String clientId, final String summary, final String detail) {
	addMessage(clientId, createFacesMessage(severity, summary, detail));
    }

    public static void addGlobalMessage(final FacesMessage facesMessage) {
	addMessage(null, facesMessage);
    }

    public static void addGlobalFatalMessage(final String detail) {
	addMessage(FacesMessage.SEVERITY_FATAL, null, ApplicationMessageUtils.getMessage("message.fatal"), detail);
    }

    public static void addGlobalErrorMessage(final String detail) {
	addMessage(FacesMessage.SEVERITY_ERROR, null, ApplicationMessageUtils.getMessage("message.error"), detail);
    }

    public static void addGlobalErrorMessage(final String summary, final String detail) {
	addMessage(FacesMessage.SEVERITY_ERROR, null, summary, detail);
    }

    public static void addGlobalWarnMessage(final String detail) {
	addMessage(FacesMessage.SEVERITY_WARN, null, ApplicationMessageUtils.getMessage("message.warn"), detail);
    }

    public static void addGlobalInfoMessage(final String detail) {
	addMessage(FacesMessage.SEVERITY_INFO, null, ApplicationMessageUtils.getMessage("message.info"), detail);
    }

    /**
     * 
     * @param c
     *            the component from which we should start the search
     * @param id
     *            the id of the component to search
     * @return the UI component that mathes the passed in id, child of the <code>c</code> component passed in as argument
     */
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

    public static UIComponent findChildComponent(final UIComponent toValidate, final String nameToFind) {
	if (toValidate.getClientId().endsWith(nameToFind)) {
	    return toValidate;
	}
	for (final UIComponent child : toValidate.getChildren()) {
	    final UIComponent foundChild = findChildComponent(child, nameToFind);
	    if (foundChild != null) {
		return foundChild;
	    }
	}
	return null;
    }

    public static UIComponent findChildComponent(final UIViewRoot viewRoot, final String nameToFind) {
	for (final UIComponent child : viewRoot.getChildren()) {
	    final UIComponent foundChild = findChildComponent(child, nameToFind);
	    if (foundChild != null) {
		return foundChild;
	    }
	}
	return null;
    }

    /**
     * 
     * @param key
     *            the parameter key to look for in the request parameter map.
     * @return the request parameter for the passed in <code>key</code> from the request parameter map.
     */
    public static String getRequestParameter(String key) {
	return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(key);
    }

    /**
     * 
     * @param requestParameterName
     *            the name of the parameter from the request to look for
     * @param converter
     *            the JSF converter to be used for the object to be converted
     * @param component
     *            the component from where the object is to be fetched
     * @return the object from the request whose name matches the <code>requestParameterName</code>.
     */
    public static Object getObjectFromRequestParameter(String requestParameterName, Converter converter, UIComponent component) {
	String theId = JsfUtils.getRequestParameter(requestParameterName);
	return converter.getAsObject(FacesContext.getCurrentInstance(), component, theId);
    }

    /**
     * 
     * @param requestParameterName
     *            the name of the request parameter
     * @param converter
     *            the converter to use for the object conversion
     * @return the object from the request parameter
     */
    public static Object getObjectFromRequestParameter(String requestParameterName, Converter converter) {
	final UIComponent component = JsfUtils.findComponent(FacesContext.getCurrentInstance().getViewRoot(), requestParameterName);
	final String clientId = component == null ? null : component.getClientId();
	final String value = JsfUtils.getRequestParameter(clientId);
	return converter.getAsObject(FacesContext.getCurrentInstance(), component, value);
    }
}