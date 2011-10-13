package com.linkare.rec.am.model;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 
 * @author Artur Correia - Linkare TI
 */
@Entity
public class BooleanVal extends PhysicsVal {

    private static final long serialVersionUID = 1L;

    @Column(name = "BOOLEAN_VALUE")
    private Boolean value;

    public Boolean getValue() {
	return value;
    }

    public void setValue(Boolean value) {
	this.value = value;
    }

    /*
     * @Override public int hashCode() { int hash = 0; hash += (id != null ? id.hashCode() : 0); return hash; }
     * 
     * @Override public boolean equals(Object object) { // TODO: Warning - this method won't work in the case the id fields are not set if (!(object instanceof
     * EBooleanVal)) { return false; } EBooleanVal other = (EBooleanVal) object; if ((this.id == null && other.id != null) || (this.id != null &&
     * !this.id.equals(other.id))) { return false; } return true; }
     */
}
