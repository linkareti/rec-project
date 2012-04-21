package com.linkare.rec.am.web.validator;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.linkare.commons.dao.security.LoginDAO;
import com.linkare.jsf.utils.JsfUtil;
import com.linkare.rec.am.web.util.ConstantUtils;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
public class EmailValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent toValidate, Object value) throws ValidatorException {
	String email = (String) value;

	if (!org.apache.commons.validator.EmailValidator.getInstance().isValid(email)) {
	    ((UIInput) toValidate).setValid(false);
	    JsfUtil.addErrorMessage(toValidate.getClientId(context), ConstantUtils.LABEL_ERROR_KEY, LoginDAO.ERROR_PASSWORD_AND_CONFIRM_PASSWORD_ARE_DIFFERENT);
	}
    }
}