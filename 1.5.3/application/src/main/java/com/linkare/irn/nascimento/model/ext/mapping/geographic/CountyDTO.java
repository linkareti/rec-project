package com.linkare.irn.nascimento.model.ext.mapping.geographic;

import com.linkare.irn.nascimento.model.ext.mapping.ImportableDTO;
import com.linkare.irn.nascimento.model.geographic.County;
import com.linkare.irn.nascimento.service.mapping.ImportUtils;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public class CountyDTO extends ImportableDTO<County> {

    private String name;

    private String code;

    private String active;

    private String districtExternalId;

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
     * @return the districtExternalId
     */
    public String getDistrictExternalId() {
	return districtExternalId;
    }

    /**
     * @param districtExternalId
     *            the districtExternalId to set
     */
    public void setDistrictExternalId(String districtExternalId) {
	this.districtExternalId = districtExternalId;
    }

    @Override
    public County toEntity() {
	final County county = new County();
	county.setName(name);
	county.setCode(code);
	county.setActive(ImportUtils.booleanOf(active));
	return county;
    }
}