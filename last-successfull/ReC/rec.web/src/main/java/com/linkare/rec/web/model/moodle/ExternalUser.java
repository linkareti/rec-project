package com.linkare.rec.web.model.moodle;

import com.linkare.rec.web.wsgen.moodle.UserRecord;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
public class ExternalUser extends UserRecord {

    private static final long serialVersionUID = -3676998527874345655L;

    private UserRecord delegate;

    private String loginDomain;

    public ExternalUser() {
	super();
	this.delegate = new UserRecord();
    }

    public ExternalUser(final String loginDomain, final UserRecord userRecord) {
	this();
	this.loginDomain = loginDomain;
	this.delegate = userRecord;
    }

    @Override
    public java.lang.String getError() {
	return delegate.getError();
    }

    @Override
    public java.math.BigInteger getId() {
	return delegate.getId();
    }

    @Override
    public java.lang.String getAuth() {
	return delegate.getAuth();
    }

    @Override
    public java.math.BigInteger getConfirmed() {
	return delegate.getConfirmed();
    }

    @Override
    public java.math.BigInteger getPolicyagreed() {
	return delegate.getPolicyagreed();
    }

    @Override
    public java.math.BigInteger getDeleted() {
	return delegate.getDeleted();
    }

    @Override
    public java.lang.String getUsername() {
	return delegate.getUsername();
    }

    @Override
    public java.lang.String getIdnumber() {
	return delegate.getIdnumber();
    }

    @Override
    public java.lang.String getFirstname() {
	return delegate.getFirstname();
    }

    @Override
    public java.lang.String getLastname() {
	return delegate.getLastname();
    }

    public java.lang.String getEmail() {
	return delegate.getEmail();
    }

    @Override
    public java.lang.String getIcq() {
	return delegate.getIcq();
    }

    @Override
    public java.math.BigInteger getEmailstop() {
	return delegate.getEmailstop();
    }

    @Override
    public java.lang.String getSkype() {
	return delegate.getSkype();
    }

    @Override
    public java.lang.String getYahoo() {
	return delegate.getYahoo();
    }

    @Override
    public java.lang.String getAim() {
	return delegate.getAim();
    }

    @Override
    public java.lang.String getMsn() {
	return delegate.getMsn();
    }

    @Override
    public java.lang.String getPhone1() {
	return delegate.getPhone1();
    }

    @Override
    public java.lang.String getPhone2() {
	return delegate.getPhone2();
    }

    @Override
    public java.lang.String getInstitution() {
	return delegate.getInstitution();
    }

    @Override
    public java.lang.String getDepartment() {
	return delegate.getDepartment();
    }

    @Override
    public java.lang.String getAddress() {
	return delegate.getAddress();
    }

    public java.lang.String getCity() {
	return delegate.getCity();
    }

    public java.lang.String getCountry() {
	return delegate.getCountry();
    }

    @Override
    public java.lang.String getLang() {
	return delegate.getLang();
    }

    @Override
    public java.math.BigInteger getTimezone() {
	return delegate.getTimezone();
    }

    @Override
    public java.math.BigInteger getMnethostid() {
	return delegate.getMnethostid();
    }

    @Override
    public java.lang.String getLastip() {
	return delegate.getLastip();
    }

    @Override
    public java.lang.String getTheme() {
	return delegate.getTheme();
    }

    @Override
    public java.lang.String getDescription() {
	return delegate.getDescription();
    }

    @Override
    public java.math.BigInteger getRole() {
	return delegate.getRole();
    }

    public String getLoginDomain() {
	return loginDomain;
    }

    public String getFullUsername() {
	return getUsername() + "@" + getLoginDomain();
    }
}