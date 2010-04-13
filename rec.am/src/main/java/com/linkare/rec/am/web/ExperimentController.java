package com.linkare.rec.am.web;

import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.linkare.rec.am.model.Experiment;
import com.linkare.rec.am.model.ExperimentFacade;
import com.linkare.rec.am.web.controller.AbstractController;
import com.linkare.rec.am.web.util.JsfUtil;

@ManagedBean(name = "experimentController")
@RequestScoped
public class ExperimentController extends AbstractController<Experiment, ExperimentFacade> {

    @EJB
    private com.linkare.rec.am.model.ExperimentFacade ejbFacade;

    public ExperimentController() {
    }

    public final Experiment getSelected() {
	if (current == null) {
	    current = new Experiment();
	    selectedItemIndex = -1;
	}
	return current;
    }

    @Override
    protected ExperimentFacade getFacade() {
	return ejbFacade;
    }

    @Override
    public final String prepareCreate() {
	current = new Experiment();
	selectedItemIndex = -1;
	return CREATE;
    }

    @Override
    public final String create() {
	try {
	    getFacade().create(current);
	    JsfUtil.addSuccessMessage(ResourceBundle.getBundle(BUNDLE).getString("ExperimentCreated"));
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
	    JsfUtil.addSuccessMessage(ResourceBundle.getBundle(BUNDLE).getString("ExperimentUpdated"));
	    return VIEW;
	} catch (Exception e) {
	    JsfUtil.addErrorMessage(e, ResourceBundle.getBundle(BUNDLE).getString("PersistenceErrorOccured"));
	    return null;
	}
    }

    protected void performDestroy() {
	try {
	    getFacade().remove(current);
	    JsfUtil.addSuccessMessage(ResourceBundle.getBundle(BUNDLE).getString("ExperimentDeleted"));
	} catch (Exception e) {
	    JsfUtil.addErrorMessage(e, ResourceBundle.getBundle(BUNDLE).getString("PersistenceErrorOccured"));
	}
    }

    @FacesConverter(forClass = Experiment.class)
    public static class ExperimentControllerConverter implements Converter {

	@Override
	public final Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
	    if (value == null || value.length() == 0) {
		return null;
	    }
	    ExperimentController controller = (ExperimentController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null,
															    "experimentController");
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
	    if (object instanceof Experiment) {
		Experiment o = (Experiment) object;
		return getStringKey(o.getPk());
	    } else {
		throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: "
			+ Experiment.class.getName());
	    }
	}
    }
}
