package com.linkare.rec.am.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import com.linkare.commons.jpa.DefaultDomainObject;

/**
 * 
 * @author artur
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "PARAMETER_FROM")
@Table(name = "PARAMETER_CONFIG")
public abstract class ParameterConfig extends DefaultDomainObject {

    private static final long serialVersionUID = 1L;

    @Column(name = "PARAMETER_NAME")
    private String parameterName;

    @Column(name = "PARAMETER_VALUE")
    private String parameterValue;

    public String getParameterName() {
	return parameterName;
    }

    public void setParameterName(String parameterName) {
	this.parameterName = parameterName;
    }

    public String getParameterValue() {
	return parameterValue;
    }

    public void setParameterValue(String parameterValue) {
	this.parameterValue = parameterValue;
    }
}
