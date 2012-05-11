package com.linkare.rec.am.web.controller;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.SelectItem;

import com.linkare.jsf.utils.JsfUtil;
import com.linkare.rec.am.model.Experiment;
import com.linkare.rec.am.service.ExperimentService;
import com.linkare.rec.am.service.ExperimentServiceLocal;
import com.linkare.rec.am.web.util.ConstantUtils;

@ManagedBean(name = "experimentController")
@RequestScoped
public class ExperimentController extends AbstractController<Long, Experiment, ExperimentService> {

    private static final long serialVersionUID = 1L;

    @EJB(beanInterface = ExperimentServiceLocal.class)
    private ExperimentService service;

    public final Experiment getSelected() {
	if (getCurrent() == null) {
	    setCurrent(new Experiment());
	}
	return getCurrent();
    }

    @Override
    protected ExperimentService getService() {
	return service;
    }

    @Override
    public final String prepareCreate() {
	setCurrent(new Experiment());
	return ConstantUtils.CREATE;
    }

    public SelectItem[] getActiveItemsAvailableSelectOne() {
	return JsfUtil.getSelectItems(getService().findAllActiveExperiments(), true);
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