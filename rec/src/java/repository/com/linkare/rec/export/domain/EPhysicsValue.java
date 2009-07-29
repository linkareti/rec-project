/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.linkare.rec.export.domain;

import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.export.domain.enums.EnumMultiplier;
import com.linkare.rec.export.exceptions.InvalidMultiplierException;
import com.linkare.rec.export.exceptions.InvalidTypeValException;
import com.linkare.rec.utils.Utils;
import java.io.Serializable;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author artur
 */
@Entity
@Table(name = "PHYSICS_VALUE")
public class EPhysicsValue implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private EPhysicsVal theValue;
    private EPhysicsVal theError;
    private EnumMultiplier appliedMultiplier;
    private EColumnPhysicsValue column;

    public EPhysicsValue() {
    }

    public EPhysicsValue(PhysicsValue physicsValue) throws InvalidTypeValException, InvalidMultiplierException {
        this.setTheValue(Utils.physicsValToEntity(physicsValue.getValue()));
        this.setTheError(Utils.physicsValToEntity(physicsValue.getError()));
        try {
            if (physicsValue.getAppliedMultiplier() != null) {
                this.setAppliedMultiplier(EnumMultiplier.values()[physicsValue.getAppliedMultiplier().getValue()]);
            }
        } catch (IndexOutOfBoundsException e) {
            //logar o erro
            throw new InvalidMultiplierException();
        }
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    @OneToOne(cascade = CascadeType.PERSIST, optional = false)
    public EPhysicsVal getTheValue() {
        return theValue;
    }

    public void setTheValue(EPhysicsVal theValue) {
        this.theValue = theValue;
    }

    @OneToOne(cascade = CascadeType.PERSIST, optional = false)
    public EPhysicsVal getTheError() {
        return theError;
    }

    public void setTheError(EPhysicsVal theError) {
        this.theError = theError;
    }

    public EnumMultiplier getAppliedMultiplier() {
        return appliedMultiplier;
    }

    public void setAppliedMultiplier(EnumMultiplier appliedMultiplier) {
        this.appliedMultiplier = appliedMultiplier;
    }

    @ManyToOne
    @JoinColumn(name = "COLUMNPHYSICSVALUE_ID")
    public EColumnPhysicsValue getColumn() {
        return column;
    }

    public void setColumn(EColumnPhysicsValue column) {
        this.column = column;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EPhysicsValue)) {
            return false;
        }
        EPhysicsValue other = (EPhysicsValue) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "[id=" + id + "]";
    }
}
