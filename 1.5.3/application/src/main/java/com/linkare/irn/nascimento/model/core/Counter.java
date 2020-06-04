package com.linkare.irn.nascimento.model.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.linkare.irn.nascimento.model.VersionedDomainObject;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
@Entity
@Table(name = "counter", indexes = { @Index(name = "idx_unique_code", columnList = "code", unique = true) })
@NamedQueries({ @NamedQuery(name = Counter.QUERY_NAME_FIND_BY_CODE, query = "select c from Counter c where c.code = :" + Counter.QUERY_PARAM_CODE)

})
public class Counter extends VersionedDomainObject {

    private static final long INITIAL_KEY_COUNT = 1l;

    private static final long serialVersionUID = 1L;

    public static final String QUERY_NAME_FIND_BY_CODE = "Counter.findByCode";

    public static final String QUERY_PARAM_CODE = "code";

    @Column(name = "code", length = 30, nullable = false)
    @Size(max = 30)
    @NotNull
    private String code;

    @Column(name = "count", nullable = false)
    private long count = INITIAL_KEY_COUNT;

    public Counter() {
	super();
    }

    /**
     * @param code
     */
    public Counter(String code) {
	this();
	this.code = code;
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
     * @return the count
     */
    public long getCount() {
	return count;
    }

    /**
     * @param count
     *            the count to set
     */
    public void setCount(long count) {
	this.count = count;
    }

    public void increment() {
	count++;
    }
}