package com.linkare.rec.export.domain;

import java.io.Serializable;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

/**
 *
 * @author artur
 */
@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@Table(name="PHYSICS_VAL")
@DiscriminatorColumn(name="VALUE_TYPE")
public abstract class EPhysicsVal implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private Long id;
    
    
    
    
    @Id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(object instanceof EPhysicsVal)) {
            return false;
        }
        EPhysicsVal other = (EPhysicsVal) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    } 

    @Override
    public String toString() {
        return this.getClass().getName() + "[id=" + id + "]";
    } */
    
    
    
    
}
