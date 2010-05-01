package com.linkare.rec.am.web;

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
import com.linkare.rec.am.web.util.ConstantUtils;

@ManagedBean(name = "experimentController")
@RequestScoped
public class ExperimentController extends AbstractController<Long, Experiment, ExperimentFacade> {

    private static final long serialVersionUID = 1L;

    @EJB
    private ExperimentFacade ejbFacade;

    public final Experiment getSelected() {
	if (current == null) {
	    current = new Experiment();
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
	return ConstantUtils.CREATE;
    }

    @FacesConverter(value = "experimentConverter", forClass = Experiment.class)
    public static class ExperimentConverter implements Converter {

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
		return getStringKey(o.getIdInternal());
	    } else {
		throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: "
			+ Experiment.class.getName());
	    }
	}
    }
}
