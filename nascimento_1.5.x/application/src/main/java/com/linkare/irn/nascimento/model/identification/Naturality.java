package com.linkare.irn.nascimento.model.identification;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.linkare.irn.nascimento.model.DomainObject;
import com.linkare.irn.nascimento.model.geographic.Country;
import com.linkare.irn.nascimento.model.validation.BirthRegistrationFirstParentValidation;
import com.linkare.irn.nascimento.model.validation.BirthRegistrationRegistrationValidation;
import com.linkare.irn.nascimento.model.validation.BirthRegistrationSecondParentValidation;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
@Entity
@Table(name = "naturality")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "instance_type")
public abstract class Naturality extends DomainObject {

    private static final long serialVersionUID = 7554553051019951775L;

    @ManyToOne
    @JoinColumn(name = "key_country")
    @NotNull(groups = { BirthRegistrationFirstParentValidation.class, BirthRegistrationSecondParentValidation.class,
	    BirthRegistrationRegistrationValidation.class })
    private Country country;

    /**
     * @return the country
     */
    public Country getCountry() {
	return country;
    }

    /**
     * @param country
     *            the country to set
     */
    public void setCountry(Country country) {
	this.country = country;
    }

    /**
     * @return the naturalityType
     */
    public abstract NaturalityType getNaturalityType();
}