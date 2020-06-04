package com.linkare.irn.nascimento.web.validator;

import static com.linkare.irn.nascimento.util.ApplicationMessageUtils.getMessage;

import java.util.Objects;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.ValidatorException;

import com.linkare.irn.nascimento.util.JsfUtils;

@FacesValidator("com.linkare.irn.nascimento.web.validator.ForbiddenEmailFieldValidator")
public class ForbiddenEmailFieldValidator extends GenericValidator {

	public static String EMAIL_INVALID_MAIL_FIELD_REGEX = "[%\"#|@&=;»«~´`^,<>?;̈]";

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

		if (!isValidationDisabled() && Objects.nonNull(value)) {
			final String forbiddenEmailField = (String) value;

			if (Pattern.compile(EMAIL_INVALID_MAIL_FIELD_REGEX).matcher(forbiddenEmailField).find()) {

				final FacesMessage message = JsfUtils.createFacesMessage(FacesMessage.SEVERITY_ERROR,
						getMessage("message.error"),
						getMessage("message.error.invalidFieldCharacters", forbiddenEmailField));

				throw new ValidatorException(message);
			}
		}
	}

}