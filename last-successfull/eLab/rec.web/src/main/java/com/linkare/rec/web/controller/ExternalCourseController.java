package com.linkare.rec.web.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.linkare.rec.web.model.moodle.ExternalCourse;
import com.linkare.rec.web.service.ExternalCourseService;
import com.linkare.rec.web.service.ExternalCourseServiceBean;
import com.linkare.rec.web.wsgen.moodle.UserRecord;

/**
 * 
 * @author Joao
 */
@ManagedBean(name = "externalCourseController")
@RequestScoped
public class ExternalCourseController extends AbstractController<String, ExternalCourse, ExternalCourseService> {

    private static final long serialVersionUID = 1L;

    private ExternalCourseService service;

    private UserRecord[] students;

    private UserRecord[] teachers;

    public final ExternalCourse getSelected() {
	if (getCurrent() == null) {
	    setCurrent(new ExternalCourse());
	}
	return getCurrent();
    }

    @Override
    protected ExternalCourseService getService() {
	if (service == null) {
	    service = new ExternalCourseServiceBean();
	}
	return service;
    }

    @Override
    public final String prepareCreate() {
	throw new UnsupportedOperationException("external.courses.prepareCreate.not.available");
    }

    @Override
    public final String create() {
	throw new UnsupportedOperationException("external.courses.create.not.available");
    }

    @Override
    public String prepareEdit() {
	throw new UnsupportedOperationException("external.courses.prepareEdit.not.available");
    }

    @Override
    public final String update() {
	throw new UnsupportedOperationException("external.courses.update.not.available");
    }

    protected void performDestroy() {
	throw new UnsupportedOperationException("external.courses.performDestroy.not.available");
    }

    @Override
    public String destroy() {
	throw new UnsupportedOperationException("external.courses.destroy.not.available");
    }

    public UserRecord[] getTeachers() {
	if (teachers == null) {
	    teachers = getService().getTeachers(getSelected() == null ? null : getSelected().getShortname());
	}
	return teachers;
    }

    public UserRecord[] getStudents() {
	if (students == null) {
	    students = getService().getStudents(getSelected() == null ? null : getSelected().getShortname());
	}
	return students;
    }

    @FacesConverter(value = "externalCourseConverter", forClass = ExternalCourse.class)
    public static class ExternalCourseConverter implements Converter {

	@Override
	public final Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
	    if (value == null || value.length() == 0) {
		return null;
	    }
	    ExternalCourseController controller = (ExternalCourseController) facesContext.getApplication().getELResolver()
											 .getValue(facesContext.getELContext(), null,
												   "externalCourseController");
	    return controller.getService().find(value);
	}

	@Override
	public final String getAsString(FacesContext facesContext, UIComponent component, Object object) {
	    if (object == null) {
		return null;
	    }
	    if (object instanceof ExternalCourse) {
		ExternalCourse o = (ExternalCourse) object;
		return o.id();
	    } else {
		throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: "
			+ ExternalCourse.class.getName());
	    }
	}
    }
}