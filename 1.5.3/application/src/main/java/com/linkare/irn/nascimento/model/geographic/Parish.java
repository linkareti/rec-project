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
@DiscriminatorValue("Parish")
@NamedQueries({ @NamedQuery(name = Parish.QUERY_NAME_FIND_BY_CODE, query = "select p from Parish p where p.code = :" + Parish.QUERY_PARAM_CODE),
	@NamedQuery(name = Parish.QUERY_NAME_FIND_ALL_LIKE_NAME_IN_FULL_PATH, query = "select p from Parish p join p.county c join c.district d where lower(p.name) like :"
		+ Parish.QUERY_PARAM_NAME + " or lower(c.name) like :" + Parish.QUERY_PARAM_NAME + " or lower(d.name) like :" + Parish.QUERY_PARAM_NAME
		+ " order by p.active desc, p.name"),
	@NamedQuery(name = Parish.QUERY_NAME_FIND_ACTIVE_LIKE_NAME_IN_FULL_PATH, query = "select p from Parish p join p.county c join c.district d where (lower(p.name) like :"
		+ Parish.QUERY_PARAM_NAME + " or lower(c.name) like :" + Parish.QUERY_PARAM_NAME + " or lower(d.name) like :" + Parish.QUERY_PARAM_NAME
		+ ") and (p.active = true and c.active = true and d.active = true) order by p.name"),
	@NamedQuery(name = Parish.QUERY_NAME_FIND_BY_NAME, query = "select p from Parish p where lower(p.name) = :" + Parish.QUERY_PARAM_NAME) })
public class Parish extends AdministrativeDivision {

    private static final long serialVersionUID = 6125050924535494051L;

    public static final String QUERY_NAME_FIND_BY_CODE = "Parish.findByCode";

    public static final String QUERY_NAME_FIND_ALL_LIKE_NAME_IN_FULL_PATH = "Parish.findAllLikeNameInFullPath";
    
    public static final String QUERY_NAME_FIND_ACTIVE_LIKE_NAME_IN_FULL_PATH = "Parish.findActiveLikeNameInFullPath";

    public static final String QUERY_NAME_FIND_BY_NAME = "Parish.findByName";

    public static final String QUERY_PARAM_CODE = "code";

    public static final String QUERY_PARAM_NAME = "name";

    @ManyToOne
    @JoinColumn(name = "key_county")
    @NotNull
    private County county;

    /**
     * @return the county
     */
    public County getCounty() {
	return county;
    }

    /**
     * @param county
     *            the county to set
     */
    public void setCounty(County county) {
	this.county = county;
    }
}