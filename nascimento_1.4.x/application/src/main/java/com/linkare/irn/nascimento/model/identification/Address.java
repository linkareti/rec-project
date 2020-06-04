package com.linkare.irn.nascimento.model.identification;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.linkare.irn.nascimento.model.geographic.Country;
import com.linkare.irn.nascimento.model.geographic.Parish;
import com.linkare.irn.nascimento.model.validation.BirthRegistrationFirstParentValidation;
import com.linkare.irn.nascimento.model.validation.BirthRegistrationRegistrationValidation;
import com.linkare.irn.nascimento.model.validation.BirthRegistrationSecondParentValidation;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
@Embeddable
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "address_type", length = 75)
    @Enumerated(EnumType.STRING)
    @NotNull(groups = { BirthRegistrationFirstParentValidation.class, BirthRegistrationSecondParentValidation.class,
	    BirthRegistrationRegistrationValidation.class })
    private AddressType addressType;

    @Column(name = "street", length = 255)
    @Size(max = 255)
    private String street;

    @Column(name = "zip_code", length = 50)
    @Size(max = 50)
    private String zipCode;

    @Column(name = "location", length = 150)
    @Size(max = 150)
    private String location;

    @ManyToOne
    @JoinColumn(name = "key_parish")
    private Parish parish;

    @ManyToOne
    @JoinColumn(name = "key_country")
    private Country country;

    /**
     * @return the addressType
     */
    public AddressType getAddressType() {
	return addressType;
    }

    /**
     * @param addressType
     *            the addressType to set
     */
    public void setAddressType(AddressType addressType) {
	this.addressType = addressType;
    }

    /**
     * @return the street
     */
    public String getStreet() {
	return street;
    }

    /**
     * @param street
     *            the street to set
     */
    public void setStreet(String street) {
	this.street = street;
    }

    /**
     * @return the zipCode
     */
    public String getZipCode() {
	return zipCode;
    }

    /**
     * @param zipCode
     *            the zipCode to set
     */
    public void setZipCode(String zipCode) {
	this.zipCode = zipCode;
    }

    /**
     * @return the location
     */
    public String getLocation() {
	return location;
    }

    /**
     * @param location
     *            the location to set
     */
    public void setLocation(String location) {
	this.location = location;
    }

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
}