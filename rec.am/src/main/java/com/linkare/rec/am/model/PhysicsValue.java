/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.linkare.rec.am.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.linkare.commons.jpa.DefaultDomainObject;
import com.linkare.rec.am.experiment.MultiplierEnum;

/**
 * 
 * @author Artur Correia - Linkare TI
 */
@Entity
@Table(name = "PHYSICS_VALUE")
public class PhysicsValue extends DefaultDomainObject {

    private static final long serialVersionUID = 1L;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "KEY_PHYSICS_VAL_VALUE", nullable = false, updatable = false)
    private PhysicsVal value;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "KEY_PHYSICS_VAL_ERROR", nullable = false, updatable = false)
    private PhysicsVal error;

    @Enumerated(EnumType.STRING)
    @Column(name = "APPLIED_MULTIPLIER")
    private MultiplierEnum appliedMultiplier;

    public PhysicsValue() {
    }

    public PhysicsVal getValue() {
	return value;
    }

    public void setValue(PhysicsVal value) {
	this.value = value;
    }

    public PhysicsVal getError() {
	return error;
    }

    public void setError(PhysicsVal error) {
	this.error = error;
    }

    public MultiplierEnum getAppliedMultiplier() {
	return appliedMultiplier;
    }

    public void setAppliedMultiplier(MultiplierEnum appliedMultiplier) {
	this.appliedMultiplier = appliedMultiplier;
    }

}
