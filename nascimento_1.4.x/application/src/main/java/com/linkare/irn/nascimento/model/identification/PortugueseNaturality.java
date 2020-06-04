package com.linkare.irn.nascimento.model.identification;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.linkare.irn.nascimento.model.geographic.Parish;
import com.linkare.irn.nascimento.model.validation.BirthRegistrationFirstParentValidation;
import com.linkare.irn.nascimento.model.validation.BirthRegistrationRegistrationValidation;
import com.linkare.irn.nascimento.model.validation.BirthRegistrationSecondParentValidation;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
@Entity
@DiscriminatorValue("PortugueseNaturality")
public class PortugueseNaturality extends Naturality {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "key_parish")
    @NotNull(groups = { BirthRegistrationFirstParentValidation.class, BirthRegistrationSecondParentValidation.class,
	    BirthRegistrationRegistrationValidation.class })
    private Parish parish;

    /**
     * @return the parish
     */
    public Parish getParish() {
	return parish;
    }

    /**
     * @param parish
     *            the parish to set
     */
    public void setParish(Parish parish) {
	this.parish = parish;
    }

    @Override
    public NaturalityType getNaturalityType() {
	return NaturalityType.PORTUGUESE;
    }
}