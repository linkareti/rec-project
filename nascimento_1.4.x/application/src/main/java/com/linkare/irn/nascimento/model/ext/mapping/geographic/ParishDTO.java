package com.linkare.irn.nascimento.model.ext.mapping.geographic;

import com.linkare.irn.nascimento.model.ext.mapping.ImportableDTO;
import com.linkare.irn.nascimento.model.geographic.Parish;
import com.linkare.irn.nascimento.service.mapping.ImportUtils;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public class ParishDTO extends ImportableDTO<Parish> {

    private String name;

    private String code;

    private String active;

    private String countyExternalId;

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
     * @return the countyExternalId
     */
    public String getCountyExternalId() {
	return countyExternalId;
    }

    /**
     * @param countyExternalId
     *            the countyExternalId to set
     */
    public void setCountyExternalId(String countyExternalId) {
	this.countyExternalId = countyExternalId;
    }

    @Override
    public Parish toEntity() {
	final Parish parish = new Parish();
	parish.setName(name);
	parish.setCode(code);
	parish.setActive(ImportUtils.booleanOf(active));
	return parish;
    }
}