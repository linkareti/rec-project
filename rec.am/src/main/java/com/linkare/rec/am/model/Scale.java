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
 * @author artur
 */
@Entity
@Table(name = "SCALE")
public class Scale extends DefaultDomainObject {

    private static final long serialVersionUID = 1L;

    @Enumerated(EnumType.STRING)
    @Column(name = "APPLIED_MULTIPLIER")
    private MultiplierEnum multiplier;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "KEY_PHYSICS_VAL_MINVALUE")
    private PhysicsVal minValue;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "KEY_PHYSICS_VAL_MAXVALUE")
    private PhysicsVal maxValue;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "KEY_PHYSICS_VAL_DEFAULTERROR")
    private PhysicsVal defaultError;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "KEY_PHYSICS_VAL_STEP")
    private PhysicsVal step;

    @Column(name = "PHYSICS_UNIT_NAME")
    private String physicsUnitName;

    @Column(name = "PHYSICS_UNIT_SYMBOL")
    private String physicsUnitSymbol;

    @Column(name = "SCALE_LABEL")
    private String scaleLabel;

    public Scale() {
    }

    public MultiplierEnum getMultiplier() {
	return multiplier;
    }

    public void setMultiplier(MultiplierEnum appliedMultiplier) {
	this.multiplier = appliedMultiplier;
    }

    public PhysicsVal getMinValue() {
	return minValue;
    }

    public void setMinValue(PhysicsVal minValue) {
	this.minValue = minValue;
    }

    public PhysicsVal getMaxValue() {
	return maxValue;
    }

    public void setMaxValue(PhysicsVal maxValue) {
	this.maxValue = maxValue;
    }

    public PhysicsVal getDefaultError() {
	return defaultError;
    }

    public void setDefaultError(PhysicsVal defaultError) {
	this.defaultError = defaultError;
    }

    public PhysicsVal getStep() {
	return step;
    }

    public void setStep(PhysicsVal step) {
	this.step = step;
    }

    public String getPhysicsUnitName() {
	return physicsUnitName;
    }

    public void setPhysicsUnitName(String physicsUnitName) {
	this.physicsUnitName = physicsUnitName;
    }

    public String getPhysicsUnitSymbol() {
	return physicsUnitSymbol;
    }

    public void setPhysicsUnitSymbol(String physicsUnitSymbol) {
	this.physicsUnitSymbol = physicsUnitSymbol;
    }

    public String getScaleLabel() {
	return scaleLabel;
    }

    public void setScaleLabel(String scaleLabel) {
	this.scaleLabel = scaleLabel;
    }
}
