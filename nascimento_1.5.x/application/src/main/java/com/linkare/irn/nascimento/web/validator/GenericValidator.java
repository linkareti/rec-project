package com.linkare.irn.nascimento.web.validator;

import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 *
 */
public abstract class GenericValidator implements Validator {

    protected boolean isValidationDisabled() {
	final HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	final String disableValidationParameter = request.getParameter("disableValidation");
	return StringUtils.isNotBlank(disableValidationParameter) && Boolean.parseBoolean(disableValidationParameter);
    }
}