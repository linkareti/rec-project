/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.linkare.rec.am.experiment;

/**
 * 
 * @author Artur Correia - Linkare TI
 */
public class ScaleDTO extends AbstractBaseDTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private MultiplierEnum multiplier;

    private PhysicsValDTO minValue;

    private PhysicsValDTO maxValue;

    private PhysicsValDTO defaultError;

    private PhysicsValDTO step;

    private String physicsUnitName;

    private String physicsUnitSymbol;

    private String scaleLabel;

    public ScaleDTO() {
    }

    public MultiplierEnum getMultiplier() {
	return multiplier;
    }

    public void setMultiplier(MultiplierEnum appliedMultiplier) {
	this.multiplier = appliedMultiplier;
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

    @Override
    public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("ScaleDTO [multiplier=");
	builder.append(multiplier);
	builder.append(", minValue=");
	builder.append(minValue);
	builder.append(", maxValue=");
	builder.append(maxValue);
	builder.append(", defaultError=");
	builder.append(defaultError);
	builder.append(", step=");
	builder.append(step);
	builder.append(", physicsUnitName=");
	builder.append(physicsUnitName);
	builder.append(", physicsUnitSymbol=");
	builder.append(physicsUnitSymbol);
	builder.append(", scaleLabel=");
	builder.append(scaleLabel);
	builder.append("]");
	return builder.toString();
    }

}
