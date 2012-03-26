package com.linkare.rec.export.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;

/**
 *
 * @author artur
 */
@Entity
@AttributeOverride(name="value",column=@Column(name="BYTE_VALUE"))
public class EByteVal extends EPhysicsVal {

    private static final long serialVersionUID = 1L;

    private Byte value;
    
    
    public Byte getValue() {
        return value;
    }

    public void setValue(Byte value) {
        this.value = value;
    }

    /*@Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EByteVal)) {
            return false;
        }
        EByteVal other = (EByteVal) object;
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
