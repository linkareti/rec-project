package com.linkare.irn.nascimento.model.geographic;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
@Entity
@DiscriminatorValue("County")
@NamedQueries({ @NamedQuery(name = County.QUERY_NAME_FIND_BY_CODE, query = "select c from County c where c.code = :" + County.QUERY_PARAM_CODE) })
public class County extends AdministrativeDivision {

    private static final long serialVersionUID = 6125050924535494051L;

    public static final String QUERY_NAME_FIND_BY_CODE = "County.findByCode";

    public static final String QUERY_PARAM_CODE = "code";

    @ManyToOne
    @JoinColumn(name = "key_district")
    @NotNull
    private District district;

    /**
     * @return the district
     */
    public District getDistrict() {
	return district;
    }

    /**
     * @param district
     *            the district to set
     */
    public void setDistrict(District district) {
	this.district = district;
    }
}