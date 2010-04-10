package com.linkare.rec.am.web;

import com.linkare.rec.am.model.UserPrincipal;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

/**
 *
 * @author Joao
 */
public class UserPrincipalConverter implements Converter {

    @Override
    public final Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        if (value == null || value.length() == 0) {
            return null;
        }
        UserPrincipalController controller = (UserPrincipalController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "userPrincipalController");
        return controller.getFacade().find(getKey(value));
    }

    final java.lang.String getKey(String value) {
        java.lang.String key;
        key = value;
        return key;
    }

    final String getStringKey(java.lang.String value) {
        StringBuffer sb = new StringBuffer();
        sb.append(value);
        return sb.toString();
    }

    @Override
    public final String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof UserPrincipal) {
            UserPrincipal o = (UserPrincipal) object;
            return getStringKey(o.getName());
        } else {
            throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + UserPrincipal.class.getName());
        }
    }
}
