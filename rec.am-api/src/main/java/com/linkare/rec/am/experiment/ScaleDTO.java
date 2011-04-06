/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.linkare.rec.am.experiment;

/**
 * 
 * @author artur
 */
public class ScaleDTO extends AbstractBaseDTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private MultiplierEnum appliedMultiplier;

    private PhysicsValDTO minValue;

    private PhysicsValDTO maxValue;

    private PhysicsValDTO defaultError;

    private PhysicsValDTO step;

    private String physicsUnitName;

    private String physicsUnitSymbol;

    private String scaleLabel;

    public ScaleDTO() {
    }

    public MultiplierEnum getAppliedMultiplier() {
	return appliedMultiplier;
    }

    public void setAppliedMultiplier(MultiplierEnum appliedMultiplier) {
	this.appliedMultiplier = appliedMultiplier;
    }

    public PhysicsValDTO getMinValue() {
	return minValue;
    }

    public void setMinValue(PhysicsValDTO minValue) {
	this.minValue = minValue;
    }

    public PhysicsValDTO getMaxValue() {
	return maxValue;
    }

    public void setMaxValue(PhysicsValDTO maxValue) {
	this.maxValue = maxValue;
    }

    public PhysicsValDTO getDefaultError() {
	return defaultError;
    }

    public void setDefaultError(PhysicsValDTO defaultError) {
	this.defaultError = defaultError;
    }

    public PhysicsValDTO getStep() {
	return step;
    }

    public void setStep(PhysicsValDTO step) {
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
