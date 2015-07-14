/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.linkare.rec.web.controller;

import com.linkare.rec.web.model.BadWord;
import com.linkare.rec.web.service.BadWordService;
import com.linkare.rec.web.service.BadWordServiceLocal;
import com.linkare.rec.web.util.ConstantUtils;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Gedsimon Pereira - Linkare TI
 */
@ManagedBean(name = "badWordController")
@RequestScoped
public class BadWordController extends AbstractController<Long, BadWord, BadWordService> {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4912332656715840492L;
	@EJB(beanInterface = BadWordServiceLocal.class)
    private BadWordService service;

    @Override
    public final BadWord getSelected() {
        if (getCurrent() == null) {
            setCurrent(new BadWord());
        }
        return getCurrent();
    }
    @Override
    protected BadWordService getService() {
        return service;
    }
    @Override
    public final String prepareCreate() {
        setCurrent(new BadWord());
        return ConstantUtils.CREATE;
    }
    
    @FacesConverter(value = "badWordConverter", forClass = BadWord.class)
    public static class LaboratoryConverter implements Converter {

        @Override
        public final Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            BadWordController controller = (BadWordController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null,
                    "badWordController");
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
            if (object instanceof BadWord) {
                BadWord o = (BadWord) object;
                return getStringKey(o.getIdInternal());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: "
                        + BadWord.class.getName());
            }
        }
    }

}
