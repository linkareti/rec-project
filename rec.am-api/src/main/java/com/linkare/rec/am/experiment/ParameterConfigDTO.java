/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.linkare.rec.am.experiment;

/**
 * 
 * @author artur
 */
public class ParameterConfigDTO extends AbstractBaseDTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String parameterName;
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
