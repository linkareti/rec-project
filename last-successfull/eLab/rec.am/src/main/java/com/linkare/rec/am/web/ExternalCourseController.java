package com.linkare.rec.am.web;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.linkare.rec.am.model.ExternalCourseFacade;
import com.linkare.rec.am.model.Laboratory;
import com.linkare.rec.am.model.moodle.ExternalCourse;
import com.linkare.rec.am.web.controller.AbstractController;
import com.linkare.rec.am.wsgen.moodle.UserRecord;

/**
 * 
 * @author Joao
 */
@ManagedBean(name = "externalCourseController")
@RequestScoped
public class ExternalCourseController extends AbstractController<ExternalCourse, ExternalCourseFacade> {

    private ExternalCourseFacade facade;

    private UserRecord[] students;

    private UserRecord[] teachers;

    public ExternalCourseController() {
    }

    public final ExternalCourse getSelected() {
	if (current == null) {
	    current = new ExternalCourse();
	    selectedItemIndex = -1;
	}
	return current;
    }

    @Override
    protected ExternalCourseFacade getFacade() {
	if (facade == null) {
	    facade = new ExternalCourseFacade();
	}
	return facade;
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
    public String next() {
	throw new UnsupportedOperationException("external.courses.next.not.available");
    }

    @Override
    public String previous() {
	throw new UnsupportedOperationException("external.courses.previous.not.available");
    }

    @Override
    public String destroy() {
	throw new UnsupportedOperationException("external.courses.destroy.not.available");
    }

    @Override
    public String destroyAndView() {
	throw new UnsupportedOperationException("external.courses.destroyAndView.not.available");
    }

    public UserRecord[] getTeachers() {
	if (teachers == null) {
	    teachers = getFacade().getTeachers(getSelected() == null ? null : getSelected().getShortname());
	}
	return teachers;
    }

    public UserRecord[] getStudents() {
	if (students == null) {
	    students = getFacade().getStudents(getSelected() == null ? null : getSelected().getShortname());
	}
	return students;
    }

    @FacesConverter(value = "externalCourseConverter", forClass = ExternalCourse.class)
    public static class ExternalCourseControllerConverter implements Converter {

	@Override
	public final Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
	    if (value == null || value.length() == 0) {
		return null;
	    }
	    ExternalCourseController controller = (ExternalCourseController) facesContext.getApplication().getELResolver()
											 .getValue(facesContext.getELContext(), null,
												   "externalCourseController");
	    return controller.getFacade().find(getKey(value));
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
	    if (object instanceof ExternalCourse) {
		ExternalCourse o = (ExternalCourse) object;
		return getStringKey(o.getPk());
	    } else {
		throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: "
			+ Laboratory.class.getName());
	    }
	}
    }
}