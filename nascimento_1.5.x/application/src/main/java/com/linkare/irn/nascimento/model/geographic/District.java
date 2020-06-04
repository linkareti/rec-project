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
@DiscriminatorValue("District")
@NamedQueries({ @NamedQuery(name = District.QUERY_NAME_FIND_BY_CODE, query = "select d from District d where d.code = :" + District.QUERY_PARAM_CODE) })
public class District extends AdministrativeDivision {

    private static final long serialVersionUID = 6125050924535494051L;

    public static final String QUERY_NAME_FIND_BY_CODE = "District.findByCode";

    public static final String QUERY_PARAM_CODE = "code";

    @ManyToOne
    @JoinColumn(name = "key_country")
    @NotNull
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
}