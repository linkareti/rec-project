/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.linkare.rec.am.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.linkare.commons.jpa.DefaultDomainObject;
import com.linkare.rec.am.experiment.MultiplierEnum;

/**
 * 
 * @author artur
 */
@Entity
@Table(name = "PHYSICS_VALUE")
public class PhysicsValue extends DefaultDomainObject {

    private static final long serialVersionUID = 1L;

    @OneToOne(optional = false)
    @JoinColumn(name = "KEY_PHYSICS_VAL_VALUE")
    private PhysicsVal value;

    @OneToOne(optional = false)
    @JoinColumn(name = "KEY_PHYSICS_VAL_ERROR")
    private PhysicsVal error;

    @Enumerated(EnumType.STRING)
    @Column(name = "APPLIED_MULTIPLIER")
    private MultiplierEnum appliedMultiplier;

    @ManyToOne
    @JoinColumn(name = "KEY_COLUMN_PHYSICS_VALUE")
    private ColumnPhysicsValue column;

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

    public ColumnPhysicsValue getColumn() {
	return column;
    }

    public void setColumn(ColumnPhysicsValue column) {
	this.column = column;
    }

}
