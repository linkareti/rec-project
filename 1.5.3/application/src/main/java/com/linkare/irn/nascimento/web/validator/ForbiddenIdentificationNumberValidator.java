package com.linkare.irn.nascimento.web.validator;

import static com.linkare.irn.nascimento.util.ApplicationMessageUtils.getMessage;

import java.util.Objects;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.ValidatorException;

import org.apache.commons.lang.StringUtils;

import com.linkare.irn.nascimento.util.JsfUtils;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
@FacesValidator("com.linkare.irn.nascimento.web.validator.ForbiddenIdentificationNumberValidator")
public class ForbiddenIdentificationNumberValidator extends GenericValidator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		if (!isValidationDisabled() && Objects.nonNull(value)) {
			final String forbiddenIdentificationNumber = (String) component.getAttributes()
					.get("forbiddenIdentificationNumber");

			if (StringUtils.isNotBlank(forbiddenIdentificationNumber)
					&& Objects.equals(forbiddenIdentificationNumber, value)) {
				final FacesMessage message = JsfUtils.createFacesMessage(FacesMessage.SEVERITY_ERROR,
						getMessage("message.error"),
						getMessage("message.error.forbiddenIdentificationNumber", forbiddenIdentificationNumber));

				throw new ValidatorException(message);
			}else if(!((String) value).matches("[0-9]+") || ((String) value).length() > 9) {
				
				final FacesMessage message = JsfUtils.createFacesMessage(FacesMessage.SEVERITY_ERROR,
						getMessage("message.error"),
						getMessage("message.error.invalidIdentificationNumber", forbiddenIdentificationNumber));
				
				throw new ValidatorException(message);
			}
		}
	}
}