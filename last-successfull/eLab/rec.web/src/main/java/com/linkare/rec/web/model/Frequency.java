/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.linkare.rec.web.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.linkare.rec.web.repository.FrequencyDefTypeEnum;
import com.linkare.rec.web.repository.MultiplierEnum;

/**
 * 
 * @author Artur Correia - Linkare TI
 */
@Embeddable
public class Frequency implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Column(name = "FREQUENCY")
    private double frequency;

    @Enumerated(EnumType.STRING)
    @Column(name = "APPLIED_MULTIPLIER")
    private MultiplierEnum appliedMultiplier;

    @Enumerated(EnumType.STRING)
    @Column(name = "FREQUENCY_DEF_TYPE")
    private FrequencyDefTypeEnum frequencyDefType;

    //default constructor to serializable purposes
    public Frequency() {
    }

    public double getFrequency() {
	return frequency;
    }

    public void setFrequency(double frequency) {
	this.frequency = frequency;
    }

    public MultiplierEnum getAppliedMultiplier() {
	return appliedMultiplier;
    }

    public void setAppliedMultiplier(MultiplierEnum appliedMultiplier) {
	this.appliedMultiplier = appliedMultiplier;
    }

    public FrequencyDefTypeEnum getFrequencyDefType() {
	return frequencyDefType;
    }

    public void setFrequencyDefType(FrequencyDefTypeEnum frequencyDefType) {
	this.frequencyDefType = frequencyDefType;
    }

    @Override
    public String toString() {
	return "Frequency [frequency=" + frequency + ", appliedMultiplier=" + appliedMultiplier + ", frequencyDefType=" + frequencyDefType + "]";
    }

}
