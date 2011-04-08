package com.linkare.rec.am.experiment;

/**
 * 
 * 
 * @author Artur Correia - Linkare TI
 * 
 */
public class PhysicsValueDTO extends AbstractBaseDTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private PhysicsValDTO value;

    private PhysicsValDTO error;

    private MultiplierEnum appliedMultiplier;

    public PhysicsValueDTO() {
    }

    public PhysicsValDTO getValue() {
	return value;
    }

    public void setValue(PhysicsValDTO value) {
	this.value = value;
    }

    public PhysicsValDTO getError() {
	return error;
    }

    public void setError(PhysicsValDTO error) {
	this.error = error;
    }

    public MultiplierEnum getAppliedMultiplier() {
	return appliedMultiplier;
    }

    public void setAppliedMultiplier(MultiplierEnum appliedMultiplier) {
	this.appliedMultiplier = appliedMultiplier;
    }

    @Override
    public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("PhysicsValueDTO [value=");
	builder.append(value);
	builder.append(", error=");
	builder.append(error);
	builder.append(", appliedMultiplier=");
	builder.append(appliedMultiplier);
	builder.append("]");
	return builder.toString();
    }

}
