package com.linkare.rec.export.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;

/**
 *
 * @author artur
 */
@Entity
@AttributeOverride(name="value",column=@Column(name="DOUBLE_VALUE"))
public class EDoubleVal extends EPhysicsVal {

    private static final long serialVersionUID = 1L;
    private Double value;

    public Double getValue() {
        return value;
    }

    public void setValue(Double doubleValue) {
        this.value = doubleValue;
    }

    /* @Override
    public int hashCode() {
    int hash = 0;
    hash += (id != null ? id.hashCode() : 0);
    return hash;
    }
    @Override
    public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof EDoubleVal)) {
    return false;
    }
    EDoubleVal other = (EDoubleVal) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
    return false;
    }
    return true;
    } */
    @Override
    public String toString() {
        return this.getClass().getName() + "[id=" + this.getId() + "]";
    }
}
