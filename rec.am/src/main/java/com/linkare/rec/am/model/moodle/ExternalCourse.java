package com.linkare.rec.am.model.moodle;

import java.util.List;

import javax.inject.Named;

import com.linkare.commons.jpa.Deletable;
import com.linkare.commons.jpa.Identifiable;
import com.linkare.rec.am.web.moodle.MoodleClientHelper;
import com.linkare.rec.am.wsgen.moodle.CourseRecord;
import com.linkare.rec.am.wsgen.moodle.LoginReturn;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
@Named("externalCourse")
public class ExternalCourse implements Identifiable<String>, Deletable {

    private CourseRecord delegate;

    private List<ExternalUser> students;

    private List<ExternalUser> teachers;

    private String loginDomain;

    private LoginReturn loginReturn;

    public ExternalCourse() {
	super();
	this.delegate = new CourseRecord();
    }

    public ExternalCourse(final String loginDomain, final LoginReturn loginReturn, final CourseRecord courseRecord) {
	this();
	this.loginDomain = loginDomain;
	this.loginReturn = loginReturn;
	this.delegate = courseRecord;
    }

    @Override
    public String id() {
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

    public String getLoginDomain() {
	return loginDomain;
    }

    public LoginReturn getLoginReturn() {
	return loginReturn;
    }

    @Override
    public String toString() {
	return getShortname();
    }

    public List<ExternalUser> getStudents() {
	if (students == null && getShortname() != null) {
	    students = MoodleClientHelper.getStudents(this);
	}
	return students;
    }

    public List<ExternalUser> getTeachers() {
	if (teachers == null && getShortname() != null) {
	    teachers = MoodleClientHelper.getTeachers(this);
	}
	return teachers;
    }

    @Override
    public boolean delete() {
	return false;
    }
}