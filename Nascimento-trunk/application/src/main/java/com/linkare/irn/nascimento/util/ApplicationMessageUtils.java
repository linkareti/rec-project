package com.linkare.irn.nascimento.util;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 *
 */
public class ApplicationMessageUtils {

    private ApplicationMessageUtils() {
    }

    public static String getMessageValue(final String key, final Object... params) {
	final FacesContext facesContext = FacesContext.getCurrentInstance();
	final ResourceBundle bundle = facesContext.getApplication().getResourceBundle(facesContext, "messages");
	String text = null;
	try {
	    text = bundle.getString(key);
	} catch (final MissingResourceException e) {
	    text = key;
	}
	if (params != null) {
	    final MessageFormat mf = new MessageFormat(text);
	    text = mf.format(params).toString();
	}
	return text;
    }

    public static String getMessage(final String key, final Object... params) {
	return getMessageValue(key, params);
    }
}