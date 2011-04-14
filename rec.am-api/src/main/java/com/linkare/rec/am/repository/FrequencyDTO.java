/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.linkare.rec.am.repository;

/**
 * 
 * @author Artur Correia - Linkare TI
 */
public class FrequencyDTO extends AbstractBaseDTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private double frequency;
    private MultiplierEnum appliedMultiplier;
    private FrequencyDefTypeEnum frequencyType;

    public double getFrequency() {
	return frequency;
    }

    public void setFrequency(double frequency) {
	this.frequency = frequency;
    }

    public FrequencyDefTypeEnum getFrequencyDefType() {
	return frequencyType;
    }

    public void setFrequencyDefType(FrequencyDefTypeEnum frequencyType) {
	this.frequencyType = frequencyType;
    }

    public MultiplierEnum getAppliedMultiplier() {
	return appliedMultiplier;
    }

    public void setAppliedMultiplier(MultiplierEnum multiplier) {
	this.appliedMultiplier = multiplier;
    }

    @Override
    public String toString() {
	return "FrequencyDTO [frequency=" + frequency + ", appliedMultiplier=" + appliedMultiplier + ", frequencyType=" + frequencyType + "]";
    }

}
