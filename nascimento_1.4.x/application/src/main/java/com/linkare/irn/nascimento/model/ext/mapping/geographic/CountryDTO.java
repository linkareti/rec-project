package com.linkare.irn.nascimento.model.ext.mapping.geographic;

import com.linkare.irn.nascimento.model.ext.mapping.ImportableDTO;
import com.linkare.irn.nascimento.model.geographic.Country;
import com.linkare.irn.nascimento.service.mapping.ImportUtils;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public class CountryDTO extends ImportableDTO<Country> {

    private String name;

    private String code;

    private String active;

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

    @Override
    public Country toEntity() {
	final Country country = new Country();
	country.setName(name);
	country.setCode(code);
	country.setActive(ImportUtils.booleanOf(active));
	return country;
    }
}