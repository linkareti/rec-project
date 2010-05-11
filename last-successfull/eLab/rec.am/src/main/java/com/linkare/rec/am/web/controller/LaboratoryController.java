package com.linkare.rec.am.web.controller;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.linkare.rec.am.model.Laboratory;
import com.linkare.rec.am.service.LaboratoryService;
import com.linkare.rec.am.service.LaboratoryServiceLocal;
import com.linkare.rec.am.web.util.ConstantUtils;

@ManagedBean(name = "laboratoryController")
@RequestScoped
public class LaboratoryController extends AbstractController<Long, Laboratory, LaboratoryService> {

    private static final long serialVersionUID = 1L;

    @EJB(beanInterface = LaboratoryServiceLocal.class)
    private LaboratoryService service;

    @Override
    public final Laboratory getSelected() {
	if (getCurrent() == null) {
	    setCurrent(new Laboratory());
	}
	return getCurrent();
    }

    @Override
    protected LaboratoryService getService() {
	return service;
    }

    @Override
    public final String prepareCreate() {
	setCurrent(new Laboratory());
	return ConstantUtils.CREATE;
    }

    @FacesConverter(value = "laboratoryConverter", forClass = Laboratory.class)
    public static class LaboratoryConverter implements Converter {

	@Override
	public final Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
	    if (value == null || value.length() == 0) {
		return null;
	    }
	    LaboratoryController controller = (LaboratoryController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null,
															    "laboratoryController");
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
	    if (object instanceof Laboratory) {
		Laboratory o = (Laboratory) object;
		return getStringKey(o.getIdInternal());
	    } else {
		throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: "
			+ Laboratory.class.getName());
	    }
	}
    }
}