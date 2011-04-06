package com.linkare.rec.am.model;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 
 * @author artur
 */
@Entity
public class FloatVal extends PhysicsVal {

    private static final long serialVersionUID = 1L;

    @Column(name = "FLOAT_VALUE")
    private Float value;

    public Float getValue() {
	return value;
    }

    public void setValue(Float value) {
	this.value = value;
    }

    /*
     * @Override public int hashCode() { int hash = 0; hash += (id != null ? id.hashCode() : 0); return hash; }
     * 
     * @Override public boolean equals(Object object) { // TODO: Warning - this method won't work in the case the id fields are not set if (!(object instanceof
     * EFloatVal)) { return false; } EFloatVal other = (EFloatVal) object; if ((this.id == null && other.id != null) || (this.id != null &&
     * !this.id.equals(other.id))) { return false; } return true; }
     */

}
