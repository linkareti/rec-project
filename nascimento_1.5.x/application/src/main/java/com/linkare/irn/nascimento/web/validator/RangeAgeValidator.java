package com.linkare.irn.nascimento.web.validator;

import static com.linkare.irn.nascimento.util.ApplicationMessageUtils.getMessage;

import java.util.Calendar;
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

@FacesValidator("com.linkare.irn.nascimento.web.validator.RangeAgeValidator")
public class RangeAgeValidator extends GenericValidator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

	if (!isValidationDisabled() && Objects.nonNull(value)) {

	    Integer minAge = (Integer) component.getAttributes().get("minAge");
	    final Integer maxAge = (Integer) component.getAttributes().get("maxAge");

	    checkComponentAttributes(minAge, maxAge);

	    final Date dateOfBirth = (Date) value;

	    validateAgainstCurrentDate(dateOfBirth);
	    validateMinAge(minAge, dateOfBirth);
	    validateMaxAge(maxAge, dateOfBirth);
	}
    }

    private void validateMaxAge(final Integer maxAge, final Date dateOfBirth) {
	if (Objects.nonNull(maxAge)) {
	    final Calendar minCalendar = Calendar.getInstance();
	    minCalendar.add(Calendar.YEAR, -maxAge);

	    final Date minDate = minCalendar.getTime();

	    if (minDate.after(dateOfBirth)) {
		final FacesMessage message = JsfUtils.createFacesMessage(FacesMessage.SEVERITY_ERROR, getMessage("message.error"),
									 getMessage("message.error.minDateOfBirth", maxAge));
		throw new ValidatorException(message);
	    }
	}
    }

    private void validateMinAge(Integer minAge, final Date dateOfBirth) {
	if (Objects.isNull(minAge)) {
	    minAge = 0;
	}

	final Calendar maxCalendar = Calendar.getInstance();
	maxCalendar.add(Calendar.YEAR, -minAge);

	final Date maxDate = maxCalendar.getTime();

	if (maxDate.before(dateOfBirth)) {
	    final FacesMessage message = JsfUtils.createFacesMessage(FacesMessage.SEVERITY_ERROR, getMessage("message.error"),
								     getMessage("message.error.maxDateOfBirth", minAge));
	    throw new ValidatorException(message);
	}
    }

    private void validateAgainstCurrentDate(final Date dateOfBirth) {
	final Calendar currentDate = Calendar.getInstance();
	if (dateOfBirth.after(currentDate.getTime())) {
	    final FacesMessage message = JsfUtils.createFacesMessage(FacesMessage.SEVERITY_ERROR, getMessage("message.error"),
								     getMessage("message.error.dateOfBirthMustBeBeforeCurrentDate"));

	    throw new ValidatorException(message);
	}
    }

    private void checkComponentAttributes(Integer minAge, final Integer maxAge) {
	if (minAge != null && maxAge != null && minAge > maxAge) {
	    throw new IllegalArgumentException("minAge can not be lower than maxAge");
	}
    }
}