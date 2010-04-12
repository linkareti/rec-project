package com.linkare.rec.am.web;

import com.linkare.rec.am.model.Laboratory;
import com.linkare.rec.am.web.controller.AbstractController;
import com.linkare.rec.am.web.util.JsfUtil;
import com.linkare.rec.am.model.LaboratoryFacade;

import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@ManagedBean(name = "laboratoryController")
@RequestScoped
public class LaboratoryController extends AbstractController<Laboratory, LaboratoryFacade> {

    @EJB
    private com.linkare.rec.am.model.LaboratoryFacade ejbFacade;

    public LaboratoryController() {
    }

    @Override
    public final Laboratory getSelected() {
        if (current == null) {
            current = new Laboratory();
            selectedItemIndex = -1;
        }
        return current;
    }

    @Override
    protected LaboratoryFacade getFacade() {
        return ejbFacade;
    }

    @Override
    public final String prepareCreate() {
        current = new Laboratory();
        selectedItemIndex = -1;
        return CREATE;
    }

    @Override
    public final String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle(BUNDLE).getString("LaboratoryCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle(BUNDLE).getString("PersistenceErrorOccured"));
            return null;
        }
    }

    @Override
    public final String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle(BUNDLE).getString("LaboratoryUpdated"));
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle(BUNDLE).getString("LaboratoryDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle(BUNDLE).getString("PersistenceErrorOccured"));
        }
    }

    @FacesConverter(forClass = Laboratory.class)
    public static class LaboratoryControllerConverter implements Converter {

        @Override
        public final Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            LaboratoryController controller = (LaboratoryController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "laboratoryController");
            return controller.ejbFacade.find(getKey(value));
        }

        private String getKey(String value) {
            return value;
        }

        private String getStringKey(String value) {
            return value;
        }

        @Override
        public final String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Laboratory) {
                Laboratory o = (Laboratory) object;
                return getStringKey(o.getName());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Laboratory.class.getName());
            }
        }
    }
}
