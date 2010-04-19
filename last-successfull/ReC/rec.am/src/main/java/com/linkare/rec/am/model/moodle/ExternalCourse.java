package com.linkare.rec.am.model.moodle;

import com.linkare.commons.jpa.Identifiable;
import com.linkare.rec.am.wsgen.moodle.CourseRecord;

public class ExternalCourse implements Identifiable<String> {

    private static final long serialVersionUID = 1L;

    private CourseRecord delegate;

    public ExternalCourse() {
	super();
	this.delegate = new CourseRecord();
    }

    public ExternalCourse(final CourseRecord courseRecord) {
	this();
	this.delegate = courseRecord;
    }

    @Override
    public String getPk() {
	return delegate.getShortname();
    }

    public java.lang.String getError() {
	return delegate.getError();
    }

    public java.math.BigInteger getId() {
	return delegate.getId();
    }

    public java.math.BigInteger getCategory() {
	return delegate.getCategory();
    }

    public java.math.BigInteger getSortorder() {
	return delegate.getSortorder();
    }

    public java.lang.String getPassword() {
	return delegate.getPassword();
    }

    public java.lang.String getFullname() {
	return delegate.getFullname();
    }

    public java.lang.String getShortname() {
	return delegate.getShortname();
    }

    public java.lang.String getIdnumber() {
	return delegate.getIdnumber();
    }

    public java.lang.String getSummary() {
	return delegate.getSummary();
    }

    public java.lang.String getFormat() {
	return delegate.getFormat();
    }

    public java.math.BigInteger getShowgrades() {
	return delegate.getShowgrades();
    }

    public java.math.BigInteger getNewsitems() {
	return delegate.getNewsitems();
    }

    public java.lang.String getTeacher() {
	return delegate.getTeacher();
    }

    public java.lang.String getTeachers() {
	return delegate.getTeachers();
    }

    public java.lang.String getStudent() {
	return delegate.getStudent();
    }

    public java.lang.String getStudents() {
	return delegate.getStudents();
    }

    public java.math.BigInteger getGuest() {
	return delegate.getGuest();
    }

    public java.math.BigInteger getStartdate() {
	return delegate.getStartdate();
    }

    public java.math.BigInteger getEnrolperiod() {
	return delegate.getEnrolperiod();
    }

    public java.math.BigInteger getNumsections() {
	return delegate.getNumsections();
    }

    public java.math.BigInteger getMarker() {
	return delegate.getMarker();
    }

    public java.math.BigInteger getMaxbytes() {
	return delegate.getMaxbytes();
    }

    public java.math.BigInteger getVisible() {
	return delegate.getVisible();
    }

    public java.math.BigInteger getHiddensections() {
	return delegate.getHiddensections();
    }

    public java.math.BigInteger getGroupmode() {
	return delegate.getGroupmode();
    }

    public java.math.BigInteger getGroupmodeforce() {
	return delegate.getGroupmodeforce();
    }

    public java.lang.String getLang() {
	return delegate.getLang();
    }

    public java.lang.String getTheme() {
	return delegate.getTheme();
    }

    public java.lang.String getCost() {
	return delegate.getCost();
    }

    public java.math.BigInteger getTimecreated() {
	return delegate.getTimecreated();
    }

    public java.math.BigInteger getTimemodified() {
	return delegate.getTimemodified();
    }

    public java.math.BigInteger getMetacourse() {
	return delegate.getMetacourse();
    }

    public java.math.BigInteger getMyrole() {
	return delegate.getMyrole();
    }

    @Override
    public String toString() {
	return getShortname();
    }
}