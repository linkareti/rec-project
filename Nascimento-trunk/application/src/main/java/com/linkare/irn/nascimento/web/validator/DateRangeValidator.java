package com.linkare.irn.nascimento.web.validator;

import static com.linkare.irn.nascimento.util.ApplicationMessageUtils.getMessage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.ValidatorException;

import com.linkare.irn.nascimento.util.JsfUtils;

/**
 * @author Sérgio Bogas - Linkare TI
 *
 */

@FacesValidator("com.linkare.irn.nascimento.web.validator.DateRangeValidator")
public class DateRangeValidator extends GenericValidator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

	if (!isValidationDisabled() && Objects.nonNull(value)) {

	    final Date minDate = (Date) component.getAttributes().get("minDate");
	    final Date maxDate = (Date) component.getAttributes().get("maxDate");
	    final String datePattern = (String) component.getAttributes().get("pattern");

	    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePattern);

	    checkComponentAttributes(minDate, maxDate);

	    final Date date = (Date) value;

	    validateMinDate(minDate, date, simpleDateFormat);
	    validateMaxAge(maxDate, date, simpleDateFormat);
	}
    }

    private void validateMaxAge(final Date maxDate, final Date date, SimpleDateFormat simpleDateFormat) {

	if (maxDate.before(date)) {
	    final FacesMessage message = JsfUtils.createFacesMessage(FacesMessage.SEVERITY_ERROR, getMessage("message.error"),
								     getMessage("message.error.maxDate", simpleDateFormat.format(maxDate)));
	    throw new ValidatorException(message);
	}
    }

    private void validateMinDate(Date minDate, final Date date, SimpleDateFormat simpleDateFormat) {

	if (minDate.after(date)) {
	    final FacesMessage message = JsfUtils.createFacesMessage(FacesMessage.SEVERITY_ERROR, getMessage("message.error"),
								     getMessage("message.error.minDate", simpleDateFormat.format(minDate)));
	    throw new ValidatorException(message);
	}
    }

    private void checkComponentAttributes(final Date minDate, final Date maxDate) {
	if (minDate != null && maxDate != null && minDate.after(maxDate)) {
	    throw new IllegalArgumentException("minDate can not be lower than maxDate");
	}
    }
}