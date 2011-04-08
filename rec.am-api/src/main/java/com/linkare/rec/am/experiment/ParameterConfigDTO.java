/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.linkare.rec.am.experiment;

/**
 * 
 * @author Artur Correia - Linkare TI
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

    @Override
    public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("ParameterConfigDTO [parameterName=");
	builder.append(parameterName);
	builder.append(", parameterValue=");
	builder.append(parameterValue);
	builder.append("]");
	return builder.toString();
    }

}
