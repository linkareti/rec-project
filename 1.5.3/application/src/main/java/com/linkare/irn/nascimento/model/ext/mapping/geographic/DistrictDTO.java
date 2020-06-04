package com.linkare.irn.nascimento.model.ext.mapping.geographic;

import com.linkare.irn.nascimento.model.ext.mapping.ImportableDTO;
import com.linkare.irn.nascimento.model.geographic.District;
import com.linkare.irn.nascimento.service.mapping.ImportUtils;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public class DistrictDTO extends ImportableDTO<District> {

    private String name;

    private String code;

    private String active;

    private String countryExternalId;

    /**
     * @return the name
     */
    public String getName() {
	return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
	this.name = name;
    }

    /**
     * @return the code
     */
    public String getCode() {
	return code;
    }

    /**
     * @param code
     *            the code to set
     */
    public void setCode(String code) {
	this.code = code;
    }

    /**
     * @return the active
     */
    public String getActive() {
	return active;
    }

    /**
     * @param active
     *            the active to set
     */
    public void setActive(String active) {
	this.active = active;
    }

    /**
     * @return the countryExternalId
     */
    public String getCountryExternalId() {
	return countryExternalId;
    }

    /**
     * @param countryExternalId
     *            the countryExternalId to set
     */
    public void setCountryExternalId(String countryExternalId) {
	this.countryExternalId = countryExternalId;
    }

    @Override
    public District toEntity() {
	final District district = new District();
	district.setName(name);
	district.setCode(code);
	district.setActive(ImportUtils.booleanOf(active));
	return district;
    }
}