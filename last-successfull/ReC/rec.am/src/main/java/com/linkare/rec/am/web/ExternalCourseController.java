package com.linkare.rec.am.web;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.linkare.rec.am.model.ExternalCourseFacade;
import com.linkare.rec.am.web.controller.AbstractController;
import com.linkare.rec.am.wsgen.moodle.CourseRecord;
import com.linkare.rec.am.wsgen.moodle.UserRecord;

/**
 * 
 * @author Joao
 */
@ManagedBean(name = "externalCourseController")
@RequestScoped
public class ExternalCourseController extends AbstractController<CourseRecord, ExternalCourseFacade> {

    private ExternalCourseFacade facade;

    private UserRecord[] students;

    private UserRecord[] teachers;

    public ExternalCourseController() {
    }

    public final CourseRecord getSelected() {
	if (current == null) {
	    current = new CourseRecord();
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
}