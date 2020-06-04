package com.linkare.irn.nascimento.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 *
 */
@MappedSuperclass
public abstract class VersionedDomainObject extends DomainObject {

    private static final long serialVersionUID = 5452496747967322411L;

    @Version
    @Column(name = "version")
    private int version;

    /**
     * @return the version
     */
    public int getVersion() {
	return version;
    }

    /**
     * @param version
     *            the version to set
     */
    public void setVersion(int version) {
	this.version = version;
    }
}