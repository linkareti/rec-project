package com.linkare.rec.am.model;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.PrePersist;

/**
 * 
 * @author artur
 */
@Entity
public class ByteArrayVal extends PhysicsVal {

    private static final long serialVersionUID = 1L;

    @Embedded
    private ByteArrayValue value;

    public ByteArrayVal() {
    }

    public ByteArrayValue getValue() {
	return value;
    }

    public void setValue(ByteArrayValue value) {
	this.value = value;
    }

    /*
     * @Override public int hashCode() { int hash = 0; hash += (id != null ? id.hashCode() : 0); return hash; }
     * 
     * @Override public boolean equals(Object object) { // TODO: Warning - this method won't work in the case the id fields are not set if (!(object instanceof
     * EByteArrayValue)) { return false; } EByteArrayValue other = (EByteArrayValue) object; if ((this.id == null && other.id != null) || (this.id != null &&
     * !this.id.equals(other.id))) { return false; } return true; }
     */

    @PrePersist
    public void preInsert() {
	//	try {
	//	    InitialContext ic = new InitialContext();
	//	    ConsultarReCLocal beanLocal = (ConsultarReCLocal) ic.lookup("java:comp/env/ejb/com.linkare.rec.ejb.ConsultarReCBeanBean");
	//	    beanLocal.sendMessage(this.getValue().getVirtualPath(), this.getValue().getData());
	//	} catch (NamingException ex) {
	//	}

    }
}
